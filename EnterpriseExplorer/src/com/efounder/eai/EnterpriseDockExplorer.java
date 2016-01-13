/*
 * @(#)JideDemos.java 2/14/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

package com.efounder.eai;

import com.efounder.pfc.window.*;
import com.efounder.eai.ide.*;
import com.jidesoft.docking.DockableHolder;
import javax.swing.JPanel;
import com.borland.jbcl.layout.VerticalFlowLayout;
import java.awt.BorderLayout;
import com.jidesoft.document.IDocumentPane;
import com.jidesoft.action.*;
import java.awt.Dimension;
import javax.swing.JToolBar;
import com.jidesoft.swing.JideLabel;
import com.jidesoft.swing.AutoCompletionComboBox;
import com.efounder.action.ActionToolBarPane;
import com.jidesoft.swing.*;
import com.jidesoft.plaf.basic.ThemePainter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import java.awt.Color;
import javax.swing.*;
import com.jidesoft.status.StatusBar;
import com.efounder.eai.ui.ExplorerWindowStatus;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author not attributable
 * @version 1.0
 */ 
public class EnterpriseDockExplorer extends EnterpriseExplorer2{
  static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.Res");
  /**
   *
   */
  public EnterpriseDockExplorer() {
	  super();
  }

  /**
   *
   * @param enterprise Object
   * @param args Object
   * @param o3 Object
   * @param o4 Object
   */
  public void InitMainWindow(Object enterprise,Object args,Object o3,Object o4) {

  }
  /**
   *
   * @throws Exception
   */
  protected void jbInit1() throws Exception {
    JideDemos.main(this);
    initLayout();

    initViewDevice();
    initMenuBar();
    initToolBar();
    initStatusBar();
  }
  protected void initStatusBar() {
    ExplorerWindowStatus ws = new ExplorerWindowStatus();
    WindowStatusBar = ws;
    // 设置状态行
    ws.setStatusBar(this.getStatusBar());
    ws.initStatus();
    System.getProperties().put("WindowStatus",ws);
  }
  public IView getContentView() {
    return JideDemos.getContentView();
  }
  /**
   *
   * @return StatusBar
   */
  public StatusBar getStatusBar() {
    return JideDemos.getStatusBar();
  }
  /**
   *
   * @return DockableHolder
   */
  public DockableHolder getDockableHolder() {
    return JideDemos.getDockableHolder();
  }
  /**
   *
   * @return DockableBarHolder
   */
  public DockableBarHolder getDockableBarHolder() {
    return JideDemos.getDockableBarHolder();
  }
  /**
   *
   * @return IDocumentPane
   */
  public IDocumentPane getDocumentPane() {
    return JideDemos.getDocumentPane();
  }
  /**
   *
   */
  protected void initLayout() {
    //
    glassPane   = new FixedGlassPane(this.getContentPane());
    glassPane.setOpaque(false);
    getRootPaneContainer().setGlassPane(glassPane);
  }
  /**
   *
   */
  protected void initMenuBar() {
    MenuBar = new ExplorerMenuBar(this,"MenuBar");
    MenuBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
    MenuBar.setInitIndex(0);
    MenuBar.setPaintBackground(false);
    MenuBar.setStretch(true);
    MenuBar.setFloatable(true);
    MenuBar.setHidable(false);
    getDockableBarHolder().getDockableBarManager().addDockableBar(MenuBar);
  }
  /**
   *
   */
  protected void initToolBar() {

    ToolBar1 = new ExplorerToolBarPane(this,0,"topBar");
    ToolBar[0] = ToolBar1;
    ToolBar1.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
    ToolBar1.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
    ToolBar1.setInitIndex(1);
    getDockableBarHolder().getDockableBarManager().addDockableBar(ToolBar1);


//    commandToolBar = new ExplorerToolBarPane(this,-1,"commandBar");
//    commandToolBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
//    commandToolBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
//    commandToolBar.setInitIndex(1);
//    getDockableBarHolder().getDockableBarManager().addDockableBar(commandToolBar);
//    initRunCommand(commandToolBar);


//    ToolBar2 = new ExplorerToolBarPane(this,1,"appBar");
//    ToolBar[1] = ToolBar2;
//    ToolBar2.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
//    ToolBar2.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
//    ToolBar2.setInitIndex(2);
//    getDockableBarHolder().getDockableBarManager().addDockableBar(ToolBar2);
//    getDockableBarHolder().getDockableBarManager().hideDockableBar(ToolBar2.getKey());
//
//
//    ToolBar3 = new ExplorerToolBarPane(this,2,"addBar");
//    ToolBar[2] = ToolBar3;
//    ToolBar3.setInitSide(DockableBarContext.DOCK_SIDE_SOUTH);
//    ToolBar3.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
//    ToolBar3.setInitIndex(3);
//    getDockableBarHolder().getDockableBarManager().addDockableBar(ToolBar3);
//    getDockableBarHolder().getDockableBarManager().hideDockableBar(ToolBar3.getKey());
  }
  private void initRunCommand(ActionToolBarPane commandToolBar) {

    JideSplitButton button = new JideSplitButton(res.getString("KEY1")+":");
    button.setForegroundOfState(ThemePainter.STATE_DEFAULT, Color.BLACK);
    button.setIcon(ExplorerIcons.getExplorerIcon("oicons/forward.png"));
        commandToolBar.add(button);
    JMenuItem mi = new JRadioButtonMenuItem();
    mi.setText(res.getString("KEY2"));
    mi.setIcon(ExplorerIcons.getExplorerIcon("oicons/forward.png"));
    button.add(mi);
    mi = new JRadioButtonMenuItem();
        mi.setText(res.getString("KEY3"));
        mi.setIcon(ExplorerIcons.getExplorerIcon("jrubik/script_go.png"));
    button.add(mi);
//    button.add(new AbstractAction("Sample Menu Item") {
//        public void actionPerformed(ActionEvent e) {
//        }
//      });


//    JideLabel label = new JideLabel();
//    label.setIcon(ExplorerIcons.getExplorerIcon("oicons/forward.png"));
//    label.setText("命令行：");
//    commandToolBar.add(label);

    AutoCompletionComboBox runCommand = new AutoCompletionComboBox(new String[]{"help"});
    runCommand.setPreferredSize(new Dimension(150,23));
    runCommand.setStrict(false);
    runCommand.setName("Ccommand line");
    runCommand.setToolTipText("命令行");
    commandToolBar.add(runCommand);
    runCommand.setSelectedIndex(-1);

    JideButton tnRun = new JideButton();
    tnRun.setIcon(ExplorerIcons.ICON_RUN);
    tnRun.setToolTipText("执行");
    commandToolBar.add(tnRun);

//    JideButton helpRun = new JideButton();
//    helpRun.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/help_16.png"));
//    helpRun.setToolTipText("帮助");
//    commandToolBar.add(helpRun);
//    commandToolBar.doLayout();
  }
  }
