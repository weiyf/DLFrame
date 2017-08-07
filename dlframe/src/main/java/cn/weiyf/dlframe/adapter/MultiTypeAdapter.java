package cn.weiyf.dlframe.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.weiyf.dlframe.base.BaseDBAdapter;
import cn.weiyf.dlframe.base.BindingViewHolder;
import cn.weiyf.dlframe.loadmore.LoadMoreView;


/**
 * Created by weiyf on 2016/9/26.
 */

public class MultiTypeAdapter extends BaseDBAdapter<Object> {

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
    protected BindingViewHolder createDBViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutRes(viewType), parent, false));
    }

    public void addViewTypeToLayoutMap(Integer viewType, @LayoutRes Integer layoutRes) {
        mItemTypeToLayoutMap.put(viewType, layoutRes);
    }


    public void add(int viewType, Object item) {
        if (null != mDatas) {
            mDatas.add(item);
            mDataViewType.add(viewType);
            notifyItemInserted(mDatas.size() + getHeaderLayoutCount());
        }
    }

    public void add(int position, int viewType, Object data) {
        mDatas.add(position, data);
        mDataViewType.add(viewType);
        notifyItemInserted(position + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void addAll(int viewType, List<?> newData) {
        if (null != mDatas) {
            mDatas.addAll(newData);
            for (int i = 0; i < newData.size(); ++i) {
                mDataViewType.add(viewType);
            }
            notifyItemRangeInserted(mDatas.size() - newData.size() + getHeaderLayoutCount(), newData.size());
            compatibilityDataSizeChanged(newData.size());
        }
    }

    public final void addAll(int viewType, Object... newDatas) {
        if (null != mDatas) {
            Collections.addAll(mDatas, newDatas);
            for (int i = 0; i < newDatas.length; ++i) {
                mDataViewType.add(viewType);
            }
            notifyItemRangeInserted(mDatas.size() - newDatas.length + getHeaderLayoutCount(), newDatas.length);
            compatibilityDataSizeChanged(newDatas.length);
        }
    }

    public void addAll(MultiViewType multiViewType, List<?> newDatas) {
        if (null != mDatas) {
            mDatas.addAll(newDatas);
            for (Object item : newDatas) {
                mDataViewType.add(multiViewType.getViewType(item));
            }
            notifyItemRangeInserted(mDatas.size() - newDatas.size() + getHeaderLayoutCount(), newDatas.size());
            compatibilityDataSizeChanged(newDatas.size());
        }
    }

    public final void addAll(MultiViewType multiViewType, Object... newDatas) {
        if (null != mDatas) {
            Collections.addAll(mDatas, newDatas);
            for (Object item : newDatas) {
                mDataViewType.add(multiViewType.getViewType(item));
            }
            notifyItemRangeInserted(mDatas.size() - newDatas.length + getHeaderLayoutCount(), newDatas.length);
            compatibilityDataSizeChanged(newDatas.length);
        }
    }

    public void insert(int position, Object item, int viewType) {
        if (null != mDatas) {
            mDatas.add(position, item);
            mDataViewType.add(position, viewType);
            notifyItemInserted(mDatas.size() + getHeaderLayoutCount());
            compatibilityDataSizeChanged(1);
        }
    }

    public void insert(int position, List<?> datas, int viewType) {
        mDatas.addAll(position, datas);
        for (int i = 0; i < datas.size(); i++) {
            mDataViewType.add(position + 1, viewType);
        }
        notifyItemRangeInserted(position + getHeaderLayoutCount(), datas.size());
        compatibilityDataSizeChanged(datas.size());
    }


    public void update(MultiViewType multiViewType, List<Object> mDatas) {
        this.mDatas = mDatas == null ? Collections.EMPTY_LIST : mDatas;
        mDataViewType.clear();
        for (Object item : mDatas) {
            mDataViewType.add(multiViewType.getViewType(item));
        }
        if (mRequestLoadMoreListener != null) {
            mNextLoadEnable = true;
            mLoadMoreEnable = true;
            mLoading = false;
            mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        }
        mLastPosition = -1;
        notifyDataSetChanged();
    }


    @Override
    protected int getDefItemViewType(int position) {
        return mDataViewType.get(position);
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
