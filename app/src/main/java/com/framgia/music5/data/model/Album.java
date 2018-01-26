package com.framgia.music5.data.model;

import android.database.Cursor;

import static com.framgia.music5.BaseColumsDatabase.ID;
import static com.framgia.music5.BaseColumsDatabase.TITLE;

/***
 * Album
 * */
public class Album {
    private int mId;
    private String mNameAlbum;

    public Album(int id, String name) {
        mId = id;
        mNameAlbum = name;
    }

    public Album(Cursor cursor) {
        mId = cursor.getInt(cursor.getColumnIndex(ID));
        mNameAlbum = cursor.getString(cursor.getColumnIndex(TITLE));
    }

    public int getId() {
        return mId;
    }

    public String getNameAlbum() {
        return mNameAlbum;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setNameAlbum(String nameAlbum) {
        mNameAlbum = nameAlbum;
    }
}
