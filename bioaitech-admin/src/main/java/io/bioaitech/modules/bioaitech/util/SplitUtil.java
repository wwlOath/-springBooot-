package io.bioaitech.modules.bioaitech.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author miaozhuang
 * @Description
 * @Date 2019/10/8
 */
public class SplitUtil {
    /**
     * 截取数字
     *
     * @param content
     * @return
     */
    public static Integer getNumbers(String content) {
    	String arg = "\\d+";
        Pattern pattern = Pattern.compile(arg);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return Integer.valueOf(matcher.group(0));
        }
        return 0;
    }

    /**
     * 截取非数字
     *
     * @param content
     * @return
     */
    public static String splitNotNumber(String content) {
    	String arg = "\\D+";
        Pattern pattern = Pattern.compile(arg);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
