package cn.weiyf.dlframe.listener;


import android.view.View;

/**
 * Created by Administrator on 2017/1/17.
 */

public interface OnItemLongClickListener<T>  {

    boolean onItemLongClick(int position, T t, View view);
}
