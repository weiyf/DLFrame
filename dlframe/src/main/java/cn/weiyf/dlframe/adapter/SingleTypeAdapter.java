package cn.weiyf.dlframe.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.weiyf.dlframe.base.BaseAdapter;

/**
 * Created by weiyf on 2016/9/26.
 */

public class SingleTypeAdapter<T> extends BaseAdapter<T> {

    protected int mLayoutRes;

    public interface Presenter<T> extends BaseAdapter.Presenter {
        void onItemClick(T t);
    }

    public SingleTypeAdapter(int layoutRes) {
        mDatas = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        getLayoutRes(), parent, false));
    }

    public void add(T object) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(object);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<? extends T> collection) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.addAll(collection);
            }
        }
        if (getItemCount() - collection.size() != 0) {
            notifyItemRangeInserted(getItemCount() - collection.size(), collection.size());
        } else {
            notifyDataSetChanged();
        }
    }

    @SafeVarargs
    public final void addAll(T... items) {
        synchronized (mLock) {
            if (null != mDatas) {
                Collections.addAll(mDatas, items);
            }
        }
        if (getItemCount() - items.length != 0) {
            notifyItemRangeInserted(getItemCount() - items.length, items.length);
        } else {
            notifyDataSetChanged();
        }
    }

    public void insert(int index, T object) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(index, object);
            }
        }
        notifyItemInserted(index);
    }

    public void update(List<T> mDatas) {
        synchronized (mLock) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }

}
