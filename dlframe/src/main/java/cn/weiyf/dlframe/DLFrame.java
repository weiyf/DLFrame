package cn.weiyf.dlframe;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;



/**
 * Created by Administrator on 2017/4/25.
 */

public class DLFrame {


    private static DLFrame mInstance;
    private Context sContext;
    private boolean debug;

    private DLFrame() {
    }

    public static DLFrame getInstance() {
        if (mInstance == null) {
            synchronized (DLFrame.class) {
                if (mInstance == null) {
                    mInstance = new DLFrame();
                }
            }
        }
        return mInstance;
    }

    public boolean isDebug() {
        return debug;
    }

    public void debug(boolean isDebug) {
        this.debug = isDebug;
    }


    public void init(Application application) {
        sContext = application.getApplicationContext();
    }

    public Context getContext() {
        synchronized (DLFrame.class) {
            if (sContext == null)
                throw new NullPointerException("Call DLFrame.init(context) within your Application onCreate() method.");
            return sContext.getApplicationContext();
        }
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    public Resources.Theme getTheme() {
        return getContext().getTheme();
    }

    public AssetManager getAssets() {
        return getContext().getAssets();
    }

    public Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    public int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    public Object getSystemService(String name) {
        return getContext().getSystemService(name);
    }

    public Configuration getConfiguration() {
        return getResources().getConfiguration();
    }

}
