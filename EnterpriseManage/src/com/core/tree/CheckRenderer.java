package com.core.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

public class CheckRenderer extends JPanel
  implements TreeCellRenderer
{
  protected JCheckBox check;
  protected TreeLabel label;

  public CheckRenderer()
  {
    setLayout(null);
    add(this.check = new JCheckBox());
    add(this.label = new TreeLabel());
    this.check.setBackground(UIManager.getColor("Tree.textBackground"));
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus)
  {
    String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);

    setEnabled(tree.isEnabled());
    this.check.setSelected(((CheckNode)value).isSelected());
    this.label.setFont(tree.getFont());
    this.label.setText(stringValue);
    this.label.setSelected(isSelected);
    this.label.setFocus(hasFocus);
    if (leaf)
      this.label.setIcon(UIManager.getIcon("Tree.leafIcon"));
    else if (expanded)
      this.label.setIcon(UIManager.getIcon("Tree.openIcon"));
    else {
      this.label.setIcon(UIManager.getIcon("Tree.closedIcon"));
    }
    return this;
  }

  public Dimension getPreferredSize() {
    Dimension d_check = this.check.getPreferredSize();
    Dimension d_label = this.label.getPreferredSize();
    return new Dimension(d_check.width + d_label.width, (d_check.height < d_label.height) ? d_label.height : d_check.height);
  }

  public void doLayout()
  {
    Dimension d_check = this.check.getPreferredSize();
    Dimension d_label = this.label.getPreferredSize();
    int y_check = 0;
    int y_label = 0;
    if (d_check.height < d_label.height)
      y_check = (d_label.height - d_check.height) / 2;
    else {
      y_label = (d_check.height - d_label.height) / 2;
    }
    this.check.setLocation(0, y_check);
    this.check.setBounds(0, y_check, d_check.width, d_check.height);
    this.label.setLocation(d_check.width, y_label);
    this.label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
  }

  public void setBackground(Color color)
  {
    if (color instanceof ColorUIResource)
      color = null;
    super.setBackground(color);
  }

  class TreeLabel extends JLabel
  {
    boolean isSelected;
    boolean hasFocus;

    public void setBackground(Color color)
    {
      if (color instanceof ColorUIResource)
        color = null;
      super.setBackground(color);
    }

    public void paint(Graphics g)
    {
      String str;
      if (((str = getText()) != null) && 
        (0 < str.length())) {
        if (this.isSelected)
          g.setColor(UIManager.getColor("Tree.selectionBackground"));
        else {
          g.setColor(UIManager.getColor("Tree.textBackground"));
        }
        Dimension d = getPreferredSize();
        int imageOffset = 0;
        Icon currentI = getIcon();
        if (currentI != null) {
          imageOffset = currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
        }
        g.fillRect(imageOffset, 0, d.width - 1 - imageOffset, d.height);
        if (this.hasFocus) {
          g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
          g.drawRect(imageOffset, 0, d.width - 1 - imageOffset, d.height - 1);
        }
      }

      super.paint(g);
    }

    public Dimension getPreferredSize() {
      Dimension retDimension = super.getPreferredSize();
      if (retDimension != null) {
        retDimension = new Dimension(retDimension.width + 3, retDimension.height);
      }

      return retDimension;
    }

    void setSelected(boolean isSelected) {
      this.isSelected = isSelected;
    }

    void setFocus(boolean hasFocus) {
      this.hasFocus = hasFocus;
    }
  }
}