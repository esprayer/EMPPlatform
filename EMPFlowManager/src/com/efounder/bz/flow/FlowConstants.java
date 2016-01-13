package com.efounder.bz.flow;

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
public interface FlowConstants {
  // 表名
  public static final String _FLOW_TASK_LIST_     = "FLOW_TASK_LIST"; // 流程任务列表
  public static final String _FLOW_TASK_END_LIST_    = "FLOW_TASK_END_LIST";// 流程结束任务列表
  public static final String _FLOW_TASK_ADD_DATA_ = "FLOW_TASK_ADD_DATA";// 流程资源的附加数据
  // 列名
  public static final String _OP_ID_COL_          = "OP_ID";          // 操作ID
  public static final String _FLOW_ID_COL_        = "FLOW_ID";        // 流程ID
  public static final String _NODE_TAG_COL_       = "NODE_TAG";       // 目标节点
  public static final String _EDGE_ID_COL_        = "EDGE_ID";        // 连接边的ID
  public static final String _OBJ_GUID_COL_       = "OBJ_GUID";       // 对象唯一标识
  public static final String _OP_GUID_COL_        = "OP_GUID";
  public static final String _TASK_NAME_COL_      = "TASK_NAME";      // 任务名称
  public static final String _TASK_UNIT_COL_      = "TASK_UNIT";      // 任务创建单位
  public static final String _TASK_TO_UNIT_COL_   = "TASK_TO_UNIT";   // 需要处理的单位
  public static final String _NODE_SRC_COL_       = "NODE_SRC";       // 来源节点
  public static final String _NODE_SRC_NAME_COL_  = "NODE_SRC_NAME";  // 来源节点名称
  public static final String _NODE_TAG_NAME_COL_  = "NODE_TAG_NAME";  // 目标节点名称

  public static final String _BIZ_DJBH_COL_       = "BIZ_DJBH";       // 业务单据号
  public static final String _BIZ_UNIT_COL_       = "BIZ_UNIT";       // 业务单位
  public static final String _BIZ_DATE_COL_       = "BIZ_DATE";       // 业务时间
  public static final String _OP_MODE_COL_        = "OP_MODE";        // 操作模式（自动，手动）
  public static final String _RESR_STATUS_COL_    = "RESR_STATUS";    // 资源状态
  public static final String _RESR_IN_CAUSE_COL_  = "RESR_IN_CAUSE";  // 资源进入状态的方式
  public static final String _RESR_OUT_CAUSE_COL_ = "RESR_OUT_CAUSE"; // 资源进入完成状态的方式
  public static final String _OP_ORDER_COL_       = "OP_ORDER";       // 操作顺序
  public static final String _OP_NOTE_COL_        = "OP_NOTE";        // 操作说明
  public static final String _OP_PROC_NOTE_COL_   = "OP_PROC_NOTE";   // 操作说明
  public static final String _OP_TIME_COL_        = "OP_TIME";        // 操作日期
  public static final String _OP_PROC_TIME_COL_   = "OP_PROC_TIME";   // 处理日期
  public static final String _AUTO_TIME_COL_      = "AUTO_TIME";      // 自动完成的时间
  public static final String  _OP_USER_COL_       = "OP_USER";        // 原始提交用户
  public static final String  _OP_USER_NAME_COL_  = "OP_USER_NAME";   // 原始提交用户名称
  public static final String _OP_SUBMIT_USER_COL_ = "OP_SUBMIT_USER"; // 操作用户
  public static final String _OP_SUBMIT_NAME_COL_ = "OP_SUBMIT_NAME"; // 操作用户
  public static final String _OP_PROC_USER_COL_   = "OP_PROC_USER";   // 处理用户
  public static final String _OP_PROC_NAME_COL_   = "OP_PROC_NAME";   // 处理用户
  public static final String _OP_EDGES_COL_       = "OP_EDGES";       // 所有处理过的边
  public static final String _OP_GNBH_COL_        = "OP_GNBH";        // 功能编号
  public static final String _OP_RULE_COL_        = "OP_RULE";        // 用户角色
  public static final String _OP_LEVEL_COL_       = "OP_LEVEL";       // 操作级别
  public static final String _TASK_STATUS_COL_    = "TASK_STATUS";    // 任务状态 00 or null运行，01取消 02结束
  public static final String _POP_ID_COL_         = "POP_ID";         // 父操作ID
  public static final String _TOFLOW_ID_COL_      = "TOFLOW_ID";      // 子流程
  public static final String _PFLOW_ID_COL_       = "PFLOW_ID";       // 父流程
  public static final String _PFLOW_NODE_ID_COL_  = "PFLOW_NODE_ID";       // 父流程
  public static final String _TASK_ADD_DATA_COL_  = "TASK_ADD_DATA";  // 流程附加数据
  public static final String _LOOP_ID_  = "LOOP_ID";  //
  public static final String _BIZ_SUBMIT_UNIT_  = "BIZ_SUBMIT_UNIT";  //一个节点上最后的提交单位
  public static final String _BIZ_SUBMIT_USER_  = "BIZ_SUBMIT_USER";  //一个节点上最后的提交人

