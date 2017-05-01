package cn.weiyf.dlframe.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.weiyf.dlframe.base.BaseDBAdapter;
import cn.weiyf.dlframe.base.BindingViewHolder;
import cn.weiyf.dlframe.loadmore.LoadMoreView;


/**
 * Created by Administrator on 2017/1/11.
 */

public class SingleTypeAdapter<T> extends BaseDBAdapter<T> {


    protected int mLayoutRes;

    public SingleTypeAdapter(int layoutRes) {
        mDatas = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @Override
    protected BindingViewHolder createDBViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        getLayoutRes(), parent, false));
    }

    public void add(T object) {
        if (null != mDatas) {
            mDatas.add(object);
            notifyItemInserted(mDatas.size() + getHeaderLayoutCount());
            compatibilityDataSizeChanged(1);
        }
    }

    public void add(int position, T data) {
        mDatas.add(position, data);
        notifyItemInserted(position + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void addAll(List<? extends T> newData) {
        if (null != mDatas) {
            mDatas.addAll(newData);
            notifyItemRangeInserted(mDatas.size() - newData.size() + getHeaderLayoutCount(), newData.size());
            compatibilityDataSizeChanged(newData.size());
        }
    }

    @SafeVarargs
    public final void addAll(T... newDatas) {
        if (null != mDatas) {
            Collections.addAll(mDatas, newDatas);
            notifyItemRangeInserted(mDatas.size() - newDatas.length + getHeaderLayoutCount(), newDatas.length);
            compatibilityDataSizeChanged(newDatas.length);
        }
    }

    public void insert(int position, T object) {
        if (null != mDatas) {
            mDatas.add(position, object);
            notifyItemInserted(mDatas.size() + getHeaderLayoutCount());
            compatibilityDataSizeChanged(1);
        }
    }

    public void insert(int position, List<T> datas) {
        if (null != mDatas) {
            mDatas.addAll(position, datas);
            notifyItemRangeInserted(position + getHeaderLayoutCount(), datas.size());
            compatibilityDataSizeChanged(datas.size());
        }
    }

    public void update(List<T> mDatas) {
        this.mDatas = mDatas == null ? Collections.EMPTY_LIST : mDatas;
        if (mRequestLoadMoreListener != null) {
            mNextLoadEnable = true;
            mLoadMoreEnable = true;
            mLoading = false;
            mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        }
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    public void update(int position, T data) {
        mDatas.set(position, data);
        notifyItemChanged(position + getHeaderLayoutCount());
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }
}
