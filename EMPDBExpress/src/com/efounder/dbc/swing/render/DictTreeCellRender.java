package com.efounder.dbc.swing.render;

import javax.swing.tree.*;
import javax.swing.JTree;
import java.awt.*;
import javax.swing.*;

import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.pub.comp.CompoundIcon;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DictTreeCellRender extends DefaultTreeCellRenderer {
    Icon icons[]=new Icon[10];
    public DictTreeCellRender() {
        for(int i=0;i<10;i++){
            icons[i]=ExplorerIcons.getExplorerIcon("idea/gutter/"+i+".png");
        }
    }
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        JLabel label=(JLabel)super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
      try{
          DataSetTreeNode dst = (DataSetTreeNode) value;
          int js = dst.getIntJs();
          Icon icon = null;
          if (leaf)
              icon = getLeafIcon();
          else if (expanded)
              icon = getOpenIcon();
          else
              icon = getClosedIcon();
          if (icon != null)
              icon = new CompoundIcon(icons[js], icon);
          else
              icon = icons[js];
          label.setIcon(icon);
//          label.setText(dst.getMC());
      }catch(Exception e){}
      return label;
    }

}
