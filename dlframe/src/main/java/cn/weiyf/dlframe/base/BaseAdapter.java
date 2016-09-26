package cn.weiyf.dlframe.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.weiyf.dlframe.BR;
import cn.weiyf.dlframe.adapter.BindingViewHolder;

/**
 * Created by weiyf on 16-7-28.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {

    protected LayoutInflater mLayoutInflater;
    protected final Object mLock = new Object();
    protected List<T> mDatas;
    protected Presenter mPresenter;
    protected Decorator mDecorator;


    protected int removeIndex = -1;
    protected boolean removeSuccess;


    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return null;
    }

    @Override
    public final void onBindViewHolder(BindingViewHolder holder, int position) {
        final Object item = mDatas.get(position);
        holder.getBinding().setVariable(BR.item, item);
        holder.getBinding().setVariable(BR.presenter, getPresenter());
        holder.getBinding().executePendingBindings();
        if (mDecorator != null) {
            mDecorator.decorator(holder, position, getItemViewType(position));
        }
    }



    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() != 0) {
            return mDatas.size();
        } else {
            return 0;
        }
    }


//    public void add(T object) {
//        synchronized (mLock) {
//            if (null != mDatas) {
//                mDatas.add(object);
//            }
//        }
//        notifyItemInserted(getItemCount() - 1);
//    }
//
//    public void addAll(Collection<? extends T> collection) {
//        synchronized (mLock) {
//            if (null != mDatas) {
//                mDatas.addAll(collection);
//            }
//        }
//        if (getItemCount() - collection.size() != 0) {
//            notifyItemRangeInserted(getItemCount() - collection.size(), collection.size());
//        } else {
//            notifyDataSetChanged();
//        }
//    }
//
//    @SafeVarargs
//    public final void addAll(T... items) {
//        synchronized (mLock) {
//            if (null != mDatas) {
//                Collections.addAll(mDatas, items);
//            }
//        }
//        notifyItemRangeInserted(getItemCount() - items.length, getItemCount() - 1);
//    }
//
//    public void insert(T object, int index) {
//        synchronized (mLock) {
//            if (null != mDatas) {
//                mDatas.add(index, object);
//            }
//        }
//        notifyItemInserted(index);
//    }
//
//    public void remove(int index) {
//        if (index > 0 && index < getItemCount()) {
//            synchronized (mLock) {
//                mDatas.remove(index);
//            }
//            notifyItemRemoved(index);
//        } else {
//            throw new IllegalArgumentException("index less than zero or index more than list's size");
//        }
//    }
//
//
//    public void remove(T object) {
//        removeIndex = -1;
//        removeSuccess = false;
//        synchronized (mLock) {
//            for (int index = 0; index < getItemCount(); index++) {
//                if (object.equals(getItem(index))) {
//                    removeIndex = index;
//                }
//            }
//            if (mDatas != null) {
//                removeSuccess = mDatas.remove(object);
//            }
//        }
//        if (removeSuccess) {
//            notifyItemRemoved(removeIndex);
//        }
//    }

    public void clear() {
        synchronized (mLock) {
            if (mDatas != null) {
                mDatas.clear();
            }
        }
        notifyDataSetChanged();
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mDatas != null) {
                Collections.sort(mDatas, comparator);
            }
        }
        notifyDataSetChanged();
    }

    public void update(List<T> mDatas) {
        synchronized (mLock) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public int getPosition(T item) {
        return mDatas.indexOf(item);
    }

    public List<T> getData() {
        if (null != mDatas) {
            return mDatas;
        } else {
            //noinspection unchecked
            return Collections.EMPTY_LIST;
        }
    }



    public interface Presenter {

    }

    public interface Decorator {
        void decorator(BindingViewHolder holder, int position, int viewType);
    }

    public void setDecorator(Decorator decorator) {
        mDecorator = decorator;
    }

    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    protected Presenter getPresenter() {
        return mPresenter;
    }

}
