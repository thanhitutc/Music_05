package com.framgia.music5.service;

/**
 * Created by MyPC on 30/01/2018.
 */

public interface BaseMediaPlayer {

    void start();

    void play(int position);

    void pause();

    void resume();

    void stop();

    void release();

    void seekTo(int index);

    void next();

    void previous();

    long getDuration();

    long getCurrentDuration();

    boolean isPlay();
}
