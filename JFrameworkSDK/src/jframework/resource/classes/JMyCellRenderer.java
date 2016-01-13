package jframework.resource.classes;

import java.awt.*;

import javax.swing.*;

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
class JMyCellRenderer extends javax.swing.DefaultListCellRenderer {
  ListCellRenderer CellRenderer;
  JMulLangF1Object MulObject;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JMyCellRenderer(ListCellRenderer CR,JMulLangF1Object MO) {
    super();
    CellRenderer = CR;
    MulObject    = MO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public java.awt.Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
      if ( CellRenderer != null && MulObject != null ) {
        Object Res = MulObject.GetString("_LST_",value);
        return CellRenderer.getListCellRendererComponent(list,Res,index,isSelected,cellHasFocus);
      }
      return getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
  }
}
