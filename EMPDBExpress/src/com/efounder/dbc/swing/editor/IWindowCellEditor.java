package com.efounder.dbc.swing.editor;

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
public interface IWindowCellEditor {

    boolean isVisible();
    void setVisible(boolean visible);

    void setValue(Object value);
    Object getValue();

    void setValueList(java.util.List valueList);
    java.util.List getValueList();

    boolean isResultOK();

}
