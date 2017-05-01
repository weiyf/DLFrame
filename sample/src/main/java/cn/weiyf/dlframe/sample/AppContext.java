package cn.weiyf.dlframe.sample;

import android.app.Application;

import cn.weiyf.dlframe.DLFrame;

/**
 * Created by Administrator on 2017/4/24.
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DLFrame.getInstance().init(this);
        DLFrame.getInstance().debug(true);
    }


}
