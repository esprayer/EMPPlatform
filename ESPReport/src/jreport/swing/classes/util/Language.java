package jreport.swing.classes.util;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_104", "系统提示"}, {
        "String_103", "选择类别节点为空，请选择节点！"}, {
        "String_102", "系统提示"}, {
        "String_101", "选择报表为空，请选择报表！"}, {
            "String_152", "<校验>"},  {
        "String_153", "<调整>"},{
        "String_150", "<计算>"}
    };
    public Object[][] getContents() {
        return contents;
    }
}
