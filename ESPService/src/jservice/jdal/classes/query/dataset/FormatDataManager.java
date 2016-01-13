package jservice.jdal.classes.query.dataset;

import java.util.zip.GZIPOutputStream;
import java.util.Vector;
import java.sql.ResultSet;
import java.util.Map;
import java.sql.SQLException;
import java.sql.Statement;

import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.Debug;
import com.efounder.pub.util.StringFunction;
import com.efounder.sql.JConnection;

public class FormatDataManager {
  private StringBuffer contentBuffer = null;
  private Object outObject = null;

  public FormatDataManager() {
    contentBuffer = new StringBuffer();
  }

  /**
   * @param viewID String[]  显示列标题数组,即显示格式中,每列的id
   * @param ids int[]        记录集字段序号,用于从记录集ResultSet获取数据,譬如rs.getstring(1)
   * @param rs ResultSet     ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         String[] viewID,
                         int[] ids,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, false, "", viewID,
        ids, rs));
  }

  /**
   * @param isLeveled boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String         分级列编码结构,譬如 部门44444
   * @param viewID String[]     显示列标题数组,即显示格式中,每列的id
   * @param ids int[]           记录集字段序号,用于从记录集ResultSet获取数据,譬如 rs.getstring(1)
   * @param rs ResultSet        ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         boolean isLeveled,
                         String stru,
                         String[] viewID,
                         int[] ids,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, isLeveled, stru,
        viewID, ids, rs));
  }

  /**
   * @param viewID String[]   显示列标题数组,即显示格式中,每列的id
   * @param ids String[]      字段名数组,用于从记录集ResultSet获取数据,rs.getstring(ids[i])
   * @param rs ResultSet      ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         String[] viewID,
                         String[] ids,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, false, "", viewID,
        ids, rs));
  }

  /**
   *
   * @param isLeveled boolean 数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String       分级列编码结构,譬如 部门 44444
   * @param viewID  String[]  显示列标题数组,即显示格式中,每列的id
   * @param ids String[]      字段名数组,用于从记录集ResultSet获取数据,rs.getstring(ids[i])
   * @param rs ResultSet      ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         boolean isLeveled,
                         String stru,
                         String[] viewID,
                         String[] ids,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, isLeveled, stru,
        viewID, ids, rs));
  }

  /**
   * 根据记录集和列描述信息生成输出数据。
   * @param isSumRow boolean    是否在最后增加一行合计行
   * @param rs ResultSet        记录集
   * @param dataStruArray PDataSetStru[] 列描述信息数组
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         PDataSetStru[] dataStruArray,
                         boolean isSumRow,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, false, "",
        dataStruArray, isSumRow, rs));
  }

  /**
   * 根据记录集和列描述信息生成输出数据。
   * @param isLeveled boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String         分级列编码结构,譬如 44444
   * @param isSumRow boolean    是否在最后增加一行合计行
   * @param rs ResultSet        记录集
   * @param dataStruArray PDataSetStru[] 列描述信息数组
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         boolean isLeveled,
                         String stru,
                         PDataSetStru[] struArray,
                         boolean isSumRow,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, isLeveled, stru,
        struArray, isSumRow, rs));
  }

  /**
   * 格式化数据
   * @param PO JParamObject
   * @param viewID String[]   显示列标题数组,即显示格式中,每列的id
   * @param dataVector Vector 数据集,Vector是String[]的集合
   *                          String[]严格按照与viewID[]一致的列标题顺序,存放数据;
   * 譬如:
   * String[] viewID ={"c001","c002","c003","c005001",.....,"c007"}
   * String[] rowData={"1200","物探公司川东经理部","借","5000.52",.....,"平"}
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         String[] viewID,
                         Vector dataVector) throws Exception {
    QueryDataOutObject.formatData(PO, viewID, dataVector, this);
  }

  /**
   * 格式化数据
   * @param PO JParamObject
   * @param isLeveled boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String         分级列编码结构,譬如 44444
   * @param viewID String[]     显示列标题数组,即显示格式中,每列的id
   * @param dataVector Vector   数据集
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         boolean isLeveled,
                         String stru,
                         String[] viewID,
                         Vector dataVector) throws Exception {
    QueryDataOutObject.formatData(PO, isLeveled, stru, viewID, dataVector, this);
  }

  /**
   * @param isLeveled boolean   数据行是否分级;若分级,则约定按照第一列(编码列)分级
   * @param stru String         分级列编码结构,譬如 部门44444
   * @param viewID String[][]   显示列标题数组,即显示格式中,每列的id
   * @param ids String[][]      记录集字段,用于从记录集ResultSet获取数据
   * @param rs ResultSet        ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         boolean isLeveled,
                         String stru,
                         String[][] viewID,
                         String[][] ids,
                         ResultSet rs) throws Exception {
    contentBuffer.append(QueryDataOutObject.formatData(PO, isLeveled, stru,
        viewID, ids, rs));
  }

  /**
   * 生成分栏数据，典型案例：凭证汇总查询。
   * @param PO JParamObject
   * @param isLeveled boolean     是否分级
   * @param stru String           编码结构
   * @param viewID String[]       显示列标题数组
   * @param cids String[]         固定列字段名数组
   * @param hbbz String[]         行合并标准字段名数组
   * @param lmbhColumn String     栏目编号字段名
   * @param lmDataColumn String   栏目数据字段名
   * @param hjTag int             合计标志: 0 不增加合计列;-1 增加各栏目合计列且位置在固定列之前;1 增加各栏目合计列且位置在固定列之后;
   * @param rs ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
                         boolean isLeveled,
                         String stru,
                         String[] viewID,
                         String[] cids,
                         String[] hbbz,
                         String lmbhColumn,
                         String lmDataColumn,
                         Map lmIndexMap,
                         int hjTag,
                         ResultSet rs) throws Exception {
    QueryDataOutObject.formatData(PO, isLeveled, stru,
                                  viewID, cids, hbbz, lmbhColumn, lmDataColumn,
                                  lmIndexMap, hjTag, rs, this);
  }

  /**
   * 根据记录集生成分栏目数据，典型应用案例：二维余额表
   * @param PO JParamObject
   * @param isLeveled boolean     是否分级
   * @param stru String           编码结构
   * @param viewID String[]       显示列标题数组
   * @param cids String[]         固定列字段名数组
   * @param hbbz String[]         行合并标准字段名数组
   * @param lmbhColumn String     栏目编号字段名
   * @param lmDataColumn String   栏目数据字段名
   * @param hjTag int             合计标志: 0 不增加合计列;-1 增加各栏目合计列且位置在固定列之前;1 增加各栏目合计列且位置在固定列之后;
   * @param isSumRow boolean      是否增加合计行
   * @param rowFilterExp String   若增加合计行，提供行过滤表达式，null 或 "" 表示 无条件，所有行均参加合计
   *                              参与合计的列为：所有变动栏目。
   * @param rs ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
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
                         ResultSet rs) throws Exception {
    QueryDataOutObject.formatData(PO, isLeveled, stru,
                                  viewID, cids, hbbz, lmbhColumn, lmDataColumn,
                                  lmIndexMap, hjTag, isSumRow, rowFilterExp,
                                  dec, rs, this);
  }

  /**
   * 根据记录集生成分栏目数据，典型应用案例：二维余额表
   * @param PO JParamObject
   * @param isLeveled boolean     是否分级
   * @param stru String           编码结构
   * @param viewID String[]       显示列标题数组
   * @param cids String[]         固定列字段名数组
   * @param hbbz String[]         行合并标准字段名数组
   * @param lmbhColumn String     栏目编号字段名
   * @param lmDataColumn String   栏目数据字段名
   * @param hjTag int             合计标志: 0 不增加合计列;-1 增加各栏目合计列且位置在固定列之前;1 增加各栏目合计列且位置在固定列之后;
   * @param isSumRow boolean      是否增加合计行
   * @param rowFilterExp String   若增加合计行，提供行过滤表达式，null 或 "" 表示 无条件，所有行均参加合计
   *                              参与合计的列为：所有变动栏目。
   * @param rs ResultSet
   * @throws Exception
   */
  public void formatData(JParamObject PO,
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
                         ResultSet rs) throws Exception {
    QueryDataOutObject.formatData(PO, isLeveled, stru,
                                  viewID, cids, hbbz, lmbhColumn, lmDataColumn,
                                  lmIndexMap, hjTag, isSumRow, rowFilterExp,
                                  dec, rs, this);
  }

