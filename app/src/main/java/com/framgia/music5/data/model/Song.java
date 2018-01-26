package com.framgia.music5.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.MediaStore;

import static com.framgia.music5.BaseColumsDatabase.DATA;
import static com.framgia.music5.BaseColumsDatabase.DURATION;
import static com.framgia.music5.BaseColumsDatabase.ID;
import static com.framgia.music5.BaseColumsDatabase.SINGER;
import static com.framgia.music5.BaseColumsDatabase.TITLE;

public class Song {
    private String mId;
    private String mTitle;
    private String mSinger;
    private String mData;
    private long mDuration;

    public Song(String id, String title, String singer, String data, long duration) {
        mId = id;
        mTitle = title;
        mSinger = singer;
        mData = data;
        mDuration = duration;
    }

    public Song(Cursor cursor) {
        mId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        mDuration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        mData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        mSinger = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSinger() {
        return mSinger;
    }

    public String getData() {
        return mData;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSinger(String singer) {
        mSinger = singer;
    }

    public void setData(String data) {
        mData = data;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        if (mId != null) {
            contentValues.put(ID, mId);
        }
        if (mTitle != null) {
            contentValues.put(TITLE, mTitle);
        }
        if (mSinger != null) {
            contentValues.put(SINGER, mSinger);
        }
        if (mDuration != 0) {
            contentValues.put(DURATION, mDuration);
        }
        if (mData != null) {
            contentValues.put(DATA, mData);
        }
        return contentValues;
    }
}
