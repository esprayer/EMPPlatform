package com.efounder.service.func;

import org.openide.*;
import org.openide.util.*;
import com.efounder.eai.ide.*;
import com.efounder.model.biz.*;
import com.efounder.service.script.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BIZExecuteManager {
  /**
   *
   */
  protected BIZExecuteManager() {
  }
  /**
   *
   * @param bizFunction BIZFunction
   * @return BIZExecuteFunction
   */
  public static BIZExecuteFunction getInstance(BIZFunction bizFunction) {
    return getInstance(bizFunction,null,null);
  }
  /**
   *
   * @param bizFunction BIZFunction
   * @param userObject Object
   * @return BIZExecuteFunction
   */
  public static BIZExecuteFunction getInstance(BIZFunction bizFunction,Object userObject) {
    return getInstance(bizFunction,userObject,null);
  }
  /**
   *
   * @param execKey String
   * @param userObject Object
   * @param addinObject Object
   * @return BIZExecuteFunction
   */
  public static BIZExecuteFunction getInstance(BIZFunction bizFunction,Object userObject,Object addinObject) {
    BIZExecuteFunctionAdapter execute = (BIZExecuteFunctionAdapter)Lookup.getDefault().lookups(BIZExecuteFunction.class,bizFunction.getEntityKey());
    if ( execute == null ) return null;
    execute.setBizFunction(bizFunction);
    execute.setUserObject(userObject);execute.setAddinObject(addinObject);
    return execute;
  }
  /**
   *
   * @param exec BIZExecuteFunction
   * @return Object
   * @throws Exception
   */
  public static Object execute(BIZExecuteFunction exec) throws Exception {
    Object responseObject = null;
    BIZExecuteFunctionAdapter exe = (BIZExecuteFunctionAdapter)exec;
    BIZFunction bizFunction = exe.getBizFunction();
    ThreadExecute threadExecute = ThreadExecute.getInstance(exec,bizFunction);
    ScriptManager sm = (ScriptManager) bizFunction.getBIZContext().
                                   getBIZValue("scriptManager", null);
    if(sm!=null){
      String script=(String)bizFunction.getBIZValue("sScript","");
      if(!"".equals(script)){
        Object view=(Object)bizFunction.getBIZContext().
                                   getBIZValue("FlowApplicationView", null);
          System.out.println(sm.runFunction((Scriptable)view, script));

        }
      }
    if ( exe.isThread(bizFunction) ) {
      // 使用线程执行
      WaitingManager.getDefault().waitInvoke(bizFunction.getEntityName(),
                                             "正在执行" + bizFunction.getEntityName(),
                                             ExplorerIcons.getExplorerIcon(
                                             bizFunction.getEntityLargeIcon()),
                                             threadExecute);
    } else {
      RequestProcessor.Task openNodeObjectTask = null;
      openNodeObjectTask = RequestProcessor.getDefault().create(threadExecute);
      openNodeObjectTask.schedule(0);
    }
    return responseObject;
  }
  /**
   *
   * @param exec BIZExecuteFunction
   * @param bizFunction BIZFunction
   * @param threadExecute ThreadExecute
   * @throws Exception
   */
  public static void processResponse(BIZExecuteFunction exec,BIZFunction bizFunction,
                                        ThreadExecute threadExecute) throws Exception {
    // 如果功能，线程，结果有一个为空，则不处理
//    if ( exec == null || threadExecute == null || threadExecute.getResponseObject() == null ) return;
    if ( exec == null || threadExecute == null ) return;
    // 获取
    FuncResponseProcess responseProcess = (FuncResponseProcess)Lookup.getDefault().lookups(FuncResponseProcess.class,bizFunction.getEntityKey());
    if ( responseProcess != null ) {
      responseProcess.processResponse(exec, bizFunction,
                                      threadExecute.getPrepareObject(),
                                      threadExecute.getResponseObject());
    }
  }
  /**
   * 只以非线程方式执行
   * @param exec BIZExecuteFunction
   * @return Object
   * @throws Exception
   */
  public static Object waitInvoke(BIZExecuteFunction exec) throws Exception {
    Object responseObject = null;
    BIZExecuteFunctionAdapter exe = (BIZExecuteFunctionAdapter)exec;
    BIZFunction bizFunction = exe.getBizFunction();
    ThreadExecute threadExecute = ThreadExecute.getInstance(exec,bizFunction);
    try {
      WaitingManager.getDefault().beginWait(null);
      responseObject = ThreadExecute.execute(threadExecute);
    } finally {
      WaitingManager.getDefault().endWait(null);
    }
    return responseObject;
  }
  /**
   WaitingManager.getDefault().waitInvoke(this.getShortText(),"正在执行"+this.getShortText(),this.getSmallIcon(),this);
   */
  }
