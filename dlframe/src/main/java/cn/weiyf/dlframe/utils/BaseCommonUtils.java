package cn.weiyf.dlframe.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import java.security.MessageDigest;

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


    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static void copyToClipboard(String info, Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("msg", info);
        manager.setPrimaryClip(clipData);
        showToast(String.format("[%s] 已经复制到剪切板啦✧", info));
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
