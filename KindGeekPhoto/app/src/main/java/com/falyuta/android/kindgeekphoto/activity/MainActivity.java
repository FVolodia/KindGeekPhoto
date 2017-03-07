package com.falyuta.android.kindgeekphoto.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.falyuta.android.kindgeekphoto.R;
import com.falyuta.android.kindgeekphoto.interfaces.views.MainView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView{

    @BindView(R.id.recycler_main)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle(getString(R.string.app_name));
    }
}
