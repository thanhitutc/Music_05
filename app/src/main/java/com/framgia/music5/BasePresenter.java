package com.framgia.music5;



public interface BasePresenter<T> {

    void setsView(T sView);

    void onStart();

    void onStop();
}
