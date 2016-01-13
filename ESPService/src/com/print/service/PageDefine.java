package com.print.service;

import com.print.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PageDefine {
  // 此页的显示列及组
  protected ViewDefine  ViewLayout = null;
  protected int rowPage = 0;
  protected int colPage = 0;
  protected JF1PageSets F1PageSets = null;
  public int getRowPage() {
    return rowPage;
  }

  public void setColPage(int colPage) {
    this.colPage = colPage;
  }

  public void setRowPage(int rowPage) {
    this.rowPage = rowPage;
  }

  public void setF1PageSets(JF1PageSets F1PageSets) {
    this.F1PageSets = F1PageSets;
  }
  public int getColPage() {
    return colPage;
  }

  public JF1PageSets getF1PageSets() {
    return F1PageSets;
  }
  /**
   *
   */
  public PageDefine(int rowPage,int colPage,JF1PageSets F1PageSets) {
    this.rowPage = rowPage;
    this.colPage = colPage;
    this.F1PageSets = F1PageSets;
  }

}
