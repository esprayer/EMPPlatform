package jframework.foundation.classes;

import org.jdom.Element;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述: 框架管理对象的Stub,根据它可以找到对象,并生成对象的实例
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JActiveObjectStub {

  public Class          ActiveObjectClass   = null;  // 对象类信息
  public String         id;                          // 对象名称
  public String         guid;                        // 对象GUID
  public String         name;                        // 对象的描述
  public String         objectclass;                 // 对象的类名称
  public String         ver;                         // 对象的版本号
  // 对象的类型,这个类型不是对象的类,而是对象是业务对象还数据对象,或是流程对象,也有可能是其他自定义对象
  public String         type;
  public Element        PublicAttrib         = null; // 对象在注册表中公用属性元素
  public String         mRmtServer           = "";
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JActiveObjectStub() {
  }
}
