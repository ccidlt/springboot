package com.ds.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 判断long值是否为0或NULL
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(Long s) {
        if (s == null || s == 0) return true;
        return false;
    }

    public static boolean isNotEmpty(Long s) {
        return !isEmpty(s);
    }

    /**
     * 判断Integer值是否为0或NULL
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(Integer s) {
        if (s == null || s == 0) return true;
        return false;
    }

    public static boolean isNotEmpty(Integer s) {
        return !isEmpty(s);
    }

    /**
     * 判断字符是否为空串或NULL
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (s == null || "".equals(s.trim())) return true;
        return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 判断字符是否为空串或NULL
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(Object s) {
        if (s == null || "".equals(s.toString().trim())) return true;
        return false;
    }

    public static boolean isNotEmpty(Object s) {
        return !isEmpty(s);
    }

    /**
     * @param s
     * @return
     */
    public static String getString(Object s) {
        if (s == null) {
            return "";
        }
        return String.valueOf(s);
    }

    /**
     * @param s
     * @return
     */
    public static String getString(Object s, String defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        return String.valueOf(s);
    }

    /**
     * @param s
     * @return
     */
    public static String getString(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     * @param s
     * @return
     */
    public static String getString(Integer s) {
        if (s == null) {
            return "";
        }
        return String.valueOf(s);
    }


    /**
     * @param s
     * @return
     */
    public static String getString(Float s) {
        if (s == null) {
            return "";
        }
        return String.valueOf(s);
    }

    /**
     * @param s
     * @return
     */
    public static String getString(Long s) {
        if (s == null) {
            return "";
        }
        return String.valueOf(s);
    }


    /**
     * 查询获取OR集合
     *
     * @param field
     * @param os
     * @param separator
     * @param open
     * @param close
     * @return
     */
    public static String getBySeparator(String field, Object[] os, String separator, String open, String close) {
        StringBuffer buf = new StringBuffer();
        if (os != null) {
            buf.append(open);
            for (int i = 0; i < os.length; i++) {
                buf.append(" ");
                buf.append(field);
                buf.append("=");
                buf.append(os[i]);
                buf.append(" ");
                if (i != os.length - 1) {
                    buf.append(separator);
                }
            }
            buf.append(close);
        }
        return buf.toString().replaceAll("'", "");
    }

    public static String formatStr2UrlStr(String str) {
        String realStr = null;
        if (!isEmpty(str)) {
            realStr = str.replaceAll("&", "&amp;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("<", "&lt;");
        }
        return realStr;
    }

    /**
     * 判断数字是否为手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 手机号:目前全国有27种手机号段。
     * 移动有16个号段：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188。其中147、157、188是3G号段，其他都是2G号段。
     * 联通有7种号段：130、131、132、155、156、185、186。其中186是3G（WCDMA）号段，其余为2G号段。
     * 电信有4个号段：133、153、180、189。其中189是3G号段（CDMA2000），133号段主要用作无线网卡号
     * 150、151、152、153、155、156、157、158、159 九个;
     * 130、131、132、133、134、135、136、137、138、139 十个;
     * 180、182、184、185、186、187、188、189 八个;
     * 13、15、18三个号段共30个号段，154暂时没有，加上147共27个
     * 增加177手机号
     **/
    public static boolean telCheck(String tel) {
        Pattern p = Pattern.compile("^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,1,2,3,4,5,6,7,8,9]\\d{8}$)|(147\\d{8})$|(178\\d{8})$|(177\\d{8})$|(175\\d{8})$|(170\\d{8})$|(171\\d{8})$|(173\\d{8})$)");
        Matcher m = p.matcher(tel);
        return m.matches();
    }
}
