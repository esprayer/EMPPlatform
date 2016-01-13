package jformservice.jdof.classes;


import com.eai.form.dal.update.*;
import com.eai.form.form.editor.*;
import com.eai.form.uvl.classes.*;
import com.eai.form.uvl.event.*;
import com.eai.frame.fcl.classes.message.*;
import com.eai.frame.fcl.interfaces.message.*;
import com.pansoft.pub.util.*;
import com.eai.form.dal.interfaces.IDataSetDefine;
import com.eai.form.dal.interfaces.IDataSetOperation;
import com.eai.form.uvl.interfaces.IFormSTUB;
import jfoundation.dataobject.classes.JParamObject;
import jframework.foundation.classes.JActiveDComDM;
import jframework.foundation.classes.JAppSvrStub;
import jframework.foundation.classes.JAppSvrManager;
/**
 * <p>JDataWindow</p>
 * <p>客户端的数据窗口.
 *    客户端的所有数据窗对象均从本类继承。</p>
 * <p>Copyright (c) 2003</p>
 * <p>Pansoft.</p>
 * @author Fndlvr
 * @version 1.0
 */

public class JDataWindow extends TFormSTUB{
  private   String    mServiceName= null;
  private   String    mOpenMethodName= null;
  private   String    mUpdateMethodName= null;
  //调用接口
  private   JFSDKCall mCall  =  new JFSDKCall("FormService","OpenVirtual","UpdateEdit");

  /**
   * 回调方法。打开。
   * 缺省的打开是调用服务器上的FormService。
   * @param POEnv    客户端的PO。
   * @param MSG      输入对象ExchangeMessage.
   * @return         调用完成后的返回对象。
   */
  protected boolean onOpenForm( IExchangeMessage MSG ){
    /**
     * 开始调用,把参数传入
     */
    if (mServiceName != null && mOpenMethodName != null  ) {
      if ( mCall.CallRmt(mServiceName,mOpenMethodName,MSG.getInputMessage(),MSG.getResultMessage())){
        return true;
      }
      else
        return false;
    }
    else{
      if ( mCall.CallRmt("FormService","OpenVirtual",MSG.getInputMessage(),MSG.getResultMessage())){
        return true;
      }
      else
        return false;
    }

  }

  /**
   * 回调方法。打开。
   * 缺省的打开是调用服务器上的FormService。
   * @param POEnv    客户端的PO。
   * @param MSG      输入对象ExchangeMessage.
   * @return         调用完成后的返回对象。
   */
  protected boolean onOpenFormEx( IExchangeMessage MSG ){
    /**
     * 开始调用,把参数传入
     */
    if (mServiceName != null && mOpenMethodName != null  ) {
      if ( mCall.CallRmt(mServiceName,mOpenMethodName,MSG.getInputMessage(),MSG.getResultMessage())){
        return true;
      }
      else
        return false;
    }
    else{
      if ( mCall.CallRmt("FormService","OpenVirtualEx",MSG.getInputMessage(),MSG.getResultMessage())){
        return true;
      }
      else
        return false;
    }

  }

  /**
   * 回调方法。更新。
   * 缺省的打开是调用服务器上的FormService。
   * @param POEnv    客户端的PO。
   * @param MSG      输入对象ExchangeMessage.
   * @return         调用完成后的返回对象。
   */
  protected boolean onUpdateForm( IExchangeMessage MSG ){
    /**
     * 开始调用,把参数传入
     */
    if (mServiceName != null && mOpenMethodName != null  ) {
      if ( mCall.CallRmt(mServiceName,mUpdateMethodName,MSG.getInputMessage(),MSG.getResultMessage())){
        return true;
      }
      else
        return false;
    }
    else{
      if ( mCall.CallRmt("FormService","UpdateEdit",MSG.getInputMessage(),MSG.getResultMessage())){
        return true;
      }
      else
        return false;
    }


  }
  public boolean CallRmt(String strRmtAppName,String strRmtObjName,String strRmtMethodName, IMessage  MsgIn,IMessage MsgOut,boolean isBackground){
    return false;
  }

