package com.framgia.music5.adapter;

import android.support.annotation.IntDef;

import static com.framgia.music5.adapter.TabType.ALBUM_FRAGMENT;
import static com.framgia.music5.adapter.TabType.ALL_SONG_FRAGMENT;
import static com.framgia.music5.adapter.TabType.FAVORITE_FRAGMENT;

/**
 * Created by MyPC on 22/01/2018.
 */

@IntDef({ALL_SONG_FRAGMENT,ALBUM_FRAGMENT,FAVORITE_FRAGMENT})
public @interface TabType {
    int ALL_SONG_FRAGMENT = 0;
    int ALBUM_FRAGMENT = 1;
    int FAVORITE_FRAGMENT = 2;
}
