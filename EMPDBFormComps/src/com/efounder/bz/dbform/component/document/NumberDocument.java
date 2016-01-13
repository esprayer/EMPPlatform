package com.efounder.bz.dbform.component.document;

import java.util.Arrays;

import javax.swing.text.*;

import com.efounder.pub.util.RegexUtil;

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
public class NumberDocument extends PlainDocument {

  //数值模式，允许输入正负号、小数点
    private String pattern = "^[-+]?\\d+(\\.\\d+)?$";

  //特殊处理的符号
    private String[] pmark = {"+", "-", ".", "%"};
   //百分比显示
    private boolean showPercentage = false;
    //是否启用最大最小控制
    private boolean maxinControl = false;
    //允许输入的最大数字
    private double maxNumber = Double.MAX_VALUE;
    //允许输入的最小数字
    private double minNumber = Double.MIN_VALUE;
    private boolean onlyInt=false;

    /**
     *
     */
    public NumberDocument() {
    }

    /**
     *
     * @param showPercentage boolean
     */
    public void setShowPercentage(boolean showPercentage) {
        this.showPercentage = showPercentage;
    }

    /**
     *
     * @param max double
     */
    public void setMaxNumber(double max) {
        this.maxNumber = max;
    }

    /**
     *
     * @param minNumber double
     */
    public void setMinNumber(double minNumber) {
        this.minNumber = minNumber;
    }

    /**
     *
     * @param maxinControl boolean
     */
    public void setMaxinControl(boolean maxinControl) {
        this.maxinControl = maxinControl;
    }

    public void setOnlyInt(boolean onlyInt) {
        this.onlyInt = onlyInt;
    }


    /**
     *
     * @return boolean
     */
    public boolean isShowPercentage() {
        return showPercentage;
    }

    /**
     *
     * @return double
     */
    public double getMaxNumber() {
        return maxNumber;
    }

    /**
     *
     * @return double
     */
    public double getMinNumber() {
        return minNumber;
    }

    /**
     *
     * @return boolean
     */
    public boolean isMaxinControl() {
        return maxinControl;
    }

    /**
     *
     * @param offset int
     * @param str String
     * @param a AttributeSet
     * @return boolean
     */
    public boolean isCanInsert(int offset, String str, AttributeSet a) throws
        BadLocationException {
        if (str == null) return false;
        String text = getText(0, getLength());
        //特殊处理小数点和正负号
        if (Arrays.asList(pmark).contains(str)) {
          //只能有一个小数点
           if(".".equals(str)&&onlyInt)return false;
            if (text.indexOf(".") < 0 && ".".equals(str)) return true;
            //正负号只能在最前头
            if ( ("+".equals(str) || "-".equals(str)) && text.length() == 0) return true;
            return false;
        }
        //正则校验未通过
        text = text.replaceAll(",", "");
        if (!RegexUtil.isMatch(pattern, text + str)) return false;
        //是否启用最大最小值判断
        if (maxinControl) {
            String pnum = text + str;
            double dnum = Double.parseDouble(pnum);
            if (dnum >= getMinNumber() && dnum <= getMaxNumber()) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     *
     */
    public void insertString(int offset, String str, AttributeSet a) throws
        BadLocationException {
       String value=str;
       str=str.replace(",","");
        if (isCanInsert(offset, str, a)){
            super.insertString(offset, value, a);
            ;
        }
    }

}
