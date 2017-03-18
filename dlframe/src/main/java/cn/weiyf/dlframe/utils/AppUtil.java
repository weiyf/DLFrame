package cn.weiyf.dlframe.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import cn.weiyf.dlframe.DLApplication;


public class AppUtil {

    private static String mDeviceType = "";
    private static String mPlatform = "";
    private static String mDeviceId = "";
    private static String mMacStr = "";
    private static String mVersionName = "";

    private static int mScreenWidth = 0;
    private static int mScreenHeight = 0;
    private static float mScreenDensity = 0.0f;

    private static void getScreenMsg() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) DLApplication.mInstance.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
    }

    public static int getScreenWidth() {
        if (mScreenWidth == 0) {
            getScreenMsg();
        }
        return mScreenWidth;
    }

    public static int getScreenHeight() {
        if (mScreenHeight == 0) {
            getScreenMsg();
        }
        return mScreenHeight;
    }

    public static float getScreenDensity() {
        if (mScreenDensity == 0) {
            getScreenMsg();
        }
        return mScreenDensity;
    }

    public static PackageInfo getPackageInfo() {
        PackageManager pm = getPackageManager();
        try {
            return pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getPackageName() {
        return DLApplication.mInstance.getPackageName();
    }

    private static PackageManager getPackageManager() {
        return DLApplication.mInstance.getPackageManager();
    }

    public static ApplicationInfo getApplicationInfo() {
        PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName() {

        if (!TextUtils.isEmpty(mVersionName)) {
            return mVersionName;
        }
        PackageInfo packageInfo = getPackageInfo();
        if (null != packageInfo) {
            mVersionName = packageInfo.versionName;
            return mVersionName;
        }
        return "";
    }

    public static int getVersionCode() {

        PackageInfo packageInfo = getPackageInfo();
        if (null != packageInfo) {
            return packageInfo.versionCode;
        }
        return 1;
    }


    public static String getDeviceType() {

        if (TextUtils.isEmpty(mDeviceType)) {
            getPhoneMes();
        }
        return TextUtils.isEmpty(mDeviceType) ? "" : mDeviceType;
    }


    public static String getPlatform() {
        if (TextUtils.isEmpty(mPlatform)) {
            getPhoneMes();
        }
        return TextUtils.isEmpty(mPlatform) ? "" : mPlatform;
    }

    public static String getDeviceId() {
        if (TextUtils.isEmpty(mDeviceId)) {
            getPhoneMes();
        }
        return TextUtils.isEmpty(mDeviceId) ? "" : mDeviceId;
    }

    public static void getPhoneMes() {
        try {
            TelephonyManager tm = (TelephonyManager) DLApplication.mInstance.getSystemService(Context.TELEPHONY_SERVICE);

            String type = android.os.Build.MODEL; // 手机型号
            String tyb = android.os.Build.BRAND;//手机品牌
            mDeviceType = tyb + "-" + type;

            String platform = android.os.Build.VERSION.RELEASE;//手机Android系统版本
            String display = Build.DISPLAY; //手机系统名称
            mPlatform = "Android版本：" + platform + " 系统名称：" + display;

            mDeviceId = tm.getDeviceId();//手机设备IME
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMac() {
        Enumeration<NetworkInterface> interfaces = null;
        if (TextUtils.isEmpty(mMacStr)) {
            try {
                interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iF = interfaces.nextElement();
                    byte[] address = iF.getHardwareAddress();
                    if (address == null || address.length == 0) {
                        continue;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : address) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    String mac = buf.toString();
                    if (!TextUtils.isEmpty(iF.getDisplayName()) && iF.getDisplayName().equals("wlan0")) {
                        mMacStr = mac;
                    }
                }
            } catch (SocketException e) {
                mMacStr = "";
                e.printStackTrace();
            }
        }
        return mMacStr;
    }

    public static int getStatusBarHeight() {
        Resources resources = DLApplication.mInstance.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getNavigationBarHeight() {
        Resources resources = DLApplication.mInstance.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
