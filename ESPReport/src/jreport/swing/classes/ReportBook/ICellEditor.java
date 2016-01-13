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
  // 获取编缉组件
  public Component getEditorComponent(JReportView ReportView, Object value,boolean isSelected,int Row,int Col);
  // 开始编缉
  public void startEditing(int Row,int Col,JUnitInfo UI,JBDUnitInfo BDUI);
  // 结束编缉
  public void endEditing(int Row,int Col,JUnitInfo UI,JBDUnitInfo BDUI);
  // 取消编缉
  public void cancelEditing(int Row,int Col,JUnitInfo UI,JBDUnitInfo BDUI);
  // 编缉器
  public void show(boolean v);
  // 设置相应的定制对象
  public void setCustomObject(Object Custom);
}
