package jreport.swing.classes.util;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FormulaInfo {
  public int mRow = 0;//行
  public int mCol = 0;//列
  /**
   * 校验公式时,每一个校验公式都有独立的位移,存放在mFormulas中的JJygsInfo中
   */
  public int mRowOffset = 0;//行位移
  public int mColOffset = 0;//列位移
  /**
   * 公式列表
   * 计算公式:只有一个计算公式元素
   * 校验公式:为JJygsInfo的列表
   */
  public Vector mFormulas = new Vector();
  public FormulaInfo() {
  }

}