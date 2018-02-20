package com.example.usamanaseer.googlemap.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.usamanaseer.googlemap.service.NotificationService;
import com.example.usamanaseer.googlemap.utils.AppPreference;

public class StartupReceiver extends BroadcastReceiver {

    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isNotificationEnabled = AppPreference.isNotificationEnabled(context);
        NotificationService.setNotificationServiceAlarm(context, isNotificationEnabled);
    }
}
