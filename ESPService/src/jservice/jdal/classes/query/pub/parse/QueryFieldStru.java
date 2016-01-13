package jservice.jdal.classes.query.pub.parse;

import java.io.*;

public class QueryFieldStru implements Serializable{

  /**
   * 支持的数据类型:字符类型/数值类型
   */
  public static String CHAR_TYPE = "C";
  public static String NUM_TYPE = "N";

  /**
   * SELECT (A+B-C) AS F_D FROM TABLE WHERE ……
   * A+B-C 为Fieldexp
   * D 为Field
   */
  //列名（字段名）
  private String Field = null;
  //列表达式
  private String Fieldexp = null;

  /**
   * 显示格式列ID
   */
  private String viewID = null;

  /**
   * 类型: 字符C/数值N
   */
  private String Type = CHAR_TYPE;

  /**
   * 该列是否参与合计行
   */
  private boolean colSum = false;

  /**
   * 是否摘要列
   */
  private boolean colJy = false;

  /**
   * 是否是汇总列
   */
  private boolean convergeColumn = false;

  /**
   * 汇总时参照的列
   */
  private String convergeColName = "";

  /**
   * 是否关联字典表
   */
  private boolean relateTable = false;

  /**
   * 关联字典表的表名
   */
  private String tableName = "";

  /**
   * 关联字典表中对应列
   */
  private String tableColName = "";

  /**
   *
   */
  private String relateColName = "";

  /**
   * "合计"描述
   */
  private String sumString = null;

  /**
   * 该列合计生成合计行时的过滤条件,仅当 colSum为真是有效
   */
  private String colSumConditionExp;

  /**
   * 精度
   */
  private int dec = 2;

  /**
   * 是否主键列
   */
  private boolean Key = false;

  /**
   * 是否计算列
   */
  private boolean counted = false;

  /**
   * 列运算表达式: Field1 + Field2 - Field3
   */
  private String expression = null;

  /**
   * 无参构造函数
   */
  public QueryFieldStru() {
  }

  /**
   * 有参构造函数
   * String field    列名（字段名）
   * String fieldexp 列表达式
   * String type     类型: 字符C/数值N
   */
  public QueryFieldStru(String field,String fieldexp,String type){
    this.Field = field;
    this.Fieldexp = fieldexp;
    this.Type = type;
  }

  /**
   * 有参构造函数
   * String field    列名（字段名）
   * String fieldexp 列表达式
   * String type     类型: 字符C/数值N
   * boolean key     是否主键列
   */
  public QueryFieldStru(String field,String fieldexp,String type,boolean key){
    this.Field = field;
    this.Fieldexp = fieldexp;
    this.Type = type;
    this.Key = key;
  }

  /**
   * 校验表达式有效性
   * @param ID String
   */
  public boolean validateExpression() {
    return true;
  }

  /**
   * 与记录集ResultSet对应的字段名，通过该字段名从ResultSet中获取数据，譬如rs.getString("F_MC")。
   * @param ID String
   */
  public void setField(String field) {
    this.Field = field;
  }

  /**
   * 获取字段名
   * @return String
   */
  public String getField() {
    return Field;
  }
  /**
   * 设置列表达式
   * @param exp String
   */
  public void setFieldexp(String exp){
    this.Fieldexp = exp;
  }
  /**
   * 取列表达式
   * @return String
   */
  public String getFieldexp(){
    return this.Fieldexp;
  }

  /**
   * 设置数据精度，用于数值型运算列，譬如列求和。
   * @param dec int
   */
  public void setDec(int dec) {
    this.dec = dec;
  }

  /**
   * 设置该列是否计算列，即该列是否由其他列运算得到；如果是运算列，需要指定列运算表达式。
   * @param counted boolean
   * @see setExpression(String expression)
   */
  public void setCounted(boolean counted) {
    this.counted = counted;
  }

  /**
   * 设置列运算表达式，譬如： c8=c5+c6+c7
   * @param expression String
   * @see setCounted(boolean counted)
   */
  public void setExpression(String expression) {
    this.expression = expression;
  }

