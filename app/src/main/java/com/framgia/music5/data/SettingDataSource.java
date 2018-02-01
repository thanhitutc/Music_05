package com.framgia.music5.data;

import com.framgia.music5.data.model.Setting;

/**
 * Created by MyPC on 02/02/2018.
 */

public interface SettingDataSource {

    Setting getSetting();

    void saveSetting(Setting setting);
}
