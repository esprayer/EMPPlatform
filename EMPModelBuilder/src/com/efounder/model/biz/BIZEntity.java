package com.efounder.model.biz;

import com.efounder.eai.data.JParamObject;

public abstract interface BIZEntity
  extends BIZContext
{
  public static final int ENTITY_FUNC_NOTHING = 0;
  public static final int ENTITY_FUNC_CALC = 1;
  public static final int ENTITY_FUNC_COUNT = 2;
  public static final int ENTITY_FUNC_CHECK = 4;
  public static final int ENTITY_FUNC_EXP = 8;
  public static final int ENTITY_FUNC_IMP = 16;
  public static final int ENTITY_FUNC_PRINT = 32;
  
  public abstract BIZEntityAppView getBIZEntityAppView();
  
  public abstract void setBIZEntityAppView(BIZEntityAppView paramBIZEntityAppView);
  
  public abstract void setBIZContext(BIZContext paramBIZContext);
  
  public abstract BIZContext getBIZContext();
  
  public abstract int getFuncType();
  
  public abstract void setEntityKey(String paramString);
  
  public abstract String getEntityKey();
  
  public abstract String getEntityID();
  
  public abstract String getEntityBIZID();
  
  public abstract String getEntityName();
  
  public abstract String getEntityIcon();
  
  public abstract String getEntityLargeIcon();
  
  public abstract String getEntityFuncID();
  
  public abstract String getEntityLimit();
  
  public abstract String getEntityUnit();
  
  public abstract String getEntityType();
  
  public abstract String getDateType();
  
  public abstract String getEntitySDate();
  
  public abstract String getEntityEDate();
  
  public abstract String getBIZContextString();
  
  public abstract String getPrepareAttString();
  
  public abstract Object getPrepareObject();
  
  public abstract Object getEntityObject(String paramString, Object paramObject);
  
  public abstract void setEntityObject(String paramString, Object paramObject);
  
  public abstract Object getObject(Object paramObject1, Object paramObject2);
  
  public abstract void setObject(Object paramObject1, Object paramObject2);
  
  public abstract Object getEntityValue(Object paramObject1, Object paramObject2);
  
  public abstract void setEntityValue(Object paramObject1, Object paramObject2);
  
  public abstract JParamObject getParamobject();
  
  public abstract void setParamObject(JParamObject paramJParamObject);
  
  public abstract Object getLostValue(Object paramObject1, Object paramObject2);
  
  public abstract void setLostValue(Object paramObject1, Object paramObject2);
  
  public abstract void setBIZEntityContext(BIZEntityContext paramBIZEntityContext);
  
  public abstract BIZEntityContext getBIZEntityContext();
  
  public abstract Object cloneObject(BIZEntityAdapter paramBIZEntityAdapter)
    throws Exception;
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZEntity
 * JD-Core Version:    0.7.0.1
 */