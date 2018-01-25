package com.framgia.music5.data.repository;

import android.content.Context;
import com.framgia.music5.data.SongInAlbumDataSource;
import com.framgia.music5.data.local.SongInAlbumLocalDataSource;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public final class SongInAlbumRepository implements SongInAlbumDataSource {
    private static SongInAlbumRepository sRepository;
    private SongInAlbumDataSource mSongInAlbumDataSource;

    public static SongInAlbumRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository =
                    new SongInAlbumRepository(SongInAlbumLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private SongInAlbumRepository(SongInAlbumDataSource instance) {
        mSongInAlbumDataSource = instance;
    }

    @Override
    public List<Song> getSongOfAlbum(int idAlbum) {
        return mSongInAlbumDataSource.getSongOfAlbum(idAlbum);
    }

    @Override
    public boolean insertSongToAlbum(int idAlbum, String idSong) {
        return mSongInAlbumDataSource.insertSongToAlbum(idAlbum, idSong);
    }

    @Override
    public boolean deleteAllSongOfAlbum(int idAlbum) {
        return mSongInAlbumDataSource.deleteAllSongOfAlbum(idAlbum);
    }

    @Override
    public boolean deleteSongInAlbum(String idSong, int idAlbum) {
        return mSongInAlbumDataSource.deleteSongInAlbum(idSong, idAlbum);
    }
}
