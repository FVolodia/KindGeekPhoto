package com.falyuta.android.kindgeekphoto.manager;

import com.falyuta.android.kindgeekphoto.MainApplication;
import com.falyuta.android.kindgeekphoto.models.Photo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by FVolodia on 08.03.17.
 */

public class RealmPhotoManager {

    public static void updatePhoto(final Photo photo) {
        Realm realm = Realm.getInstance(MainApplication.getInstance().getRealmConfig());
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(photo));
    }

    public static List<Photo> getPhoto() {
        List<Photo> responsePhotos = new ArrayList<>();
        Realm realm = Realm.getInstance(MainApplication.getInstance().getRealmConfig());
        RealmResults<Photo> realmPhotos = realm.where(Photo.class)
                .findAllSorted("mPhotoDate");
        if (realmPhotos.isValid()) {
            for (Photo photo : realmPhotos) {
                responsePhotos.add(photo);
            }
        }
        return responsePhotos;

    }

    public static void removePhotoFromDb(final String photoPath) {
        Realm realm = Realm.getInstance(MainApplication.getInstance().getRealmConfig());
        realm.executeTransaction(realm1 -> {
            RealmResults<Photo> realmCard = realm1.where(Photo.class).equalTo("mPhotoPath", photoPath).findAll();
            realmCard.deleteAllFromRealm();
        });
    }
}
