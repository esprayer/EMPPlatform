package jreport.swing.classes.wizard;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JLimitObjectStub {
  //id="YEAR" type="N" help="HelpYear" text="会计年度" des1="" des2=""
  public String id;
  public String type;
  public String help;
  public String text;
  public String des1;
  public String des2;
  public String value;
  public String defvalue;
  public String tbName;
  public String bhCol;
  public String mcCol;
  public String struCol;
  public JWizardHelpObject HelpObject;
  public JFunctionStub     FunctionStub;
  public JLimitObjectStub() {
  }

    public String getBhCol() {
        return bhCol;
    }

    public String getDefvalue() {
        return defvalue;
    }

    public String getDes1() {
        return des1;
    }

    public String getDes2() {
        return des2;
    }

    public String getHelp() {
        return help;
    }

    public String getId() {
        return id;
    }

    public String getMcCol() {
        return mcCol;
    }

    public String getStruCol() {
        return struCol;
    }

    public String getTbName() {
        return tbName;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public JFunctionStub getFunctionStub() {
        return FunctionStub;
    }

    public JWizardHelpObject getHelpObject() {
        return HelpObject;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public void setStruCol(String struCol) {
        this.struCol = struCol;
    }

    public void setMcCol(String mcCol) {
        this.mcCol = mcCol;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDes2(String des2) {
        this.des2 = des2;
    }

    public void setDes1(String des1) {
        this.des1 = des1;
    }

    public void setDefvalue(String defvalue) {
        this.defvalue = defvalue;
    }

    public void setBhCol(String bhCol) {
        this.bhCol = bhCol;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setHelpObject(JWizardHelpObject HelpObject) {
        this.HelpObject = HelpObject;
    }
}
