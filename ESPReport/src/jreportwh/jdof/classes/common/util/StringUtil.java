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
     * 数字转化为字母形式
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
     * 字母转化为数字形式
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
     * 如果字符串位数不够则在最左边填充指定字符
     * @param source
     * @param ch      填充字符
     * @param length  字符串长度
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
     * 如果字符串位数不够则在最右边填充指定字符
     * @param source
     * @param ch      填充字符
     * @param length  字符串长度
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
     * 将多个字符串以指定分隔符连接
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
     * 将以指定分隔符连接的字符串分解为多个字符串
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
     * 反转
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
     * 去除左边的空格
     * @param source
     * @param ch
     * @return
     */
    public static String strip(String source, char ch) {
        return source;
    }

    /**
     * 字符串居中
     * @param source
     * @param ch
     * @return
     */
    public static String center(String source, char ch) {
        return source;
    }

    /**
     * 获得字符串第一个数字的索引
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
     * 获得字符串最后一个数字的索引
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
     * 字符串是否包含字符
     * @param str
     * @param searchChar
     * @return
     */
    public static boolean isContain(String str, char searchChar) {
        return false;
    }

    /**
     * 字符串是否包含字符串
     * @param str
     * @param searchStr
     * @return
     */
    public static boolean isContain(String str, String searchStr) {
        return false;
    }

    /**
     * 字符串是否以指定字符串开始
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
     * 字符串是字母
     * @param str
     * @return
     */
    public static boolean isAlpha(String str) {
        return true;
    }

    /**
     * 字符串是数字
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
     * 字符串是数字或者字母
     * @param str
     * @return
     */
    public static boolean isAlphanumeric(String str) {
        return true;
    }

    /**
     * 字符串是否为空或者trim后为""
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
     * 将位字符串转化为BitSet对象
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
     * 从坐标转换为行列数组
     * @param  coor String   坐标，如C5
     * @return      String[] 行列数组，如{"5","3"}，先行后列
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
            //是数字的话，就是行坐标，否则是列坐标
            if (Character.isDigit(c)) {
                rowBuffer.append(c);
            } else {
                //如果这个字母的值
                if (c >= 65 && c <= 90) {
                    colBuffer.append(c);
                }else{
                    return null;
                }
            }
        }
        String rowStr = rowBuffer.toString();
        String colStr = colBuffer.toString();
        //如果行列有一个为空，则非法
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
