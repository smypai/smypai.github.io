package Java.Java基础.数据类型.数据校验;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证真实用户名
     */
    public static final String REGEX_REALNAME = "^[\\x{4e00}-\\x{9fa5}_a-zA-Z0-9（）\\-\\.\\(\\)]{1,20}/u";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    public static final String REGEX_COMPLEX_PASSWORD =
        "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-ZW_]+$)(?![a-z0-9]+$)(?![a-zW_]+$)(?![0-9W_]+$)[a-zA-Z0-9W_]{8,}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL =
        "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证只能为数值
     */
    public static final String REGEX_NUMBER = "^[0-9]*$";

    public static final String REGEX_CUSTOM_NAME = "^[\\x{4e00}-\\x{9fa5}A-Za-z0-9\\(\\)-_.（）]{1,20}$";

    /**
     * 校验用户名
     * @param String realname
     * @return bool 校验通过返回true，否则返回false
     */
    public static boolean isRealName(String realName) {
        return Pattern.matches(REGEX_REALNAME, realName);
    }

    /**
     * 校验用户名
     * @param String username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean isComplexPassword(String password) {
        return Pattern.matches(REGEX_COMPLEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param String mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 校验 只能为数字
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        return Pattern.matches(REGEX_NUMBER, number);
    }

    public static boolean checkCustomName(String customName) {
        return Pattern.matches(REGEX_CUSTOM_NAME, customName);
    }

    /**
     * 校验 是否为日期
     * @param length int
     * @param sDate 2020-01-01
     * @return bool
     */
    public static boolean isDate(int length, String sDate){ return isLegalDate(length,sDate,"yyyy-MM-dd");}

    /**
     * 校验 是否为时间字符串
     * @param length int
     * @param sDate 12:36:30
     * @return bool
     */
    public static boolean isTime(int length, String sDate){ return isLegalDate(length,sDate,"HH:mm:ss");}

    /**
     * 根据时间 和时间格式 校验是否正确
     * @param length 校验的长度
     * @param sDate 校验的日期
     * @param format 校验的格式
     * @return bool
     * isLegalDate(yearMonthday.length(),"2020-01-01","yyyy-MM-dd")
     */
    public static boolean isLegalDate(int length, String sDate,String format) {
        int legalLen = length;
        if ((sDate == null) || (sDate.length() != legalLen)) {
            return false;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

}