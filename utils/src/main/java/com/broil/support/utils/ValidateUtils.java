

package com.broil.support.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateUtils
 * <p>
 * Created by broil on 2014-5-19.
 */
public class ValidateUtils {

    private ValidateUtils() {
    }

    public static boolean isMobileNumber(String str) {
        String reg = "^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$";
        return validate(reg, str);
    }

    public static boolean isEmail(String str) {
        String reg = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]*@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return validate(reg, str);
    }

    public static boolean isNumeric(String str) {
        return validate("^[0-9]+$", str);
    }

    public static boolean isNumAndAlphabet(String str) {
        return validate("^[A-Za-z0-9]+$", str);
    }

    public static boolean isAlphabet(String str) {
        return validate("^[A-Za-z]+$", str);
    }

    public static boolean isUrl(String str) {
        String reg = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return validate(reg, str);
    }

    public static boolean isChinese(String str) {
        String reg = "[\\u4e00-\\u9fa5]+";// 表示+表示一个或多个中文
        return validate(reg, str);
    }

    public static boolean containChinese(String str) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count++;
            }
        }

        return count > 0;
    }

    public static boolean validate(String expression, String str) {
        if (str == null) return false;
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return validate(regex, idCard);
    }

    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return
     */
    public static String phoneNoHide(String phone) {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return
     */
    public static String cardIdHide(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }
}
