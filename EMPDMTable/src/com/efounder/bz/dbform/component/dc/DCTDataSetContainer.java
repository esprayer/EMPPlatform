package com.efounder.bz.dbform.component.dc;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.ESPRowSet;
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
public interface DCTDataSetContainer {
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet();
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getSelectRowSet();
  /**
   *
   * @return DCTMetaData
   */
  public DCTMetaData getDCTMetaData();
  /**
   *
   * @param dctMetaData DCTMetaData
   */
  public void setDCTMetaData(DCTMetaData dctMetaData);
}
