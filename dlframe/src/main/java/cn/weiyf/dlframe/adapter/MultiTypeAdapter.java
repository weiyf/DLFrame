package cn.weiyf.dlframe.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.weiyf.dlframe.base.BaseAdapter;

/**
 * Created by weiyf on 2016/9/26.
 */

public class MultiTypeAdapter<T> extends BaseAdapter<T> {


    public interface MultiViewTyper {
        int getViewType(Object item);
    }

    protected ArrayList<Integer> mCollectionViewType;

    private HashMap<Integer, Integer> mItemTypeToLayoutMap = new HashMap<>();


    public MultiTypeAdapter(Map<Integer, Integer> viewTypeToLayoutMap) {
        mDatas = new ArrayList<>();
        mCollectionViewType = new ArrayList<>();
        if (viewTypeToLayoutMap != null && !viewTypeToLayoutMap.isEmpty()) {
            mItemTypeToLayoutMap.putAll(viewTypeToLayoutMap);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
    }

    public void addViewTypeToLayoutMap(Integer viewType, Integer layoutRes) {
        mItemTypeToLayoutMap.put(viewType, layoutRes);
    }

    @Override
    public int getItemViewType(int position) {
        return mCollectionViewType.get(position);
    }

    public void add(T object,int viewType) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(object);
                mCollectionViewType.add(viewType);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(Collection<? extends T> collection, int viewType) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.addAll(collection);
                for (int i = 0; i < collection.size(); ++i) {
                    mCollectionViewType.add(viewType);
                }
            }
        }
        if (getItemCount() - collection.size() != 0) {
            notifyItemRangeInserted(getItemCount() - collection.size(), collection.size());
        } else {
            notifyDataSetChanged();
        }
    }

    @SafeVarargs
    public final void addAll(int viewType, T... items) {
        synchronized (mLock) {
            if (null != mDatas) {
                Collections.addAll(mDatas, items);
            }
        }
        notifyItemRangeInserted(getItemCount() - items.length, getItemCount() - 1);
    }

    public void insert(T object, int index, int viewType) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(index, object);
            }
        }
        notifyItemInserted(index);
    }

    public void remove(int index, int viewType) {
        if (index > 0 && index < getItemCount()) {
            synchronized (mLock) {
                mDatas.remove(index);
            }
            notifyItemRemoved(index);
        } else {
            throw new IllegalArgumentException("index less than zero or index more than list's size");
        }
    }


    public void remove(T object, int viewType) {
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


    public void clear() {
        mCollectionViewType.clear();
        super.clear();
    }

    @LayoutRes
    protected int getLayoutRes(int viewType) {
        return mItemTypeToLayoutMap.get(viewType);
    }
}
