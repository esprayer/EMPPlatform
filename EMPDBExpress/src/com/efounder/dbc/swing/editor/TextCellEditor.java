package com.efounder.dbc.swing.editor;

import java.awt.*;
import javax.swing.*;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.swing.editor.component.TextEditField;
import com.efounder.dctbuilder.data.*;

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
public class TextCellEditor extends DefaultCellEditor {

    protected JComponent helpcomp=null;
    ColumnMetaData cmd;
    private DictModel model;

    public TextCellEditor(ColumnMetaData cmd){
        super(new JTextField());
        helpcomp=new TextEditField((JTextField)editorComponent,cmd);
        helpcomp.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        this.cmd=cmd;
        initDictModel();
     }

    /**
     * 初始化DictModel
     */
    private void initDictModel() {
        if (cmd.getLostValue("DictModelObject", null) != null) {
            model = (DictModel) cmd.getLostValue("DictModelObject", null);
        }
    }

    public Object getCellEditorValue() {
//        String value=(String)super.getCellEditorValue();

        return model.getDataSet().getRowSet().getString(cmd.getColid(), "");
     }

     public Component getComponent() {
         return helpcomp;
     }

     public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected,
                                                  int row, int column) {
         super.getTableCellEditorComponent(table,value,isSelected,row,column);
         return helpcomp;
     }

//     /**
//     *
//     * @return String
//     */
//    private String getServerName() {
//    	String colServerName = cmd.getExtAttriValue("COL_OPT", "=", ";", "ServerName");
//        if(colServerName != null && colServerName.trim().length() > 0){
//			return colServerName;
//        }else if(cmd.getString("ServerName","") != null && cmd.getString("ServerName","").trim().length() > 0){
//			return cmd.getString("ServerName","");
//        }else if (model != null){
//        	return model.getCdsParam().getServerName();
//        }
//        return "";
//    }
}
