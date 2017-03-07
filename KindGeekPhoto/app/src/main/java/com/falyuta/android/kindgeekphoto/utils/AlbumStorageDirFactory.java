package com.falyuta.android.kindgeekphoto.utils;

import java.io.File;

/**
 * Created by FVolodia on 08.03.17.
 */

public abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
