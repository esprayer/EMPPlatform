package com.print.service;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ViewDefine {
//  protected Hashtable ColList   = null;
//  protected Hashtable GroupList = null;

  protected Vector ViewColList  = null;

  // 可视的Layout
  protected Vector ViewLayout = new Vector();
  public Vector getViewLayout() {
    return ViewLayout;
  }

  public Vector getViewColList() {
    if ( ViewColList == null )
      ViewColList = buildViewColList();
    return ViewColList;
  }

  /**
   * 只获取列
   * @param index int
   * @return ColumnDefine
   */
  public ColumnDefine getColumn(int index) {
    ColumnDefine CD = null;
    if ( ViewColList == null ) buildViewColList();
    if ( index < ViewColList.size() ) {
      CD = (ColumnDefine)ViewColList.get(index);
    }
    return CD;
  }
  /**
   * 创建所有列列表，列表中只有列，没有组
   * @return Vector
   */
  public Vector buildViewColList() {
    ViewColList = new Vector();ColumnDefine CD = null;
    for(int i=0;i<ViewLayout.size();i++) {
      CD = (ColumnDefine)ViewLayout.get(i);
      buildViewCol(ViewColList,CD);
    }
    return ViewColList;
  }
  public static void buildViewCol(Vector viewColList,ColumnDefine CD) {
    if ( CD == null ) return;
    // 如果是列类型，直接增加到列表中
    if ( CD instanceof GroupDefine ) {
      // 如果是组类型，需要进一步处理
      GroupDefine GD = null;
      if (CD instanceof GroupDefine) {
        GD = (GroupDefine) CD;
        for (int i = 0; i < GD.getSize(); i++) {
          CD = GD.get(i);
          buildViewCol(viewColList, CD);
        }
      }
    } else {
      viewColList.add(CD);
    }
  }
  /**
   * 按索引号获取可视的列或组
   * @param index int
   * @return ColumnDefine
   */
  public ColumnDefine getViewObject(int index) {
    ColumnDefine CD = null;
    if ( index < ViewLayout.size() ) {
      CD = (ColumnDefine)ViewLayout.get(index);
    }
    return CD;
  }

//  public void setColList(Hashtable ColList) {
//    this.ColList = ColList;
//  }

//  public Hashtable getColList() {
//    return ColList;
//  }

//  public void setGroupList(Hashtable GroupList) {
//    this.GroupList = GroupList;
//  }

//  public Hashtable getGroupList() {
//    return GroupList;
//  }
  /**
   * 获取每页的列数
   * @return int
   */
  public int getColCount() {
    if ( ViewColList == null ) {
      this.buildViewColList();
    }
    return ViewColList.size();
  }
  public void addCol(int index,ColumnDefine col) {
    // 如果大于-1，说明已经存在了，不需要增加
    if ( ViewLayout.indexOf(col) > -1 ) return;
    ViewLayout.add(index,col);
  }
  public void addCol(ColumnDefine col) {
     addCol(ViewLayout.size(),col);
  }
  public Object removeCol(ColumnDefine col) {
    if ( ViewLayout.remove(col) )
      return col;
    return null;
  }
  public Object removeCol(int index) {
    return ViewLayout.remove(index);
  }
  public ViewDefine() {
  }

}
