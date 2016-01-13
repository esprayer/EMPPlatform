package com.efounder.builder.base.data;

import java.util.*;

public class DataSetEvent extends EventObject {
  /**
   *
   * @param source Object
   */
  protected DataSetEvent(EFDataSet source) {
    super(source);
  }
  /**
   *
   */
  protected ESPRowSet eventRowSet = null;
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getEventRowSet() {
    return eventRowSet;
  }
  /**
   *
   * @param eventRowSet ESPRowSet
   */
  public void setEventRowSet(ESPRowSet eventRowSet) {
    this.eventRowSet = eventRowSet;
  }
  /**
   *
   * @return int
   */
  public int getEventType() {
    return type;
  }
  /**
   *
   * @param source Object
   * @return DataSetEvent
   */
  public static DataSetEvent getInstance(EFDataSet source,int eventType) {
    DataSetEvent dse = new DataSetEvent(source);
    dse.type = eventType;
    return dse;
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return (EFDataSet)this.getSource();
  }
  /**
   *
   */
  protected int oldCursor = -1;
  /**
   *
   * @param oldCursor int
   */
  protected void setOldCursor(int oldCursor) {
    this.oldCursor = oldCursor;
  }
  /**
   *
   * @return int
   */
  public int getOldCursor() {
    return oldCursor;
  }
  /**
   *
   */
  protected int newCursor = -1;
  /**
   *
   * @param newCursor int
   */
  protected void setNewCursor(int newCursor) {
    this.newCursor = newCursor;
  }

  public void setUpdateRowSet(ESPRowSet updateRowSet) {
    this.updateRowSet = updateRowSet;
  }

  public void setUpdateFieldName(String updateFieldName) {
    this.updateFieldName = updateFieldName;
  }

  public void setUpdateOldValue(Object updateOldValue) {
    this.updateOldValue = updateOldValue;
  }

  public void setUpdateNewValue(Object updateNewValue) {
    this.updateNewValue = updateNewValue;
  }

  /**
   *
   * @return int
   */
  public int getNewCursor() {
    return newCursor;
  }
  public static final int CURSOR =  2;
  /** Identifies the addtion of new rows or columns. */
  public static final int INSERT =  1;
  /** Identifies a change to existing data. */
  public static final int UPDATE =  0;
  /** Identifies the removal of rows or columns. */
  public static final int DELETE = -1;

  /** Identifies the header row. */
  public static final int HEADER_ROW = -1;

  /** Specifies all columns in a row or rows. */
  public static final String ALL_COLUMNS = null;

//
//  Instance Variables
//

  protected int       type;
  protected int	firstRow;
  protected int	lastRow;
  protected String	columnID;
  private ESPRowSet updateRowSet;
  private String updateFieldName;
  private Object updateOldValue;
  private Object updateNewValue;
  /**
   *
   * @param source EFDataSet
   * @param row int
   */
  public DataSetEvent(EFDataSet source, int row) {
      this(source, row, row, ALL_COLUMNS, UPDATE);
  }

  /**
   *  The data in rows [<I>firstRow</I>, <I>lastRow</I>] have been updated.
   */
  public DataSetEvent(EFDataSet source, int firstRow, int lastRow) {
      this(source, firstRow, lastRow, ALL_COLUMNS, UPDATE);
  }

  /**
   *  The cells in column <I>column</I> in the range
   *  [<I>firstRow</I>, <I>lastRow</I>] have been updated.
   */
  public DataSetEvent(EFDataSet source, int firstRow, int lastRow, String column) {
      this(source, firstRow, lastRow, column, UPDATE);
  }

  /**
   *  The cells from (firstRow, column) to (lastRow, column) have been changed.
   *  The <I>column</I> refers to the column index of the cell in the model's
   *  co-ordinate system. When <I>column</I> is ALL_COLUMNS, all cells in the
   *  specified range of rows are considered changed.
   *  <p>
   *  The <I>type</I> should be one of: INSERT, UPDATE and DELETE.
   */
  public DataSetEvent(EFDataSet source, int firstRow, int lastRow, String column, int type) {
      super(source);
      this.firstRow = firstRow;
      this.lastRow = lastRow;
      this.columnID = column;
      this.type = type;
  }
 /** Returns the first row that changed.  HEADER_ROW means the meta data,
   * ie. names, types and order of the columns.
   */
  public int getFirstRow() { return firstRow; };

  /** Returns the last row that changed. */
  public int getLastRow() { return lastRow; };

  /**
   *  Returns the column for the event.  If the return
   *  value is ALL_COLUMNS; it means every column in the specified
   *  rows changed.
   */
  public String getColumnID() { return columnID; };

  /**
   *  Returns the type of event - one of: INSERT, UPDATE and DELETE CURSOR.
   */
  public int getType() { return type; }

  public ESPRowSet getUpdateRowSet() {
    return updateRowSet;
  }

  public String getUpdateFieldName() {
    return updateFieldName;
  }

  public Object getUpdateOldValue() {
    return updateOldValue;
  }

  public Object getUpdateNewValue() {
    return updateNewValue;
  }

}

