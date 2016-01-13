package jreport.swing.classes.wizard;

import java.util.ResourceBundle;

public class JDataObjectStub {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
  //<O id="NCJF" type="N" text="年初借方" des1="去年年末的借方余额"/>
  public String id;
  public String type;
  public String text;
  public String des;
  public JDataObjectStub() {
  }

  public String toString() {
    return text + " (" + id + ")";
  }

    public String getDes() {
        return des;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }
}
