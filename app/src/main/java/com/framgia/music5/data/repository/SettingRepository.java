package com.framgia.music5.data.repository;

import com.framgia.music5.data.SettingDataSource;
import com.framgia.music5.data.model.Setting;

/**
 * Created by MyPC on 02/02/2018.
 */

public final class SettingRepository implements SettingDataSource {
    private static SettingRepository sRepository;
    private SettingDataSource mSettingDataSource;

    public static SettingRepository getInstance(SettingDataSource localDataSource) {
        if (sRepository == null) {
            sRepository = new SettingRepository(localDataSource);
        }
        return sRepository;
    }

    private SettingRepository(SettingDataSource instance) {
        mSettingDataSource = instance;
    }

    @Override
    public Setting getSetting() {
        return mSettingDataSource.getSetting();
    }

    @Override
    public void saveSetting(Setting setting) {
        mSettingDataSource.saveSetting(setting);
    }
}
