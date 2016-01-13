package com.efounder.server;

import com.efounder.dbc.RequestDataSetData;
import com.efounder.eai.framework.*;
import com.efounder.eai.service.util.DataServiceUtil;
import com.efounder.eai.data.*;
/**
 *
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
public class DataServiceActiveObject extends JActiveObject {
	/**
	 *
	 */
	private static final String GUID = "00000000-0003-0001-0000000000000004";
	/**
	 *
	 */
	public DataServiceActiveObject() {
		setObjectGUID(GUID);
	}
	/**
	 *
	 * @param paramObject JParamObject
	 * @param request RequestDataSetData
	 * @param o3 Object
	 * @param o4 Object
	 * @return Object
	 * @throws Exception
	 */
	public Object provider(JParamObject paramObject,RequestDataSetData request,Object o3,Object o4) throws Exception {
		return DataServiceUtil.invokeDBAgentService(request);
	}
	/**
	 *
	 * @param paramObject JParamObject
	 * @param request RequestDataSetData
	 * @param o3 Object
	 * @param o4 Object
	 * @return Object
	 * @throws Exception
	 */
	public Object resolver(JParamObject paramObject,RequestDataSetData request,Object o3,Object o4) throws Exception {
		return DataServiceUtil.invokeDBAgentService(request);
	}
}
