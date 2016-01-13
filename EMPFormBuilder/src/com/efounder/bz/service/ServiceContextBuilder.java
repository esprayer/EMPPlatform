package com.efounder.bz.service;

import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.sql.JConnection;
import java.sql.Statement;

public abstract class ServiceContextBuilder {
	public final ESPServerContext createContext(JParamObject paramObject, JConnection connection, Object dataObject, Object customObject, Object addinObject, int runType) throws Exception {
		ESPServerContext espContext = newContext(paramObject, connection, dataObject, customObject, addinObject, runType);
		if (espContext != null) {
			espContext.setParamObject(paramObject);espContext.setConnection(connection);
			espContext.setDataObject(dataObject);espContext.setCustomObject(customObject);
			espContext.setAddinObject(addinObject);
//			if (runType == 2) {
//				paramObject.setAutoConnection(false);
//				paramObject.removeConn(connection);
//			}
		}
		return espContext;
	}
  
	protected abstract ESPServerContext newContext(JParamObject paramJParamObject, JConnection paramJConnection, Object paramObject1, Object paramObject2, Object paramObject3, int paramInt) throws Exception;
  
	public abstract Object createResponse(ESPServerContext paramESPServerContext) throws Exception;
  
	public final void finish(ESPServerContext espContext, Object response) {
		finishContext(espContext, response);
	}
  
	protected abstract void finishContext(ESPServerContext paramESPServerContext, Object paramObject);
  
	public final void closeConnection(ESPServerContext espContext) {
		try {
			Statement stmt = espContext.getStatement();
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception exx) {
				exx.printStackTrace();
			}
			JConnection conn = espContext.getConnection();
			if ((conn != null) && (!conn.isClosed())) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
  
	public Object[] getPrepareServiceParams(ESPServerContext espContext, Object response) {
		return null;
	}
  
	public void processPrepareServiceResponse(ESPServerContext espContext, Object response, Object[] params, JResponseObject ro) {}
  
	public Object[] getFinishServiceParams(ESPServerContext espContext, Object response) {
		return null;
	}
  
	public void processFinishServiceResponse(ESPServerContext espContext, Object response, Object[] params, JResponseObject ro) {}
}
