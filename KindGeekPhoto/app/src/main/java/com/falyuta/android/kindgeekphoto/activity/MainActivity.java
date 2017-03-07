package com.falyuta.android.kindgeekphoto.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.falyuta.android.kindgeekphoto.Presenter.IMainPresenter;
import com.falyuta.android.kindgeekphoto.Presenter.MainPresenterImpl;
import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.Utils.Constants;
import com.falyuta.android.kindgeekphoto.Utils.DisplayUtil;
import com.falyuta.android.kindgeekphoto.Utils.ImageUtils;
import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.MainView;

import java.io.File;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements MainView, BaseNavigator {

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mAddPhotoBtn;
    private IMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle(getString(R.string.app_name));
        mPresenter = new MainPresenterImpl(this, this);
        mAddPhotoBtn.setOnClickListener(view ->
                checkPermissionForSelectPhoto());
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
            takePhoto();
        });

        dialog.findViewById(R.id.upload_photo_from_gallery).setOnClickListener(v -> {
            dialog.dismiss();
            takePhotoFromGallery();
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, Constants.REQUEST_IMAGE_CAPTURE);

        } else {
            Toast.makeText(this, "Not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePhotoFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Constants.REQUEST_IMAGE_GALLERY);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String picturePath;
        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            picturePath = file.getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);
            Bitmap rotatedBitmap = ImageUtils.rotateImage(bitmap, picturePath);
//            mUserPhotoIv.setImageBitmap(rotatedBitmap);
//            imgProfileUrl = picturePath;

        } else if (requestCode == Constants.REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK &&
                null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                    null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                final Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

                if (bitmap != null) {
//                    mUserPhotoIv.setImageBitmap(ImageUtils.rotateImage(bitmap, picturePath));
//                    imgProfileUrl = picturePath;
                }
            }
        }
    }

}
