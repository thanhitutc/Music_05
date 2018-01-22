package com.framgia.music5.screen.main;

import android.support.annotation.IntDef;

import static com.framgia.music5.screen.main.MainType.ALBUM;
import static com.framgia.music5.screen.main.MainType.ALL_SONG;
import static com.framgia.music5.screen.main.MainType.FAVORITE;

/**
 * Created by MyPC on 22/01/2018.
 */

@IntDef({ALL_SONG, ALBUM, FAVORITE})
public @interface MainType {
    int ALL_SONG = 0;
    int ALBUM = 1;
    int FAVORITE = 2;
}