  /**
   * 构造方法。适用于动态的数据窗口开发。在构造方法中须给出有服务器端的调用对象、打开调用方法、更新调用方法。
   * @param strServiceName     服务端的调用对象名字。
   * @param strOpenMethodName  服务端的打开调用方法名字。
   * @param mUpdateMethodName  服务端的更新调用方法名字。
   */
  public JDataWindow(String strServiceName,String strOpenMethodName,String strUpdateMethodName) {
    super();
    onCreatePO();
    mServiceName         = strServiceName;
    mOpenMethodName      = strOpenMethodName;
    mUpdateMethodName    = strUpdateMethodName;

    super.Prepare();


    super.setCallInterfaceRef(mCall);
  }

  public JDataWindow(String strServiceName,String strOpenMethodName,String strUpdateMethodName,JParamObject PO) {
    super();

    mCall.setSystemPO(PO);

    mServiceName         = strServiceName;
    mOpenMethodName      = strOpenMethodName;
    mUpdateMethodName    = strUpdateMethodName;

    super.Prepare();


    super.setCallInterfaceRef(mCall);
  }

  /**
   * 构造方法。适用于静态的数据窗口开发。
   * 如果决定在客户端使用静态数据窗，可以使用本构造方法。这样，数据的装入与更新都是通过系统缺省的服务对象进行的。
   */
  public JDataWindow() {
    super();
    onCreatePO();
    mServiceName = null;
    mOpenMethodName = null;
    mUpdateMethodName = null;
    super.Prepare();

    super.setCallInterfaceRef(mCall);

  }

  public JDataWindow(JParamObject PO) {
    super();

    mCall.setSystemPO(PO);

    mServiceName = null;
    mOpenMethodName = null;
    mUpdateMethodName = null;
    super.Prepare();

    super.setCallInterfaceRef(mCall);

  }
  /**
   *
   * @param FormName
   * @param MSG
   * @return
   */
  public boolean OpenNoFormat(String FormName, IExchangeMessage MSG){
    super.setFormName( FormName );
    MSG.getInputMessage().CreateItem("ReqFormat",false);
    return Open(FormName,MSG);
  }

  /**
   * 打开服务上的数据窗。
   * @param FormName      位于服务器上的事先定义好的数据窗的名字。
   *                      对于静态数据窗，这个参数是必须的；对于动态窗，该参数设为"".
   * @param MSG           调用时的输入参数。可以放入自己需要的变量参数。这些如果服务端页面服务需要的话将作为输入参数,来产生不同的页面数据.
   * @return              打开成功与否。
   */
  public boolean Open(String FormName, IExchangeMessage MSG) {
    super.setFormName( FormName );

    //暂存传入的输入消息
    IMessage MsgInput = MSG.getInputMessage();

    //是否需要格式
    boolean  isReqFormat = MsgInput.asBoolValue("ReqFormat",true);
    /**
     * 设置是否需要数据集
     */
    MsgInput.CreateItem("ReqDataSet",super.getActiveDataSetRef() == null);

    //使用一个新的输入消息。
//    MSG.setInputMessage( new Message());

    MsgInput.CreateItem("Name",FormName);
    /**
     * 在输入消息里设置名字
     */
//    MSG.getInputMessage().CreateItem("Name", FormName );

    /**
     * 在输入消息里加入一个新的MSG。
     */
//    MSG.getInputMessage().CreateItem("msgInput",MsgInput);

    /**
     * 保存一下SQL参数
     */
    Object obj = MsgInput.asObjectValue("SqlValues");
    if ( obj != null){
      super.setSqlValues( (IMessage)obj );
    }
    /**
     * 本地参数环境
     */
    //激活事件
    super.ActionFormListener(TFormEvent.FORM_EVENT_OPEN, FormName);

    /**
     * 开始调用,把参数传入
     */
    boolean mIsOK = onOpenForm(MSG);

    //激活事件
    super.ActionFormListener(TFormEvent.FORM_EVENT_OPENED, FormName);

    if ( mIsOK ) {
      //得到结果消息接口
      super.setResultMessage(MSG.getResultMessage());       //保存结果
      super.getResultMessage().CreateItem("ReqFormat",isReqFormat);
      //如果正确，有结果存在
      //-------------------------------------------------------------------------------------
      /**
       * 本次调用如何成功,将在Result中返回如下结果
       * SQLRESULT : 首个数据集IDataExchange
       * DSDEFINE  : 数据集的定义格式XML文本
       * FORMDEFINE: 视的定义格式文本
       */
      String RE = MSG.getResultMessage().asStringValue("RESULT", "");
      if (RE.equals("OK") || RE.equals("")) {
        return ProcessResult(MSG.getInputMessage(),MSG.getResultMessage());
      }
      else {
        System.err.println( super.getResultMessage().asStringValue("MSG", "ERROR"));
        return false;
        //return ProcessResult(MSG.getInputMessage(),MSG.getResultMessage());
      }
    }
    else {
      //得到结果消息接口
      super.setResultMessage(MSG.getResultMessage());       //保存结果

      return false;
    }

  }