  public static final String _MDL_ID_             = "MDL_ID";         // 模型ID
  public static final String _ROLL_RETAKE_FLAG_   = "ROLL_RETAKE_FLAG";// 回退，取回标志

  public static final String _OP_LEVEL_01_        = "01";
  public static final String _OP_LEVEL_02_        = "02";
  public static final String _OP_LEVEL_03_        = "03";
  public static final String _OP_LEVEL_04_        = "04";








  ///////////////////////////////////////////////////////////////////////
  // 资源在流程节点上的状态
  ///////////////////////////////////////////////////////////////////////
  /**
   * 资源在流程节点上处于待处理状态
   */
  public static final String _RESR_NODE_STATUS_PENDING_    = "pending";
  /**
   * 资源在流程节点上处于已处理状态
   */
  public static final String _RESR_NODE_STATUS_PROCESSED_  = "processed";
  /**
   * 资源在流程节点上处理正在处理状态
   */
  public static final String _RESR_NODE_STATUS_PROCESSING_ = "processing";
  /**
   * 资源在流程节点上处于等待处理完状态
   */
  public static final String _RESR_NODE_STATUS_WAITING_    = "waiting";
  /**
   * 资源在流程节点上处于结束状态
   */
  public static final String _RESR_NODE_STATUS_END_        = "end";
  /**
   * 资源在流程节点上处于被取消状态
   */
  public static final String _RESR_NODE_STATUS_CANCEL_     = "cancel";

  // 任务状态的设置
  public static final String _TASK_STATUS_PROCESSING_      = _RESR_NODE_STATUS_PROCESSING_;
  public static final String _TASK_STATUS_END_             = _RESR_NODE_STATUS_END_;
  public static final String _TASK_STATUS_CANCEL_          = _RESR_NODE_STATUS_CANCEL_;








  ///////////////////////////////////////////////////////////////////////
  // 资源在流程节点上的原因
  ///////////////////////////////////////////////////////////////////////
  /**
   * 新建的资源
   */
  public static final String _RESR_NODE_CAUSE_CREATE_      = "create";
  /**
   * 提交的资源
   */
  public static final String _RESR_NODE_CAUSE_SUBMIT_      = "submit";
  /**
   * 回退的资源
   */
  public static final String _RESR_NODE_CAUSE_ROLLBACK_    = "rollback";
  /**
   * 取回的资源
   */
  public static final String _RESR_NODE_CAUSE_RETAKE_      = "retake";
  /**
   *
   */
  public static final String _RESR_NODE_CAUSE_RESET_      = "reset";

  public static final String _RESR_NODE_CAUSE_VIEW_        = "viewTask";
  public static final String _RESR_NODE_PRIVTASK_        = "privTask";
  public static final String _RESR_NODE_NEXTTASK_        = "nextTask";







  ///////////////////////////////////////////////////////////////////////
  // 资源在流程节点上的操作方式
  ///////////////////////////////////////////////////////////////////////
  /**
   * 自动来的资源
   */
  public static final String _RESR_NODE_OPMODE_AUTO_       = "auto";
  /**
   * 手动来的资源
   */
  public static final String _RESR_NODE_OPMODE_MANUAL_     = "manual";


  ///////////////////////////////////////////////////////////////////////
  // 流程上的节点类型
  ///////////////////////////////////////////////////////////////////////
  public static final String _FLOW_NODE_TYPE_NORMAL_       = "normal"; // 普通节点
  public static final String _FLOW_NODE_TYPE_FORK_         = "fork";   // 分支节点
  public static final String _FLOW_NODE_TYPE_JOIN_         = "join";   // 聚合节点
  public static final String _FLOW_NODE_TYPE_JOIN_FORK_    = "join_fork";// 妈是分支又是聚合

  // 流程类型
  public static final int _ROWSETAPPTYPE_FLOW_TYPE_ = 0x0001;
  // 节点类型
  public static final int _ROWSETAPPTYPE_NODE_TYPE_ = 0x0002;
  // 任务类型
  public static final int _ROWSETAPPTYPE_TASK_TYPE_ = 0x0003;
  public static final String CHILD_FLOW_DISP_TYPE="_CHILD_FLOW_DISP_TYPE_";
  public static final int _DISP_RECURSION_=2;
  public static final int _DISP_LIST_=1;

  //任务中断的类型
  public static final String _BREAK_TASK_TYPE_="BREAK_TASK_TYPE";
  public static final int _RESET_=0;//重置
  public static final int _CANCEL_=1;//取消
  public static final int _END_=2;//正常中断结束
  //获取任务调用类型
  public static final int _COMMON_LOAD=1;
  public static final int _SERVICE_KEY_LOAD=2;
  public static final int _SERVER_MTHOD_LOAD=3;
  
  public static final String _RESET_START="RESET_START";
  //是不是重置后的重新发起,这时需要更改提交人为原始的制单人
}
