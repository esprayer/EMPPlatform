package com.efounder.action;

import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultListener implements ActionListener {
  private final Object a; /* synthetic field */
  private final JMenuItem b; /* synthetic field */
  private final Action c; /* synthetic field */
  public DefaultListener(Object obj, JMenuItem jmenuitem, Action action)
  {
    a = obj;
    b = jmenuitem;
    c = action;
  }
  public void actionPerformed(ActionEvent actionevent)
  {
      ActionEvent actionevent1 = new ActionEvent(a, 1001, b.getText());
      c.actionPerformed(actionevent1);
  }

}
