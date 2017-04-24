package cn.weiyf.dlframe.net;


import android.content.DialogInterface;
import android.net.ParseException;
import android.support.v4.app.FragmentManager;

import com.google.gson.JsonParseException;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.weiyf.dlframe.DLApplication;
import cn.weiyf.dlframe.loading.LoadingDialogFragment;
import cn.weiyf.dlframe.utils.BaseCommonUtils;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class DLObserver<T> extends DisposableObserver<T> {


    private FragmentManager mFragmentManager;
    private LoadingDialogFragment mLoadingDialogFragment;
    private boolean mIsShowLoading = true;

    public DLObserver(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public DLObserver(FragmentManager fragmentManager, boolean isShowLoading) {
        mIsShowLoading = isShowLoading;
        mFragmentManager = fragmentManager;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mIsShowLoading) {
            showLoading();
        }
    }

    private void showLoading() {
        if (mLoadingDialogFragment == null) {
            mLoadingDialogFragment = new LoadingDialogFragment();
        }
        mLoadingDialogFragment.show(mFragmentManager, "LoadingFragment", new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dispose();
                onError(new DLException(-1, "用户主动取消请求"));
            }
        });
    }

    private void dismissLoading() {
        if (mLoadingDialogFragment != null) {
            if (mLoadingDialogFragment.isAdded()) {
                mLoadingDialogFragment.dismiss();
            }
        }
    }

    @Override
    public void onComplete() {
        if (mIsShowLoading) {
            dismissLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mIsShowLoading) {
            dismissLoading();
        }
        if (DLApplication.mInstance.isDebug()) {
            Logger.e(e.getMessage());
        }
        if (e instanceof HttpException) {
            BaseCommonUtils.showToast("网络连接错误，请稍后再试！");
        } else if (e instanceof DLException) {
            handlerError((DLException) e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            BaseCommonUtils.showToast("数据解析错误，请联系管理员！");
        } else if (e instanceof UnknownHostException) {
            BaseCommonUtils.showToast("服务器错误，请稍后再试！");
        } else if (e instanceof SocketTimeoutException) {
            BaseCommonUtils.showToast("连接服务器超时，请稍后再试！");
        } else {
            e.printStackTrace();
            BaseCommonUtils.showToast("系统错误，请稍后再试！");
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public void handlerError(DLException exception) {
        RxBus.get().post("handlerError", exception);
        _onError(exception);
    }


    public abstract void _onNext(T t);

    public void _onError(DLException e) {
    }
}
