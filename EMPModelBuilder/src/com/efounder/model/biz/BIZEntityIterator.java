package com.efounder.model.biz;

import java.util.List;

public abstract interface BIZEntityIterator
{
  public abstract BIZContext getBIZContext();
  
  public abstract List getCacheBIZEntityList();
  
  public abstract List getBIZEntityList(BIZEntityBuilder paramBIZEntityBuilder, String paramString, Object paramObject);
  
  public abstract boolean hasEntity(BIZEntityBuilder paramBIZEntityBuilder, String paramString, Object paramObject);
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZEntityIterator
 * JD-Core Version:    0.7.0.1
 */