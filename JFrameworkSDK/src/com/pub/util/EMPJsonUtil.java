package com.pub.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.util.EMPReflectUtil;

public class EMPJsonUtil {
	
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();
		JSONArray       jsonArray = new JSONArray();
		jsonObject.put("F_KJQJ", "201309");
		jsonObject.put("F_DJBH", "11111");
		jsonObject.put("F_GUID", "22222");
		
		jsonObject1 = new JSONObject();
		jsonObject1.put("F_KJQJ", "11111");
		jsonObject1.put("F_DJBH", "22222");
		jsonObject1.put("F_GUID", "33333");
		jsonArray.element(jsonObject);
		
		jsonObject1 = new JSONObject();
		jsonObject1.put("F_KJQJ", "11111");
		jsonObject1.put("F_DJBH", "22222");
		jsonObject1.put("F_GUID", "33333");
		jsonArray.element(jsonObject);
		
		jsonObject.put("111", jsonArray);
		
		System.out.println(jsonObject.toString());
	}
	
	//将json类型为JSONObject转化为Map
	public static Map<String, Object> convertJsonToBean(String strJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator                 it;
		JSONObject       jsonObject;
		String              jsonKey = "";
		Object              jsonVal;
		
		System.out.println("before convert:" + strJson);
		
		try {
			jsonObject = (JSONObject)JSONSerializer.toJSON(strJson);
			it = jsonObject.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				jsonKey = m.getKey().toString();
				jsonVal = m.getValue();
				if(jsonVal instanceof JSONArray) {
					convertJSONArrayToObject(dataMap, jsonKey, jsonVal);
				} else if(jsonVal instanceof JSONObject) {
					convertJSONObjectToObject(dataMap, jsonKey, jsonVal);
				} else if(jsonVal instanceof String) {
					dataMap.put(jsonKey, jsonVal);
				}
			}
		} catch(Exception ce) {
			ce.printStackTrace();
			dataMap.put("error", ce.toString());
		}		
		return dataMap;
	}
	
	//将json转化为EFRowSet
	public static EFRowSet convertMapToRowSet(Map dataMap, EFRowSet metadata) {
		EFRowSet             rowSet = EFRowSet.getInstance();
		List               jsonList;	
		Iterator                 it;
		String              jsonKey = "";
		Object              jsonVal;
		
		System.out.println("before convert:" + dataMap.toString());
		
		try {
			it = dataMap.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				jsonKey = m.getKey().toString();
				jsonVal = m.getValue();
				if(jsonVal instanceof List) {
					jsonList = (List)jsonVal;
					convertListToDataSet(rowSet, jsonKey, jsonList, metadata);
				} else if(jsonVal instanceof String) {
					rowSet.putObject(jsonKey, jsonVal);
				}
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}		
		return rowSet;
	}
	
	//将json转化为List
	public static List<Object> convertJsonToList(String strJson) {
		List<Object>       dataList = new ArrayList<Object>();
		JSONArray         jsonArray;
		
		try {
			jsonArray = (JSONArray)JSONSerializer.toJSON(strJson);
			return convertJsonArrayToList(jsonArray);
		} catch(Exception ce) {
			ce.printStackTrace();
			dataList.add("error:" + ce.toString());
		}		
		return dataList;
	}

	public static void convertJSONObjectToObject(Map<String, Object> dataMap, String jsonKey, Object jsonVal) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		JSONObject jsonObject = (JSONObject)jsonVal;
		dataMap.put(jsonKey, createClassObject(jsonKey, jsonObject));
	}
	
	public static void convertJSONArrayToObject(Map<String, Object> dataMap, String jsonKey, Object jsonVal) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		List              list = new ArrayList();
		JSONArray    jsonArray = (JSONArray)jsonVal;
		
		if(jsonArray.size() > 0) {
			if(jsonArray.get(0) instanceof JSONObject) {
				list = convertJSONArrayToBeanList(jsonKey, jsonVal);
			} else {
				list = convertJsonArrayToList(jsonArray);				
			}
			dataMap.put(jsonKey, list);
		}
	}
	
	public static List convertJSONArrayToBeanList(String jsonKey, Object jsonVal) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		JSONObject   jsonObject;	
		List               list = new ArrayList();
		JSONArray     jsonArray = (JSONArray)jsonVal;
		
		for(int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			list.add(createClassObject(jsonKey, jsonObject));
		}
		return list;
	}
	
	//将json转化为List
	public static List<Object> convertJsonArrayToList(Object jsonVal) throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		List<Object>       dataList = new ArrayList<Object>();
		JSONArray         jsonArray = (JSONArray)jsonVal;
		JSONArray             array;
		Object                  obj;
		
		for(int i = 0; i < jsonArray.size(); i++) {
			obj = jsonArray.get(i);
			if(obj instanceof JSONArray) {
				array = (JSONArray) obj;
				if(array.size() > 0) {
					if(array.get(0) instanceof JSONObject) {
						dataList = convertJSONArrayToBeanList("", array);
					} else {
						dataList.add(convertJsonArrayToList(array));
					}
				}
			} else {
				dataList.add(jsonArray.get(i));
			}
		}
		return dataList;
	}
	
	public static Object createClassObject(String key, JSONObject jsonObject) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class    reflectClass = EMPReflectUtil.getReflectClass(key);	
		Field[]        fields = reflectClass.getDeclaredFields();
		Field           field;
		
		Object       classObj  = reflectClass.newInstance();            //创建类的对象
		
		for(int i = 0; i < fields.length; i++) {
			field = fields[i];
			if(jsonObject.get(field.getName()) == null) continue;
			EMPReflectUtil.setDeclareFieldValue(classObj, field, jsonObject.get(field.getName()));
		}
		return classObj;
	}
	
	//将json转化为EFRowSet
	public static EFRowSet convertJsonToRowSet(String strJson) {
		EFRowSet             rowSet = EFRowSet.getInstance();
		JSONArray         jsonArray;	
		Iterator                 it;
		JSONObject       jsonObject;
		String              jsonKey = "";
		Object              jsonVal;
		
		System.out.println("before convert:" + strJson);
		
		try {
			jsonObject = (JSONObject)JSONSerializer.toJSON(strJson);
			it = jsonObject.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				jsonKey = m.getKey().toString();
				jsonVal = m.getValue();
				if(jsonVal instanceof JSONArray) {
					jsonArray = (JSONArray)jsonVal;
					convertJsonArrayToDataSet(rowSet, jsonKey, jsonArray);
				} else if(jsonVal instanceof JSONObject) {
					convertJsonObjectToDataSet(rowSet, jsonKey, (JSONObject)jsonVal);
				} else if(jsonVal instanceof String) {
					rowSet.putObject(jsonKey, jsonVal);
				}
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}		
		return rowSet;
	}
	
	//将json转化为EFRowSet
	public static EFRowSet convertMapToRowSet(Map dataMap) {
		EFRowSet             rowSet = EFRowSet.getInstance();
		List               jsonList;	
		Iterator                 it;
		String              jsonKey = "";
		Object              jsonVal;
		
		System.out.println("before convert:" + dataMap.toString());
		
		try {
			it = dataMap.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				jsonKey = m.getKey().toString();
				jsonVal = m.getValue();
				if(jsonVal instanceof List) {
					jsonList = (List)jsonVal;
					convertListToDataSet(rowSet, jsonKey, jsonList);
				} else if(jsonVal instanceof String) {
					rowSet.putObject(jsonKey, jsonVal);
				}
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}		
		return rowSet;
	}
	
	//将List转化为EFDataSet
	public static void convertListToDataSet(EFRowSet rowSet, String jsonKey, List list) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class         reflectClass = EMPReflectUtil.getReflectClass(jsonKey);	
		EFRowSet            rowset;
		Map                    map = null;
		EFDataSet          dataSet = EFDataSet.getInstance(reflectClass.getName());
		
		for(int i = 0; i < list.size(); i++) {
			rowset = EFRowSet.getInstance();
			map = (Map)list.get(i);
			rowset = createRowSet(reflectClass, map);
			dataSet.insertRowSet(rowset);
		}
		rowSet.setDataSet(jsonKey, dataSet);
	}
	
	//将List转化为EFDataSet
	public static void convertListToDataSet(EFRowSet rowSet, String jsonKey, List list, EFRowSet metadata) throws Exception {
		EFDataSet      metaDataSet = metadata.getDataSet(jsonKey);
		EFRowSet        metaRowSet = null;
		EFRowSet            rowset;
		Map                    map = null;
		EFDataSet          dataSet = EFDataSet.getInstance(jsonKey);
		
		metaDataSet.setPrimeKey(new String[]{"COL_ID"});
		metaDataSet.buildPrimeKeyIndex();
		for(int i = 0; i < list.size(); i++) {
			rowset = EFRowSet.getInstance();
			map = (Map)list.get(i);
			rowset = createRowSet(map, metaDataSet);
			dataSet.insertRowSet(rowset);
		}
		rowSet.setDataSet(jsonKey, dataSet);
	}
	
	//将json转化为EFDataSet
	public static void convertJsonArrayToDataSet(EFRowSet rowSet, String jsonKey, JSONArray jsonArray) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class         reflectClass = EMPReflectUtil.getReflectClass(jsonKey);	
		JSONObject      jsonObject;
		EFRowSet            rowset;
		EFDataSet          dataSet = EFDataSet.getInstance(reflectClass.getName());
		
		for(int i = 0; i < jsonArray.size(); i++) {
			rowset = EFRowSet.getInstance();
			jsonObject = jsonArray.getJSONObject(i);
			rowset = createRowSet(reflectClass, jsonKey, jsonObject);
			dataSet.insertRowSet(rowset);
		}
		rowSet.setDataSet(jsonKey, dataSet);
	}
	
	//将json转化为EFDataSet
	public static void convertJsonObjectToDataSet(EFRowSet rowSet, String jsonKey, JSONObject jsonObject) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class       reflectClass = EMPReflectUtil.getReflectClass(jsonKey);	
