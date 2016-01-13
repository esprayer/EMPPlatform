package com.efounder.eai.service.dal;

import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.service.config.ConfigManager;

public class EMPDALConfigObject extends JActiveObject {
	private static final String GUID = "00000000-0003-0001-0000000000000009";

	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public EMPDALConfigObject() {
		setObjectGUID(GUID);
	}

	/**
	 * 
	 * @param o1
	 *            Object
	 * @param o2
	 *            Object
	 * @param o3
	 *            Object
	 * @param o4
	 *            Object
	 * @throws Exception
	 * @return Object
	 */
	public Object saveWebInfConfig(Object o1, Object o2, Object o3, Object o4) throws Exception {
		JParamObject PO = (JParamObject) o1;
		String key = (String) o2;
		Object configObject = (Object) o3;
		ConfigManager.getDefault().saveWebInfCfg(key, configObject);
		return null;
	}

	/**
	 * 
	 * @param o1
	 *            Object
	 * @param o2
	 *            Object
	 * @param o3
	 *            Object
	 * @param o4
	 *            Object
	 * @throws Exception
	 * @return Object
	 */
	public Object loadWebInfConfig(Object o1, Object o2, Object o3, Object o4) throws Exception {
		JParamObject      PO = (JParamObject) o1;
		JResponseObject   RO = null;
		String           key = (String) o2;
		Object           res = null;
		
		try {
			res = ConfigManager.getDefault().loadWebInfCfg(key);
			RO = new JResponseObject(res, 0);
		} catch (Exception ce) {
			ce.printStackTrace();
			RO = new JResponseObject(null, 0, ce);
		}
		return RO;
	}

	/**
	 * 
	 * @param PO
	 *            JParamObject
	 * @param fileList
	 *            Map
	 * @param o3
	 *            Object
	 * @param o4
	 *            Object
	 * @throws Exception
	 * @return Object
	 */
	public Object uploadFiles(JParamObject PO, java.util.Map fileList, Object o3, Object o4) throws Exception {

		return null;
	}
}