package com.efounder.eai.framework;

import java.util.*;

import org.jdom.*;
import com.efounder.eai.*;
import com.core.xml.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
public class JManagerActiveObject {
  static final  String KeyName = "CLASSES";
  static final  String ID="id";
  static final  String NAME="name";
  static final  String TYPE="type";
  static final  String VER="ver";
  static final  String CLASS="class";
  static final  String GUID="guid";
  static public java.util.Hashtable ActiveObjectStubHashtable = new java.util.Hashtable();
//------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JManagerActiveObject() {
  }
  /**
   *
   * @param type String
   * @return Vector
   */
  public static  Vector getClassList(String type) {
    Vector classList = null;
    classList = (Vector)ActiveObjectStubHashtable.get(type);
    return classList;
  }
  /**
   *
   * @param type String
   * @return Vector
   */
  private static Vector createClassList(String type) {
    Vector classList = null;
    classList = (Vector)ActiveObjectStubHashtable.get(type);
    if ( classList == null ) {
      classList = new Vector();
      ActiveObjectStubHashtable.put(type,classList);
    }
    return classList;
  }
  //------------------------------------------------------------------------------------------------
  //����: ��ע�����װ��ActiveObject����
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static public void LoadAcitveObjects() {
//    Element ClassesRoot,ClassElement,TypeElement;Element node;List nodelist;int Index=0;
//    JActiveObjectStub AOS = null;String TypeName;Vector ActiveObjectStubList;
//      ActiveObjectStubHashtable.clear();
//      ClassesRoot = EAI.Registry.RegOpenKey(EAI.Registry.HKEY_CLASSES_ROOT,KeyName);
//      // ö�ٶ�������
//      nodelist = EAI.Registry.BeginEnumerate(ClassesRoot);
//      while ( nodelist != null ) {
//        node = EAI.Registry.Enumerate(nodelist,Index++);
//        if ( node == null ) break;
////        if ( node.getNodeType() == node.ELEMENT_NODE ) {
//          TypeElement = (Element)node;
//          TypeName = TypeElement.getName();
//          ActiveObjectStubList = createClassList(TypeName);
////              new Vector();
//          // ��HashTable������ActiveObjectStubList
////          ActiveObjectStubHashtable.put(TypeName,ActiveObjectStubList);
////        }
//      }
//      EAI.Registry.EndEnumerate();
//      // ��ݶ�������,��ȡ�����͵����ж���
//      for (Enumeration e = ActiveObjectStubHashtable.keys(); e.hasMoreElements();) {
//        TypeName = (String)e.nextElement();
//        ActiveObjectStubList = (Vector)ActiveObjectStubHashtable.get((Object)TypeName);
//        ClassesRoot = EAI.Registry.RegOpenKey(EAI.Registry.HKEY_CLASSES_ROOT,KeyName+"\\"+TypeName);
//        nodelist = EAI.Registry.BeginEnumerate(ClassesRoot);
//        Index = 0;
//        while ( nodelist != null ) {
//          node = EAI.Registry.Enumerate(nodelist,Index++);
//          // ���Ϊ��,����
//          if ( node == null ) break;
//          // ���Ϊ��,���Ƿ���Ԫ��,��Ԫ�زŽ��д���
////          if ( node.getNodeType() == node.ELEMENT_NODE ) {
//            ClassElement = (Element)node;
//            AOS = new JActiveObjectStub();
//            AOS.PublicAttrib = ClassElement;             // ������ע����еĹ�������Ԫ��
//            AOS.guid  = EAI.Registry.GetElementValue(ClassElement,GUID);
//            AOS.id    = EAI.Registry.GetElementValue(ClassElement,ID);
//            AOS.name  = EAI.Registry.GetElementValue(ClassElement,NAME);
//            AOS.ver   = EAI.Registry.GetElementValue(ClassElement,VER);
//            AOS.objectclass = EAI.Registry.GetElementValue(ClassElement,CLASS);
//            AOS.type  = EAI.Registry.GetElementValue(ClassElement,TYPE);
//            ActiveObjectStubList.add(AOS);
////          }
//        }
//      }
      initPackageObject();
  }
  /**
   *
   */
  public static void initPackageObject() {
    // ��ȡPackageStub�е�Class�б�
    Vector ClassList = PackageStub.getContentVector("classes");
    if ( ClassList == null ) return;
    StubObject SO = null;JActiveObjectStub AOS = null;Vector ActiveObjectStubList = null;
    for(int i=0;i<ClassList.size();i++) {
      SO = (StubObject)ClassList.get(i);
      // ��SutbObject ת��Ϊ ActiveObjectStub
      AOS = convertSO2AO(SO);
      ActiveObjectStubList = createClassList(AOS.type);
      // ���ӽ�ActiveObjectStubList
      if ( ActiveObjectStubList != null )
        ActiveObjectStubList.add(AOS);
    }
  }
  /**
   *
   * @param SO StubObject
   * @return JActiveObjectStub
   */
  private static JActiveObjectStub convertSO2AO(StubObject SO) {
    JActiveObjectStub AOS = null;
    AOS = new JActiveObjectStub();
    AOS.PublicAttrib = null;
    AOS.guid  = (String)SO.getObject(GUID,null);
    AOS.id    = (String)SO.getObject(ID,null);
    AOS.name  = (String)SO.getObject(NAME,null);
    AOS.ver   = (String)SO.getObject(VER,null);
    AOS.objectclass = (String)SO.getObject(CLASS,null);
    AOS.type  = (String)SO.getObject(TYPE,null);
    return AOS;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static public JActiveObjectStub FindActiveObjectStub(String Value,String CLSID_Type) {
    JActiveObjectStub AOS = null;int i,Count;Vector ActiveObjectStubList;
      ActiveObjectStubList = (Vector)ActiveObjectStubHashtable.get((Object)CLSID_Type);
      if ( ActiveObjectStubList != null ) {
        Count = ActiveObjectStubList.size();
        for(i=0;i<Count;i++) {
          AOS = (JActiveObjectStub)ActiveObjectStubList.get(i);
          // ��ѯ����ͨ�����GUID���Ƕ���ı�ʶ
          if ( AOS.guid.equals(Value) || AOS.id.equals(Value) ) return AOS;
        }
      }
      return null;
  }
}
