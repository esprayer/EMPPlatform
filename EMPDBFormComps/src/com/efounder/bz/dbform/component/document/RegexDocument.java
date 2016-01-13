package com.efounder.bz.dbform.component.document;

import javax.swing.text.*;

import com.efounder.pub.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class RegexDocument extends PlainDocument {

  //正则表达式模式
    private String regexPattern;

    /**
     *
     */
    public RegexDocument() {
        this(null);
    }

    /**
     *
     * @param pattern String
     */
    public RegexDocument(String pattern) {
        this.regexPattern = pattern;
    }

    /**
     *
     * @param pattern String
     */
    public void setRegexPattern(String pattern) {
        this.regexPattern = pattern;
    }

    /**
     *
     * @return String
     */
    public String getRegexPattern() {
        return this.regexPattern;
    }

    /**
     *
     * @param offset int
     * @param str String
     * @param attr AttributeSet
     * @throws BadLocationException
     */
    public void insertString(int offset, String str, AttributeSet attr) throws
        BadLocationException {
        if (this.isMatch(offset, str, attr)) {
            super.insertString(offset, str, attr);
        }
    }

    /**
     *
     * @param offset int
     * @param str String
     * @param attr AttributeSet
     * @return boolean
     */
    protected boolean isMatch(int offset, String str, AttributeSet attr) throws
        BadLocationException {
        if (str == null) return false;
        String text = getText(0, getLength()) + str;
        //匹配成功则允许输入
        return RegexUtil.isMatch(getRegexPattern(), text);
    }

}
