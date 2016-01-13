package com.efounder.service.security;

import com.core.xml.StubObject;

public class OperateLoginfo extends StubObject {
  /**
   *
   */
  public OperateLoginfo() {
  }
  /**
   *
   * @param gnbh String
   * @param gnmc String
   * @return OperateLoginfo
   */
  public static OperateLoginfo getInstance(String gnbh,String gnmc) {
    OperateLoginfo ol = new OperateLoginfo();
    ol.setString("F_GNBH",gnbh);
    ol.setString("F_GNMC",gnmc);
    return ol;
  }
  /**
   *
   * @param key String
   * @param value String
   */
  public void addOperateData(String key,String value) {
    java.util.Map valueMap = (java.util.Map)this.getObject("F_SJ",null);
    if ( valueMap == null ) {
      valueMap = new java.util.HashMap();this.setObject("F_SJ",valueMap);
    }
    valueMap.put(key,value);
  }
  /**
   *
   * @param time long
   */
  public void setStartTime(long time) {
    this.setLong("F_STIME",time);
  }
  /**
   *
   * @param time long
   */
  public void setEndTime(long time) {
    this.setLong("F_ETIME",time);
  }
}
