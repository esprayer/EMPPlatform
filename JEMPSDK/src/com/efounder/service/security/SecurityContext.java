package com.efounder.service.security;

import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SecurityContext extends StubObject {
  /**
   * ��ȡ�û�����Ȩ�޵��б�
   * @param UserName String
   * @return Map
   */
  public java.util.List getUserFunctionList(String UserName) {
    java.util.List functionList = (java.util.List)this.getObject(UserName,null);
    return functionList;
  }
  /**
   * ���û��Ĺ���Ȩ���б����û����ΪKey,���ý�ϵͳ�б���
   * @param UserName String
   * @param functionList Map
   */
  public void setUserFunctionList(String UserName,java.util.List functionList) {
    this.setObject(UserName,functionList);
  }
  public SecurityContext() {
  }

}
