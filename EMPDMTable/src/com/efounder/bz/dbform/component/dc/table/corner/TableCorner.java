package com.efounder.bz.dbform.component.dc.table.corner;

import javax.swing.*;
import com.borland.dbswing.plaf.basic.BasicJdbTableRowHeaderUI;
import com.efounder.bz.dbform.component.dc.table.*;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TableCorner extends JButton {
  /**
   *
   */
  protected String cornerKey = null;
  /**
   *
   * @return String
   */
  public String getCornerKey() {
    return cornerKey;
  }
  /**
   *
   * @param cornerKey String
   */
  public void setCornerKey(String cornerKey) {
    this.cornerKey = cornerKey;
  }
  protected DictTable dmTable = null;
  /**
   *
   */
  public TableCorner(DictTable dmTable) {
    this.dmTable = dmTable;
    this.setText(" ");
    updateUI();
  }

  /**
   * <p>Updates the UI.</p>
   */
  public void updateUI() {
    setUI(BasicTableCornerUI.createUI(this));
  }
}
