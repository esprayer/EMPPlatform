package com.efounder.bz.dbform.datamodel;

import com.efounder.eai.data.*;
import com.efounder.eai.*;

public class ServiceComponent {
	/**
	 *
	 */
	private String serviceKey;

	/**
	 *
	 */
	public ServiceComponent() {
	}

	public static ServiceComponent getIntance(String serviceKey) {
		ServiceComponent sc = new ServiceComponent();
		sc.serviceKey = serviceKey;
		return sc;
	}

	/**
	 *
	 */
	protected int runType = 0x0001;

	/**
	 * 
	 * @return int
	 */
	public int getRunType() {
		return runType;
	}

	/**
	 * 
	 * @param runType  int
	 */
	public void setRunType(int runType) {
		this.runType = runType;
	}

	/**
	 * 
	 * @param serviceKey  String
	 */
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	/**
	 * 
	 * @return String
	 */
	public String getServiceKey() {
		return serviceKey;
	}

	/**
	 * 
	 * @param paramObject    JParamObject
	 * @param dataObject     Object
	 * @param customObject   Object
	 * @param addinObject    Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public JResponseObject runService(JParamObject paramObject, Object dataObject, Object customObject, Object addinObject) throws Exception {
		if (serviceKey == null || serviceKey.trim().length() == 0)
			return null;
		return runEnterpriseService(serviceKey, runType, paramObject, dataObject, customObject, addinObject);
	}

	/**
	 * 
	 * @param serviceKey   String
	 * @param runType      int
	 * @param paramObject  JParamObject
	 * @param dataObject   Object
	 * @param customObject Object
	 * @param addinObject  Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject runEnterpriseService(String serviceKey, int runType, JParamObject paramObject, Object dataObject, Object customObject, Object addinObject) throws Exception {
		if (serviceKey == null || serviceKey.trim().length() == 0)
			return null;
		//
		paramObject.SetValueByEnvName("ServiceKey", serviceKey);
		if (runType == 0x0001)
			//
			return (JResponseObject) EAI.DAL.IOM("BZServiceComponentManager", "syncRunService", paramObject, dataObject, customObject, addinObject);
		if (runType == 0x0002)
			// �������ú�̨����
			return (JResponseObject) EAI.DAL.IOM("BZServiceComponentManager", "asyncRunService", paramObject, dataObject, customObject, addinObject);
		return null;
	}

	/**
	 * 
	 * @param serviceKey    String
	 * @param paramObject   JParamObject
	 * @param dataObject    Object
	 * @param customObject  Object
	 * @param addinObject   Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject runSyncService(String serviceKey, JParamObject paramObject, Object dataObject, Object customObject, Object addinObject) throws Exception {
		if (serviceKey == null || serviceKey.trim().length() == 0)
			return null;
		//
		paramObject.SetValueByEnvName("ServiceKey", serviceKey);
		return runEnterpriseService(serviceKey, 0x0001, paramObject, dataObject, customObject, addinObject);
	}

	/**
	 * 
	 * @param serviceKey   String
	 * @param paramObject  JParamObject
	 * @param dataObject   Object
	 * @param customObject Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject runSyncService(String serviceKey, JParamObject paramObject, Object dataObject, Object customObject) throws Exception {
		return runSyncService(serviceKey, paramObject, dataObject, customObject, null);
	}

	/**
	 * 
	 * @param serviceKey    String
	 * @param paramObject   JParamObject
	 * @param dataObject    Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject runSyncService(String serviceKey, JParamObject paramObject, Object dataObject) throws Exception {
		return runSyncService(serviceKey, paramObject, dataObject, null, null);
	}

	/**
	 * 
	 * @param serviceKey  String
	 * @param paramObject JParamObject
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject runSyncService(String serviceKey, JParamObject paramObject) throws Exception {
		return runSyncService(serviceKey, paramObject, null, null, null);
	}

	/**
	 * 
	 * @param serviceKey    String
	 * @param paramObject   JParamObject
	 * @param dataObject    Object
	 * @param customObject  Object
	 * @param addinObject   Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject runAyncService(String serviceKey, JParamObject paramObject, Object dataObject, Object customObject, Object addinObject) throws Exception {
		if (serviceKey == null || serviceKey.trim().length() == 0)
			return null;
		//
		paramObject.SetValueByEnvName("ServiceKey", serviceKey);
		return runEnterpriseService(serviceKey, 0x0002, paramObject, dataObject, customObject, addinObject);
	}

	/**
	 * 
	 * @param serviceKey    String
	 * @param paramObject   JParamObject
	 * @param dataObject    Object
	 * @param customObject  Object
	 * @param addinObject   Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject syncRunEnterpriseService(String serviceKey, JParamObject paramObject, Object dataObject, Object customObject, Object addinObject) throws Exception {
		return runEnterpriseService(serviceKey, 0x0001, paramObject, dataObject, customObject, addinObject);
	}

	/**
	 * 
	 * @param serviceKey   String
	 * @param paramObject  JParamObject
	 * @param dataObject   Object
	 * @param customObject Object
	 * @param addinObject  Object
	 * @return JResponseObject
	 * @throws Exception
	 */
	public static JResponseObject asyncRunEnterpriseService(String serviceKey, JParamObject paramObject, Object dataObject, Object customObject, Object addinObject) throws Exception {
		return runEnterpriseService(serviceKey, 0x0002, paramObject, dataObject, customObject, addinObject);
	}
}
