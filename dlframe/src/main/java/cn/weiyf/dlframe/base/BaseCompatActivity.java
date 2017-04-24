package cn.weiyf.dlframe.base;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import cn.weiyf.dlframe.R;
import cn.weiyf.dlframe.loading.LoadingDialogFragment;
import cn.weiyf.dlframe.utils.BaseCommonUtils;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static cn.weiyf.dlframe.base.BaseCompatActivity.TransitionMode.NONE;

/**
 * Created by weiyf on 2016/7/20.
 */

public abstract class BaseCompatActivity extends SupportActivity implements LifecycleProvider<ActivityEvent> {

    protected LoadingDialogFragment mDialogFragment;


    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        RxBus.get().register(this);
        if (getOverridePendingTransitionMode() != NONE) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mDialogFragment = new LoadingDialogFragment();
        BaseAppManager.getInstance().addActivity(this);
        initViews(savedInstanceState);
        if (isSwipeBackEnable()) {
            onActivityCreate();
        }
    }


    private void onActivityCreate() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isSwipeBackEnable()) {
            mSwipeBackLayout.attachToActivity(this);
        }
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return view;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先;  false: Activity不允许滑动退出
     */
    public boolean swipeBackPriority() {
        return getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (getOverridePendingTransitionMode() != NONE) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void showToast(String str) {
        BaseCommonUtils.showToast(str);
    }

    public Snackbar showSnackBar(View contentView, String string) {
        Snackbar snackbar = Snackbar.make(contentView, string, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showSnackBar(View contentView, String string, String action, View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(contentView, string, Snackbar.LENGTH_INDEFINITE).setAction(action, clickListener);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showSnackBar(View contentView, String string, String action, View.OnClickListener clickListener, Snackbar.Callback callback) {
        Snackbar snackbar = Snackbar.make(contentView, string, Snackbar.LENGTH_INDEFINITE)
                .setAction(action, clickListener).setCallback(callback);
        snackbar.show();
        return snackbar;
    }

    public Snackbar showSnackBar(View contentView, String string, Snackbar.Callback callback) {
        Snackbar snackbar = Snackbar.make(contentView, string, Snackbar.LENGTH_SHORT).setCallback(callback);
        snackbar.show();
        return snackbar;
    }

    public TransitionMode getOverridePendingTransitionMode() {
        return NONE;
    }

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected boolean isSwipeBackEnable() {
        return false;
    }


    protected enum TransitionMode {
        NONE, LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }


    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
