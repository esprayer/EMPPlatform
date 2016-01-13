package com.efounder.dbc.swing.editor;

import java.text.*;
import java.util.*;

import javax.swing.*;

import com.efounder.dctbuilder.data.ColumnMetaData;
import java.awt.Component;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TimeCellEditor
    extends DefaultCellEditor {
    private ColumnMetaData cmd;
    protected JComponent helpcomp=null;
    int datetype = 0; //0 java.sql.date 1:java.util.date

    public TimeCellEditor(ColumnMetaData cmd,int datetype) {
        super(new JTextField());
        helpcomp=new TimeTextField((JTextField)editorComponent,cmd);
        this.cmd=cmd;
        this.datetype = datetype;
    }

    public Object getCellEditorValue() {
        String value = null;
        try {
            value = (String) super.getCellEditorValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    /**
     * 根据值的类型以及返回格式，对值进行加工
     *
     * @param date Date
     * @return Object
     */
    private Object getSelfValue(Date date){
        //获取显示掩码
        String format = cmd.getString("COL_VIEW", "");  //元数据定义
        if (format.trim().length() == 0){
            format = cmd.getString("VALUE_FORMAT", "");
        }
        if ("C".equals(cmd.getColType())) {
            if (format.trim().length() == 0) {
                format = "yyyyMMdd";  //默认
            }
        }
        //
        if (format.trim().length() > 0){
            SimpleDateFormat f;
            try {
                 f = new SimpleDateFormat(format);
                 return f.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Component getComponent() {
        return helpcomp;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        return helpcomp;
    }

    public void setColumnMetaData(ColumnMetaData cmd) {
        this.cmd = cmd;
    }
}
