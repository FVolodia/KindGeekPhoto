package com.falyuta.android.kindgeekphoto.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.DetailView;
import com.falyuta.android.kindgeekphoto.models.Photo;
import com.falyuta.android.kindgeekphoto.presenter.DetailPresenterImpl;
import com.falyuta.android.kindgeekphoto.presenter.IDetailPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailActivity extends AppCompatActivity implements DetailView, BaseNavigator {
    public static final String ARG_PHOTO = "_argPhoto";

    @BindView(R.id.photo_detail_iv)
    ImageView mDetailIv;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private IDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        mPresenter = new DetailPresenterImpl(this, this);
        mPresenter.init(getIntent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPhoto(Photo photo) {
        mCollapsingToolbarLayout.setTitle(photo.getPhotoName());
        Glide
                .with(this)
                .load(new File(photo.getPhotoPath()))
                .centerCrop()
                .crossFade()
                .into(mDetailIv);
    }

    @Override
    public void setToolbarTitle(String title) {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void openPhotoDetailActivity(Photo photo, View view) {

    }
}
