package jreport.foundation.classes.data;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CodeStub {
//  protected int    Index = 0;
  protected String Index = null;
  protected String Value = null;
  public void setValue(String Value) {
    this.Value = Value;
  }

  public String getValue() {
    return Value;
  }

  public void setIndex(String inPut) {
    this.Index = inPut;
  }

  public int getIndex() {
	  int iRes = 0;
	  try {
		  iRes = Integer.parseInt(Index);
	  } catch(Exception ex) {
	  }
	  return iRes;
  }

  public CodeStub() {
  }
  public String toString() {
    String sIndex = String.valueOf(Index);
    int Len = 4 - sIndex.length();
    for(int i=1;i<=Len;i++) {
      sIndex += " ";
    }
    return sIndex + " | " + Value;
  }
}
