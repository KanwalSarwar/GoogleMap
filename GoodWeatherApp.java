package com.example.usamanaseer.googlemap;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;

import com.example.usamanaseer.googlemap.utils.LanguageUtil;
import com.example.usamanaseer.googlemap.utils.PreferenceUtil;
import com.example.usamanaseer.googlemap.utils.PreferenceUtil.Theme;

public class GoodWeatherApp extends Application {

    private static final String TAG = "GoodWeatherApp";

    private static Theme sTheme = Theme.light;

    @Override
    public void onCreate() {
        super.onCreate();
        LanguageUtil.setLanguage(this, PreferenceUtil.getLanguage(this));

        sTheme = PreferenceUtil.getTheme(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageUtil.setLanguage(this, PreferenceUtil.getLanguage(this));
    }

    public void reloadTheme() {
        sTheme = PreferenceUtil.getTheme(this);
    }

    public void applyTheme(Activity activity) {
        activity.setTheme(getThemeResId());
    }

    public static int getThemeResId() {
        switch (sTheme) {
            case light:
                return R.style.AppThemeLight;
            case dark:
                return R.style.AppThemeDark;
            default:
                return R.style.AppThemeLight;
        }
    }
}
