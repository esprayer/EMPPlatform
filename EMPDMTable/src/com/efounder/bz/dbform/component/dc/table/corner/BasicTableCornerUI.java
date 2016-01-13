package com.efounder.bz.dbform.component.dc.table.corner;

import javax.swing.plaf.basic.*;
import javax.swing.LookAndFeel;
import javax.swing.AbstractButton;
import javax.swing.UIManager;

public class BasicTableCornerUI extends BasicButtonUI {
  /**
   *
   */
  public BasicTableCornerUI() {
  }
  /**
   *
   * @param b AbstractButton
   */
  protected void installDefaults(AbstractButton b) {
//    super.installDefaults(b);
    LookAndFeel.installColorsAndFont(b, "TableHeader.background",
                                     "TableHeader.foreground", "TableHeader.font");
//    LookAndFeel.installBorder (b,"TableHeader.cellBorder");
//    b.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
  }
}
