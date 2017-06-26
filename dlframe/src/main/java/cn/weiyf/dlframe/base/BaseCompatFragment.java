package cn.weiyf.dlframe.base;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import cn.weiyf.dlframe.DLFrame;
import cn.weiyf.dlframe.loading.LoadingDialogFragment;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * Created by weiyf on 2016/7/20.
 */
public abstract class BaseCompatFragment extends SupportFragment implements LifecycleProvider<FragmentEvent> {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    public BaseCompatActivity mActivity;
    protected LoadingDialogFragment mDialogFragment;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        mDialogFragment = new LoadingDialogFragment();
        if (isSwipeBackEnable()) {
            onFragmentCreate();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        initViews(savedInstanceState);
    }

    protected View attachToSwipeBack(View view) {
        mSwipeBackLayout.attachToFragment(this, view);
        return mSwipeBackLayout;
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
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

    public void setParallaxOffset(@FloatRange(from = 0.0f, to = 1.0f) float offset) {
        mSwipeBackLayout.setParallaxOffset(offset);
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

    public void showNormal(String normal) {
        Toasty.normal(DLFrame.getInstance().getContext(), normal).show();
    }

    public void showInfo(String info) {
        Toasty.info(DLFrame.getInstance().getContext(), info).show();
    }

    public void showSuccess(String success) {
        Toasty.success(DLFrame.getInstance().getContext(), success).show();
    }

    public void showError(String error) {
        Toasty.error(DLFrame.getInstance().getContext(), error).show();
    }

    public void showWarning(String warning) {
        Toasty.warning(DLFrame.getInstance().getContext(), warning).show();
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

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
        mActivity = (BaseCompatActivity) activity;
    }


    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }


    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
        mActivity = null;
    }

}
