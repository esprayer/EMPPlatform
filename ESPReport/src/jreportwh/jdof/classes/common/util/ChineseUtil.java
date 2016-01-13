package jreportwh.jdof.classes.common.util;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class ChineseUtil
{
    //��Ź���һ�����ֲ�ͬ��������ʼ��λ��
    static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");
  static final int[] secPosValueList = {
        1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
        3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
        4390, 4558, 4684, 4925, 5249};

    //��Ź���һ�����ֲ�ͬ��������ʼ��λ���Ӧ����
    static final char[] firstLetter = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'w', 'x', 'y', 'z'};

    //��ȡһ���ַ�����ƴ����
    public static String getFirstLetter(String oriStr)
    {
        String str = oriStr.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        char ch;
        char[] temp;
        for (int i = 0; i < str.length(); i++) { //���δ���str��ÿ���ַ�
            ch = str.charAt(i);
            temp = new char[] {ch};
            byte[] uniCode = new String(temp).getBytes();
            if (uniCode[0] < 128 && uniCode[0] > 0) { // �Ǻ���
                buffer.append(temp);
            } else {
                buffer.append(convert(uniCode));
            }
        }
        return buffer.toString();
    }

    //��ȡһ�����ֵ�ƴ������ĸ
    static char convert(byte[] bytes)
    {

        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= 160;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(ChineseUtil.getFirstLetter("I love u"));
        System.out.println(ChineseUtil.getFirstLetter("�Ұ������찲��"));
        System.out.println(ChineseUtil.getFirstLetter("I love �����찲��"));
    }
}
