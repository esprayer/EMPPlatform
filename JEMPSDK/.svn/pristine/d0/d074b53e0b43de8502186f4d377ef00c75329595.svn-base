package com.efounder.eai.framework.bof;

import com.efounder.eai.framework.*;
import jframework.foundation.classes.JActiveDComDM;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBusinessActiveFramework extends JActiveFramework implements IBusinessActiveFramework {
  /**
   *
   */
  public JBusinessActiveFramework() {
    AcitveFrameworkName = "BusinessAcitveFramework";
    setCLSID_Type("CLSID_BusinessActiveObjectCategory");
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
    return JActiveDComDM.BusinessActiveFramework.InvokeObjectMethod(
        ActiveObjectName,ActiveObjectMethodName,Param,Data,
        CustomObject,AdditiveObject);
  }

	@Override
	public Object InvokeFrameworkMethod(String ActiveFrameworkMethodName,
			Object Param, Object Data, Object CustomObject,
			Object AdditiveObject) throws Exception {
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
