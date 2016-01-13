package com.efounder.eai.ui.action;

import com.efounder.eai.ide.*;
import com.core.update.*;
import com.efounder.eai.*;
import javax.swing.*;


/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class UpdateCenterAction extends ExplorerAction {
  public UpdateCenterAction() {
  }
  public void actionPerformed(EnterpriseExplorer browser) {
    UpdateObject.execUpdateService(UpdateObject.EXEC_UPDATE_TYPE,EAI.PropertyList,browser.getMainWindow());
  }
  public UpdateCenterAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }

}
