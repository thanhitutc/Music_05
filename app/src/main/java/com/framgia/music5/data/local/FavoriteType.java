package com.framgia.music5.data.local;

import android.support.annotation.IntDef;

import static com.framgia.music5.data.local.FavoriteType.IN_TABLE_FAVORITE;
import static com.framgia.music5.data.local.FavoriteType.NOT_IN_TABLE_FAVORITE;

/**
 * Created by MyPC on 24/01/2018.
 */

@IntDef({
        IN_TABLE_FAVORITE, NOT_IN_TABLE_FAVORITE
})
public @interface FavoriteType {
    int IN_TABLE_FAVORITE = 0;
    int NOT_IN_TABLE_FAVORITE = 1;
}
