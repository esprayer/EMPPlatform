package com.efounder.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import com.efounder.ui.Images;

// Referenced classes of package com.borland.primetime.actions:
//            ActionGroup, ActionCombo, ActionWidget, ComboDelegateAction,
//            Res, g, h, f,
//            SearchCombo, ActionPopupMenu, i, ActionButton,
//            UpdateAction

public class ActionToolBar {
  /**
   *
   */
  private static UpdateTimer updateTimber = new UpdateTimer(500);
  /**
   *
   * @param component JComponent
   */
  public static void removeAutoUpdateComponent(Component component) {
    if ( component == null ) return;
//    if ( "com.efounder.bz.dbform.action.ActionToolBar".equals(component.getClass().getName()) ) {
//      System.out.println(component);
//    }
    if ( component instanceof ActionWidget )
        updateTimber.actionButtonList.remove(component);
    Component[] components = null;
    if ( component instanceof Container )
      components = ((Container)component).getComponents();
    for(int i=0;components!=null&&i<components.length;i++) {
      removeAutoUpdateComponent(components[i]);
    }
  }
  /**
   *
   * @param component ActionWidget
   */
  public static void addAutoUpdateComponent(ActionWidget component) {
    updateTimber.addActionWidget(component);
  }
}
