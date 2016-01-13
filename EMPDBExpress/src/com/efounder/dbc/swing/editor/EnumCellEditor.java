package com.efounder.dbc.swing.editor;

import java.util.*;
import java.util.List;

import java.awt.*;
import javax.swing.*;

import javax.swing.event.ListDataListener;

import com.efounder.dbc.data.DataItem;
import com.efounder.dctbuilder.data.ColumnMetaData;

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
public class EnumCellEditor
    extends DefaultCellEditor {
  Map hash;
  List keyList=null;

  private ColumnMetaData colmeta;

  /**
   *
   * @param colmeta ColumnMetaData
   */
  public void setColumnMetaData(ColumnMetaData colmeta) {
      this.colmeta = colmeta;
  }

  public EnumCellEditor(Map hash){
      super(new JComboBox());
      this.hash=hash;
      JComboBox box=(JComboBox)editorComponent;
      createBox(box,null);
      box.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
  }

  public EnumCellEditor(Map hash, List keyList) {
      super(new JComboBox());
      this.hash = hash;
      this.keyList = keyList;
      JComboBox box = (JComboBox) editorComponent;
      createBox(box, keyList);
  }

  /**
   *
   * @param hash Map
   * @param keyList List
   * @param colmeta ColumnMetaData
   */
  public EnumCellEditor(Map hash, List keyList, ColumnMetaData colmeta) {
      this(hash, keyList);
      this.colmeta = colmeta;
      if (colmeta != null && colmeta.isEditable()) {
          JComboBox box = (JComboBox) editorComponent;
          box.setEditable(true);
      }
  }

  /**
   * 设置是否可以编辑
   * @param editable boolean
   */
  public void setEditable(boolean editable){
      JComboBox box = (JComboBox) editorComponent;
      box.setEditable(editable);
  }

  // --------- 此处修改，使下拉框可编辑   zhangft 20100208 ------------
//  protected void createBox(JComboBox box,List keyList){
//     if(keyList==null){
//         Object data[] = hash.keySet().toArray();
//          keyList = new ArrayList();
//         for (int i = 0; i < data.length; i++) {
//             keyList.add(data[i]);
//         }
//         java.util.Collections.sort(keyList, null);
//     }
//     Object o;
//     for (int i = 0; i < keyList.size(); i++) {
//         o = keyList.get(i);
//         box.addItem(new DataItem((String) o, (String) hash.get(o)));
//     }
// }
// public Object getCellEditorValue() {
//   JComboBox box=(JComboBox)editorComponent;
//   DataItem di=(DataItem)box.getSelectedItem();
//   if(di==null)return "";
//      return di.getBh();
//  }
//  public Component getTableCellEditorComponent(JTable table, Object value,
//                                               boolean isSelected,
//                                               int row, int column) {
//
//    DataItem di=null;
//    if(value!=null)
//     di=new DataItem(value.toString(),"");
//   else
//       return super.getTableCellEditorComponent(table,value,isSelected,row,column);
//     return super.getTableCellEditorComponent(table,di,isSelected,row,column);
//  }
  protected void createBox(JComboBox box,List keyList){
     if(keyList==null){
         Object data[] = hash.keySet().toArray();
          keyList = new ArrayList();
         for (int i = 0; i < data.length; i++) {
             keyList.add(data[i]);
         }
         java.util.Collections.sort(keyList, null);
     }
     Object o;
     for (int i = 0; i < keyList.size(); i++) {
         o = keyList.get(i);
         box.addItem(new DataItem((String) o, (String) hash.get(o)));
     }
 }
 public Object getCellEditorValue() {
     JComboBox box = (JComboBox) editorComponent;
     Object itemObject = box.getSelectedItem();
     if (itemObject == null) {
         return "";
     }
     else {
         if (itemObject instanceof DataItem) {
             return ( (DataItem) itemObject).getBh();
         }
         else if (itemObject instanceof String) {
             return itemObject.toString();
         }
     }
     return null;
 }
  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected,
                                               int row, int column) {
    if (value != null){
        DataItem di = new DataItem(value.toString(), "");

        return super.getTableCellEditorComponent(table, di, isSelected, row,
                                                 column);
    }
    else{
        return super.getTableCellEditorComponent(table, value, isSelected,
                                                 row, column);
    }
  }
  // --------- 此处修改,使下拉框可编辑   zhangft 20100208 ------------


  public Map getHash() {
    return hash;
  }

    public void setSelectedItem(Object anItem) {
    }

    public Object getSelectedItem() {
        return null;
    }

    public void addElement(Object obj) {
    }

    public void removeElement(Object obj) {
    }

    public void insertElementAt(Object obj, int index) {
    }

    public void removeElementAt(int index) {
    }

    public int getSize() {
        return 0;
    }

    public Object getElementAt(int index) {
        return null;
    }

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }

}
