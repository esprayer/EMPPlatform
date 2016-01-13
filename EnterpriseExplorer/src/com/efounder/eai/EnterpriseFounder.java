package com.efounder.eai;

import java.util.*;

import com.efounder.eai.application.*;
import com.core.xml.*;
import com.efounder.eai.data.*;
import javax.swing.*;
import com.efounder.eai.ide.*;
/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class EnterpriseFounder extends JEnterpriseApplication {
  public static final String InitOpenEnterpriseExplorer = "InitOpenEnterpriseExplorer";
  private static java.util.List OpenEnterpriseExplorerList = null;
  private static java.util.List StartOpenEnterpriseExplorerList = null;
  private static java.util.List EndOpenEnterpriseExplorerList = null;

  static {
    initUI();
  }

  private static void loadClazz(List ClazzList,String UITag) {
    try {
      StubObject SO = null;      Class clazz = null;
      String CustomClass = null;
      List UIList = PackageStub.getContentVector(UITag);
      if ( UIList == null ) return;
      for (int i = 0; i < UIList.size(); i++) {
        SO = (StubObject) UIList.get(i);
        CustomClass = SO.getString("class", null);
        if (CustomClass != null && !"".equals(CustomClass)) {
          try {
            clazz = Class.forName(CustomClass);
            ClazzList.add(clazz);
          }catch (Exception e) {e.printStackTrace();}
        }
      }
    } catch ( Exception ee ) {ee.printStackTrace();}
  }
  private static void initUI() {
    StartOpenEnterpriseExplorerList = new ArrayList();
    OpenEnterpriseExplorerList      = new ArrayList();
    EndOpenEnterpriseExplorerList   = new ArrayList();
    loadClazz(StartOpenEnterpriseExplorerList,"startUI");
    loadClazz(OpenEnterpriseExplorerList,"UI");
    loadClazz(EndOpenEnterpriseExplorerList,"endUI");
  }
  /**
   *
   */
  public EnterpriseFounder() {

  }
  /**
   *
   * @param key String
   * @param OEEClass Class
   */
  public static void registerOEE(Class OEEClass) {
    OpenEnterpriseExplorerList.add(OEEClass);
  }
  /**
   *
   * @param key String
   * @return Object
   */
  public static Class unregisterOEE(Class OEEClass) {
    OpenEnterpriseExplorerList.remove(OEEClass);
    return OEEClass;
  }
  /**
   *
   * @return Hashtable
   */
  public static java.util.List getOEEList() {
    return OpenEnterpriseExplorerList;
  }
  /**
   *
   * @param args String[]
   */
  public static void main(String[] args) {

  }
  private static void callUI(java.util.List UIList,Object[] OArray) {
    Object OEEArray[] = UIList.toArray();
    if ( OEEArray == null ) return;Class cls;
    for(int i=0;i<OEEArray.length;i++) {
      cls = (Class)OEEArray[i];
      JBOFClass.CallClassMethod(cls,InitOpenEnterpriseExplorer,OArray);
    }
  }
  /**
   *
   * @param args String[]
   */
  void InitOpenEnterpriseExplorer(Object args) {
    Object OArray[] = {this,args,null,null};
    callUI(StartOpenEnterpriseExplorerList,OArray);
    callUI(OpenEnterpriseExplorerList,OArray);
    callUI(EndOpenEnterpriseExplorerList,OArray);
  }
  public int Execute(Object Param,Object[] Array) {
    initIcon();
//    initLog();
    InitOpenEnterpriseExplorer(Param);
    initPO();

    //
    int code = super.Execute(Param,Array);




//      SwingUtilities.invokeLater(new Runnable() {
//        public void run() {
//          try {
//            // 是否需要？
//            if ( EnterpriseExplorer.getExplorer().getRootPaneContainer() instanceof Component ) {
//                SwingUtilities.updateComponentTreeUI((Component)EnterpriseExplorer.getExplorer().getRootPaneContainer());
//              }
//
//          } catch (Exception e) {
//            e.printStackTrace();
//          }
//        }
//      });

    return code;
  }

  private void initLog() {
    org.openide.TopLogging.getDefault();
  }
  private void initPO() {
    JParamObject.setObject("Application",EAI.Application);
    JParamObject.setObject("Product",EAI.Product);
    JParamObject.setObject("Language",EAI.getLanguage());
  }
  private void initIcon() {
    Icon icon = null;
    icon = ExplorerIcons.getExplorerIcon("FC.gif");
    if ( icon != null )
      System.getProperties().put("RES_ICON",icon);
    icon = ExplorerIcons.getExplorerIcon("FM.gif");
    if ( icon != null )
      System.getProperties().put("MAX_ICON",icon);
//    GENERICCLOSE.jpg
//    READONLY_CLOSE.jpg
  }
}
