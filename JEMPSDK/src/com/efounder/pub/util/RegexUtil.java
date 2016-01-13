package com.efounder.pub.util;

import java.util.regex.*;

/**
 * 正则表达式工具
 */
public class RegexUtil {

    /**
     *
     * @param pattern String
     * @param content String
     * @return boolean
     */
    public static boolean isMatch(String pattern, String content) {
        Pattern p;
        Matcher m;
        pattern = pattern == null ? "" : pattern;
        content = content == null ? "" : content;
        try {
            p = Pattern.compile(pattern);
            m = p.matcher(content);
            return m.matches();
        } catch (java.util.regex.PatternSyntaxException e) {
            return false;
        }
    }

    /**
     *
     * @param pattern String
     * @param content String
     * @return boolean
     */
    public static boolean isFind(String pattern, String content) {
        Pattern p;
        Matcher m;
        pattern = pattern == null ? "" : pattern;
        content = content == null ? "" : content;
        try {
            p = Pattern.compile(pattern);
            m = p.matcher(content);
            return m.find();
        } catch (java.util.regex.PatternSyntaxException e) {
            return false;
        }
    }


    public static void main(String[] args) {
        String pattern = "^\\d{4}$";
        String content = "1234";
        System.out.println(isFind(pattern, content ));
    }

}
