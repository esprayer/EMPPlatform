package com.core.tree;

import java.util.Enumeration;
import java.util.Vector;

import esyt.framework.com.core.xml.StubObject;

public class StubObjectTreeNode extends CheckNode
{
  StubObject stubObject = null;

  public StubObjectTreeNode(StubObject SO) { super(SO);
    this.stubObject = SO;
  }

  public String toString()
  {
    return this.stubObject.toString();
  }

  public void setSelected(boolean isSelected)
  {
    this.stubObject.setSelected(isSelected);
    if (this.children != null) {
      Enumeration enumer = this.children.elements();
      while (enumer.hasMoreElements()) {
        CheckNode node = (CheckNode)enumer.nextElement();
        node.setSelected(isSelected);
      }
    }
    if (getParent() == null)
      return;
  }

  public boolean isSelected()
  {
    return this.stubObject.isSelected();
  }
}