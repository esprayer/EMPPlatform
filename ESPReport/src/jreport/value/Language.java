package jreport.value;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_44", "初始化会计年度时出错！\r\n原因："}, {
        "String_40", "会计年度初始化"}, {
        "String_63", "启用新年度："}, {
        "String_62", "取消(C)"}, {
        "String_60", "确定(O)"},
        {"String_84","校验选项"}
    };
    public Object[][] getContents() {
        return contents;
    }
}
