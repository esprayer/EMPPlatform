package com.efounder.mdm.server.provider.plugin;

import java.util.Date;

import com.efounder.mdm.EFMDMDataModel;
import com.efounder.mdm.server.MDMContext;
import com.efounder.mdm.server.provider.util.DICTProviderUtils;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.bz.service.ServicePluginAdapter;

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
public class DICTDataLoader extends ServicePluginAdapter{
  /**
   *
   */
  public DICTDataLoader() {
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
    // 获取字典ID
    String DCT_ID = mdmContext.getParamObject().GetValueByParamName("DCT_ID",null);
    if ( DCT_ID == null ) return null;
    mdmDataModel.setDCT_ID(DCT_ID);
    // 获取元数据
    DCTMetaData dctMetaData = mdmContext.getDCTMetaData();
    if ( dctMetaData == null ) return null;
    mdmDataModel.putString("DCT_BMCOLID",dctMetaData.getDCT_BMCOLID());
    // 是否要装入
    String loader = mdmContext.getParamObject().GetValueByParamName(DCT_ID,"1");
    if ( "0".equals(loader) ) return null;
    // 获取数据字典的值
    EFDataSet dataSet = DICTProviderUtils.getDICTData(dctMetaData,mdmContext);
    // 设置到返回值中
    if ( dataSet != null ) mdmDataModel.setDataSet(DCT_ID,dataSet);
    Date date = new Date();
//    System.out.print("数据字典开始传输FLEX："+date.getTime());
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
