package com.falyuta.android.kindgeekphoto.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.falyuta.android.kindgeekphoto.adapters.PhotoAdapter;
import com.falyuta.android.kindgeekphoto.interfaces.PhotoClickListener;
import com.falyuta.android.kindgeekphoto.models.Photo;
import com.falyuta.android.kindgeekphoto.presenter.IMainPresenter;
import com.falyuta.android.kindgeekphoto.presenter.MainPresenterImpl;
import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.utils.AlbumStorageDirFactory;
import com.falyuta.android.kindgeekphoto.utils.BaseAlbumDirFactory;
import com.falyuta.android.kindgeekphoto.utils.Constants;
import com.falyuta.android.kindgeekphoto.utils.DisplayUtil;
import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.MainView;
import com.falyuta.android.kindgeekphoto.utils.DividerItemDecoration;
import com.falyuta.android.kindgeekphoto.utils.EndOffsetItemDecoration;
import com.falyuta.android.kindgeekphoto.utils.FroyoAlbumDirFactory;
import com.falyuta.android.kindgeekphoto.utils.StartOffsetItemDecoration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements MainView, BaseNavigator, PhotoClickListener {

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mAddPhotoBtn;
    private IMainPresenter mPresenter;
    private PhotoAdapter mAdapter;
    private String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle(getString(R.string.app_name));
        mPresenter = new MainPresenterImpl(this, this);
        setUpAdapter();
        mAddPhotoBtn.setOnClickListener(view ->
                checkPermissionForSelectPhoto());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
        mPresenter.getPhotos();
    }

    private void setUpAdapter() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new EndOffsetItemDecoration(24));
        mRecyclerView.addItemDecoration(new StartOffsetItemDecoration(24));

        Drawable dividerItem = ContextCompat.getDrawable(this, R.drawable.divider_item_photo);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerItem));

        mAdapter = new PhotoAdapter().withPhotoClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void checkPermissionForSelectPhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
            } else {
                createChooserDialogForSelectDriverPhoto();
            }
        } else {
            createChooserDialogForSelectDriverPhoto();
        }
    }

    private void requestCameraPermission() {
        String[] requestPermissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, requestPermissions, Constants.ACCESS_PERMISSION_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.ACCESS_PERMISSION_CAMERA) {
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                createChooserDialogForSelectDriverPhoto();
            } else {
                requestCameraPermission();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void createChooserDialogForSelectDriverPhoto() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_photo);
        //noinspection ConstantConditions
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.y = DisplayUtil.getRelativeTop(mAddPhotoBtn);
        dialog.show();

        dialog.findViewById(R.id.take_photo).setOnClickListener(v -> {
            dialog.dismiss();
            dispatchTakePictureIntent();
        });

        dialog.findViewById(R.id.upload_photo_from_gallery).setOnClickListener(v -> {
            dialog.dismiss();
            takePhotoFromGallery();
        });
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = null;

        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
        startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
    }

    private void takePhotoFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Constants.REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            }

            case Constants.REQUEST_IMAGE_GALLERY: {
                if (resultCode == RESULT_OK) {
                    handlePhotoFromGalery(data);
                }
                break;
            }

        }
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
            Log.d("555", "handleBigCameraPhoto: " + mCurrentPhotoPath);
            galleryAddPic();
            mCurrentPhotoPath = null;

        }

    }

    private void handlePhotoFromGalery(Intent intent) {
        Uri selectedImage = intent.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mCurrentPhotoPath = cursor.getString(columnIndex);
            cursor.close();
            Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
            Log.d("555", "handleBigCameraPhoto: " + mCurrentPhotoPath);
        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = Constants.JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, Constants.JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        mAdapter.setListPhotos(photos);
    }
}
