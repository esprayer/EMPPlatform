package com.efounder.mdm.server.provider.plugin;

import com.efounder.bz.service.*;
import com.efounder.mdm.EFMDMDataModel;
import com.efounder.mdm.server.MDMContext;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.mdm.server.provider.util.DICTProviderUtils;

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
public class FKEYDataLoader extends ServicePluginAdapter {
  /**
   *
   */
  public FKEYDataLoader() {
  }
  /**
   *
   * @param mdmContext MDMContext
   * @param mdmDataModel EFMDMDataModel
   * @return Object
   * @throws Exception
   */
  public Object prepareMDMLoader(MDMContext mdmContext,EFMDMDataModel mdmDataModel) throws Exception {
    return null;
  }
  /**
   *
   * @param mdmContext MDMContext
   * @param mdmDataModel EFMDMDataModel
   * @return Object
   * @throws Exception
   */
  public Object processMDMLoader(MDMContext mdmContext,EFMDMDataModel mdmDataModel) throws Exception {
    String DCT_ID = mdmContext.getParamObject().GetValueByParamName("DCT_ID",null);
    DCTMetaData dctMetaData = mdmContext.getDCTMetaData();
    // 获取字典ID
    String FKEY_ID = mdmContext.getParamObject().GetValueByParamName("FKEY_ID",null);
    if ( FKEY_ID == null ) return null;
    // 是否要装入
    String loader = mdmContext.getParamObject().GetValueByParamName(FKEY_ID,"1");
    if ( "0".equals(loader) ) return null;
    mdmDataModel.setFKEY_ID(FKEY_ID);
    // 获取元数据
    DCTMetaData fkeyMetaData = dctMetaData.getFKeyDCTMetaData(FKEY_ID);
    if ( fkeyMetaData == null ) return null;
    // 获取数据字典的值
    EFDataSet dataSet = DICTProviderUtils.getDICTData(fkeyMetaData,mdmContext);
    // 设置到返回值中
    if ( dataSet != null ) {
      mdmDataModel.setDataSet(FKEY_ID, dataSet);
      String FKEY_COL_ID = dctMetaData.getDoMetaData().getColIDByFKEYObj(FKEY_ID);
      mdmDataModel.putString("FKEY_COL_ID",FKEY_COL_ID);
    }
    return null;
  }
  /**
   *
   * @param mdmContext MDMContext
   * @param mdmDataModel EFMDMDataModel
   * @return Object
   * @throws Exception
   */
  public Object finishMDMLoader(MDMContext mdmContext,EFMDMDataModel mdmDataModel) throws Exception {
    return null;
  }
}
