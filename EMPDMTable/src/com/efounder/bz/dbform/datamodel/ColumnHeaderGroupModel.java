package com.efounder.bz.dbform.datamodel;

import com.efounder.bz.dbform.datamodel.column.*;
import javax.swing.table.TableColumnModel;
import java.beans.PropertyChangeListener;
import javax.swing.table.TableColumn;
import java.util.Enumeration;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableColumnModelEvent;

public class ColumnHeaderGroupModel {
  /**
   *
   */
  public ColumnHeaderGroupModel() {

  }

  /**
   *
   */
  protected ColumnModel columnModel = null;
  /**
   *
   * @return ColumnModel
   */
  public ColumnModel getColumnModel() {
    return columnModel;
  }
  /**
   *
   * @param columnModel ColumnModel
   */
  public void setColumnModel(ColumnModel columnModel) {
    this.columnModel = columnModel;
  }


  /**
   *
   * @param column Column
   * @return boolean
   */
  public boolean existsColumn(Column column) {
//    if ( this.childList == null || childList.size() == 0 ) return false;
//    ColumnGroup columnGroup = null;
//    for(int i=0;i<childList.size();i++) {
//      columnGroup = (ColumnGroup)childList.get(i);
//      if ( columnGroup.existsColumn(column) ) return true;
//    }
    return false;
  }

  /**
   *
   * @param dataComponent DataComponent
   */
//  public int insertDataComponent(DataComponent dataComponent) {
//    if ( !(dataComponent instanceof ColumnGroup) ) return -1;
//    int index = super.insertDataComponent(dataComponent);
//    ColumnGroup columnGroup = (ColumnGroup)dataComponent;
//    if ( this.getColumnModel() != null )
//      columnGroup.addPropertyChangeListener(this.getColumnModel());
//    fireColumnMoved();
//    return index;
//  }
  /**
   *
   * @param e TableColumnModelEvent
   */
  public void fireColumnMoved() {
    if ( getColumnModel() == null ) return;
    if ( getColumnModel().getColumnCount() == 0 ) return;
    TableColumnModelEvent e = new TableColumnModelEvent(this.getColumnModel(), 0, 0);
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==TableColumnModelListener.class) {
        // Lazily create the event:
        // if (e == null)
        //  e = new ChangeEvent(this);
        ((TableColumnModelListener)listeners[i+1]).
            columnMoved(e);
      }
    }
  }


  /**
   *
   */
  protected EventListenerList listenerList = new EventListenerList();
  /**
   * Adds a listener for table column model events.
   * @param x  a <code>TableColumnModelListener</code> object
   */
  public void addColumnModelListener(TableColumnModelListener x) {
      listenerList.add(TableColumnModelListener.class, x);
  }

  // implements javax.swing.table.TableColumnModel
  /**
   * Removes a listener for table column model events.
   * @param x  a <code>TableColumnModelListener</code> object
   */
  public void removeColumnModelListener(TableColumnModelListener x) {
    listenerList.remove(TableColumnModelListener.class, x);
  }
  /**
   *
   * @param evt PropertyChangeEvent
   */
  public void propertyChange(PropertyChangeEvent evt) {
  }
}
