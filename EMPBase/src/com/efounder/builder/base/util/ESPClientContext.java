package com.efounder.builder.base.util;

import com.efounder.eai.data.JParamObject;

public class ESPClientContext extends ESPContext {
  /**
   *
   */
  protected ESPClientContext() {
  }
  /**
   *
   * @return ESPClientContext
   */
  public static ESPClientContext getInstance() {
    ESPClientContext ctx = new ESPClientContext();
    return ctx;
  }
  /**
   *
   * @return ESPClientContext
   */
  public static ESPClientContext getInstance(JParamObject paramObject) {
    ESPClientContext ctx = new ESPClientContext();
    ctx.paramObject = paramObject;
    return ctx;
  }
  /**
   *
   * @return String
   */
  public String getDBUniqueID() {
    return "";
  }
  /**
   *
   */
  protected JParamObject paramObject = null;
  /**
   *
   * @return JParamObject
   */
  public JParamObject getParamObject() {
    return paramObject;
  }
  /**
   *
   * @param paramObject JParamObject
   */
  public void setParamObject(JParamObject paramObject) {
    this.paramObject = paramObject;
  }
}
