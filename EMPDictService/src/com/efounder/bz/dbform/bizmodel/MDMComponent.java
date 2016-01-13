package com.efounder.bz.dbform.bizmodel;

import com.efounder.builder.meta.bizmodel.BIZMetaData;
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
public interface MDMComponent {
  public String getDCT_ID();
  public String getMDL_ID();
  public BIZMetaData getBIZMetaData();
  public DCTMetaData getDCTMetaData();
}
