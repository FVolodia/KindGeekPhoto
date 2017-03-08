package com.falyuta.android.kindgeekphoto.presenter;

import android.content.Intent;

import com.falyuta.android.kindgeekphoto.activity.PhotoDetailActivity;
import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.DetailView;
import com.falyuta.android.kindgeekphoto.models.Photo;


/**
 * Created by volodymyr on 3/7/17.
 */
public class DetailPresenterImpl extends BasePresenter<DetailView, BaseNavigator>
        implements IDetailPresenter {

    private static final String TAG = DetailPresenterImpl.class.getSimpleName();
    private Photo mPhoto;


    public DetailPresenterImpl(DetailView view, BaseNavigator navigator) {
        setView(view);
        setNavigator(navigator);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void init(Intent intent) {
        mPhoto = (Photo) intent.getParcelableExtra(PhotoDetailActivity.ARG_PHOTO);
        view.showPhoto(mPhoto);
    }
}
