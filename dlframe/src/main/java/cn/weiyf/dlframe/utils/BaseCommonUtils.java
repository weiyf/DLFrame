package cn.weiyf.dlframe.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.weiyf.dlframe.DLApplication;


/**
 * Created by weiyf on 16-11-23.
 */
public class BaseCommonUtils {

    private static NetworkInfo getNetworkInfo() {

        ConnectivityManager cm = (ConnectivityManager) DLApplication.mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isNetworkisAvailable() {
        NetworkInfo mNetworkInfo = getNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    public static boolean isWifi() {
        NetworkInfo info = getNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
        }
        return false;
    }

    public static boolean isMobile() {
        NetworkInfo info = getNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    public static boolean checkSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(str.getBytes());
            BigInteger bigInt = new BigInteger(1, thedigest);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void copyToClipboard(String info, Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("msg", info);
        manager.setPrimaryClip(clipData);
        showToast(String.format("[%s] 已经复制到剪切板啦✧", info));
    }

    public static String getSign() {
        try {
            PackageManager pm = DLApplication.mInstance.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(DLApplication.mInstance.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = pi.signatures;
            Signature signature0 = signatures[0];
            return signature0.toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int dp2px(float dp) {
        float scale = getDensityDpi();
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        float scale = getDensityDpi();
        return (int) (px / scale + 0.5f);
    }

    private static int getDensityDpi() {
        return DLApplication.mInstance.getResources().getDisplayMetrics().densityDpi;
    }


    public static int sp2px(float sp) {
        float scale = getDensityDpi();
        return (int) (sp * scale + 0.5f);
    }

    public static int px2sp(float px) {
        float scale = getDensityDpi();
        return (int) (px / scale + 0.5f);
    }


    public static void showToast(String msg) {
        if (DLApplication.mInstance != null) {
            Toast.makeText(DLApplication.mInstance.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
