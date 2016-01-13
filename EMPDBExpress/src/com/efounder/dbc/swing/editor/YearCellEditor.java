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
public class YearCellEditor extends MonthCellEditor{
    public YearCellEditor() {
        super();
    }
    protected String[] getData() {
        String[] data = new String[40];
        for (int i = 0; i < data.length; i++) {
            data[i] = "" + (i + 2000);
        }
        return data;
    }
}
