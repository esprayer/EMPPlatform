package com.efounder.bz.dbform.component.dc.table.render;

import javax.swing.*;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.awt.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FooterNumberRender extends FooterTextRender{
  String pattern;
    public FooterNumberRender(JTable table,String pattern) {
        super(table);
        setHorizontalAlignment(RIGHT);
        this.pattern=pattern;

    }
    public void setPattern(String p){
      pattern=p;

    }
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        setValue(value);
        return this;
    }

    public void setValue(Object value) {
           String sValue;
           double dValue = 0;
           if(value==null||value.equals("")){
            this.setText("");
            return;
           }
           Number number = BigDecimal.ZERO;
           if (value instanceof Number) {
             number = (Number)value;
           }
           else if (value instanceof String) {
               try{
                   number = new BigDecimal((String) value);
               }catch(Exception e){
                    this.setText((String) value);
                    return;
               }
           }
           if(number.doubleValue()<0)
            setForeground(Color.red);
          else
            setForeground(Color.black);
          if (pattern == null || pattern.trim().length() == 0)
             pattern = "#,##0.00;#,##0.00";
           NumberFormat nf = new DecimalFormat(pattern);
           String aa= nf.format(number);
           this.setText(aa);
    }
}
