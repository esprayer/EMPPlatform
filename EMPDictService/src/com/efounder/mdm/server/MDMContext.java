package com.efounder.mdm.server;

import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.sql.JConnection;
import com.efounder.eai.data.JParamObject;
import com.efounder.builder.meta.dctmodel.DCTMetaData;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MDMContext extends ESPServerContext {
  /**
   *
   */
  protected MDMContext() {
  }
  /**
   *
   */
  protected BIZMetaData bizMetaData = null;
  /**
   *
   * @return BIZMetaData
   */
  public BIZMetaData getBIZMetaData() {
    return bizMetaData;
  }
  /**
   *
   * @param bizMetaData BIZMetaData
   */
  public void setBIZMetaData(BIZMetaData bizMetaData) {
    this.bizMetaData = bizMetaData;
  }
  /**
   *
   */
  protected DCTMetaData dctMetaData = null;
  /**
   *
   * @return BIZMetaData
   */
  public DCTMetaData getDCTMetaData() {
    return dctMetaData;
  }
  /**
   *
   * @param bizMetaData BIZMetaData
   */
  public void setDCTMetaData(DCTMetaData dctMetaData) {
    this.dctMetaData = dctMetaData;
  }
  /**
   *
   * @return MDMContext
   */
  public static MDMContext getInstance(JParamObject paramObject,
                                        JConnection connection) {
    MDMContext mdmContext = new MDMContext();
    mdmContext.setParamObject(paramObject);
    mdmContext.setConnection(connection);
    return mdmContext;
  }
}
