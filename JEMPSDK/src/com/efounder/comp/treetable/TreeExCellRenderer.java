package com.efounder.comp.treetable;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import com.core.xml.*;


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
//    TreeModel TM = (DefaultTreeModel)tree.getModel();
    Icon icon = null;
    if ( value != null ) {
      if ( value instanceof StubObject ) {
//        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) value;
        Object uo = value;
        if (uo instanceof StubObject ) {
          StubObject usernode = (StubObject)uo;
            icon = usernode.getIcon();
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
