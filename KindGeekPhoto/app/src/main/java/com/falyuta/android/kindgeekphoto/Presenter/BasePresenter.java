package com.falyuta.android.kindgeekphoto.Presenter;

import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.BaseView;

/**
 * Created by volodymyr on 3/7/17.
 */

public abstract class BasePresenter<V extends BaseView, N extends BaseNavigator> {
    protected V view;
    protected N navigator;

    protected void setView(V view) {
        this.view = view;
    }

    protected void setNavigator(N navigator) {
        this.navigator = navigator;
    }


    public void onResume() {
    }

    public void onStart() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
        view = null;
        navigator = null;
    }
}
