package cn.weiyf.dlframe.listener;


import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(int position, T t, View view);

}