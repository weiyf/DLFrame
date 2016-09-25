package cn.weiyf.dlframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import cn.weiyf.dleventbus.EventBus;
import cn.weiyf.dlframe.loading.LoadingDialogFragment;
import cn.weiyf.dlframe.loading.onDismissListener;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by weiyf on 2016/7/20.
 */

public abstract class BaseCompatFragment extends SupportFragment {

    protected View mRootView;


    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected LoadingDialogFragment mDialogFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogFragment = new LoadingDialogFragment();
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        initViews(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        hideSoftInput();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideSoftInput();
        }
    }

    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    public void showToast(String strings) {
        Toast.makeText(getActivity(), strings, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(String string) {
        Snackbar.make(mRootView, string, Snackbar.LENGTH_SHORT).show();

    }

    public void showSnackBar(String string, String action, View.OnClickListener clickListener) {
        Snackbar.make(mRootView, string, Snackbar.LENGTH_SHORT).setAction(action, clickListener).show();
    }

    public void showSnackBar(String string, Snackbar.Callback callback) {
        Snackbar.make(mRootView, string, Snackbar.LENGTH_SHORT).setCallback(callback).show();
    }

    public void showLoading() {
        mDialogFragment.show(getSupportFragmentManager(), "loading");
    }

    public void showLoading(onDismissListener onDismissListener) {
        mDialogFragment.show(getSupportFragmentManager(), "loading", onDismissListener);
    }

    public void dismissLoading() {
        mDialogFragment.dismiss();
    }


    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected boolean isBindEventBusHere() {
        return false;
    }

}
