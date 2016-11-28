package cn.weiyf.dlframe.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.weiyf.dlframe.base.BaseAdapter;

/**
 * Created by weiyf on 2016/9/26.
 */

public class MultiTypeAdapter extends BaseAdapter<Object> {

    protected ArrayList<Integer> mDataViewType;

    private ArrayMap<Integer, Integer> mItemTypeToLayoutMap = new ArrayMap<>();

    public MultiTypeAdapter() {
        this(null);
    }


    public MultiTypeAdapter(Map<Integer, Integer> viewTypeToLayoutMap) {
        mDatas = new ArrayList<>();
        mDataViewType = new ArrayList<>();
        if (viewTypeToLayoutMap != null && !viewTypeToLayoutMap.isEmpty()) {
            mItemTypeToLayoutMap.putAll(viewTypeToLayoutMap);
        }
    }


    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutRes(viewType), null, false));
    }

    public void addViewTypeToLayoutMap(Integer viewType, @LayoutRes Integer layoutRes) {
        mItemTypeToLayoutMap.put(viewType, layoutRes);
    }


    public void add(int viewType, Object item) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(item);
                mDataViewType.add(viewType);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(int viewType, Collection<?> collection) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.addAll(collection);
                for (int i = 0; i < collection.size(); ++i) {
                    mDataViewType.add(viewType);
                }
            }
        }
        if (getItemCount() - collection.size() != 0) {
            notifyItemRangeInserted(getItemCount() - collection.size(), collection.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public final void addAll(int viewType, Object... items) {
        synchronized (mLock) {
            if (null != mDatas) {
                Collections.addAll(mDatas, items);
                for (int i = 0; i < items.length; ++i) {
                    mDataViewType.add(viewType);
                }
            }
        }
        if (getItemCount() - items.length != 0) {
            notifyItemRangeInserted(getItemCount() - items.length, items.length);
        } else {
            notifyDataSetChanged();
        }
    }

    public void addAll(MultiViewType multiViewType, List<?> items) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.addAll(items);
                for (Object item : items) {
                    mDataViewType.add(multiViewType.getViewType(item));
                }
            }
        }
        if (getItemCount() - items.size() != 0) {
            notifyItemRangeInserted(getItemCount() - items.size(), items.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public final void addAll(MultiViewType multiViewType, Object... items) {
        synchronized (mLock) {
            if (null != mDatas) {
                Collections.addAll(mDatas, items);
                for (Object item : items) {
                    mDataViewType.add(multiViewType.getViewType(item));
                }
            }
        }
        if (getItemCount() - items.length != 0) {
            notifyItemRangeInserted(getItemCount() - items.length, items.length);
        } else {
            notifyDataSetChanged();
        }
    }

    public void insert(int position, Object item, int viewType) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(position, item);
                mDataViewType.add(position, viewType);
            }
        }
        notifyItemInserted(position);
    }

    public void insert(int position, List<?> objects, int viewType) {
        mDatas.addAll(position, objects);
        for (int i = 0; i < objects.size(); i++) {
            mDataViewType.add(position + 1, viewType);
        }
        notifyItemRangeChanged(position, objects.size() - position);
    }


    public void update(MultiViewType multiViewType, List<Object> mDatas) {
        synchronized (mLock) {
            if (null != mDatas) {
                this.mDatas = mDatas;
                mDataViewType.clear();
                for (Object item : mDatas) {
                    mDataViewType.add(multiViewType.getViewType(item));
                }

            }
        }
        notifyDataSetChanged();
    }


    @Override
    public void remove(int position) {
        mDataViewType.remove(position);
        super.remove(position);
    }

    @Override
    public void clear() {
        mDataViewType.clear();
        super.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataViewType.get(position);
    }

    @LayoutRes
    protected int getLayoutRes(int viewType) {
        if (mItemTypeToLayoutMap.containsKey(viewType)) {
            return mItemTypeToLayoutMap.get(viewType);
        } else {
            return mItemTypeToLayoutMap.valueAt(0);
        }
    }


    public interface MultiViewType {
        int getViewType(Object item);
    }
}