  /**
   * 根据账页数据生成多栏账，典型案例：科目多栏账。
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
  public void formatData(JParamObject PO,
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
                         ResultSet rs) throws Exception {
    QueryDataOutObject.formatData(PO, viewId, fixColumnId, jfLM, dfLM,
                                  mergeColumnID,
                                  format, dec, lmYearSum, ye,
                                  lmbhColumnID, lmDataColumnID,
                                  fxColumnID,
                                  preMonth, rs, this);
  }

  /**
   * 数据逐级汇总
   * @param con JConnection
   * @param tableName String　　　逐级汇总的表
   * @param stru String　　　　　　分级的编码结构
   * @param jzjs String　　　　　　汇总到的级数
   * @param noHzCol String[][]　  不需要汇总的列．第一个是insert 列，第二个是select 列　，这里不要包括分级的编号列．
   * @param hzCol String[]　　    需要汇总的列名，
   * @param dec String[]         与需要汇总的列相对应的精度．
   * @param bhCol String　　　　　分级的编号列
   * @param jsCol String　　　　　级数列
   * @param groupCol String[]　　除编号列的的，其它分组列，如果需要的话．
   * @param where String       　需要加查询数据的条件　／／要自己加　and
   * @throws SQLException
   */
  public static void convergeDate(JConnection con,
                                  String tableName,
                                  String stru,
                                  String jzjs,
                                  String[][] noHzCol,
                                  String[] hzCol,
                                  String[] dec,
                                  String bhCol,
                                  String jsCol,
                                  String[] groupCol,
                                  String where
      ) throws SQLException {

    Statement stmt = con.createStatement();
    StringBuffer insertCol = new StringBuffer();
    StringBuffer selectCol = new StringBuffer();
    String vsSql = "";
    String groupby = "";
    int viLen = 0;
    //组织出insert COl,Select COl
    for (int i = 0; i < noHzCol.length; i++) {
      insertCol.append(noHzCol[i][0]);
      insertCol.append(",");
      selectCol.append(noHzCol[i][1]);
      selectCol.append(",");
    }
    for (int i = 0; i < hzCol.length; i++) {
      insertCol.append(hzCol[i]);
      insertCol.append(",");
      selectCol.append(" round(sum(round(" + hzCol[i] + "," + dec[i] +
                       "))," + dec[i] + ")");
      selectCol.append(",");
    }

    insertCol.delete(insertCol.length() - 1, insertCol.length());
    selectCol.delete(selectCol.length() - 1, selectCol.length());
    //groupby COl
    for (int i = 0; i < groupCol.length; i++) {
      groupby += groupCol[i];
      groupby += ",";
    }
    if (groupby.length() > 0) {
      groupby = groupby.substring(0, groupby.length() - 1);
    }
    //从最大级数减一开始汇总，到查询级数截止
    //取数条件为当前级数的下一级，并且归集的新编号不等于已存在的编号，按编号分组
    for (int i = (stru.length() - 1); i >= Integer.parseInt(jzjs); i--) {
      viLen = StringFunction.GetStructLength(stru, i);
      vsSql = " insert into " + tableName + "(" + bhCol + "," + jsCol + "," +
          insertCol.toString() + ")" +
          " SELECT substring(" + bhCol + ",1," + viLen + "),'" + i + "'," +
          selectCol.toString() +
          " from " + tableName +
          " where " + jsCol + " = '" + (i + 1) + "' and " + bhCol +
          " <> substring(" + bhCol + ",1," +
          viLen + ") " +
          where +
          " group by substring(" + bhCol + ",1," + viLen + ") ";
      if (groupby.length() > 0) {
        vsSql += "," + groupby;
      }
      Debug.PrintlnMessageToSystem(vsSql);
      stmt.addBatch(vsSql);
    }
    stmt.executeBatch();
  }

  /**
   * 返回格式化后的数据(文件URL)
   * @return String
   */
  public String getFormatedDate() {
    close();
    return getFormatedBufferDate().toString();
  }

  public StringBuffer getFormatedBufferDate() {
    return contentBuffer;
  }

  public Object getOutObject() {
    return outObject;
  }

  public void setOutObject(Object obj) {
    this.outObject = obj;
  }

  private void close() {
    try {
      GZIPOutputStream out = (GZIPOutputStream) getOutObject();
      if (out != null) {
        out.finish();
        out.close();
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    //...

    //1 实例化一个对象
    //FormatDataManager formatDataManager=new FormatDataManager();
    //2 根据具体的数据特点,组织好参数数据,调用某个formatData(...)方法
    //formatDataObject.formatData(...);
    //3 获取数据处理结果
    //String data = formatDataObject.getFormatedDate();

    //...
  }
}
