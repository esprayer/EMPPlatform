package jservice.jdal.classes.query.dataset;

import java.io.*;
import java.security.*;
import java.sql.*;
import java.util.*;
import java.util.zip.*;

import org.jdom.Element;
import org.nfunk.jep.JEP;

import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.Debug;
import com.efounder.pub.util.JFNumber;

import jdatareport.dof.classes.report.QueryDataTransformer;
import jservice.jdal.classes.query.server.*;


/**
 * 服务器端数据格式化类。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Pansoft</p>
 * @author not attributable
 * @version 1.0
 */
public class QueryDataOutObject {
  /**
   * 缓存数据行数阀值，保持内存稳定；行数小于阀值的查询结果不写入文件，而是直接返回数据
   */
  public static int ROW_VALVE = 2000;
  /**
   * StringBuffer 的初始和增量单位 2k
   */
  public static int BUFFER_INIT_SIZE = 2048;


  /**
   * 私有构造器，强制该类不被外界类实例化，只用其静态方法
   */
  private QueryDataOutObject() {
  }

  /**
   * 根据记录集行数，确定写 文件/字符串
   * @param viewIDs String[]  与格式文件一致的列标题数组
   * @param ids String[]      字段名数组,用于从记录集ResultSet获取数据,rs.getstring(ids[i])
   * @param rs ResultSet      ResultSet
   * @return Object           数据存放的URL 或 数据字符串
   * @throws Exception
   */
  public static String formatData(JParamObject PO,
                                  String[] viewIDs,
                                  String[] ids,
                                  ResultSet rs) throws Exception {
    return formatData(PO, false, "", viewIDs, ids, rs);
  }

