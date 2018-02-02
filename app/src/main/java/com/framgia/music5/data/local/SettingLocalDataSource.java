package com.framgia.music5.data.local;

import android.content.Context;
import com.framgia.music5.data.SettingDataSource;
import com.framgia.music5.data.model.Setting;
import com.framgia.music5.ultils.SharedPrefs;

import static com.framgia.music5.ultils.Constant.ConstantSharePrefs.PREF_REPEAT_MEDIA;
import static com.framgia.music5.ultils.Constant.ConstantSharePrefs.PREF_SHUFFLE_MEDIA;

/**
 * Created by MyPC on 02/02/2018.
 */

public final class SettingLocalDataSource implements SettingDataSource {
    private static SettingDataSource sSource;
    private SharedPrefs mSharedPrefs;

    public static SettingDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new SettingLocalDataSource(context);
        }
        return sSource;
    }

    private SettingLocalDataSource(Context context) {
        mSharedPrefs = SharedPrefs.getInstance(context);
    }

    @Override
    public Setting getSetting() {
        Setting setting = new Setting();
        setting.setShuffleMode(mSharedPrefs.get(PREF_SHUFFLE_MEDIA, Boolean.class));
        setting.setRepeatMode(mSharedPrefs.get(PREF_REPEAT_MEDIA, Integer.class));
        return setting;
    }

    @Override
    public void saveSetting(Setting setting) {
        if (setting == null) {
            return;
        }
        mSharedPrefs.put(PREF_REPEAT_MEDIA, setting.getRepeatMode());
        mSharedPrefs.put(PREF_SHUFFLE_MEDIA, setting.isShuffleMode());
    }
}
