package com.pub.comp;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * <p>Title:日期文本框</p>
 * <p>Description: 仅允许输入日期的文本框</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDateField extends JTextField  implements FocusListener
{
  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
  /**
   * 初始化
   */
  public JDateField()
    {
        super();
        setDocument(new dateDoc(this));
        addFocusListener(this);
    }
    public void focusGained(FocusEvent e){}
    public void focusLost(FocusEvent e)
    {
        removeFocusListener(this);/////////////////////////////////
        if(!dateDoc.isLegalDate(getText()))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this,res.getString("String_21"));
            requestFocus();
        }
        addFocusListener(this);/////////////////////////////////////
    }
}
