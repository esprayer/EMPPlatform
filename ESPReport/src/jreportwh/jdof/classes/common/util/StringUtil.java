package jreportwh.jdof.classes.common.util;

import java.util.*;

/**
 * <p>Title: StringUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class StringUtil {

    static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");

    /**
     * ����ת��Ϊ��ĸ��ʽ
     * @param sid String
     * @return int
     */
    public static int getID(String sid) {
        int result = 0;
        int carry = 1;
        for (int i = sid.length() - 1; i >= 0; i--) {
            result += ( (sid.charAt(i) - 65) + 1) * carry;
            carry *= 26;
        }
        return result;
    }

    /**
     * ��ĸת��Ϊ������ʽ
     * @param index int
     * @return String
     */
    public static String getID(int index) {
        String str = "";
        index--;
        str += (char) (65 + index % 26);
        while ( (index = index / 26 - 1) >= 0) {
            str = (char) (65 + index % 26) + str;
        }
        return str;
    }

    /**
     * ����ַ���λ������������������ָ���ַ�
     * @param source
     * @param ch      ����ַ�
     * @param length  �ַ�������
     * @return
     */
    public static String leftPad(String source, char ch, int length) {
        if (source != null && source.length() < length) {
            StringBuffer target = new StringBuffer(source);
            for (int i = 0; i < length - source.length(); i++) {
                target.insert(0, ch);
            }
            return target.toString();
        }
        return source;
    }

    /**
     * ����ַ���λ�������������ұ����ָ���ַ�
     * @param source
     * @param ch      ����ַ�
     * @param length  �ַ�������
     * @return
     */
    public static String rightPad(String source, char ch, int length) {
        if (source != null && source.length() < length) {
            StringBuffer target = new StringBuffer(source);
            for (int i = 0; i < length - source.length(); i++) {
                target.append(ch);
            }
            return target.toString();
        }
        return source;
    }

    /**
     * ������ַ�����ָ���ָ�������
     * @param source
     * @param separator
     * @return
     */
    public static String join(Vector source, char separator) {
        String target = null;
        if (source != null && source.size() > 0) {
            for (Enumeration enume = source.elements(); enume.hasMoreElements(); ) {
                String crtStr = (String) enume.nextElement();
                target.concat(crtStr);
                target.concat(Character.toString(separator));
            }
            target.substring(target.length() - 1);
        }
        return target;
    }

    /**
     * ����ָ���ָ������ӵ��ַ����ֽ�Ϊ����ַ���
     * @param source
     * @param separator
     * @return
     */
    public static Vector split(String source, char separator) {
        Vector target = null;
        if (source != null && source.length() > 0) {
            target = new Vector();
            String[] strs = source.split(Character.toString(separator));
            for (int i = 0; i < strs.length; i++) {
                String crtStr = strs[i];
                if (!crtStr.equals(Character.toString(separator))) {
                    target.addElement(crtStr);
                }
            }
        }
        return target;
    }

    /**
     * ��ת
     * @param source
     * @return
     */
    public static String reverse(String source) {
        if (source != null) {
            StringBuffer target = new StringBuffer(source);
            return target.reverse().toString();
        }
        return source;
    }

    /**
     * ȥ����ߵĿո�
     * @param source
     * @param ch
     * @return
     */
    public static String strip(String source, char ch) {
        return source;
    }

    /**
     * �ַ�������
     * @param source
     * @param ch
     * @return
     */
    public static String center(String source, char ch) {
        return source;
    }

    /**
     * ����ַ�����һ�����ֵ�����
     * @param str
     * @return
     */
    public static int getFirstNumberIndex(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ����ַ������һ�����ֵ�����
     * @param str
     * @return
     */
    public static int getLastNumberIndex(String str) {
        int index = -1;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                index = i;
            }
        }
        return index;
    }

    /**
     * �ַ����Ƿ�����ַ�
     * @param str
     * @param searchChar
     * @return
     */
    public static boolean isContain(String str, char searchChar) {
        return false;
    }

    /**
     * �ַ����Ƿ�����ַ���
     * @param str
     * @param searchStr
     * @return
     */
    public static boolean isContain(String str, String searchStr) {
        return false;
    }

    /**
     * �ַ����Ƿ���ָ���ַ�����ʼ
     * @param str
     * @param prefixes
     * @return
     */
    public static boolean isStartWith(String str, String[] prefixes) {
        for (int i = 0; i < prefixes.length; i++) {
            if (str.startsWith(prefixes[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * �ַ�������ĸ
     * @param str
     * @return
     */
    public static boolean isAlpha(String str) {
        return true;
    }

    /**
     * �ַ���������
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * �ַ��������ֻ�����ĸ
     * @param str
     * @return
     */
    public static boolean isAlphanumeric(String str) {
        return true;
    }

    /**
     * �ַ����Ƿ�Ϊ�ջ���trim��Ϊ""
     * @param str
     * @return
     */
    public static boolean isNullSpace(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * ��λ�ַ���ת��ΪBitSet����
     * @param bits
     * @return
     */
    public static BitSet toBitSet(String bits) {
        BitSet bitset = new BitSet();
        for (int i = 0; i < bits.length(); i++) {
            bitset.set(i, bits.charAt(i) == '1');
        }
        return bitset;
    }

    /**
     * ������ת��Ϊ��������
     * @param  coor String   ���꣬��C5
     * @return      String[] �������飬��{"5","3"}�����к���
     */
    public static String[] getRowColByCoor(String coor){
        if (coor == null || coor.trim().length() == 0)
            return null;
        String[] rowCol = new String[2];
        coor = coor.toUpperCase();
        char[] chars = coor.toCharArray();
        StringBuffer rowBuffer = new StringBuffer();
        StringBuffer colBuffer = new StringBuffer();
        for (int i = 0, n = chars.length; i < n; i++) {
            char c = chars[i];
            //�����ֵĻ������������꣬������������
            if (Character.isDigit(c)) {
                rowBuffer.append(c);
            } else {
                //��������ĸ��ֵ
                if (c >= 65 && c <= 90) {
                    colBuffer.append(c);
                }else{
                    return null;
                }
            }
        }
        String rowStr = rowBuffer.toString();
        String colStr = colBuffer.toString();
        //���������һ��Ϊ�գ���Ƿ�
        if (rowStr.trim().length() == 0 || colStr.trim().length() == 0)
            return null;
        rowCol[0] = rowStr;
        rowCol[1] = String.valueOf(getID(colStr));
        return rowCol;
    }

    public static void main(String[] a) {
        String rowCol = "A1:A2";
        String[] rc = getRowColByCoor(rowCol);
        System.out.println(rowCol + " --> " + rc[0] + "-" + rc[1]);
    }

}
