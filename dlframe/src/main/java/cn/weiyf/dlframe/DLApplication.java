package cn.weiyf.dlframe;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.weiyf.dlframe.base.BaseAppManager;

/**
 * Created by Administrator on 2016/9/23.
 */

public abstract class DLApplication extends Application {

    private static String TAG = "DL_LOG";

    public static DLApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            synchronized (DLApplication.class) {
                if (mInstance == null) {
                    mInstance = DLApplication.this;
                }
            }
        }
        initLogger(getLogTag());
    }

    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public String getLogTag() {
        return TAG;
    }

    private void initLogger(String tag) {
        Logger.init(tag)
                .methodCount(1)
                .hideThreadInfo()
                .logLevel(isDebug() ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(0);
    }

    public static void exitApp() {
        BaseAppManager.getInstance().clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

