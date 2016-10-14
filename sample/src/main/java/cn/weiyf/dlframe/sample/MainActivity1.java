package cn.weiyf.dlframe.sample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.weiyf.dlframe.adapter.BindingViewHolder;
import cn.weiyf.dlframe.adapter.MultiTypeAdapter;
import cn.weiyf.dlframe.adapter.SingleTypeAdapter;
import cn.weiyf.dlframe.base.BaseCompatActivity;
import cn.weiyf.dlframe.base.BaseSwipeBackCompatActivity;
import cn.weiyf.dlframe.sample.databinding.ActivityMainBinding;
import cn.weiyf.dlframe.sample.entity.Test;

public class MainActivity1 extends BaseCompatActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
    }


    public class Presenter implements SingleTypeAdapter.Presenter<Test> {

        @Override
        public void onItemClick(Test test) {
            showToast(test.getName());
        }
    }

    public class Decorator implements SingleTypeAdapter.Decorator {

        @Override
        public void decorator(BindingViewHolder holder, int position, int viewType) {

        }
    }

    @Override
    protected boolean isSwipeBackEnable() {
        return true;
    }
}
