package com.efounder.service.security;

import com.core.xml.StubObject;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: ��ɫ���� </p>
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
   * ����ɫע��һ������Ȩ��
   * @param fso FunctionStubObject
   */
  public void registryFunction(FunctionStubObject fso) {
    if ( functionList == null ) functionList = new ArrayList();
    if ( functionList.indexOf(fso) == -1 ) {
      functionList.add(fso);
    }
  }
  /**
   * ȡ��ע��һ������Ȩ��
   * @param fso FunctionStubObject
   */
  public void unregistryFunction(FunctionStubObject fso) {
    if ( functionList != null ) {
      functionList.remove(fso);
    }
  }
  /**
   * ���ؽ�ɫ�����еĹ���Ȩ���б�
   * @return List
   */
  public java.util.List getFunctionList() {
    return functionList;
  }
}
