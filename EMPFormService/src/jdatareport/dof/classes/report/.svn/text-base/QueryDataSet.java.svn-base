package jdatareport.dof.classes.report;

import java.io.*;
import java.util.*;
import jdatareport.dof.classes.report.filter.JDRRowFilter;
import jdatareport.dof.classes.report.ext.condition.util.JCExpression;

public class QueryDataSet {

  /**
   *本地文件缓存方式下，每次读入的最大行数
   */
  public static int MAX_SIZE = 10000;
  /**
   * 页长，每次以‘页长’为单位向显示组件中增加数据
   */
  public static int PAGE_SIZE = 10000;
  /**
   *  数据集合,存储多行数据，每行数据存在一个字符数组中,数组长度=数据列数目+3；
   *  扩展的三个数组元素中,第一个数组元素存放元素自身下标，第二个数组元素存放父元素下标，第三个元素存放子孙数目
   */
  private Vector dataVector;
  /**
   * 分级数据的根结点集合
   */
  private Vector rootVector;
  /**
   * 打印数据集合（分级数据，当前展开的数据集合，非分级数据则为所有数据即dataVector）
   * 2008-2-2 实现分级数据的所见所得打印
   */
  private Vector unWrapedVector;
  /**
   * 数据是否分级
   */
  private boolean classed = false;
  /**
   * 2008-1-3，fengbo
   * 兼容 TAG=0/1/2 的不同取值
   */
  private String classTag;

  /**
   * 当前页号
   */
  private int currentPageNum = 0;
  /**
   * 当前行号
   */
  private int currentRowNum = 0;
  /**
   * 用于分级的编码结构个数，譬如 4444-333，则struCount的值为 2
   * 此数据用于确定分级数据展示时的缩进列，即第 struCount 列为缩进列。
   */
  private int struCount = 0;
  /**
   * 查询数据的本地文件缓存
   */
  private BufferedReader bufReader;
  /**
   * 本地缓存文件名
   */
  private String fileName;
  /**
   * 是否到文件尾
   */
  private boolean end = true;
  private FileReader fileReader;
  private String[] cidArray;
  private int totalRowCount = 0;

  /**
   * 行过滤器,2008-3-4 fengbo
   */
  private JDRRowFilter rowFilter = null;

  public QueryDataSet() {
    dataVector = new Vector();
    currentRowNum = 0;
    totalRowCount = 0;
  }

  /**
   * 取某一页数据,用于显示；所以直接从dataVector取数。
   * @param tag boolean    若tag 为false，则强制按照非分级数据取数，譬如数据过滤时，不考虑分级；
   *                       若tag 为true ，则所取数据是否分级取决于数据自身是否分级
   * @param pageIndex int
   * @return Vector
   */
  public Vector getNextPageData(boolean tag) {
    if (tag == true) {
      tag = this.isClassed();
    }
    //数据不分级
    if (!tag) {
      if (currentPageNum == 0 && dataVector.size() < PAGE_SIZE) {
        currentPageNum++;
        return dataVector;
      }
      else {
        Vector pageVector = new Vector();
        int startIndex = currentPageNum * PAGE_SIZE;
        int endIndex = (currentPageNum + 1) * PAGE_SIZE;
        for (int i = startIndex; i < endIndex && i < dataVector.size(); i++) {
          pageVector.add(dataVector.get(i));
        }
        currentPageNum++;
        return pageVector;
      }
    }
    //数据分级;因为数据量不大,所以不分页一次全部取出根结点
    else {
      if (currentPageNum == 0) {
        currentPageNum++;
        return this.getRootVector();
      }
      else {
        return null;
      }
    }
  }

  /**
   * 获取某个结点的直接下级子结点集合
   * @param index int  行结点在数据全集中的序号
   * @return Vector
   */
  public Vector getChildOfData(int index) {
    String indexStr = Integer.toString(index);
    //不分级,则没有子结点
    if (!classed) {
      return null;
    }
    Vector vector = new Vector();
    String[] temp = (String[]) dataVector.get(index);
    //取出孩子数目
    int childCount = Integer.parseInt(temp[temp.length - 1]);
    //根据数据元素排列规则,下标在[Index+1,Index+childCount]都是temp的子孙结点
    for (int i = index + 1; i <= index + childCount; i++) {
      temp = (String[]) dataVector.get(i);
      if (temp[temp.length - 2].equals(indexStr)) {
        vector.add(temp);
      }
    }
    return vector;
  }

  /**
   * 获取某结点的所有子孙数目
   * @param index int 行结点在数据全集中的序号
   * @return int
   */
  public int getAllChildCount(int index) {
    String[] temp = (String[]) dataVector.get(index);
    //取出孩子数目
    return Integer.parseInt(temp[temp.length - 1]);
  }

  /**
   * 判断数据集中是否还有下一行数据，用于打印、预览。
   * @return boolean
   */
  public boolean hasNext() {
//    boolean tag = currentRowNum < dataVector.size();
    //2008-2-18, unWrapedVectorc初值为dataVector
    if (unWrapedVector == null || unWrapedVector.size() == 0) {
      unWrapedVector = this.dataVector;
    }
    boolean tag = currentRowNum < this.unWrapedVector.size();
    if (tag) {
      return true;
    }
    //读文件数据
    else {
      Vector data = readDataFromFile();
      if (data != null) {
        //清空数据，释放内存；更换数据集;数据行指针复位
        this.dataVector.clear();
        this.dataVector = data;
        currentRowNum = 0;
        unWrapedVector = this.dataVector;
        tag = true;
      }
    }
    return tag;
  }

