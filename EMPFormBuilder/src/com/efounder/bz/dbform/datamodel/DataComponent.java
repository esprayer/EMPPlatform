package com.efounder.bz.dbform.datamodel;

import java.util.List;
import java.util.Map;

import com.efounder.bz.dbform.event.FormFunctionObject;

public abstract interface DataComponent {
  public abstract DataComponent getParentDataComponent();
  
  public abstract void setParentDataComponent(DataComponent paramDataComponent);
  
  public abstract List getChildList();
  
  public abstract void setChildList(List paramList);
  
  public abstract DataComponent getDataComponent(String paramString);
  
  public abstract DataComponent getDataComponent(int paramInt);
  
  public abstract int insertDataComponent(DataComponent paramDataComponent);
  
  public abstract void removeDataComponent(DataComponent paramDataComponent);
  
  public abstract DataContainer getDataContainer();
  
  public abstract String getName();
  
  public abstract void setName(String paramString);
  
  public abstract String getID();
  
  public abstract void setID(String paramString);
  
  public abstract Map getPropertyMap();
  
  public abstract void setPropertyMap(Map paramMap);
  
  public abstract Object getProperty(Object paramObject1, Object paramObject2);
  
  public abstract void setProperty(Object paramObject1, Object paramObject2);
  
  public abstract Map getEventMap();
  
  public abstract void setEventMap(Map paramMap);
  
  public abstract boolean canAddChild(DataComponent paramDataComponent);
  
  public abstract void setDataContainer(DataContainer paramDataContainer);
  
  public abstract FormFunctionObject getCustomFunction();
  
  public abstract void setCustomFunction(FormFunctionObject paramFormFunctionObject);
  
  public abstract void sysInitDataComponent();
}
