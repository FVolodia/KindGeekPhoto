package com.falyuta.android.kindgeekphoto.interfaces;

import android.view.View;
import android.widget.ImageView;

import com.falyuta.android.kindgeekphoto.models.Photo;

/**
 * Created by volodymyr on 3/7/17.
 */

public interface BaseNavigator {

    void openPhotoDetailActivity(Photo photo, View view);
}
