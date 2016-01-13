package com.efounder.view;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.*;
import java.awt.*;
import javax.swing.tree.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JExplorerTreeCellRenderer extends DefaultTreeCellRenderer {
  private JTree tree;
  public JExplorerTreeCellRenderer() {
    setOpaque(false);
    setBackground(null);
    setBackgroundNonSelectionColor(null);
  }
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
        if (uo instanceof JNodeStub) {
          JNodeStub usernode = (JNodeStub)uo;
          if ( usernode.isOpen() ) {
            icon = usernode.getOpenIcon();
          } else {
            icon = usernode.getClosedIcon();
          }
          if ( icon == null )
            icon = usernode.getNodeIcon();
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

      this.tree = tree;
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
  private int getLabelStart() {
      Icon currentI = getIcon();
      if(currentI != null && getText() != null) {
          return currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
      }
      return 0;
    }
  public void paint(Graphics g) {
      Color bColor;

      if(selected) {
          bColor = getBackgroundSelectionColor();
      } else {
          bColor = getBackgroundNonSelectionColor();
          if(bColor == null)
              bColor = getBackground();
      }
      bColor = null;
      int imageOffset = -1;
      if(bColor != null) {
          Icon currentI = getIcon();

          imageOffset = getLabelStart();
          g.setColor(bColor);
          if(getComponentOrientation().isLeftToRight()) {
              g.fillRect(imageOffset, 0, getWidth() - 1 - imageOffset,
                         getHeight());
          } else {
              g.fillRect(0, 0, getWidth() - 1 - imageOffset,
                         getHeight());
          }
      }
      boolean drawsFocusBorderAroundIcon = true;
      if (hasFocus) {
          if (drawsFocusBorderAroundIcon) {
              imageOffset = 0;
          }
          else if (imageOffset == -1) {
              imageOffset = getLabelStart();
          }
          Color       bsColor = getBorderSelectionColor();

          if (bsColor != null) {
              g.setColor(bsColor);
              if(getComponentOrientation().isLeftToRight()) {
                  g.drawRect(imageOffset, 0, getWidth() - 1 - imageOffset,
                             getHeight() - 1);
              } else {
                  g.drawRect(0, 0, getWidth() - 1 - imageOffset,
                             getHeight() - 1);
              }
          }
      }

    }
}
