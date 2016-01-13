package com.efounder.action;

import java.awt.event.*;
import javax.swing.*;

import java.awt.*;
import org.openide.*;
import com.efounder.service.security.*;
import com.efounder.eai.*;
import com.efounder.help.HelpTopic;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class UpdateAction extends AbstractAction implements UpdateableAction,Runnable{
  // ����Ȩ��Key
  protected String SecurityKey   = null;
  // ����Ȩ��Value
  protected String SecurityValue = null;
  // ���Ȩ��Key
  protected String AccessKey     = null;
  // ���Ȩ��Value
  protected String AccessValue   = null;

  public static final String SHOW_BUTTON_TEXT;//      = "ShowButtonText";
  public static final String ALT_SHORT_DESCRIPTION;// = "AltShortDescription";
  public static final String LARGE_ICON;//            = "LargeIcon";
  public static final String HELP_TOPIC;//            = "HelpTopic";
  public static final String ACCELERATOR;//           = "Accelerator";
  public static final String MNEMONIC;//              = "Mnemonic";
  public static final UpdateAction EMPTY_ARRAY[] = new UpdateAction[0];
  public void setSecurityValue(String SecurityValue) {
    this.SecurityValue = SecurityValue;
  }

  public String getSecurityValue() {
    return SecurityValue;
  }

  public void setAccessValue(String AccessValue) {
    this.AccessValue = AccessValue;
  }

  public String getAccessValue() {
    return AccessValue;
  }

  public void setAccessKey(String AccessKey) {
    this.AccessKey = AccessKey;
  }

  public String getAccessKey() {
    return AccessKey;
  }

  public void setSecurityKey(String SecurityKey) {
    this.SecurityKey = SecurityKey;
  }

  public String getSecurityKey() {
    return SecurityKey;
  }

  public UpdateAction() {
  }
  /**
   *
   * @param e ActionEvent
   */
  public abstract void doPerformed(ActionEvent e);
  /**
   *
   * @return boolean
   */
  protected boolean canAction() {
    return true;
  }
  /**
   *
   * @return String
   */
  protected String getLastErrorMessge() {
    return null;
  }
  /**
   *
   * @param e ActionEvent
   */
  public final void actionPerformed(ActionEvent e) {
    if ( !canAction() ) {
      return;
    }
    if ( !this.isEnabled() ) return;
    // �ȴ����
    if ( waitInvoke ) {
      noThreadDo(e);
    // ���ȴ����
    } else {
      threadDo(e);
    }
  }
  protected boolean waitInvoke = true;
  /**
   *
   * @param v boolean
   */
  public void setWaitInvoke(boolean v) {
    waitInvoke = v;
  }
  /**
   *
   * @return boolean
   */
  public boolean isWaitInvoke() {
    return waitInvoke;
  }
//  /**
//   *
//   * @param e ActionEvent
//   */
//  protected void noThreadDo(ActionEvent e) {
//    Object comp = e.getSource();
//    try {
//      WaitingManager.getDefault().showWait(findWindow(comp));
//      doPerformed(e);
//    } catch ( Exception ex ) {
//      ex.printStackTrace();
//    } finally {
//       java.awt.Window win=findWindow(comp);
//      if(win==null)
//        win=EAI.getMainWindow();
//      WaitingManager.getDefault().endShowWait(win);
//    }
//  }
  protected ActionEvent actionEvent = null;
  protected void threadDo(ActionEvent e) {
    actionEvent = e;
//    Object comp = actionEvent.getSource();
//    WaitingManager.getDefault().beginWait(findWindow(comp));
    this.run();
//    try {
//      WaitingManager.getDefault().waitInvoke(this.getShortText(),"����ִ��"+this.getShortText(),this.getSmallIcon(),this);
//    } catch ( Exception ex) {
//    	ex.printStackTrace();
//    } finally {
////      WaitingManager.getDefault().endWait(findWindow(comp));
//    }
  }
  
  protected void noThreadDo(ActionEvent e) {
	  doPerformed(e);
  }
  
  /**
   *
   */
  public void run() {
    doPerformed(actionEvent);
  }
  public final void update(Object obj) {
    if ( ServiceSecurityManager.getDefault() != null && ServiceSecurityManager.getDefault().checkPermission(this.getSecurityKey()) ) {
      doUpdate(obj);
    } else {
      this.setEnabled(true);
    }
  }
  /**
   *
   * @param obj Object
   */
  public void doUpdate(Object obj) {
    setEnabled(true);
  }

  public static java.awt.Window findWindow(Object obj)
  {
      if(obj instanceof java.awt.Window)
          return (java.awt.Window)obj;
      if(obj instanceof Component)
          return findWindow(((Component)obj).getParent());
      else
          return null;
  }

  static
  {
      MNEMONIC              = "Mnemonic";
      ACCELERATOR           = "Accelerator";
      HELP_TOPIC            = "HelpTopic";
      LARGE_ICON            = "LargeIcon";
      ALT_SHORT_DESCRIPTION = "AltShortDescription";
      SHOW_BUTTON_TEXT      = "ShowButtonText";
  }
  public HelpTopic getHelpTopic()
  {
      Object obj = super.getValue("HelpTopic");
      if(obj instanceof HelpTopic)
          return (HelpTopic)obj;
      else
          return null;
  }

  public void setHelpTopic(HelpTopic helptopic)
  {
      super.putValue("HelpTopic", helptopic);
  }

  public Icon getLargeIcon()
  {
      Object obj = super.getValue("LargeIcon");
      if(obj instanceof Icon)
          return (Icon)obj;
      else
          return null;
  }

  public void setLargeIcon(Icon icon)
  {
      super.putValue("LargeIcon", icon);
  }

  public Icon getSmallIcon()
  {
      Object obj = super.getValue("SmallIcon");
      if(obj instanceof Icon)
          return (Icon)obj;
      else
          return null;
  }

  public void setSmallIcon(Icon icon)
  {
      super.putValue("SmallIcon", icon);
  }

  public String getLongText()
  {
      Object obj = super.getValue("LongDescription");
      if(obj instanceof String)
          return (String)obj;
      else
          return null;
  }

  public void setLongText(String s)
  {
      super.putValue("LongDescription", s);
  }

  public char getMnemonic()
  {
      Object obj = super.getValue("Mnemonic");
      if(obj instanceof Character)
          return ((Character)obj).charValue();
      else
          return '\0';
  }

  public void setMnemonic(char c)
  {
      super.putValue("Mnemonic", new Character(c));
  }

  public String getAltShortText()
  {
      Object obj = super.getValue("AltShortDescription");
      if(obj instanceof String)
          return (String)obj;
      else
          return null;
  }

  public void setAltShortText(String s)
  {
      String s1 = getShortText();
      if(s1 == null || s == null || !s1.equals(s))
          super.putValue("AltShortDescription", s);
  }

  public String getShortText()
  {
      Object obj = super.getValue("ShortDescription");
      if(obj instanceof String)
          return (String)obj;
      else
          return null;
  }

  public void setShortText(String s)
  {
      super.putValue("ShortDescription", s);
  }

  public UpdateAction(String s, char c, String s1, Icon icon, Icon icon1)
  {
      this(s, c, s1, icon);
      super.putValue("LargeIcon", icon1);
  }

  public UpdateAction(String s, char c, String s1, Icon icon, String s2)
  {
      this(s, c, s1);
      super.putValue("SmallIcon", icon);
      setAltShortText(s2);
  }

  public UpdateAction(String s, char c, String s1, Icon icon)
  {
      this(s, c, s1);
      super.putValue("SmallIcon", icon);
  }

  public UpdateAction(String s, char c, String s1)
  {
      this(s, c);
      super.putValue("LongDescription", s1);
  }

  public UpdateAction(String s, char c)
  {
      this(s);
      super.putValue("Mnemonic", new Character(c));
  }

  public UpdateAction(String s)
  {
      super.putValue("ShortDescription", s);
  }
}
