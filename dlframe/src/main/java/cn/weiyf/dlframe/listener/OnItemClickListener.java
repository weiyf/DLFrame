package cn.weiyf.dlframe.listener;


import cn.weiyf.dlframe.base.BaseDBAdapter;

public interface OnItemClickListener<T> extends BaseDBAdapter.Presenter {
    void onItemClick(int position, T t);
}