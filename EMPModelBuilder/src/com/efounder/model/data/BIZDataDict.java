package com.efounder.model.data;

import com.efounder.model.biz.BIZContext;
import com.efounder.model.biz.BIZEntityAdapter;

public class BIZDataDict
  extends BIZEntityAdapter
{
  public BIZDataDict() {}
  
  public BIZDataDict(String key, String entityID, String entityBIZID, String entityName, String entityIcon, String entityLargeIcon, String entityFuncID, String entityLimit, BIZContext bizContext)
  {
    super(key, entityID, entityBIZID, entityName, entityIcon, entityLargeIcon, entityFuncID, entityLimit, bizContext);
  }
  
  public String getEntityIcon()
  {
    String ii = super.getEntityIcon();
    if (ii == null) {
      ii = "office/(25,12).gif";
    }
    return ii;
  }
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.data.BIZDataDict
 * JD-Core Version:    0.7.0.1
 */