  /**
   * 打开服务上的数据窗。
   * @param FormName      位于服务器上的事先定义好的数据窗的名字。
   *                      对于静态数据窗，这个参数是必须的；对于动态窗，该参数设为"".
   * @param MSG           调用时的输入参数。可以放入自己需要的变量参数。这些如果服务端页面服务需要的话将作为输入参数,来产生不同的页面数据.
   * @return              打开成功与否。
   */
  public boolean OpenSqlOnly(String FormName, IExchangeMessage MSG) {
    super.setFormName( FormName );

    //暂存传入的输入消息
    IMessage MsgInput = MSG.getInputMessage();

    //是否需要格式
    boolean  isReqFormat = MsgInput.asBoolValue("ReqFormat",true);
    /**
     * 设置是否需要数据集
     */
    MsgInput.CreateItem("ReqDataSet",super.getActiveDataSetRef() == null);
    MsgInput.CreateItem("Name",FormName);

    /**
     * 保存一下SQL参数
     */
    Object obj = MsgInput.asObjectValue("SqlValues");
    if ( obj != null){
      super.setSqlValues( (IMessage)obj );
    }
    /**
     * 本地参数环境
     */
    //激活事件
    super.ActionFormListener(TFormEvent.FORM_EVENT_OPEN, FormName);

    /**
     * 开始调用,把参数传入
     */
    boolean mIsOK = onOpenFormEx(MSG);

    //激活事件
    super.ActionFormListener(TFormEvent.FORM_EVENT_OPENED, FormName);

    if ( mIsOK ) {
      //得到结果消息接口
      super.setResultMessage(MSG.getResultMessage());       //保存结果
      super.getResultMessage().CreateItem("ReqFormat",isReqFormat);
      //如果正确，有结果存在
      //-------------------------------------------------------------------------------------
      /**
       * 本次调用如何成功,将在Result中返回如下结果
       * SQLRESULT : 首个数据集IDataExchange
       * DSDEFINE  : 数据集的定义格式XML文本
       * FORMDEFINE: 视的定义格式文本
       */
      String RE = MSG.getResultMessage().asStringValue("RESULT", "");
      boolean isOK = false;
      if (RE.equals("OK") || RE.equals("")) {
        isOK = ProcessResult(MSG.getInputMessage(),MSG.getResultMessage());
      }
      else {
        Debug.PrintlnMessageToSystem(MSG.getResultMessage().asStringValue("MSG", "ERROR!"));
        isOK = ProcessResult(MSG.getInputMessage(),MSG.getResultMessage());
      }

      //设置右键菜单。
      setupPopMenu(this);

      return isOK;
    }
    else {

      return false;
    }

  }

  private void setupPopMenu(IFormSTUB pForm){
    IDataSetDefine pDSDefine = pForm.getDataSetInterface().getDataSetDefine();
    IDataSetOperation pUserOpt = pDSDefine.getUserOperations();

    pUserOpt.setCtrlVisible(pUserOpt.T_FIND,true);
    pUserOpt.setCtrlVisible(pUserOpt.T_ADVSORT,true);
    pUserOpt.setCtrlVisible(pUserOpt.T_EXPORT,true);
    pUserOpt.setCtrlVisible(pUserOpt.T_FILTER,true);
    pUserOpt.setCtrlVisible(pUserOpt.T_IMPORT,true);
    pUserOpt.setCtrlVisible(pUserOpt.T_PASTETEXT,true);
    pUserOpt.setCtrlVisible(pUserOpt.T_SORT,true);



  }

