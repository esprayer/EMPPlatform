package jreport.model.classes.calculate;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_136", "ReportError：函数左右括号不匹配-"}, {
        "String_135", "ReportError：函数没有找到左括号-"}, {
        "String_11", "SAP函数计算出错：\r\n"}, {
        "String_211", "报表没有通过校验，发现了【%s】处错误！"}, {
        "String_210", "报表校验通过！"}, {
        "String_183", " 错误："}, {
        "String_182", "\r\n报表编号："}, {
        "String_166", "报表校验"}, {
        "String_217", "报表计算"}, {
        "String_30", "校验"}, {
        "String_29", "计算"}, {
		"String_40", "计算完成,提交数据"}, {
        "String_36", "系统中线程数"}, {
        "String_26", "当前使用内存"}, {
        "String_24", "JVM空闲内存"}, {
        "String_22", "JVM内存总量"}, {
        "String_21", "已执行线程数"}, {
        "String_20", "当前执行线程"}, {
        "String_19", "当前等待线程"}, {
            "String_18", "已分配连接数"}, {
            "String_17", "已释放连接数"}, {
            "String_16", "系统中连接数"}
    };
    public Object[][] getContents() {
        return contents;
    }
}
