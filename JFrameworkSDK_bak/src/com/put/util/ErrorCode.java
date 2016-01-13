package com.put.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 错误信息处理
 */

public class ErrorCode {
    
    /**
     * 判断数据是否为空
     * @param inputStr
     * @return
     */
    public static boolean isEmpty(String inputStr) {
    	
        if (inputStr == null || inputStr.trim().equals("")||inputStr.equals("null")) {
            return true;
        }
        return false;
    }

    
    /**
     * 判断数据是否为指定的长度
     * @param inputStr
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean isInLength(String inputStr, int minLength, int maxLength) {

        int fieldLength = inputStr.trim().length();
        if (fieldLength > maxLength || fieldLength < minLength) {
            return false;
        }
        return true;
    }

    
   /**
    * 判断数据是否为指定的格式ָ
    * @param inputStr
    * @param expstr
    * @return
    */
    public static boolean isMatcher(String inputStr,String expstr) {
        Pattern pattern = Pattern.compile(expstr);
        Matcher m = pattern.matcher(inputStr);
        boolean b = m.matches();
        return b;
    }


    /**
     * 检查in是否在start与end之间
     * @param in
     * @param start
     * @param end
     * @return
     */
    public static boolean isInRange(double in, double start, double end) {
    	if(in < start || in >= end) {
    		return false;
    	} else {
    		return true;
    	}
    }

    
    /**
     * 主函数，用来测试方法的准确性
     * @param args
     */
    public static void main(String[]args){
//    	Map map = ErrorCode.map;
//    	Object[] arrays = map.keySet().toArray();
//    	String ms = null;
//    	//Object
//    	for(int i=0; i<arrays.length; i++){
//    		
//    		 ms =	ErrorCode.getErrorMessage(Integer.parseInt(""+arrays[i]));
//    		 log4.info(arrays[i]+" == "+ms);
//    	}
     
      
    }
}

