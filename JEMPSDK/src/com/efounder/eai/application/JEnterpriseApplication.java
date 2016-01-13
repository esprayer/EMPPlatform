package com.efounder.eai.application;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.RootPaneContainer;

import com.efounder.eai.EAI;
import com.efounder.eai.resource.JEnterpriseResource;
import com.efounder.pfc.application.JApplication;
import com.efounder.pfc.window.IWindowCustom;
import com.efounder.pfc.window.JMainWindow;
import com.efounder.resource.JResource;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JEnterpriseApplication extends JApplication implements IEnterpriseApplication,IWindowCustom {
  public JMainWindow MainWindow = null;
  public JApplicationStub AStub = null;
  public JEnterpriseApplication() {
  }
  public int Execute(Object Param,Object[] Array) {
    AStub = (JApplicationStub)Param;
    InitWindow(AStub,Param,Array);
    ShowWindow(AStub);
    setMainWindow(AStub.EnterpriseWindow);
    try {
      // ���ò�Ʒ������
      EAI.BOF.InvokeObjectMethod(EAI.Product, EAI.Product,this,AStub.EnterpriseWindow,AStub,Array);
    } catch ( Exception e ) {}
    return 0;
  }
  protected void InitWindow(JApplicationStub AStub,Object Param,Object[] Array) {
    if ( AStub.EnterpriseWindow != null ) {
      MainWindow = AStub.EnterpriseWindow;
      return;
    }
    try {
      AStub.EnterpriseWindow = (JMainWindow)Class.forName(AStub.mainwindow).newInstance();
      AStub.EnterpriseWindow.addWindowCustomEvent(this);
      AStub.EnterpriseWindow.addWindowCustomEvent(this);
      MainWindow = AStub.EnterpriseWindow;
      MainWindow.setApplication(this);
      MainWindow.InitMainWindow(this, Param, Array, AStub);
      URL url = JResource.getResource(this,"/"+AStub.name+"/Resource",AStub.icon,EAI.getLanguage());
      if ( url != null )
        AStub.EnterpriseIcon = new ImageIcon(url);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  protected void ShowWindow(JApplicationStub AStub) {
    if ( MainWindow == null ) return;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    MainWindow.setSize(screenSize.width,screenSize.height-30);
    if ( AStub.EnterpriseIcon != null )
      MainWindow.setIconImage(AStub.EnterpriseIcon.getImage());
    String Text  = JEnterpriseResource.GetString(AStub.name,AStub.name,AStub.des);//+" "+AStub.ver;
    String Text1 = JEnterpriseResource.GetString(EAI.Product,EAI.Product,null);
    if ( Text1 != null ) Text = Text +"-"+Text1;
    MainWindow.setTitle(Text);
    MainWindow.setExtendedState(MainWindow.MAXIMIZED_BOTH);
    MainWindow.setVisible(canShowMainWindow());
  }
  protected boolean canShowMainWindow() {
    RootPaneContainer rootPaneContainer = (RootPaneContainer)System.getProperties().get("RootPaneContainer");
    return ( rootPaneContainer == null );
  }
  public void WindowOpenEvent(Object window,Object o) {
//    JEnterpriseStatusPanel ESP = new JEnterpriseStatusPanel();
//    JComponent pnStatus = ((JMainWindow)window).getStatus();
//    if ( pnStatus != null ) {
//      pnStatus.add(ESP,BorderLayout.CENTER);
//    }
  }
  public void WindowCloseEvent(Object window,Object o) {

  }
  public void WindowCustomEvent(Object window,Object o) {

  }
}
