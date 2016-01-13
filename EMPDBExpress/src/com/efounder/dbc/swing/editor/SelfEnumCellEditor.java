package com.efounder.dbc.swing.editor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListDataListener;

import com.efounder.dbc.data.DataItem;
import com.efounder.dctbuilder.data.ColumnMetaData;

/**
 * 
 * @author zhangfutao
 *
 */
public class SelfEnumCellEditor extends DefaultCellEditor {
	private Map hash = null;
	private List keyList = null;
	private ColumnMetaData colmeta = null;
	private JComboBox box = null;
	
	public SelfEnumCellEditor(Map hash) {
		this(hash, null);
	}

	public SelfEnumCellEditor(Map hash, List keyList) {
		this(hash, keyList, null);
	}

	/**
	 * 
	 * @param hash
	 *            Map
	 * @param keyList
	 *            List
	 * @param colmeta
	 *            ColumnMetaData
	 */
	public SelfEnumCellEditor(Map hash, List keyList, ColumnMetaData colmeta) {
		super(new JComboBox());
		this.hash = hash;
		this.keyList = keyList;
		this.colmeta = colmeta;
		this.box = (JComboBox) editorComponent;
		createBox(box, keyList);
	}

	protected void createBox(JComboBox box, List keyList) {
		box.setEditable(true);
		box.removeAllItems();
		if (keyList == null) {
			Object data[] = hash.keySet().toArray();
			keyList = new ArrayList();
			for (int i = 0; i < data.length; i++) {
				keyList.add(data[i]);
			}
			java.util.Collections.sort(keyList, null);
		}
		Object o = null;
		for (int i = 0; i < keyList.size(); i++) {
			o = keyList.get(i);
			box.addItem(new DataItem((String) o, (String) hash.get(o)));
		}
	}
	
	public Object getCellEditorValue() {
		Object itemObject = box.getSelectedItem();
		if (itemObject == null) {
			return "";
		} else {
			if (itemObject instanceof DataItem) {
				return ((DataItem) itemObject).getBh();
			} else if (itemObject instanceof String) {
				return itemObject.toString();
			}
		}
		return null;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value != null) {
			DataItem di = new DataItem(value.toString(), "");
			return super.getTableCellEditorComponent(table, di, isSelected,
					row, column);
		} else {
			return super.getTableCellEditorComponent(table, value, isSelected,
					row, column);
		}
	}

	public void addElement(Object obj) {
		DataItem item = (DataItem)obj;
		hash.put(item.getBh(), item.getName());
		keyList.add(item.getBh());
		box.addItem(obj);
	}

	public void removeElement(Object obj) {
		DataItem item = (DataItem)obj;
		hash.remove(item.getBh());
		keyList.remove(item.getBh());
		box.removeItem(obj);
	}

	public void insertElementAt(Object obj, int index) {
		DataItem item = (DataItem)obj;
		hash.put(item.getBh(), item.getName());
		keyList.add(item.getBh());
		box.insertItemAt(obj, index);
	}

	public void removeElementAt(int index) {
		DataItem item = (DataItem)box.getItemAt(index);
		hash.remove(item.getBh());
		keyList.remove(item.getBh());
		box.removeItem(index);
	}

	public int getSize() {
		return box.getItemCount();
	}

	public void setSelectedItem(Object anItem) {
		DataItem item = (DataItem)anItem;
		hash.put(item.getBh(), item.getName());
		keyList.add(item.getBh());
		box.setSelectedItem(anItem);
	}
	
	public void setHash(Map hash){
		this.hash = hash;
		this.keyList.clear();
		Iterator keys = hash.keySet().iterator();
		while(keys.hasNext()){
			this.keyList.add((String)keys.next());
		}
		createBox(box, keyList);
	}
	
	public void setColumnMetaData(ColumnMetaData colmeta) {
		this.colmeta = colmeta;
		String view = colmeta.getColView();
        this.hash = new Hashtable();
        this.keyList = new ArrayList();
        String[] item = view.split(";");
        for (int i = 0; i < item.length; i++) {
            if (item[i] == null || item[i].indexOf(":") < 0) continue;
            String s1 = item[i].substring(0, item[i].indexOf(":"));
            String s2 = item[i].substring(item[i].indexOf(":") +1);
            hash.put(s1, s2);
            keyList.add(s1);
        }
        createBox(box, keyList);
	}
	
	public Object getSelectedItem() {
		return box.getSelectedItem();
	}
	
	public Object getElementAt(int index) {
		return box.getItemAt(index);
	}
	
	public Map getHash() {
		return hash;
	}
	
	public List getKeyList() {
		return keyList;
	}
	
	public ColumnMetaData getColumnMetaData() {
		return colmeta;
	}
	
	public void addListDataListener(ListDataListener l) {
	}

	public void removeListDataListener(ListDataListener l) {
	}
	
}
