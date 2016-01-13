package jdatareport.dof.classes.report.figure;

import java.util.*;

import jdatareport.dof.classes.report.util.*;

/**
 *
 * <p>Title: JDRDefaultFigure</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public class JDRDefaultFigure
    implements JDRFigure, JDRConstants {
    /**
     *
     */
    private String mName = "";
    private Hashtable mAttribures = new Hashtable();

    private int mStartRow = 0;
    private int mStartCol = 0;
    private int mEndRow = -1;
    private int mEndCol = -1;

    private int mRowCount = 0;
    private int mColCount = 0;
    /**
     * 构造方法
     */
    public JDRDefaultFigure() {
    }

    /**
     *
     * @see {@link JDRFigure.getName()}
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     *
     * @return
     */
    public int getStartRow() {
        return this.mStartRow;
    }

    /**
     *
     * @return
     */
    public int getStartCol() {
        return this.mStartCol;
    }

    /**
     *
     * @return
     */
    public int getEndRow() {
        return this.mEndRow;
    }

    /**
     *
     * @return
     */
    public int getEndCol() {
        return this.mEndCol;
    }

    /**
     *
     * @return
     */
    public Hashtable getAttributes() {
        return mAttribures;
    }

    /**
     *
     * @param attributes
     */
    public void setAttributes(Hashtable attributes) {
        if (attributes != null && attributes.size() > 0) {
            this.mAttribures = attributes;
        }
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addAttribute(String name, String value) {
        if (name != null && value != null) {
            this.mAttribures.put(name, value);
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String getAttribute(String name) {
        if (name != null) {
            return (String)this.mAttribures.get(name);
        }
        return null;
    }

    /**
     *
     * @param startRow
     */
    public void setStartRow(int startRow) {
        this.mStartRow = startRow;
    }

    /**
     *
     * @param startCol
     */
    public void setStartCol(int startCol) {
        this.mStartCol = startCol;
    }

    /**
     *
     * @param endRow
     */
    public void setEndRow(int endRow) {
        this.mEndRow = endRow;
    }

    /**
     *
     * @param endCol
     */
    public void setEndCol(int endCol) {
        this.mEndCol = endCol;
    }

//  /**
//   *
//   * @return
//   */
//  public int getRowCount()
//  {
//    return mRowCount;
//  }
//
//  /**
//   * 获得列数
//   * @return
//   */
//  public int getColCount()
//  {
//    return mColCount;
//  }
//
//  /**
//   * 设置行数
//   * @param rowCount
//   */
//  public void setRowCount(int rowCount)
//  {
//    if (rowCount > -1&& rowCount >mRowCount) {
//      mRowCount = rowCount;
//    }
//  }
//
//  /**
//   * 设置列数
//   * @param colCount
//   */
//  public void setColCount(int colCount)
//  {
//    if (colCount > -1 && colCount >mColCount) {
//      mColCount = colCount;
//    }
//  }
}