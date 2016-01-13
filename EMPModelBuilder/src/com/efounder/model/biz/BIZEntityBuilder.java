package com.efounder.model.biz;

import java.util.Map;

public abstract interface BIZEntityBuilder
{
  public abstract String getBIZEntityKey(BIZContext paramBIZContext);
  
  public abstract BIZEntity createBIZEntity(String paramString, BIZContext paramBIZContext, BIZEntity paramBIZEntity, Map paramMap, int paramInt, Object paramObject);
  
  public abstract Class getBIZEntityClazz(String paramString, BIZContext paramBIZContext, Object paramObject);
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZEntityBuilder
 * JD-Core Version:    0.7.0.1
 */