//		EFRowSet          rowset = createRowSet(reflectClass, jsonKey, jsonObject);
		EFDataSet        dataSet = EFDataSet.getInstance(reflectClass.getName());

		rowSet.setDataSet(jsonKey, dataSet);
	}
	
	public static EFRowSet createRowSet(Class reflectClass, String key, JSONObject jsonObject) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {		
		Field[]    fields = reflectClass.getDeclaredFields();
		Field       field;
		EFRowSet   rowset = EFRowSet.getInstance();
		
		for(int i = 0; i < fields.length; i++) {
			field = fields[i];
			if(jsonObject.get(field.getName()) == null) continue;
			EMPReflectUtil.setDeclareFieldValueToRowSet(rowset, field, jsonObject.get(field.getName()));
		}
		return rowset;
	}
	
	public static EFRowSet createRowSet(Class reflectClass, Map map) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {		
		Field[]    fields = reflectClass.getDeclaredFields();
		Field       field;
		EFRowSet   rowset = EFRowSet.getInstance();

		for(int i = 0; i < fields.length; i++) {
			field = fields[i];
			if(map.get(field.getName()) == null) continue;
			EMPReflectUtil.setDeclareFieldValueToRowSet(rowset, field, map.get(field.getName()));
		}
		return rowset;
	}
	
	public static EFRowSet createRowSet(Map map, EFDataSet metaDataSet) throws Exception {
		EFRowSet                  rowset = EFRowSet.getInstance();
		EFRowSet             meataRowset = null;
		String                       key = "";
		Object                     value = null;
		Iterator                iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry m = (Entry) iterator.next();
			key = m.getKey().toString();
			value = m.getValue();
			meataRowset = (EFRowSet) metaDataSet.getRowSet(key);
			if(meataRowset == null) continue;
			EMPReflectUtil.setDeclareFieldValueToRowSet(rowset, meataRowset, value);
		}
		return rowset;
	}
}
