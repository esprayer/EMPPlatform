package com.efounder.eai.ui;

import java.awt.*;
import javax.swing.*;
import com.efounder.pfc.window.*;
import java.util.*;
import com.efounder.action.*;
import com.efounder.pfc.swing.*;
import java.awt.event.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JSelectViewPanel extends JPanel implements IView,ActionListener {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel pnContent = new JPanel();
//  JLabel lbTitle = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  JComboBox cbxWindowList = new JComboBox();
  CardLayout cardLayout = new CardLayout();
  private IViewDevice ViewDevice = null;
  /**
   *
   */
  public JSelectViewPanel() {
    try {
      jbInit();
      initEvent();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  private void initEvent() {
    this.cbxWindowList.addActionListener(this);
  }
  /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource().equals(this.cbxWindowList) ) {
      actionCbxWindowList();
    }
  }
  private void actionCbxWindowList() {
    WinStub winStub = (WinStub)this.cbxWindowList.getSelectedItem();
    if ( winStub != null ) {
      this.cardLayout.show(this.pnContent,winStub.getWindow().getID());
      winStub.getWindow().activeWindowEvent(this);
    }
  }
  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jPanel1.setLayout(borderLayout2);
    pnContent.setLayout(cardLayout);
    cardLayout.setHgap(0);
    this.add(jPanel1, BorderLayout.NORTH);
//    jPanel1.add(lbTitle,  BorderLayout.WEST);
    jPanel1.add(cbxWindowList,  BorderLayout.CENTER);
    this.add(pnContent, BorderLayout.CENTER);
    JIConListCellRenderer icr = new JIConListCellRenderer(null);
    this.cbxWindowList.setRenderer(icr);
//    lbTitle.setVisible(false);
  }
  protected Hashtable windowAttr = null;
  public void putObject(Object Key,Object Value) {
    if ( windowAttr == null ) windowAttr = new Hashtable();
    windowAttr.put(Key,Value);
  }
  public Object getObject(Object Key,Object Def) {
    Object res = Def;
    if ( windowAttr != null ) {
      res = windowAttr.get(Key);
      if ( res == null ) res = Def;
    }
    return res;
  }
  public JComponent getViewComponent() {
    return this;
  }
  public void setStyle(int style) {
  }
  /**
   * 显示/隐CANG某一个子窗口
   * @param cw IWindow
   * @param v boolean
   */
  public void setVisible(IWindow cw,boolean v) {

  }
  public boolean isVisible(IWindow cw) {
    return true;
  }
  public int  getStyle() {
    return 0;
  }
  public void openChildWindow(IWindow cw) {
    openChildWindow(cw,cw.getTitle(),cw.getTips(),cw.getIcon());
  }
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon){
    openChildWindow(cw,title,tips,icon,IWindow.WINDOW_NO_TITLE);
  }
  public class WinStub {
    IWindow cw;String title;String tips;Icon icon;
    protected WinStub(IWindow cw,String title,String tips,Icon icon) {
      this.cw = cw;this.title = title;this.tips = tips;this.icon = icon;
    }
    public String toString() {
      return title;
    }
    public Icon getIcon() {
      return icon;
    }
    public IWindow getWindow() {
      return cw;
    }
  }
  private JComponent getChild(JComponent comp) {
    Component[] comps = pnContent.getComponents();
    for(int i=0;i<comps.length;i++) {
      if ( comps[i].equals(comp) ) return comp;
    }
    return null;
  }
  public boolean isOpen(IWindow cw) {
    return false;
  }
  /**
   *
   * @param cw IWindow
   * @param title String
   * @param tips String
   * @param icon Icon
   * @param Style int
   */
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style){
    JComponent comp = getChild(cw.getWindowComponent());
    if ( comp == null ) {
      pnContent.add(cw.getWindowComponent(), cw.getID());
      WinStub winStub = new WinStub(cw,title,tips,icon);
      int index = this.cbxWindowList.getItemCount();
      this.cbxWindowList.insertItemAt(winStub,index);
      cbxWindowList.setSelectedIndex(index);
    } else {
      for(int i=0;i<cbxWindowList.getItemCount();i++) {
        WinStub winStub = (WinStub)cbxWindowList.getItemAt(i);
        if ( winStub.getWindow().equals(cw) ) {
          cbxWindowList.setSelectedIndex(i);
          break;
        }
      }
    }
    cardLayout.show(this.pnContent,cw.getID());
  }
  /**
   *
   * @param cw IWindow
   * @return IWindow
   */
  public IWindow closeChildWindow(IWindow cw) {
    return closeChildWindow(cw,false);
  }
  public IWindow closeChildWindow(IWindow cw,boolean mustClose){
    if ( cw!=null ) {
      setVisible(cw, false);
      cw.closeWindowEvent(this);
      return cw;
    } else {
      this.pnContent.removeAll();
      this.setVisible(false);
      cbxWindowList.setSelectedIndex(-1);
      this.cbxWindowList.removeAllItems();
//      cbxWindowList.setSelectedItem(null);
    }
    return null;
  }
  String Title = null;
  public void setTitle(String title){
    Title = title;
//    lbTitle.setText(title);
  }

  public String getTitle(){
    return Title;//lbTitle.getText();
  }
  public void setIcon(Icon icon) {
//    lbTitle.setIcon(icon);
  }
  public Icon getIcon() {
    return null;
//    return lbTitle.getIcon();
  }
  public void setVisible(boolean v) {
    ViewDevice.setVisible(this,v);
  }
  public boolean isVisible() {
    return ViewDevice.isVisible(this);
  }
  public void setViewDevice(IViewDevice vd) {
    ViewDevice = vd;
  }
  public IViewDevice getViewDevice() {
    return ViewDevice;
  }
  public void setViewPlacement(int viewPlacement) {

  }
  public int  getViewPlacement() {
    return 0;
  }
  public void setLocation(int l) {
    ViewDevice.setLocation(this,l);
  }
  public void setLocation(double l) {
    ViewDevice.setLocation(this,l);
  }
  private String Layer = null;
  public void   setLayer(String layer) {
    Layer = layer;
  }
  public String getLayer() {
    return Layer;
  }

  String viewID = null;
  public String getID() {
    return viewID;
  }
  public void setID(String ID) {
    viewID = ID;
  }
  public Action getAction() {
    return null;
  }
  public void setAction(Action a) {

  }
  /**
   *
   * @param ag ActionGroup
   * @return Action
   */
  public Action getContextAction(ActionGroup ag) {
    return null;
  }
  public int getWindowCount() {
    return this.cbxWindowList.getItemCount();
  }
  public void firePropertyChange(String propertyName, Object cw, Object newValue) {
    super.firePropertyChange(propertyName,cw,newValue);
  }
  public void setExtendedState(int stat) {
    this.ViewDevice.setExtendedState(this,stat);
  }
  IWindow LAST_ACTIVE_WINDOW = null;
  /**
   * 激活某一个窗口
   * @param cw IWindow
   */
  public void activeWindow(IWindow cw) {
//    if ( !cw.equals(LAST_ACTIVE_WINDOW) )
//      tbContent.setSelectedComponent(cw.getWindowComponent());
  }
  /**
   *
   * @return IWindow
   */
  public IWindow getActiveWindow() {
    IWindow win = null;
//    Component comp = tbContent.getSelectedComponent();
//    if ( comp instanceof IWindow )
//      win = (IWindow)comp;
    return win;
  }
  public IWindow getWindow(int index) {
    IWindow win = null;
//    win = (IWindow)this.tbContent.getComponentAt(index);
    return win;
  }

  public WindowGroup[] getWindowGroups() {
    return null;
  }
  public void showView(Object context) {}
  public void hideView(){}
  public void initView(Object node){}
  public int getResult(){return -1;}
  public void removeWindowAction() {}
  public void insertWindowAction(IWindow win) {}
  protected int viewStatus = -1;
  /**
   *
   * @return int
   */
  public int getViewStatus() {
    return viewStatus;
  }
  public void setViewStatus(int status) {
    viewStatus = status;
  }
}
