package cn.weiyf.dlframe.base;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

/**
 * Created by weiyf on 2016/9/27.
 */

public class BaseDataBindingAdapter {

    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView recyclerView, BaseDBAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

}
