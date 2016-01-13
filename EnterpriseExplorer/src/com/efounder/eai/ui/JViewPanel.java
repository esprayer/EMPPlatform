package com.efounder.eai.ui;

import java.beans.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.efounder.action.*;
import com.efounder.comp.tabbedpane.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.*;
import com.efounder.pfc.window.*;
import com.efounder.pub.comp.*;
import com.jidesoft.swing.*;
import com.jidesoft.action.DockableBarHolder;
import com.jidesoft.docking.DockableHolder;
import com.efounder.node.view.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JViewPanel extends JPanel implements DoubleClickListener,com.efounder.comp.tabbedpane.PopupMenuListener,MaxListener,CloseListener,IView,MouseListener,PropertyChangeListener,ChangeListener {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnTOP = new JPanel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel lbTitle = new JLabel();
  JPanel jPanel2 = new JPanel();
  JCheckBox bnClose = new JCheckBox();//JButton();
  JButton bnMAX = new JButton();
  JButton bnMIN = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();

 protected IWindow LAST_ACTIVE_WINDOW = null;

  protected IViewDevice ViewDevice = null;
  protected JTabbedPane tbContent = null;
  public boolean isOpen(IWindow cw) {
    if ( tbContent == null ) return false;
    return (tbContent.indexOfComponent(cw.getWindowComponent()) != -1);
  }
  protected JTabbedPane getTabbedPane() {
    JideTabbedPane idetbContent = new JideTabbedPane();//new CloseAndMaxTabbedPane(true);

    idetbContent.setCloseAction(new AbstractAction(){
      public void actionPerformed(ActionEvent e) {
        System.out.println(e);
        Object comp = e.getSource();
        if ( comp == null || !(comp instanceof IWindow) ) return;
        JViewPanel.this.closeChildWindow((IWindow)comp);
      }
    });

    tbContent = idetbContent;
    tbContent.setOpaque(false);
//    tbContent.set
    tbContent.setBorder(null);
    tbContent.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    idetbContent.setShowTabButtons(true);
//    idetbContent.setShowCloseButton(true);
    idetbContent.setShowCloseButtonOnTab(true);
    idetbContent.setBoldActiveTab(true);
//    idetbContent.setUseDefaultShowCloseButtonOnTab(true);
    return tbContent;
  }
//  protected JTabbedPane tbContent = new JTabbedPane();
  String Layer  = null;
  String viewID = null;
  Action GROUP_View = null;//new ExplorerActionGroup("PopupMenu",'P',"PopupMenu");
  protected int Style = VIEW_TITLE & VIEW_CLOSE;
  protected Hashtable windowAttr = null;
//  protected static Vector HideWindowList = new Vector();
  /**
   *
   * @param Key Object
   * @param Value Object
   */
  public void putObject(Object Key,Object Value) {
    if ( windowAttr == null ) windowAttr = new Hashtable();
    windowAttr.put(Key,Value);
  }
  /**
   *
   * @param Key Object
   * @param Def Object
   * @return Object
   */
  public Object getObject(Object Key,Object Def) {
    Object res = Def;
    if ( windowAttr != null ) {
      res = windowAttr.get(Key);
      if ( res == null ) res = Def;
    }
    return res;
  }
  /**
   *
   */
  public JViewPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setBorder(null);
    getTabbedPane();
    this.setLayout(borderLayout1);
    jPanel1.setInputVerifier(null);
    jPanel1.setLayout(borderLayout2);
    pnTOP.setLayout(borderLayout3);
    bnMIN.setPreferredSize(new Dimension(16, 16));
    bnMIN.setMargin(new Insets(0, 0, 0, 0));
    bnMAX.setPreferredSize(new Dimension(16, 16));
    bnMAX.setMargin(new Insets(0, 0, 0, 0));
    bnClose.setPreferredSize(new Dimension(16, 16));
    bnClose.setMargin(new Insets(0, 0, 0, 0));
    jPanel2.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    this.add(pnTOP, BorderLayout.NORTH);
    pnTOP.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(lbTitle,  BorderLayout.CENTER);
    pnTOP.add(jPanel2,  BorderLayout.EAST);
    jPanel2.add(bnMIN, null);
    jPanel2.add(bnMAX, null);
    jPanel2.add(bnClose, null);
    this.add(tbContent, BorderLayout.CENTER);
    pnTOP.setVisible(false);
    bnMIN.setVisible(false);
    bnMAX.setVisible(false);
    bnClose.setVisible(false);
//    this.bnClose.setIcon(ExplorerIcons.ICON_GENERICCLOSE);
    initEvent();

  }
  /**
   *
   */
  protected void initEvent() {
    ((JideTabbedPane)tbContent).addMouseListener(this);
//    this.tbContent.addMouseListener(this);
//    ((CloseAndMaxTabbedPane)tbContent).addCloseListener(this);
//    ((CloseAndMaxTabbedPane)tbContent).addMaxListener(this);
//    ((CloseAndMaxTabbedPane)tbContent).addDoubleClickListener(this);
//    ((CloseAndMaxTabbedPane)tbContent).addPopupMenuListener(this);
    this.tbContent.addChangeListener(this);
//    this.addMouseListener(this);
  }
  public JComponent getViewComponent() {
    return this;
  }
  public void setStyle(int style) {
    // 没有标题�\uFFFD
    Style = style;
    if ( ( Style | VIEW_NO_TITLE) == VIEW_NO_TITLE ) {
      this.pnTOP.setVisible(false);// 没有
    }
  }
  protected void addAction(IWindow cw) {
    // 注册�\uFFFD个WindowAction
    Action ac = new JViewAction(this,cw,cw.getTitle(),'@',cw.getTips(),cw.getIcon());
    ActionGroup ag = (ActionGroup)this.getAction();
    if ( ag != null ) {
      ag.add(ac);
    }
  }
  protected void delActioin(IWindow cw) {
    ActionGroup ag = (ActionGroup)this.getAction();
    if ( ag == null || ag.getActionCount() == 0 ) return;
    JViewAction viewAction = null;
    for(int i=0;i<ag.getActionCount();i++) {
      viewAction = (JViewAction)ag.getAction(i);
      if ( viewAction.getActionWindow().equals(cw) ) {
        if ( cw.canDeleteAction() ) {
          ag.remove(viewAction);
        }
        break;
      }
    }
  }
  /**
   * 显示/隐CANG某一个子窗口
   * @param cw IWindow
   * @param v boolean
   */
  public void setVisible(IWindow cw,boolean v) {
    if ( v ) { // 要显示某�\uFFFD�\uFFFD
      // 如果
      if ( tbContent.indexOfComponent(cw.getWindowComponent()) == -1 ) { //&& HideWindowList.indexOf(cw) != -1 ) {
        if ( tbContent.getTabCount() == 0 ) {
          setVisible(true);
        }
        tbContent.insertTab(cw.getTitle(),getWindowIcon(cw),cw.getWindowComponent(),cw.getTips(),tbContent.getTabCount());
        tbContent.setSelectedComponent(cw.getWindowComponent());
//        HideWindowList.remove(cw);
      } else { //
        if ( tbContent.indexOfComponent(cw.getWindowComponent()) > -1  ) { //&& !isVisible()
          tbContent.setSelectedComponent(cw.getWindowComponent());
          setVisible(true);
        }
      }
    } else {// 不显示某�\uFFFD个Window
      if ( tbContent.indexOfComponent(cw.getWindowComponent()) != -1 ) {
        tbContent.remove(cw.getWindowComponent());
        setFirstTab();
//        HideWindowList.add(cw);
      }
      // 如果此视中一个子窗口都没有了，则�\uFFFD
      if ( tbContent.getTabCount() == 0 ) {
        setVisible(false);
      }
    }
  }
  public boolean isVisible(IWindow cw) {
    return tbContent.indexOfComponent(cw.getWindowComponent()) == -1?false:true;
  }
  public int  getStyle() {
    return Style;
  }
  public void openChildWindow(IWindow cw) {
    openChildWindow(cw,cw.getTitle(),cw.getTips(),cw.getIcon());
  }
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon){
    openChildWindow(cw,title,tips,icon,IWindow.WINDOW_NO_TITLE);
  }
  protected Icon getCompIcon(IWindow cw) {
    Icon closeIcon = null;
    if ( cw.isModified() )
      closeIcon = ExplorerIcons.ICON_WARNING;//getExplorerIcon("CLOSE_MODIFIED.jpg");//
    else if ( cw.isLock() )
      closeIcon = ExplorerIcons.ICON_MONITORLOCK;//ExplorerIcons.getExplorerIcon("READONLY_CLOSE.jpg");
    else
      closeIcon = null;//ExplorerIcons.getExplorerIcon("GENERICCLOSE.jpg");
    return closeIcon;
  }
  protected Icon getWindowIcon(IWindow cw) {
    Icon windowIcon = cw.getIcon();Icon compIcon = null;Icon closeIcon = null;
//    return windowIcon;
    closeIcon = getCompIcon(cw);
    if ( windowIcon == null )
      compIcon = closeIcon;
    else if ( closeIcon != null )
      compIcon = new CompoundIcon(closeIcon,windowIcon,CompoundIcon.HORIZONTAL,0);
    else
      compIcon = windowIcon;
    return compIcon;
  }
  protected int OPEN_NEW_INDEX = -1;
  /**
   *
   * @param newIndex int
   */
  public void waitInvoke(int newIndex) {
//    OPEN_NEW_INDEX = newIndex;
//    RequestProcessor.Task openNodeObjectTask = null;
//    openNodeObjectTask = RequestProcessor.getDefault().create(this);
//    openNodeObjectTask.schedule(500);
  }
