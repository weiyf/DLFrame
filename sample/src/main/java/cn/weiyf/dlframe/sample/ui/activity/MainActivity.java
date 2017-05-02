package cn.weiyf.dlframe.sample.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import cn.weiyf.dlframe.adapter.MultiTypeAdapter;
import cn.weiyf.dlframe.adapter.SingleTypeAdapter;
import cn.weiyf.dlframe.sample.R;
import cn.weiyf.dlframe.sample.base.BaseActivity;
import cn.weiyf.dlframe.sample.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {


    private ActivityMainBinding mMainBinding;

    private MultiTypeAdapter mAdapter;

    private LinearLayoutManager mManager;


    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new MultiTypeAdapter();
        mManager = new LinearLayoutManager(this);
        mMainBinding.setLayoutManager(mManager);
        mAdapter.addViewTypeToLayoutMap(0, R.layout.item_revenue_tag);
        mAdapter.addViewTypeToLayoutMap(1, R.layout.item_revenue);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null, false));
        mMainBinding.setAdapter(mAdapter);
        mAdapter.add(0, "一月");
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(0, "一月");
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(0, "一月");
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
        mAdapter.add(1, new Object());
    }

}
