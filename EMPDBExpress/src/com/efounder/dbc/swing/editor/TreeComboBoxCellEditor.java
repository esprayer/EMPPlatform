package com.efounder.dbc.swing.editor;

import com.sunking.swing.*;
import javax.swing.*;
import javax.swing.tree.TreePath;

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
public class TreeComboBoxCellEditor
    extends DefaultCellEditor {
  public TreeComboBoxCellEditor(JTree tree) {
    super(new JTreeComboBox(tree){
      public void setSelectedItem(Object o){
//       tree.setSelectionPath((TreePath)o);
//       setItemValue(o);
//       getModel().setSelectedItem(o);

   }


    });
  }
}
