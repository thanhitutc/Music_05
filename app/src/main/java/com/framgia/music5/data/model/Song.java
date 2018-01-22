package com.framgia.music5.data.model;

public class Song {
    private String mId;
    private String mImageUrl;
    private String mTitle;
    private String mSinger;
    private String mData;
    private long mDuration;

    public String getId() {
        return mId;
    }

    public String getImageUrl() {
        return mImageUrl;
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

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
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

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
