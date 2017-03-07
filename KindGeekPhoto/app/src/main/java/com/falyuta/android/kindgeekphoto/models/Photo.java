package com.falyuta.android.kindgeekphoto.models;

/**
 * Created by volodymyr on 3/7/17.
 */

public class Photo {
    private String mPhotoPath;
    private String mPhotoDate;
    private String mPhotoName;

    public Photo() {
    }

    public Photo(String photoPath, String photoDate, String photoName) {
        mPhotoPath = photoPath;
        mPhotoDate = photoDate;
        mPhotoName = photoName;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        mPhotoPath = photoPath;
    }

    public String getPhotoDate() {
        return mPhotoDate;
    }

    public void setPhotoDate(String photoDate) {
        mPhotoDate = photoDate;
    }

    public String getPhotoName() {
        return mPhotoName;
    }

    public void setPhotoName(String photoName) {
        mPhotoName = photoName;
    }
}