  /**
   * 根据记录集行数，确定写 文件/字符串
   * @param isClass boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String       分级列编码结构,譬如 44444
   * @param viewIDs String[]  与格式文件一致的列标题数组
   * @param ids String[]      字段名数组,用于从记录集ResultSet获取数据,rs.getstring(ids[i])
   * @param rs ResultSet      ResultSet
   * @return String           数据存放的URL 或 数据字符串
   * @throws Exception
   */
  public static String formatData(JParamObject PO,
                                  boolean isClass,
                                  String stru,
                                  String[] viewIDs,
                                  String[] ids,
                                  ResultSet rs) throws Exception {

    long s = System.currentTimeMillis();
    //取写文件的行阀值
    ROW_VALVE = Integer.parseInt(PO.GetValueByEnvName("ZW_QUERYVALVE", "2000"));
    //也许用不到，改造缓存方法时所加2009-09-04
    FormatDataManager dataObject = new FormatDataManager();
    StringBuffer buffer = new StringBuffer(BUFFER_INIT_SIZE);
    //头信息 2008-1-3 fengbo
    String tempTag = "";
    String classTag = PO.GetValueByParamName(QueryDataTransformer.
                                             PART_BH_CLASS_NOTE, "");
    //数据不分级
    if (!isClass) {
      tempTag = QueryDataTransformer.NO_CLASS_Tag;
    }
    else {
      if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag)) {
        tempTag = QueryDataTransformer.PART_BH_CLASS_Tag;
      }
      else {
        tempTag = QueryDataTransformer.ALL_BH_CLASS_Tag;
      }
    }
    buffer.append("TAG=" + tempTag);

    buffer.append("\r\n");
    buffer.append("STRU=" + stru);
    buffer.append("\r\n");
    for (int i = 0; i < viewIDs.length; i++) {
      buffer.append(viewIDs[i]);
      buffer.append("\t");
    }
    buffer.append("\r\n");
    //数据
    byte[] bys;
    int count = 0;
    String temp;
    while (rs.next()) {
      //缓存一行数据
      for (int i = 0; i < ids.length; i++) {
        temp = rs.getString(ids[i]);
        if (temp == null) {
          temp = "";
        }
        /**
         * 经和fengbo/phoenix讨论，确定只取掉后面的空格
         * modified by gengeng 2009-3-12
         */
        temp = "G" + temp;
        temp = temp.trim();
        buffer.append(temp.substring(1) + "\t");
      }
      buffer.append("\r\n");
      count++;

      //当缓存达到预定的阀值（ROW_VALVE）时，写入文本文件，释放内存
      if (count >= ROW_VALVE) {
        //写文件
        bys = buffer.toString().getBytes("UTF-8");
        QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,false);
        //复位，释放内存占用，保持内存稳定
        buffer.setLength(0);
        count = 0;
      }
    }
    //输出剩余数据，关闭文件
    if (dataObject.getOutObject() != null) {
      bys = buffer.toString().getBytes("UTF-8");
      QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,true);
    }
    //若数据行数<ROW_VALVE ,则写字符串
    if (dataObject.getOutObject() == null) {
      dataObject.getFormatedBufferDate().append(buffer);
    }
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("记录集一次性写文件，耗时：" + (e - s) + " 毫秒");
    return dataObject.getFormatedBufferDate().toString();
  }

  public static String formatData(JParamObject PO,
                                  boolean isClass,
                                  String stru,
                                  String[] viewIDs,
                                  int[] ids,
                                  ResultSet rs) throws Exception {

    long s = System.currentTimeMillis();
    //取写文件的行阀值
    ROW_VALVE = Integer.parseInt(PO.GetValueByEnvName("ZW_QUERYVALVE", "2000"));
    FormatDataManager dataObject = new FormatDataManager();
    StringBuffer buffer = new StringBuffer(BUFFER_INIT_SIZE);
    //头信息
//    buffer.append("TAG=" + (isClass ? "1" : "0"));
    //头信息 2008-1-3 fengbo
    String tempTag = "";
    String classTag = PO.GetValueByParamName(QueryDataTransformer.
                                             PART_BH_CLASS_NOTE, "");
    //数据不分级
    if (!isClass) {
      tempTag = QueryDataTransformer.NO_CLASS_Tag;
    }
    else {
      if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag)) {
        tempTag = QueryDataTransformer.PART_BH_CLASS_Tag;
      }
      else {
        tempTag = QueryDataTransformer.ALL_BH_CLASS_Tag;
      }
    }
    buffer.append("TAG=" + tempTag);
    buffer.append("\r\n");
    buffer.append("STRU=" + stru);
    buffer.append("\r\n");
    for (int i = 0; i < viewIDs.length; i++) {
      buffer.append(viewIDs[i]);
      buffer.append("\t");
    }
    buffer.append("\r\n");
    //数据
    byte[] bys;
    int count = 0;
    String temp;
    while (rs.next()) {
      //缓存一行数据
      for (int i = 0; i < ids.length; i++) {
        temp = rs.getString(ids[i]);
        if (temp == null) {
          temp = "";
        }
        buffer.append(temp + "\t");
      }
      buffer.append("\r\n");
      count++;

      //当缓存达到预定的阀值（ROW_VALVE）时，写入文本文件，释放内存
      if (count >= ROW_VALVE) {
        //写文件
        bys = buffer.toString().getBytes("UTF-8");

        QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,false);
        //复位，释放内存占用，保持内存稳定
        buffer.setLength(0);
        count = 0;
      }
    }
    //输出剩余数据，关闭文件
    if (dataObject.getOutObject() != null) {
      bys = buffer.toString().getBytes("UTF-8");
      QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,true);
    }
    //若数据行数<ROW_VALVE ,则写字符串
    if (dataObject.getOutObject() == null) {
      dataObject.getFormatedBufferDate().append(buffer);
    }
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("记录集一次性写文件，耗时：" + (e - s) + " 毫秒");
    return dataObject.getFormatedDate().toString();
  }

  /**
   * 根据记录集和列描述信息生成输出数据。
   * @param rs ResultSet
   * @param dataStruArray PDataSetStru[]
   * @return String
   * @throws Exception
   */
  public static String formatData(JParamObject PO,
                                  PDataSetStru[] dataStruArray,
                                  boolean isSumRow,
                                  ResultSet rs) throws
      Exception {
    return formatData(PO, false, "", dataStruArray, isSumRow, rs);
  }

  /**
   * 根据记录集和列描述信息生成输出数据。
   * @param isClass boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String       分级列编码结构,譬如 44444
   * @param rs ResultSet      记录集
   * @param dataStruArray PDataSetStru[] 列描述信息数组
   * @return String
   * @throws Exception
   */
  public static String formatData(JParamObject PO,
                                  boolean isClass,
                                  String stru,
                                  PDataSetStru[] dataStruArray,
                                  boolean isSumRow,
                                  ResultSet rs) throws
      Exception {

    long s = System.currentTimeMillis();
    //取写文件的行阀值
    ROW_VALVE = Integer.parseInt(PO.GetValueByEnvName("ZW_QUERYVALVE", "2000"));
    PDataSetStru dataStru;
    FormatDataManager dataObject = new FormatDataManager();
    StringBuffer buffer = new StringBuffer(BUFFER_INIT_SIZE);
    Map indexMap = new HashMap();
    //头信息
//    buffer.append("TAG=" + (isClass ? "1" : "0"));
    //头信息 2008-1-3 fengbo
    String tempTag = "";
    String classTag = PO.GetValueByParamName(QueryDataTransformer.
                                             PART_BH_CLASS_NOTE, "");
    //数据不分级
    if (!isClass) {
      tempTag = QueryDataTransformer.NO_CLASS_Tag;
    }
    else {
      if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag)) {
        tempTag = QueryDataTransformer.PART_BH_CLASS_Tag;
      }
      else {
        tempTag = QueryDataTransformer.ALL_BH_CLASS_Tag;
      }
    }
    buffer.append("TAG=" + tempTag);
    buffer.append("\r\n");
    buffer.append("STRU=" + stru);
    buffer.append("\r\n");
    for (int i = 0; i < dataStruArray.length; i++) {
      dataStru = dataStruArray[i];
      buffer.append(dataStru.getViewID());
      buffer.append("\t");
      indexMap.put(dataStru.getViewID(), new Integer(i));
    }
    buffer.append("\r\n");

    //初始化合计行数据
    double[] sumRow = new double[dataStruArray.length];
    for (int i = 0; i < sumRow.length; i++) {
      sumRow[i] = 0;
    }
    //缓存一行数据
    String[] rowData = new String[dataStruArray.length];
    byte[] bys;
    int count = 0;
    int rowCount = 0;
    String temp;
    //数据
    while (rs.next()) {
      rowCount++;
      //1 取基本数据（原始数据）
      for (int i = 0; i < dataStruArray.length; i++) {
        dataStru = dataStruArray[i];
        if (dataStru.getID() != null && !dataStru.getID().equals("")) {
          //rowData[i] = rs.getString(dataStru.getID());
          temp = rs.getString(dataStru.getID());
          if (temp == null) {
            temp = "";
          }
          rowData[i] = temp;
        }
      }
      //2 计算列求值, 列合计
      rowData = countExpCol(rowCount, indexMap, dataStruArray, rowData, sumRow);
      //3 输出该行
      for (int i = 0; i < rowData.length; i++) {
        buffer.append(rowData[i] + "\t");
        //置空
        rowData[i] = "";
      }
      buffer.append("\r\n");
      count++;
      //当缓存达到预定的阀值（ROW_VALVE）时，写入文本文件，释放内存
      if (count >= ROW_VALVE) {

        bys = buffer.toString().getBytes("UTF-8");
        QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,false);
        //复位，释放内存占用，保持内存稳定
        buffer.setLength(0);
        count = 0;
      }
    }
    //最后添加合计行
    if (isSumRow) {
      for (int i = 0; i < dataStruArray.length; i++) {
        dataStru = dataStruArray[i];
        //合计标示
        if (dataStru.getSumString() != null &&
            !dataStru.getSumString().equals("")) {
          buffer.append(dataStru.getSumString() + "\t");
        }
        else {
          if (dataStru.isColSum()) {
            buffer.append(sumRow[i] + "\t");
          }
          else {
            buffer.append("\t");
          }
        }
      }
      buffer.append("\r\n");
    }
    //输出剩余数据，关闭文件
    if (dataObject.getOutObject() != null) {
      bys = buffer.toString().getBytes("UTF-8");
      QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,true);
    }
    //若数据行数<ROW_VALVE ,则写字符串
    if (dataObject.getOutObject() == null) {
      dataObject.getFormatedBufferDate().append(buffer);
    }
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("记录集一次性写文件，耗时：" + (e - s) + " 毫秒");
    return dataObject.getFormatedDate().toString();
  }

  /**
   * 计算列求值
   */
  private static String[] countExpCol(int rowCount,
                                      Map indexMap,
                                      PDataSetStru[] dataStruArray,
                                      String[] rowData,
                                      double[] sumRow) throws Exception {
    PDataSetStru dataStru;
    String expression;
    double expValue;
    for (int i = 0; i < dataStruArray.length; i++) {
      dataStru = dataStruArray[i];
      expression = dataStru.getExpression();
      //列运算
      if ( (dataStru.getID() == null || dataStru.getID().equals("")) &&
          expression != null && !expression.equals("")) {
        //自增运算
        if (expression.equals(PDataSetStru.INCREMENT_VALUE)) {
          rowData[i] = Integer.toString(rowCount);
        }
        //列运算
        else {
          expValue = translateExpression(indexMap, expression, rowData,
                                         dataStruArray);
          expValue = JFNumber.round(expValue, dataStru.getDec());
          rowData[i] = Double.toString(expValue);
        }
      }
      //行合计
      if (dataStru.getType().equals(PDataSetStru.NUM_TYPE) && dataStru.isColSum()) {
        //列合计过滤表达式
        expression = dataStru.getColSumConditionExp();
        //有条件合计
        if (expression != null && !expression.trim().equals("")) {
          expValue = translateExpression(indexMap, expression, rowData,
                                         dataStruArray);
          //Debug.PrintlnMessageToSystem("布尔表达式：" + expression + " 值：" + expValue);
          if (expValue == 1) {
            sumRow[i] += Double.parseDouble(rowData[i]);
            sumRow[i] = JFNumber.round(sumRow[i], dataStru.getDec());
          }
        }
        //无条件合计
        else {
          sumRow[i] += Double.parseDouble(rowData[i]);
          sumRow[i] = JFNumber.round(sumRow[i], dataStru.getDec());
        }
      }
    }
    return rowData;
  }

  private static double translateExpression(Map indexMap,
                                            String expression,
                                            String[] rowData,
                                            PDataSetStru[] dataStruArray) throws
      Exception {
    Iterator iterator = indexMap.keySet().iterator();
    String key;
    int index;
    int position;
    //确定表达式类型
    String expType = getType(dataStruArray, expression);
    //替换表达式中的列id为具体数据
    while (iterator.hasNext()) {
      key = (String) iterator.next();
      index = ( (Integer) indexMap.get(key)).intValue();
      position = expression.indexOf(key);
      if (position > -1) {
        //数值型
        if (expType.equals(PDataSetStru.NUM_TYPE)) {
          expression = expression.replaceAll(key, rowData[index]);
        }
        //字符串型
        else {
          expression = expression.replaceAll(key, "\"" + rowData[index] + "\"");
        }
      }
    }
    //计算表达式
    JEP mJepParser = new JEP();
    mJepParser.parseExpression(expression);
    Object obj = mJepParser.getValueAsObject();
    if (obj != null && obj instanceof Double) {
      return ( (Double) obj).doubleValue();
    }
    throw new Exception("表达式: " + expression + " 解析异常！");
  }

  private static String getType(PDataSetStru[] dataStruArray, String expression) {
    String viewID;
    for (int i = 0; i < dataStruArray.length; i++) {
      viewID = dataStruArray[i].getViewID();
      if (expression.indexOf(viewID) > -1) {
        //表达式中有字符列，则认为表达式是字符型
        if (dataStruArray[i].getType().equals(PDataSetStru.CHAR_TYPE)) {
//          Debug.PrintlnMessageToSystem("布尔表达式: " + expression + " 类型：" +
//                                       dataStruArray[i].getType());
          return dataStruArray[i].getType();
        }
      }
    }
//    Debug.PrintlnMessageToSystem("布尔表达式: " + expression + " 类型：" +
//                                 PDataSetStru.NUM_TYPE);
    return PDataSetStru.NUM_TYPE;
  }

  public static String formatData(JParamObject PO,
                                  boolean isLeveled,
                                  String stru,
                                  String[][] viewID,
                                  String[][] ids,
                                  ResultSet rs) throws Exception {
    long s = System.currentTimeMillis();
    //取写文件的行阀值
    ROW_VALVE = Integer.parseInt(PO.GetValueByEnvName("ZW_QUERYVALVE", "2000"));
    int len = viewID.length;
    FormatDataManager[] dataObject = new FormatDataManager[len];
    StringBuffer[] buffer = new StringBuffer[len];
    for (int i = 0; i < len; i++) {
      dataObject[i] = new FormatDataManager();
      //初始化
      buffer[i] = new StringBuffer(BUFFER_INIT_SIZE);
      //头信息
//      buffer[i].append("TAG=" + (isLeveled ? "1" : "0"));
      //头信息 2008-1-3 fengbo
      String tempTag = "";
      String classTag = PO.GetValueByParamName(QueryDataTransformer.
                                               PART_BH_CLASS_NOTE, "");
      //数据不分级
      if (!isLeveled) {
        tempTag = QueryDataTransformer.NO_CLASS_Tag;
      }
      else {
        if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag)) {
          tempTag = QueryDataTransformer.PART_BH_CLASS_Tag;
        }
        else {
          tempTag = QueryDataTransformer.ALL_BH_CLASS_Tag;
        }
      }
      buffer[i].append("TAG=" + tempTag);
      buffer[i].append("\r\n");
      buffer[i].append("STRU=" + stru);
      buffer[i].append("\r\n");
      for (int j = 0; j < viewID[0].length; j++) {
        buffer[i].append(viewID[0][j]);
        buffer[i].append("\t");
      }
      buffer[i].append("\r\n");
    }
    //数据
    byte[] bys;
    int count = 0;
    String temp;
    while (rs.next()) {
      //缓存一行数据
      for (int i = 0; i < len; i++) {
        for (int j = 0; j < ids[0].length; j++) {
          //buffer[i].append(rs.getString(ids[i][j]) + "\t");
          temp = rs.getString(ids[i][j]);
          if (temp == null) {
            temp = "";
          }
          buffer[i].append(temp + "\t");
        }
        buffer[i].append("\r\n");
      }
      count++;
      //当缓存达到预定的阀值（ROW_VALVE）时，写入文本文件，释放内存
      if (count >= ROW_VALVE) {
        //建文件
        for (int i = 0; i < len; i++) {
          //写文件
          bys = buffer[i].toString().getBytes("UTF-8");
          QueryCacheManager.getDefault().saveQueryResult("",dataObject[i],bys,false);
          //复位，释放内存占用，保持内存稳定
          buffer[i].setLength(0);
        }
        count = 0;
      }
    }
    //输出剩余数据，关闭文件
    if (dataObject[0].getOutObject() != null) {
      for (int i = 0; i < len; i++) {
        bys = buffer[i].toString().getBytes("UTF-8");
        QueryCacheManager.getDefault().saveQueryResult("",dataObject[i],bys,true);
      }
    }
    //若数据行数<ROW_VALVE ,则写字符串
    if (dataObject[0].getOutObject() == null) {
      for (int i = 0; i < len; i++) {
        dataObject[i].getFormatedBufferDate().append(buffer[i]);
      }
    }
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("记录集一次性写文件，耗时：" + (e - s) + " 毫秒");
    for (int i = 1; i < len; i++) {
      dataObject[0].getFormatedBufferDate().append("#@#" + dataObject[i].getFormatedBufferDate());
    }
    return dataObject[0].getFormatedDate().toString();
  }

  public static String formatData(JParamObject PO,
          boolean isLeveled,
          String stru,
          String[][] viewID,
          String[][] ids,
          String[] classifyColumn,
          ResultSet rs) throws Exception {
	  long s = System.currentTimeMillis();
	    //取写文件的行阀值
	    ROW_VALVE = Integer.parseInt(PO.GetValueByEnvName("ZW_QUERYVALVE", "2000"));
	    int len = viewID.length;
	    FormatDataManager[] dataObject = new FormatDataManager[len];
	    StringBuffer[] buffer = new StringBuffer[len];
	    for (int i = 0; i < len; i++) {
	      dataObject[i] = new FormatDataManager();
	      //初始化
	      buffer[i] = new StringBuffer(BUFFER_INIT_SIZE);
	      //头信息
//	      buffer[i].append("TAG=" + (isLeveled ? "1" : "0"));
	      //头信息 2008-1-3 fengbo
	      String tempTag = "";
	      String classTag = PO.GetValueByParamName(QueryDataTransformer.
	                                               PART_BH_CLASS_NOTE, "");
	      //数据不分级
	      if (!isLeveled) {
	        tempTag = QueryDataTransformer.NO_CLASS_Tag;
	      }
	      else {
	        if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag)) {
	          tempTag = QueryDataTransformer.PART_BH_CLASS_Tag;
	        }
	        else {
	          tempTag = QueryDataTransformer.ALL_BH_CLASS_Tag;
	        }
	      }
	      buffer[i].append("TAG=" + tempTag);
	      if(!classifyColumn[i].equals("")) {
	    	  buffer[i].append(";storeCol=" + classifyColumn[i]);
	      }
	      buffer[i].append("\r\n");
	      buffer[i].append("STRU=" + stru);
	      buffer[i].append("\r\n");
	      for (int j = 0; j < viewID[0].length; j++) {
	        buffer[i].append(viewID[0][j]);
	        buffer[i].append("\t");
	      }
	      buffer[i].append("\r\n");
	    }
	    //数据
	    byte[] bys;
	    int count = 0;
	    String temp;
	    while (rs.next()) {
	      //缓存一行数据
	      for (int i = 0; i < len; i++) {
	        for (int j = 0; j < ids[0].length; j++) {
	          //buffer[i].append(rs.getString(ids[i][j]) + "\t");
	          temp = rs.getString(ids[i][j]);
	          if (temp == null) {
	            temp = "";
	          }
	          buffer[i].append(temp + "\t");
	        }
	        buffer[i].append("\r\n");
	      }
	      count++;
	      //当缓存达到预定的阀值（ROW_VALVE）时，写入文本文件，释放内存
	      if (count >= ROW_VALVE) {
	        //建文件
	        for (int i = 0; i < len; i++) {
	          //写文件
	          bys = buffer[i].toString().getBytes("UTF-8");
	          QueryCacheManager.getDefault().saveQueryResult("",dataObject[i],bys,false);
	          //复位，释放内存占用，保持内存稳定
	          buffer[i].setLength(0);
	        }
	        count = 0;
	      }
	    }
	    //输出剩余数据，关闭文件
	    if (dataObject[0].getOutObject() != null) {
	      for (int i = 0; i < len; i++) {
	        bys = buffer[i].toString().getBytes("UTF-8");
	        QueryCacheManager.getDefault().saveQueryResult("",dataObject[i],bys,true);
	      }
	    }
	    //若数据行数<ROW_VALVE ,则写字符串
	    if (dataObject[0].getOutObject() == null) {
	      for (int i = 0; i < len; i++) {
	        dataObject[i].getFormatedBufferDate().append(buffer[i]);
	      }
	    }
	    long e = System.currentTimeMillis();
	    Debug.PrintlnMessageToSystem("记录集一次性写文件，耗时：" + (e - s) + " 毫秒");
	    for (int i = 1; i < len; i++) {
	      dataObject[0].getFormatedBufferDate().append("#@#" + dataObject[i].getFormatedBufferDate());
	    }
	    return dataObject[0].getFormatedDate().toString();
  }
  
  public static String formatData(JParamObject PO,
                                  boolean isLeveled,
                                  String stru,
                                  String[] viewID,
                                  String[] cids,
                                  String[] hbbz,
                                  String lmbhColumn,
                                  String lmDataColumn,
                                  Map lmIndexMap,
                                  int hjTag,
                                  ResultSet rs,
                                  FormatDataManager dataObject) throws
      Exception {
    return QueryDataOutObject.formatData(PO, isLeveled, stru,
                                         viewID, cids, hbbz,
                                         lmbhColumn, lmDataColumn, lmIndexMap,
                                         hjTag, false, null, 0,
                                         rs, dataObject);

  }

  /**
   *
   * @param PO JParamObject
   * @param isLeveled boolean     是否分级
   * @param stru String           编码结构
   * @param viewID String[]       显示列标题数组
   * @param cids String[]         固定列字段名数组
   * @param hbbz String[]         行合并标准字段名数组
   * @param lmbhColumn String     栏目编号字段名
   * @param lmDataColumn String   栏目数据字段名
   * @param lmIndexMap Map        (栏目,序号)对应关系
   * @param hjTag int             合计标志: 0 不增加合计列;-1 增加各栏目合计列且位置在固定列之前;1 增加各栏目合计列且位置在固定列之后;
   * @param isSumRow boolean      是否增加合计行
   * @param rowFilterExp String   若增加合计行，提供行过滤表达式，null 或 "" 表示 无条件，所有行均参加合计
   *                              参与合计的列为：所有变动栏目。
   * @param rs ResultSet
   * @throws Exception
   */
  public static String formatData(JParamObject PO,
                                  boolean isLeveled,
                                  String stru,
                                  String[] viewID,
                                  String[] cids,
                                  String[] hbbz,
                                  String lmbhColumn,
                                  String lmDataColumn,
                                  Map lmIndexMap,
                                  int hjTag,
                                  boolean isSumRow,
                                  String rowFilterExp,
                                  int dec,
                                  ResultSet rs,
                                  FormatDataManager dataObject) throws
      Exception {
    Vector dataVector = new Vector();
    //计算cids的有效长度
    int fixlen = 0;
    for (int i = 0; i < cids.length; i++) {
      if (cids[i] != null && !cids[i].equals("")) {
        fixlen++;
      }
    }
    //编号 名称 [合计]，[...变动列]
    int len = viewID.length;

    //计算合计列的位置
    int hjindex = -1;
    if (hjTag == -1) {
      hjindex = fixlen;
      //固定列+合计列
      fixlen += 1;
    }
    else if (hjTag == 1) {
//      hjindex = len;
      //2008-8-15 fengbo 修改原来对合计列在后面的支持bug
      hjindex = len - 1;
    }

    //初始化合计行数据
    double[] sumRow = new double[len];
    for (int i = 0; i < sumRow.length; i++) {
      sumRow[i] = 0;
    }

    String[] oldhbbzData = new String[hbbz.length];
    String[] hbbzData = new String[hbbz.length];
    String[] rowData = null;
    String lmbh;
    double lmye;
    double yehj = 0;
    String temp;
    Integer tempIndex;
    while (rs.next()) {
      //取得合并依据数据
      for (int i = 0; i < hbbz.length; i++) {
        hbbzData[i] = rs.getString(hbbz[i]);
      }
      //新增一行
      if (isNewRow(hbbzData, oldhbbzData)) {
        //合计前一行
        if (hjTag != 0) {
          if (rowData != null) {
            rowData[hjindex] = Double.toString(yehj);
          }
        }
        yehj = 0;
        //新增一行
        rowData = new String[len];
        for (int i = 0; i < rowData.length; i++) {
          rowData[i] = "0";
        }
        //固定列数据
        for (int i = 0; i < cids.length; i++) {
          if (cids[i] != null && !cids[i].equals("")) {
            //rowData[i] = rs.getString(cids[i]);
            temp = rs.getString(cids[i]);
            if (temp == null) {
              temp = "";
            }
            rowData[i] = temp;
          }
        }
        dataVector.add(rowData);
      }
      //传递数据
      for (int i = 0; i < hbbzData.length; i++) {
        oldhbbzData[i] = hbbzData[i];
      }
      //变动列数据：栏目编号/栏目余额
      lmbh = rs.getString(lmbhColumn);
      lmye = rs.getDouble(lmDataColumn);
      //定位栏目,赋值
      tempIndex = (Integer) lmIndexMap.get(lmbh);
      if (tempIndex != null) {
        rowData[fixlen + tempIndex.intValue()] = Double.toString(lmye);
        if (isSumRow && isAccepted(lmIndexMap, rowData, rowFilterExp)) {
          sumRow[fixlen + tempIndex.intValue()] += lmye;
          sumRow[fixlen +
              tempIndex.intValue()] = JFNumber.round(sumRow[fixlen +
              tempIndex.intValue()], dec);
        }
        yehj += lmye;
      }
    }
    //最后一行列合计
    if (hjTag != 0) {
      if (rowData != null) {
        rowData[hjindex] = Double.toString(yehj);
      }
    }
    //添加合计行
    int tag = hjTag == -1 ? 1 : 0;
    //2008-8-15 fengbo 修改原来对合计列在后面的支持bug，避免对合计列自身的合计
    int tag2 = hjTag == 1 ? 1 : 0;
    if (isSumRow) {
      String[] tempRow = new String[len];
      tempRow[0] = "合计";
      for (int i = 1; i < (len - tag2); i++) {
        //固定列-不做合计
        if (i < fixlen - tag) {
          tempRow[i] = "";
        }
        //变动列-数值型
        else {
          tempRow[i] = Double.toString(sumRow[i]);
          if (hjTag != 0) {
            sumRow[hjindex] += sumRow[i];
            sumRow[hjindex] = JFNumber.round(sumRow[hjindex], dec);

          }
        }
      }
      if (hjTag != 0) {
        tempRow[hjindex] = Double.toString(sumRow[hjindex]);
      }
      dataVector.add(tempRow);
    }
    formatData(PO, isLeveled, stru, viewID, dataVector, dataObject);
    return dataObject.getFormatedDate();
  }

  /**
   * 增强的二维余额表。
   * @param PO JParamObject
   * @param isLeveled boolean     是否分级
   * @param stru String           编码结构
   * @param viewID String[]       显示列标题数组
   * @param cids String[]         固定列字段名数组
   * @param hbbz String[]         行合并标准字段名数组
   * @param lmbhColumn String     栏目编号字段名
   * @param lmDataColumn String[] 栏目数据字段名数组
   * @param lmIndexMap Map        (栏目,序号)对应关系
   * @param hjTag int             合计标志: 0 不增加合计列;-1 增加各栏目合计列且位置在固定列之前;1 增加各栏目合计列且位置在固定列之后;
   * @param isSumRow boolean      是否增加合计行
   * @param rowFilterExp String   若增加合计行，提供行过滤表达式，null 或 "" 表示 无条件，所有行均参加合计
   *                              参与合计的列为：所有变动栏目。
   * @param rs ResultSet
   * @throws Exception
   */
  public static String formatData(JParamObject PO,
                                  boolean isLeveled,
                                  String stru,
                                  String[] viewID,
                                  String[] cids,
                                  String[] hbbz,
                                  String lmbhColumn,
                                  String[] lmDataColumn,
                                  Map lmIndexMap,
                                  int hjTag,
                                  boolean isSumRow,
                                  String rowFilterExp,
                                  int[] dec,
                                  ResultSet rs,
                                  FormatDataManager dataObject) throws
      Exception {
    Vector dataVector = new Vector();
    //计算cids的有效长度
    int fixlen = 0;
    for (int i = 0; i < cids.length; i++) {
      if (cids[i] != null && !cids[i].equals("")) {
        fixlen++;
      }
    }
    //编号 名称 [合计]，[...变动列]
    int len = viewID.length;

    //计算合计列的起始位置
    int hjindex = -1;
    if (hjTag == -1) {
      hjindex = fixlen;
      //固定列+合计列
      //fixlen += 1;
    }
    else if (hjTag == 1) {
      hjindex = len - lmDataColumn.length;
    }

    //初始化合计行数据
    double[] sumRow = new double[len];
    for (int i = 0; i < sumRow.length; i++) {
      sumRow[i] = 0;
    }

    String[] oldhbbzData = new String[hbbz.length];
    String[] hbbzData = new String[hbbz.length];
    String[] rowData = null;
    String lmbh;
    double lmye;
    String temp;
    Integer tempIndex;
    //初始化列合计数组
    double[] yehj = new double[lmDataColumn.length];
    for (int i = 0; i < yehj.length; i++) {
      yehj[i] = 0;
    }
    while (rs.next()) {
      //取得合并依据数据
      for (int i = 0; i < hbbz.length; i++) {
        hbbzData[i] = rs.getString(hbbz[i]);
      }
      //新增一行
      if (isNewRow(hbbzData, oldhbbzData)) {
        // 合计前一行
        if (hjTag != 0) {
          if (rowData != null) {
            for (int i = 0; i < lmDataColumn.length; i++) {
              rowData[hjindex + i] = Double.toString(yehj[i]);
            }
          }
        }

        //清空列合计
        for (int i = 0; i < yehj.length; i++) {
          yehj[i] = 0;
        }
        //新增一行
        rowData = new String[len];
        for (int i = 0; i < rowData.length; i++) {
          rowData[i] = "0";
        }
        //固定列数据
        for (int i = 0; i < cids.length; i++) {
          if (cids[i] != null && !cids[i].equals("")) {
            //rowData[i] = rs.getString(cids[i]);
            temp = rs.getString(cids[i]);
            if (temp == null) {
              temp = "";
            }
            rowData[i] = temp;
          }
        }
        dataVector.add(rowData);
      }
      //传递数据
      for (int i = 0; i < hbbzData.length; i++) {
        oldhbbzData[i] = hbbzData[i];
      }
      //变动列数据：栏目编号/栏目余额
      lmbh = rs.getString(lmbhColumn);
      //定位栏目,赋值
      tempIndex = (Integer) lmIndexMap.get(lmbh);
      if (tempIndex != null) {
        int lmIndex = tempIndex.intValue();
        //取出多个数据值
        for (int i = 0; i < lmDataColumn.length; i++) {
          lmye = rs.getDouble(lmDataColumn[i]);
          //计算该栏目的开始位置
          int index = fixlen + (hjTag == -1 ? lmDataColumn.length : 0) +
              lmIndex * lmDataColumn.length + i;
          rowData[index] = Double.toString(lmye);
          //计入合计行
          if (isSumRow && isAccepted(lmIndexMap, rowData, rowFilterExp)) {
            sumRow[index] += lmye;
            sumRow[index] = JFNumber.round(sumRow[index], dec[i]);
          }
          //计算列合计
          yehj[i] += lmye;
        }
      }
    }
    //最后一行列合计
    if (hjTag != 0) {
      if (rowData != null) {
        for (int i = 0; i < lmDataColumn.length; i++) {
          rowData[hjindex + i] = Double.toString(yehj[i]);
        }
      }
    }
    //添加合计行
    //int tag = hjTag == -1 ? 1 : 0;
    if (isSumRow) {
      String[] tempRow = new String[len];
      tempRow[0] = "合计";
      for (int i = 1; i < len; i++) {
        //固定列-不做合计
        if (i < fixlen) {
          tempRow[i] = "";
        }
        //变动列-数值型
        else {
          tempRow[i] = Double.toString(sumRow[i]);
          if (hjTag != 0) {
            for (int j = 0; j < lmDataColumn.length; j++) {
              sumRow[hjindex + j] += sumRow[i + j];
              sumRow[hjindex + j] = JFNumber.round(sumRow[hjindex + j], dec[j]);
            }
          }
        }
      }
      if (hjTag != 0) {
        for (int j = 0; j < lmDataColumn.length; j++) {
          tempRow[hjindex + j] = Double.toString(sumRow[hjindex + j]);
        }
      }
      dataVector.add(tempRow);
    }
    formatData(PO, isLeveled, stru, viewID, dataVector, dataObject);
    return dataObject.getFormatedDate();
  }

  /**
   * 判断是否开始新一行
   * @param hbbzData String[]
   * @param oldhbbzData String[]
   * @return boolean
   */
  private static boolean isNewRow(String[] hbbzData, String[] oldhbbzData) {
    //长度为零，表示不合并
    if (hbbzData.length == 0) {
      return true;
    }
    for (int i = 0; i < hbbzData.length; i++) {
      if (!hbbzData[i].equals(oldhbbzData[i])) {
        return true;
      }
    }
    return false;
  }

  private static boolean isAccepted(Map indexMap, String[] rowData,
                                    String expression) throws Exception {
    if (expression == null || expression.equals("")) {
      return true;
    }
    Iterator iterator = indexMap.keySet().iterator();
    String key;
    int index;
    int position;
    //替换表达式中的列id为具体数据
    while (iterator.hasNext()) {
      key = (String) iterator.next();
      index = ( (Integer) indexMap.get(key)).intValue();
      position = expression.indexOf(key);
      if (position > -1 && !key.equals("")) {
        expression = expression.replaceAll(key, "\"" + rowData[index] + "\"");
      }
    }
    //计算表达式
    JEP mJepParser = new JEP();
    mJepParser.parseExpression(expression);
    Object obj = mJepParser.getValueAsObject();
    if (obj != null && obj instanceof Double) {
      return ( (Double) obj).doubleValue() == 1;
    }
    throw new Exception("表达式: " + expression + " 解析异常！");
  }

  public static String formatData(JParamObject PO,
                                  String[] viewId,
                                  String[] fixColumnId,
                                  String[] jfLM,
                                  String[] dfLM,
                                  String[] mergeColumnID,
                                  String format,
                                  int[] dec,
                                  double[][] lmYearSum,
                                  double[] ye,
                                  String lmbhColumnID,
                                  String[] lmDataColumnID,
                                  String fxColumnID,
                                  String preMonth,
                                  ResultSet rs,
                                  FormatDataManager dataObject) throws
      Exception {
    Vector vector = builderMultiLedger(viewId, fixColumnId, jfLM, dfLM,
                                       mergeColumnID,
                                       format, dec, lmYearSum, ye,
                                       lmbhColumnID, lmDataColumnID,
                                       fxColumnID,
                                       preMonth, rs);
    formatData(PO, false, "", viewId, vector, dataObject);
    return dataObject.getFormatedDate();
  }

  /**
   *
   * @param viewId String[]           显示列标题数组,即显示格式中,每列的id
   * @param fixColumnId String[]      固定列字段名数组
   * @param jfLM String[]             借方栏目编号数组
   * @param dfLM String[]             贷方栏目编号数组
   * @param mergeColumnID String[]    行合并标准字段名数组
   * @param format String             金额，数量，外币账式，111
   * @param dec int[]                 金额，数量，外币精度
   * @param lmYearSum double[][]      [金额，数量，外币][借方栏目+贷方栏目] 的期初本年累计发生额
   * @param ye double[]               金额，数量，外币余额
   * @param lmbhColumnID String       栏目编号字段名
   * @param lmDataColumnID String[]   [金额，数量，外币]栏目数据字段名数组
   * @param fxColumnID String         发生额方向列字段名
   * @param preMonth String           期初月份
   * @param rs ResultSet              原始账页ResultSet
   * @return Vector                   数据集
   * @throws Exception
   */
  public static Vector builderMultiLedger(String[] viewId,
                                          String[] fixColumnId,
                                          String[] jfLM,
                                          String[] dfLM,
                                          String[] mergeColumnID,
                                          String format,
                                          int[] dec,
                                          double[][] lmYearSum,
                                          double[] ye,
                                          String lmbhColumnID,
                                          String[] lmDataColumnID,
                                          String fxColumnID,
                                          String preMonth,
                                          ResultSet rs) throws Exception {
    //最终结果数据集
    Vector dataVector = new Vector();
    //金额，数量，外币标示
    int jeTag = format.charAt(0) - 48;
    int slTag = format.charAt(1) - 48;
    int wbTag = format.charAt(2) - 48;
    //固定列长度= （月，日，凭证编号，摘要）
    int fixLen = fixColumnId.length;
    //借/贷方栏目合计标示,有栏目，才有合计
    int jfhjTag = jfLM.length > 0 ? 1 : 0;
    int dfhjTag = dfLM.length > 0 ? 1 : 0;
    //栏目总长度=借方栏目长度+贷方栏目长度+（借,贷）合计栏目
    int lmLen = jfLM.length + dfLM.length + jfhjTag + dfhjTag;
    //数据数组总长度=（固定列长度+方向）+(栏目总长度+余额)*（金额标示+数量标示+外币标示）
    int totalLen = (fixLen + 1) + (lmLen + 1) * (jeTag + slTag + wbTag);
    //借方合计在数组中的下标，在固定列之后
    int jfhjIndex = fixLen;
    //贷方合计在数组中的下标：在所有栏目之后，‘方向’前面位置
    int dfhjIndex = fixLen +
        (jfLM.length + dfLM.length + jfhjTag) * (jeTag + slTag + wbTag);
    //余额方向在数组中的下标: 在'贷方合计'之后
    int yeFxIndex = fixLen +
        (jfLM.length + dfLM.length + jfhjTag + dfhjTag) *
        (jeTag + slTag + wbTag);

    //本月合计二维数组,维度与本年累计相同
    double[][] lmMonthSum = new double[lmYearSum.length][lmYearSum[0].length];
    for (int i = 0; i < lmMonthSum.length; i++) {
      for (int j = 0; j < lmMonthSum[0].length; j++) {
        lmMonthSum[i][j] = 0;
      }
    }
    //发生额:金额[，数量，外币]
    double[] data = new double[lmDataColumnID.length];
    for (int i = 0; i < data.length; i++) {
      data[i] = 0;
    }
    //固定列数据
    String[] tempRow = new String[fixColumnId.length];
    for (int i = 0; i < tempRow.length; i++) {
      tempRow[i] = "";
    }
    //合并依据数据
    String[] merge = new String[mergeColumnID.length];
    for (int i = 0; i < merge.length; i++) {
      merge[i] = "";
    }
    //一组临时变量
    int index;
    int jzfx;
    int lmfx;
    int dataIndex;
    int pzBeginRowIndex = -1;
    String tempData;
    String month = null;
    String tempMonth;
    //上期结转
    tempRow[0] = preMonth;
    tempRow[tempRow.length - 1] = "上期结转";
    addDataRow(dataVector, jeTag, slTag, wbTag,
               jfhjIndex, dfhjIndex, yeFxIndex, totalLen,
               pzBeginRowIndex, fixLen, 1,
               merge, tempRow, data, ye);
    //数据集
    try {
      while (rs.next()) {
        tempMonth = rs.getString(1);
        if (month != null && tempMonth.compareTo(month) > 0) {
          //添加本月累计
          addSumRow(dataVector, jfhjTag, totalLen, jfLM.length, fixLen,
                    jeTag, slTag, wbTag,
                    jfhjIndex, dfhjIndex, yeFxIndex,
                    lmMonthSum, month, "本期合计",
                    merge, tempRow, data, ye);
          //计算本年累计发生额=上期本年累计发生额+本月累计发生额
          for (int i = 0; i < lmYearSum.length; i++) {
            for (int j = 0; j < lmYearSum[0].length; j++) {
              lmYearSum[i][j] += lmMonthSum[i][j];
              lmYearSum[i][j] = JFNumber.round(lmYearSum[i][j], dec[i]);
            }
          }
          //本年累计行
          addSumRow(dataVector, jfhjTag, totalLen, jfLM.length, fixLen,
                    jeTag, slTag, wbTag,
                    jfhjIndex, dfhjIndex, yeFxIndex,
                    lmYearSum, month, "本年累计",
                    merge, tempRow, data, ye);
          //本月合计清零
          for (int i = 0; i < lmMonthSum.length; i++) {
            for (int j = 0; j < lmMonthSum[0].length; j++) {
              lmMonthSum[i][j] = 0;
            }
          }
        }
        month = tempMonth;
        //栏目编号-->栏目序号
        index = getLMIndex(jfLM, dfLM, rs.getString(lmbhColumnID));
        if (index > -1) {
          //栏目方向
          lmfx = index < jfLM.length ? 1 : -1;
          //固定列数据
          for (int i = 0; i < fixColumnId.length; i++) {
            tempData = rs.getString(fixColumnId[i]);
            tempRow[i] = tempData != null ? tempData : "";
          }
          //合并依据数据
          for (int i = 0; i < mergeColumnID.length; i++) {
            tempData = rs.getString(mergeColumnID[i]);
            merge[i] = tempData != null ? tempData : "";
          }
          //记帐方向
          jzfx = rs.getString(fxColumnID).equals("J") ? 1 : -1;
          //栏目数据：金额[，数量，外币]
          for (int i = 0; i < lmDataColumnID.length; i++) {
            data[i] = rs.getDouble(lmDataColumnID[i]) * jzfx;
            //计算余额
            ye[i] += data[i];
            //本月累计发生额
            lmMonthSum[i][index] += data[i];
            lmMonthSum[i][index] = JFNumber.round(lmMonthSum[i][index], dec[i]);
          }
          //放入数据集
          //计算下标=月/日/凭证编号/摘要（4个）+ 借方合计栏目(0/1)
          dataIndex = fixLen + (jfhjTag + index) * (jeTag + slTag + wbTag);
          pzBeginRowIndex = addDataRow(dataVector, jeTag, slTag, wbTag,
                                       jfhjIndex, dfhjIndex, yeFxIndex,
                                       totalLen,
                                       pzBeginRowIndex, dataIndex, lmfx,
                                       merge, tempRow, data, ye);
        }
      }
      if (month == null) {
        month = preMonth;
      }
      //添加本月累计
      addSumRow(dataVector, jfhjTag, totalLen, jfLM.length, fixLen,
                jeTag, slTag, wbTag,
                jfhjIndex, dfhjIndex, yeFxIndex,
                lmMonthSum, month, "本期合计",
                merge, tempRow, data, ye);
      //本年累计行
      for (int i = 0; i < (jeTag + slTag + wbTag); i++) {
        for (int j = 0; j < lmYearSum[0].length; j++) {
          lmYearSum[i][j] += lmMonthSum[i][j];
          lmYearSum[i][j] = JFNumber.round(lmYearSum[i][j], dec[i]);
        }
      }
      addSumRow(dataVector, jfhjTag, totalLen, jfLM.length, fixLen,
                jeTag, slTag, wbTag,
                jfhjIndex, dfhjIndex, yeFxIndex,
                lmYearSum, month, "本年累计",
                merge, tempRow, data, ye);
    }
    finally {
      rs.close();
    }
    return dataVector;
  }

  private static int addSumRow(Vector dataVector,
                               int jfhjTag,
                               int totalLen,
                               int jfLMLen,
                               int fixLen,
                               int jeTag,
                               int slTag,
                               int wbTag,
                               int jfhjIndex,
                               int dfhjIndex,
                               int yeFxIndex,
                               double[][] lmMonthSum,
                               String month,
                               String jy,
                               String[] merge,
                               String[] tempRow,
                               double[] data,
                               double[] ye) {
    int lmfx;
    int dataIndex;
    //合计需要开始新行
    int pzBeginRowIndex = -1;
    for (int i = 0; i < lmMonthSum[0].length; i++) {
      //栏目方向
      lmfx = i < jfLMLen ? 1 : -1;
      //合并依据
      merge[0] = month;
      for (int k = 1; k < merge.length - 1; k++) {
        merge[k] = "";
      }
      merge[merge.length - 1] = jy;
      //固定列数据
      tempRow[0] = month;
      for (int k = 1; k < tempRow.length - 1; k++) {
        tempRow[k] = "";
      }
      tempRow[tempRow.length - 1] = jy;
      //变动数据
      for (int k = 0; k < jeTag + slTag + wbTag; k++) {
        data[k] = lmMonthSum[k][i];
      }
      dataIndex = fixLen + (jfhjTag + i) * (jeTag + slTag + wbTag);
      pzBeginRowIndex = addDataRow(dataVector, jeTag, slTag, wbTag,
                                   jfhjIndex, dfhjIndex, yeFxIndex, totalLen,
                                   pzBeginRowIndex, dataIndex, lmfx,
                                   merge, tempRow, data, ye);
    }
    return -1;
  }

  /**
   *
   * @param dataVector Vector     数据集
   * @param jfhjIndex int         借方合计在数组中的列下标
   * @param dfhjIndex int         贷方合计在数组中的列下标
   * @param yeFxIndex int         余额方向在数组中的列下标
   * @param totalLen int          数组总长度
   * @param pzBeginRowIndex int   凭证在数据集中的开始行下标
   * @param dataIndex int         数据在数组中的列下标
   * @param lmfx int              数据发生栏目的方向,借方:1;贷方:-1
   * @param merge String[]        行合并数据
   * @param tempRow String[]      固定列数据
   * @param data double[]         发生数据
   * @param ye double[]           当前余额
   * @return int
   */
  private static int addDataRow(Vector dataVector,
                                int jeTag, int slTag, int wbTag,
                                int jfhjIndex, int dfhjIndex,
                                int yeFxIndex, int totalLen,
                                int pzBeginRowIndex, int dataIndex, int lmfx,
                                String[] merge, String[] tempRow,
                                double[] data, double[] ye) {

    String[] rowdata;
    //新凭证
    int rowIndex = -1;
    boolean isNewPZ = true;
    if (pzBeginRowIndex != -1) {
      isNewPZ = isNewPZ( (String[]) dataVector.get(dataVector.size() - 1),
                        merge);
    }
    if (isNewPZ) {
      rowIndex = -1;
    }
    else {
      rowIndex = getRowIndex(dataVector, pzBeginRowIndex, dataIndex);
    }

    //开始新行
    if (rowIndex == -1) {
      rowdata = new String[totalLen];
      for (int i = 0; i < rowdata.length; i++) {
        rowdata[i] = "0";
      }
      dataVector.add(rowdata);
      //固定列数据
      for (int i = 0; i < tempRow.length; i++) {
        rowdata[i] = tempRow[i];
      }
    }
    else {
      rowdata = (String[]) dataVector.get(rowIndex);
    }
    //赋值
    for (int i = 0; i < jeTag + slTag + wbTag; i++) {
      rowdata[dataIndex + i] = Double.toString(data[i] * lmfx);
    }
    //借/贷方合计
    int position = lmfx == 1 ? jfhjIndex : dfhjIndex;
    for (int i = 0; i < jeTag + slTag + wbTag; i++) {
      rowdata[position +
          i] = Double.toString(Double.parseDouble(rowdata[position + i]) +
                               data[i] * lmfx);
    }
    //余额方向
    position = yeFxIndex;
    rowdata[position] = ye[0] > 0 ? "借" : ye[0] < 0 ? "贷" : "平";
    //余额
    position = yeFxIndex + 1;
    int tag = ye[0] < 0 ? -1 : 1;
    rowdata[position] = Double.toString(ye[0] * tag);
    for (int i = 1; i < jeTag + slTag + wbTag; i++) {
      //金额为负值时,数量，外币乘以-1调整
      rowdata[position + i] = Double.toString(ye[i] * tag);
    }
    //凭证的起始行序号
    if (isNewPZ) {
      return dataVector.size() - 1;
    }
    else {
      return pzBeginRowIndex;
    }
  }

  /**
   * 是否开始一张新凭证
   * @param preRow String[]
   * @param merge String[]
   * @return boolean
   */
  private static boolean isNewPZ(String[] preRow, String[] merge) {
    //合无并数据，则认为不合并
    if (merge.length == 0) {
      return true;
    }
    for (int i = 0; i < merge.length; i++) {
      if (merge[i] != null && !merge[i].equals(preRow[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * 从凭证起始行下标开始，定位数据行下标
   * @param dataVector Vector 数据集
   * @param beginRow int      凭证起始行下标
   * @param index int         数据列下标
   * @return int              返回-1，表示没有位置，需要开始新行
   */
  private static int getRowIndex(Vector dataVector, int beginRow, int index) {
    if (beginRow < 0) {
      return -1;
    }
    String[] array;
    for (int i = beginRow; i < dataVector.size(); i++) {
      array = (String[]) dataVector.get(i);
      //有空位
      if (array[index].equals("0")) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 匹配栏目
   * @param jfLM String[]  借方栏目数组
   * @param dfLM String[]  贷方栏目数组
   * @param lmbh String    栏目编号
   * @return int
   */
  private static int getLMIndex(String[] jfLM, String[] dfLM, String lmbh) {
    for (int i = 0; i < jfLM.length; i++) {
      if (lmbh.startsWith(jfLM[i])) {
        return i;
      }
    }
    for (int i = 0; i < dfLM.length; i++) {
      if (lmbh.startsWith(jfLM[i])) {
        return jfLM.length + i;
      }
    }
    return -1;
  }

  /**
   * 统一的数据输出方法，根据数据集的大小确定写 文件/字符串
   * @param cids String
   * @param dataVector Vector
   * @return String
   * @throws Exception
   */
  public static void formatData(JParamObject PO,
                                String[] cids,
                                Vector dataVector,
                                FormatDataManager dataObject) throws Exception {
    formatData(PO, false, "", cids, dataVector, dataObject);
  }

  /**
   * 统一的数据输出方法，根据数据集的大小确定写 文件/字符串
   * @param cids String
   * @param dataVector Vector
   * @return String
   * @throws Exception
   */
  public static void formatData(JParamObject PO,
                                boolean isClass,
                                String stru,
                                String[] cids,
                                Vector dataVector,
                                FormatDataManager dataObject) throws Exception {
    //取写文件的行阀值
    ROW_VALVE = Integer.parseInt(PO.GetValueByEnvName("ZW_QUERYVALVE", "2000"));
    //新建输出对象
    if (dataObject.getOutObject() == null) {
      if (dataVector.size() >= ROW_VALVE) {
        formatDataToFile(PO, isClass, stru, cids, dataVector, dataObject);
      }
      else {
        formatDataToStringBuffer(PO, isClass, stru, cids, dataVector,
                                 dataObject);
      }
    }
    //向输出对象追加数据
    else {
      appendFormatData(PO, dataObject, dataVector);
    }

  }

  /**
   * 输出到字符串
   * @param cids String[]
   * @param dataVector Vector
   * @return String 返回数据集字符串
   * @throws Exception
   */
  private static void formatDataToStringBuffer(JParamObject PO,
                                               String[] cids, Vector dataVector,
                                               FormatDataManager dataObject) throws
      Exception {

    formatDataToStringBuffer(PO, false, "", cids, dataVector,
                             dataObject);
  }

  /**
   * 输出到字符串
   * @param isClass boolean  数据是否分级
   * @param stru String      编码结构
   * @param cids String[]    与格式文件一致的列标题数组
   * @param dataVector Vector数据集
   * @return String          返回数据集字符串
   * @throws Exception
   */
  private static void formatDataToStringBuffer(JParamObject PO,
                                               boolean isClass,
                                               String stru,
                                               String[] cids,
                                               Vector dataVector,
                                               FormatDataManager dataObject) throws
      Exception {

    long s = System.currentTimeMillis();
    StringBuffer buffer = getContentBuffer(PO, isClass, stru, cids, dataVector);
    dataObject.getFormatedBufferDate().append(buffer);
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("构造数据字符串，耗时：" + (e - s) + " 毫秒");
  }

  /**
   * 输出数据到文本文件.
   * @param cids String[]     列标题数组
   * @param dataVector Vector 数据集合
   * @throws Exception        文件I/O异常或其他异常
   */
  private static void formatDataToFile(JParamObject PO,
                                       String[] cids,
                                       Vector dataVector,
                                       FormatDataManager dataObject) throws
      Exception {
    formatDataToFile(PO, false, "", cids, dataVector, dataObject);
  }

  /**
   * 输出数据到文本文件.
   * @param isClass boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String       分级列编码结构,譬如 44444
   * @param cids String[]     与格式文件一致的列标题数组
   * @param dataVector Vector 数据集合
   * @throws Exception        文件I/O异常或其他异常
   */
  private static void formatDataToFile(JParamObject PO,
                                       boolean isClass,
                                       String stru,
                                       String[] cids,
                                       Vector dataVector,
                                       FormatDataManager dataObject) throws
      Exception {
    StringBuffer buffer = getContentBuffer(PO, isClass, stru, cids, dataVector);
    byte[] bys = buffer.toString().getBytes("UTF-8");

    QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,false);

  }

  /**
   * 向已经存在的文件添加数据
   * @param fileName String   文件名
   * @param dataVector Vector 数据集
   * @param closed   boolean  是否关闭输出对象
   */
  private static void appendFormatData(JParamObject PO,
                                       FormatDataManager dataObject,
                                       Vector dataVector) throws Exception {
    //追加数据
    long s = System.currentTimeMillis();
    String[] array;
    StringBuffer buffer = new StringBuffer(BUFFER_INIT_SIZE);
    for (int i = 0; i < dataVector.size(); i++) {
      array = (String[]) dataVector.get(i);
      for (int j = 0; j < array.length; j++) {
        buffer.append(array[j] + "\t");
      }
      buffer.append("\r\n");
    }
    byte[] bys = buffer.toString().getBytes("UTF-8");

    QueryCacheManager.getDefault().saveQueryResult("",dataObject,bys,dataVector.size());

    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("追加 " + dataVector.size() + " 条数据到输出对象，耗时：" +
                                 (e - s) + " 毫秒");
  }

  /**
   * 公用的组织数据方法
   */
  private static StringBuffer getContentBuffer(JParamObject PO,
                                               boolean isClass,
                                               String stru,
                                               String[] cids,
                                               Vector dataVector) {
    StringBuffer buffer = new StringBuffer(BUFFER_INIT_SIZE);
    Object[] array;
    //头信息
//    buffer.append("TAG=" + (isClass ? "1" : "0"));
    //头信息 2008-1-3 fengbo
    String tempTag = "";
    String classTag = PO.GetValueByParamName(QueryDataTransformer.
                                             PART_BH_CLASS_NOTE, "");
    //数据不分级
    if (!isClass) {
      tempTag = QueryDataTransformer.NO_CLASS_Tag;
    }
    else {
      if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag)) {
        tempTag = QueryDataTransformer.PART_BH_CLASS_Tag;
      }
      else {
        tempTag = QueryDataTransformer.ALL_BH_CLASS_Tag;
      }
    }
    buffer.append("TAG=" + tempTag);
    buffer.append("\r\n");
    buffer.append("STRU=" + stru);
    buffer.append("\r\n");
    for (int i = 0; i < cids.length; i++) {
      buffer.append(cids[i]);
      buffer.append("\t");
    }
    buffer.append("\r\n");
    //数据
    for (int i = 0; i < dataVector.size(); i++) {
      array = (Object[]) dataVector.get(i);
      for (int j = 0; j < array.length; j++) {
        buffer.append(array[j] + "\t");
      }
      buffer.append("\r\n");
    }
    return buffer;
  }


  /**
   * 返回唯一临时文件名
   * @return String
   */
  private synchronized static String getUUIDFileName() {
    java.util.Date date = new java.util.Date();
    return generateUUID() + "_" + date.getTime();
  }

  /* 该方法用来产生一个简化的32位的唯一标记
   * @return String
   */
  private static String generateUUID() {
    StringBuffer uid = new StringBuffer(32);
    long currentTimeMillis = System.currentTimeMillis();
    uid.append(toHex( (int) (currentTimeMillis & -1L), 8));
    SecureRandom seeder = new SecureRandom();
    seeder.nextInt();
    uid.append(toHex(seeder.nextInt(), 8));
    return uid.toString();
  }

  private static String toHex(int value, int length) {
    char hexDigits[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    StringBuffer buffer = new StringBuffer(length);
    int shift = length - 1 << 2;
    for (int i = -1; ++i < length; ) {
      buffer.append(hexDigits[value >> shift & 0xf]);
      value <<= 4;
    }
    return buffer.toString();
  }

  public static void main1(String[] args) {
    ResultSet rs = null;
    Connection con = null;
    try {
      QueryDataOutObject a = new QueryDataOutObject();
      String url = "jdbc:sybase:Tds:10.122.80.63:7755/cwbase2?charset=cp850";
      String drivers = "com.sybase.jdbc2.jdbc.SybDriver";
      if (drivers != null) {
        System.setProperty("jdbc.drivers", drivers);
      }
      String username = "sa";
      String password = "";
      con = DriverManager.getConnection(url, username, password);
      Statement stmt = con.createStatement();
      String sql = "select F_BMBH from ACBMZD"; //"select F_VKEY,F_VAL from LSCONF";
      rs = stmt.executeQuery(sql);
      String[] cids = new String[1];
      cids[0] = "F_BMBH";
      Debug.PrintlnMessageToSystem(QueryDataOutObject.formatData(null, cids,
          cids, rs));

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        con.close();
      }
      catch (SQLException e) {
      }
    }
  }

  public static void main(String[] args) {
    JEP mJepParser = new JEP();
//    Map map = new HashMap();
//    map.put("c001", new Integer(1));
//    map.put("c002", new Integer(2));
//    map.put("c003", new Integer(3));
//    map.put("c004", new Integer(4));
//    map.put("c005", new Integer(5));
//    String exp = "(c002+c003)*(c004-c005)";
//    String[] rowData = {
//        "1200", "", "20", "30", "40", "50"};
//    exp = translateExpression(map, exp, rowData);
//    Debug.PrintlnMessageToSystem(exp);
    String exp = "3++"; //"\"aaa\"==\"aaa\"";
    mJepParser.parseExpression(exp);
    double d = mJepParser.getValue();
    Debug.PrintlnMessageToSystem(d);
  }
}

class Empty {
}
