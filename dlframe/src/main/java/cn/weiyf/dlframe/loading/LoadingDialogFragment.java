package cn.weiyf.dlframe.loading;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import cn.weiyf.dlframe.R;

/**
 * Created by Administrator on 2016/9/22.
 */

public class LoadingDialogFragment extends DialogFragment {

    DialogInterface.OnDismissListener mOnDismissListener;

    private DialogInterface.OnCancelListener mOnCancelListener;

    @Override
    public void show(FragmentManager manager, String tag) {
        if (this.isAdded() || isVisible()) {
            return;
        }
        super.show(manager, tag);
    }



    public void show(FragmentManager manager, String tag, DialogInterface.OnDismissListener dismissListener) {
        this.mOnDismissListener = dismissListener;
        show(manager, tag);
    }

    public void show(FragmentManager manager, String tag, DialogInterface.OnCancelListener cancelListener) {
        this.mOnCancelListener = cancelListener;
        show(manager, tag);
    }


    public void show(FragmentManager manager, String tag, DialogInterface.OnDismissListener dismissListener, DialogInterface.OnCancelListener cancelListener) {
        this.mOnDismissListener = dismissListener;
        this.mOnCancelListener = cancelListener;
        show(manager, tag);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(dialog);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.dl_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_loading);
        return dialog;
    }


}
