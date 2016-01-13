package jreport.foundation.classes.data;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CodeFileStub {
  protected String eFileName  = null;
  protected String eFileName_x = null;
  protected String cFileName  = null;
  public void setCFileName(String cFileName) {
    this.cFileName = cFileName;
  }

  public void setEFileName(String eFileName) {
    this.eFileName = eFileName;
  }

  public void setEFileName_x(String eFileName_x) {
    this.eFileName_x = eFileName_x;
  }

  public String getEFileName() {
    return eFileName;
  }

  public String getCFileName() {
    return cFileName;
  }

  public String getEFileName_x() {
    return eFileName_x;
  }

  public String getFileName() {
    return eFileName_x+".TXT";
  }
  public String getKey() {
    if ( eFileName == null || cFileName == null ) return null;
    return eFileName+cFileName+".TXT";
  }

  public String toString() {
    return cFileName;
  }
}
