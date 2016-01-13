package jreportfunction.model;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_133", "找不到函数的配制信息"}, {
        "String_127", "函数日期设置错误"}, {
        "String_126", "函数参数设置错误"}, {
        "String_105", "函数参数设置错误"}, {
        "String_84", "找不到函数的配制信息"}, {
        "String_220", "调用BIS出错！"}, {
        "String_202", "调用BIS出错！"}
    };
    public Object[][] getContents() {
        return contents;
    }
}
