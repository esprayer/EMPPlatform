package com.efounder.mdm.server.provider.component;

import com.efounder.builder.base.util.*;
import com.efounder.bz.service.ServiceComponentAdapter;
import com.efounder.mdm.EFMDMDataModel;
import com.efounder.mdm.server.MDMContext;

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
public class MDMServiceComponent extends ServiceComponentAdapter {
  public MDMServiceComponent() {
  }
  /**
   *
   * @param espContext ESPServerContext
   * @param responseObject Object
   * @return String
   * @todo Implement this com.efounder.bz.service.ServiceComponentAdapter method
   */
  protected String getPreparePluginServiceName(ESPServerContext espContext,
                                               Object responseObject) {
    return "prepareMDMLoader";
  }
  /**
   *
   * @param mdmContext MDMContext
   * @param mdmDataModel EFMDMDataModel
   * @return Object
   * @throws Exception
   */
  public Object prepareMDMLoader(MDMContext mdmContext,EFMDMDataModel mdmDataModel) throws Exception {
    Object[] OArray = {mdmContext,mdmDataModel};
    return this.executePluginService("prepareMDMLoader",OArray);
  }
  /**
   *
   * @param espContext ESPServerContext
   * @param responseObject Object
   * @return String
   * @todo Implement this com.efounder.bz.service.ServiceComponentAdapter method
   */
  protected String getRunPluginServiceName(ESPServerContext espContext,
                                           Object responseObject) {
    return "processMDMLoader";
  }
  /**
   *
   * @param mdmContext MDMContext
   * @param mdmDataModel EFMDMDataModel
   * @return Object
   * @throws Exception
   */
  public Object processMDMLoader(MDMContext mdmContext,EFMDMDataModel mdmDataModel) throws Exception {
    Object[] OArray = {mdmContext,mdmDataModel};
    return this.executePluginService("processMDMLoader",OArray);
  }
  /**
   *
   * @param espContext ESPServerContext
   * @param responseObject Object
   * @return String
   * @todo Implement this com.efounder.bz.service.ServiceComponentAdapter method
   */
  protected String getFinishPluginServiceName(ESPServerContext espContext,
                                              Object responseObject) {
    return "finishMDMLoader";
  }
  /**
   *
   * @param mdmContext MDMContext
   * @param mdmDataModel EFMDMDataModel
   * @return Object
   * @throws Exception
   */
  public Object finishMDMLoader(MDMContext mdmContext,EFMDMDataModel mdmDataModel) throws Exception {
    Object[] OArray = {mdmContext,mdmDataModel};
    return this.executePluginService("finishMDMLoader",OArray);
  }
}
