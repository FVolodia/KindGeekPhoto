package com.falyuta.android.kindgeekphoto.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.interfaces.views.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

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
}
