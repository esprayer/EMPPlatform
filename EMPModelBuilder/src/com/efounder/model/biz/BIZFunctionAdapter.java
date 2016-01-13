package com.efounder.model.biz;

public class BIZFunctionAdapter
  extends BIZEntityAdapter
  implements BIZFunction, EntityConstant
{
  public BIZFunctionAdapter() {}
  
  public BIZFunctionAdapter(String entityKey, String entityID, String entityBIZID, String entityName, String entityIcon, String entityLargeIcon, String entityFuncID, String entityLimit, BIZContext bizContext)
  {
    super(entityKey, entityID, entityBIZID, entityName, entityIcon, entityLargeIcon, entityFuncID, entityLimit, bizContext);
  }
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZFunctionAdapter
 * JD-Core Version:    0.7.0.1
 */