package com.falyuta.android.kindgeekphoto.utils;

import android.os.Environment;

import com.falyuta.android.kindgeekphoto.utils.AlbumStorageDirFactory;

import java.io.File;

/**
 * Created by FVolodia on 08.03.17.
 */

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

    // Standard storage location for digital camera files
    private static final String CAMERA_DIR = "/dcim/";

    @Override
    public File getAlbumStorageDir(String albumName) {
        return new File (
                Environment.getExternalStorageDirectory()
                        + CAMERA_DIR
                        + albumName
        );
    }
}
