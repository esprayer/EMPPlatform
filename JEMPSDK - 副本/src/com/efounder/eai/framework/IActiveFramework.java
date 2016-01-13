package com.efounder.eai.framework;

import java.util.Map;

import com.efounder.object.IDComInterface;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * ���ע����Ϣ��ṹ��
 * GUID
 * ID
 * NAME
 * COMMENT
 * RESID
 * VER
 * CLASS
 */
public interface IActiveFramework extends IDComInterface {
  // �ڴ�Ҫ�����ܵĹ��õ��÷���(�ṩ��1.0��ܵ�����ݵĵ��÷���)
  void addActiveObjectStub(Object ActiveObjectStub);
  // ���б���ɾ��һ��ActiveObjectStub
  void delActiveObjectStub(Object ActiveObjectStub);
  // ���ÿ�ܱ���ķ���
  Object InvokeFrameworkMethod(String ActiveFrameworkMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
  public Object CallObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,
                                 Object Param,
                                 Object Data,
                                 Object CustomObject,
                                 Object AdditiveObject) throws Exception ;

  // ���ÿ�ܹ�������ĳһ�������ĳһ������
  // ��ܹ������ı�׼����:Param,Data,Context,Environment,Security,CustomObject,AdditiveObject
  Object MInvokeObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
  Object InvokeObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
  Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName) throws Exception;
  Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName,Object Param) throws Exception;
  Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data) throws Exception;
  Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data,Object CustomObject) throws Exception;

  Object MIOM(String ActiveObjectName,String ActiveObjectMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
  Object IOM(String ActiveObjectName,String ActiveObjectMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
  Object IOM(String ActiveObjectName,String ActiveMethodName) throws Exception;
  Object IOM(String ActiveObjectName,String ActiveMethodName,Object Param) throws Exception;
  Object IOM(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data) throws Exception;
  Object IOM(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data,Object CustomObject) throws Exception;
  public Object webInvoke(String ActiveObjectName,String ActiveMethodName,Object Param,Map requestHeader) throws Exception;

//
  public void setCompany(String Value);
  public String getCompany();
  public void setProduct(String Value);
  public String getProduct();
  public void setTier(String Value);
  public String getTier();

  // Զ�̶��󷽷�����

  // ���������״̬�µĹ���(��Ҫ�Ƿ������õĹ���,������ݵĹ�����������ģ���н��е�)
  // ��ܵĳ�ʼ������
  Object Initialize(String Param,String Data,Object CustomObject,Object AdditiveObject);
  // ��ܵķ���ʼ������
  Object Destroy();
  public void GetFrameworkGUID();
}
