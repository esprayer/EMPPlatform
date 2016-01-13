package jreportfunction.pub;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_135", "自定义辅助"}, {
        "String_120", "专项核算"}, {
        "String_106", "要素台账"}, {
        "String_91", "现金流量"}, {
        "String_76", "往来单位"}, {
        "String_61", "个人往来"}, {
        "String_46", "产品核算"}, {
        "String_31", "成本中心"}, {
        "String_16", "责任成本中心核算"}, {
        "String_2", "科目"}
    };
    public Object[][] getContents() {
        return contents;
    }
}
