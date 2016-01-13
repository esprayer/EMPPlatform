package com.efounder.service.tree.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.service.tree.node.*;

public class TreeExCellRenderer extends DefaultTreeCellRenderer {
  /**
   *
   */
  public TreeExCellRenderer() {
  }
  /**
   *
   * @param tree JTree
   * @param value Object
   * @param sel boolean
   * @param expanded boolean
   * @param leaf boolean
   * @param row int
   * @param hasFocus boolean
   * @return Component
   */
  public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                boolean sel,
                                                boolean expanded,
                                                boolean leaf, int row,
                                                boolean hasFocus) {
    DefaultTreeModel TM = (DefaultTreeModel)tree.getModel();
    Icon icon = null;
    if ( value != null ) {
      if ( value instanceof DefaultMutableTreeNode ) {
        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) value;
        Object uo = treenode;//treenode.getUserObject();
        if (uo instanceof ModelTreeNode) {
          ModelTreeNode usernode = (ModelTreeNode)uo;
          Icon openIcon = usernode.getOpenIcon();
          if ( expanded ) {
            icon = openIcon;
          } else {
             icon = usernode.getClosedIcon();
          }
          if ( icon == null )
            icon = usernode.getNodeIcon();
          if ( icon == null) icon = openIcon;
          if (icon == null) {
            if (leaf) {
              icon = (getLeafIcon());
            }
            else if (expanded) {
              icon = (getOpenIcon());
            }
            else {
              icon = (getClosedIcon());
            }
          }
        } else {
          return super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
        }
      }
    }
      String         stringValue = tree.convertValueToText(value, sel,
                                        expanded, leaf, row, hasFocus);

      this.hasFocus = hasFocus;
      setText(stringValue);
      if(sel)
          setForeground(getTextSelectionColor());
      else
          setForeground(getTextNonSelectionColor());
      // There needs to be a way to specify disabled icons.
      if (!tree.isEnabled()) {
          setEnabled(false);
          if (leaf) {
              setDisabledIcon(icon);
          } else if (expanded) {
              setDisabledIcon(icon);
          } else {
              setDisabledIcon(icon);
          }
      }
      else {
          setEnabled(true);
          if (leaf) {
              setIcon(icon);
          } else if (expanded) {
              setIcon(icon);
          } else {
              setIcon(icon);
          }
      }
      setComponentOrientation(tree.getComponentOrientation());

      selected = sel;

      return this;
  }
}
