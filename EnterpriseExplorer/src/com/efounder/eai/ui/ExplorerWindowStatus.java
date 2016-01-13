package com.efounder.eai.ui;

import java.awt.*;
import javax.swing.*;

import com.efounder.pfc.window.*;
import com.jidesoft.status.StatusBar;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.status.ProgressStatusBarItem;
import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.OvrInsStatusBarItem;
import java.awt.event.ActionEvent;
import com.jidesoft.status.TimeStatusBarItem;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.status.ButtonStatusBarItem;
import com.jidesoft.status.MemoryStatusBarItem;
import java.awt.event.ActionListener;
import com.core.xml.PackageStub;
import org.openide.ErrorManager;
import java.util.Vector;
import com.core.xml.StubObject;
import com.core.xml.*;
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
public class ExplorerWindowStatus implements IWindowStatus {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  /**
   *
   */
  public ExplorerWindowStatus() {
  }
  protected ProgressStatusBarItem progress = null;
  private Timer _timer;
//  protected LabelStatusBarItem progress = null;
  /**
   *
   * @param Type String
   */
  private void initExtStatus(String Type) {
    StubObject SO = null;
    Vector statusList = PackageStub.getContentVector(Type);
    for(int i=0;statusList!=null&&i<statusList.size();i++) {
      SO = (StubObject)statusList.get(i);
      initStubObject(SO,Type);
    }
  }
  private void initStubObject(StubObject SO,String Type) {
    String clazz = SO.getString("clazz",null);
    if ( clazz == null || "".equals(clazz.trim()) ) return;
    String Caption = SO.getString("caption",null);
    try {
      Class clsFactory = Class.forName(clazz);
      ComponentFactory cf = (ComponentFactory)clsFactory.newInstance();
      JComponent[] comps = cf.createComponent(this.statusBar);
      JComponent comp;
      for(int i=0;i<comps.length;i++) {
        comp = comps[i];
//        comp.setToolTipText(Caption);
        if ("StatusComponentFactory".equals(Type))
          this.registryComponent(comp,JideBoxLayout.FLEXIBLE);
        if ("StatusContainerFactory".equals(Type))
          this.registryContainer( (Container) comp,SO.getString("id",""));
      }
    } catch ( Exception e ) {
      e.printStackTrace();
//      ErrorManager.getDefault().notify(e);
    }
  }
  /**
   *
   */
  public void initStatus() {
      progress = new ProgressStatusBarItem();
      progress.setCancelCallback(new ProgressStatusBarItem.CancelCallback() {
          public void cancelPerformed() {
              _timer.stop();
              _timer = null;
              progress.setStatus("Cancelled");
              progress.showStatus();
          }
      });
      registryComponent(progress, JideBoxLayout.VARY);

      // 初始化扩展状态栏
      initExtStatus("StatusComponentFactory");

//      ButtonStatusBarItem button = new ButtonStatusBarItem("READ-ONLY");
//      button.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.SAVE));
//      button.setPreferredWidth(20);
//      statusBar.add(button, JideBoxLayout.FLEXIBLE);
//
//      button.addActionListener(new ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//              if (_timer != null && _timer.isRunning())
//                  return;
//              _timer = new Timer(100, new ActionListener() {
//                  int i = 0;
//
//                  public void actionPerformed(ActionEvent e) {
//                      if (i == 0)
//                          progress.setProgressStatus("Initializing ......");
//                      if (i == 10)
//                          progress.setProgressStatus("Running ......");
//                      if (i == 90)
//                          progress.setProgressStatus("Completing ......");
//                      progress.setProgress(i++);
//                      if (i > 100)
//                          _timer.stop();
//                  }
//              });
//              _timer.start();
//          }
//      });
//
//      final LabelStatusBarItem label = new LabelStatusBarItem("Line");
//      label.setText("100:42");
//      label.setAlignment(JLabel.CENTER);
//      statusBar.add(label, JideBoxLayout.FLEXIBLE);
//
//      final OvrInsStatusBarItem ovr = new OvrInsStatusBarItem();
//      ovr.setPreferredWidth(100);
//      ovr.setAlignment(JLabel.CENTER);
//      statusBar.add(ovr, JideBoxLayout.FLEXIBLE);

      final TimeStatusBarItem time = new TimeStatusBarItem();
      java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance() ;
      time.setTextFormat(df);
      registryComponent(time, JideBoxLayout.FLEXIBLE);
      final MemoryStatusBarItem gc = new MemoryStatusBarItem();
      statusBar.add(gc, JideBoxLayout.FLEXIBLE);
  }
  /**
   *
   */
  protected StatusBar statusBar = null;
  /**
   *
   * @return StatusBar
   */
  public StatusBar getStatusBar() {
    return statusBar;
  }
  /**
   *
   * @param sb StatusBar
   */
  public void setStatusBar(StatusBar sb) {
    statusBar = sb;
  }
  /**
   * beginWait
   *
   * @param time long
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void beginWait(long time) {
  }

  /**
   * clearProgress
   *
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void clearProgress() {
  }

  /**
   * endProgress
   *
   * @param text String
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void endProgress(String text) {
  }

  /**
   * endWait
   *
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void endWait() {
  }

  /**
   * getComponent
   *
   * @param ID Object
   * @return Component
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Component getComponent(Object ID) {
    return null;
  }

  /**
   * getContainer
   *
   * @param ID Object
   * @return Component
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Component getContainer(Object ID) {
    return null;
  }

  /**
   * getStatusComp
   *
   * @return JComponent
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public JComponent getStatusComp() {
    return null;
  }

  /**
   * getText
   *
   * @param index int
   * @return String
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public String getText(int index) {
    return "";
  }

  /**
   * incProgress
   *
   * @param step int
   * @param text String
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void incProgress(int step, String text) {
  }

  /**
   * registryComponent
   *
   * @param comp Component
   * @return Component
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Component registryComponent(Component comp) {
    return null;
  }

  /**
   * registryComponent
   *
   * @param comp Component
   * @param Id Object
   * @return Component
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Component registryComponent(Component comp, Object Id) {
    this.statusBar.add(comp, Id);
    return comp;
  }

  /**
   * registryContainer
   *
   * @param comp Container
   * @return Container
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Container registryContainer(Container comp) {
//    this.statusBar.add(comp, Id);
//    return comp;
    return null;
  }

  /**
   * registryContainer
   *
   * @param comp Container
   * @param Id Object
   * @return Container
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Container registryContainer(Container comp, Object Id) {
    return null;
  }

  /**
   * setIcon
   *
   * @param icon Icon
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void setIcon(Icon icon) {
//    progress.setIcon(icon);
    progress.setStatusIcon(icon);
  }

  /**
   * setText
   *
   * @param index int
   * @param text String
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void setText(int index, String text) {
  }

  /**
   * setText
   *
   * @param Text String
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void setText(String Text) {
    progress.setStatus(Text);
//    progress.setText(Text);
  }

  /**
   * startProgress
   *
   * @param max int
   * @param text String
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public void startProgress(int max, String text) {
  }

  /**
   * unregistryComponent
   *
   * @param comp Component
   * @return Component
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Component unregistryComponent(Component comp) {
    return null;
  }

  /**
   * unregistryContainer
   *
   * @param comp Container
   * @return Container
   * @todo Implement this com.efounder.pfc.window.IWindowStatus method
   */
  public Container unregistryContainer(Container comp) {
    return null;
  }
  /**
   *
   * @param compName String
   * @param text String
   */
  public void setText(String compName, String text) {
    Component comp = findCompByName(this.statusBar,compName);
    if ( comp != null ) {
      JBOFClass.CallObjectMethod(comp,"setText",text);
    }
  }
  public void setToolTipText(String compName,String text) {
    Component comp = findCompByName(this.statusBar,compName);
    if ( comp != null ) {
      JBOFClass.CallObjectMethod(comp,"setToolTipText",text);
    }
  }
  /**
   *
   * @param compName String
   * @param icon Icon
   */
  public void setIcon(String compName, Icon icon) {
    Component comp = findCompByName(this.statusBar,compName);
    if ( comp != null ) {
      JBOFClass.CallObjectMethod(comp,"setIcon",icon);
    }
  }
  /**
   *
   * @param comp Component
   * @param name String
   * @return Component
   */
  public static Component findCompByName(Component comp,String name) {
    if ( comp == null ) return null;
    if ( name.equals(comp.getName()) ) return comp;
    if ( comp instanceof Container ) {
      Component[] comps = ((Container)comp).getComponents();
      for(int i=0;i<comps.length;i++) {
        Component cp = findCompByName(comps[i],name);
        if ( cp != null ) return cp;
      }
    }
    return null;
  }
}
