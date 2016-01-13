package com.pub.comp;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import com.efounder.pub.util.StringFunction;
import java.util.ResourceBundle;
/**
 * <p>Title:数字文本框</p>
 * <p>Description: 仅允许输入数字的文本框</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JNumberField extends JTextField implements FocusListener
{
  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
  private int dec=2;
  /**
   * 初始化
   */
  public JNumberField()
    {
        super(12);
        setDocument(new numDoc());
        this.addFocusListener(this);
    }
    /**
     * 指定精度的初始化
     * @param decLen 精度
     */
    public JNumberField(int decLen)
    {
        super(12);
        setDocument(new numDoc(decLen));
        this.addFocusListener(this);
        dec = decLen;
    }
    /**
     * 指定精度,定长的初始化
     * @param decLen 精度
     * @param maxLen 最大长度
     */
    public JNumberField(int decLen,int maxLen)
    {
        super(12);
        setDocument(new numDoc(decLen,maxLen));
        this.addFocusListener(this);
        dec = decLen;
    }
  public void focusGained(FocusEvent e) {
    String text = this.getText();
    if(!text.trim().equals("")){
      try{
        double value = Double.parseDouble(text);
        java.text.NumberFormat nf = new java.text.DecimalFormat("######."+
            StringFunction.fillString("",'0',dec));
        this.setText(nf.format(value));
      }catch(Exception ex){
      }
    }
    this.selectAll();
  }
  public void focusLost(FocusEvent e) {
  }
}
