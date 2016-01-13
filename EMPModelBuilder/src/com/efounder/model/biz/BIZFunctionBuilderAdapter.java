package com.efounder.model.biz;

import java.util.Map;
import java.util.Set;

public abstract class BIZFunctionBuilderAdapter
  implements BIZFunctionBuilder
{
  public BIZFunction createBIZEntity(String entityType, BIZContext bizContext, BIZFunction bizFunction, Map arrayMap, int entityIndex, Object userObject)
  {
    if (bizFunction == null) {
      bizFunction = createEntityInstance(entityType, bizContext, userObject);
    }
    if (bizFunction == null) {
      return null;
    }
    bizFunction.setEntityKey(entityType);
    bizFunction.setBIZContext(bizContext);
    Object[] keyArray = arrayMap.keySet().toArray();
    String[] valueArray = null;String value = null;
    for (int k = 0; k < keyArray.length; k++)
    {
      valueArray = (String[])arrayMap.get(keyArray[k]);
      value = entityIndex < valueArray.length ? valueArray[entityIndex] : null;
      bizFunction.setObject(keyArray[k], value);
    }
    return bizFunction;
  }
  
  protected final BIZFunction createEntityInstance(String entityType, BIZContext bizContext, Object userObject)
  {
    Class entityClass = getBIZEntityClazz(entityType, bizContext, userObject);
    BIZFunction bizEntity = null;
    try
    {
      bizEntity = (BIZFunction)entityClass.newInstance();
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
 * Qualified Name:     com.efounder.model.biz.BIZFunctionBuilderAdapter
 * JD-Core Version:    0.7.0.1
 */