package com.efounder.eai.ui.action.calcaction;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public interface CalcActionInterface {
  public boolean canCalc();
  public boolean canCheck();
  public boolean canCheckReport();
  public boolean canChart();
  public void calcNode();
  public void checkNode();
  public void checkReportNode();
  public void chartNode();
}
