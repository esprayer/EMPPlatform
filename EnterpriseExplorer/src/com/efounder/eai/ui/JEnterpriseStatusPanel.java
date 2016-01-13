package com.efounder.eai.ui;

import java.awt.*;
import javax.swing.*;

import com.efounder.pfc.window.*;
import org.openide.util.*;
import java.util.*;
import com.core.xml.*;
import org.openide.*;
import com.efounder.eai.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JEnterpriseStatusPanel extends JPanel implements IWindowStatus,Runnable {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  JPanel pnRight = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnComponent = new JPanel();
  JLabel lbDateTime = new JLabel();
  FlowLayout flowLayout2 = new FlowLayout();
  JPanel pnContent = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel pnContainer = new JPanel();
  JPanel pnStatus = new JPanel();
  FlowLayout flowLayout3 = new FlowLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel lbText = new JLabel();

  public JEnterpriseStatusPanel() {
    try {
      jbInit();
      initTime();
      initStatus("StatusComponentFactory");
      initStatus("StatusContainerFactory");
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public void reInitStatus(){
    pnComponent.removeAll();
    pnContainer.removeAll();
    initStatus("StatusComponentFactory");
    initStatus("StatusContainerFactory");
    this.setVisible(false);
    this.setVisible(true);
  }
  private void initStatus(String Type) {
    StubObject SO = null;
    Vector statusList = PackageStub.getContentVector(Type);
    for(int i=0;i<statusList.size();i++) {
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
      JComponent[] comps = cf.createComponent(this);
      JComponent comp;
      for(int i=0;i<comps.length;i++) {
        comp = comps[i];
        comp.setToolTipText(Caption);
        if ("StatusComponentFactory".equals(Type))
          this.registryComponent(comp,SO.getString("id",""));
        if ("StatusContainerFactory".equals(Type))
          this.registryContainer( (Container) comp,SO.getString("id",""));
      }
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(e);
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    pnRight.setLayout(flowLayout1);
    pnRight.setOpaque(false);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnRight.setBorder(BorderFactory.createLoweredBevelBorder());
    pnComponent.setLayout(flowLayout2);
    pnComponent.setOpaque(false);
    flowLayout2.setAlignment(FlowLayout.RIGHT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    pnContent.setLayout(borderLayout2);
    pnContent.setOpaque(false);
    pnContainer.setLayout(flowLayout3);
    pnContainer.setOpaque(false);
    flowLayout3.setAlignment(FlowLayout.RIGHT);
    flowLayout3.setHgap(0);
    flowLayout3.setVgap(0);
    pnStatus.setLayout(borderLayout3);
    pnStatus.setOpaque(false);
    pnComponent.setBorder(null);
    this.add(pnRight, BorderLayout.EAST);
    pnRight.add(pnComponent, null);
    pnRight.add(lbDateTime, null);
    this.add(pnContent,  BorderLayout.CENTER);
    pnContent.add(pnContainer,  BorderLayout.EAST);
    pnContent.add(pnStatus,  BorderLayout.CENTER);
    pnStatus.add(lbText, BorderLayout.WEST);
    lbText.setOpaque(false);
    //
    System.getProperties().put("WindowStatus",this);
    if ( EAI.getSecurity() ) {
      this.setBackground(Color.yellow.darker());
    }
  }
  public void setText(int index,String text) {

  }
  public String getText(int index) {
    return null;
  }
  public void setText(String Text) {
    lbText.setText(Text);
  }
  public void setIcon(Icon icon) {
    lbText.setIcon(icon);
  }
  public JComponent getStatusComp() {
    return this;
  }
  public void startProgress(int max,String text) {
    return;
  }
  public void incProgress(int step,String text) {
    return;
  }
  public void endProgress(String text) {
    return;
  }
  public void clearProgress() {
    return;
  }
  public void beginWait(long time) {
    return;
  }
  public void endWait() {
    return;
  }
  public Component registryComponent(Component comp) {
    this.pnComponent.add(comp);
    return comp;
  }
  public Component registryComponent(Component comp,Object ID) {
    comp.setName(ID==null?"":ID.toString());
    registryComponent(comp);
    return comp;
  }
  public Component unregistryComponent(Component comp) {
    this.pnComponent.remove(comp);
    return comp;
  }
  public Container registryContainer(Container comp) {
    this.pnContainer.add(comp);
    return comp;
  }
  public Container registryContainer(Container comp,Object ID) {
    comp.setName(ID==null?"":ID.toString());
    registryContainer(comp);
    return comp;
  }
  public Container unregistryContainer(Container comp) {
    this.pnContainer.remove(comp);
    return comp;
  }
  public Component getContainer(Object ID){
    Component[] comps= this.pnContainer.getComponents();
    Component comp = null;
    for (int i=0;i<comps.length;i++){
      if(comps[i].getName().equals(ID)){
        comp = comps[i];
      }
    }
    return comp;
  }
  public Component getComponent(Object ID){
    Component[] comps= this.pnComponent.getComponents();
    Component comp = null;
    for (int i=0;i<comps.length;i++){
      String name = comps[i].getName();
      if (name==null) name = "";
      if(name.equals(ID)){
        comp = comps[i];
      }
    }
    return comp;
  }
  RequestProcessor.Task timeStatusTask;
  private void initTime() {
    timeStatusTask = RequestProcessor.getDefault().create(this);
    timeStatusTask.schedule(0);
  }
  /**
   *
   */
  public void run() {
    try {
        lbDateTime.setText(getDateTime());
        timeStatusTask.schedule(1000*60);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  private String getDateTime() {
    Calendar Cale = Calendar.getInstance();
    String value = Cale.get(Cale.YEAR)+"-"+(Cale.get(Cale.MONTH)+1)+"-"+Cale.get(Cale.DAY_OF_MONTH)+" "+Cale.get(Cale.HOUR_OF_DAY)+":";
    int m = Cale.get(Cale.MINUTE);
    if ( m < 10 ) value += "0"+m;else value += String.valueOf(m);
    return value;
//    return Cale.toString();
  }

  public void setText(String compName, String text) {
  }

  public void setIcon(String compName, Icon icon) {
  }

  public void setToolTipText(String compName, String text) {
  }

  //  public Component registryComponent(Component comp, Object Id) {
//    return null;
//  }
//
//  public Container registryContainer(Container comp, Object Id) {
//    return null;
//  }
//
//  public Component getContainer(Object ID) {
//    return null;
//  }
//
//  public Component getComponent(Object ID) {
//    return null;
//  }
}
