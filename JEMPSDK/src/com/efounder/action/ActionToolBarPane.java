package com.efounder.action;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import com.jidesoft.action.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionToolBarPane
    extends CommandBar {
//  public static UpdateTimer updateTimber = new UpdateTimer(500);

  protected Border noBorder;
  protected Border vertBorder;
  protected Border horzBorder;
  private Dimension b;
  protected boolean rebuilding;
  protected VerticalFlowLayout vertFlowLayout;
  protected FlowLayout flowLayout;
  protected Object source;
  protected ActionPopupMenu pop;
//  protected HashMap toolbarHash;
  protected ArrayList groups;
  protected boolean horizontal;
  protected ActionGroup contextGroup;

//  public ActionToolBar[] getActionToolBars() {
//    Collection collection = toolbarHash.values();
//    ActionToolBar aactiontoolbar[] = new ActionToolBar[collection.size()];
//    collection.toArray(aactiontoolbar);
//    return aactiontoolbar;
//    return null;
//  }

//  protected ActionToolBar getActionToolBar(ActionGroup actiongroup) {
//    return null;
//    ActionToolBar actiontoolbar = (ActionToolBar) toolbarHash.get(actiongroup);
//    if (actiontoolbar != null) {
//      return actiontoolbar;
//    }
//    else {
//      ActionToolBar actiontoolbar1 = new ActionToolBar(source, actiongroup);
//      actiontoolbar1.setContextGroup(contextGroup);
//      toolbarHash.put(actiongroup, actiontoolbar1);
//      return actiontoolbar1;
//    }
//  }

  public void removeToolBarAll() {
    //��ö�����е����
//    removeComp(this);
    // ɾ�����е����
    ActionToolBar.removeAutoUpdateComponent(this);
    removeAll();
  }

  public void rebuild() {
    rebuilding = true;
    boolean flag = false;
    removeToolBarAll();
    for (int i = 0; groups!=null && i < groups.size(); i++) {
      if ( i > 0 && i < (groups.size() - 1))
        this.addSeparator();
      expandGroup((ActionGroup) groups.get(i));
//      ActionToolBar actiontoolbar = getActionToolBar( (ActionGroup) groups.get(
//          i));
//      actiontoolbar.setOpaque(false);
//      actiontoolbar.setHorizontal(horizontal);
//      actiontoolbar.setBorder(horizontal ? horzBorder : vertBorder);
//      if (actiontoolbar.isVisible()) {
//        add(actiontoolbar);
//        flag = true;
//      }
//      this.repaint();
    }

    rebuilding = false;
    rebuildComplete(flag);
    this.repaint();
  }

  protected void rebuildComplete(boolean flag) {
  }

  public boolean isToolBarVisible(ActionGroup actiongroup) {
//    ActionToolBar actiontoolbar = (ActionToolBar) toolbarHash.get(actiongroup);
//    return actiontoolbar != null && actiontoolbar.isVisible();
    return true;
  }

  public void setToolBarVisible(ActionGroup actiongroup, boolean flag) {
    if (groups.contains(actiongroup)) {
//      ActionToolBar actiontoolbar = getActionToolBar(actiongroup);
//      actiontoolbar.setVisible(flag);
      rebuild();
    }
  }

  public ActionGroup getContextGroup() {
    return contextGroup;
  }

  public void setContextGroup(ActionGroup actiongroup) {
    contextGroup = actiongroup;
    if (actiongroup != null) {
      pop = new ActionPopupMenu(source, actiongroup);
    }
    else {
      pop = null;
    }
//    for (int i = 0; i < groups.size(); i++) {
//      ActionToolBar actiontoolbar = (ActionToolBar) toolbarHash.get(groups.get(
//          i));
//      if (actiontoolbar != null) {
//        actiontoolbar.setContextGroup(contextGroup);
//      }
//    }

  }

  public boolean isHorizontal() {
    return horizontal;
  }

  public void setHorizontal(boolean flag) {
    horizontal = flag;
    setLayout( ( (java.awt.LayoutManager) (horizontal ?
                                           ( (java.awt.LayoutManager) (
        flowLayout)) : ( (java.awt.LayoutManager) (vertFlowLayout)))));
    rebuild();
  }

  public void removeAllGroup() {
    if (groups.size() == 0) {
      return;
    }
    this.groups.clear();
//    toolbarHash.clear();
    rebuild();
//    this.setVisible(false);
  }

  public void removeGroup(ActionGroup actiongroup) {
    groups.remove(actiongroup);
//    ActionToolBar actiontoolbar = (ActionToolBar) toolbarHash.remove(
//        actiongroup);
//    if (actiontoolbar != null) {
//      this.removeComp(actiontoolbar);
//    }
    rebuild();
//      if ( groups.size() == 0 ) this.setVisible(false);
  }

  public void addGroup(int i, ActionGroup actiongroup) {
    if (groups.contains(actiongroup)) {
      return;
    }
    else {
      groups.add(i, actiongroup);
      rebuild();
      return;
    }
  }

  public void addGroup(ActionGroup actiongroup) {
    if (groups.contains(actiongroup)) {
      return;
    }
    else {
      groups.add(actiongroup);
      rebuild();
      return;
    }
  }

  public ActionGroup[] getGroups() {
    ActionGroup aactiongroup[] = new ActionGroup[groups.size()];
    aactiongroup = (ActionGroup[]) groups.toArray(aactiongroup);
    return aactiongroup;
  }

  public ActionGroup getGroup(int i) {
    return (ActionGroup) groups.get(i);
  }

  public int getGroupCount() {
    return groups.size();
  }

  public Object getSource() {
    return source;
  }
  public ActionToolBarPane(Object obj) {

    this(obj == null ? "Toolbar" : obj.toString() + obj.hashCode(),obj);
  }
  public ActionToolBarPane(String id,Object obj) {

    super(id);
//    this.setOpaque(false);
      horizontal = true;
      groups = new ArrayList();
//      toolbarHash = new HashMap();
//      flowLayout = new FlowLayout(0, 2, 1);
//      vertFlowLayout = new VerticalFlowLayout(0, 1, 2, false, false);
//      horzBorder = new EdgeBorder(240);
//      vertBorder = new EdgeBorder(15);
//      noBorder = new c((ActionToolBarPane)this);
//      setLayout(flowLayout);
    source = obj;
//      addMouseListener(new d((ActionToolBarPane)this));
  }
  protected void expandGroup(ActionGroup actiongroup) {
    if ( actiongroup.aList == null ) return;
    Action aaction[] = new Action[actiongroup.aList.size()];
    aaction = (Action[])actiongroup.aList.toArray(aaction);
    boolean flag = false;
    for(int j = 0; j < aaction.length; j++) {
      if((aaction[j] instanceof ActionGroup) && !((ActionGroup)aaction[j]).isPopup()) {
        if(((ActionGroup)aaction[j]).getActionCount() <= 0)
          continue;
        if(j > 0 && !flag)
          this.addSeparator();
        expandGroup((ActionGroup)aaction[j]);
        continue;
      }
      // ����Ǵ���Action
      if ( aaction[j] instanceof DelegateAction ) {
        ActionWidget actionWidget = ((DelegateAction)aaction[j]).getActionComponent();
        if ( actionWidget != null ) {
          Component comp = actionWidget.getActionComponent();
          if ( comp != null ) {
            this.add(actionWidget.getActionComponent());// ������
            ActionToolBar.addAutoUpdateComponent(actionWidget);// ��ӵ�UpdateTimber��
          }
        }
      }
      if(aaction[j] instanceof SliderDelegateAction) {
        ActionSlider actionSlider = new ActionSlider(source, (SliderDelegateAction)aaction[j]);
        this.add(actionSlider);
      } else if(aaction[j] instanceof ComboDelegateAction) {
        if(((ComboDelegateAction)aaction[j]).isSearchCombo()) {
          SearchCombo searchcombo = new SearchCombo(source, (ComboDelegateAction)aaction[j]);
          this.add(searchcombo);
        } else {
          ActionCombo actioncombo = new ActionCombo(source, (ComboDelegateAction)aaction[j]);
          this.add(actioncombo);
        }
      } else if ( !(aaction[j] instanceof DelegateAction) ) {
        ActionButton actionbutton = new ActionButton(source==null?this:source, aaction[j], fixedButtonSize, useSmallIcon);//new ActionButton(source, aaction[j], fixedButtonSize, useSmallIcon);
        Integer integer = (Integer)aaction[j].getValue("ShowButtonText");
        if(integer != null) {
          actionbutton.setShowText(true);
          java.awt.Font font = UIManager.getFont("Button.font");
          FontMetrics fontmetrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
          String s = (String)aaction[j].getValue("ShortDescription");
          int k = (s == null ? 0 : fontmetrics.stringWidth(s)) + (showIcons ? 22 : 0) + ((aaction[j] instanceof ActionGroup) ? 20 : 0);
          actionbutton.setFixedWidth(s == null ? integer.intValue() : Math.min(k, integer.intValue()));
          actionbutton.setCentered(false);
        } else {
          actionbutton.setShowText(showText);
        }
        actionbutton.setShowIcon(showIcons);
        actionbutton.setGlowStyle(glowStyle);
        actionbutton.setShowRips(showRips);
//        actionbutton.addMouseListener(new h((ActionToolBar)this, actionbutton));
        this.add(actionbutton);
      }
      flag = false;
    }
    }
    Dimension fixedButtonSize = new Dimension(23, 23);
    protected boolean scrolling;
    protected int scrollIndex;
    protected boolean glowStyle;
    protected boolean showRips;
    protected boolean showBars;
    protected boolean showText;
    protected boolean showIcons = true;
    protected boolean useSmallIcon = true;
}
