package cn.weiyf.dlframe.sample;

import cn.weiyf.dlframe.DLApplication;
import cn.weiyf.dlframe.base.BaseCompatActivity;

/**
 * Created by Administrator on 2016/9/23.
 */

public class SampleApplication extends DLApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public Class<? extends BaseCompatActivity> getMainPage() {
        return MainActivity.class;
    }

    @Override
    public String getLogTag() {
        return "test";
    }
}
