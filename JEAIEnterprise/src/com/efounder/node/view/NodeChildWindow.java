package com.efounder.node.view;

import javax.swing.JTabbedPane;
import com.efounder.comp.tabbedpane.JColorTabbedPane;
import javax.swing.JViewport;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import com.efounder.action.ActionGroup;
import com.efounder.action.ActiveObjectAction;
import java.util.HashMap;
import java.awt.Point;
import com.efounder.action.ActionPopupMenu;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.*;
import com.efounder.pfc.window.IWindow;
import com.jidesoft.swing.JideTabbedPane;
import java.awt.*;
//import com.jidesoft.swing.*;

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
public class NodeChildWindow extends NodeWindow implements MouseListener,ChangeListener {
  /**
   *
   */
  JideTabbedPane tpNodeView = new JideTabbedPane();//new CloseAndMaxTabbedPane(true,false,Color.orange,Color.black);
  protected void ofterInsertNodeView() {
    if ( tpNodeView.getTabCount() <= 1 ) {
      tpNodeView.setShowTabArea(false);
    } else {
        tpNodeView.setShowTabArea(true);
    }
  }
  /**
   *
   */
  public NodeChildWindow() {
    super();
    try {
      jbInit2();
    }catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @throws Exception
   */
  void jbInit2() throws Exception {
//    tpNodeView.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    tpNodeView.setBorder(null);
    tpNodeView.setContentBorderInsets(new Insets(0,0,0,0));
    tpNodeView.setTabPlacement(JTabbedPane.BOTTOM);
    this.add(tpNodeView,  BorderLayout.CENTER);
    initMouseEvent();
  }
  /**
   *
   * @return NodeChildWindow
   */
  public static NodeChildWindow getNodeChildWindow() {
    NodeChildWindow nw = new NodeChildWindow();
    return nw;
  }
  protected JSplitPane createJSplitPane() {
    JSplitPane sp = new JSplitPane();
    sp.setOrientation(JSplitPane.VERTICAL_SPLIT);
//    sp.setProportionalLayout(true);
    sp.setDividerSize(0);
    sp.setDividerLocation(0.80);
    sp.setBottomComponent(null);
    sp.setBorder(null);
    return sp;
  }
  protected void initJSplitPanel() {
    int tabCount = tpNodeView.getTabCount();
    for(int i=0;i<tabCount;i++) {
      JSplitPane sp = (JSplitPane)tpNodeView.getComponentAt(i);
      if ( sp.getTopComponent() != null && sp.getBottomComponent() != null ) {
        sp.setDividerLocation(0.80);
      }
    }
  }
  public void openBottomWindow(NodeViewer nodeViewer,IWindow cw,String title,String tips,Icon icon) {
    JSplitPane sp = findJSplitPane(nodeViewer);
    if ( sp == null ) return;
//    JideTabbedPane tabbedPane = (JideTabbedPane)sp.getBottomComponent();
    JTabbedPane tabbedPane = (JTabbedPane)sp.getBottomComponent();
    if ( tabbedPane == null ) {
      tabbedPane = new JideTabbedPane();//JideTabbedPane();
      tabbedPane.setTabPlacement(JTabbedPane.LEFT);
      tabbedPane.setBorder(null);
//      sp.add(tabbedPane,JideBoxLayout.FLEXIBLE);
//      sp.setPaneAt(tabbedPane,1);
      sp.setDividerSize(2);
      sp.setBottomComponent(tabbedPane);

    }
    tabbedPane.insertTab(title,icon,cw.getWindowComponent(),title,tabbedPane.getTabCount());
  }
  /**
   *
   * @param nv NodeViewer
   * @param index int
   */
  protected void insertNodeView(NodeViewer nv,int index) {
    JSplitPane sp = createJSplitPane();
//    sp.add(nv.getViewerComponent(),JideBoxLayout.FLEXIBLE);
//    sp.setPaneAt(nv.getViewerComponent(),0);
    sp.setTopComponent(nv.getViewerComponent());
    tpNodeView.insertTab(nv.getViewerTitle(),nv.getViewerIcon(),sp,nv.getViewerDescription(),index);
  }
  protected JSplitPane findJSplitPane(NodeViewer nv) {
    int tabCount = tpNodeView.getTabCount();
    for(int i=0;i<tabCount;i++) {
      JSplitPane sp = (JSplitPane)tpNodeView.getComponentAt(i);
      if ( sp.getTopComponent() == nv.getViewerComponent() ) {
        return sp;
      }
    }
    return null;
  }
  /**
   *
   * @param nv NodeViewer
   */
  protected void selectNodeView(NodeViewer nv) {
//    tpNodeView.setSelectedComponent(nv.getViewerComponent());
    tpNodeView.setSelectedComponent(findJSplitPane(nv));
  }
  /**
   *
   * @return NodeViewer
   */
  public NodeViewer getSelectedNodeView() {
    JSplitPane sp = (JSplitPane)tpNodeView.getSelectedComponent();
    if ( sp == null )
      return null;
//    return (NodeViewer)sp.getPaneAt(0);
    return (NodeViewer)sp.getTopComponent();
//    return (NodeViewer)tpNodeView.getSelectedComponent();
  }
  /**
   *
   * @return Object
   */
  protected Object getStateChanged() {
    return tpNodeView;
  }
  /**
   *
   * @return JTabbedPane
   */
  public JComponent getViewPane() {
    return tpNodeView;
  }
  /**
   *
   */
  void initEvent() {
    this.tpNodeView.addChangeListener(this);
  }
  /**
   *
   * @param l ChangeListener
   */
  public void addChangeListener(ChangeListener l) {
    tpNodeView.addChangeListener(l);
  }
  /**
   *
   * @param l ChangeListener
   */
  public void removeChangeListener(ChangeListener l) {
    tpNodeView.removeChangeListener(l);
  }
  /**
   *
   */
  private void initMouseEvent() {
    String javaVersion = System.getProperty("java.runtime.version");
    if ( !javaVersion.startsWith("1.4") ) {
      tpNodeView.addMouseListener(this);
    } else {
      Component[] comps = tpNodeView.getComponents();
      if (comps.length > 0 && comps[0] instanceof JViewport) {
        JViewport vp = (JViewport) comps[0];
        vp.getComponent(0).addMouseListener(this);
      }
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {

  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseExited(MouseEvent e) {
  }
  /**
   *
   * @param event MouseEvent
   */
  public void mousePressed(MouseEvent event) {

  }
  /**
   *
   * @param event MouseEvent
   */
  public void mouseReleased(MouseEvent event) {
    // �����Ҫ����˵�
    if ( ( event.getModifiers() & event.BUTTON3_MASK ) != 0 ) {
      ActionGroup ag = getTabContextActions(event.getComponent());
      showPopupmenu(event.getPoint(),event.getComponent(),ag);
    }
  }
  /**
   *
   * @param tabPane JTabbedPane
   * @return ActionGroup
   */
  private ActionGroup getTabContextActions(Component tabPane) {
    int count = tpNodeView.getTabCount();java.util.Map classMap = null;
    ActiveObjectAction tabAction = null; ActionGroup ag = null,allag = null;;
    for(int i=0;i<count;i++) {
      if ( allag == null ) allag = new ActionGroup();
      if ( classMap == null ) classMap = new HashMap();
      JSplitPane sp = (JSplitPane)tpNodeView.getComponentAt(i);
      Component comp = sp.getTopComponent();//tpNodeView.getComponentAt(i);
      ag = (ActionGroup)classMap.get(comp.getClass().getName());
      if ( ag == null ) {
        ag = new ActionGroup();
        allag.add(ag);
        classMap.put(comp.getClass().getName(),ag);
      }
      tabAction = new ActiveObjectAction(this,new Integer(i),"activeNodeView",tpNodeView.getTitleAt(i),'0',tpNodeView.getToolTipTextAt(i),tpNodeView.getIconAt(i));
      ag.add(tabAction);
    }
    return allag;
  }
  /**
   *
   * @param X int
   * @param Y int
   */
  protected void showPopupmenu(Point p,Component invoker,ActionGroup popupMenu) {
    if ( popupMenu != null ) {
      ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker, popupMenu, true);
      actionPopupMenu.show(invoker, p.x,p.y);
    }
  }
  /**
   *
   * @param actionObject Object
   * @param dataObject Object
   * @param action Action
   * @param actionevent ActionEvent
   */
  public void activeNodeView(Object actionObject,Object dataObject,Action action,ActionEvent actionevent) {
    if ( dataObject instanceof Integer ) {
      this.tpNodeView.setSelectedIndex(((Integer)dataObject).intValue());
    }
  }
  /**
   *
   */
  protected void autoNodeViewTitle() {
//    Component selectComp = tpNodeView.getSelectedComponent();
//    JSplitPane sp = (JSplitPane)tpNodeView.getSelectedComponent();
    JSplitPane sp = (JSplitPane)tpNodeView.getSelectedComponent();
    Component selectComp = sp.getTopComponent();//tpNodeView.getComponentAt(i);
//    Component selectComp = sp.getPaneAt(0);//tpNodeView.getComponentAt(i);
    int index = tpNodeView.getSelectedIndex();
    if ( selectComp instanceof NodeViewer ) {
      NodeViewer selectView = (NodeViewer)selectComp;
      if ( selectView.isAutoTitle() ) {
        this.setTitle(tpNodeView.getTitleAt(index));
        this.setIcon(tpNodeView.getIconAt(index));
      }
    }
  }
  /**
   *
   * @param nodeViewer NodeViewer
   * @return int
   */
  public int indexOfNodeView(NodeViewer nodeViewer) {
    return tpNodeView.indexOfComponent(this.findJSplitPane(nodeViewer));
//    return tpNodeView.indexOfComponent(nodeViewer.getViewerComponent());
  }
  /**
   *
   * @param index int
   * @param icon Icon
   */
  public void setNodeViewerIconAt(int index,Icon icon) {
    tpNodeView.setIconAt(index,icon);
  }
  /**
   *
   * @param index int
   * @param v String
   */
  public void setNodeViewerTitleAt(int index,String v) {
    tpNodeView.setToolTipTextAt(index,v);
  }
  /**
   *
   * @param index int
   * @param v String
   */
  public void setNodeViewerToolTipAt(int index,String v) {
    tpNodeView.setTitleAt(index,v);
  }
  /**
   *
   * @param e ChangeEvent
   */
  public void stateChanged(ChangeEvent e) {
    if ( e.getSource() == this.getStateChanged() ) {
      activeNodeView();
    }
  }
}
