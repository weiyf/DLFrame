package cn.weiyf.dlframe.listener;


import cn.weiyf.dlframe.base.BaseDBAdapter;

/**
 * Created by Administrator on 2017/1/17.
 */

public interface OnItemLongClickListener<T> extends BaseDBAdapter.Presenter {

    boolean onItemLongClick(int position, T t);
}
