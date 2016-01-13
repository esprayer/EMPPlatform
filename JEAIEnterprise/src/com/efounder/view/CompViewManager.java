package com.efounder.view;

import java.util.*;
import com.core.xml.*;
import java.awt.Component;
import com.efounder.node.Context;

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
public class CompViewManager {
  /**
   *
   */
  public static final String COMPVIEWFACTORY = "CompViewFactory";
  /**
   *
   */
  protected CompViewManager() {
  }
  public static List getComponentView(String key,Object contextObject,Object compObject) {
    return getComponentView(key,contextObject,compObject,null);
  }
  /**
   *
   * @param key String
   * @param contextObject Object
   * @param compObject Object
   * @return List
   */
  public static List getComponentView(String key,Object contextObject,Object compObject,Context context) {
    List vfList = getCompViewFactory(key);
    List viewList = new ArrayList();
    CompViewFactory cvf = null;ComponentView cv = null;
    for(int i=0;i<vfList.size();i++) {
      cvf = (CompViewFactory)vfList.get(i);
      if ( cvf.canDisplay(contextObject,compObject) ) {
        cv = cvf.createComponent(contextObject,compObject,context);
        cv.initComponentView(cvf,cvf.getStubObject());
        if ( contextObject instanceof Component )
          cv.setParentView((Component)contextObject);
        viewList.add(cv);
      }
    }
    return viewList;
  }
  /**
   *
   * @return List
   */
  public static List getCompViewFactory(String key) {
    java.util.List vfList = new ArrayList();
    java.util.List fstubList = PackageStub.getContentVector(COMPVIEWFACTORY);
    StubObject so = null;CompViewFactory cvf = null;
    for(int i=0;fstubList!=null&&i<fstubList.size();i++) {
      so = (StubObject)fstubList.get(i);
      // 如果两个Key相等
      if ( key.equals(so.getString("key",null)) ) {
        cvf = createCompViewFactory(so);
        vfList.add(cvf);
      }
    }
    return vfList;
  }
  /**
   *
   * @param so StubObject
   * @return CompViewFactory
   */
  protected static CompViewFactory createCompViewFactory(StubObject so) {
    String clazz = so.getString("clazz",null);
    if ( clazz == null || "".equals(clazz) ) return null;
    CompViewFactory cvf = null;
    try {
      cvf = (CompViewFactory) Class.forName(clazz).newInstance();
      cvf.setStubObject(so);
    } catch ( Exception e ) {e.printStackTrace();}
    return cvf;
  }
}