  /**
   * 该列对应的查询格式列id。
   * @param viewID String
   */
  public void setViewID(String viewID) {
    this.viewID = viewID;
  }

  /**
   * 该列的数据类型。
   * @param type String
   */
  public void setType(String type) {
    this.Type = type;
  }

  /**
   * 该列为关键字
   * @param key boolean
   */
  public void setKey(boolean key){
    this.Key = key;
  }

  /**
   * 设置该列是否参与合计行；该选项仅在设置了在底部生成合计行时有效。
   * @param colSum boolean
   */
  public void setColSum(boolean colSum) {
    this.colSum = colSum;
  }

  /**
   * 在查询结果底部生成合计行时的过滤条件；只有满足条件的行参与底部合计行;
   * 表达式格式满足java语法：譬如 c1=='1'，其中c1是查询格式列id。
   * @param colSumConditionExp String
   * @see setColSum(boolean sum)
   * @see setExpression(String expression)
   */
  public void setColSumConditionExp(String colSumConditionExp) {
    this.colSumConditionExp = colSumConditionExp;
  }

  /**
   * 查询结果底部生成合计行时，设置“合计”字样。
   * @param sumString String
   */
  public void setSumString(String sumString) {
    this.sumString = sumString;
  }

  /**
   * 是否是摘要列
   * @param colJy boolean
   */
  public void setColJy(boolean colJy){
    this.colJy = colJy;
  }

  /**
   * 设置是否关联字典表
   * @param pConvergeColumn String
   */
  public void setRelateTable(boolean pRelateTable){
    this.relateTable = pRelateTable;
  }

  /**
   * 设置关联字典表对应的列名称
   * @param pTableColName String
   */
  public void setTableColName(String pTableColName) {
    if (pTableColName != null) {
      this.tableColName = pTableColName;
    }
  }

  /**
   * 设置关联字典表名称
   * @param pTableColName String
   */
  public void setTableName(String pTableName) {
    if (pTableName != null) {
      this.tableName = pTableName;
    }
  }
  /**
   * 设置关联字典表对应的列名称
   * @param pTableColName String
   */
  public void setRelateColName(String pRelateColName) {
    if (pRelateColName != null) {
      this.relateColName = pRelateColName;
    }
  }
  /**
   * 是否汇总列
   * @return boolean
   */
  public boolean isConvergeColumn() {
    return this.convergeColumn;
  }


  /**
   * 设置汇总时参照的列名
   * @param pTableColName String
   */
  public void setConvergeColName(String pConvergeColumn) {
    if (pConvergeColumn != null) {
      if(pConvergeColumn.equals("")){
        convergeColumn = false;
      }else{
        convergeColumn = true;
      }
      this.convergeColName = pConvergeColumn;
    }
  }
  /**
   * 是否关联字典表
   * @return boolean
   */
  public boolean isRelateTable(){
    return this.relateTable;
  }

  public boolean isColJy() {
    return colJy;
  }

  /**
   * 获取汇总时参照列名
   * @return String
   */
  public String getConvergeColName() {
    return convergeColName;
  }

  /**
   * 获取列关联字典表名
   * @return String
   */
  public String getTableName() {
    return this.tableName;
  }

  /**
   * 获取列关联字典表对应列名
   */
  public String getTableColName() {
    return this.tableColName;
  }

  /**
   * 获取列关联字典表对应列名
   */
  public String getRelateColName() {
    return this.relateColName;
  }
  /**
   * 获取精度
   * @param dec int
   */
  public int getDec() {
    return dec;
  }

  public boolean isCounted() {
    return counted;
  }

  public String getExpression() {
    return expression;
  }

  public String getViewID() {
    return viewID;
  }

  public String getType() {
    return Type;
  }

  public boolean isSum() {
    return colSum;
  }
  public boolean isKey(){
    return Key;
  }
  public String getColSumConditionExp() {
    return colSumConditionExp;
  }

  public String getSumString() {
    return sumString;
  }
}
