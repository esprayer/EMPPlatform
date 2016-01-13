package com.efounder.dbc.swing.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.*;
import java.util.*;

import javax.swing.*;

import com.pub.util.ESPKeyValueUtil;
import com.sunking.swing.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.borland.dx.dataset.Variant;

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
public class DateCellEditor
    extends DefaultCellEditor implements KeyListener {

    //add by gengeng 2009-9-24
    private ColumnMetaData column;

    JDatePickerEx jdp = null;
    JTextField textField = null;
    int datetype = 0; //0 java.sql.date 1:java.util.date

    public DateCellEditor() {
        this(new JDatePickerEx());
    }

    public DateCellEditor(int type) {
        this(new JDatePickerEx());
        datetype = type;
    }

    public DateCellEditor(final JComboBox comboBox) {
        super(comboBox);
        jdp = (JDatePickerEx) editorComponent;
        textField = ( (JTextField)jdp.getEditor().getEditorComponent());
        textField.addKeyListener(this);
    }

    /**
     * 设置该列的元数据
     * add by gengeng 2009-9-24
     *
     * @param column ColumnMetaData
     */
    public void setColumnMetaData(ColumnMetaData column){
        this.column = column;
    }

    public Object getCellEditorValue() {
        Date d = null;
        try {
            d = jdp.getSelectedDate();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        //值为"1644-01-01"表示为空
        if(jdp.getSelectedItem() != null && jdp.getSelectedItem().toString().startsWith("1644-01-01")){
            return "";
        }
        /**
         * 改进以支持类型VARCHAR2的，还需要使用DATE控件的列
         * 可以通过设置日期的返回格式，返回任何格式的数据
         * 需要在列的元数据中增加一个参数VALUE_FORMAT：有值是新式用法，没值是老式用法
         *
         * modified by gengeng 2009-9-24
         */
        if (column != null) {
            Object o = getSelfValue(d);
            if (o != null) {
                return o;
            }
        }
        String dataType = "";
        char   cDataTye;
        if (datetype == 0) {
            if (column != null) {
                DictModel model = (DictModel) column.getLostValue("DictModelObject", null);
                if (model != null) {
                    dataType = model.getDataSet().hasColumn(column.getColid()).getColType();
                }
            }
            cDataTye = dataType.charAt(0);
            //WebLogic DATE->TIMESTAMP
            if (cDataTye == Variant.TIMESTAMP) {
                java.sql.Timestamp time = new java.sql.Timestamp(d.getTime());
                return time;
            }
            java.sql.Date sd = new java.sql.Date(d.getTime());
            return sd;
        } else {
            return d;
        }
    }

    /**
     * 根据值的类型以及返回格式，对值进行加工
     * add by gengeng 2009-10-14
     *
     * @param date Date
     * @return Object
     */
    private Object getSelfValue(Date date){
    	String format = "";
        if ("C".equals(column.getColType())) {
            format = "yyyyMMdd";
            if (column.getString("VALUE_FORMAT", "") != null && column.getString("VALUE_FORMAT", "").trim().length() > 0) 
            	format = column.getString("VALUE_FORMAT", "");
            if(column.getColView() != null && column.getColView().trim().length() > 0)
            	format = column.getColView();
			String valueformat = column.getExtAttriValue("COL_OPT",
					ESPKeyValueUtil._DEFAULT_CONNECTOR_,
					ESPKeyValueUtil._DEFAULT_DELIMETER_, "ValueFormat");
    		if(valueformat != null && valueformat.trim().length() > 0)
    			format = valueformat;
        }else{
        	format = column.getString("VALUE_FORMAT", "");
            if (column.getColView() != null && column.getColView().trim().length() > 0) 
            	format = column.getColView();
        }
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

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		//如果删除就将值设为"1644-01-01"
		if(e.getKeyCode()==KeyEvent.VK_DELETE){
            jdp.setSelectedItem("1644-01-01");
            textField.setText("");
        }
	}

	public void keyReleased(KeyEvent e) {
		
	}

}
