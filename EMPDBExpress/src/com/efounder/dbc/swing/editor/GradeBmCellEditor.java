package com.efounder.dbc.swing.editor;

import javax.swing.DefaultCellEditor;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.pub.util.StringFunction;

import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;

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
public class GradeBmCellEditor extends DefaultCellEditor {
    DictMetaDataEx dme;
    int jslen=0;
    String parent="";
 public GradeBmCellEditor(DictMetaDataEx dme) {
     super(new JTextField());
     this.dme=dme;
 }

 public Object getCellEditorValue() {
     JTextField box = (JTextField) getComponent();
     String bh = box.getText();
     if (dme.isGradeDict()) {
         if (bh.length() < jslen)
             bh = StringFunction.fillString("", '0', jslen - bh.length()) + bh;
         else
             bh = bh.substring(0, jslen);
         bh = parent + bh;
     }
     return bh;
 }

 public Component getTableCellEditorComponent(JTable table, Object value,
                                              boolean isSelected,
                                              int row, int column) {
     JTextField text = (JTextField) getComponent();
     String bh = "";
     if (value == null)
         return text;
     String newbh = (String) value;
     bh = newbh;
     int len = newbh.length();
     parent = "";
     if (dme.isGradeDict()) {
         int[] stru = dme.getStrulen();
         jslen = stru[0];
         for (int i = 0; i < stru.length; i++) {
             if (len <= stru[i]) {
                 //add by luody
                 if (i > 0)
                     jslen = stru[i] - stru[i - 1];
                 //end add
                 break;
             } else {
                 newbh = bh.substring(stru[i]);
                 parent = bh.substring(0, stru[i]);
             }
             if (i > 0)
                 jslen = stru[i] - stru[i - 1];
         }
     }
     text.setText(newbh);
     return text;
 }
}
