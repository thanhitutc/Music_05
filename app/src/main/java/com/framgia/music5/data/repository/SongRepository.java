package com.framgia.music5.data.repository;

import android.content.Context;
import com.framgia.music5.data.SongDataSource;
import com.framgia.music5.data.local.SongLocalDataSource;
import com.framgia.music5.data.model.Song;
import java.util.List;

/**
 * Created by MyPC on 22/01/2018.
 */

public final class SongRepository implements SongDataSource {
    private static SongRepository sRepository;
    private SongDataSource mSongDataSource;

    public static SongRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new SongRepository(SongLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private SongRepository(SongDataSource instance) {
        mSongDataSource = instance;
    }

    @Override
    public List<Song> getSong() {
        return mSongDataSource.getSong();
    }

    @Override
    public List<Song> getSongByName(String name) {
        return mSongDataSource.getSongByName(name);
    }

    @Override
    public boolean deleteSong(String id) {
        return mSongDataSource.deleteSong(id);
    }
}
