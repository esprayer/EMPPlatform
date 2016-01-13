package jframework.foundation.classes;

import com.efounder.eai.framework.IActiveFramework;
import com.efounder.eai.framework.IActiveObject;
import com.efounder.object.JDComObject;
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
//描述: 框架所管理的对象,所有框架管理的对象都是从这个类继承的
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JActiveObject extends JDComObject implements IActiveObject {
  public IActiveFramework oIActiveFramework = null;
  // 对象所在的框架
  public com.efounder.eai.framework.IActiveFramework  ActiveFramework   = null;
  // 对象的Stub
  public JActiveObjectStub ActiveObjectStub  = null;
  // 对象所在框架的Stub
  public JFwkActiveObjectStub FwkActiveObjectStub = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JActiveObject() {
  }
  //------------------------------------------------------------------------------------------------
  //描述: 返回对象所在的活动框架,对象只有知道对象所在的活动框架的引用,才能正确的调用框架所提供的功能
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public com.efounder.eai.framework.IActiveFramework getActiveFramework() {
    return ActiveFramework;
  }
  //------------------------------------------------------------------------------------------------
  //描述: 设置框架所在的活动框架
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------

  public void setActiveFramework(com.efounder.eai.framework.IActiveFramework ActiveFramework) {
    this.ActiveFramework = ActiveFramework;
  }
  //------------------------------------------------------------------------------------------------
  //描述: 获取对象存根,对象是可以访问对存根的
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object getActiveObjectStub() {
    return ActiveObjectStub;
  }
  //------------------------------------------------------------------------------------------------
  //描述: 设置对象存根
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setActiveObjectStub(Object ActiveObjectStub) {
    this.ActiveObjectStub = (JActiveObjectStub)ActiveObjectStub;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 活动对象多方法调用,框架在调用对象的方法时都是经过这个方法进行的
  //      方法名要注意大小写
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Object InvokeObjectMethod(String ActiveObjectMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject)  throws Exception {
    Class cls;
    Method Mth;Object Res=null;
    Method[] MA;int i,Count;String MName;
//      try {
          // 在这里形成参数数组
          Object argsArray[] = { Param,Data,CustomObject,AdditiveObject };
          Res = InvokeMethod(ActiveObjectMethodName,argsArray);
//      } catch ( Exception e) {
//        e.printStackTrace();
//      }
      return Res;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object DestroyObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    return null;
  }
}
