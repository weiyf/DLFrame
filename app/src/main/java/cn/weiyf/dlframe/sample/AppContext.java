package cn.weiyf.dlframe.sample;

import android.app.Application;

import cn.weiyf.dlframe.DLFrame;

/**
 * Created by Administrator on 2017/7/9.
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DLFrame.getInstance().debug(true);
        DLFrame.getInstance().init(this);
    }
}
