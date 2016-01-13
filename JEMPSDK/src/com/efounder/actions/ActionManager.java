package com.efounder.actions;

import java.util.*;
import javax.swing.*;
import org.openide.*;
import com.efounder.action.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionManager {
  private static Hashtable ProviderTable = new Hashtable();
  public ActionManager() {
  }
  /**
   *
   * @param Key Object
   * @param Provider ContextActionProvider
   */
  public static void registerContextActionProvider(Object Key,ContextActionProvider Provider) {
    registerContextActionProvider(Key,Provider,-1);
  }
  public static void registerContextActionProvider(Object Key,ContextActionProvider Provider,int order) {
    Vector ProviderList = null;
    ProviderList = (Vector)ProviderTable.get(Key);
    if ( ProviderList == null ) {
      ProviderList = new Vector();
      ProviderTable.put(Key,ProviderList);
    }
    if ( ProviderList.indexOf(Provider) == -1 ) {// 如果没有注册过
      if ( order == -1 || order > ProviderList.size() ) order = ProviderList.size();
      ProviderList.add(order,Provider);
    }
  }
  /**
   *
   * @param Key Object
   * @return ContextActionProvider[]
   */
  public static ContextActionProvider[] getContextActionProviders(Object Key) {
    Vector ProviderList = null;ContextActionProvider[] cap=null;
    ProviderList = (Vector)ProviderTable.get(Key);
    if ( ProviderList != null ) {
      cap = new ContextActionProvider[ProviderList.size()];
      for(int i=0;i<ProviderList.size();i++) {
        cap[i] = (ContextActionProvider)ProviderList.get(i);
      }
    }
    return cap;
  }
  public static Action[] getContextActions(Object Key,Object frame, Object node) {
    Object[] na = {node};
    return getContextActions(Key,frame,na);
  }
  /**
   *
   * @param Key Object
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action[]
   */
  public static Action[] getContextActions(Object Key,Object frame, Object[] nodeArray) {
    Action[] as = null;ContextActionProvider[] cap=null;Action action;
    if ( Key == null ) return null;
      Component comp = UpdateAction.findWindow(frame);
    try {
      WaitingManager.getDefault().showWait(comp);
      cap = getContextActionProviders(Key);
      Vector ActionList = null;
      if (cap != null) {
        ActionList = new Vector();
        for (int i = 0; i < cap.length; i++) {
          try {
            action = cap[i].getContextAction(frame, nodeArray);
          } catch ( Exception e ) {
            e.printStackTrace();
            action = null;
          }
          if (action != null)
            ActionList.add(action);
        }
      }
      if (ActionList != null) {
        as = new Action[ActionList.size()];
        for (int i = 0; i < ActionList.size(); i++) {
          as[i] = (Action) ActionList.get(i);
        }
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      WaitingManager.getDefault().endShowWait(comp);
    }
    return as;
  }
  /**
   *
   * @param Key Object
   * @param Provider ContextActionProvider
   * @return ContextActionProvider
   */
  public static ContextActionProvider unregisterContextActionProvider(Object Key,ContextActionProvider Provider) {
    ContextActionProvider mProvider = Provider;Vector ProviderList = null;
    ProviderList = (Vector)ProviderTable.get(Key);
    if ( ProviderList != null ) {
      ProviderList.remove(Provider);
    }
    return mProvider;
  }
}
