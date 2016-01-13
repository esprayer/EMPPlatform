package jdatareport.dof.classes.report.filter;

import org.nfunk.jep.*;
import jdatareport.dof.classes.report.ext.condition.util.*;
import com.pansoft.report.table.*;
import jdatareport.dof.classes.report.QueryDataSet;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRRowFilter {
  /**
   *
   */
  private JEP mJepParser = new JEP();
  private JCExpression mFilterExp = null;

  /**
   *
   */
  public JDRRowFilter() {
    mJepParser.addStandardFunctions();
    mJepParser.addStandardConstants();
    mJepParser.addFunction("like", new FuncLike()); // Add the custom function
    mJepParser.addFunction("substring", new FuncSubstring());
  }

  /**
   *
   * @param filterExp
   */
  public JDRRowFilter(JCExpression filterExp) {
    this();
    this.mFilterExp = filterExp;
  }

  /**
   *
   */
  public void setFilterExpressions(JCExpression filterExp) {
    this.mFilterExp = filterExp;
  }

  /**
   *
   * @return
   */
  public JCExpression getFilterExpressions() {
    return this.mFilterExp;
  }


  /**
   * 过滤数据
   * @param queryDataSet QueryDataSet 查询数据全集
   * @param data String[]             一条数据
   * @param colCount int
   * @return boolean
   */
  public boolean isAcceptable(String[] data,int colCount) throws Exception{
    if (mFilterExp != null && data != null) {
      String strExp = JDRFilterUtils.normalize(data, colCount, mFilterExp);
          String a = com.pansoft.pub.util.ESPParseExpression.getResult(strExp);
          if(a.equals("true")){
            return true;
          }else{
            return false;
          }
      }
    return true;
  }
}
