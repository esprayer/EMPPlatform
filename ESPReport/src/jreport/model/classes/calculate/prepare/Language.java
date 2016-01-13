package jreport.model.classes.calculate.prepare;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_3", "预处理函数[%s]中不存在[%s]取数对象"}, {
        "String_19", "SAP函数计算出错：\r\n"}, {
        "String_18", "没有找到内部公式对应的预处理公式："}, {
        "String_17", "内部公式定义不正确："}
    };
    public Object[][] getContents() {
        return contents;
    }
}
