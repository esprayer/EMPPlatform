package jreport.swing.classes.util;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SearchInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.util.Language");
  public int    mRow  = 0;
  public int    mCol  = 0;
  public String mText = "";
  public int    mJygsIndex = 0;
  public SearchInfo() {
  }

}