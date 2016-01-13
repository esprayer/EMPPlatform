package jbof.application.classes.operate;

import java.io.Serializable;

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
public class JOperateItemStub
    implements Serializable {
  public String OperateName = null;
  public String OperateNo = null;
  public String Description = null;
  public String OperateObject = null;
  public String OperateMethod = null;
  public String ParamString = null;
  public String ParamData = null;
  public String Standard = null;
  public String MenuFileName=null;
  public JOperateItemStub() {
  }
}
