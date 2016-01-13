package jreportfunction.pub;

import java.util.ResourceBundle;

/**
 * <p>Title: 查询定义</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */
public class JHslxStub {
static ResourceBundle res = ResourceBundle.getBundle("jreportfunction.pub.Language");
  public JHslxStub() {
    super();
  }
  public String HSLX  = "";  //核算类型 BMHS、CPHS、CBZX、XJLL
  public String LXMC  = "";  //辅助核算名
  public String ZDTBL = "";  //字典表
  public String ZDLB  = "";  //类别字段
  public String HSLB  = "";  //专门记录专项核算和要素台账
  public String ZDBH  = "";  //字典编号字段
  public String ZDMC  = "";  //字典名称字段
  public String STRU  = "";  //编号结构
  public String QXBH  = "";  //权限编号
  public String YETBL = "";  //金额余额表
  public String SLTBL = "";  //数量余额表
  public String WBTBL = "";  //外币余额表
  public String CTBH  = "";  //穿透编号
  
  public String FZYETBL = "";  //辅助金额余额表
  public String FZSLTBL = "";  //辅助数量余额表
  public String FZWBTBL = "";  //辅助外币余额表

  
  public String YECOL = "";  //余额表中编号列
  public String PZCOL = "";  //凭证库中编号列
  public String GROUP = "";  //分组表
  public String GROUPGL = "";  //分组关联表
  public String GPCOL = "";  //分组列 KMGP,BMGP
}
