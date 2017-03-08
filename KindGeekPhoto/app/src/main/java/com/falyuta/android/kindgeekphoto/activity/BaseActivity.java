package com.falyuta.android.kindgeekphoto.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.BaseView;
import com.falyuta.android.kindgeekphoto.models.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView, BaseNavigator {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitleTv;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbarTitleTv.setText(title);
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void openPhotoDetailActivity(Photo photo, View view) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PhotoDetailActivity.ARG_PHOTO, photo);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(BaseActivity.this,
                        view,
                        ViewCompat.getTransitionName(view));
        startActivity(intent, options.toBundle());
    }
}
