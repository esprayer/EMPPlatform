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
  public int mRow = 0;//��
  public int mCol = 0;//��
  /**
   * У�鹫ʽʱ,ÿһ��У�鹫ʽ���ж�����λ��,�����mFormulas�е�JJygsInfo��
   */
  public int mRowOffset = 0;//��λ��
  public int mColOffset = 0;//��λ��
  /**
   * ��ʽ�б�
   * ���㹫ʽ:ֻ��һ�����㹫ʽԪ��
   * У�鹫ʽ:ΪJJygsInfo���б�
   */
  public Vector mFormulas = new Vector();
  public FormulaInfo() {
  }

}