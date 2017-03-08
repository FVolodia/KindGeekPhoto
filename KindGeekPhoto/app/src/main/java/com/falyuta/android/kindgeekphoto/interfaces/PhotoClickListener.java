package com.falyuta.android.kindgeekphoto.interfaces;

import android.view.View;

import com.falyuta.android.kindgeekphoto.models.Photo;

/**
 * Created by volodymyr on 3/7/17.
 */

public interface PhotoClickListener {

    void onPhotoClick(View view, int position, Photo photo);

    void removePhoto(View view, int position, Photo photo);
}
