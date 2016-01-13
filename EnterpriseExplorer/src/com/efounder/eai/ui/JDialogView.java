package com.efounder.eai.ui;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.efounder.action.*;
import com.efounder.eai.*;
import com.efounder.pfc.dialog.*;
import com.efounder.pfc.window.*;
import com.efounder.node.*;
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
 * @author Skyline
 * @version 1.0
 */
public class JDialogView extends JPDialog implements IView,IViewDevice,ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  JPanel rootContext = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
//  JPanel pnBottom = new JPanel();
  JNormalViewPanel pnContent = null;
  FlowLayout flowLayout1 = new FlowLayout();
//  JButton bnCancel = new JButton();
//  JButton bnOK = new JButton();
  /**
   *
   */
  protected void initContext() {
    pnContent = new JNormalViewPanel();
    pnContent.setViewDevice(this);
    pnContent.setID("DialogView");
    this.openView(pnContent,BorderLayout.CENTER,IView.VIEW_NO_TITLE);
  }
  public JDialogView(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      initContext();
      initBottom();
      initLayer();
      initEvent();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public JDialogView() {
    this(EAI.EA.getMainWindow(),EAI.EA.getMainWindow().getTitle(), true);
  }
  private void initEvent() {
    DialogViewAdapter dva = new DialogViewAdapter(this);
    this.addWindowListener(dva);
//    this.bnOK.addActionListener(this);
//    this.bnCancel.addActionListener(this);
  }
  private void initBottom() {
//    pnBottom.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setVgap(0);
    flowLayout1.setHgap(0);
//    bnCancel.setMnemonic('C');
//    bnCancel.setText("取消(C)");
//    bnOK.setMnemonic('O');
//    bnOK.setText("确定(O)");
//    pnBottom.add(bnOK);
//    pnBottom.add(bnCancel);
  }
  private void jbInit() throws Exception {
    rootContext.setLayout(borderLayout1);
    getContentPane().add(rootContext);

  }
  private void initLayer() {
//    rootContext.add(pnBottom, java.awt.BorderLayout.SOUTH);
  }
  public JComponent getViewComponent() {
    return pnContent.getViewComponent();
  }

  public void setStyle(int style) {
//    pnContent.setStyle(style);
  }

  public int getStyle() {
//    return pnContent.getStyle();
    return 0;
  }

  public void setVisible(IWindow cw, boolean v) {
//    pnContent.setVisible(cw,v);
  }

  public boolean isVisible(IWindow cw) {
//    return pnContent.isVisible(cw);
    return true;
  }

  public void openChildWindow(IWindow cw) {
    openChildWindow(cw,cw.getTitle(),cw.getTips(),cw.getIcon());
  }

  public void openChildWindow(IWindow cw, String title, String tips, Icon icon) {
    openChildWindow(cw,title,tips,icon,IWindow.WINDOW_NO_TITLE);
  }
  IWindow oneWindow = null;
  public void openChildWindow(IWindow cw, String title, String tips, Icon icon,
                              int Style) {
    pnContent.openChildWindow(cw,title,tips,icon,Style,true);
//    Component comp = pnContent.getTabbedPane().getComponent(0);
    pnContent.add(cw.getWindowComponent(),BorderLayout.CENTER);
    oneWindow = cw;
    cw.activeWindowEvent(this);
  }
  public boolean isOpen(IWindow cw) {
    return false;
  }
  public IWindow closeChildWindow(IWindow cw) {
    return closeChildWindow(cw,false);
  }

  public IWindow closeChildWindow(IWindow cw, boolean mustClose) {
//    pnContent.getTabbedPane().add(cw.getWindowComponent());
    return pnContent.closeChildWindow(cw,mustClose);
  }

  public void setIcon(Icon icon) {
    pnContent.setIcon(icon);

//    this.setIconImage((ImageIcon)icon);
  }

  public void setViewPlacement(int viewPlacement) {
    pnContent.setViewPlacement(viewPlacement);
  }

  public int getViewPlacement() {
    return pnContent.getViewPlacement();
  }

  public Icon getIcon() {
    return pnContent.getIcon();
  }

  public String getID() {
    return pnContent.getID();
  }

  public void setID(String ID) {
    pnContent.setID(ID);
  }

  public void setViewDevice(IViewDevice vd) {
    pnContent.setViewDevice(vd);
  }

  public IViewDevice getViewDevice() {
    return pnContent.getViewDevice();
  }

  public void setLocation(int l) {
//    pnContent.setLocation(l);
  }

  public void setLocation(double w) {
//    pnContent.setLocation(w);
  }

  public void setLayer(String layer) {
    pnContent.setLayer(layer);
  }
  public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    super.firePropertyChange(propertyName,oldValue,newValue);
    if ( pnContent != null )
      pnContent.firePropertyChange(propertyName,oldValue,newValue);
  }
  public String getLayer() {
    return pnContent.getLayer();
  }

  public void putObject(Object Key, Object Value) {
    pnContent.putObject(Key,Value);
  }

  public Object getObject(Object Key, Object Def) {
    return pnContent.getObject(Key,Def);
  }

  public Action getAction() {
    return pnContent.getAction();
  }

  public void setAction(Action a) {
    pnContent.setAction(a);
  }

  public void setExtendedState(int stat) {
    pnContent.setExtendedState(stat);
  }

  public void activeWindow(IWindow cw) {
    pnContent.activeWindow(cw);
  }

  public IWindow getActiveWindow() {
    return pnContent.getActiveWindow();
  }

  public IWindow getWindow(int index) {
    return oneWindow;
//    return pnContent.getWindow(index);
  }

  public Action getContextAction(ActionGroup ag) {
    return pnContent.getContextAction(ag);
  }

  public int getWindowCount() {
    return 1;//pnContent.getWindowCount();
  }

  public WindowGroup[] getWindowGroups() {
    return pnContent.getWindowGroups();
  }
  public void showView(Object ctx) {
    Context context = (Context)ctx;
    Dimension d = (Dimension)context.getObject("viewSize",null);
    if ( d == null )
      this.pack();
    else
      this.setSize(d);
    String caption = context.getString("viewCaption",null);
    if ( caption != null ) this.setTitle(caption);
    this.CenterWindow();
    this.show();
  }
  public void hideView(){
    this.hide();
  }

  public JComponent getDeviceComponent() {
    return null;
  }

  public void openView(IView comp, int layer) {
  }

  public void openView(IView comp, int layer, int Style) {
    rootContext.add(comp.getViewComponent(), java.awt.BorderLayout.CENTER);
  }
  public void initView(Object node){

  }
  public void setVisible(IView comp, boolean v) {

  }

  public boolean isVisible(IView comp) {
    return true;
  }

  public void openView(IView comp, String layer) {
    openView(comp,layer,0);
  }

  public void openView(IView comp, String layer, int Style) {
    rootContext.add(comp.getViewComponent(), java.awt.BorderLayout.CENTER);
  }

  public void setLocation(IView comp, int l) {
  }

  public void setLocation(IView comp, double w) {
  }

  public void setExtendedState(IView comp, int stat) {
  }



  public IView getTopView() {
    return null;
  }

  public IView getContentView() {
    return null;
  }

  public IView getStructView() {
    return null;
  }

  public IView getPropertyView() {
    return null;
  }

  public IView getMessageView() {
    return null;
  }

  public IView getExplorerView() {
    return null;
  }
  /**
   *
   * @return boolean
   */
  public boolean closeAllWindow() {
    IWindow win = null;java.util.List winList = new ArrayList();
    int count = getWindowCount();
    for(int i=0;i<count;i++) {
      win = (IWindow)getWindow(i);
      winList.add(win);
    }
    for(int i=0;i<winList.size();i++) {
      win = (IWindow)winList.get(i);
      if ( closeChildWindow(win) == null ) {
        return false;
      }
    }
    return true;
  }
  /**
   *
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
//    if ( bnOK.equals(e.getSource()) ) {
//      DialogViewAdapter.closeDialogView(this);
//      super.OnOk();
//    }
//    if ( bnCancel.equals(e.getSource()) ) {
//      DialogViewAdapter.closeDialogView(this);
//      super.OnCancel();
//    }
  }
  public int getResult(){
    return this.Result;
  }
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

  public void setViewState(IView comp, int stat) {
  }
}
