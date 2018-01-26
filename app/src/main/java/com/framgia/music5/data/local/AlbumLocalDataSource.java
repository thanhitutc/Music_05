package com.framgia.music5.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.framgia.music5.data.AlbumDataSource;
import com.framgia.music5.data.model.Album;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.music5.BaseColumsDatabase.ID;
import static com.framgia.music5.BaseColumsDatabase.TITLE;
import static com.framgia.music5.data.local.ContractSong.DatabaseAlbum.TABLE_ALBUM;

/**
 * Created by MyPC on 25/01/2018.
 */

public final class AlbumLocalDataSource extends DatabaseHelper implements AlbumDataSource {
    private static AlbumDataSource sSource;

    public static AlbumDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new AlbumLocalDataSource(context);
        }
        return sSource;
    }

    public AlbumLocalDataSource(Context context) {
        super(context);
    }

    @Override
    public List<Album> getListAlbum() {
        return getAlbum();
    }

    @Override
    public boolean insertAlbum(String name) {
        if (name == null || name.equals("")) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TITLE, name);
            result = db.insert(TABLE_ALBUM, null, contentValues);
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean deleteAlbum(int idAlbum) {
        if (idAlbum == -1) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            result = db.delete(TABLE_ALBUM, ID + "=?", new String[] { String.valueOf(idAlbum) });
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean updateName(int idAlbum, String name) {
        if (name == null || idAlbum == -1) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            ContentValues values = new ContentValues();
            values.put(ContractSong.DatabaseAlbum.TITLE, name);
            result = db.update(TABLE_ALBUM, values, ID + "=?",
                    new String[] { String.valueOf(idAlbum) });
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public int getLastIdInsert() {
        return getLastInsertRow();
    }

    private int getLastInsertRow() {
        int id = -1;
        SQLiteDatabase db = getReadableDatabase();
        String[] project = { ID };
        String oderBy = ID + " DESC LIMIT 1";
        Cursor cursor = db.query(TABLE_ALBUM, project, null, null, null, null, oderBy);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(ID));
            cursor.close();
        }
        db.close();
        return id;
    }

    private List<Album> getAlbum() {
        List<Album> albums = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = { ID, TITLE };
        Cursor cursor = db.query(TABLE_ALBUM, project, null, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                albums.add(new Album(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        return albums;
    }
}
