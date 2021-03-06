package jdatareport.dof.classes.report;

import java.io.FileInputStream;
import java.net.URL;
import java.io.StringReader;
import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.pub.util.Debug;
import java.util.zip.GZIPInputStream;
import com.report.table.jxml.TreeTableDataManager;
import org.jdom.Element;
import java.util.List;
import org.jdom.Attribute;
import java.io.File;
import java.io.FileWriter;
import jservice.jdof.ssl.JSSLConnectionManager;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import com.pub.util.*;
import jframework.foundation.classes.JActiveDComDM;
import java.util.zip.*;

/**
 * 根据文本串、文本文件、URL 封装查询数据集合。
 * [约定]：数据结点 node“数组中元素的顺序”与“数据展示顺序即tablemanager中viewcol中各元素的顺序”一致。
 * 测试结果：
 * 构造5万条数据耗时约 1270 毫秒，是否分级影响不明显。
 * 构造10万条数据造成内存益处。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QueryDataTransformer {

  /**
   * 分页标示符
   */
  public static String PageTag = "-ENDPAGE-";
  public static String NO_CLASS_Tag = "0";
  public static String ALL_BH_CLASS_Tag = "1";
  /**
   * '-' 之前的编号参与分级
   */
  public static String PART_BH_CLASS_NOTE = "_PART_BH_CLASS_NOTE_";
  public static String PART_BH_CLASS_Tag = "2";

  public QueryDataTransformer() {
  }

  /**
   * 根据文本URL封装查询数据集合。
   * @param url URL ，譬如：http://10.122.80.181:8080/EAIServer/Query/Query.txt
   * @return Vector   返回封装好的数据集合
   * @throws Exception
   */
  public static QueryDataSet getQueryNodeFromTXTURL(String[] cidArray, URL url) throws
      Exception {
    long s = System.currentTimeMillis();
    InputStream in = url.openStream();
    InputStreamReader reader = new InputStreamReader(in);
    BufferedReader bufReader = new BufferedReader(reader);
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("建立Http连接耗时：" + (e - s) + " 毫秒");
    return getQueryNodeFromBufferReader(cidArray, bufReader);
  }

  /**
   * 根据文本封装查询数据集合。
   * @param fileName String
   * @return Vector
   * @throws Exception
   */
  public static QueryDataSet getQueryNodeFromTXTFile(String[] cidArray,
      String fileName) throws Exception {
    FileReader fileReader = new FileReader(fileName);
    BufferedReader bufReader = new BufferedReader(fileReader);
    return getQueryNodeFromBufferReader(cidArray, bufReader);
  }

  /**
   * 根据文本字符串封装查询数据集合。
   * @param data String
   * @return Vector
   * @throws Exception
   */
  public static QueryDataSet getQueryNodeFromTXTString(String[] cidArray,
      String data) throws Exception {
    StringReader reader = new StringReader(data);
    BufferedReader bufReader = new BufferedReader(reader);
    return getQueryNodeFromBufferReader(cidArray, bufReader);
  }

  /**
   * 封装查询数据集合
   * @param cids String[]  显示列id数组
   * @param url URL
   * @throws Exception
   */
  public static QueryDataSet getQueryNodeFromZIPURL(String[] cidArray, URL url) throws
      Exception {
    return getQueryNodeFromZIPURL(cidArray, url, false);
  }

  /**
   * 封装查询数据集合
   * @param cids String[]    显示列id数组
   * @param url URL
   * @param bufferd boolean  是否本地缓存该文件
   * @throws Exception
   */
  public static QueryDataSet getQueryNodeFromZIPURL(String[] cidArray, URL url,
      boolean bufferd) throws
      Exception {
    long s = System.currentTimeMillis();
    //打开GZIP文件，创建本地TXT文件
    if (bufferd) {
      String fileName = "temp";
      int totalRowCount = copyToTxTFile(url, fileName);
      QueryDataSet queryDataSet = new QueryDataSet();
      queryDataSet.setCidArray(cidArray);
      queryDataSet.setFileName(fileName);
      queryDataSet.setTotalRowCount(totalRowCount);
      return queryDataSet;
    }
    else {
      InputStream in = getInputStream(url);
      GZIPInputStream zipInputStream = new GZIPInputStream(in);
      InputStreamReader inputStreamReader = new InputStreamReader(
          zipInputStream, "UTF-8");
      BufferedReader bufReader = new BufferedReader(inputStreamReader);
      return getQueryNodeFromBufferReader(cidArray, bufReader);
    }
  }
  /**
   * 打开以Memcached方式缓存的文件
   * @param cidArray String[]
   * @param memkey String
   * @param bufferd boolean
   * @return QueryDataSet
   * @throws Exception
   */
  public static QueryDataSet getQueryNodeFromZIPDAL(String[] cidArray, String memkey) throws
        Exception {
      long s = System.currentTimeMillis();
      //打开GZIP文件，创建本地TXT文件
//      if (bufferd) {
//        String fileName = "temp";
//        int totalRowCount = copyToTxTFile(url, fileName);
//        QueryDataSet queryDataSet = new QueryDataSet();
//        queryDataSet.setCidArray(cidArray);
//        queryDataSet.setFileName(fileName);
//        queryDataSet.setTotalRowCount(totalRowCount);
//        return queryDataSet;
//      }
//      else {
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("QueryFile",memkey);
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
            InvokeObjectMethod("GenerQueryObject", "GetQueryObject",PO);
        byte[] filedata = (byte[])RO.ResponseObject;
        InputStream in = new ByteArrayInputStream(filedata);
        GZIPInputStream zipInputStream = new GZIPInputStream(in);
        InputStreamReader inputStreamReader = new InputStreamReader(zipInputStream, "UTF-8");
        BufferedReader bufReader = new BufferedReader(inputStreamReader);
        return getQueryNodeFromBufferReader(cidArray, bufReader);
//      }
  }
  private static int copyToTxTFile(URL url, String fileName) throws
      Exception {
    String lineTag = "\r\n";
    //打开新文件
    File file = new File(fileName);
    FileWriter writer = new FileWriter(file);
    InputStream in = getInputStream(url);
    GZIPInputStream zipInputStream = new GZIPInputStream(in);
    InputStreamReader inputStreamReader = new InputStreamReader(
        zipInputStream, "UTF-8");
    BufferedReader bufReader = new BufferedReader(inputStreamReader);
    //分级标示
    String tag = bufReader.readLine();
    //编码结构
    String stru = bufReader.readLine();
    //数据列标题
    String cids = bufReader.readLine();
    //写文件头
    writer.write(tag + lineTag);
    writer.write(stru + lineTag);
    writer.write(cids + lineTag);
    //数据
    String line = bufReader.readLine();
    int count = 0;
    int totalRowCount = 0;
    while (line != null) {
      writer.write(line + lineTag);
      count++;
      totalRowCount++;
      line = bufReader.readLine();
      if (count >= QueryDataSet.MAX_SIZE) {
        //分页标示行
        writer.write(PageTag + lineTag);
        //写文件头
        writer.write(tag + lineTag);
        writer.write(stru + lineTag);
        writer.write(cids + lineTag);
        count = 0;
      }
    }
    writer.close();
    in.close();
    return totalRowCount;
  }

  private static InputStream getInputStream(URL url) {
    String urlString = url.toString();
    try {
      if (urlString.indexOf("https") >= 0) {
        HttpsURLConnection sslConn = (HttpsURLConnection) JSSLConnectionManager.
            getSSLConnection(url);
        sslConn.connect();
        return sslConn.getInputStream();
      }
      else {
        return url.openStream();
      }
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static QueryDataSet getQueryNodeFromBufferReader(String[] cidArray,
      BufferedReader bufReader) throws Exception {
    QueryDataSet queryDataSet = new QueryDataSet();
    long s = System.currentTimeMillis();
    Vector queryNodeVector = new Vector();
    Vector rootVector = new Vector();
    String[] classArray = null;
    String storeCol = "";
    String[] stroeCols = null;
    //分级标志，数据是否分级
    String classTag = bufReader.readLine();
    //判断是否有排序列
    if(classTag.indexOf(";") > -1) {
    	classArray = classTag.split(";");
    	classTag = classArray[0].substring(classArray[0].indexOf("=") + 1);
    	storeCol = classArray[1].substring(classArray[1].indexOf("=") + 1);
    	stroeCols = storeCol.split(",");
    } else {
    	classTag = classTag.substring(classTag.indexOf("=") + 1);
    }
    
    queryDataSet.setClassed(!classTag.equals("0") ? true : false);
    //add 2008-1-3 fengbo
    queryDataSet.setClassTag(classTag);
    //编码结构
    String stru = bufReader.readLine();
    stru = stru.substring(stru.indexOf("=") + 1);
    String[] struArray = stru.split("-");
    queryDataSet.setStruCount(struArray.length);
    Map[] struMap = new Map[struArray.length];
    for (int k = 0; k < struMap.length; k++) {
      struMap[k] = new HashMap();
    }
    //建立（编码长度，级别）对照表
    int totallen = 0;
    for (int k = 0; k < struArray.length; k++) {
      for (int len = 0, i = 0; i < struArray[k].length(); i++) {
        len += StringFunction.GetStructLength(struArray[k].substring(i, i + 1),1);
        struMap[k].put(Integer.toString(len), new Integer(i + 1));
      }
      totallen += struArray[k].length();
    }
    //计算数组，存放行号[1--totallen]
    //兼容编号譬如：1001-11111的情况，此时 stru 的长度小于 1001-11111的级数
    //2007-10-25 modi by fengbo （解决：往来单位属性分多级问题）这个数组长度可以任意放大。
//    int[] array = new int[totallen + 1 + 1];
    int[] array = new int[totallen + 1 + 2];

    //数据列标题
    String[] dataColIDs = bufReader.readLine().split("\t");
    Map cidMap = new HashMap();
    for (int i = 0; i < dataColIDs.length; i++) {
      cidMap.put(dataColIDs[i], new Integer(i));
    }
    //数据行
    String line = bufReader.readLine();
    String[] node = null;
    String[] temp;
    int prejs = 0;
    int js = 0;
    int ijs = 0;
    int iprejs = 0;
    int rowCount = 0;
    int beginJS = 1;
    int tempI;
    Integer integer;
    String[] preNode = {
        "", "", ""};
    while (line != null && !line.equals(PageTag)) {
      //解析行获取需要的数据
      temp = line.split("\t");
      if (temp.length < cidArray.length) {
        temp = extend(temp, cidArray.length);
      }
      //node 存放实际显示数据的数组,并且和显示顺序一致
      node = new String[cidArray.length + 3];
      for (int i = 0; i < cidArray.length; i++) {
        integer = (Integer) cidMap.get(cidArray[i]);
        if (integer != null) {
          if (temp.length > integer.intValue()) {
            node[i] = temp[integer.intValue()];
          }
        }
        else {
          node[i] = "0";
        }
      }
      //序号
      node[node.length - 3] = Integer.toString(rowCount);
      //父结点序号
      node[node.length - 2] = "-1";
      //子孙数目
      node[node.length - 1] = "0";

      //根据数据是否分级，决定后继操作
      if (classTag.equals("0")) {
        queryNodeVector.addElement(node);
        line = bufReader.readLine();
        rowCount++;
        continue;
      }

      // 1. 取出上一个结点，计算当前结点的级数
      if (queryNodeVector.size() > 0) {
        preNode = (String[]) queryNodeVector.get(queryNodeVector.size() - 1);
      }
      js = getBHJS(node, preNode, stroeCols, struMap, classTag);
      ijs = getJSInt(js);
      // 2. 若级数变小，更新父结点的孩子数目
      if (js < prejs) {
        //取出父结点
        if (ijs == 0) {
          ijs = 1;
        }
        iprejs = getJSInt(prejs);
        //
        tempI = Math.max(ijs, beginJS);
        for (int i = tempI; i < iprejs; i++) {
          temp = (String[]) queryNodeVector.get(array[i]);
          temp[node.length - 1] = (array[iprejs] - array[i]) + "";
        }
      }
      // 3. 将当前结点序号放入数组
      array[ijs] = rowCount;

      // 4. 记录父结点序号,若当前结点是根节点，则加入根结点集合
      if (ijs > 1) {
        if (isRootNode(node, rootVector)) {
          rootVector.add(node);
          //记住开始级数
          beginJS = ijs;
        }
        else {
          node[node.length - 2] = array[ijs - 1] + "";
        }
      }
      else {
        rootVector.add(node);
        beginJS = 1;
      }

      // 5. 传递上一个结点的级数
      prejs = js;

      // 6. 将当前结点加入数据集合
      queryNodeVector.addElement(node);
      line = bufReader.readLine();
      rowCount++;
    }

    // 7. 冲出最后一组
    if (prejs > 0) {
      js = 0;
      ijs = getJSInt(js);
      iprejs = getJSInt(prejs);
      //取出父结点
      if (ijs == 0) {
        ijs = 1;
      }
      tempI = Math.max(ijs, beginJS);
      for (int i = tempI; i < iprejs; i++) {
        temp = (String[]) queryNodeVector.get(array[i]);
        temp[node.length - 1] = (array[iprejs] - array[i]) + "";
      }
    }

    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("组织 " + rowCount + " 条数据，耗时：" + (e - s) +
                                 " 毫秒");
    queryDataSet.setDataVector(queryNodeVector);
    queryDataSet.setRootVector(rootVector);
    if (line == null) {
      queryDataSet.setEnd(true);
    }
    else {
      queryDataSet.setEnd(false);
    }
    //print(queryDataSet);
    return queryDataSet;
  }

  //兼容 编号不是从第一级开始，譬如 10020001，100200010002 2007-3-22
  private static boolean isRootNode(String[] node, Vector rootVector) {
    if (rootVector.size() == 0) {
      return true;
    }
    String id = node[0];
    String parentId = ( (String[]) rootVector.get(rootVector.size() - 1))[0];
    //譬如，三个编号相同 都为 11000001；则第一个编号为根（即长兄若父的原则）
    if (id.startsWith(parentId)) { // 若加上这个条件，则兄弟平级 && !id.equals(parentId))
      return false;
    }
    return true;
  }

  private static String[] extend(String[] temp, int len) {
    String[] muchTemp = new String[len];
    int i = 0;
    for (; i < temp.length; i++) {
      muchTemp[i] = temp[i];
    }
    for (; i < len; i++) {
      muchTemp[i] = "";
    }
    return muchTemp;
  }

  /**
   * 为了兼容非规则编号，譬如单位类别结构为 322，单位编号为 2030102-123，201-1001
   * 要根据‘有效编号’长度确定编号级数
   * 有效编号=编号中‘-’之前的部分（‘-’为约定的分隔符，后台在组织数据时必须与前台保持一致）
   */
  private static int getBHJS(String[] node,
                             String[] preNode,
                             String[] stroeCol,
                             Map[] struMap,
                             String classTag) {
    boolean tag = true;
    int js = 0;
    int colIndex = 0;
    Integer temp = null;
    
    //若编号中包含‘-’，则编号中'-'之前的部分参与分级
    if (classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag) && (stroeCol ==  null || stroeCol.length == 0)) {
      int index = node[0].indexOf("-");
      //不含分隔符'-',按多列分级
      if (index < 0) {
        for (int i = 0; i < struMap.length; i++) {
          //判断结点编号是否相同
          if (!node[i].equals(preNode[i])) {
            tag = false;
          }
          if (node[i] != null && !node[i].equals("null")) {
            temp = (Integer) struMap[i].get(Integer.toString(node[i].length()));
          }
          else {
            temp = new Integer(0);
          }
          if (temp != null) {
            js += (temp.intValue()) *
                (int) Math.pow(10, (struMap.length - 1 - i));
          }
        }
      }
      //若含分隔符'-',则认为只按第一列分级
      else {
        //tag=false; //2007-6-11 fengbo
        if (!node[0].equals(preNode[0])) {
          tag = false;
        }
        temp = ( (Integer) struMap[0].get(Integer.toString(node[0].
            substring(0, index).length())));
        //即认为 "01-20020001" 的级数="01"的级数+1
        js = temp.intValue() + 1;
      }
    }
    //修改分级列
    else if(classTag.equals(QueryDataTransformer.PART_BH_CLASS_Tag) && stroeCol !=  null && stroeCol.length > 0) {
    	temp = 0;
    	for(int i = 0; i < struMap.length; i++) {
    		if (!node[Integer.parseInt(stroeCol[i])].equals(preNode[Integer.parseInt(stroeCol[i])])) {
    			tag = false;
      			break;
      	    }
      	}
    	for(int i = 0; i < struMap.length; i++) {
    		colIndex = Integer.parseInt(stroeCol[i]);
    		temp = null;
    		if (node[colIndex] != null && !node[colIndex].equals("null") && node[colIndex].trim().length() > 0) {
    			temp = (Integer) struMap[i].get(Integer.toString(node[colIndex].length()));
    		}
    		
    		if (temp != null) {
                js += (temp.intValue()) *
                    (int) Math.pow(10, (struMap.length - 1 - i));
              }
      	}
    }
    //编号所有内容参与分级，不考虑‘-’
    else {
      for (int i = 0; i < struMap.length; i++) {
        //判断结点编号是否相同
        if (!node[i].equals(preNode[i])) {
          tag = false;
        }
        if (node[i] != null && !node[i].equals("null")) {
          temp = (Integer) struMap[i].get(Integer.toString(node[i].length()));
        }
        else {
          temp = new Integer(0);
        }
        if (temp != null) {
          js += (temp.intValue()) * (int) Math.pow(10, (struMap.length - 1 - i));
        }
      }
    }

    //如果编号与前一个相同，则级数+1(即长兄若父的原则)
    if (tag) {
      js = js + 1;
    }
    return js;
  }

  private static int getJSInt(int js) {
    String str = Integer.toString(js);
    int len = 0;
    int l;
    for (int i = 0; i < str.length(); i++) {
      l = Integer.parseInt(str.substring(i, i + 1));
      len += l;
    }
    return len;
  }

  private static boolean isEqualedBH(String[] node, Vector queryNodeVector) {
    if (queryNodeVector.size() == 0) {
      return false;
    }
    String preBH = ( (String[]) queryNodeVector.get(queryNodeVector.size() - 1))[
        0];
    if (node[0].equals(preBH)) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * 将XML字符串转换为数据集
   * @param cids String[]
   * @param xmlStr String
   * @return QueryDataSet
   */
  public static QueryDataSet getQueryNodeFromXMLString(String[] cids,
      String xmlStr) {
    long s = System.currentTimeMillis();
    QueryDataSet queryDataSet = new QueryDataSet();
    Vector dataVector = new Vector();
    Vector rootVector = new Vector();
    queryDataSet.setDataVector(dataVector);
    queryDataSet.setRootVector(rootVector);
    Element table;
    Element tableData;
    Element rows;
    Element row;
    TreeTableDataManager tdm = new TreeTableDataManager(xmlStr);
    table = tdm.getDocment().getRootElement();
    List tableDataList = tdm.getChildrenNodes(table, "TableData");
    tableData = (Element) tableDataList.get(0);
    List rowsList = tdm.getChildrenNodes(tableData, "Rows");
    rows = (Element) rowsList.get(0);
    //初始化元素序号
    int rowCount = 0;
    int[] value;
    //一级结点集合
    List rowList = rows.getChildren();
    for (int i = 0; i < rowList.size(); i++) {
      row = (Element) rowList.get(i);
      value = parserRowData(queryDataSet, cids, rowCount, "-1", row);
      //增加一级结点
      rootVector.add(queryDataSet.getDataVector().get(rowCount));
      rowCount = value[0];
    }
    //数据分级标示
    if (rootVector.size() < queryDataSet.getDataVector().size()) {
      queryDataSet.setClassed(true);
      //2007-06-12YRH 设置第一列为缩进列
      queryDataSet.setStruCount(1);
    }
    long e = System.currentTimeMillis();
    Debug.PrintlnMessageToSystem("转换 " + rowCount + " 条数据，耗时：" + (e - s) +
                                 " 毫秒");
    return queryDataSet;
  }

  /**
   *
   * @param queryDataSet QueryDataSet  数据集
   * @param cids String[]              显示数据列标题数组
   * @param rowCount int               元素序号
   * @param parent String              父元素序号
   * @param childCount int             孩子数目
   * @param row Element                元素结点
   */
  private static int[] parserRowData(QueryDataSet queryDataSet,
                                     String[] cids,
                                     int rowCount,
                                     String parent,
                                     Element row) {
    //1 加入该行数据
    int[] value = new int[2];
    Attribute atr;
    String data;
    String[] parentNode = new String[cids.length + 3];
    queryDataSet.getDataVector().add(parentNode);
    //数据
    for (int i = 0; i < cids.length; i++) {
      atr = row.getAttribute(cids[i]);
      if (atr != null) {
        data = atr.getValue();
      }
      else {
        data = "";
      }
      parentNode[i] = data;
    }
    //序号
    parentNode[parentNode.length - 3] = Integer.toString(rowCount++);
    //父结点序号
    parentNode[parentNode.length - 2] = parent;

    //初始化子孙数目
    int childCount = 0;
    //2 处理子元素
    List rowList = row.getChildren();
    if (rowList != null && rowList.size() > 0) {
      //孩子数目
      childCount = rowList.size();
      for (int i = 0; i < rowList.size(); i++) {
        row = (Element) rowList.get(i);
        value = parserRowData(queryDataSet, cids, rowCount,
                              parentNode[parentNode.length - 3], row);
        rowCount = value[0];
        //累加孙子数目
        childCount += value[1];
      }
    }
    //子孙数目
    parentNode[parentNode.length - 1] = Integer.toString(childCount);
    //返回下一个元素序号，本元素子孙数目
    value[0] = rowCount;
    value[1] = childCount;
    return value;
  }

  public static void print(QueryDataSet queryDataSet) {
    String[] node;
    for (int i = 0; i < queryDataSet.getDataVector().size(); i++) {
      node = (String[]) queryDataSet.getDataVector().get(i);
      System.out.println("");
      for (int j = 0; j < node.length; j++) {
        System.out.print(node[j] + "\t");
      }
    }
  }

  public static void test(String file) {
    try {
      FileReader fileReader = new FileReader(file);
      String[] cids = "c0	c1	c2	c3	c401	c402	c403	c404	c405	c406	c501	c502	c503	c601	c602	c603	c701	c702	c703	c801	c802	c803	c901	c902	".split("\t");
      BufferedReader bufReader = new BufferedReader(fileReader);
      QueryDataSet queryDataSet = getQueryNodeFromBufferReader(cids, bufReader);
      String[] node;
      for (int i = 0; i < queryDataSet.getDataVector().size(); i++) {
        node = (String[]) queryDataSet.getDataVector().get(i);
        System.out.println("");
        for (int j = 0; j < node.length; j++) {
          System.out.print(node[j] + "\t");
        }
      }

      Vector v = queryDataSet.getChildOfData(1);
      for (int i = 0; i < queryDataSet.getRootVector().size(); i++) {
        node = (String[]) queryDataSet.getRootVector().get(i);
        System.out.println("");
        for (int j = 0; j < node.length; j++) {
          System.out.print(node[j] + "\t");
        }
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    test("D:/BBB.txt");
    //test("D:/Query.txt");
//    try{
//      String[] cid = "c001001	c001002	c002	c003	c004	c005	c007	c006".split("\t");
//      URL url = new URL(
//          "http://localhost:8080/EAIServer/Query/BCCA346BA235E7F8_1167103505515");
//      QueryDataSet queryDataSet=getQueryNodeFromZIPURL(cid, url, true);
//      String [] temp;
//      while(queryDataSet.hasNext()){
//        temp=(String[])queryDataSet.next();
//        //System.out.println(temp[0]+temp[1]);
//      }
//    }
//    catch(Exception ex){
//      ex.printStackTrace() ;
//    }
  }
}
