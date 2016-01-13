package com.efounder.bz.flow;

import com.efounder.bz.flow.drive.NodeTaskDataSet;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FlowResult implements java.io.Serializable {
  /**
   *
   */
  protected int code = _FLOW_RESULT_SUCESS_;
  /**
   *
   * @return int
   */
  public int getCode() {
    return code;
  }
  /**
   *
   */
  protected String message = null;
  /**
   *
   * @return String
   */
  public String getMessage() {
    return message;
  }
  /**
   *
   * @return FlowResult
   */
  public static FlowResult getInstance() {
    return getInstance(_FLOW_RESULT_SUCESS_,null);
  }
  /**
   *
   * @param code int
   * @param message String
   * @return FlowResult
   */
  public static FlowResult getInstance(int code,String message) {
    FlowResult flowResult = new FlowResult();
    flowResult.code    = code;
    flowResult.message = message;
    return flowResult;
  }
  /**
   *
   */
  protected NodeTaskDataSet nodeTaskDataSet = null;
  /**
   *
   * @return NodeTaskDataSet
   */
  public NodeTaskDataSet getNodeTaskDataSet() {
    return nodeTaskDataSet;
  }
  /**
   *
   * @param ntd NodeTaskDataSet
   */
  public void setNodeTaskDataSet(NodeTaskDataSet ntd) {
    this.nodeTaskDataSet = ntd;
  }

  /**
   *
   */
  protected Object returnObject = null;
  /**
   *
   * @param returnObject Object
   * @return Object
   */
  public Object getReturnObject() {
    return this.returnObject;
  }
  /**
   *
   * @param returnObject Object
   */
  public void setReturnObject(Object returnObject) {
    this.returnObject = returnObject;
  }
  /**
   *
   */
  public FlowResult(){

  }
  // 流程执行成功
  /**
   *
   */
  public static final int _FLOW_RESULT_ERROR_      = -1;
  
  public static final int _FLOW_RESULT_SUCESS_      = 0x0000;
  // 当前节点没有任何出去的边
  /**
   *
   */
  public static final int _FLOW_RESULT_NO_OUT_EDGE_ = 0x0001;
  /**
   * 没有任何可提交(待处理)的任务
   */
  public static final int _FLOW_RESULT_NO_PENDING_TASK = 0x0002;
  /**
   * 不允许被回退，已有任务被提交
   */
  public static final int _FLOW_RESULT_DONT_ROLLBACK_TASK = 0x0012;
  /**
   * 不允许进行开始节点，也就是不允许进入流程
   */
  public static final int _FLOW_RESULT_NOT_INTO_START_NODE = 0x0003;
  /**
   * 不允许被取回
   */
  public static final int _FLOW_RESULT_NOT_RETAKE_TASK = 0x0004;
  public static final int _RESR_IN_FLOW_NODE_          = 0x0005;
  /**
   * 流程没有启动
   */
  public static final int _FLOW_RESULT_NOT_RUN_ = 0x8000;
  }
