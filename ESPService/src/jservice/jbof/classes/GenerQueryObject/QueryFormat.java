package jservice.jbof.classes.GenerQueryObject;

import com.report.table.jxml.TableManager;
import java.io.FileInputStream;
import com.f1j.swing.JBook;
import jdatareport.dof.classes.report.QueryDataSet;
import java.util.ArrayList;
import java.util.Vector;
import java.io.File;
import com.report.table.jxml.XmlColumn;
import jdatareport.dof.classes.report.paint.JDRQueryPainterUtils;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.efounder.eai.data.*;

/**
 * 查询格式设置、存储类，实现查询格式的本地化存储、读取。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class QueryFormat {
  /**
   * 本地查询格式存储路径
   */
  public static String xmlPath = "C:/FmisQueryFormat/";

  /**
   * 获取当前展开的数据集，用于所见所得打印。
   * @param queryWindow JGenerQueryWindow
   * @param isAll boolean  是否
   */
  public static void getUnWrapedNode(JGenerQueryWindow queryWindow,boolean isUnwrped) {

    QueryDataSet dataSet = queryWindow.vwQueryView.getQueryDataSet();
    Vector unwrapedDataNodeVector = new Vector();
    //打印展开的行
    if(isUnwrped){
      MultiLevelHeaderTreeTable view = queryWindow.vwQueryView;
      JBook jbook = view.getDataReport().getBook();
      //1 标题行数
      int titleRow = view.getTableManager().getTitles().size();
      //2 表头行数
      int headRow = JDRQueryPainterUtils.getHeaderLevels(view.getTableManager());
      //3 数据起止行
      int startRow = titleRow + headRow;
      int endRow = jbook.getLastRow() + 1;
      //4 获取分级数据展开的行号数组
      Vector rowVector = new Vector();
      if (dataSet.isClassed()) {
        rowVector = getUnWrapedRows(startRow, endRow, jbook, new Vector());
        //构造展开行的数据集合
        Vector allNode = dataSet.getDataVector();
        int row = 0;
        for (int i = 0; i < rowVector.size(); i++) {
          row = Integer.parseInt( (String) rowVector.get(i)) - 1 - startRow;
          unwrapedDataNodeVector.add(allNode.get(row));
        }
      }
    }
    //打印所有行
    else{
      dataSet.setUnwrapedDataVector(dataSet.getDataVector());
    }
//    //5 异常处理，如果数据集为空，则取全部数据
//    if (unwrapedDataNodeVector == null || unwrapedDataNodeVector.size() == 0) {
//      unwrapedDataNodeVector = dataSet.getDataVector();
//    }
    if(unwrapedDataNodeVector != null && unwrapedDataNodeVector.size() > 0){
      dataSet.setUnwrapedDataVector(unwrapedDataNodeVector);
    }

//    for (int i = 0; i < rowVector.size(); i++) {
//      System.out.println("输出= " +rowVector.get(i));
//    }
//    String [] temp=null;
//    for (int i = 0; i < unwrapedDataNodeVector.size(); i++) {
//      temp=(String[])unwrapedDataNodeVector.get(i);
//      System.out.println("输出= " +temp[0]);
//    }
  }

  /**
   * 得到jbook当前展开的行（即可以看到的行）
   * @param row int  jbook中的开始行
   * @param end int  jbook中的结束行
   * @param jbook JBook
   * @param vector Vector
   * @return Vector
   */
  public static Vector getUnWrapedRows(int row, int end, JBook jbook,
                                       Vector vector) {
    Vector rows = new Vector();
    rows = vector;
    int js = -1;
    int zt = -1;
    int js1 = -1;
    //判断当前行是否有效
    if (row < end) {
      rows.add(String.valueOf(row + 1));
      //得到当前行的级数
      js = jbook.getBook().getSheet(0).getRowOutlineLevel(row);
      //得到当前行的状态,'0'是叶子结点,'1'是展开,'2'是未展开
      zt = jbook.getBook().getSheet(0).getRowOutlineType(row);
      row++;
      //如果当前行未展开,找到当前行的兄弟结点,如果没有兄弟结点, 则找到与当前行的上级是兄弟的结点
      if (zt == 2) {
        //判断当前行是否有效
        if (row < end) {
          //得到row的级数
          js1 = jbook.getBook().getSheet(0).getRowOutlineLevel(row);
          while (js < js1) {
            row++;
            //判断当前行是否有效
            if (row < end) {
              //得到row的级数
              js1 = jbook.getBook().getSheet(0).getRowOutlineLevel(row);
            }
            else {
              break;
            }
          }
          //加入找到的行
          getUnWrapedRows(row, end, jbook, rows);
        }
      }
      //如果当前行展开来是叶子结点加入当前行
      if (zt == 1 || zt == 0) {
        //判断当前行是否有效
        if (row < end) {
          getUnWrapedRows(row, end, jbook, rows);
        }
      }
    }
    return rows;
  }

  /**
   * 用本地的格式文件修正服务器端的格式。
   * @param queryFormatName String
   * @return TableManager
   */
  public static TableManager getQueryFormat(TableManager tableManager,
                                            String queryFormatName) {
    JParamObject PO = JParamObject.Create();
    String getlf = PO.GetValueByEnvName("ZW_GETLOCALFORMAT","1");
    if (getlf.equals("0")) return tableManager;
    //放弃原来的文件名为null的文件
    if("null".equals(queryFormatName)){
      return tableManager;
    }

    boolean tag = true;
    //拷贝原来的格式，发生异常时返回原格式
    TableManager tmBak = new TableManager(tableManager.GetRootXMLString());
    //0 判断文件是否存在
    int cidSize = 0;
    String[] cidNameGroup = null;
    String[] localcidNameGroup = null;
    String cidName = "";
    String localcidName = "";
    XmlColumn xmlColumn = null,localXmlColumn = null;

    String filename = xmlPath + queryFormatName;
    File file = new File(filename);
    if (file.exists()) {
      try {
        //1 打开本地格式文件
        FileInputStream in = new FileInputStream(filename);

        InputStreamReader inputStreamReader = new InputStreamReader(
            in, "UTF-8");
        BufferedReader bufReader = new BufferedReader(inputStreamReader);
        char [] content=new char[1024*256];
        int count=bufReader.read(content);
        String xml=new String(content,0,count);
        //2 读取构造格式文件
        TableManager localTableManager = new TableManager(xml);
        //3 修正
        cidNameGroup = MultiLevelHeaderTreeTable.getViewIDs(tableManager);
        localcidNameGroup = MultiLevelHeaderTreeTable.getViewIDs(
            localTableManager);
        cidSize = cidNameGroup.length;
        for (int i = 0; i < cidSize; i++) {
          cidName = cidNameGroup[i].toString();
          for (int j = 0; j < localcidNameGroup.length; j++) {
            localcidName = localcidNameGroup[j].toString();
            if (cidName.equals(localcidName)) {
              localXmlColumn = localTableManager.getColumnById(localcidName);
              //仅修正字体、字号、对齐、列宽
              xmlColumn = tableManager.getColumnById(cidName);
              xmlColumn.setFontname(localXmlColumn.getFontname());
              xmlColumn.setFontsize(localXmlColumn.getFontsize());
              xmlColumn.setAlign(localXmlColumn.getAlign() );
              xmlColumn.setWidth(localXmlColumn.getWidth());
              tableManager.UpdateColumn(xmlColumn);
            }
          }
        }
        in.close();
      }
      catch (Exception ex) {
        tag = false;
        ex.printStackTrace();
      }
    }
    //修正失败则返回原来的格式
    if (tag) {
      return tableManager;
    }
    else {
      return tmBak;
    }
  }
}
