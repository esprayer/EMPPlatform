package jreport.swing.classes.wizard;

import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JFunctionTypeStub {
  //<T id="BB" text="±¨±íÀà"/>\
  public Vector FunctionList = new Vector();
  public String id;
  public String text;
  public String dobj;
  public String cobj;
  public JFunctionTypeStub() {
  }
  public String toString() {
    return text;
  }

    public String getCobj() {
        return cobj;
    }

    public String getDobj() {
        return dobj;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Vector getFunctionList() {
        return FunctionList;
    }

    public void setCobj(String cobj) {
        this.cobj = cobj;
    }

    public void setDobj(String dobj) {
        this.dobj = dobj;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFunctionList(Vector FunctionList) {
        this.FunctionList = FunctionList;
    }
}
