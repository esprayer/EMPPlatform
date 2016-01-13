package jformservice.jdal.classes;
import java.sql.*;

import com.eai.form.dal.*;
import com.eai.form.eaiservices.interfaces.form.*;
import com.eai.frame.fcl.interfaces.message.*;
import jfoundation.dataobject.classes.*;
import jfoundation.sql.classes.*;
import jformservice.jdal.classes.DALFormService.DBOFormService;
import jformservice.jdal.classes.DALFormService.DBOFormStorage;
import com.eai.form.dal.update.IUpdateInfo;
import com.eai.form.dal.update.TUpdateInfo;

/**
 * <p>JDataWindowService</p>
 * <p>服务端动态数据窗口的基类。
 *    如果要在服务端提动态数据窗，可从本类继承。</p>
 * <p>Copyright (c) 2003</p>
 * <p>Pansoft.</p>
 * @author Fndlvr.
 * @version 1.0
 */

public class JDataWindowService extends DBOFormService{
  protected String mApplicationName = null;

  /**
   * 构造方法。
   * @param mAppName  应用名称。
   *                  本名称决定装入定义文件时的路径。这个路径是：
   *                  /WEB-INFO/Classes/Resource/Resource/zh/XML/mAppName/XML.
   *                  若指定null或者"",则使用JDataCenter作为值传入。
   */
  public JDataWindowService(String mAppName) {
    mApplicationName = mAppName;
  }

  protected String   getApplicationName(){
    return mApplicationName;
  }

  /**
   * 找开一个服务FORM
   * @param SqlStr
   * @param ParameterXmlString
   * @return
   * 返回以下几个数据值
   * SQLRESULT : 首个数据集IDataExchange
   * DSDEFINE  : 数据集的定义格式XML文本
   * FORMDEFINE: 视的定义格式文本
   * 本方法不需要子类重载.
   */
  public int Open( JConnection Con,JParamObject ENV,String FName ,IMessage _MsgInput,IMessage _MsgResult) {
    /**
     * 本方法需要根据名称建立一个Formdefine,从中找出SQL,及定义信息,打开DS
     */
    super.OpenVirtual(Con,ENV,FName ,_MsgInput,_MsgResult,mApplicationName);

    return 1;

  }

  /**
   * 客户端修改的数据提交
   * @param Con            数据库连接。本连接需要在外部建立。
   * @param ENV            客户端的PO。
   * @param _MsgInput      客户端的输入消息。
   * @param _MsgResult     客户端的输出消息。
   * @return               成功与否。
   */
  public boolean Update( JConnection Con, JParamObject ENV,IMessage _MsgInput ,IMessage _MsgResult ) {



    return super.UpdateEdit (Con,_MsgInput,_MsgResult);
  }

  /**
   * 客户端修改的数据提交
   * @param Con          数据库连接。本连接需要在外部建立。
   * @param ENV          客户端的PO。
   * @param IInfo        客户端的更新对象接口。
   * @param _MsgResult   客户端的输出消息。
   * @return
   */
  public boolean Update(JConnection Con, JParamObject ENV,IUpdateInfo IInfo ,IMessage _MsgResult ){
    return super.UpdateEdit(Con,IInfo, _MsgResult);
  }

  /**
   * 获得定义信息存取对象。
   * @return            对象。
   */
  public DBOFormStorage getStorageObject(){
    return mStg;
  }

  /**
   * 得到一个修改器,由此可以进行数据修改。
   * @param _MsgInput  客户端的输入消息。
   * @return           修改器对象接口。
   */
  protected IUpdateInfo CreateUpdateReference(IMessage _MsgInput){
    String UString = _MsgInput.asStringValue("UPDATE","");
    TUpdateInfo INFO = new TUpdateInfo(UString);

    return INFO;
  }
}