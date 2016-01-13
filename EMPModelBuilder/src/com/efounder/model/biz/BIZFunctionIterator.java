package com.efounder.model.biz;

import java.util.List;

public abstract interface BIZFunctionIterator
{
  public abstract BIZContext getBIZContext();
  
  public abstract List getCacheBIZFunctionList();
  
  public abstract List getBIZFunctionList(BIZFunctionBuilder paramBIZFunctionBuilder, String paramString, Object paramObject);
  
  public abstract boolean hasFunction(BIZFunctionBuilder paramBIZFunctionBuilder, String paramString, Object paramObject);
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZFunctionIterator
 * JD-Core Version:    0.7.0.1
 */