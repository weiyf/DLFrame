package cn.weiyf.dlframe.listener;


public interface OnItemClickListener<D, V> {

    void onItemClick(int position, D date, V view);

}