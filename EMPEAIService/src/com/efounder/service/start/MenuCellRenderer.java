package com.efounder.service.start;

import javax.swing.Icon;
import javax.swing.table.TableCellRenderer;
import com.efounder.ui.Icons;
import com.efounder.pub.comp.CompoundIcon;
import javax.swing.JTable;
import com.core.xml.StubObject;
import java.awt.Component;
import com.efounder.node.JNodeStub;
import com.efounder.comp.table.StatusCellRenderer;
import com.efounder.comp.table.StatusIconCellRenderer;
import com.efounder.eai.ide.ExplorerIcons;
import java.awt.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MenuCellRenderer extends StatusIconCellRenderer {
  protected MenuCellRenderer(Icon icon){
      super(icon);
  }
  public static TableCellRenderer getInstance(Icon icon) {
    return new MenuCellRenderer(icon);
  }
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
     StatusCellRenderer df = (StatusCellRenderer)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
      if ( !(value instanceof StubObject) ) return df;
     StubObject stub = (StubObject)value;
     Icon newii;
     Icon ii = stub.getIcon();if ( ii == null ) ii = icon;
     if ( ii != null ) {
       int level = stub.getLevel();
       Icon blankIcon = Icons.getBlankIcon(16*(level),16);
       newii = new CompoundIcon(blankIcon,ii);
       JNodeStub ns=(JNodeStub) stub.getLostValue("NODEOBJECT",null);
       if (ns != null && ns.getChildNodeStubList() != null &&
           ns.getChildNodeStubList().size() > 0){
         df.setIcon(newii);
         super.setBackground(table.getBackground());
         this.lbText.setForeground(table.getForeground());
       }
       else{
         if(isSelected){
           super.setForeground(Color.white);
           this.lbText.setForeground(Color.white);
           super.setBackground(Color.blue.darker());
         }
          else {
            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getBackground()); // 可执行
            this.lbText.setForeground(table.getForeground());
          }
         ii = new CompoundIcon(ExplorerIcons.ICON_RUN,ii);
         newii=new CompoundIcon(blankIcon,ii);
          df.setIcon(newii);
       }
     }
     return df;
  }
}
