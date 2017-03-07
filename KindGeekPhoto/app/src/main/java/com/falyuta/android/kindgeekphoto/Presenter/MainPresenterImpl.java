package com.falyuta.android.kindgeekphoto.presenter;

import com.falyuta.android.kindgeekphoto.interfaces.BaseNavigator;
import com.falyuta.android.kindgeekphoto.interfaces.views.MainView;

/**
 * Created by volodymyr on 3/7/17.
 */
public class MainPresenterImpl extends BasePresenter<MainView, BaseNavigator>
        implements IMainPresenter {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    public MainPresenterImpl(MainView view, BaseNavigator navigator) {
        setView(view);
        setNavigator(navigator);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
