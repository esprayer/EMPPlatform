package com.efounder.service.security;

import com.core.xml.StubObject;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: 角色对象 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RoleStubObject extends StubObject {
  protected java.util.List functionList = null;
  public RoleStubObject() {
  }
  /**
   * 给角色注册一个功能权限
   * @param fso FunctionStubObject
   */
  public void registryFunction(FunctionStubObject fso) {
    if ( functionList == null ) functionList = new ArrayList();
    if ( functionList.indexOf(fso) == -1 ) {
      functionList.add(fso);
    }
  }
  /**
   * 取消注册一个功能权限
   * @param fso FunctionStubObject
   */
  public void unregistryFunction(FunctionStubObject fso) {
    if ( functionList != null ) {
      functionList.remove(fso);
    }
  }
  /**
   * 返回角色所具有的功能权限列表
   * @return List
   */
  public java.util.List getFunctionList() {
    return functionList;
  }
}
