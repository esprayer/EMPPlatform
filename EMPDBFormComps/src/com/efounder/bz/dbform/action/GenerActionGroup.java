package com.efounder.bz.dbform.action;

import com.efounder.bz.dbform.datamodel.*;
import javax.swing.Action;
import com.efounder.action.ActionGroup;
import com.efounder.eai.ide.ExplorerIcons;
import javax.swing.Icon;

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
public class GenerActionGroup extends ActionComponentAdapter {
  /**
   *
   */
  public GenerActionGroup() {
  }
  /**
   *
   */
  protected boolean popup = false;
  /**
   *
   * @return boolean
   */
  public boolean isPopup() {
    return popup;
  }
  /**
   *
   * @param p boolean
   */
  public void setPopup(boolean p) {
    popup = p;
  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  public Action getAction(ActionComponent actionProvider,Object frame, Object[] nodeArray) {
    return getChildAction(frame,nodeArray);
  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  public Action getChildAction(Object frame, Object[] nodeArray) {
    java.util.List list = this.getChildList();
    if ( list == null || list.size() == 0 ) return null;
    Icon icon = ExplorerIcons.getExplorerIcon(this.actionIcon);
    if ( icon == null ) icon = ExplorerIcons.ICON_ARRAY;
    ActionGroup actionGroup = null;
    if ( popup )
      actionGroup = new ActionGroup(this.actionText,'0',this.actionText,icon,popup);
    else
      actionGroup = new ActionGroup();
    DataComponent childDC = null;
    for(int i=0;i<list.size();i++) {
      childDC = (DataComponent)list.get(i);
      Action action = getChildAction(childDC,frame,nodeArray);
      if ( action != null ) actionGroup.add(action);
    }
    if ( actionGroup.getActionCount() > 0 )
      return actionGroup;
    else
      return null;
  }
  /**
   *
   * @param childDC DataComponent
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  protected Action getChildAction(DataComponent childDC,Object frame, Object[] nodeArray) {
    if ( childDC instanceof GenerAction ) {
      return getGenerAction(((GenerAction)childDC),frame,nodeArray);
    }
    if ( childDC instanceof GenerActionGroup ) {
      return getGenerActionGroup(((GenerActionGroup)childDC),frame,nodeArray);
    }
    return null;
  }
  /**
   *
   * @param generAction GenerAction
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  protected Action getGenerAction(GenerAction generAction,Object frame, Object[] nodeArray) {
    if ( !generAction.canAccept(this,frame,nodeArray) ) return null;
    Action action = generAction.getAction(this,frame,nodeArray);
//    ActionGroup ag = new ActionGroup();
//    ag.add(action);
    return action;
  }
  /**
   *
   * @param generActionGroup GenerActionGroup
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  protected Action getGenerActionGroup(GenerActionGroup generActionGroup,Object frame, Object[] nodeArray) {
    if ( !generActionGroup.canAccept(this,frame,nodeArray) ) return null;
    return generActionGroup.getAction(this,frame,nodeArray);
  }
  /**
   *
   * @param childDC DataComponent
   * @return boolean
   */
  public boolean canAddChild(DataComponent childDC) {
    // 只允许增加GenerAction和GenerActionGroup
    return ( (childDC instanceof GenerAction) || (childDC instanceof GenerActionGroup) );
  }
}
