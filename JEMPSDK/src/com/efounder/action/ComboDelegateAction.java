package com.efounder.action;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ComboDelegateAction extends DelegateAction {
  private boolean searchCombo;

  public boolean isSearchCombo()
  {
      return searchCombo;
  }
  public ListCellRenderer getListCellRenderer() {
    return null;
  }
  public boolean isEditable() {
    return true;
  }
  public ComboBoxModel getComboItems()
  {
      return new DefaultComboBoxModel();
  }

  public ComboDelegateAction(String s, String s1,Icon icon,int width)
  {
      super(s, 'd', s1,icon,width);
  }

  public ComboDelegateAction(String s, String s1,Icon icon, boolean flag,int width)
  {
      super(s, 'd', s1,icon,width);
      searchCombo = flag;
  }
  public void setSelectedItem(Object so) {
    boolean saveEnabled = this.isEnabled();
    try {
      this.setEnabled(false);
      this.getComboItems().setSelectedItem(so);
    } finally {
      this.setEnabled(saveEnabled);
    }
  }
  public Object getSelectedItem() {
    return this.getComboItems().getSelectedItem();
  }
}
