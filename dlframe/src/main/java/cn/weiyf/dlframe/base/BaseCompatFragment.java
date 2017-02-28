package cn.weiyf.dlframe.base;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import cn.weiyf.dlframe.loading.LoadingDialogFragment;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * Created by weiyf on 2016/7/20.
 */
public abstract class BaseCompatFragment extends SupportFragment {

    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected LoadingDialogFragment mDialogFragment;

    private SwipeBackLayout mSwipeBackLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogFragment = new LoadingDialogFragment();
        if (isSwipeBackEnable()) {
            onFragmentCreate();
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

    protected View attachToSwipeBack(View view) {
        mSwipeBackLayout.attachToFragment(this, view);
        return mSwipeBackLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftInput();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mSwipeBackLayout != null) {
                mSwipeBackLayout.hiddenFragment();
            }
            hideSoftInput();
        }
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    @Override
    protected void initFragmentBackground(View view) {
        if (view instanceof SwipeBackLayout) {
            View childView = ((SwipeBackLayout) view).getChildAt(0);
            setBackground(childView);
        } else {
            setBackground(view);
        }
    }

    private void onFragmentCreate() {
        mSwipeBackLayout = new SwipeBackLayout(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
        mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    public void showToast(String strings) {
        Toast.makeText(_mActivity.getApplicationContext(), strings, Toast.LENGTH_SHORT).show();
    }

    public Snackbar showSnackBar(String string) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar.make(getView(), string, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return snackbar;
        } else {
            Logger.e("getView() return null or it has no layout");
            return null;
        }
    }

    public Snackbar showSnackBar(String string, String action, View.OnClickListener clickListener) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar.make(getView(), string, Snackbar.LENGTH_INDEFINITE).setAction(action, clickListener);
            snackbar.show();
            return snackbar;
        } else {
            Logger.e("getView() return null or it has no layout");
            return null;
        }
    }

    public Snackbar showSnackBar(String string, String action, View.OnClickListener clickListener, Snackbar.Callback callback) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar.make(getView(), string, Snackbar.LENGTH_INDEFINITE)
                    .setAction(action, clickListener).setCallback(callback);
            snackbar.show();
            return snackbar;
        } else {
            Logger.e("getView() return null or it has no layout");
            return null;
        }
    }

    public Snackbar showSnackBar(String string, Snackbar.Callback callback) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar.make(getView(), string, Snackbar.LENGTH_SHORT).setCallback(callback);
            snackbar.show();
            return snackbar;
        } else {
            Logger.e("getView() return null or it has no layout");
            return null;
        }
    }

    public void showLoading() {
        mDialogFragment.show(getSupportFragmentManager(), "loading");
    }

    public void showLoading(DialogInterface.OnDismissListener onDismissListener) {
        mDialogFragment.show(getSupportFragmentManager(), "loading", onDismissListener);
    }

    public void showLoading(DialogInterface.OnCancelListener onCancelListener) {
        mDialogFragment.show(getSupportFragmentManager(), "loading", onCancelListener);
    }

    public void showLoading(DialogInterface.OnDismissListener onDismissListener, DialogInterface.OnCancelListener onCancelListener) {
        mDialogFragment.show(getSupportFragmentManager(), "loading", onDismissListener, onCancelListener);
    }

    public void dismissLoading() {
        mDialogFragment.dismiss();
    }


    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected boolean isSwipeBackEnable() {
        return false;
    }

}
