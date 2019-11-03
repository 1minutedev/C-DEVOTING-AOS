package com.kkj.cvoting.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.webkit.WebView;

import com.kkj.cvoting.util.ConfigVariable;

import androidx.multidex.MultiDex;

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

            WebView.setWebContentsDebuggingEnabled(true);
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

    public String getStartPage() {
        String url = "";

        if (ConfigVariable.CONTENTS_MODE == ConfigVariable.CONTENTS_MODE_ASSETS) {
            url = "file:///android_asset/" + ConfigVariable.CONTENTS_HTML;
        } else if (ConfigVariable.CONTENTS_MODE == ConfigVariable.CONTENTS_MODE_EXTERNAL) {
            url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ConfigVariable.CONTENTS_PATH + ConfigVariable.CONTENTS_HTML;
        } else if (ConfigVariable.CONTENTS_MODE == ConfigVariable.CONTENTS_MODE_ABSOLUTE) {
            url = ConfigVariable.CONTENTS_PATH;
        }

        return url;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
