package com.efounder.service.security;

import com.core.xml.StubObject;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class UserStubObject extends StubObject {
  // �û���Ӧ�Ľ�ɫ�б�
  protected java.util.List roleList = null;
  public UserStubObject() {
  }
  /**
   * ���û�ע��һ����ɫ
   * @param rso RoleStubObject
   */
  public void registryRole(RoleStubObject rso) {
    if ( roleList == null ) roleList = new ArrayList();
    if ( roleList.indexOf(rso) == -1 ) {
      roleList.add(rso);
    }
  }
  /**
   * �����û��Ľ�ɫ�б�
   * @return List
   */
  public java.util.List getRoleList() {
    return roleList;
  }
  /**
   *
   * @return List
   */
  public java.util.List getFunctionList() {
    if ( roleList == null ) return null;
    java.util.List functionList = new ArrayList();RoleStubObject rso = null;
    for(int i=0;i<roleList.size();i++) {
      rso = (RoleStubObject)roleList.get(i);
      java.util.List fList = rso.getFunctionList();
      if ( fList != null ) functionList.addAll(fList);
    }
    return functionList;
  }
}
