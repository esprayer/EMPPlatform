package jreport.value;

import java.util.ListResourceBundle;

public class Language
    extends ListResourceBundle {
    private static final Object[][] contents = new String[][] {
        {
        "String_44", "��ʼ��������ʱ����\r\nԭ��"}, {
        "String_40", "�����ȳ�ʼ��"}, {
        "String_63", "��������ȣ�"}, {
        "String_62", "ȡ��(C)"}, {
        "String_60", "ȷ��(O)"},
        {"String_84","У��ѡ��"}
    };
    public Object[][] getContents() {
        return contents;
    }
}
