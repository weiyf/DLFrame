package cn.weiyf.dlframe.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(), parent, false));
    }

    public void add(T object) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(object);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(Collection<? extends T> collection) {
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
        notifyItemRangeInserted(getItemCount() - items.length, getItemCount() - 1);
    }

    public void insert(T object, int index) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(index, object);
            }
        }
        notifyItemInserted(index);
    }

    public void remove(int index) {
        if (index > 0 && index < getItemCount()) {
            synchronized (mLock) {
                mDatas.remove(index);
            }
            notifyItemRemoved(index);
        } else {
            throw new IllegalArgumentException("index less than zero or index more than list's size");
        }
    }


    public void remove(T object) {
        removeIndex = -1;
        removeSuccess = false;
        synchronized (mLock) {
            for (int index = 0; index < getItemCount(); index++) {
                if (object.equals(getItem(index))) {
                    removeIndex = index;
                }
            }
            if (mDatas != null) {
                removeSuccess = mDatas.remove(object);
            }
        }
        if (removeSuccess) {
            notifyItemRemoved(removeIndex);
        }
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }

}
