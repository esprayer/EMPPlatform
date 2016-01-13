package jframework.resource.classes;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.core.xml.JBOFClass;
import com.pub.util.StringFunction;

import jframework.resource.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JMulLangF1Object implements WindowListener{
  private Object ResObject = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JMulLangF1Object() {
    ResObject = this;
  }
  public JMulLangF1Object(Object resobject) {
    ResObject = resobject;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowOpened(WindowEvent e) {
    Object Sender;
    Sender = (Object)e.getSource();
    if ( Sender instanceof javax.swing.JDialog ) {
      synchronized(Sender) {
          MulLangDialog( (javax.swing.JDialog) Sender);
      }
    }

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowClosing(WindowEvent e){

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowClosed(WindowEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowIconified(WindowEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowDeiconified(WindowEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowActivated(WindowEvent e) {
//    Object Sender;
//    Sender = (Object)e.getSource();
//    if ( Sender instanceof javax.swing.JDialog ) {
//      synchronized(Sender) {
//          MulLangDialog( (javax.swing.JDialog) Sender);
//      }
//    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void   MulLangDialog(javax.swing.JDialog Dlg) {
    Component[] Components;
    // 处理对话框标题
    synchronized ( Dlg ) {
      Dlg.setTitle(JXMLResource.GetString(ResObject,Dlg.getClass().getName(),
                                          Dlg.getTitle()));
    }
    // 处理对话框中的其他控件
    Components = Dlg.getComponents();
    MulLangComponent(Components);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void MulLangComponent(Component[] Components) {
    if ( Components == null ) return;
    int i,Count;Object Comp;Component[] Comps;
    Object[] OArray={"中文"};
    Count = Components.length;
    for(i=0;i<Count;i++) {
      Comp = Components[i];
//      synchronized (Comp) {
        if (Comp instanceof javax.swing.JComponent) {
          ProcessComponent(Comp);
          ProcessBorder( (javax.swing.JComponent) Comp);
        }
        if ( Comp instanceof javax.swing.border.TitledBorder ) {
          JBOFClass.VoidCallObjectMethod(Comp,"setTitle",OArray);
        }
        if (Comp instanceof javax.swing.JComboBox) {
          ProcessComboBox( (javax.swing.JComboBox) Comp);
        }
        if (Comp instanceof javax.swing.JList) {
          ProcessList( (javax.swing.JList) Comp);
        }
        if (Comp instanceof javax.swing.JTabbedPane) {
          ProcessTabbedPane( (javax.swing.JTabbedPane) Comp);
        }
//      }
        // 如果还是容器
        if (Comp instanceof java.awt.Container) {
          Comps = ( (java.awt.Container) Comp).getComponents();
          MulLangComponent(Comps);
        }
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessComboBox(javax.swing.JComboBox combobox) {
    ListCellRenderer CR;JMyCellRenderer MyCR;
    CR = combobox.getRenderer();
    MyCR = new JMyCellRenderer(CR,this);
    combobox.setRenderer(MyCR);
    combobox.repaint();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessList(javax.swing.JList list) {
    ListCellRenderer CR;JMyCellRenderer MyCR;
    CR = list.getCellRenderer();
    MyCR = new JMyCellRenderer(CR,this);
    list.setCellRenderer(MyCR);
    list.repaint();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessTabbedPane(javax.swing.JTabbedPane TP) {
    for(int j=0;j<TP.getTabCount();j++) {
      TP.setTitleAt(j,(String)GetString("_",TP.getTitleAt(j)));
          TP.setToolTipTextAt(j,(String)GetString("_",TP.getToolTipTextAt(j)));
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessBorder(JComponent Comp) {
    javax.swing.border.Border mBorder = Comp.getBorder();
    javax.swing.border.Border TB;
    if ( mBorder instanceof javax.swing.border.Border ) {
      TB = (javax.swing.border.Border)mBorder;
      ProcessComponent(TB);
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessComponent(Object Comp) {
    String SecName=null,Text=null;
    Object Res = JBOFClass.CallObjectMethod(Comp,"getText");
    if ( Res != null ) {
      Text = (String)GetString("_",Res);
      Object[] OArray = new Object[1];
      OArray[0] = (Object)Text;
      if ( Comp instanceof JTextArea ||
           Comp instanceof JTextPane  ||
           Comp instanceof JEditorPane ||
           Comp instanceof JPasswordField ||
           Comp instanceof JTextField ||
           Comp instanceof JComboBox ||
           Comp instanceof JSpinner ||
           Comp instanceof JFormattedTextField ) {
        return;
      } else {
        JBOFClass.VoidCallObjectMethod(Comp, "setText", OArray);
      }
    } else {
      Res = JBOFClass.CallObjectMethod(Comp,"getTitle");
      if ( Res != null ) {
        Text = (String)GetString("_",Res);
        Object[] OArray = new Object[1];
        OArray[0] = (Object)Text;
        JBOFClass.VoidCallObjectMethod(Comp,"setTitle",OArray);
      }
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object GetString(String Suf,Object Obj) {
    if ( Obj == null ) return null;
    String Text,SecName;    SecName = Suf+Obj.toString();
    // 这样比较笨，谁有空改一改！
    SecName = StringFunction.replaceString(SecName," ","_");
    SecName = StringFunction.replaceString(SecName,"(","_");
    SecName = StringFunction.replaceString(SecName,")","_");
    SecName = StringFunction.replaceString(SecName,"%","_");
    SecName = StringFunction.replaceString(SecName,"/","_");
    SecName = StringFunction.replaceString(SecName,":","_");
//    JLocalRegistry.Put(SecName,Obj.toString());
    Text = Obj.toString();
    try {
      Text = JXMLResource.GetString(ResObject, SecName, Obj.toString());
    } catch ( Exception e ) {

    }
    return Text;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void windowDeactivated(WindowEvent e) {

  }
}
