package com.falyuta.android.kindgeekphoto.presenter;

import android.view.View;

import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.MainView;
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

        photos.add(new Photo("/storage/emulated/0/Pictures/Kind Geek Photo/IMG_20170308_011029_-1257200905.jpg","23","34"));
        photos.add(new Photo("/storage/emulated/0/Pictures/Screenshots/Screenshot_20170306-172107.png","23","34"));
        photos.add(new Photo("/storage/emulated/0/DCIM/Camera/IMG_20170225_204112.jpg","23","34"));
        photos.add(new Photo("/storage/emulated/0/Pictures/Kind Geek Photo/IMG_20170308_011058_-1391305510.jpg","23","34"));

        view.showPhotos(photos);
    }

    @Override
    public void addPhoto(String path) {
        photos.add(new Photo(path, "sd", "sd"));
        view.showPhotos(photos);
    }

    @Override
    public void getPhotoDetail(Photo photo, View view) {
        navigator.openPhotoDetailActivity(photo, view);
    }


}
