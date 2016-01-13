package com.core.tree;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckNode extends DefaultMutableTreeNode
{
  public static final int SINGLE_SELECTION = 0;
  public static final int DIG_IN_SELECTION = 4;
  protected int selectionMode;
  protected boolean isSelected;

  public CheckNode()
  {
    this(null);
  }

  public CheckNode(Object userObject) {
    this(userObject, true, true);
  }

  public CheckNode(Object userObject, boolean allowsChildren, boolean isSelected)
  {
    super(userObject, allowsChildren);

    this.isSelected = true;

    this.isSelected = isSelected;
    setSelectionMode(4);
  }

  public void setSelectionMode(int mode)
  {
    this.selectionMode = mode;
  }

  public int getSelectionMode() {
    return this.selectionMode;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;

    if ((this.selectionMode != 4) || (this.children == null))
      return;
    Enumeration enumer = this.children.elements();
    while (enumer.hasMoreElements()) {
      CheckNode node = (CheckNode)enumer.nextElement();
      node.setSelected(isSelected);
    }
  }

  public boolean isSelected()
  {
    return this.isSelected;
  }
}