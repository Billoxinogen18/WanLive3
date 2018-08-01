package com.ogalo.partympakache.wanlive;


import android.app.Application;


import com.ogalo.partympakache.wanlive.utils.PreferenceUtils;
import com.sendbird.android.SendBird;

public class BaseApplication extends Application {


    private static final String APP_ID = "1DBD9158-6017-402D-81A0-E1946A1AD546"; // US-1 Demo
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
    }
}
