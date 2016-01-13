package jcomponent.custom.swing;

import javax.swing.*;

import com.core.xml.JBOFClass;

import java.awt.Component;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JIConListCellRenderer extends JLabel implements ListCellRenderer {
  private ImageIcon CellIcon = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JIConListCellRenderer(ImageIcon icon) {
    CellIcon = icon;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Component getListCellRendererComponent(
    JList list,
    Object value,            // value to display
    int index,               // cell index
    boolean isSelected,      // is the cell selected
    boolean cellHasFocus)    // the list and the cell have the focus
  {
      String s = value.toString();
      setText(s);
      Icon icon = (Icon)JBOFClass.CallObjectMethod(value,"getIcon");
      if ( icon == null )
        setIcon(CellIcon);
      else
        setIcon(icon);
     if (isSelected) {
          setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
  }
      else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
  }
  setEnabled(list.isEnabled());
  setFont(list.getFont());
      setOpaque(true);
      return this;
  }

}