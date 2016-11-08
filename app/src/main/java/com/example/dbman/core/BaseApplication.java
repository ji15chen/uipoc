package com.example.dbman.core;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.snappydb.DB;
import com.snappydb.DBFactory;

import static com.example.dbman.ui.core.ui_state.UIStateManager.UI_STATE_DB;

/**
 * Created by jerry on 2016/10/26.
 */

public class BaseApplication extends Application {
    private static BaseApplication app = null;
    public  static String DATABASE_URL ;

    private DB uiStateDB;// database for ui state

    public static String getDatabaseUrl() {
        return DATABASE_URL;
    }

    public static void setDatabaseUrl(String databaseUrl) {
        DATABASE_URL = databaseUrl;
    }

    public static synchronized BaseApplication getApp(){
        return app;
    }

    public DB getUiStateDB() {
        return uiStateDB;
    }


    public void onCreate()
    {
        super.onCreate();
        app = this;
        BaseDatabase.getInstance().init(this);
        // TypefaceProvider.registerDefaultIconSets();
        LogUtils.i("init ui database");
        try {
            uiStateDB = DBFactory.open(getApplicationContext(), UI_STATE_DB);
        }catch (Exception e){
            LogUtils.e("ui database error",e);
        }
    }
}
