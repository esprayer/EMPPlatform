package jreport.swing.classes.ReportBook;

import java.awt.*;
import jreport.swing.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ICellEditor {
  public Component getComponent();
  // ��ȡ�༩���
  public Component getEditorComponent(JReportView ReportView, Object value,boolean isSelected,int Row,int Col);
  // ��ʼ�༩
  public void startEditing(int Row,int Col,JUnitInfo UI,JBDUnitInfo BDUI);
  // �����༩
  public void endEditing(int Row,int Col,JUnitInfo UI,JBDUnitInfo BDUI);
  // ȡ���༩
  public void cancelEditing(int Row,int Col,JUnitInfo UI,JBDUnitInfo BDUI);
  // �༩��
  public void show(boolean v);
  // ������Ӧ�Ķ��ƶ���
  public void setCustomObject(Object Custom);
}
