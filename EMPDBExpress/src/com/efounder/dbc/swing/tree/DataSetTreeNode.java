package com.efounder.dbc.swing.tree;

import javax.swing.tree.*;
import com.efounder.eai.data.KeyValue;
import java.util.Map;

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
public class DataSetTreeNode
    extends DefaultMutableTreeNode implements KeyValue {
  protected long InternalRow;
  private String dctBH;
  private String dctMc;
  private String dctJs = "0";
  private String dctMx = "1";
  private String parentBH;
  private String curBH;
  private String UNIT_ID;
  private boolean childControl; //控制下级，不允许增加下级

  public DataSetTreeNode() {
  }

  public boolean isLeaf() {
    return this.isDctMx();
  }

  public String toString() {
    return curBH + " " + dctMc;
  }

  public long getInternalRow() {
    return InternalRow;
  }

  public void setInternalRow(long InternalRow) {
    this.InternalRow = InternalRow;
  }

  public void setDctBH(String dctBH) {
    this.dctBH = dctBH;
  }

  public void setDctMc(String dctMc) {
    this.dctMc = dctMc;
  }

  public void setDctJs(String dctJs) {
    this.dctJs = dctJs;
  }

  public void setUNIT_ID(String UNIT_ID) {
      this.UNIT_ID = UNIT_ID;
  }

  public String getUNIT_ID() {
      return this.UNIT_ID;
  }

  public void setDctMx(String dctMx) {
    this.dctMx = dctMx;
  }

  public void setParentBH(String parentBH) {
    this.parentBH = parentBH;
  }

  public void setCurBH(String curBH) {
    this.curBH = curBH;
  }

  public void setChildControl(boolean childControl) {
    this.childControl = childControl;
  }

  public String getDctBH() {
    return dctBH;
  }

  public String getDctMc() {
    return dctMc;
  }

  public String getDctJs() {
    return dctJs;
  }

  public int getIntJs() {
    return Integer.parseInt(dctJs);
  }

  public String getDctMx() {
    return dctMx;
  }

  public boolean isDctMx() {
    return "1".equals(dctMx);
  }

  public String getParentBH() {
    return parentBH;
  }

  public String getCurBH() {
    return curBH;
  }

  public boolean isChildControl() {
    return childControl;
  }

    /**
     * getValue
     *
     * @param key Object
     * @param def Object
     * @return Object
     * @todo Implement this com.efounder.eai.data.KeyValue method
     */
    public Object getValue(Object key, Object def) {
        if (param.get(key) == null)
            return def;
        return param.get(key);
    }

    /**
     * setValue
     *
     * @param key Object
     * @param val Object
     * @todo Implement this com.efounder.eai.data.KeyValue method
     */
    public void setValue(Object key, Object val) {
        param.put(key, val);
    }

    /**
     * getAttriMap
     *
     * @return Map
     * @todo Implement this com.efounder.eai.data.KeyValue method
     */
    public Map getAttriMap() {
        return param;
    }

    protected java.util.Map param = new java.util.HashMap();

    /**
     *
     * @return String[]
     */
    public String[] getParentBHs() {
        java.util.List list = new java.util.ArrayList();
        TreeNode node = getParent();
        String DCT_BH = "";
        while (node != null && node instanceof DataSetTreeNode) {
            DCT_BH = ((DataSetTreeNode) node).getDctBH();
            if (DCT_BH != null && !DCT_BH.equals("#ROOT"))
                list.add(DCT_BH);
            node = node.getParent();
        }
        String[] parents = new String[list.size()];
        System.arraycopy(list.toArray(), 0, parents, 0, parents.length);
        return parents;
    }
}
