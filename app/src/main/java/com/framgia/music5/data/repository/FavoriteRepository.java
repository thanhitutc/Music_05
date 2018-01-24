package com.framgia.music5.data.repository;

import android.content.Context;
import com.framgia.music5.data.FavoriteDataSource;
import com.framgia.music5.data.local.FavoriteLocalDataSource;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 24/01/2018.
 */

public final class FavoriteRepository implements FavoriteDataSource {
    private static FavoriteRepository sRepository;
    private FavoriteDataSource mFavoriteDataSource;

    public static FavoriteRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new FavoriteRepository(FavoriteLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private FavoriteRepository(FavoriteDataSource favoriteDataSource) {
        mFavoriteDataSource = favoriteDataSource;
    }

    @Override
    public List<Song> getSongFavorite(int type) {
        return mFavoriteDataSource.getSongFavorite(type);
    }


    @Override
    public boolean deleteFavorite(String idSong) {
        return mFavoriteDataSource.deleteFavorite(idSong);
    }

    @Override
    public boolean insertSongToFavorite(String idSong) {
        return mFavoriteDataSource.insertSongToFavorite(idSong);
    }

    @Override
    public void insertListSongToFavorite(List<Song> songs, CallBackInsertFavorite callBack) {
        mFavoriteDataSource.insertListSongToFavorite(songs, callBack);
    }
}