//  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style){
//    LAST_ACTIVE_WINDOW = tbContent.getSelectedComponent();
//    if ( tbContent.indexOfComponent(cw.getWindowComponent()) == -1 ) {
//      // 设置Window的属性�\uFFFD\uFFFD
//       cw.setTitle(title);cw.setIcon(icon);cw.setTips(tips);cw.setStyle(Style);
//       cw.setView(this);
//       tbContent.insertTab(cw.getTitle(),getWindowIcon(cw),cw.getWindowComponent(),cw.getTips(),0);//tbContent.getTabCount());
//       tbContent.setSelectedIndex(0);
//     } else {
//       tbContent.setSelectedComponent(cw.getWindowComponent());
//       int index = tbContent.indexOfComponent(cw.getWindowComponent());
//       if ( index != -1 ) {
//         cw.setTitle(title);
//         cw.setIcon(icon);
//         cw.setTips(tips);
//       }
//     }
//     setVisible(true);
//  }
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style){
    openChildWindow(cw,title,tips,icon,Style,false);
  }
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style,boolean dialog){
    LAST_ACTIVE_WINDOW = (IWindow)tbContent.getSelectedComponent();
    IWindow parentWindow = LAST_ACTIVE_WINDOW;
    int currentIndex = tbContent.getSelectedIndex();
    if ( tbContent.indexOfComponent(cw.getWindowComponent()) == -1 ) {
      // 设置Window的属性�\uFFFD\uFFFD
      cw.setTitle(title);cw.setIcon(icon);cw.setTips(tips);cw.setStyle(Style);
      cw.setView(this);
      if ( !dialog )
        tbContent.insertTab(cw.getTitle(),getWindowIcon(cw),cw.getWindowComponent(),cw.getTips(),tbContent.getTabCount());
      // 将此窗口注册
      registryWindow(cw);
      // 注册Action
      addAction(cw);
      cw.openWindowEvent(this);// 打开事件
      if ( tbContent.getTabCount() > 1 )
        tbContent.setSelectedIndex(tbContent.getTabCount()-1);
//      if ( LAST_ACTIVE_WINDOW == null )
//        stateChanged(null);
//      else {
//        tbContent.setSelectedIndex(0); // 设置到第0�\uFFFD
//        stateChanged(null);
//      }
      // 将LAST_ACTIVE_WINDOW设置为parentWindow
      processParentWindow(cw,parentWindow);
    } else {
      tbContent.setSelectedComponent(cw.getWindowComponent());
      int index = tbContent.indexOfComponent(cw.getWindowComponent());
      if ( index != -1 ) {
        cw.setTitle(title);
        cw.setIcon(icon);
        cw.setTips(tips);
      }
    }
//    NOW_ACTIVE_WINDOW = (IWindow)tbContent.getSelectedComponent();
    // 设置可视状�\uFFFD\uFFFD
    setVisible(true);
    // 设置是否是最大化状�\uFFFD\uFFFD
//    setViewStatus(cw);
  }
  protected void setViewStatus(IWindow cw) {
//    this.ViewDevice.setViewState(this,Frame.MAXIMIZED_BOTH);//cw.getViewStatus());
  }
  /**
   *
   * @param cw IWindow
   * @param parentWindow IWindow
   */
  protected void processParentWindow(IWindow cw,IWindow parentWindow) {
    cw.setParentWindow(parentWindow);
    if ( parentWindow != null ) {
      int status = parentWindow.getViewStatus();
      cw.setViewStatus(status);
    } else {
      cw.setViewStatus(this.getViewStatus());
    }
  }
  protected void registryWindow(IWindow cw) {
    // 将此窗口注册
    WindowManager.registryWindow(cw.getID(),cw);
  }
  protected void unregistryWindow(IWindow cw) {
    // 将此窗口取消注册
//    WindowManager.unregistryWindow(cw);
  }
  public IWindow closeChildWindow(IWindow cw){
    return closeChildWindow(cw,false);
  }
  public IWindow closeChildWindow(IWindow cw,boolean mustClose){
    if ( mustClose || (cw.closingWindowEvent(this) && cw.canClose()) ) {
      setVisible(cw, false);
      delActioin(cw);
      unregistryWindow(cw);
      cw.closeWindowEvent(this);
      setFirstTab();
      setNullParentWindow(cw); // 清除以cw为parentwindow的window

      if (EnterpriseExplorer.ContentView.getActiveWindow() == null) {
        EnterpriseExplorer.ExplorerView.setVisible(true);
      }

      return cw;
    }
    return null;
  }
  /**
   *
   * @param cw IWindow
   */
  protected void setNullParentWindow(IWindow cw) {
    int winCount = tbContent.getTabCount();IWindow win = null;
    for(int i=0;i<winCount;i++) {
      win = (IWindow)tbContent.getComponentAt(i);
      if ( win.getParentWindow() != null && win.getParentWindow() == cw ) {
        win.setParentWindow(null);
      }
    }
  }
  protected void setFirstTab() {
//    if ( this.tbContent.getTabCount() > 0 ) {
//      Component comp = tbContent.getComponentAt(0);
//      tbContent.setSelectedComponent(comp);
//    }
  }
  public void setTitle(String title){
    lbTitle.setText(title);
  }

  public String getTitle(){
    return lbTitle.getText();
  }
  public void setIcon(Icon icon) {
    lbTitle.setIcon(icon);
  }
  public Icon getIcon() {
    return lbTitle.getIcon();
  }
  public void setVisible(boolean v) {
//    ViewDevice.setVisible(this,v);
  }
  public boolean isVisible() {
//    return ViewDevice.isVisible(this);
    return true;
  }
  public void setViewDevice(IViewDevice vd) {
    ViewDevice = vd;
  }
  public IViewDevice getViewDevice() {
    return ViewDevice;
  }
  public void setViewPlacement(int viewPlacement) {
    tbContent.setTabPlacement(viewPlacement);
  }
  public int  getViewPlacement() {
    return this.tbContent.getTabPlacement();
  }
  public void setLocation(int l) {
//    ViewDevice.setLocation(this,l);
  }
  public void setLocation(double l) {
//    ViewDevice.setLocation(this,l);
  }
  public void   setLayer(String layer) {
    Layer = layer;
  }
  public String getLayer() {
    return Layer;
  }
  public String getID() {
    return viewID;
  }
  public void setID(String ID) {
    viewID = ID;
  }
  public Action getAction() {
    return GROUP_View;
  }
  public void setAction(Action a) {
    GROUP_View = a;
  }
  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e) {
    if ( e.getModifiers() == e.BUTTON3_MASK ) {
      showPopupmenu(e,e.getX(),e.getY());
    }
    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
      switchMaxWindow();
    }
  }
  /**
   *
   */
  protected void switchMaxWindow() {
    IWindow activeWindow = this.getActiveWindow();
    if ( activeWindow == null ) return;
    if ( !activeWindow.isMaxWindow() ) {
      maxWindow(activeWindow);
    }
    else {
      rstWindow(activeWindow);
    }
  }

  private void rstWindow(IWindow activeWindow) {
    if ( activeWindow.getMaxLayoutData() != null ) {
      dockableHolder.getDockingManager().setLayoutRawData(activeWindow.getMaxLayoutData());
    }
    activeWindow.setMaxWindow(false);
  }

  private void maxWindow(IWindow activeWindow) {
    byte[] _fullScreenLayout = dockableHolder.getDockingManager().getLayoutRawData();
    activeWindow.setMaxLayoutData(_fullScreenLayout);
    dockableHolder.getDockingManager().autohideAll();
    activeWindow.setMaxWindow(true);
  }
