package jservice.jbof.classes.GenerQueryObject.print;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintPaperStub {
    public Icon PaperIcon;
    public String Name;
    public short PaperIndex;
    public JPrintPaperStub() {
    }

    public JPrintPaperStub(Icon p1, String p2, short p3) {
        super();
        PaperIcon = p1;
        Name = p2;
        PaperIndex = p3;
    }

    public Icon getIcon() {
        return PaperIcon;
    }

    public String toString() {
        return Name;
    }
}
