package com.framgia.music5.service;

import android.support.annotation.IntDef;

import static com.framgia.music5.service.RepeatType.NO_REPEAT;
import static com.framgia.music5.service.RepeatType.REPEAT_ALL;
import static com.framgia.music5.service.RepeatType.REPEAT_ONE;

/**
 * Created by MyPC on 01/02/2018.
 */

@IntDef({
        NO_REPEAT, REPEAT_ONE, REPEAT_ALL
})
public @interface RepeatType {
    int NO_REPEAT = 0;
    int REPEAT_ONE = 1;
    int REPEAT_ALL = 2;
}
