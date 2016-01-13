package com.efounder.eai.framework.dal;

import com.efounder.eai.framework.JActiveFramework;
import com.efounder.eai.*;
import jframework.foundation.classes.JActiveDComDM;
import com.efounder.eai.data.JParamObject;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */

public class JAbstractDataActiveFramework extends JActiveFramework implements IAbstractDataActiveFramework {
	public JAbstractDataActiveFramework() {
		AcitveFrameworkName = "AbstractDataActiveFramework";
		setCLSID_Type("CLSID_AbstractDataActiveObjectCategory");
		setTier("Middle");
	}

	// ------------------------------------------------------------------------------------------------
	// ����:
	// ���: Skyline(2001.12.29)
	// ʵ��: Skyline
	// �޸�:
	// ------------------------------------------------------------------------------------------------
//	public Object CallObjectMethod(String ActiveObjectName, String ActiveObjectMethodName, Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
//		String eaiServer = null;
//		// if ( Param instanceof JParamObject ) {
//		// eaiServer = ((JParamObject)Param).getEAIServer(null);
//		// }
//		// if ( ActiveObjectName.indexOf(".") == -1 && ( eaiServer == null ||
//		// eaiServer.trim().length() == 0 ))
//		// return
//		// super.CallObjectMethod(ActiveObjectName,ActiveObjectMethodName,
//		// Param,Data,CustomObject,AdditiveObject);
//		// else {
//		// if ( ActiveObjectName.indexOf(".") == -1 && eaiServer != null &&
//		// eaiServer.trim().length() > 0 ) {
//		// ActiveObjectName = eaiServer+"."+ActiveObjectName;
//		// }
//		// Object[] param = {Param, Data, CustomObject, AdditiveObject};
//		// return EAI.DAL.InvokeObjectMethod("CommObject",
//		// "RemoteObjectMethodAgent",
//		// ActiveObjectName,
//		// ActiveObjectMethodName, param, null);
//		// }
//		return null;
//	}

	// ------------------------------------------------------------------------------------------------
	// ����:
	// ���: Skyline(2001.12.29)
	// ʵ��: Skyline
	// �޸�:
	// ------------------------------------------------------------------------------------------------
	public Object callOldFwkObject(String ActiveObjectName, String ActiveObjectMethodName, Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
		return JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(ActiveObjectName, ActiveObjectMethodName, Param, Data, CustomObject, AdditiveObject);
	}

	@Override
	public Object InvokeFrameworkMethod(String ActiveFrameworkMethodName, Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object MInvokeObjectMethod(String ActiveObjectName, String ActiveObjectMethodName, Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Object Initialize(String Param, String Data, Object CustomObject, Object AdditiveObject) {
		GetFrameworkGUID();
	    LoadAcitveObjects();
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
