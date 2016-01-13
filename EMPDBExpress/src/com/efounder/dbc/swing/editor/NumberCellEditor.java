package com.efounder.dbc.swing.editor;

import javax.swing.*;
import java.math.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

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
public class NumberCellEditor extends DefaultCellEditor implements FocusListener{
    public NumberCellEditor() {
        super(new JTextField());
//          JFormattedTextField text = new JFormattedTextField();
          JTextField text = (JTextField) getComponent();
          text.setHorizontalAlignment(JTextField.RIGHT);
          text.addFocusListener(this);
          text.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    }
    // 注释掉，否则有些虚拟的数值列报错误
//    public Object getCellEditorValue() {
//        JTextField box = (JTextField) getComponent();
//        String bh = box.getText();
//        try{
//            BigDecimal bd = new BigDecimal(bh);
//            return bd;
//        }catch(Exception e){
//            return new BigDecimal("0");
//        }
//    }
 public Component getTableCellEditorComponent(JTable table, Object value,
                                              boolean isSelected,
                                              int row, int column) {
     JTextField text = (JTextField) getComponent();
     if(value==null){
         return super.getTableCellEditorComponent(table,value,isSelected,row,column);
     }
//     text.setText(value.toString());
     String bh = value.toString();
     // 防止科学计数法
     NumberFormat fmt = new java.text.DecimalFormat();
     fmt.setMaximumFractionDigits(100);
     fmt.setGroupingUsed(false);
//     double d=Double.parseDouble(bh);
     if (value instanceof Number)
         text.setText(fmt.format(value));
     else
         text.setText(value.toString());
//     int size=0;
//     if(bh.indexOf(".")!=-1){
//         for (int i = bh.length() - 1; i >= 0; i--) {
//             size = i;
//             if (bh.charAt(i) == '.')
//                 break;
//             if (bh.charAt(i) != '0')
//                 break;
//         }
//         bh=bh.substring(0,size);
//         try{
//
//         }catch(Exception e){
//
//         }
//     }
     return text;
 }

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
         JTextField text = (JTextField) getComponent();
         this.stopCellEditing();
    }

}
