package com.efounder.dbc.swing.help;

import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.pfc.dialog.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CommonHelpObject {
    public CommonHelpObject() {
    }
    public static String showHelp(StubObject so,String title){
        CommonHelpWindow chw= new CommonHelpWindow(EAI.EA.getMainWindow(),title,so);
        chw.pack();
        chw.CenterWindow();
        chw.setVisible(true);
        if(chw.Result==JPDialog.RESULT_OK){
            return (String)chw.getResultObject();
        }
        return null;
    }
}
