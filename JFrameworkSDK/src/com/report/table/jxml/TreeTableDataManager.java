package com.report.table.jxml;

import java.util.*;
import org.jdom.*;

import com.pub.util.StringFunction;
import com.report.table.*;
//Xyz add 2004/08/11 分页显示 START
//Xyz add 2004/08/11 分页显示 END

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TreeTableDataManager extends XmlManager{

  //Xyz add 2004/08/11 分页显示 START
  private TreeTableDataManager  nextPageDM    = null;
  private boolean              isGetTDs      = false;
  private String               nextPageXML   = null;
  private QueryNode[]          QNArray = null;
  //Xyz add 2004/08/11 分页显示 END
  private Element     Table;
  private Element     TableData;
  private Element     Rows;

  public TreeTableDataManager() {
      super();
      initEmpty();
  }
  public TreeTableDataManager(java.io.InputStream is) {
      super();

      super.InitXMLStream(is);
      init();
  }
  public TreeTableDataManager(String s) {
    //super(s);
    //Xyz add 2004/08/11 分页显示 START
    super(StringFunction.splitStringByToken(s,TableDataManager.PageSeparator)[0]);
    //System.out.println("TreeTableDataManager s:"+s);
    //System.out.println("TreeTableDataManager sa:"+(s+TableDataManager.PageSeparator).substring(0,(s+TableDataManager.PageSeparator).indexOf(TableDataManager.PageSeparator)));
    String[] dataStrings = StringFunction.splitStringByToken(s,TableDataManager.PageSeparator);
    //System.out.println("TreeTableDataManager sb:"+dataStrings[0]);
    //System.out.println("TreeTableDataManager sc:"+dataStrings[1]);
    nextPageXML   = dataStrings[1];
//    if( dataStrings[1] != null && (!dataStrings[1].trim().equals("")) ){
//      nextPageDM = new TreeTableDataManager(dataStrings[1]);
//    }
    //Xyz add 2004/08/11 分页显示 END
    init();
  }

  /**
   * 初始化空的XML
   */
  private void initEmpty(){
    Element TT = null;//super.getRootElement();
    //if (TT == null) {
      TT = super.CreateElement("Table");
      super.Root = TT;
      super.getDocment().setRootElement(Root);
    //}

    Table = TT;

    List TableDataList = super.getChildrenNodes(Table, "TableData");
    if (TableDataList != null && TableDataList.size() > 0) {
      TableData = (Element) TableDataList.get(0);
    }
    else {
      TableData = super.CreateElement("TableData");
      Table.addContent(TableData);
    }

    List RowsList = super.getChildrenNodes(TableData, "Rows");
    if (RowsList != null && RowsList.size() > 0) {
      Rows = (Element) RowsList.get(0);
    }
    else {
      Rows = super.CreateElement("Rows");
      TableData.addContent(Rows);
    }
  }

  /**
   * 已经给定一个XMLSTRING，初始化XML
   */
  private void init(){
    Element TT = super.getDocment().getRootElement();

    Table = TT;

    List TableDataList = super.getChildrenNodes(Table, "TableData");
    if (TableDataList != null && TableDataList.size() > 0) {
      TableData = (Element) TableDataList.get(0);
    }
    else {
      TableData = super.CreateElement("TableData");
      Table.addContent(TableData);
    }

    List RowsList = super.getChildrenNodes(TableData, "Rows");
    if (RowsList != null && RowsList.size() > 0) {
      Rows = (Element) RowsList.get(0);
    }
    else {
      Rows = super.CreateElement("Rows");
      TableData.addContent(Rows);
    }
  }
  public static void convertBibtoPar(Vector BibDataList,Vector ParDataList) {
    convertBibtoPar(BibDataList,ParDataList,0);
  }
  // 将分级结构的数据，转换成平行结构的数据
  public static void convertBibtoPar(Vector BibDataList,Vector ParDataList,int JS) {
    QueryNode[] QNA = null;QueryNode QN = null;
    int Count = 0;int CurrentJS = 1;
    for(int i=0;i<BibDataList.size();i++) {
      QNA = (QueryNode[]) BibDataList.get(i);
      if ( JS == 0 || CurrentJS <= JS ) {
        convertBibtoPar(QNA, ParDataList,JS,CurrentJS);
      }
    }
    return;
  }
  private static void proJS(QueryNode QN,int js) {
    // 名称只能在第1个上
    String sValue = (String)QN.getDatas()[1];
    String temp="";
    for(int k=0;k<(js-1);k++) temp+="  ";
    sValue = temp+sValue;
    QN.getDatas()[1] = sValue;
  }
  public static void convertBibtoPar(QueryNode[] QNA,Vector ParDataList,int JS,int CJS) {
    QueryNode QN = null;QueryNode[] CQNA = null;
    int CurrentJS = CJS;
    if ( QNA == null ) return;
    for(int i=0;i<QNA.length;i++) {
      QN = QNA[i];
      proJS(QN,CurrentJS);
      ParDataList.add(QN);
      CurrentJS++;
      CQNA = QN.getChildren();
      if ( CQNA != null ) {
        if ( JS == 0 || CurrentJS <= JS )
          convertBibtoPar(CQNA, ParDataList, JS, CurrentJS);
      }
      CurrentJS--;
    }
  }
  // 获取所有页的所有行数
  public int getDataSize(Vector DataList) {
    QueryNode[] QN = null;
    int Count = 0;
    for(int i=0;i<DataList.size();i++) {
      QN = (QueryNode[]) DataList.get(i);
      Count += getQueryNodeSize(QN);
    }
    return Count;
  }

  // 获取某一页的所有行数
  public int getQueryNodeSize(QueryNode[] QN) {
    int size = 0;QueryNode[] CQN=null;
    size = QN.length;
    for(int i=0;i<QN.length;i++) {
      CQN   = QN[i].getChildren();
      if ( CQN != null )
        size += getQueryNodeSize(CQN);
    }
    return size;
  }
  // 获取所有数据页
  public void getData(Object[] colIds,Vector DataList) {
    // 获取本页的数据
    QueryNode[] QN = getDatas(colIds);//getTableDatas(Rows,colIds,null);
    if ( QN != null ) {
      DataList.add(QN); // 增加到数据列表中
    }
    // 获取下页的数据
    if( nextPageXML != null && (!nextPageXML.trim().equals("")) ){
      if(nextPageDM == null){
        nextPageDM = new TreeTableDataManager(nextPageXML);
      }
      nextPageDM.getData(colIds,DataList);
    }else{
      return;
    }
    return;
  }
  public QueryNode[] getTableDatas(Object[] colIds){
    //Xyz add 2004/08/11 分页显示 START
    if(isGetTDs){
      if( nextPageXML != null && (!nextPageXML.trim().equals("")) ){
        if(nextPageDM == null){
          nextPageDM = new TreeTableDataManager(nextPageXML);
        }
        return nextPageDM.getTableDatas(colIds);
      }else{
        return null;
      }
    }
    isGetTDs = true;
    //Xyz add 2004/08/11 分页显示 END
    if ( QNArray == null )
      QNArray = getDatas(colIds);//getTableDatas(Rows,colIds,null);
    return QNArray;
  }
  private QueryNode[] getDatas(Object[] colIds) {
    QueryNode[] qns = QNArray;
    if ( qns == null ) {
      qns = getTableDatas(Rows, colIds, null);
      QNArray = qns;
    }
    return qns;
  }
  public QueryNode[] getViewTableDatas(Object[] colIds){
    //Xyz add 2004/08/11 分页显示 START
    if(isGetTDs){
      if( nextPageXML != null && (!nextPageXML.trim().equals("")) ){
        if(nextPageDM == null){
          nextPageDM = new TreeTableDataManager(nextPageXML);
        }
        return nextPageDM.getViewTableDatas(colIds);
      }else{
        return null;
      }
    } else {
      isGetTDs = true;
      return getTableDatas(Rows,colIds,null);
    }
    //Xyz add 2004/08/11 分页显示 END
//    if ( QNArray == null )
//      QNArray = getDatas(colIds);//getTableDatas(Rows,colIds,null);
//    return QNArray;
  }

  public QueryNode[] getTableDatas(Element ParentRow,Object[] colIds,QueryNode ParentNode){
    QueryNode[] QueryNodeArray = null;
    Object Data = null;
      int colMax = colIds.length;
      if( super.getDocment() == null) return null;
      List rowList = ParentRow.getChildren();//
//      super.getChildrenNodes(ParentRow,"Row");
      if ( rowList == null || rowList.size() == 0 ) return null;
      //计算行数
      int rowMax = 0;
      rowMax = rowList.size();
//      for(int i=0;i<rowList.size();i++){ 这样做不是有毛病吗？？？？？
//        Element rowNode = (Element)rowList.get(i);
//        rowMax++;
//      }
      // 形成行节点数组
      QueryNode qn = null;
      Object[] ColsDatas = null;
      QueryNodeArray = new QueryNode[rowMax];
      for(int i=0; i<rowList.size(); i++) {
        ColsDatas = new Object[colMax];
        Element rowNode = (Element) rowList.get(i);
        for (int j = 0; j < colMax; j++) {
          Attribute ATR = rowNode.getAttribute( (String) colIds[j]);
          if (ATR == null) Data = ""; else Data = ATR.getValue();
          if (j == 0) {
            Data = qn = new QueryNode(Data, ParentNode, ColsDatas);
          }
          ColsDatas[j] = Data;
        }
        QueryNodeArray[i] = qn;
        // 递归调用
        QueryNode[] qnarray = getTableDatas(rowNode, colIds, qn);
        qn.setChildQueryNode(qnarray);
      }
      isGetTDs = true;
      this.QNArray = QueryNodeArray;
      return QueryNodeArray;
  }

  //参数是一个数据行的属性列表
  //列表中的元素是2维字符串数组，一个属性名，一个属性值
  public Element addDataRow(Vector v){
    return addDataRow(Rows,v,"Row");
  }
  public Element addDataRow(Vector v,String ElementName){
    //Xyz add 2004/08/11 分页显示 START
    if(Rows.getContent().size() >= TableDataManager.MaxLinages){
      if(nextPageDM == null){
        nextPageDM = new TreeTableDataManager();
      }
      Element Row = nextPageDM.addDataRow(v,ElementName);
      //
      return Row;
    }
    //Xyz add 2004/08/11 分页显示 END
    return addDataRow(Rows,v,ElementName);
  }
  public Element addDataRow(Element ParentRow,Vector v,String ElementName){
      Element Row;
      Row = super.CreateElement(ElementName);
      for(int i=0; i<v.size(); i++){
          Object  RES = v.elementAt(i);

          if ( RES == null ) continue;

          String[] attr =  (String[])RES;

          try{
            if ( attr[0] == null ) continue;

            if ( attr[1] == null ) attr[1] = "";

            Row.setAttribute(attr[0], attr[1]);
          }
          catch (Exception E){
            Row.setAttribute(attr[0], "");
          }
      }

      ParentRow.addContent(Row);
      return Row;
  }

  //Xyz add 2004/08/11 分页显示 START
  public String GetRootXMLString() {
    StringBuffer xmlString = new StringBuffer(super.GetRootXMLString());
    if(nextPageDM != null){
      xmlString.append(TableDataManager.PageSeparator);
      xmlString.append(nextPageDM.GetRootXMLString());
      //System.out.println("GetRootXMLString:"+xmlString);
    }
    return xmlString.toString();
  }
  public String printDOMTree() {
    StringBuffer xmlString = new StringBuffer(super.printDOMTree());
    if(nextPageDM != null){
      xmlString.append(TableDataManager.PageSeparator);
      xmlString.append(nextPageDM.printDOMTree());
      //System.out.println("printDOMTree:"+xmlString);
    }
    return xmlString.toString();
  }
  //Xyz add 2004/08/11 分页显示 END
}
/* A FileNode is a derivative of the File class - though we delegate to
 * the File object rather than subclassing it. It is used to maintain a
 * cache of a directory's children and therefore avoid repeated access
 * to the underlying file system during rendering.
 */
