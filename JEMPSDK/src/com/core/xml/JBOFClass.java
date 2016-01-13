package com.core.xml;

import java.lang.reflect.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JBOFClass {
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JBOFClass() {
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void VoidCallObjectMethod(Object Obj,String MethodName,Object[] OArray) {
    Method MD=null;
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        MD.invoke(Obj,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallObjectMethod(Object Obj,String MethodName,Object[] OArray) {
    Object Res = null;Method MD=null;
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  public static Object CallObjectMethodException(Object Obj,String MethodName,Object[] OArray) throws Exception {
    Object Res = null;Method MD=null;
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,OArray);
      }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallObjectMethod(Object Obj,String MethodName,Object Obj1,Object Obj2,Object Obj3,Object Obj4) {
    Object Res = null;Method MD=null;
    Object[] OArray = {Obj1,Obj2,Obj3,Obj4};
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallObjectMethod(Object Obj,String MethodName,Object Obj1,Object Obj2,Object Obj3) {
    Object Res = null;Method MD=null;
    Object[] OArray = {Obj1,Obj2,Obj3};
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallObjectMethod(Object Obj,String MethodName,Object Obj1,Object Obj2) {
    Object Res = null;Method MD=null;
    Object[] OArray = {Obj1,Obj2};
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallObjectMethod(Object Obj,String MethodName,Object Obj1) {
    Object Res = null;Method MD=null;
    Object[] OArray = {Obj1};
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallObjectMethod(Object Obj,String MethodName) {
    Object Res = null;Method MD=null;
    try {
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,null);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  public static Object CallObjectMethodException(Object Obj,String MethodName) throws Exception {
    Object Res = null;Method MD=null;
      MD = FindObjectMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(Obj,null);
      }
    return Res;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Method FindObjectMethod(Object Obj,String MethodName) {
    Method MD=null;Method[] MDS;
    MDS = Obj.getClass().getMethods();
    for(int i=0;i<MDS.length;i++) {
      MD = MDS[i];
      if ( MD.getName().equals(MethodName) ) return MD;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Method FindClassMethod(Class Obj,String MethodName) {
    Method MD=null;Method[] MDS;
    MDS = Obj.getMethods();
    for(int i=0;i<MDS.length;i++) {
      MD = MDS[i];
      if ( MD.getName().equals(MethodName) ) return MD;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void VoidCallClassMethod(Class Obj,String MethodName,Object[] OArray) {
    Method MD=null;
    try {
      MD = FindClassMethod(Obj,MethodName);
      if ( MD != null ) {
        MD.invoke(null,OArray);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallClassMethod(Class Obj,String MethodName,Object[] OArray) {
    Object Res = null;Method MD=null;
    try {
      MD = FindClassMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(null,OArray);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return Res;
  }
  /**
   *
   * @param Obj Class
   * @param MethodName String
   * @param OArray Object[]
   * @return Object
   * @throws Exception
   */
  public static Object CallClassMethodException(Class Obj,String MethodName,Object[] OArray) throws Exception{
    Object Res = null;Method MD=null;
    MD = FindClassMethod(Obj,MethodName);
    if ( MD != null ) {
      Res = MD.invoke(null,OArray);
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static Object CallClassMethod(Class Obj,String MethodName) {
    Object Res = null;Method MD=null;
    try {
      MD = FindClassMethod(Obj,MethodName);
      if ( MD != null ) {
        Res = MD.invoke(null,null);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static void VoidCallClassMethod(Class Obj,String MethodName) {
    Method MD=null;
    try {
      MD = FindClassMethod(Obj,MethodName);
      if ( MD != null ) {
        MD.invoke(null,null);
      }
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
  }
}
