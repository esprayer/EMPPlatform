package jreport.swing.classes.ReportBook;

import java.io.Serializable;
import java.util.ResourceBundle;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class JReportAdjustStub  implements Serializable {
  static ResourceBundle res = ResourceBundle.getBundle("jreport.jbof.classes.BOFReportObject.ReportExplorer.dlg.Language");

  public String TZZD_BH;
  public String TZZD_MC;
  public String TZZD_FH;
  public String TZZD_XS;
  public String TZZD_JD;
  public String TZZD_DW;



  public JReportAdjustStub() {
  }
  public String toString() {
    return "["+TZZD_BH + "]-[" + TZZD_MC+"]";
  }

}
