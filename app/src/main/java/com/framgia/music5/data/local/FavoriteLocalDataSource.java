package com.framgia.music5.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import com.framgia.music5.data.FavoriteDataSource;
import com.framgia.music5.data.model.Song;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.framgia.music5.BaseColumsDatabase.ID;

/**
 * Created by MyPC on 24/01/2018.
 */

public final class FavoriteLocalDataSource extends DatabaseHelper implements FavoriteDataSource {
    public static final String DESC = " DESC";
    private static FavoriteDataSource sSource;
    private Context mContext;

    public static FavoriteDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new FavoriteLocalDataSource(context);
        }
        return sSource;
    }

    private FavoriteLocalDataSource(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<Song> getSongFavorite(int type) {
        switch (type) {
            case FavoriteType.IN_TABLE_FAVORITE:
                return getSongFavFromMedia(ContractSong.DatabaseFavorite.IN_TABLE_FAVORITE);
            case FavoriteType.NOT_IN_TABLE_FAVORITE:
                return getSongFavFromMedia(ContractSong.DatabaseFavorite.NOT_IN_TABLE_FAVORITE);
            default:
                return null;
        }
    }

    @Override
    public boolean deleteFavorite(String idSong) {
        if (idSong == null) {
            return false;
        }
        long result;
        SQLiteDatabase db = getWritableDatabase();
        try {
            result = db.delete(ContractSong.DatabaseFavorite.TABLE_FAVORITE,
                    ContractSong.DatabaseFavorite.ID + "=?", new String[] { idSong });
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean insertSongToFavorite(String idSong) {
        if (idSong == null) {
            return false;
        }
        long result;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, idSong);
            result = db.insert(ContractSong.DatabaseFavorite.TABLE_FAVORITE, null, contentValues);
        } finally {
            db.close();
        }

        return result != -1;
    }

    @Override
    public void insertListSongToFavorite(List<Song> songs, CallBackInsertFavorite callBack) {
        if (songs == null) {
            callBack.onInsertNoSong();
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < songs.size(); i++) {
                values.put(ID, songs.get(i).getId());
                long result =
                        db.insertWithOnConflict(ContractSong.DatabaseFavorite.TABLE_FAVORITE, null,
                                values, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } finally {
            db.close();
        }
    }

    private String[] getDataSongFavorite() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = { ID };
        Cursor cursor =
                db.query(ContractSong.DatabaseFavorite.TABLE_FAVORITE, project, null, null, null,
                        null, null);
        if (cursor == null) {
            return null;
        }
        String[] idFavorite = new String[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int i = 0;
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(ID));
                idFavorite[i] = id;
                cursor.moveToNext();
                i++;
            }
            cursor.close();
            db.close();
        }
        return idFavorite;
    }

    private String[] getFavoriteApplication() {
        ArrayList<String> whereFav = new ArrayList<>(Arrays.asList(getDataSongFavorite()));
        ArrayList<String> whereDelete = new ArrayList<>(Arrays.asList(getSongDeleted()));
        whereFav.removeAll(whereDelete);
        return whereFav.toArray(new String[0]);
    }

    private List<Song> getSongFavFromMedia(String where) {
        String[] whereArg = getFavoriteApplication();
        Cursor cursor;
        if (whereArg.length != 0) {
            String whereClause = MediaStore.Audio.Media._ID + where;
            for (String arg : whereArg) {
                whereClause += "?,";
            }
            whereClause = whereClause.substring(0, whereClause.length() - 1) + ")";
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, whereClause, whereArg,
                            MediaStore.Audio.Media.DATE_ADDED + DESC);
        } else {
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                            MediaStore.Audio.Media.DATE_ADDED + DESC);
        }
        if (cursor == null) {
            return null;
        }
        List<Song> songs = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                songs.add(new Song(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return songs;
    }
}
