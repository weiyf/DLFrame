package cn.weiyf.dlframe.listener;

import android.view.View;

/**
 * Created by Administrator on 2017/3/16.
 */

public interface OnItemChildLongClickListener<T> {

    boolean onItemChildLongClick(int position, T t, View view);
}
