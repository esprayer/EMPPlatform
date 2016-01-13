package com.efounder.service.meta.db;

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
public class MetadataUtils {
  /**
   *
   */
  protected MetadataUtils() {
  }
  /**
   *
   * @param tableMetadata TableMetadata
   * @param colList List
   */
  public static void attachTableColumn(TableMetadata tableMetadata,java.util.List colList) {
    if ( colList == null ) {tableMetadata.colList = null;return;}
    tableMetadata.colList = new java.util.ArrayList();
    tableMetadata.colList.addAll(colList);
  }
  /**
   *
   * @param tableMetadata TableMetadata
   * @param indexList List
   */
  public static void attachTableIndex(TableMetadata tableMetadata,java.util.List indexList) {
    if ( indexList == null ) {tableMetadata.indexList = null;return;}
    tableMetadata.indexList = new java.util.ArrayList();
    tableMetadata.indexList.addAll(indexList);
  }
  /**
   *
   * @param tableMetadata TableMetadata
   * @param keyList List
   */
  public static void attachTableKey(TableMetadata tableMetadata,java.util.List keyList) {
    if ( keyList == null ) {tableMetadata.keyList = null;return;}
    tableMetadata.keyList = new java.util.ArrayList();
    tableMetadata.keyList.add(keyList);
  }
  /**
   *
   * @param dictMetadata DictMetadata
   * @param tableMetadata TableMetadata
   */
  public static void attachDictTable(DictMetadata dictMetadata,TableMetadata tableMetadata) {
    dictMetadata.tableMetadata = tableMetadata;
  }
  /**
   *
   * @param factMetadata FactMetadata
   * @param tableMetadata TableMetadata
   */
  public static void attachFactTable(FactMetadata factMetadata,TableMetadata tableMetadata) {
    factMetadata.tableMetadata = tableMetadata;
  }
  /**
   *
   * @param dictMetadata DictMetadata
   * @param dctbmgzMetadata DctbmgzMetadata
   */
  public static void attachDictTableBmgz(DictMetadata dictMetadata,DctbmgzMetadata dctbmgzMetadata) {
    dictMetadata.dctbmgzMetadata = dctbmgzMetadata;
  }
  /**
   *
   * @param dictMetadata DictMetadata
   * @param dctrlglMetadata DctrlglMetadata
   */
  public static void attachDictTableRlgl(DictMetadata dictMetadata,java.util.List dctrlglList) {
    dictMetadata.dctrlglList = new java.util.ArrayList();
    dictMetadata.dctrlglList.add(dctrlglList);
  }
  /**
   *
   * @param colMetadata ColMetadata
   * @param dictMetadata DictMetadata
   */
  public static void attachColmetadataRefDict(ColumnMetaData colMetadata,DictMetadata dictMetadata) {
    colMetadata.refDictmetadata = dictMetadata;
  }
}
