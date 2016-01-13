package com.efounder.bz.dbform.action;

import com.efounder.bz.dbform.datamodel.DataComponentAdapter;
import com.efounder.bz.dbform.datamodel.DataComponent;
import javax.swing.Action;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.action.ActiveObjectAction;
import javax.swing.Icon;
import com.efounder.bz.dbform.datamodel.ActionComponent;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GenerAction extends ActionComponentAdapter {
  /**
   *
   */
  public GenerAction() {
  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  public Action getAction(ActionComponent actionProvider,Object frame, Object[] nodeArray) {
    Icon icon =ExplorerIcons.getExplorerIcon(this.actionIcon);
    if ( icon == null ) icon = ExplorerIcons.ICON_RUN;
    Action aoa = new ActiveObjectAction(this,nodeArray,"executeAction",this.actionText,'0',this.actionText,icon);
    return aoa;
  }
  /**
   *
   * @param childDC DataComponent
   * @return boolean
   */
  public boolean canAddChild(DataComponent childDC) {
    return false;
  }
}
