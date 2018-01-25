package com.framgia.music5;

/**
 * BasePresenter
 */
public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
