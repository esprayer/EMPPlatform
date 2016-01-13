package jformservice.jdof.classes;

import com.eai.form.uvl.interfaces.*;
import com.eai.frame.fcl.classes.message.*;
import com.eai.frame.fcl.interfaces.message.*;
import com.eai.tools.translate.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;

/**
 * <p>JFSDKCall</p>
 * <p>SDK的调用接口</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JFSDKCall implements IFormRmtAgent {
 private String  mServiceName,mstrOpenname,mstrUpdateName;
 private boolean mIsOnline  = true;

 private boolean mReqTranslate = false;

 private JParamObject mSystemPO  = null;
 private boolean mIsOnlineAuto = true;

 public void setSystemPO(JParamObject pPO) {
   mSystemPO = pPO;
 }

 public String getClientRunTimeInfo() {
   return "";
 }

 public void setRmtOnline(boolean isOff) {
   mIsOnline = isOff;
 }

 public boolean isRmtOnline() {
   return mIsOnline;
 }

 public  String getRmtAppName(){
   return "";
 }
 public    void setRmtAppName(String strName){

 }

 public void setRmtLinkDefault(boolean isAuto) {
   mIsOnlineAuto = isAuto;
 }

 public boolean isRmtLinkDefault() {
   return mIsOnlineAuto;
 }

  /**
   * 实现远程的调用
   * @param parm1
   * @param parm2
   * @param parm3
   * @param parm4
   * @return
   */
  public JFSDKCall(String strServiceName,String strOpenname,String strUpdateName){
    mServiceName = strServiceName;
    mstrOpenname = strOpenname;
    mstrUpdateName = strUpdateName;

//    URL pUrl = getClass().getResource("/_FormServiceCfg.xml");
//    if ( pUrl!=null){
//      try{
//        XmlEngine pXml = new XmlEngine(pUrl.openStream());
//        pXml.OpenRootSection("FormService");
//        String strReq = pXml.getKeyValue("ReqTranslate","0");
//        mReqTranslate = strReq.equals("1");
//      }
//      catch(Exception E){
//        mReqTranslate = false;
//      }


//    }

    TCoding.REQ_TRANSLATE = mReqTranslate;

  }
  public String getRmtServiceName(){
    return mServiceName;
  }
  public String getRmtOpenCallName(){
    return mstrOpenname;
  }
  public String getRmtUpdateCallName(){
    return mstrUpdateName;
  }

  public boolean CallRmt(String strRmtObjName,String strRmtMethodName, IMessage  MsgIn,IMessage MsgOut){
    Object Res = null;
    JParamObject PO = null;
    if ( mSystemPO == null ){
      PO = (JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
    }
    else{
      PO = mSystemPO;
    }

    /**
     * 这里根据是否在线，PO设置一个变量。
     */
    if ( this.isRmtLinkDefault() ){
      PO.SetValueByEnvName("OFFLINE",JActiveDComDM.SystemOffline?"1":"0");
    }
    else{
      PO.SetValueByEnvName("OFFLINE"    ,mIsOnline?"0":"1");
      PO.SetValueByEnvName("OFFLINEINIT",mIsOnline?"0":"1");
    }
    /**
     * 开始调用,把参数传入
     */
    JResponseObject RO = null;

    IExchangeMessage MSG = new ExchangeMessage("","" );
    MSG.getInputMessage().CreateItem("msgInput",MsgIn );

    //把名字放入：
    String strFormName = MsgIn.asStringValue("Name","");
    if ( strFormName.length() > 0 ){
      MSG.getInputMessage().CreateItem("Name",strFormName);
    }

    MSG.setResultMessage(MsgOut);

    //开始FINANCE框架调用
    try {
      RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
          InvokeObjectMethod(
          strRmtObjName,
          strRmtMethodName,
          PO,
          MSG);


      if (RO.ErrorCode == 0) {
        //取得返回值
        IExchangeMessage Result = (IExchangeMessage) (RO.ResponseObject);
        MsgOut.CloneFrom(Result.getResultMessage());
      }
      else {
        //读取FISDK的错误。只有ErrorCode<>0时才是错误。
        MsgOut.CreateItem("RESULT","ERROR");
        MsgOut.CreateItem("MSG",RO.ErrorString);
      }
      return true;
    }
    catch (Exception E) {
      //读取FISDK的错误。只有ErrorCode<>0时才是错误。
      MsgOut.CreateItem("RESULT","ERROR");
      MsgOut.CreateItem("MSG",E.toString());
      return false;
    }

  }

  public boolean CallRmt(String strRmtAppName,String strRmtObjName,String strRmtMethodName, IMessage  MsgIn,IMessage MsgOut,boolean isBackground){
    Object Res = null;
    JParamObject PO = null;
    if ( mSystemPO == null ){
      PO = (JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
    }
    else{
      PO = mSystemPO;
    }

    /**
     * 这里根据是否在线，PO设置一个变量。
     */
    if ( this.isRmtLinkDefault() ){
      PO.SetValueByEnvName("OFFLINE",JActiveDComDM.SystemOffline?"1":"0");
    }
    else{
      PO.SetValueByEnvName("OFFLINE"    ,mIsOnline?"0":"1");
      PO.SetValueByEnvName("OFFLINEINIT",mIsOnline?"0":"1");
    }
    /**
     * 开始调用,把参数传入
     */
    JResponseObject RO = null;

    IExchangeMessage MSG = new ExchangeMessage("","" );
    MSG.getInputMessage().CreateItem("msgInput",MsgIn );

    //把名字放入：
    String strFormName = MsgIn.asStringValue("Name","");
    if ( strFormName.length() > 0 ){
      MSG.getInputMessage().CreateItem("Name",strFormName);
    }

    MSG.setResultMessage(MsgOut);

    //开始FINANCE框架调用
    try {
      RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(
          strRmtObjName,
          strRmtMethodName,
          PO,
          MSG);


      if (RO.ErrorCode == 0) {
        //取得返回值
        IExchangeMessage Result = (IExchangeMessage) (RO.ResponseObject);
        MsgOut.CloneFrom(Result.getResultMessage());
      }
      else {
        //读取FISDK的错误。只有ErrorCode<>0时才是错误。
        MsgOut.CreateItem("RESULT", "ERROR");
        MsgOut.CreateItem("MSG", RO.ErrorString);
      }

      return true;

    }
    catch (Exception E) {
      return false;
    }

  }

}
