package com.efounder.eai.ide;

import javax.swing.*;
import com.efounder.eai.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class SystemExitAdapter {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ide.Res");
  public SystemExitAdapter() {
  }
  /**
   *
   * @return boolean
   */
  public static boolean closeAllWindow() {
    return EnterpriseExplorer.closeAllWindow();
  }
  /**
   *
   * @param appObject Object
   */
  public static boolean execute(Object appObject) {
    int Res = JOptionPane.showConfirmDialog(EAI.EA.getMainWindow(),res.getString("KEY")+" "+EAI.EA.getMainWindow().getTitle()+res.getString("KEY1"), res.getString("KEY2"),JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
    switch ( Res ) {
      case JOptionPane.YES_OPTION:
        return true;
      case JOptionPane.NO_OPTION:
        return false;
    }
    return false;
  }
}
