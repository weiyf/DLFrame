package cn.weiyf.dlframe;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import java.net.URLEncoder;

import cn.weiyf.dlframe.base.BaseAppManager;
import cn.weiyf.dlframe.base.BaseCompatActivity;

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
        initRecovery(getMainPage());
    }

    public boolean isDebug() {
        return Boolean.parseBoolean(getResources().getString(R.string.is_debug));
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

    private void initRecovery(Class<? extends BaseCompatActivity> clazz) {
        Recovery.getInstance()
                .debug(isDebug())
                .recoverEnabled(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .callback(new DLCrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .mainPage(clazz)
                .init(this);
    }

    public static void exitApp() {
        BaseAppManager.getInstance().clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public abstract Class<? extends BaseCompatActivity> getMainPage();

    static final class DLCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            Logger.e("exceptionMessage: " + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            Logger.e(cause + "\ngoogle: http://www.google.com/search?q=" + URLEncoder.encode(cause)
                    + "\nbaidu: https://www.baidu.com/s?wd=s" + URLEncoder.encode(cause));
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            Logger.e("在(" + throwClassName.substring(throwClassName.lastIndexOf(".") + 1) + ".java:" + throwLineNumber + ")报了" + exceptionType);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }


}

