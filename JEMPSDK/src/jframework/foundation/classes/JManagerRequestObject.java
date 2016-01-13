package jframework.foundation.classes;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Iterator;

import com.efounder.eai.data.JRequestObject;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JManagerRequestObject {
  public java.util.Properties RequestLists = new java.util.Properties();
  //public JManagerRequestObject ManagerRequestObject=null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JManagerRequestObject() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized JRequestObject SetRequestObject(JRequestObject RO,String TypeName,String RequestName) {
    /*JRequestObject RO*/;java.util.Vector RequestList = null;
//    RO = new JRequestObject();
//    RO.TypeName    = TypeName;
//    RO.RequestName = RequestName;
//    RO.ManagerRequestObject = this;
    RequestList = (java.util.Vector)RequestLists.get(TypeName);
    if ( RequestList == null ) {
      RequestList = new java.util.Vector();
      RequestLists.put(TypeName,RequestList);
    }
    RequestList.add(RO);
    return RO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized JRequestObject GetRequestObject(String TypeName,String RequestName) {
    JRequestObject RO=null;java.util.Vector RequestList = null;// = new java.util.Vector();
    RequestList = (java.util.Vector)RequestLists.get(TypeName);
    if ( RequestList != null ) {
      for(int i=0;i<RequestList.size();i++) {
        RO = (JRequestObject)RequestList.get(i);
//        if ( RO.RequestName.equals(RequestName) == true ) return RO;
      }
    }
    return RO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized boolean DelRequestObject(String TypeName,String RequestName) {
    JRequestObject RO=null;java.util.Vector rl = null;// = new java.util.Vector();
    rl = (java.util.Vector)RequestLists.get(TypeName);
    RO = GetRequestObject(TypeName,RequestName);
    if(rl == null || RO == null){
        return false;
    }

    // 先清除掉此线程
//    RO.Stop();
    rl.remove(RO);

    /**
     * 如果线程为空
     * 则清除掉此用户的线程列表
     * modified by hufeng 2008.6.4
     */
    if (rl.size() == 0) {
        RequestLists.remove(rl);
    }
    return true;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized boolean StopRequestObject(String TypeName,String RequestName) {
//    JRequestObject RO=null;java.util.Vector rl = null;// = new java.util.Vector();
//    rl = (java.util.Vector)RequestLists.get(TypeName);
//    RO = GetRequestObject(TypeName,RequestName);
//    if ( rl != null && RO != null ) {
//      RO.Stop();
//    }
    return false;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized boolean PauseRequestObject(String TypeName,String RequestName) {
//    JRequestObject RO=null;java.util.Vector rl = null;// = new java.util.Vector();
//    rl = (java.util.Vector)RequestLists.get(TypeName);
//    RO = GetRequestObject(TypeName,RequestName);
//    if ( rl != null && RO != null ) {
//      RO.Suspend();
//    }
    return false;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized boolean ResumeRequestObject(String TypeName,String RequestName) {
//    JRequestObject RO=null;java.util.Vector rl = null;// = new java.util.Vector();
//    rl = (java.util.Vector)RequestLists.get(TypeName);
//    RO = GetRequestObject(TypeName,RequestName);
//    if ( rl != null && RO != null ) {
//      RO.Resume();
//    }
    return false;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public synchronized java.util.Vector GetRequestList(String TypeName) {
    return (java.util.Vector)RequestLists.get(TypeName);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void LogoutRequest(String TypeName) {
//    java.util.Vector rl = null;JRequestObject RO=null;
//    rl = (java.util.Vector)RequestLists.get(TypeName);
//    if ( rl != null ) {
//      java.util.Vector clrl = (java.util.Vector)rl.clone();
//      for(int i=0;i<clrl.size();i++) {
//        RO = (JRequestObject)clrl.get(i);
//        if ( RO.RunStatus == JRequestObject.REQUEST_DESTROY ) {
//          rl.remove(RO);
//        }
//      }
//      //如果没有正在运行的了，就直接删除
//      if ( rl.size() == 0 )
//        RequestLists.remove(TypeName);
//    }
  }

  /**
   * 清除已经完成的线程
   * add by hufeng 2008.6.3
   */
  public synchronized void ClearFinishThread() {
//      String key;
//      Vector list;
//      JRequestObject RO=null;
//      boolean bDel = false;
//      for(Enumeration e = RequestLists.keys();e.hasMoreElements();){
//          key = (String)e.nextElement();
//          list = (Vector)RequestLists.get(key);
//          if(list == null){
//              continue;
//          }
//          // 把完成的线程删除掉
//          bDel = true;
//          for(int i=0; i<list.size(); i++){
//              RO = (JRequestObject)list.get(i);
//              if ( RO.RunStatus != JRequestObject.REQUEST_DESTROY ) {
//                bDel = false;
//              }
//          }
//          // 如果为空则清除掉
//          if(bDel){
//            list.removeAllElements();
//            RequestLists.remove(key);
//          }
//      }
//
//
////    java.util.Vector rl = null;JRequestObject RO=null;
////    rl = (java.util.Vector)RequestLists.get(TypeName);
////    if ( rl != null ) {
////      java.util.Vector clrl = (java.util.Vector)rl.clone();
////      for(int i=0;i<clrl.size();i++) {
////        RO = (JRequestObject)clrl.get(i);
////        if ( RO.RunStatus == JRequestObject.REQUEST_DESTROY ) {
////          rl.remove(RO);
////        }
////      }
////      // ���û���������е��ˣ���ֱ��ɾ��
////      if ( rl.size() == 0 )
////        RequestLists.remove(TypeName);
////    }
  }
}
