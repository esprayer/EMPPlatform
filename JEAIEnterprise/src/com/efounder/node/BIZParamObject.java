package com.efounder.node;

import com.efounder.eai.data.*;
import com.efounder.model.biz.BIZContext;
import java.util.Hashtable;
import java.util.List;

public class BIZParamObject extends JParamObject implements BIZContext {
  /**
   *
   */
  public BIZParamObject() {
    this.EnvRoot = (Hashtable)GPO.clone();
  }
  /**
   *
   * @param object Object
   * @param object1 Object
   */
  public void callBack(Object object, Object object1) {

  }

  public void enumBIZKey(List list) {
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

  public String getBIZUnit() {
	return null;
  }

  public String getBIZType() {
	return null;
  }

  public String getDateType() {
	return null;
  }

  public String getBIZSDate() {
	return null;
  }

  public String getBIZEDate() {
	return null;
  }
}
