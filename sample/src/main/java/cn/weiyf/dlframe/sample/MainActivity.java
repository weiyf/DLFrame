package cn.weiyf.dlframe.sample;

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
import cn.weiyf.dlframe.sample.databinding.ActivityMainBinding;
import cn.weiyf.dlframe.sample.entity.Test;

public class MainActivity extends BaseCompatActivity {

    private ActivityMainBinding mMainBinding;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setAdapter(new MultiTypeAdapter());
        mMainBinding.getAdapter().setPresenter(new Presenter());
        mMainBinding.getAdapter().addViewTypeToLayoutMap(1, R.layout.item_test);
        mMainBinding.getAdapter().addViewTypeToLayoutMap(2, R.layout.item_test1);
        mMainBinding.getAdapter().addViewTypeToLayoutMap(3, R.layout.item_test2);
        mMainBinding.setLayoutManager(new LinearLayoutManager(this));

        List<Test> tests = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tests.add(new Test("小明", new Random().nextInt(10)));
        }
        mMainBinding.getAdapter().addAll(new MultiTypeAdapter.MultiViewType() {
            @Override
            public int getViewType(Object item) {
                return ((Test) item).getType();
            }
        }, tests);
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
}
