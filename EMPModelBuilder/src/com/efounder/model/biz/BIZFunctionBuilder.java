package com.efounder.model.biz;

import java.util.Map;

public abstract interface BIZFunctionBuilder
{
  public abstract String getBIZEntityKey(BIZContext paramBIZContext);
  
  public abstract BIZFunction createBIZEntity(String paramString, BIZContext paramBIZContext, BIZFunction paramBIZFunction, Map paramMap, int paramInt, Object paramObject);
  
  public abstract Class getBIZEntityClazz(String paramString, BIZContext paramBIZContext, Object paramObject);
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZFunctionBuilder
 * JD-Core Version:    0.7.0.1
 */