//  private boolean _autohideAll = false;
//  private byte[] _fullScreenLayout;
  protected void showPopupmenu(MouseEvent e,int X,int Y) {
    ActionGroup popupMenu = new ActionGroup();
    getContextAction(popupMenu);
    if ( popupMenu.getActionCount() == 0 ) return;
//    popupMenu.add(popupAction);
    ActionPopupMenu actionPopupMenu = new ActionPopupMenu(this, popupMenu, true);
    actionPopupMenu.show((Component)e.getSource(), X, Y);
  }//String s, char c, String s1, Icon icon)ExplorerIcons.ICON_CLOSE_PROJECT
  /**
   *
   * @param actionObject Object
   * @param dataObject Object
   * @param action ActiveObjectAction
   * @param actionevent ActionEvent
   */
  public void closeCurrentWindow(Object actionObject, IWindow win,ActiveObjectAction action,ActionEvent actionevent) {
    closeChildWindow(win);
  }
  public void closeOther0Window(Object actionObject, IWindow win,ActiveObjectAction action,ActionEvent actionevent) {
    int count = this.tbContent.getTabCount();IWindow[] winArray = new IWindow[count];
    for(int i=0;i<count;i++) {
      winArray[i] = (IWindow)this.tbContent.getComponentAt(i);
    }
    closeWindow0(winArray,win);
    closeChildWindow(win);
  }
  protected void closeWindow0(IWindow[] winArray,IWindow win) {
    for(int i=0;i<winArray.length;i++) {
      if ( winArray[i].getParentWindow() != null &&
           winArray[i].getParentWindow() == win ) {
        closeWindow0(winArray,winArray[i]);
        closeChildWindow(winArray[i]);
      }
    }
  }
  public void closeOtherWindow(Object actionObject, IWindow win,ActiveObjectAction action,ActionEvent actionevent) {
    int count = this.tbContent.getTabCount();IWindow[] winArray = new IWindow[count];
    for(int i=0;i<count;i++) {
      winArray[i] = (IWindow)this.tbContent.getComponentAt(i);
    }
    for(int i=0;i<winArray.length;i++) {
      if ( !win.equals(winArray[i]) )
        closeChildWindow(winArray[i]);
    }
  }
  public void closeAllWindow(Object actionObject, IWindow win,ActiveObjectAction action,ActionEvent actionevent) {
    int count = this.tbContent.getTabCount();IWindow[] winArray = new IWindow[count];
    for(int i=0;i<count;i++) {
      winArray[i] = (IWindow)this.tbContent.getComponentAt(i);
    }
    for(int i=0;i<winArray.length;i++) {
      closeChildWindow(winArray[i]);
    }
  }

  /**
   *
   * @param ag ActionGroup
   * @return Action
   */
  public Action getContextAction(ActionGroup ag) {
    IWindow activeWindow = this.getActiveWindow();String text;
    ActionGroup windowActionGroup = null;
    if ( activeWindow != null ) {
      windowActionGroup = new ActionGroup();
      text = activeWindow.getTitle();
      // 关闭当前窗口
      Action actionClose = null;//new ViewContextAction(this, "CloseCurrent", "关闭"+text, '0', "关闭"+text,ExplorerIcons.ICON_CLOSE_FILE);
      actionClose = new ActiveObjectAction(this,activeWindow,"closeCurrentWindow",res.getString("KEY10")+text, '0', res.getString("KEY11")+text,ExplorerIcons.ICON_CLOSE_FILE);
      windowActionGroup.add(actionClose);
      // 关闭其他窗口
      Action actionOther = null;//new ViewContextAction(this, "CloseOther", "关闭其他窗口", '0',"关闭其他窗口", null);
      actionOther = new ActiveObjectAction(this,activeWindow,"closeOtherWindow",res.getString("KEY12"), '0',res.getString("KEY13"), null);
      windowActionGroup.add(actionOther);
      // 关闭�\uFFFD有窗�\uFFFD
      Action actionAll   = null;//new ViewContextAction(this, "CloseAll", "关闭�\uFFFD有窗�\uFFFD", '0', "关闭�\uFFFD有窗�\uFFFD", null);
      actionAll = new ActiveObjectAction(this,activeWindow,"closeAllWindow",res.getString("KEY14"), '0', res.getString("KEY15"), null);
      windowActionGroup.add(actionAll);
      // 关闭衍生窗口
      Action actionOther0 = null;//new ViewContextAction(this, "CloseOther", "关闭其他窗口", '0',"关闭其他窗口", null);
      actionOther0 = new ActiveObjectAction(this,activeWindow,"closeOther0Window",res.getString("KEY16"), '0',res.getString("KEY17"), null);
      windowActionGroup.add(actionOther0);
      ag.add(windowActionGroup);
      ActionGroup agg = getWindowListAction();
      if ( agg.getActionCount() > 0 ) {
        ag.add(agg);
      }
      if ( activeWindow instanceof NodeWindow ) {
        NodeWindow nodeWindow = (NodeWindow)activeWindow;
        Action a = nodeWindow.getNodeAttribViewAction();
        if ( a != null ) ag.add(a);
      }
      //
//      Action ac = this.getAction();
//      if ( ac != null ) {
//        ActionGroup aag = new ActionGroup();
//        windowActionGroup = new ActionGroup("窗口",'0',"窗口选择",ExplorerIcons.ICON_CASCADE,true);
//        windowActionGroup.add(this.getAction());
//        aag.add(windowActionGroup);
//        ag.add(aag);
//      }
    }
    return ag;
  }
  protected ActionGroup getWindowListAction() {
    ActionGroup group = new ActionGroup();
    int count = this.tbContent.getTabCount();
    IWindow win = null;Action ac = null;
    for(int i=0;i<count;i++) {
      win = (IWindow)tbContent.getComponentAt(i);
      ac = new StateActiveObjectAction(this,win,"ActiveChindWindow",win.getTitle(),'0',win.getTips(),win.getIcon());
      group.add(ac);
    }
    return group;
  }
  /**
   *
   * @param actionObject Object
   * @param win IWindow
   * @param obj Object
   * @param action Action
   * @return boolean
   */
  public boolean getStateActiveChindWindow(Object actionObject,IWindow win,Object obj,Action action) {
    return win.getWindowComponent() == this.tbContent.getSelectedComponent();
  }
  /**
   *
   * @param actionObject Object
   * @param win IWindow
   * @param obj Object
   * @param action Action
   * @param value boolean
   */
  public void    setStateActiveChindWindow(Object actionObject,IWindow win,Object obj,Action action,boolean value) {
    if ( value ) {
      tbContent.setSelectedComponent(win.getWindowComponent());
    }
  }
  public int getWindowCount() {
    return this.tbContent.getTabCount();
  }
  public void closeOperation(MouseEvent e) {
    Point p = e.getPoint();
    MouseClickCloseWindow(p);
  }
  public void maxOperation(MouseEvent e) {
    max_rstView();
  }
  public void doubleClickOperation(MouseEvent e) {
    max_rstView();
  }
  public void popupOutsideOperation(MouseEvent e) {
    showPopupmenu(e,e.getX(),e.getY());
  }
  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e) {
    if ( e.getModifiers() == e.BUTTON1_MASK ) {
      if ( e.getClickCount() == 2 ) {
        max_rstView();
        return;
      }

//      if ( e.getSource() == this.tbContent ) {
//        Point p = e.getPoint();
//        MouseClickCloseWindow(p);
//        return;
//      }
      return;
    }
  }
  protected void max_rstView() {
//    this.ViewDevice.setExtendedState(this,Frame.MAXIMIZED_BOTH);
  }
  /**
   * Invoked when a mouse button has been released on a component.
   */
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * Invoked when the mouse enters a component.
   */
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * Invoked when the mouse exits a component.
   */
  public void mouseExited(MouseEvent e) {

  }
  /**
   * This method gets called when a bound property is changed.
   * @param evt A PropertyChangeEvent object describing the event source
   *   	and the property that has changed.
   */

  public void propertyChange(PropertyChangeEvent evt) {

  }
  Action getWindowAction(IWindow win) {
    ActionGroup ag = (ActionGroup)this.getAction();
    if ( ag == null ) return null;JWindowAction va = null;
    for(int i=0;i<ag.getActionCount();i++) {
      if ( !(ag.getAction(i) instanceof JWindowAction) ) continue;
      va = (JWindowAction)ag.getAction(i);
      if ( win.equals(va.getActionWindow()) ) {
        return va;
      }
    }
    return null;
  }
  public void firePropertyChange(String propertyName, Object cw, Object newValue) {
    if ( "setTitle".equals(propertyName) ) {
      int index = this.tbContent.indexOfComponent(((IWindow)(cw)).getWindowComponent());
      if ( index != -1 ) {
        this.tbContent.setTitleAt(index, (String) newValue);
        tbContent.setToolTipTextAt(index, (String) newValue);
//        ((IWindow)(cw)).setTitle((String)newValue);
      }
      Action a = getWindowAction((IWindow)cw);
      if ( a != null ) {
        a.putValue(Action.SHORT_DESCRIPTION,newValue);
      }
    }
    if ( "setIcon".equals(propertyName) ) {
      int index = this.tbContent.indexOfComponent(((IWindow)(cw)).getWindowComponent());
      if ( index != -1 ) {
        this.tbContent.setIconAt(index, getWindowIcon(((IWindow)(cw))));
//        ((IWindow)(cw)).setIcon((Icon)newValue);
      }
      Action a = getWindowAction((IWindow)cw);
      if ( a != null ) {
        a.putValue(Action.SMALL_ICON, ((IWindow)cw).getIcon());
      }
    }
    if ( "setTips".equals(propertyName) ) {
      int index = this.tbContent.indexOfComponent(((IWindow)(cw)).getWindowComponent());
      if ( index != -1 ) {
        this.tbContent.setToolTipTextAt(index, (String) newValue);
//        ((IWindow)(cw)).setTips((String)newValue);
      }
      Action a = getWindowAction((IWindow)cw);
      if ( a != null ) {
        a.putValue(Action.LONG_DESCRIPTION, newValue);
      }
    }
    if ( "setLock".equals(propertyName) || "setModified".equals(propertyName)) {
      int index = this.tbContent.indexOfComponent(((IWindow)(cw)).getWindowComponent());
      if ( index != -1 ) {
        this.tbContent.setIconAt(index, getWindowIcon(((IWindow)(cw))));
      }
      Action a = getWindowAction((IWindow)cw);
      if ( a != null ) {
        a.putValue(Action.SMALL_ICON, ((IWindow)cw).getIcon());
      }
    }
    super.firePropertyChange(propertyName,cw,newValue);
  }
  protected void MouseClickCloseWindow(Point p) {
    int Width = 0;
    int Index = this.tbContent.indexAtLocation(p.x,p.y);
    if ( Index == -1 ) return;
    IWindow comp = (IWindow)this.tbContent.getComponentAt(Index);
    if ( comp == null || !(comp instanceof IWindow) ) return;
    this.closeChildWindow(comp);
    this.stateChanged(null);
//    Icon icon = tbContent.getIconAt(Index);
//    if ( icon != null ) {Width = icon.getIconWidth();}
//    String Text = tbContent.getTitleAt(Index).trim();
//    Width += tbContent.getGraphics().getFontMetrics().stringWidth(Text);
//    int SX,SY,IW,IH;
//    IW = getCompIcon(comp).getIconWidth();
//    IH = getCompIcon(comp).getIconHeight();
//    Rectangle rect = this.tbContent.getBoundsAt(Index);
//    SX =  rect.x;
//    if ( Width < rect.width ) {
//      SX = rect.x + (rect.width - Width)/2;
//    }
//    SY = rect.y + (rect.height - IH)/2;
//    if ( p.x >= SX && p.x <= (SX+IW) &&
//         p.y >= SY && p.y <= (SY+IH) ) {
//      this.closeChildWindow(comp);
//    }
  }
  public void setExtendedState(int stat) {
    IWindow window = this.getActiveWindow();
    if ( stat == Frame.MAXIMIZED_BOTH ) {
      if ( !window.isMaxWindow() ) {
        maxWindow(window);
      }
    }
  }
  /**
   * �\uFFFD活某�\uFFFD个窗�\uFFFD
   * @param cw IWindow
   */
  public void activeWindow(IWindow cw) {
    if ( !cw.equals(LAST_ACTIVE_WINDOW) )
      tbContent.setSelectedComponent(cw.getWindowComponent());
  }
  /**
   *
   * @param e ChangeEvent
   */
  public void stateChanged(ChangeEvent e) {
//    if ( e.getSource() == this.tbContent ) {
      activeEvent(e);
//    }
  }
  /**
   *
   * @param e ChangeEvent
   */
  protected void activeEvent(ChangeEvent e) {
    if ( LAST_ACTIVE_WINDOW != null )
      LAST_ACTIVE_WINDOW.deactiveWindowEvent(this);
    IWindow NOW_ACTIVE_WINDOW = (IWindow)this.tbContent.getSelectedComponent();
    if ( NOW_ACTIVE_WINDOW != null )
      NOW_ACTIVE_WINDOW.activeWindowEvent(this);
    LAST_ACTIVE_WINDOW = NOW_ACTIVE_WINDOW;

    //
    IWindow win = (IWindow)this.tbContent.getSelectedComponent();
    if ( win != null ) {
//      lbTitle.setText(win.getTitle());
//      lbTitle.setIcon(win.getIcon());
    }
  }
  /**
   *
   * @return IWindow
   */
  public IWindow getActiveWindow() {
    IWindow win = null;
    Component comp = tbContent.getSelectedComponent();
    if ( comp instanceof IWindow )
      win = (IWindow)comp;
    return win;
  }
  public IWindow getWindow(int index) {
    IWindow win = null;
    win = (IWindow)this.tbContent.getComponentAt(index);
    return win;
  }

  public WindowGroup[] getWindowGroups() {
    return null;
  }
  public void showView(Object context){}
  public void hideView(){}
  public void initView(Object node){}
  public int getResult(){return -1;}
  public void removeWindowAction() {}
  public void insertWindowAction(IWindow win) {}
  protected int viewStatus = Frame.NORMAL;
  /**
   *
   * @return int
   */
  public final int getViewStatus() {
    return viewStatus;
  }
  public final void setViewStatus(int status) {
    viewStatus = status;
  }


  /**
   *
   */
  protected DockableBarHolder dockableBarHolder = null;
  /**
   *
   * @param d DockableBarHolder
   */
  public void setDockableBarHolder(DockableBarHolder d) {
    dockableBarHolder = d;
  }
  /**
   *
   */
  protected DockableHolder dockableHolder = null;
  /**
   *
   * @param d DockableHolder
   */
  public void setDockableHolder(DockableHolder d) {
    dockableHolder = d;
  }


}
