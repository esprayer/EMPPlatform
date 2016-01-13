package com.efounder.view;

import javax.swing.Action;
import com.efounder.actions.ActionManager;
import com.efounder.action.ActionGroup;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class NodeActionUtil {
  protected NodeActionUtil() {
  }
  /**
   *
   * @param key Object
   * @param frame Object
   * @param node Object
   * @return ActionGroup
   */
  public static ActionGroup getContextActions(Object key,Object frame,Object node) {
    Object[] nodeArray = {node};
    return getContextActions(key,frame,nodeArray);
  }
  /**
   * 导出的通用方法
   * @param nodeArray Object[]
   * @return ActionGroup
   */
  public static ActionGroup getContextActions(Object key,Object frame,Object[] nodeArray) {
    // 将GROUP_POPUP清空
    ActionGroup ag = null;
    // 获取上下文的Action
    Action[] actions   = ActionManager.getContextActions(key,frame,nodeArray);
    if ( actions != null ) {
      ag = new ActionGroup();
      // 将Action增加到GROUP_POPUP
      for(int i=0;i<actions.length;i++) {
        ag.add(actions[i]);
      }
    }
    return ag;
  }
}
