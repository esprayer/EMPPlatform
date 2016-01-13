package com.efounder.service.meta.mdl;

import com.efounder.service.meta.base.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BizModel extends MetaObject {
  /**
   *
   */
  protected BizModel() {
  }
  /**
   *
   * @return BizModel
   */
  public static BizModel getInstance() {
    return new BizModel();
  }
  /**
   * 模型的类型有：
   * 1.数据字典模型
   * 2.初始余额模型
   * 3.业务发生模型
   * 4.业务余额模型
   */
  // 未定义的模型
  public static final int UNKNOW_MODEL    = 0x0000;
  // 数据字典模型
  public static final int DATA_DICT_MODEL = 0x0001;
  // 初始余额模型
  public static final int INIT_DATA_MODEL = 0x0002;
  // 业务发生模型
  public static final int BILL_DATA_MODEL   = 0x0004; // 业务发生模型的标志
  public static final int BILL_DATA_MODEL_0 = 0x1004; // 不带有单据号的发生模型
  public static final int BILL_DATA_MODEL_1 = 0x0104; // 带有单据号的发生模型的第一级模型(凭证号)
  public static final int BILL_DATA_MODEL_2 = 0x0204; // 带有单据号的发生模型的第二级模型(凭证分录号)
  public static final int BILL_DATA_MODEL_3 = 0x0404; // 带有单据号的发生模型的第三级模型(辅助分录号)
  // 业务余额模型
  public static final int BALA_DATA_MODEL   = 0x0008; // 业务余额模型的标志
  public static final int BALA_DATA_MODEL_0 = 0x0108; // 简单的业务余额模型(不交叉)
  public static final int BALA_DATA_MODEL_1 = 0x0208; // 复杂的业务余额模型(交叉)
  /**
   * 返回业务模型类型
   * @return int
   */
  public int getBizModelType() {
    return 0;
  }












}
