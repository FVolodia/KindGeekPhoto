package com.falyuta.android.kindgeekphoto.presenter;

import android.view.View;
import android.widget.ImageView;

import com.falyuta.android.kindgeekphoto.models.Photo;

/**
 * Created by volodymyr on 3/7/17.
 */

public interface IMainPresenter {

    void getPhotos();

    void addPhoto(String path, String name, String date);

    void getPhotoDetail(Photo photo, View view);
}
