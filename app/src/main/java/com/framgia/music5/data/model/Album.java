package com.framgia.music5.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.framgia.music5.BaseColumsDatabase.ID;
import static com.framgia.music5.BaseColumsDatabase.TITLE;

/***
 * Album
 * */
public class Album implements Parcelable {
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

    public Album(Parcel in) {
        mId = in.readInt();
        mNameAlbum = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mNameAlbum);
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

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
