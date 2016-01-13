package com.efounder.dbc.swing.help;

import com.efounder.pfc.dialog.JPDialog;
import java.awt.Container;

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
public interface IDictColHelp {
    public void findValue(String value);
    public void initObj(Container parent,Object o);
    public Object getResult();
}
