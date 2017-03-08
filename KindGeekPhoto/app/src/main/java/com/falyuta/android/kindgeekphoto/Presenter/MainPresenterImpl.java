package com.falyuta.android.kindgeekphoto.presenter;

import android.view.View;

import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.MainView;
import com.falyuta.android.kindgeekphoto.manager.RealmPhotoManager;
import com.falyuta.android.kindgeekphoto.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by volodymyr on 3/7/17.
 */
public class MainPresenterImpl extends BasePresenter<MainView, BaseNavigator>
        implements IMainPresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    List<Photo> photos = new ArrayList<>();

    public MainPresenterImpl(MainView view, BaseNavigator navigator) {
        setView(view);
        setNavigator(navigator);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getPhotos() {
        photos = RealmPhotoManager.getPhoto();
        view.showPhotos(photos);
    }

    @Override
    public void addPhoto(String path, String name, String date) {
        RealmPhotoManager.updatePhoto(new Photo(path,date,name));
        view.invalidateData();
    }

    @Override
    public void getPhotoDetail(Photo photo, View view) {
        navigator.openPhotoDetailActivity(photo, view);
    }

    @Override
    public void removePhoto(Photo photo, int position) {
        RealmPhotoManager.removePhotoFromDb(photo.getPhotoPath());
        view.invalidateData();
    }


}
