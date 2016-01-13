package com.efounder.service.start;

import com.efounder.node.view.*;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import com.efounder.pfc.window.IWindow;
import com.efounder.node.Context;
import com.efounder.model.biz.BIZContext;
import com.efounder.view.ComponentView;
import com.efounder.view.CompViewManager;
import java.awt.*;
import javax.swing.*;
import com.efounder.eai.ide.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseListener;
import com.core.xml.*;

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
public class EAIStartView extends NodeViewerAdapter implements ChangeListener{
  public EAIStartView() {
    try {
      setNodeViewID("EAIStartView");
      super.initSystemLayout();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @param e ChangeEvent
   */
  public void stateChanged(ChangeEvent e) {
    if ( e.getSource() instanceof  JTabbedPane ) {
      processTabPane((JTabbedPane)e.getSource());
    }
  }
  /**
   *
   * @param tabPane JTabbedPane
   */
  protected void processTabPane(JTabbedPane tabPane) {
    Object obj = tabMap.get(tabPane);
    if ( obj != null ) {
      JBOFClass.CallObjectMethod(obj, "stopCompView");
    }
    obj = tabPane.getSelectedComponent();
    tabMap.put(tabPane,obj);
    if ( obj != null ) {
      JBOFClass.CallObjectMethod(obj, "startCompView");
    }
  }
  private void jbInit() throws Exception {
    spContent1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    this.add(spContent1, java.awt.BorderLayout.CENTER);
    spContent1.add(spContent2, JSplitPane.BOTTOM);
    spContent2.add(tabLeft, JSplitPane.LEFT);
    spContent2.add(tabRight, JSplitPane.RIGHT);
    spContent1.add(tabTop, JSplitPane.TOP);
    tabTop.addChangeListener(this);
    tabLeft.addChangeListener(this);
    tabRight.addChangeListener(this);
  }

  JSplitPane spContent1 = new JSplitPane();
  JSplitPane spContent2 = new JSplitPane();
  JTabbedPane tabTop = new JTabbedPane();
  JTabbedPane tabLeft = new JTabbedPane();
  JTabbedPane tabRight = new JTabbedPane();
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @param nodeWindow IWindow
   * @throws Exception
   */
  public void initNodeViewer2(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {
    spContent1.setDividerLocation(0.5);
    spContent2.setDividerLocation(0.5);
  }
  protected java.util.Map tabMap = new java.util.HashMap();
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @param nodeWindow IWindow
   * @throws Exception
   */
  public void initNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {
    initTabView(tabTop,"startTopViews");
    tabTop.setTabPlacement(JTabbedPane.BOTTOM);
    initTabView(tabLeft,"startLeftViews");
    tabLeft.setTabPlacement(JTabbedPane.BOTTOM);
    initTabView(tabRight,"startRightViews");
    tabRight.setTabPlacement(JTabbedPane.BOTTOM);
  }
  /**
   *
   * @param tabPane JTabbedPane
   * @param compViewID String
   */
  protected void initTabView(JTabbedPane tabPane,String compViewID) {
    java.util.List compViewList = CompViewManager.getComponentView(compViewID,this,nodeChildWindow);
    ComponentView cv = null;int index = 0;
    for(int i=0;i<compViewList.size();i++) {
      cv = (ComponentView)compViewList.get(i);
      if ( cv == null ) continue;
      JComponent comp = (JComponent)cv.getComponent();
      if ( comp == null ) continue;
      String title = (String)cv.getValue("viewTitle","");
      Icon icon = null;
      String sicon = (String)cv.getValue("viewIcon",null);
      icon = ExplorerIcons.getExplorerIcon(sicon);
      String viewTips = (String)cv.getValue("viewTips","");
      tabPane.insertTab(title,icon,comp,viewTips,index++);
    }
    if ( tabPane.getSelectedComponent() != null ) {
      tabMap.put(tabPane,tabPane.getSelectedComponent());
    }
  }
}
