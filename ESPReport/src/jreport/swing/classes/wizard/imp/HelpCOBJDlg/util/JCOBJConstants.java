package jreport.swing.classes.wizard.imp.HelpCOBJDlg.util;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */


public interface JCOBJConstants {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.Language");
    /**
     *
     */
    public final static String[] OP_LEFT = new String[] {"", "("};
    public final static String[] OP_COMPARE = new String[] {""," = ", " > ", " >= ", " < ", " <= ", " <> "," IN "," LIKE "};
    public final static String[] OP_RIGHT = new String[] {"", ")"};
    public final static String[] OP_CONN = new String[] {"",res.getString("String_42"), res.getString("String_43")};

    public static final String[] COL_NAMES = {res.getString("String_44"), res.getString("String_45"), res.getString("String_46"), res.getString("String_47"), res.getString("String_48"), res.getString("String_49")};

    public static final String FILE_NAME = "wizard.xml";
    public static final String COBJ_ROOT_NAME = "BaseConditionObjects";

    public static final int DEFAULT_ROW_HEIGHT = 20;
}
