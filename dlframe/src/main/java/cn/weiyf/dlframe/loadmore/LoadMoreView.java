package cn.weiyf.dlframe.loadmore;


import cn.weiyf.dlframe.BR;
import cn.weiyf.dlframe.base.BindingViewHolder;


/**
 * Created by Administrator on 2017/1/10.
 */

public class LoadMoreView {
    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public void convert(BindingViewHolder holder) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                holder.getBinding().setVariable(BR.load_more_type, 0);
                break;
            case STATUS_FAIL:
                holder.getBinding().setVariable(BR.load_more_type, 1);
                break;
            case STATUS_END:
                holder.getBinding().setVariable(BR.load_more_type, 2);
                break;
            case STATUS_DEFAULT:
                holder.getBinding().setVariable(BR.load_more_type, 3);
                break;
        }
    }

    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone() {
        return mLoadMoreEndGone;
    }


}
