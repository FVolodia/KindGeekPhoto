package com.falyuta.android.kindgeekphoto.utils;

import android.os.Environment;

import com.falyuta.android.kindgeekphoto.utils.AlbumStorageDirFactory;

import java.io.File;

/**
 * Created by FVolodia on 08.03.17.
 */

public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

    @Override
    public File getAlbumStorageDir(String albumName) {
        // TODO Auto-generated method stub
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                albumName
        );
    }
}
