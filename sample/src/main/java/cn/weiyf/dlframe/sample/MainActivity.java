package cn.weiyf.dlframe.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.weiyf.dlframe.base.BaseCompatActivity;
import cn.weiyf.dlframe.loading.onDismissListener;

public class MainActivity extends BaseCompatActivity {

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = null;
                activity.finish();
            }
        });
    }
}
