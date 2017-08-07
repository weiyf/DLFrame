package cn.weiyf.dlframe.listener;

/**
 * Created by Administrator on 2017/3/16.
 */

public interface OnItemChildLongClickListener<D, V> {

    boolean onItemChildLongClick(int position, D date, V view);
}