  /**
   * 取下一行数据
   * @return String[]
   */
  public String[] next() {
    if (hasNext()) {
//      return (String[]) dataVector.get(currentRowNum++);
      //2008-2-18
      return (String[]) unWrapedVector.get(currentRowNum++);
    }
    else {
      return null;
    }
  }

  /**
   * 行指针复位，但是仅限于在当前数据集中复位，目前暂不支持在整个文件数据集中复位
   */
  public void goTop() {
    currentRowNum = 0;
  }

  /**
   * 从本地文件缓存中读取数据
   * @return Vector
   */
  private Vector readDataFromFile() {
    if (fileName != null) {
      try {
        if (bufReader == null) {
          fileReader = new FileReader(fileName);
          bufReader = new BufferedReader(fileReader);
          end = false;
        }
        if (!end) {
          QueryDataSet dataset = QueryDataTransformer.
              getQueryNodeFromBufferReader(
                  cidArray, bufReader);
          this.end = dataset.end;
          return dataset.getDataVector();
        }
        else {
          fileReader.close();
          //delTempFile();
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      return null;
    }
    return null;
  }

  private boolean delTempFile() {
    boolean tag = true;
    File file = new File(fileName);
    if (file.exists()) {
      tag = file.delete();
    }
    return tag;
  }

  public void setDataVector(Vector dataVector) {
    this.dataVector = dataVector;
  }

  public void setUnwrapedDataVector(Vector vector) {
    this.unWrapedVector = vector;
  }

  public void setClassed(boolean classed) {
    this.classed = classed;
  }

  public Vector getDataVector() {
    return this.dataVector;
  }

  public Vector getUnwrapedDataVector() {
    if (unWrapedVector == null || unWrapedVector.size() == 0) {
      unWrapedVector = this.dataVector;
    }

    return this.unWrapedVector;
  }

  public boolean isClassed() {
    return classed;
  }

  public void setCurrentPageNum(int num) {
    this.currentPageNum = num;
  }

  public int getCurrentNum() {
    return currentPageNum;
  }

  public void setRootVector(Vector rootVector) {
    this.rootVector = rootVector;
  }

  public void setStruCount(int struCount) {
    this.struCount = struCount;
  }

  public Vector getRootVector() {
    return rootVector;
  }

  public int getStruCount() {
    return struCount;
  }

  protected String[] getCidArray() {
    return cidArray;
  }

  protected String getFileName() {
    return fileName;
  }

  protected boolean isEnd() {
    return end;
  }

  public int getTotalRowCount() {
    if (unWrapedVector == null || unWrapedVector.size() == 0) {
      unWrapedVector = this.dataVector;
    }
    if (totalRowCount == 0) {
//      return this.dataVector.size();
      //2008-2-18
      return this.unWrapedVector.size();
    }
    return totalRowCount;
  }

  public String getClassTag() {
    return classTag;
  }

  public JDRRowFilter getRowFilter() {
    return rowFilter;
  }

  protected void setCidArray(String[] cidArray) {
    this.cidArray = cidArray;
  }

  protected void setFileName(String fileName) {
    this.fileName = fileName;
  }

  protected void setEnd(boolean end) {
    this.end = end;
  }

  public void setTotalRowCount(int totalRowCount) {
    this.totalRowCount = totalRowCount;
  }

  public void setClassTag(String classTag) {
    this.classTag = classTag;
  }

  public void setRowFilter(JDRRowFilter rowFilter) {
    this.rowFilter = rowFilter;
  }

  /**
   * 用指定的过滤器，对数据行过滤,实现仅打印过滤后的数据。
   */
  public void filter(int count) throws Exception{
    //数据过滤
    if (this.rowFilter != null) {
      Vector filteredVector = new Vector();
      for (int j = 0; j < dataVector.size(); j++) {
        if (rowFilter != null &&
            !rowFilter.isAcceptable( (String[]) dataVector.get(j),
                                    count)) {
          continue;
        }
        filteredVector.add(dataVector.get(j));
      }
      this.setUnwrapedDataVector(filteredVector);
    }
    //取消过滤
    else {
      this.setUnwrapedDataVector(null);
    }
  }

  /**
   * 根据数据集的指定列的值，分解当前一个数据集为多个数据集。
   * 要求：数据集按指定列排序；
   * @param col int
   * @return QueryDataSet[]
   */
  public QueryDataSet[] disposeQueryDataSet(int col) {
    Vector queryDataSetVector = new Vector();
    QueryDataSet tempQueryDataSet = null;
    //构造一个特殊的key值
    String preKey = "__PRE_KEY__";
    String key = null;
    String[] tempArray = null;
    for (int j = 0; j < dataVector.size(); j++) {
      tempArray = (String[]) dataVector.get(j);
      key = tempArray[col];
      //关键字切换时，新数据集合产生
      if (!key.equals(preKey)) {
        if (tempQueryDataSet != null) {
          queryDataSetVector.add(tempQueryDataSet);
        }
        tempQueryDataSet = new QueryDataSet();
      }
      tempQueryDataSet.getDataVector().add(tempArray);
      preKey = key;
    }
    //最后一组数据集
    if (tempQueryDataSet != null) {
      queryDataSetVector.add(tempQueryDataSet);
    }

//    tempQueryDataSet.getDataVector().add(tempQueryDataSet) ;
    QueryDataSet[] queryDataSetArray = new QueryDataSet[queryDataSetVector.size()];
    queryDataSetVector.toArray(queryDataSetArray);
    return queryDataSetArray;
  }
}
