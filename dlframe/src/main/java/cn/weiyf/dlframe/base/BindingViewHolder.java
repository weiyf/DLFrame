package cn.weiyf.dlframe.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public final class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final T mBinding;

    public BindingViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getBinding() {
        return mBinding;
    }
}