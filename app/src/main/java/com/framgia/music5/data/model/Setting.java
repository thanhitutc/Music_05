package com.framgia.music5.data.model;

/**
 * Created by MyPC on 02/02/2018.
 */

public class Setting {
    private boolean mShuffleMode;
    private int mRepeatMode;

    public boolean isShuffleMode() {
        return mShuffleMode;
    }

    public void setShuffleMode(boolean shuffleMode) {
        mShuffleMode = shuffleMode;
    }

    public int getRepeatMode() {
        return mRepeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
    }
}
