package jservice.jdal.classes.query.dataset;

public class PDataSetStru {
  /**
   * 支持的数据类型:字符类型/数值类型
   */
  public static String CHAR_TYPE = "C";
  public static String NUM_TYPE = "N";
  /**
   * 自增字段（从1开始），用于序号列，此时 将ID的值设置为 ""
   */
  public static String INCREMENT_VALUE = "++";

  /**
   * 列名（字段名）
   */
  private String ID = null;
  /**
   * 显示格式列ID
   */
  private String viewID = null;
  /**
   * 类型: 字符C/数值N
   */
  private String type = CHAR_TYPE;

  /**
   * 该列是否参与合计行
   */
  private boolean colSum = false;

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
   * 是否计算列
   */
  private boolean counted = false;

  /**
   * 列运算表达式: c5=c6+c7+c8
   */
  private String expression = null;

  public PDataSetStru() {
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
  public void setID(String ID) {
    this.ID = ID;
  }

  /**
   * 获取字段名。
   * @return String
   * @see setID(String ID)
   */
  public String getID() {
    return ID;
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
    this.type = type;
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
    return type;
  }

  public boolean isColSum() {
    return colSum;
  }

  public String getColSumConditionExp() {
    return colSumConditionExp;
  }

  public String getSumString() {
    return sumString;
  }

}
