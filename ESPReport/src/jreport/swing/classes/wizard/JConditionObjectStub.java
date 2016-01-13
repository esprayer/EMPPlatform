package jreport.swing.classes.wizard;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JConditionObjectStub {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
    //<O id="KMBH" type="N" text="科目编号"/>
    public String id = "";
    public String type = "";
    public String text = "";
    /**
     *
     * show:取子串(text,2,3)
     * store:SUBSTRING(id,2,3)
     */
    public JConditionObjectStub() {
    }

    /**
     *
     * @return
     */
    public String toString() {
        return text;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }
}
