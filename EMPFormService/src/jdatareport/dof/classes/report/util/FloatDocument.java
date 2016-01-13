package jdatareport.dof.classes.report.util;

import javax.swing.text.*;

/**
 * <p>Title:FloatDocument </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class FloatDocument
    extends PlainDocument {

    private int size = 0;
    /**
     * 构造方法
     * @param size 字符串最大长度
     */
    public FloatDocument(int size) {
        this.size = size;
    }

    public void insertString(int offset, String str, AttributeSet as) throws
        javax.swing.text.BadLocationException {
        /**@todo Override this javax.swing.text.PlainDocument method*/
        boolean allow = false;
        /**
         * 首先判断输入的是否是数字字符
         * 否则beep
         * 是则继续判断输入的字符数是否小于size
         * 否则beep
         * 是则
         * super...
         */

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ( (!Character.isDigit(chars[i])) && chars[i] != '.') {
                allow = false;
                java.awt.Toolkit.getDefaultToolkit().beep();
                break;
            }
            else if (offset + 1 > size) {
                allow = false;
                java.awt.Toolkit.getDefaultToolkit().beep();
                break;
            }
            else {
                allow = true;
                break;
            }

        }
        if (allow) {
            super.insertString(offset, str, as);

        }
    }
}