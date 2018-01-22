package com.framgia.music5.data.model;
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
