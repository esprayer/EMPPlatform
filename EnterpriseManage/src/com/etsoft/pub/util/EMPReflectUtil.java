package com.etsoft.pub.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;

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
		} else if(fieldType.equals(int.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else rowset.putNumber(field.getName(), Integer.parseInt(objVal.toString())); 
		} else if(fieldType.equals(float.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else rowset.putNumber(field.getName(), Float.parseFloat(objVal.toString())); 			
		} else if(fieldType.equals(double.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else rowset.putNumber(field.getName(), Double.parseDouble(objVal.toString())); 		
		} else if(fieldType.equals(long.class)) {
			if(objVal == null) rowset.putNumber(field.getName(), 0);
			else rowset.putNumber(field.getName(), Long.parseLong(objVal.toString())); 		
		}
	}
}
