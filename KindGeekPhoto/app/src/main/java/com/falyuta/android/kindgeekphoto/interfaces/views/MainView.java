package com.falyuta.android.kindgeekphoto.interfaces.views;

import com.falyuta.android.kindgeekphoto.models.Photo;

import java.util.List;

/**
 * Created by volodymyr on 3/7/17.
 */

public interface MainView extends BaseView {

    void showPhotos(List<Photo> photos);

    void invalidateData();



}
