package com.pub.util;

import java.math.*;

import com.pub.util.StringFunction;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ESPKeyValueUtil {
    /**
     * String的KeyValue工具
     */

    /**
     * 默认的分隔符
     */
    public static final String _DEFAULT_DELIMETER_ = ";";
    /**
     * 默认的连接符
     */
    public static final String _DEFAULT_CONNECTOR_ = "=";
    /**
     * 默认的合并符，如果一个字符串中key有多个值，进行合并
     */
    public static final String _DEFAULT_MERGE_SYMBOL_ = ",";

    /**
     * 测试方法
     * @param map Map
     * @return String
     */
    public static String map2String(java.util.Map map) {
        if (map == null) return "";
        Object[] key = map.keySet().toArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; key != null && i < key.length; i++) {
            if (sb.length() > 0) sb.append(";");
            sb.append(key[i]);
            sb.append("=");
            sb.append(map.get(key[i]) == null ? "":map.get(key[i]).toString());
        }
        return sb.toString();
    }

    //测试方法
    public static void main(String[] args) {
        String a = "ab";
        String b = "a" + "b";
        System.out.println(a == b);
        long time = 180*24*60*60;
        time = time * 1000;
        long time1= System.currentTimeMillis();
        System.out.println(time);
        System.out.println(time1);
        System.out.println(time+time1);
        String keyValue = "KEY1=VALUE1;KEY2=VALUE2;KEY3=VALUE3;KEY1=VALUE11;KEY1=VALUE12;KEY2=VALUE21";
        //合并符号
        java.util.Map symbol = new java.util.HashMap();
        symbol.put("KEY1", ",");
        symbol.put("KEY2", " || ");

        java.util.Map map1 = getKeyValue(keyValue);
        System.out.println("未合并：" + map2String(map1));
        java.util.Map map2 = getMergedKeyValue(keyValue, symbol);
        System.out.println("合并：" + map2String(map2));
        String value1 = getValueByKey("KEY1", "222", keyValue);
        System.out.println("未合并：" + value1);
        String value2 = getMergedValueByKey("KEY1", "222", keyValue, "||");
        System.out.println("合并：" + value2);
    }

    /**
     *
     * @param keyValue String
     * @return List
     */
    public static java.util.List<String[]> getMergedKeyValueArray(String keyValue, java.util.Map<String,String> mergeSymbolMap) {
        return getMergedKeyValueArray(null, null, keyValue, mergeSymbolMap);
    }

    /**
     *
     * @param connector String
     * @param delimeter String
     * @param keyValue String
     * @param mergeSymbolMap Map //每个key可能是有自己的合并符号，默认是逗号
     * @return List
     */
    public static java.util.List<String[]> getMergedKeyValueArray(String connector, String delimeter, String keyValue,
            java.util.Map<String,String> mergeSymbolMap) {
        //原始KeyValue数组
        java.util.List<String[]> arrays = getKeyValueArray(connector, delimeter, keyValue);
        //按Key进行合并
        String mergeSymbol = null;
        //被合并的索引
        java.util.List<String> mergedIndex = new java.util.ArrayList<String>();
        for (int i = 0; i < arrays.size(); i++) {
            String[] array0 = arrays.get(i);
            for (int j = i + 1; j < arrays.size(); j++) {
                String[] array1 = arrays.get(j);
                //如果二者的key相同，合并二者的value
                if (array1[0].equals(array0[0])) {
                    //是否有自定义的合并符号
                    if (mergeSymbolMap != null) mergeSymbol = mergeSymbolMap.get(array1[0]);
                    //使用默认的合并符号
                    if (mergeSymbol == null || mergeSymbol.length() == 0) mergeSymbol = _DEFAULT_MERGE_SYMBOL_;
                    //合并以后的value
                    array0[1] += mergeSymbol + array1[1];
                    //记下来被合并的，最后删除之
                    mergedIndex.add(String.valueOf(j));
                }
            }
        }
        //最后删除被合并的索引，从高位往低位删
        java.util.List<String[]> newArrays = new java.util.ArrayList<String[]>();
        for (int i = 0; i < arrays.size(); i++) {
            //被合并的，不要了
            if (mergedIndex.contains(String.valueOf(i))) continue;
            newArrays.add(arrays.get(i));
        }
        return newArrays;
    }

    /**
     *
     * @param keyValue String
     * @return List
     */
    public static java.util.List<String[]> getKeyValueArray(String keyValue) {
        return getKeyValueArray(null, null, keyValue);
    }

    /**
     *
     * @param connector String
     * @param delimeter String
     * @param keyValue String
     * @return List
     */
    public static java.util.List<String[]> getKeyValueArray(String connector, String delimeter, String keyValue) {
        java.util.List<String[]> keyValueArray = new java.util.ArrayList<String[]>();
        if (connector == null || connector.length() == 0) connector = _DEFAULT_CONNECTOR_;
        if (delimeter == null || delimeter.length() == 0) delimeter = _DEFAULT_DELIMETER_;
        String[] extValues = null;
        if (keyValue != null)
            extValues = keyValue.split(delimeter);
        for (int i = 0; extValues != null && i < extValues.length; i++) {
            //没有连接符的视为无效
            if (extValues[i].indexOf(connector) < 0) continue;
            String[] array = StringFunction.splitStringByToken(extValues[i], connector);
//            String[] array = extValues[i].split(connector);
//            String key = extValues[i].substring(0, extValues[i].indexOf(connector));
//            String val = extValues[i].substring(extValues[i].indexOf(connector)+ 1);
//            String[] array = new String[]{key, val};
            keyValueArray.add(array);
        }
        return keyValueArray;
    }

    /**
     *
     * @param connector String
     * @param delimeter String
     * @param keyValue String
     * @return Map
     */
    public static java.util.Map<String,String> getKeyValue(String connector, String delimeter, String keyValue){
        java.util.Map<String,String> extMap = new java.util.HashMap<String,String>();
        java.util.List<String[]>   extArray = getKeyValueArray(connector, delimeter, keyValue);
        for (int i = 0; extArray != null && i < extArray.size(); i++) {
            String[] array = extArray.get(i);
            extMap.put(array[0], array[1]);
        }
        return extMap;
    }

    /**
     *
     * @param connector String
     * @param delimeter String
     * @param keyValue String
     * @param mergeSymbolMap Map
     * @return Map
     */
    public static java.util.Map<String,String> getMergedKeyValue(String connector, String delimeter, String keyValue,
            java.util.Map<String, String> mergeSymbolMap) {
        java.util.Map<String,String> extMap = new java.util.HashMap<String,String>();
        java.util.List<String[]> extArray = getMergedKeyValueArray(connector, delimeter, keyValue, mergeSymbolMap);
        for (int i = 0; extArray != null && i < extArray.size(); i++) {
            String[] array = extArray.get(i);
            extMap.put(array[0], array[1]);
        }
        return extMap;
    }

    /**
     *
     * @param keyValue String
     * @return Map
     */
    public static java.util.Map<String, String> getKeyValue(String keyValue) {
        return getKeyValue(null, null, keyValue);
    }

    /**
     *
     * @param keyValue String
     * @return Map
     */
    public static java.util.Map<String, String> getMergedKeyValue(String keyValue, java.util.Map<String, String> mergeSymbolMap) {
        return getMergedKeyValue(null, null, keyValue, mergeSymbolMap);
    }

    /**
     * 取一个字符串中的某个关键字的值
     *
     * @param key String
     * @param defaultValue String
     * @param keyValue String
     * @return String
     */
    public static String getValueByKey(String key, String defaultValue, String keyValue) {
        java.util.Map<String, String> keyValueMap = getKeyValue(keyValue);
        if (keyValueMap.get(key) == null) return defaultValue;
        return (String) keyValueMap.get(key);
    }

    /**
     *
     * @param key String
     * @param defaultValue String
     * @param keyValue String
     * @param mergeSymbol String
     * @return String
     */
    public static String getMergedValueByKey(String key, String defaultValue, String keyValue, String mergeSymbol) {
        java.util.Map<String, String> mergeSymbolMap = new java.util.HashMap<String, String>();
        mergeSymbolMap.put(key, mergeSymbol);
        java.util.Map<String, String> keyValueMap = getMergedKeyValue(keyValue, mergeSymbolMap);
        if (keyValueMap.get(key) == null) return defaultValue;
        return (String) keyValueMap.get(key);
    }

    /**
     *
     * @param connector String
     * @param delimeter String
     * @param key String
     * @param defaultValue String
     * @param keyValue String
     * @return String
     */
    public static String getValueByKey(String connector, String delimeter, String key, String defaultValue, String keyValue) {
        java.util.Map<String, String> keyValueMap = getKeyValue(connector, delimeter, keyValue);
        if (keyValueMap.get(key) == null) return defaultValue;
        return (String) keyValueMap.get(key);
    }

    /**
     *
     * @param connector String
     * @param delimeter String
     * @param key String
     * @param defaultValue String
     * @param keyValue String
     * @param mergeSymbol String
     * @return String
     */
    public static String getMergedValueByKey(String connector, String delimeter, String key, String defaultValue, String keyValue, String mergeSymbol) {
        java.util.Map<String, String> mergeSymbolMap = new java.util.HashMap<String, String>();
        mergeSymbolMap.put(key, mergeSymbol);
        java.util.Map<String, String> keyValueMap = getMergedKeyValue(connector, delimeter, keyValue, mergeSymbolMap);
        if (keyValueMap.get(key) == null) return defaultValue;
        return (String) keyValueMap.get(key);
    }

}
