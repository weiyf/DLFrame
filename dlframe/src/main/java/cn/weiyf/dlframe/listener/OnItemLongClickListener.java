package cn.weiyf.dlframe.listener;


/**
 * Created by Administrator on 2017/1/17.
 */

public interface OnItemLongClickListener<D, V> {

    boolean onItemLongClick(int position, D data, V view);

}
