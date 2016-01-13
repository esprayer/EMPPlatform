package com.efounder.eai.service;

import com.danga.MemCached.ErrorHandler;
import com.danga.MemCached.MemCachedClient;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.framework.JActiveObject;

public class SessionManager extends JActiveObject implements ErrorHandler {
	  /**
	   *
	   */
	  private static final String GUID = "00000000-0003-0001-0000000000000004";
	  
	  /**
	   *
	   */
	  protected MemCachedClient memCachedClient = null;
	  
	  /**
	   *
	   */
	  public SessionManager() {
	    setObjectGUID(GUID);
	  }
	  /**
	   *
	   * @param paramObject JParamObject
	   * @param dataObject Object
	   * @param customObject Object
	   * @param AdditiveObject Object
	   * @return Object
	   * @throws Exception
	   */
	  public JResponseObject initMemCachedManager(Object o1, Object o2, Object o3, Object o4) throws Exception {
		  initMemCachedClient();
		  return null;
	  }
	  
	  protected void initMemCachedClient() {
		  memCachedClient = new MemCachedClient();
		  memCachedClient.setCompressEnable(false);// 不需要压缩，以性能为主
		  memCachedClient.setPrimitiveAsString(false);
		  memCachedClient.setErrorHandler(this);
	  }
	  @Override
	  public void handleErrorOnDelete(MemCachedClient arg0, Throwable arg1, String arg2) {
		
	  }
	  @Override
	  public void handleErrorOnFlush(MemCachedClient arg0, Throwable arg1) {
		  // TODO Auto-generated method stub
		
	  }
	  @Override
	  public void handleErrorOnGet(MemCachedClient arg0, Throwable arg1, String arg2) {
		// TODO Auto-generated method stub
		
	  }
	  @Override
	  public void handleErrorOnGet(MemCachedClient arg0, Throwable arg1, String[] arg2) {
		// TODO Auto-generated method stub
		
	  }
	  @Override
	  public void handleErrorOnInit(MemCachedClient arg0, Throwable arg1) {
		  // TODO Auto-generated method stub
		
	  }
	  @Override
	  public void handleErrorOnSet(MemCachedClient arg0, Throwable arg1, String arg2) {
		  // TODO Auto-generated method stub
		
	  }
	  @Override
	  public void handleErrorOnStats(MemCachedClient arg0, Throwable arg1) {
		  // TODO Auto-generated method stub
		
	  }
}
