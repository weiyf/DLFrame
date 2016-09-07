package cn.weiyf.dlframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cn.weiyf.dleventbus.EventBus;
import cn.weiyf.dlframe.R;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by weiyf on 2016/7/20.
 */

public abstract class BaseCompatActivity extends SupportActivity {


    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;


    private boolean mIsOverridePendingTransition;

    private TransitionMode mOverridePendingTransitionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (mIsOverridePendingTransition) {
            switch (mOverridePendingTransitionMode) {
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
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        BaseAppManager.getInstance().addActivity(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        initViews(savedInstanceState);
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (mIsOverridePendingTransition) {
            switch (mOverridePendingTransitionMode) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View contentView, String string) {
        Snackbar.make(contentView, string, Snackbar.LENGTH_SHORT).show();

    }

    public void showSnackBar(View contentView, String string, String action, View.OnClickListener clickListener) {
        Snackbar.make(contentView, string, Snackbar.LENGTH_SHORT).setAction(action, clickListener).show();
    }

    public void showSnackBar(View contentView, String string, Snackbar.Callback callback) {
        Snackbar.make(contentView, string, Snackbar.LENGTH_SHORT).setCallback(callback).show();
    }

    public boolean isOverridePendingTransition() {
        return mIsOverridePendingTransition;
    }

    public void setOverridePendingTransition(boolean overridePendingTransition) {
        mIsOverridePendingTransition = overridePendingTransition;
    }

    public TransitionMode getOverridePendingTransitionMode() {
        return mOverridePendingTransitionMode;
    }

    public void setOverridePendingTransitionMode(TransitionMode overridePendingTransitionMode) {
        this.mOverridePendingTransitionMode = overridePendingTransitionMode;
    }

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected boolean isBindEventBusHere() {
        return false;
    }

    protected enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }
}
