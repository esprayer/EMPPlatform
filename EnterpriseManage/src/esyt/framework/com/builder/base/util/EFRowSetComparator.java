package esyt.framework.com.builder.base.util;

import java.io.*;
import java.util.*;

import com.etsoft.pub.util.JCompareObject;

import esyt.framework.com.builder.base.data.EFRowSet;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EFRowSetComparator
    implements Comparator, Serializable {

  /**
   *
   */
  private String keyName;
  /**
   *
   */
  private boolean ascending;
  /**
   *
   */
  private Comparator comparator;

  /**
   *
   */
  public EFRowSetComparator() {
    this(null);
  }

  /**
   *
   * @param keyName String
   */
  public EFRowSetComparator(String keyName) {
    this(keyName, true);
  }

  /**
   *
   * @param keyName String
   * @param ascending boolean
   * @param comparator Comparator
   */
  public EFRowSetComparator(String keyName, boolean ascending) {
    this.setKeyName(keyName);
    this.setAscending(ascending);
    this.comparator = new JCompareObject();
  }

  /**
   *
   * @param o1 Object
   * @param o2 Object
   * @return int
   */
  public int compare(Object o1, Object o2) {
    if (keyName == null || keyName.trim().length() == 0)
      return comparator.compare(o1.toString(), o2.toString()) * (ascending ? 1 : -1);
    EFRowSet rs1 = (EFRowSet) o1;
    EFRowSet rs2 = (EFRowSet) o2;
    Object data1 = rs1.getObject(keyName, null);
    Object data2 = rs2.getObject(keyName, null);
    if (data1 == null) data1 = "";
    if (data2 == null) data2 = "";
    return comparator.compare(data1, data2) * (ascending ? 1 : -1);
  }

  /**
   *
   * @param o Object
   * @return boolean
   */
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (! (o instanceof EFRowSetComparator))
      return false;
    final EFRowSetComparator rsComparator = (EFRowSetComparator) o;
    if (!comparator.equals(rsComparator.comparator))
      return false;
    if (keyName != null)
      if (!keyName.equals(rsComparator.keyName))
        return false;
      else
        return (rsComparator.keyName == null);
    return true;
  }

  /**
   *
   * @param keyName String
   */
  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  /**
   *
   * @param ascending boolean
   */
  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }

  /**
   *
   * @param comparator Comparator
   */
  public void setComparator(Comparator comparator) {
    this.comparator = comparator;
  }

  /**
   *
   * @return String
   */
  public String getKeyName() {
    return keyName;
  }

  /**
   *
   * @return boolean
   */
  public boolean isAscending() {
    return this.ascending;
  }

  /**
   *
   * @return Comparator
   */
  public Comparator getComparator() {
    return this.comparator;
  }

}
