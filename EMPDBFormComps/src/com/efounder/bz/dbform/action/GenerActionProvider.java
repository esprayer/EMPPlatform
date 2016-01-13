package com.efounder.bz.dbform.action;

import javax.swing.Action;
import com.efounder.action.ActionGroup;
import com.efounder.bz.dbform.datamodel.DataComponent;

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
public class GenerActionProvider extends ActionAdapter {
  /**
   *
   */
  public GenerActionProvider() {
  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  public Action getAction(Object frame, Object[] nodeArray) {
    java.util.List list = this.getChildList();
    if ( list == null || list.size() == 0 ) return null;
    ActionGroup ag = new ActionGroup();
    DataComponent childDC = null;
    for(int i=0;i<list.size();i++) {
      childDC = (DataComponent)list.get(i);
      Action action = getChildAction(childDC,frame,nodeArray);
      if ( action != null ) ag.add(action);
    }
    return ag;
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
    ActionGroup ag = new ActionGroup();
    ag.add(action);
    return ag;
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
    ActionGroup ag = (ActionGroup)generActionGroup.getAction(this,frame,nodeArray);
    ActionGroup agg = null;
    if ( ag != null ) {
      agg = new ActionGroup();
      agg.add(ag);
    }
    return agg;
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
