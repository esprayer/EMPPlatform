package com.efounder.builder.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.util.DateUtil;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;

public class EMPReflectUtil {

	public static Object rowtsetReflectClass(String className, EFRowSet rowset) throws Exception {
		Class               reflectClass = getReflectClass(className);
		Field[]                   fields = reflectClass.getDeclaredFields();
		Field                      field;
		Map<String, Field>      fieldMap = new HashMap<String, Field> ();
		Map<String, Object>      dataMap = rowset.getDataMap();
		String                       key = "";
		Object                     value = null;
		Object                  classObj  = reflectClass.newInstance();            //创建类的对象

		for(int i = 0; i < fields.length; i++) {
			field = fields[i];
			fieldMap.put(field.getName(), field);
		}
		
		Iterator it = dataMap.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry m = (Entry) it.next();
			key = m.getKey().toString();
			value = m.getValue();
			if(fieldMap.get(key) == null) continue;
			setDeclareFieldValue(classObj, fieldMap.get(key), value);
		}
		return classObj;
	}
	
	public static List datasetReflectClass(String className, EFDataSet dataset) throws Exception{
		Object                  classObj = null;
		EFRowSet                  rowset = null;
		List                        list = new ArrayList();

		for(int i = 0; i < dataset.getRowCount(); i++) {
			rowset = dataset.getRowSet(i);
			classObj = rowtsetReflectClass(className, rowset);
			list.add(classObj);
		}
		return list;
	}
	
	public static Class getReflectClass(String className) throws ClassNotFoundException {
		Class reflectClass = Class.forName(className);
		return reflectClass;
	}
	
	public static Field[] getDeClareFields(Class reflectClass) {
		return reflectClass.getDeclaredFields(); 
	}
	
	public static void setDeclareFieldValue(Object classObj, Field field, Object objVal) throws IllegalArgumentException, IllegalAccessException {
		Class fieldType = field.getType();
		field.setAccessible(true);                    //设置私有可以访问.
		BigDecimal b = null;
		
		//目前先处理这几个，以后需要再继续加
		if(fieldType.equals(String.class)) {
			field.set(classObj, objVal.toString());
		} else if(fieldType.equals(int.class)) {
			b = new BigDecimal(objVal.toString());
			field.set(classObj, b.intValue());
		} else if(fieldType.equals(float.class)) {
			b = new BigDecimal(objVal.toString());
			field.set(classObj, b.floatValue());
		} else if(fieldType.equals(double.class)) {
			b = new BigDecimal(objVal.toString());
			field.set(classObj, b.doubleValue());
		} else if(fieldType.equals(long.class)) {
			b = new BigDecimal(objVal.toString());
			field.set(classObj, b.longValue());
		}
	}
	
	public static void setDeclareFieldValueToRowSet(EFRowSet rowset, Field field, Object objVal) throws IllegalArgumentException, IllegalAccessException {
		Class fieldType = field.getType();
		
		//目前先处理这几个，以后需要再继续加
		if(fieldType.equals(String.class)) {
			if(objVal == null) rowset.putString(field.getName(), "");
			else rowset.putString(field.getName(), objVal.toString());
		} else if(fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else {
				try {
					rowset.putNumber(field.getName(), Integer.parseInt(objVal.toString())); 
				} catch(Exception ce) {
					
				}
			}
		} else if(fieldType.equals(float.class) || fieldType.equals(Float.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else rowset.putNumber(field.getName(), Float.parseFloat(objVal.toString())); 			
		} else if(fieldType.equals(double.class) || fieldType.equals(Double.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else rowset.putNumber(field.getName(), Double.parseDouble(objVal.toString())); 		
		} else if(fieldType.equals(long.class) || fieldType.equals(Long.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else {
				try {
					rowset.putNumber(field.getName(), Long.parseLong(objVal.toString())); 
				} catch(Exception ce) {
					rowset.putNumber(field.getName(), 0); 
				}	
			}
		} else if(fieldType.equals(Date.class)) {
			if(objVal == null) rowset.putObject(field.getName(), new Date());
			else {
				if(objVal instanceof String) {
					rowset.putString(field.getName(), objVal.toString());
				} else {
					rowset.putObject(field.getName(), objVal); 	
				}		
			}
		}
	}
	
	public static void setDeclareFieldValueToRowSet(EFRowSet rowset, EFRowSet metaRowset, Object objVal) throws IllegalArgumentException, IllegalAccessException {
		//目前先处理这几个，以后需要再继续加
		//字符串
		if(metaRowset.getString("COL_TYPE", "").equals("C")) {
			if(objVal == null) rowset.putString(metaRowset.getString("COL_ID", ""), "");
			else rowset.putString(metaRowset.getString("COL_ID", ""), objVal.toString());
		} else if(metaRowset.getString("COL_TYPE", "").equals("I")) {
			if(objVal == null) rowset.putNumber(metaRowset.getString("COL_ID", ""), 0);
			else {
				try {
					rowset.putNumber(metaRowset.getString("COL_ID", ""), Integer.parseInt(objVal.toString())); 
				} catch(Exception ce) {
					
				}
			}
		} else if(metaRowset.getString("COL_TYPE", "").equals("N")) {
			BigDecimal bigDecimal = new BigDecimal(objVal.toString());
			if(objVal == null) rowset.putNumber(metaRowset.getString("COL_ID", ""), 0);
			else rowset.putNumber(metaRowset.getString("COL_ID", ""), bigDecimal); 			
		}
		//日期
		else if(metaRowset.getString("COL_TYPE", "").equals("D")) {
			if(objVal == null) rowset.putObject(metaRowset.getString("COL_ID", ""), new Date());
			else {
				if(objVal instanceof String) {
					rowset.putString(metaRowset.getString("COL_ID", ""), objVal.toString());
				} else {
					rowset.putObject(metaRowset.getString("COL_ID", ""), objVal); 	
				}		
			}
		}
	}
}
