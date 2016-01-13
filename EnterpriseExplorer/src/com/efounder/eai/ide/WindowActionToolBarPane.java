package com.efounder.eai.ide;

import com.efounder.action.ActionToolBarPane;
import com.efounder.action.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class WindowActionToolBarPane extends ActionToolBarPane {
  public WindowActionToolBarPane(Object obj,ActionGroup[] actionGroup) {
    super(obj);
    if ( actionGroup != null ) {
      for (int i = 0; i < actionGroup.length; i++)
        addGroup(actionGroup[i]);
      rebuild();
    }
  }

}
