package com.efounder.node;

import java.util.*;

import com.efounder.model.biz.*;
import com.efounder.eai.data.*;

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
public class EnvBIZContext implements BIZContext {
  protected static BIZContext bizContext = null;
  public static BIZContext getBIZContext() {
    if ( bizContext == null ) bizContext = new EnvBIZContext();
    return bizContext;
  }
  protected EnvBIZContext() {
  }

  /**
   * callBack
   *
   * @param object Object
   * @param object1 Object
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public void callBack(Object object, Object object1) {
  }

  /**
   * enumBIZKey
   *
   * @param list List
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public void enumBIZKey(List list) {
  }

  /**
   * getBIZEDate
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZEDate() {
    return null;
  }

  /**
   * getBIZSDate
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZSDate() {
    return (String)JParamObject.getObject("LoginDate",null);
  }

  /**
   * getBIZType
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZType() {
    return null;
  }

  /**
   * getBIZUnit
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getBIZUnit() {
    return (String)JParamObject.getObject("USER_DEF_DWZD_BH",null);
  }

  /**
   * getBIZValue
   *
   * @param object Object
   * @param object1 Object
   * @return Object
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public Object getBIZValue(Object object, Object object1) {
    return JParamObject.getObject(object,object1);
  }

  /**
   * getDateType
   *
   * @return String
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public String getDateType() {
    return null;
  }

  /**
   * setBIZValue
   *
   * @param object Object
   * @param object1 Object
   * @todo Implement this com.efounder.model.biz.BIZContext method
   */
  public void setBIZValue(Object object, Object object1) {
  }
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public void   initBIZContext(Object sourceObject,Object contextObject,Object addinObject){};
  public void   changeEvent(int eventType,Object sourceObject,Object contextObject,Object addinObject){};
  public JParamObject convertParamObject(Object userObject,JParamObject po){return po;}
  public void setCallBackValue(Object key,Object value){}
  public java.util.Map getCallBackMap(){return null;}
  public void addBIZContext(BIZContext bizContext) {}

  public String getPrepareAttString() {
    return null;
  }
}