  /**
   * 提交所做修改至服务器。
   * @return     如果提交成功，返回TRUE.
   *             如果失败，可以用this.getResultMessage().asStringValue("MSG","")得到失败信息。
   *             提交成功后，若想清除客户端数据窗的所有更新状态，可调用getDataSet().Reset()方法清除。
   *                        若不清，下次更新时将仍然包含本次更新结果。
   * 参数：
   *      MSG:   客户端的一个参数对象。
   * 在提交过程中，客户端生成一组描述更新信息的XML，发往服务器的服务对象。
   * 如果是静态数据窗，这个请求XML会发送至系统已经注册的FormService对象。
   * 如果是动态数据窗，这信请求XML会发送至构造本类时给定的服务端的服务对象及更新方法。
   */
  public boolean Update(IExchangeMessage MSG,IUpdateRecallEvent IRecall,String strUpdateInfo) {
    //得到接口并生成
    String UpdateXml = strUpdateInfo;

    IMessage  IMsgInput = new Message();
    IMsgInput.CloneFrom( MSG.getInputMessage());
    IMsgInput.CreateItem("UPDATE", UpdateXml);

    MSG.setInputMessage( IMsgInput );
    /**
     * 下面提交,
     * 首先设置输入参数
     */
    //激活开始更瓣事件,如果返回FALSE,不再更新。
    if (  !super.ActionFormListener(TFormEvent.FORM_EVENT_UPDATE, getFormName()) ){
       return false;
    }

    /**
     * 开始调用,把参数传入
     */
    boolean isOK = onUpdateForm( MSG);

    /**
     * 更新完毕事件
     */
    super.ActionFormListener(TFormEvent.FORM_EVENT_UPDATED, getFormName());

    if ( isOK ) {
      //得到结果消息接口
      super.setResultMessage( MSG.getResultMessage()); //保存结果
      String RE = MSG.getResultMessage().asStringValue("SQLRESULT", "");

      if (RE != null && RE.equals("OK")) {
        Debug.PrintlnMessageToSystem("提交成功！");
        return true;
      }
      else {
        Debug.PrintlnMessageToSystem(MSG.getResultMessage().asStringValue("MSG", "ERROR!"));
        return false;
      }
    }
    else {

      return false;
    }



  }

  /**
   * 提交所做修改至服务器。
   * @return     如果提交成功，返回TRUE.
   *             如果失败，可以用this.getResultMessage().asStringValue("MSG","")得到失败信息。
   *             提交成功后，若想清除客户端数据窗的所有更新状态，可调用getDataSet().Reset()方法清除。
   *                        若不清，下次更新时将仍然包含本次更新结果。
   * 参数：
   *      MSG:   客户端的一个参数对象。
   * 在提交过程中，客户端生成一组描述更新信息的XML，发往服务器的服务对象。
   * 如果是静态数据窗，这个请求XML会发送至系统已经注册的FormService对象。
   * 如果是动态数据窗，这信请求XML会发送至构造本类时给定的服务端的服务对象及更新方法。
   */
  public boolean Update(IExchangeMessage MSG,IUpdateRecallEvent IRecall,IUpdateInfo IUpdateInfo) {

    IMessage  IMsgInput = new Message();
    IMsgInput.CloneFrom( MSG.getInputMessage());

    if ( IRecall != null ){
      //IDataSet == null ,then build IRecall's only.
      IUpdateInfo.CreateFromDataSet(null,false,IRecall);
    }
    //得到接口并生成
    String UpdateXml = IUpdateInfo.OutputToString();

    IMsgInput.CreateItem("UPDATE", UpdateXml);

    MSG.setInputMessage( IMsgInput );
    /**
     * 下面提交,
     * 首先设置输入参数
     */
    //激活开始更瓣事件,如果返回FALSE,不再更新。
    if (  !super.ActionFormListener(TFormEvent.FORM_EVENT_UPDATE, getFormName()) ){
       return false;
    }

    /**
     * 开始调用,把参数传入
     */
    boolean isOK = onUpdateForm( MSG);

    /**
     * 更新完毕事件
     */
    super.ActionFormListener(TFormEvent.FORM_EVENT_UPDATED, getFormName());

    if ( isOK ) {
      //得到结果消息接口
      super.setResultMessage( MSG.getResultMessage()); //保存结果
      String RE = MSG.getResultMessage().asStringValue("SQLRESULT", "");

      if (RE != null && RE.equals("OK")) {
        Debug.PrintlnMessageToSystem("提交成功！");
        return true;
      }
      else {
        Debug.PrintlnMessageToSystem(MSG.getResultMessage().asStringValue("MSG", "ERROR!"));
        return false;
      }
    }
    else {

      return false;
    }

  }


