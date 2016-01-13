package jreport.model.classes.analyze;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface AnalyzeReportConstants {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");
  public static final String DEFAULT_NUMBER_FORMAT="#,##0.00";
  public static final int TWIPSE_SCALE = 16;
  public static final int DEFAULT_ROW_HEIGHT = 25*TWIPSE_SCALE;
}