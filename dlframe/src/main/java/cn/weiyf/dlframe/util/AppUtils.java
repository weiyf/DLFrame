package cn.weiyf.dlframe.util;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import cn.weiyf.dlframe.DLFrame;

/**
 * Created by Administrator on 2017/4/26.
 */
public class AppUtils {
    private static Context sContext = DLFrame.getInstance().getContext();

    private AppUtils() {
        throw new UnsupportedOperationException("别想调用我！");
    }

    /**
     * Read meta data from application string.
     * 读取application 节点  meta-data 信息
     *
     * @param key the key meta-data的key
     * @return the string 对应key的value
     */
    public static String readMetaDataFromApplication(String key) {
        try {
            ApplicationInfo appInfo = sContext.getPackageManager()
                    .getApplicationInfo(sContext.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Start app.
     * 打开某个app
     *
     * @param packageName the package name 包名
     */
    public static void startApp(String packageName) {
        if (EmptyUtils.isSpace(packageName)) return;
        sContext.startActivity(sContext.getPackageManager().getLaunchIntentForPackage(packageName));
    }

    /**
     * Is install app boolean.
     * 是否安装了指定包名app
     *
     * @param packageName the package name 包名
     * @return the boolean 是否安装
     */
    public static boolean isInstallApp(String packageName) {
        PackageManager manager = sContext.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo info = pkgList.get(i);
            if (info.packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * Install apk.
     * 打开并安装apk
     *
     * @param file the file 文件
     */
    public static void installApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        sContext.startActivity(intent);
    }

    /**
     * Uninstall apk.
     * 卸载指定包名app
     *
     * @param packageName the package name 包名
     */
    public static void uninstallApk(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        sContext.startActivity(intent);
    }

    /**
     * Is running foreground boolean.
     * 是否前台运行
     * L之前，使用该接口需要 android.permission.GET_TASKS
     * 即使是自己开发的普通应用，只要声明该权限，即可以使用getRunningTasks接口。
     * 但从L开始，这种方式以及废弃。
     * 应用要使用该接口必须声明权限android.permission.REAL_GET_TASKS
     * 而这个权限是不对三方应用开放的。（在Manifest里申请了也没有作用）
     * 系统应用（有系统签名）可以调用该权限。
     *
     * @return the boolean 是否在前台
     */
    public static boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) sContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            if (componentName != null && componentName.getPackageName().equals(sContext.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is service running boolean.
     * 判断服务是否运行.
     *
     * @param className the class name 类名
     * @return the boolean 是否运行
     */
    public static boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) sContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
        Iterator<ActivityManager.RunningServiceInfo> l = servicesList.iterator();
        while (l.hasNext()) {
            ActivityManager.RunningServiceInfo si = l.next();
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * Stop running service boolean.
     * 停止服务
     *
     * @param className the class name 类名
     * @return the boolean 是否成功
     */
    public static boolean stopRunningService(String className) {
        Intent intent = null;
        boolean ret = false;
        try {
            intent = new Intent(sContext, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent != null) {
            ret = sContext.stopService(intent);
        }
        return ret;
    }

    /**
     * Gets package info.
     * 获取package info
     *
     * @return the package info
     */
    public static PackageInfo getPackageInfo() {
        PackageManager packageManager = sContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(sContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * Gets version name.
     * 获取版本名
     *
     * @return the version name 版本名
     */
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * Gets version code.
     * 获取版本号
     *
     * @return the version code 版本号
     */
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * Gets sign.
     * 获取应用签名
     *
     * @param pkgName the pkg name 包名
     * @return the sign 签名
     */
    public static String getSign(String pkgName) {
        try {
            PackageInfo pis = sContext.getPackageManager()
                    .getPackageInfo(pkgName,
                            PackageManager.GET_SIGNATURES);
            return hexDigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * hexDigest.
     * 将签名字符串转换成需要的32位签名
     *
     * @param paramArrayOfByte the pkg name 包名
     * @return the sign 32位签名字符串
     */
    private static String hexDigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Compare version int.
     * 比较版本号大小
     *
     * @param version1 the version 1 版本1
     * @param version2 the version 2 版本2
     * @return the int 比较结果，前者大返回正数， 后者大返回负数，相等则返回0
     * @throws Exception the exception
     */
    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion xloading_error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }


    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) DLFrame.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId() == null ? "" : tm.getDeviceId();
    }

    public static String getMac() {
        Enumeration<NetworkInterface> interfaces = null;
        String mMacStr = "";
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
        return mMacStr;
    }

    public static String getDeviceType() {
        return android.os.Build.MODEL + "-" + android.os.Build.BRAND;
    }


    public static String getPlatform() {
        return "Android版本：" + android.os.Build.VERSION.RELEASE + " 系统名称：" + Build.DISPLAY;
    }

    public static void copyToClipboard(String info, Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("msg", info);
        manager.setPrimaryClip(clipData);
    }

}
