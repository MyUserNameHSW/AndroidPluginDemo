package com.hsw.classinvokeplugin;

import android.app.Application;

/**
 * @author: hsw
 * @date: 2022/3/29
 * @desc:
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
