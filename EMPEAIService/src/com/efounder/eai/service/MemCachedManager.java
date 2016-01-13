package com.efounder.eai.service;

import com.danga.MemCached.*;

import java.util.*;
import com.core.xml.*;

public class MemCachedManager implements ErrorHandler{
  /**
   *
   */
  protected MemCachedManager() {
  }
  /**
   *
   */
  protected static MemCachedManager memCachedManager = null;
  /**
   *
   * @return MemCachedManager
   */
  public static MemCachedManager getDefault() {
    // 如果为空，并且没有初始化过
    if ( memCachedManager == null && !isInitMemCached ) {
      isInitMemCached = true;
      memCachedManager = new MemCachedManager();
      // 如果初始化失败，返回空
      if ( !memCachedManager.initMemCachedManager() ) return null;
    }
    return memCachedManager;
  }
  protected static boolean isInitMemCached = false;
  /**
   *
   * @return MemCachedClient
   */
  public MemCachedClient getMemCached() {
    return memCachedClient;
  }
  /**
   *
   */
  protected MemCachedClient memCachedClient = null;
  /**
   *
   */
  public boolean initMemCachedManager() {
    Object[] lists=getMemcachedServer();
    if ( lists == null ) return false;
    String[]  servers = (String[])lists[0];
    Integer[] weights =(Integer[])lists[1];
    initMemCachedClient();
    initSockIOPool(servers,weights);
    return true;
  }
  /**
   *
   */
  public void initMemCachedClient() {
    memCachedClient = new MemCachedClient();
    memCachedClient.setCompressEnable(false);// 不需要压缩，以性能为主
    memCachedClient.setPrimitiveAsString(false);
    memCachedClient.setErrorHandler(this);
  }
  /**
   *
   */
  protected SockIOPool sockIOPool = null;
  /**
   *
   */
  protected void initSockIOPool(String[] serverlist,Integer[] weights) {
    sockIOPool = SockIOPool.getInstance();
    sockIOPool.setServers(serverlist);
    sockIOPool.setWeights(weights);
    sockIOPool.initialize();
  }
  /**
   *
   */
  protected static String _MEMCACHEDSERVER_ = "memcachedServer";
  /**
   *
   * @return List[]
   */
  protected Object[] getMemcachedServer() {
    List serverList = PackageStub.getContentVector(_MEMCACHEDSERVER_);
    if ( serverList == null ) return null;
    StubObject serverStub = null;
    String[] spList = null;Integer[] wtList = null;
    for(int i=0;i<serverList.size();i++) {
      if ( spList == null ) spList = new String[serverList.size()];
      if ( wtList == null ) wtList = new Integer[serverList.size()];
      serverStub = (StubObject)serverList.get(i);
      spList[i] = (serverStub.getString("host","")+":"+serverStub.getString("port",""));
      wtList[i] = (new Integer(serverStub.getInt("weight",1)));
    }
    return new Object[]{spList,wtList};
  }
public void handleErrorOnDelete(MemCachedClient arg0, Throwable arg1,
		String arg2) {
	System.out.println("Error in Memcached Delete:"+arg1.getLocalizedMessage());
	arg1.printStackTrace();
	
}
public void handleErrorOnFlush(MemCachedClient arg0, Throwable arg1) {
	System.out.println("Error in Memcached Flush:"+arg1.getLocalizedMessage());
	arg1.printStackTrace();
	
}
public void handleErrorOnGet(MemCachedClient arg0, Throwable arg1, String arg2) {
	System.out.println("Error in Memcached Get:"+arg2+" "+arg1.getLocalizedMessage());
	arg1.printStackTrace();
	
}
public void handleErrorOnGet(MemCachedClient arg0, Throwable arg1, String[] arg2) {
	System.out.println("Error in Memcached Get:"+arg2+" "+arg1.getLocalizedMessage());
	arg1.printStackTrace();
	
}
public void handleErrorOnInit(MemCachedClient arg0, Throwable arg1) {
	// TODO Auto-generated method stub
	
}
public void handleErrorOnSet(MemCachedClient arg0, Throwable arg1, String arg2) {
	System.out.println("Error in Memcached Set:"+arg2+" "+arg1.getLocalizedMessage());
	arg1.printStackTrace();
	
}
public void handleErrorOnStats(MemCachedClient arg0, Throwable arg1) {
	// TODO Auto-generated method stub
	
}
}
