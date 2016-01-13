package com.metadata.bizmodel;

public interface SYS_MDL_CTN {
  // 每种类型的业务内容最大的事实表的数据，默认为16个，应该够用了
  public static final int _BIZ_CTN_TYPE_FCT_COUNT_ = 16;
  // 发生数类型的事实表，用于记录明细的发生数
  // 表单信息表
  public static final String _BIZ_CTN_TYPE_JBDS_  = "01"; // BILL_DATA_SET 表单数据集
  // 分录信息表
  public static final String _BIZ_CTN_TYPE_JIDS_  = "02"; // ITEM_DATA_SET 分录数据集
  // 明细数据集
//  public static final String _BIZ_CTN_TYPE_JPDS_  = "03"; // PART_DATA_SET 明细数据集
  // 核销数据集(此处可能需要调整)
//  public static final String _BIZ_CTN_TYPE_JVDS_  = "JVDS"; // Verifying_DATA_SET 核销类明细分录表
  // 用于记录根据发生数汇总产生的余额表
//  public static final String _BIZ_CTN_TYPE_BLDS_  = "04"; // Balance_DATA_SET 余额数据
  // 预算控制类的余额表
//  public static final String _BIZ_CTN_TYPE_BCDS_  = "BCDS"; // Control_DATA_SET 版本控制类余额表
  // 核销类的余额表
//  public static final String _BIZ_CTN_TYPE_BVDS_  = "BVDS"; // Verifying_DATA_SET核销类余额表

  /**
   *
   */
  public static final String CTN_ID  = "CTN_ID";
  /**
   *
   */
  public static final String CTN_FCT = "CTN_FCT";
  /**
   *
   */
  public static final String CTN_TYPE = "CTN_TYPE";
  /**
   *
   */
  public static final String PCTN_ID  = "PCTN_ID";
  /**
   *
   */
  public static final String CTN_NAME = "CTN_NAME";

}
