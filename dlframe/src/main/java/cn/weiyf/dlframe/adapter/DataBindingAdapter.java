package cn.weiyf.dlframe.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import cn.weiyf.dlframe.base.BaseAdapter;

/**
 * Created by weiyf on 2016/9/27.
 */

public class DataBindingAdapter {

    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView recyclerView, BaseAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }
}
