package cn.weiyf.dlframe.net;


import android.content.DialogInterface;
import android.net.ParseException;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.weiyf.dlframe.DLFrame;
import cn.weiyf.dlframe.loading.LoadingDialogFragment;
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
                onError(new DLException("-1", "用户主动取消请求"));
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
        if (DLFrame.getInstance().isDebug()) {
            Logger.e(e.getMessage());
        }
        if (e instanceof HttpException) {
            Toast.makeText(DLFrame.getInstance().getContext(), "网络连接错误，请稍后再试！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof DLException) {
            handlerError((DLException) e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            Toast.makeText(DLFrame.getInstance().getContext(), "数据解析错误，请联系管理员！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof UnknownHostException) {
            Toast.makeText(DLFrame.getInstance().getContext(), "服务器错误，请稍后再试！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException) {
            Toast.makeText(DLFrame.getInstance().getContext(), "连接服务器超时，请稍后再试！", Toast.LENGTH_SHORT).show();
        } else {
            e.printStackTrace();
            Toast.makeText(DLFrame.getInstance().getContext(), "系统错误，请稍后再试！", Toast.LENGTH_SHORT).show();
        }
        _onError(new DLException("-1", e.getMessage()));
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
