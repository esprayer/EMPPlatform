package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.ExplorerActionGroup;
import javax.swing.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ReOpenExplorerActionGroup extends ExplorerActionGroup {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  public ReOpenExplorerActionGroup(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }
  public ReOpenExplorerActionGroup(String s, char c, String s1, Icon icon, boolean flag)
  {
      super(s, c, s1, icon);
      super.putValue("Popup", new Boolean(flag));
  }


}
