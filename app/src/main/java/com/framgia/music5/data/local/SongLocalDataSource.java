package com.framgia.music5.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.framgia.music5.data.SongDataSource;
import com.framgia.music5.data.model.Song;
import java.util.List;

import static com.framgia.music5.BaseColumsDatabase.ID;
import static com.framgia.music5.data.local.ContractSong.DatabaseSongDeleted.TABLE_SONG_DELETED;

/**
 * Created by MyPC on 22/01/2018.
 */

public final class SongLocalDataSource extends DatabaseHelper implements SongDataSource {
    private static SongDataSource sSource;

    public static SongDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new SongLocalDataSource(context);
        }
        return sSource;
    }

    private SongLocalDataSource(Context context) {
        super(context);
    }

    @Override
    public List<Song> getSong() {
        return getSongsFromMediaStore();
    }

    @Override
    public List<Song> getSongByName(String name) {
        return null;
    }

    @Override
    public boolean deleteSong(String id) {
        if (id == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        long result = db.insert(TABLE_SONG_DELETED, null, contentValues);
        return result != -1;
    }
}
