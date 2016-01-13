package com.efounder.eai.framework;

import java.lang.reflect.*;
import java.util.*;

import org.jdom.*;
import com.efounder.eai.*;
import com.core.xml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class JActiveFramework implements IActiveFramework {
  static final String SOFTWARE                     = "SOFTWARE";
  static final String JActiveFrameworkApplications = "JActiveFrameworkApplications";
  protected String AcitveFrameworkName             = "BusinessAcitveFramework";
  static final String GUID                         = "guid";

  static final String FRAMEWORKS = "FRAMEWORKS";
  static final String Objects    = "Objects";
  static final String NAME       = "name";
  static public String CLSID_Type;
  //
  public    ClassLoader           FrameworkClassLoader   = null;
  // �й�ActiveObject����ĺ������Ա
  //------------------------------------------------------------------------------------------------
  //����:���ڱ���˿�������еĻ�����Stub����
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  protected Vector    ActiveObjectStubList              = new Vector();// ��������б�
  protected String Company;  // ��ܵĹ�˾���
  protected String Product;  // ������ڵĲ�Ʒ���
  protected String Tier;     // ������ڵ�����λ��,Browser/Mid/Server/Other
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setCompany(String Value) {
    Company = Value;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String getCompany() {
    return Company;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setProduct(String Value) {
    Product = Value;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String getProduct() {
    return Product;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setTier(String Value) {
    Tier = Value;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String getTier() {
    return Tier;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setCLSID_Type(String Value) {
    CLSID_Type = Value;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String getCLSID_Type() {
    return CLSID_Type;
  }
  // ͬʱ�ṩ���·������д���
  // ���б�������һ��AcitveObjectStub
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void addActiveObjectStub(Object ActiveObjectStub) {
    ActiveObjectStubList.add(ActiveObjectStub);
  }
  // ���б���ɾ��һ��ActiveObjectStub
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void delActiveObjectStub(Object ActiveObjectStub) {
    ActiveObjectStubList.remove(ActiveObjectStub);
  }
  // ͨ��Name����AcitveObjectStub
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JFwkActiveObjectStub findActiveObjectStubByName(String ActiveObjectName) {
    JFwkActiveObjectStub FwkAOS;int i,Count;
    Count = ActiveObjectStubList.size();
    for(i=0;i<Count;i++) {
      FwkAOS = (JFwkActiveObjectStub)ActiveObjectStubList.get(i);
      if ( FwkAOS.ActiveObjectStub.id.compareTo(ActiveObjectName) == 0 ) return FwkAOS;
    }
    return null;
  }
  // ͨ��GUID����ActiveObjectStub
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JFwkActiveObjectStub findActiveObjectStubByGUID(String ActiveObjectGUID) {
    JFwkActiveObjectStub FwkAOS;int i,Count;
    Count = ActiveObjectStubList.size();
    for(i=0;i<Count;i++) {
      FwkAOS = (JFwkActiveObjectStub)ActiveObjectStubList.get(i);
      if ( FwkAOS.ActiveObjectStub.guid.compareTo(ActiveObjectGUID) == 0 ) return FwkAOS;
    }
    return null;
  }
  public Object MIOM(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception {
    return MInvokeObjectMethod(ActiveObjectName,ActiveObjectMethodName,Param,Data,CustomObject,AdditiveObject);
  }

  /**
   *
   * @param ActiveObjectName String
   * @param ActiveObjectMethodName String
   * @param Param Object
   * @param Data Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   * @throws Exception
   */
  public abstract Object callOldFwkObject(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception;

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object TInvokeObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception{
    return CallObjectMethod(ActiveObjectName,ActiveObjectMethodName,
                                   Param,
                                   Data,
                                   CustomObject,
                                   AdditiveObject);

  }
  // ���ÿ�ܹ�������ĳһ�������ĳһ������
  // ��ܹ������ı�׼����:Param,Data,Context,Environment,Security,CustomObject,AdditiveObject
  //------------------------------------------------------------------------------------------------
  //����: BOF
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object InvokeObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception{
    return CallObjectMethod(ActiveObjectName,ActiveObjectMethodName,
                                   Param,
                                   Data,
                                   CustomObject,
                                   AdditiveObject);
  }
  public Object IOM(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveObjectMethodName,
                                   Param,
                                   Data,
                                   CustomObject,
                                   AdditiveObject);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,null,null,null,null);
  }
  public Object IOM(String ActiveObjectName,String ActiveMethodName) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName);//DAL
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName,Object Param) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param,null,null,null);
  }
  public Object IOM(String ActiveObjectName,String ActiveMethodName,Object Param) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param);//BOF
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param,Data,null,null);
  }
  public Object IOM(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param,Data);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object InvokeObjectMethod(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data,Object CustomObject) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param,Data,CustomObject,null);
  }
  public Object IOM(String ActiveObjectName,String ActiveMethodName,Object Param,Object Data,Object CustomObject) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param,Data,CustomObject);
  }
  /**
   *
   * @param ActiveObjectName String
   * @param ActiveMethodName String
   * @param Param Object
   * @param Data Object
   * @return Object
   * @throws Exception
   */
  public Object webInvoke(String ActiveObjectName,String ActiveMethodName,Object Param,Map requestHeader) throws Exception{
    return InvokeObjectMethod(ActiveObjectName,ActiveMethodName,Param,requestHeader);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public IActiveObject QueryActiveObject(String Name) {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JActiveFramework() {
  }
  /**
   *
   */
  public void initPackageObject(Vector InitList) {
    // ��ȡPackageStub�е�Class�б�
    Vector ClassList = PackageStub.getContentVector("classes");
    if ( ClassList == null ) return;
    StubObject SO = null;JActiveObjectStub AOS = null;String CLS_Type,AOGuid,INIT;
    JFwkActiveObjectStub FwkAOS = null;
    for(int i=0;i<ClassList.size();i++) {
      SO = (StubObject)ClassList.get(i);
      // ��ȡ�����Type
      CLS_Type = (String)SO.getObject("type",null);
      // ���������ͺͿ������һ�������ӽ�����
      if ( CLSID_Type.equals(CLS_Type) ) {
//        AOGuid   = (String)SO.getObject("guid",null);
        AOGuid   = (String)SO.getObject("id",null);
        INIT     = (String)SO.getObject("init",null);
        JManagerActiveObject.initPackageObject();
        AOS = JManagerActiveObject.FindActiveObjectStub(AOGuid,CLSID_Type);
        if ( AOS != null ) {
          FwkAOS = new JFwkActiveObjectStub();
          if ( "1".equals(INIT) ) {
            FwkAOS.Init = true; InitList.add(FwkAOS);
          } else {
            FwkAOS.Init = false;
          }
          FwkAOS.ActiveObjectStub = AOS;  FwkAOS.iActiveObject = null;
          FwkAOS.oActiveObject    = null; FwkAOS.PrivateAttrib = null;
          ActiveObjectStubList.add(FwkAOS);
        }
      }
    }
  }

  void InitObject(Vector InitList) {
    JFwkActiveObjectStub FwkAOS = null;
    for(int i=0;i<InitList.size();i++) {
      FwkAOS = (JFwkActiveObjectStub)InitList.get(i);
      try {
        InvokeObjectMethod(FwkAOS.ActiveObjectStub.id, null);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object Destroy() {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object Initialize(String Param,String Data,Object CustomObject,Object AdditiveObject) {
    GetFrameworkGUID();
    LoadAcitveObjects();
    return null;
  }
  
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void LoadAcitveObjects() {
	Element ClassesRoot,ClassElement;Element node;List nodelist;int Index=0;
    JActiveObjectStub AOS = null;String FwkGUID,AOGuid;//ObjectGUID
    JFwkActiveObjectStub FwkAOS;String INIT=null;Vector InitList = new Vector();
	ActiveObjectStubList.removeAllElements();
	initPackageObject(InitList);
	InitObject(InitList);
  }
  
//------------------------------------------------------------------------------------------------
  //����:BOF
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CallObjectMethod(String ActiveObjectName,String ActiveObjectMethodName,
                                   Object Param,
                                   Object Data,
                                   Object CustomObject,
                                   Object AdditiveObject) throws Exception {
    JFwkActiveObjectStub AOStub=null;Object Res=null;
    // ���Ȱ������
    AOStub = (JFwkActiveObjectStub)findActiveObjectStubByName(ActiveObjectName);
    // ���û�ҵ�,��GUID��
    if ( AOStub == null ) AOStub = (JFwkActiveObjectStub)findActiveObjectStubByGUID(ActiveObjectName);
    if ( AOStub != null ) {
      Res = invokeMethod(ActiveObjectMethodName, Param, Data, CustomObject, AdditiveObject, AOStub);
    } else {
      System.out.println("对象-"+ActiveObjectName+"方法-"+ActiveObjectMethodName);
    }
    return Res;
  }
  
  /**
  *
  * @param ActiveObjectMethodName String
  * @param Param Object
  * @param Data Object
  * @param CustomObject Object
  * @param AdditiveObject Object
  * @param AOStub JFwkActiveObjectStub
  * @return Object
  * @throws Exception
  */
 private Object invokeMethod(String ActiveObjectMethodName, Object Param,
                             Object Data, Object CustomObject,
                             Object AdditiveObject,
                             JFwkActiveObjectStub AOStub) throws Exception {
   Object Res = null;
   if ( AOStub.iActiveObject == null ) {
       AOStub.ActiveObjectStub.ActiveObjectClass = Class.forName(AOStub.ActiveObjectStub.objectclass);
       //AOStub.ActiveObjectStub.ActiveObjectClass = FrameworkClassLoader.loadClass(AOStub.ActiveObjectStub.objectclass,true);
       AOStub.oActiveObject = (JActiveObject)AOStub.ActiveObjectStub.ActiveObjectClass.newInstance();
       AOStub.oActiveObject.ActiveFramework     = this;
       AOStub.oActiveObject.ActiveObjectStub    = AOStub.ActiveObjectStub;
       AOStub.oActiveObject.FwkActiveObjectStub = AOStub;
       AOStub.iActiveObject = (IActiveObject) AOStub.oActiveObject;
       // ִ�ж���ĳ�ʼ������
       AOStub.oActiveObject.InitObject(this,AOStub,AOStub.ActiveObjectStub,null);
   }
   // ���ö���ķ���
   if ( AOStub.iActiveObject != null && ActiveObjectMethodName != null ) {
     Res = AOStub.iActiveObject.InvokeObjectMethod(ActiveObjectMethodName,Param,Data,CustomObject,AdditiveObject);
   }
   return Res;
 }
}
