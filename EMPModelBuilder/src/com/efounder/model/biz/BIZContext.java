package com.efounder.model.biz;

import com.efounder.eai.data.JParamObject;
import java.util.List;
import java.util.Map;

public abstract interface BIZContext
{
  public static final int CHANGE_EVENT_TYPE_NULL = 0;
  public static final int CHANGE_EVENT_TYPE_UNIT = 1;
  public static final int CHANGE_EVENT_TYPE_DATE = 2;
  public static final int CHANGE_EVENT_TYPE_OTHR = 4;
  public static final int CHANGE_EVENT_TYPE_DCT = 16;
  public static final String BIZTYPE_SYS = "0";
  public static final String BIZTYPE_DWZD = "1";
  public static final String BIZTYPE_LBZD = "2";
  public static final String DATETYPE_YEAR = "0";
  public static final String DATETYPE_QUARTER = "1";
  public static final String DATETYPE_MONTH = "2";
  public static final String DATETYPE_TENDAY = "3";
  public static final String DATETYPE_WEEK = "4";
  public static final String DATETYPE_DAY = "5";
  
  public abstract String getBIZUnit();
  
  public abstract String getBIZType();
  
  public abstract String getDateType();
  
  public abstract String getBIZSDate();
  
  public abstract String getBIZEDate();
  
  public abstract String getPrepareAttString();
  
  public abstract Object getBIZValue(Object paramObject1, Object paramObject2);
  
  public abstract void setBIZValue(Object paramObject1, Object paramObject2);
  
  public abstract void callBack(Object paramObject1, Object paramObject2);
  
  public abstract void enumBIZKey(List paramList);
  
  public abstract void initBIZContext(Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void changeEvent(int paramInt, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract JParamObject convertParamObject(Object paramObject, JParamObject paramJParamObject);
  
  public abstract void setCallBackValue(Object paramObject1, Object paramObject2);
  
  public abstract Map getCallBackMap();
  
  public abstract void addBIZContext(BIZContext paramBIZContext);
}


/* Location:           E:\WorkZone\EnterpriseServer\CodeSpace\JEnterprise\public\ModelBuilder.jar
 * Qualified Name:     com.efounder.model.biz.BIZContext
 * JD-Core Version:    0.7.0.1
 */