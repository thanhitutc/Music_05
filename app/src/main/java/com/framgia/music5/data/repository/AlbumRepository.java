package com.framgia.music5.data.repository;

import android.content.Context;
import com.framgia.music5.data.AlbumDataSource;
import com.framgia.music5.data.local.AlbumLocalDataSource;
import com.framgia.music5.data.model.Album;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public final class AlbumRepository implements AlbumDataSource {
    private static AlbumRepository sRepository;
    private AlbumDataSource mAlbumDataSource;

    public static AlbumRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new AlbumRepository(AlbumLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private AlbumRepository(AlbumDataSource instance) {
        mAlbumDataSource = instance;
    }

    @Override
    public List<Album> getListAlbum() {
        return mAlbumDataSource.getListAlbum();
    }

    @Override
    public boolean insertAlbum(String name) {
        return mAlbumDataSource.insertAlbum(name);
    }

    @Override
    public boolean deleteAlbum(int idAlbum) {
        return mAlbumDataSource.deleteAlbum(idAlbum);
    }

    @Override
    public boolean updateName(int idAlbum, String name) {
        return mAlbumDataSource.updateName(idAlbum, name);
    }
}
