package com.efounder.model.biz;

import java.util.Map;
import java.util.Set;

public abstract class BIZEntityBuilderAdapter
  implements BIZEntityBuilder
{
  public BIZEntity createBIZEntity(String entityType, BIZContext bizContext, BIZEntity bizEntity, Map arrayMap, int entityIndex, Object userObject)
  {
    if (bizEntity == null) {
      bizEntity = createEntityInstance(entityType, bizContext, userObject);
    }
    if (bizEntity == null) {
      return null;
    }
    bizEntity.setEntityKey(entityType);
    bizEntity.setBIZContext(bizContext);
    Object[] keyArray = arrayMap.keySet().toArray();
    String[] valueArray = null;String value = null;
    for (int k = 0; k < keyArray.length; k++)
    {
      valueArray = (String[])arrayMap.get(keyArray[k]);
      value = entityIndex < valueArray.length ? valueArray[entityIndex] : null;
      bizEntity.setObject(keyArray[k], value);
    }
    return bizEntity;
  }
  
  protected final BIZEntity createEntityInstance(String entityType, BIZContext bizContext, Object userObject)
  {
    Class entityClass = getBIZEntityClazz(entityType, bizContext, userObject);
    BIZEntity bizEntity = null;
    try
    {
      bizEntity = (BIZEntity)entityClass.newInstance();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return bizEntity;
  }
  
  public abstract Class getBIZEntityClazz(String paramString, BIZContext paramBIZContext, Object paramObject);
  
  public abstract String getBIZEntityKey(BIZContext paramBIZContext);
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZEntityBuilderAdapter
 * JD-Core Version:    0.7.0.1
 */