  /**
   * 提交所做修改至服务器。
   * @return     如果提交成功，返回TRUE.
   *             如果失败，可以用this.getResultMessage().asStringValue("MSG","")得到失败信息。
   *             提交成功后，若想清除客户端数据窗的所有更新状态，可调用getDataSet().Reset()方法清除。
   *                        若不清，下次更新时将仍然包含本次更新结果。
   * 参数：
   *      MSG:   客户端的一个参数对象。
   * 在提交过程中，客户端生成一组描述更新信息的XML，发往服务器的服务对象。
   * 如果是静态数据窗，这个请求XML会发送至系统已经注册的FormService对象。
   * 如果是动态数据窗，这信请求XML会发送至构造本类时给定的服务端的服务对象及更新方法。
   */
  public boolean Update(IExchangeMessage MSG,IUpdateRecallEvent IRecall) {
    //得到接口并生成
    TUpdateInfo UpdateInfo = new TUpdateInfo();
    UpdateInfo.CreateFromDataSet(super.getDataSetInterface(),IRecall );
    String UpdateXml = UpdateInfo.GetXmlString();
    return Update(MSG,IRecall,UpdateXml);
  }


  /**
   * 提交所做修改至服务器。
   * @return     如果提交成功，返回TRUE.
   *             如果失败，可以用this.getResultMessage().asStringValue("MSG","")得到失败信息。
   *             提交成功后，若想清除客户端数据窗的所有更新状态，可调用getDataSet().Reset()方法清除。
   *                        若不清，下次更新时将仍然包含本次更新结果。
   * 参数：
   *      MSG:   客户端的一个参数对象。
   * 在提交过程中，客户端生成一组描述更新信息的XML，发往服务器的服务对象。
   * 如果是静态数据窗，这个请求XML会发送至系统已经注册的FormService对象。
   * 如果是动态数据窗，这信请求XML会发送至构造本类时给定的服务端的服务对象及更新方法。
   */
  public boolean Update(IExchangeMessage MSG) {
    return Update(MSG,null);
  }

  /**
   * 提交所做修改至服务器。
   * @return     如果提交成功，返回TRUE.
   *             如果失败，可以用this.getResultMessage().asStringValue("MSG","")得到失败信息。
   *             提交成功后，若想清除客户端数据窗的所有更新状态，可调用getDataSet().Reset()方法清除。
   *                        若不清，下次更新时将仍然包含本次更新结果。
   * 在提交过程中，客户端生成一组描述更新信息的XML，发往服务器的服务对象。
   * 如果是静态数据窗，这个请求XML会发送至系统已经注册的FormService对象。
   * 如果是动态数据窗，这信请求XML会发送至构造本类时给定的服务端的服务对象及更新方法。
   */
  public boolean Update() {
    /**
     * 下面提交,
     * 首先设置输入参数
     */
    ExchangeMessage MSG = new ExchangeMessage("","");
    return Update(MSG);

  }

  public boolean Update(IUpdateRecallEvent IEvent) {
    /**
     * 下面提交,
     * 首先设置输入参数
     */
    ExchangeMessage MSG = new ExchangeMessage("","");
    return Update(MSG,IEvent);
  }

  public  IEditor  getActiveEditor(){
    return super.getDataForm().getFormUI().getActiveEditCtrl();
  }

  private JParamObject onCreatePO(){
    JParamObject PO = (JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");

    //获得需要的URL。
    mCall.setSystemPO(PO);

    return PO;
  }


  /**
   * 自动判断，生成一个调用地址。
   * @return String
   */
  private String getAutoCommBase(JParamObject pInputPO) {
    String strCommBase = "";
    /**
     * 新加的处理过程。自动判断调用来原及服务器对象位于哪个服务器上。
     */
    String pClsName = null;

    /**
     *制造异常，获得调用祖先。
     */
    JAppSvrStub pSvr = null;
    try {
      int i = 1 / 0;
    }
    catch (Exception E) {
      StackTraceElement[] pELS = E.getStackTrace();
      StringBuffer pRES = new StringBuffer();

      for (int i = 0; i < pELS.length; i++) {
        pClsName = pELS[i].getClassName();

        pSvr = JAppSvrManager.getDefault().getServerByClassInfo(pClsName);
        if (pSvr != null) {
          strCommBase = pSvr.getUrl();

          System.out.println(pClsName);
          //设置调者的目的服务器。
          pInputPO.SetValueByParamName("Server", pSvr.getName());

          //找到了资金系统调用。
          break;
        }
      }
    }
    return strCommBase;
  }

}
