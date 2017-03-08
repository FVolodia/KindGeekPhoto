package com.falyuta.android.kindgeekphoto.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by volodymyr on 3/7/17.
 */

public class Photo extends RealmObject implements Parcelable {
    @PrimaryKey
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPhotoPath);
        dest.writeString(this.mPhotoDate);
        dest.writeString(this.mPhotoName);
    }

    protected Photo(Parcel in) {
        this.mPhotoPath = in.readString();
        this.mPhotoDate = in.readString();
        this.mPhotoName = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
