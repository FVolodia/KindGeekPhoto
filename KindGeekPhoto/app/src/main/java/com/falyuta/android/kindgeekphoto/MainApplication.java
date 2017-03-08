package com.falyuta.android.kindgeekphoto;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by FVolodia on 08.03.17.
 */

public class MainApplication extends Application {

    private static MainApplication instance;
    private RealmConfiguration config;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm realm = Realm.getInstance(getRealmConfig());
        realm.close();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public RealmConfiguration getRealmConfig() {
        if (config == null) {
            config = new RealmConfiguration.Builder(this)
                    .name(getString(R.string.app_name))
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
        }
        return config;
    }
}
