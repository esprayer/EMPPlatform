package com.efounder.eai.framework.dof;

import com.efounder.eai.framework.JActiveFramework;
import jframework.foundation.classes.JActiveDComDM;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDataActiveFramework extends JActiveFramework implements IDataActiveFramework {
  /**
   *
   */
  public JDataActiveFramework() {
    AcitveFrameworkName = "DataActiveFramework";
    setCLSID_Type("CLSID_DataActiveObjectCategory");
    setTier("Client");
  }
  /**
   *
   * @param ActiveObjectName String
   * @param ActiveObjectMethodName String
   * @param Param Object
   * @param Data Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   * @throws Exception
   */
  public Object callOldFwkObject(String ActiveObjectName,String ActiveObjectMethodName,
                                 Object Param,
                                 Object Data,
                                 Object CustomObject,
                                 Object AdditiveObject) throws Exception {
    return JActiveDComDM.DataActiveFramework.InvokeObjectMethod(
        ActiveObjectName,ActiveObjectMethodName,Param,Data,
        CustomObject,AdditiveObject);
  }
@Override
public Object InvokeFrameworkMethod(String ActiveFrameworkMethodName,
		Object Param, Object Data, Object CustomObject, Object AdditiveObject)
		throws Exception {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Object CallObjectMethod(String ActiveObjectName,
		String ActiveObjectMethodName, Object Param, Object Data,
		Object CustomObject, Object AdditiveObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Object MInvokeObjectMethod(String ActiveObjectName,
		String ActiveObjectMethodName, Object Param, Object Data,
		Object CustomObject, Object AdditiveObject) throws Exception {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Object Initialize(String Param, String Data, Object CustomObject,
		Object AdditiveObject) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void GetFrameworkGUID() {
	// TODO Auto-generated method stub
	
}
@Override
public Object QueryInterface(String GUID) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public String getObjectName() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void setObjectName(String ObjectName) {
	// TODO Auto-generated method stub
	
}
@Override
public String getObjectGUID() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void setObjectGUID(String ObjectGUID) {
	// TODO Auto-generated method stub
	
}
@Override
public int getInterfaceType() {
	// TODO Auto-generated method stub
	return 0;
}
}
