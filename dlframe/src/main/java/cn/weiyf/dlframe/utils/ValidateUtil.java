package cn.weiyf.dlframe.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidateUtil {

    public final static int PHONE_NUM = 0;
    public final static int CHINESE = 1;
    public final static int EMAIL = 2;
    public final static int PASSWD = 3;

    private static Pattern emailPattern;
    private static Pattern phonePattern;
    private static Pattern passwordPattern;
    private static Pattern chinesePattern;

    private static boolean isEmail(String email) {
        if (null == emailPattern)
            initEmailPattern();
        return emailPattern.matcher(email).matches();
    }

    private static void initEmailPattern() {
        emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    private static boolean isPhoneNumber(String phoneNumber) {
        if (null == phonePattern)
            initPhonePatter();
        return phonePattern.matcher(phoneNumber).matches();
    }

    private static void initPhonePatter() {
        phonePattern = Pattern.compile("^1\\d{10}$");
    }

    private static boolean isPassword(String password) {
        if (passwordPattern == null)
            initPasswordPatter();
        return passwordPattern.matcher(password).matches();
    }

    private static void initPasswordPatter() {
        passwordPattern = Pattern.compile("^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,12}$");
    }

    private static boolean isChinese(String str) {
        if (null == chinesePattern)
            initChinese();
        return chinesePattern.matcher(str).matches();
    }

    private static void initChinese() {
        chinesePattern = Pattern.compile("[\u4e00-\u9fa5]+");
    }

    public static boolean isValidate(Object obj) {
        return obj != null;
    }

    public static boolean isValidate(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isValidate(Map<?, ?> map) {
        return map != null && map.size() > 0;
    }

    public static boolean isValidate(int type, String str) {
        switch (type) {
            case PHONE_NUM:
                return isPhoneNumber(str);
            case CHINESE:
                return isChinese(str);
            case EMAIL:
                return isEmail(str);
            case PASSWD:
                return isPassword(str);
        }
        return false;
    }

    /**
     * 验证年龄0-120
     *
     * @param value
     * @return
     */
    public static boolean checkAge(String value) {
        return value.matches("120|((1[0-1]|\\d)?\\d)");
    }


    /**
     * 检查QQ是否 合法，必须是数字，且首位不能为0，最长15位
     *
     * @param value
     * @return
     */

    public static boolean checkQQ(String value) {
        return value.matches("[1-9][0-9]{4,13}");
    }

    /**
     * 检查邮编是否 合法
     *
     * @param value
     * @return
     */
    public static boolean checkPostCode(String value) {
        return value.matches("[1-9]\\d{5}(?!\\d)");
    }

}
