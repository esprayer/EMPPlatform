package com.efounder.mdm;

import com.efounder.builder.base.data.*;
import com.efounder.object.*;

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
public class EFMDMDataModel extends EFRowSet implements java.io.Serializable,ChildLinkParentKey {
  private String DCT_ID;
  private String RLGL_ID;
  private String FKEY_ID;
  private String MDL_ID;
  /**
   *
   */
  public EFMDMDataModel() {
  }

  /**
   *
   * @return EFMDMDataModel
   */
  public static EFMDMDataModel getInstance() {
    EFMDMDataModel mdmDataModel = new EFMDMDataModel();
    return mdmDataModel;
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDCTDataSet() {
    return this.getDataSet(DCT_ID);
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getRLGLDCTDataSet() {
    return this.getDataSet("RLGL_"+RLGL_ID);
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getFKEYDCTDataSet() {
    return this.getDataSet(FKEY_ID);
  }

  public void setDCT_ID(String DCT_ID) {
    this.DCT_ID = DCT_ID;
  }

  public String getDCT_ID() {
    return DCT_ID;
  }

  public void setRLGL_ID(String RLGL_ID) {
    this.RLGL_ID = RLGL_ID;
  }

  public String getRLGL_ID() {
    return RLGL_ID;
  }

  public void setFKEY_ID(String FKEY_ID) {
    this.FKEY_ID = FKEY_ID;
  }

  public String getFKEY_ID() {
    return FKEY_ID;
  }

  public void setMDL_ID(String MDL_ID) {
    this.MDL_ID = MDL_ID;
  }

  public String getMDL_ID() {
    return MDL_ID;
  }

  public Object getMasterKey() {
	// TODO Auto-generated method stub
	return null;
  }
}
