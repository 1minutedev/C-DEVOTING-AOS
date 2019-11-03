package com.kkj.cvoting.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.kkj.cvoting.util.ConfigVariable;

public class Init extends Application {
    private static final String TAG = Init.class.toString();

    private void initSettingInfo() {
        if (ConfigVariable.isRelease) {
            ConfigVariable.CONTENTS_MODE = ConfigVariable.CONTENTS_MODE_ASSETS;
            ConfigVariable.CONTENTS_PATH = "contents/index.html";
        } else {
            SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);

            ConfigVariable.CONTENTS_MODE = pref.getInt("mode", ConfigVariable.CONTENTS_MODE_ABSOLUTE);
            ConfigVariable.CONTENTS_PATH = pref.getString("path", "");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String packageName = getPackageName();
        if (packageName.endsWith(".dev")) {
            ConfigVariable.isRelease = false;
        } else {
            ConfigVariable.isRelease = true;
        }

        initSettingInfo();
    }

}
