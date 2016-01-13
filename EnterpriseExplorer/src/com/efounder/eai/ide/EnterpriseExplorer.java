package com.efounder.eai.ide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.action.ActionGroup;
import com.efounder.action.ActionToolBarPane;
import com.efounder.eai.EnterpriseFounder;
import com.efounder.pfc.window.IView;
import com.efounder.pfc.window.IViewDevice;
import com.efounder.pfc.window.IWindow;
import com.efounder.pfc.window.IWindowStatus;
import com.efounder.pfc.window.JMainWindow;
import com.efounder.service.comm.CommListener;
import com.efounder.service.comm.CommManager;
import com.jidesoft.action.DockableBarHolder;
import com.jidesoft.docking.DockableHolder;
import com.jidesoft.document.IDocumentPane;
import com.jidesoft.status.StatusBar;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class EnterpriseExplorer extends JMainWindow implements ViewContainer,CommListener {
//public class EnterpriseExplorer {
  BorderLayout borderLayout1 = null;
  JPanel pnTOPSet = null;
  JPanel pnTOP = null;
  JPanel pnBOTTOM = null;
  protected ActionToolBarPane ToolBar[] = new ActionToolBarPane[3];
  protected ActionToolBarPane commandToolBar = null;
  protected ActionToolBarPane ToolBar1 = null;
  protected ActionToolBarPane ToolBar2 = null;
  protected ActionToolBarPane ToolBar3 = null;
  protected ExplorerMenuBar     MenuBar = null;
  protected JPanel   pnMenubar = null;
//  protected JToolBar tbMenubar = null;
//  protected JToolBar tbToolbar1 = null;
//  protected JToolBar tbToolbar2 = null;
//  protected JToolBar tbToolbar3 = null;
//  protected JToolBar tbToolBar[] = new JToolBar[3];
  static public EnterpriseExplorer  Explorer = null;
  public IDocumentPane getDocumentPane() {
    return null;
  }
  public DockableHolder getDockableHolder() {
    return null;
  }
  public DockableBarHolder getDockableBarHolder() {
    return null;
  }
  /**
   *
   * @return StatusBar
   */
  public StatusBar getStatusBar() {
    return null;
  }
  /**
   *
   * @return ExplorerMenuBar
   */
  public ExplorerMenuBar getExplorerMenuBar() {
    return MenuBar;
  }
  /**
   *
   * @return ActionToolBarPane[]
   */
  public ActionToolBarPane[] getToolBars() {
    return ToolBar;
  }
  /**
   *
   */
  WindowAdapter WAdapter        = null;
  /**
   *
   */
  static public IWindowStatus WindowStatusBar = null;

  /**
   *
   */
  Container contentPane    = null;
  protected FixedGlassPane glassPane = null;


  static public IViewDevice ViewDevice = null; // 视图设备
  static public IView  TopView      = null; // 管理顶层视图
  static public IView  ExplorerView = null; // 资源管理视图
  static public IView  StructView   = null; // 节点结构视图
  static public IView  MessageView  = null; // 系统消息视图
  static public IView  ContentView  = null; // 系统内容视图
  static public IView  PropertyView = null; // 系统属性视图

  static public IView  FloatView    = null; // 浮动内容窗口
  static public IView  DialogView   = null; // 对话框内容窗口
  /**
   *
   * @return IView
   */
  public static IView getDialogView() {
    return getDialogView(null);
  }
  /**
   *
   * @return IView
   */

  public static IView getDialogView(Object node) {
    IView dialogView = ViewBuilder.buildDialogView(node);
    return dialogView;
  }

  public void setViewDevice(IViewDevice vd) {
    ViewDevice = vd;
  }
  public void setTopView(IView v) {
    TopView = v;
  }
  public void setExplorerView(IView v) {
    ExplorerView = v;
  }
  public void setStructView(IView v) {
    StructView = v;
  }
  public void setMessageView(IView v) {
    MessageView = v;
  }
  public void setContentView(IView v) {
    ContentView = v;
  }
  public void setPropertyView(IView v) {
    PropertyView = v;
  }
  public void setFloatView(IView v) {
    FloatView = v;
  }
  public void setDialogView(IView v) {
    DialogView = v;
  }
  //
  private static ArrayList   MenuGroupArray    = new ArrayList();
  private static ArrayList[] ToolbarGroupArray = new ArrayList[3];
  static {
    ToolbarGroupArray[0] = new ArrayList();
    ToolbarGroupArray[1] = new ArrayList();
    ToolbarGroupArray[2] = new ArrayList();
  }
  public EnterpriseExplorer() {
    try {
      Explorer = this;
      jbInit1();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  protected void jbInit1() throws Exception {
    initLayout();
    initGridBag();
    initMenuBar();
    initToolBar();
    initViewClass();
    initWindowEvent();
    initViewDevice();
    initStatusBar();
    initImageLabel();
  }
  void initViewClass() {
    try {
      WAdapter = ViewBuilder.builderWindowAdapter();
      this.addWindowListener(WAdapter);
      WindowStatusBar = ViewBuilder.builderWindowStatus();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  void initWindowEvent() {

  }
  /**
   *
   */
  protected static ActionGroup topFrameActionGroup = new ActionGroup();
  /**
   *
   * @return ActionGroup
   */
  public static ActionGroup getTopFrameActionGroup() {
    return topFrameActionGroup;
  }
  /**
   *
   * @param ag ActionGroup
   */
  public static void insertTopFrameActionGroup(ActionGroup ag) {
    topFrameActionGroup.add(ag);
    Explorer.ToolBar[0].addGroup(topFrameActionGroup);
  }
  /**
   *
   */
  public static void removeTopFrameActionGroup() {
    if ( Explorer != null && topFrameActionGroup != null && topFrameActionGroup.getActionCount() > 0 ) {
      Explorer.ToolBar[0].removeGroup(topFrameActionGroup);
      topFrameActionGroup.removeAll();
    }
  }
  /**
   *
   */
  protected RootPaneContainer rootPaneContainer = null;

  /**
   *
   * @return JMainWindow
   */
  public JMainWindow getMainWindow() {
    return this;
  }
  /**
   *
   * @return Container
   */
  public RootPaneContainer getRootPaneContainer() {
    rootPaneContainer = (RootPaneContainer)System.getProperties().get("RootPaneContainer");
    if ( rootPaneContainer != null ) return rootPaneContainer;
    return this;
  }
//
//  public void validate() {
//    getRootPaneContainer().getContentPane().validate();
//  }
  /**
   *
   */
  protected void initViewDevice() {
    try {
      initViewClass();
      ViewBuilder.initViewClass();
      // 锟斤拷锟斤拷ViewBuilder
      ViewBuilder.builderRootView(this.getRootPaneContainer().getContentPane(),this);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @param v boolean
   */
  public void setDisableEvent(boolean v) {
    glassPane.setEnabled(v);
    glassPane.setVisible(v);
  }
  /**
   *
   */
  void initLayout() {
    //
    contentPane = getRootPaneContainer().getContentPane();

    //
    glassPane   = new FixedGlassPane(contentPane);
    glassPane.setOpaque(false);
    getRootPaneContainer().setGlassPane(glassPane);
//    glassPane.setVisible(true);
//    glassPane.setNeedToRedispatch(true);

    borderLayout1 = new BorderLayout();
    pnTOP = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    pnTOP.setLayout(verticalFlowLayout1);

    pnBOTTOM = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    pnBOTTOM.setLayout(borderLayout2);

    this.getRootPaneContainer().getContentPane().setLayout(borderLayout1);
    this.getRootPaneContainer().getContentPane().add(pnTOP, BorderLayout.NORTH);
    this.getRootPaneContainer().getContentPane().add(pnBOTTOM, BorderLayout.SOUTH);
  }
  void initGridBag() {
//    ToolBarGrid   = new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0);
//    WorkSpaceGrid = new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0);
//    StatusGrid    = new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0);
  }
  protected void initStatusBar() {
//    String CustomClass = JEnterpriseResource.GetString(EAI.Product,"MainWindowBottom",null,null);
    try {
//      WindowStatusBar = (IWindowStatus) Class.forName(CustomClass).newInstance();
//      pnBOTTOM.add(WindowStatusBar.getStatusComp(), BorderLayout.SOUTH);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  void initMenuBar() {
    MenuBar = new ExplorerMenuBar(this,"Menu Bar");
    MenuBar.setBorder(null);
//    tbMenubar = new JToolBar();
//    tbMenubar.add(this.MenuBar);
//    tbMenubar.setFloatable(false);
    pnMenubar = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    pnMenubar.setLayout(borderLayout1);
//    pnMenubar.add(tbMenubar,BorderLayout.CENTER);
    pnMenubar.add(MenuBar,BorderLayout.CENTER);
  }
  public void addExplorerMenu(JMenu menu,int index){
    MenuBar.add(menu,index);
  }
  public void removeExplorerMenu(JMenu menu) {
    MenuBar.remove(menu);
  }

  JPanel rightPanel = null;
  JPanel pluginPanel = null;
  JLabel imageLable = null;
//  Icon   fixImage = ExplorerIcons.getExplorerIcon("Earth-09-june.png");
//  Icon   anmImage = ExplorerIcons.getExplorerIcon("Earth-09-june.gif");
  Icon   fixImage = ExplorerIcons.getExplorerIcon("done.gif");
  Icon   anmImage = ExplorerIcons.getExplorerIcon("sloading.gif");
  /**
   *
   */
  void initImageLabel() {
    // 锟斤拷锟斤拷rightPanel
    rightPanel = new JPanel();
    BorderLayout borderLayout = new BorderLayout();
    rightPanel.setLayout(borderLayout);
    rightPanel.setSize(120,16);
    pnMenubar.add(rightPanel,BorderLayout.EAST);
    // 锟斤拷锟斤拷pluginPanel
//    pluginPanel = new JPanel();
//    // 锟斤拷锟斤拷pluginPanel Layout
//    FlowLayout flowLayout = new FlowLayout();
//    flowLayout.setHgap(0);
//    flowLayout.setVgap(0);
//    pluginPanel.setLayout(flowLayout);
//    rightPanel.add(pluginPanel,BorderLayout.CENTER);

    imageLable = new JLabel();
    imageLable.setIcon(fixImage);
    imageLable.setBorder(new javax.swing.border.LineBorder(Color.lightGray));
    rightPanel.add(imageLable,BorderLayout.EAST);
    CommManager.getDefault().addChangeListener(this);
    //
    searchBar = new JComboBox();
    searchBar.setPreferredSize(new Dimension(100,16));
    rightPanel.setSize(120,16);
    rightPanel.add(searchBar,BorderLayout.CENTER);
//    initWorkSpaceList();
  }
  JComboBox searchBar = null;
  protected JComponent workSpaceBox = null;
  void initWorkSpaceList() {
    workSpaceBox = new JButton();
//    workSpaceBox.setSize(80,imageLable.getHeight());
    rightPanel.add(workSpaceBox,BorderLayout.CENTER);
  }
  void startImageLabel() {
    imageLable.setIcon(anmImage);
  }
  void stopImageLabel() {
    imageLable.setIcon(fixImage);
  }
  protected void initToolBar() {
    Dimension dim = new Dimension(0,18);
//    tbToolbar1= new JToolBar();
//    tbToolbar1.setSize(dim);
//    tbToolbar1.setBorder(null);
//    tbToolBar[0] = tbToolbar1;
//    tbToolbar2= new JToolBar();
//    tbToolbar2.setSize(dim);
//    tbToolbar2.setBorder(null);
//    tbToolBar[1] = tbToolbar2;
//    tbToolbar3= new JToolBar();
//    tbToolbar3.setSize(dim);
//    tbToolbar3.setBorder(null);
//    tbToolBar[2] = tbToolbar3;

    ToolBar1 = new ExplorerToolBarPane(this,0,"topBar");
    ToolBar1.setSize(dim);
    ToolBar1.setBorder(null);
    ToolBar[0] = ToolBar1;
    ToolBar2 = new ExplorerToolBarPane(this,1,"appBar");
    ToolBar2.setSize(dim);
    ToolBar[1] = ToolBar2;
    ToolBar3 = new ExplorerToolBarPane(this,2,"addBar");
    ToolBar3.setSize(dim);
    ToolBar[2] = ToolBar3;

//    tbToolbar1.add(ToolBar1);
//    tbToolbar2.add(ToolBar2);
//    tbToolbar3.add(ToolBar3);

//    pnTOP.add(tbMenubar, null);
    pnTOP.add(pnMenubar, null);
//    pnTOP.add(tbToolbar1, null);
//    pnTOP.add(tbToolbar2, null);
//    pnBOTTOM.add(tbToolbar3, null);
  }
  public Component[]setMenuAndToolBar(Component[] comps){
    Component[] comp=pnTOP.getComponents();
    pnTOP.removeAll();
    for(int i=0;i<comps.length;i++)
      pnTOP.add((Component)comps[i]);
    return comp;
  }
  public static void removeMenuAll(){
    if ( Explorer != null ){
      for(int i=0;i<MenuGroupArray.size();i++){
        Explorer.MenuBar.removeGroup((ActionGroup)MenuGroupArray.get(i));
      }
    }
    MenuGroupArray.clear();
  }
  public static void removeMenuGroup(ActionGroup actiongroup) {
      MenuGroupArray.remove(actiongroup);
      if ( Explorer != null )
        Explorer.MenuBar.removeGroup(actiongroup);
  }

  public static void addMenuGroup(int Index, ActionGroup actiongroup){
    MenuGroupArray.add(Index, actiongroup);
    if ( Explorer != null )
      Explorer.MenuBar.addGroup(Index,actiongroup);
  }

  public static void addMenuGroup(ActionGroup actiongroup) {
      MenuGroupArray.add(actiongroup);
      if ( Explorer != null )
        Explorer.MenuBar.addGroup(actiongroup);
  }

  public static ActionGroup[] getMenuGroups() {
    ActionGroup aactiongroup[] = new ActionGroup[MenuGroupArray.size()];
    aactiongroup = (ActionGroup[])MenuGroupArray.toArray(aactiongroup);
    return aactiongroup;
  }

  public static ActionGroup getMenuGroup(int i1) {
    return (ActionGroup)MenuGroupArray.get(i1);
  }

  public static int getMenuGroupCount() {
    return MenuGroupArray.size();
  }
  public static EnterpriseExplorer getExplorer() {
    return Explorer;
  }
  public static EnterpriseExplorer findExplorer(Object obj)
  {
      if(obj instanceof EnterpriseExplorer)
          return (EnterpriseExplorer)obj;
      if(obj instanceof Component)
          return findExplorer(((Component)obj).getParent());
      else
          return null;
  }
  public void InitMainWindow(Object enterprise,Object args,Object o3,Object o4) {
//    initMenubar();
//    initToolbar();
  }
  public static void InitOpenEnterpriseExplorer(EnterpriseFounder enterprise,Object args,Object o3,Object o4) {

  }
  public static void addToolBarGroup(int i1, ActionGroup actiongroup,int index) {
      ToolbarGroupArray[index].add(i1, actiongroup);
      if ( Explorer != null )
        Explorer.ToolBar[index].addGroup(i1,actiongroup);
  }

  public static void addToolBarGroup(ActionGroup actiongroup,int index) {
    ToolbarGroupArray[index].add(actiongroup);
    if ( Explorer != null && Explorer.ToolBar != null && Explorer.ToolBar[index] != null)
      Explorer.ToolBar[index].addGroup(actiongroup);
  }

  public static void removeToolBarGroup(ActionGroup actiongroup,int index) {
    ToolbarGroupArray[index].remove (actiongroup);
    if ( Explorer != null )
      Explorer.ToolBar[index].removeGroup(actiongroup);
  }
  public static void removeAllToolBarGroup(int index) {
    ToolbarGroupArray[index].clear();
    if ( Explorer != null ) {
      if ( Explorer.ToolBar[index] == null ) return;
      Explorer.ToolBar[index].removeAllGroup();
      Explorer.showToolbar(index,false);
    }
  }

  public static ActionGroup[] getToolBarGroups(int index) {
    ActionGroup aactiongroup[] = new ActionGroup[ToolbarGroupArray[index].size()];
    aactiongroup = (ActionGroup[])(ToolbarGroupArray[index]).toArray(aactiongroup);
    return aactiongroup;

  }


  public static ActionGroup getToolBarGroup(int i1,int index)
  {
      return (ActionGroup)ToolbarGroupArray[index].get(i1);
  }

  public static int getToolBarGroupCount(int index)
  {
      return ToolbarGroupArray[index].size();
  }

  public ActionToolBarPane getToolBarPane(int index)
  {
      return ToolBar[index];
  }
  public void showToolbar(int index,boolean isshow) {
    if ( this.ToolBar[index] != null ) {
//      this.ToolBar[index].setVisible(isshow);
      if ( isshow )
        this.getDockableBarHolder().getDockableBarManager().showDockableBar(this.ToolBar[index].getKey());
      else
        this.getDockableBarHolder().getDockableBarManager().hideDockableBar(this.ToolBar[index].getKey());
    }
//    this.tbToolBar[index].setVisible(isshow);
  }
//  public JNodeStub getActiveNode() {
//    JNodeStub nodeStub = null;
//    JExplorerView ev = JExplorerView.getActiveExplorerView();
//    if ( ev != null )
//      nodeStub = ev.getActiveNode();
//    return nodeStub;
//  }
  public static boolean closeAllWindow() {
    IWindow win = null;java.util.List winList = new ArrayList();
    int count = EnterpriseExplorer.ContentView.getWindowCount();
    for(int i=0;i<count;i++) {
      win = (IWindow)EnterpriseExplorer.ContentView.getWindow(i);
      winList.add(win);
    }
    for(int i=0;i<winList.size();i++) {
      win = (IWindow)winList.get(i);
      if ( EnterpriseExplorer.ContentView.closeChildWindow(win) == null ) {
        return false;
      }
    }
    return true;
  }
  private int commCount = 0;
  /**
   *
   * @param object Object
   * @param uRL URL
   * @param object2 Object
   */
  public void startComm(Object object, URL uRL, Object object2) {
    if ( commCount == 0 )
      this.startImageLabel();
    commCount++;
  }
  /**
   *
   * @param object Object
   * @param uRLConnection URLConnection
   * @param jResponseObject JResponseObject
   */
  public void stopComm(Object object, URLConnection uRLConnection,
                       Object jResponseObject) {
    commCount--;
    if ( commCount == 0 )
      this.stopImageLabel();
  }

  public IView getContentView() {
    return null;
  }
  

//  public void validate() {
//	  getRootPaneContainer().getContentPane().validate();
//  }
}
