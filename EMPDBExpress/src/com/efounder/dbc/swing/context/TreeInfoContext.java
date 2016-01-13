package com.efounder.dbc.swing.context;

import javax.swing.tree.TreeNode;

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
public class TreeInfoContext extends DbSwingContext {
    public TreeInfoContext() {
    }
    public boolean isGradeDict(){
        if(getBMStru().equals("")||"".equals(getJSColumn().trim()))
        return false;
     else return true;
    }

}
