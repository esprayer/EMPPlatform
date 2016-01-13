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

public class GroupDefine extends ColumnDefine {
  protected Vector ChildColumn = null; // 子列列表

  public GroupDefine() {
  }
  public int getSize() {
    int size = 0;
    if ( ChildColumn != null ) size = ChildColumn.size();
    return size;
  }
  public ColumnDefine get(int i) {
    ColumnDefine CD = null;
    if ( i < getSize() ) {
      CD = (ColumnDefine)ChildColumn.get(i);
    }
    return CD;
  }
  public boolean hasChild() {
    return (ChildColumn != null && ChildColumn.size() > 0);
  }

  public boolean hasChild(ColumnDefine col){
    boolean hasChild = false;
    for (int i = 0; i < getSize(); i++){
      ColumnDefine child = this.get(i);
      if (child==col){
        hasChild = true;
        break;
      }
    }
    return hasChild;
  }

  public void addChildColumn(ColumnDefine col) {
    if ( ChildColumn == null ) {
      ChildColumn = new Vector();
    }
    addChildColumn(ChildColumn.size(),col);
    col.setParentCol(this);
  }
  public void addChildColumn(int index,ColumnDefine col) {
    if ( ChildColumn == null ) {
      ChildColumn = new Vector();
    }
    ChildColumn.add(index,col);
    col.setParentCol(this);
  }

  public void removeChildColumn(ColumnDefine col) {
    if ( ChildColumn == null ) return;
    ChildColumn.remove(col);
    if ( ChildColumn.size() == 0 ) ChildColumn = null;
    col.setParentCol(null);
  }
  /**
   * 在一个组列表中，获取其中最大的级别
   * @param GroupList Hashtable
   * @return int
   */
  public static int getMaxLevel(Hashtable GroupList) {
    int MaxLevel = 1;ColumnDefine GD = null;int Level = 0;
    Object[] OArray = GroupList.keySet().toArray();
    for(int i=0;i<OArray.length;i++) {
      GD = (ColumnDefine)GroupList.get(OArray[i]);
      if ( GD instanceof GroupDefine ) {
        Level = getGroupLevel( (GroupDefine) GD);
        MaxLevel = (Level > MaxLevel) ? Level : MaxLevel;
      }
    }
    return MaxLevel;
  }
  private static int getGroupLevel(GroupDefine GD) {
    int[] Level = new int[1];
    Level[0] = 0;
    getGroupLevel(GD,1,Level);
    return Level[0];
  }
  private static void getGroupLevel(ColumnDefine CD,int index,int[] Level) {
    GroupDefine GD = null;
    if ( index > Level[0] ) Level[0] = index;
    if ( CD instanceof GroupDefine ) {
      GD = (GroupDefine)CD;
      for(int i=0;i<GD.getSize();i++) {
        index++;
        CD = GD.get(i);
        getGroupLevel(CD,index,Level);
        index--;
      }
    } else if ( CD instanceof ColumnDefine) {
      return;
    }
  }

  /**
   * 返回一个clone的组对象，但是不包括组里的列信息
   * @return Object
   */
  public Object clone() {
    try {
      GroupDefine GD = (GroupDefine) super.clone();
      if ( GD != null ) {
        GD.ChildColumn = null;
      }
      return GD;
    } catch ( Exception e ) {
    }
    return null;
  }

}
