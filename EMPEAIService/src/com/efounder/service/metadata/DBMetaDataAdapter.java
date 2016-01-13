package com.efounder.service.metadata;

import com.core.xml.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DBMetaDataAdapter implements DBMetaData,java.io.Serializable {
  /**
   *
   */
  public DBMetaDataAdapter() {
  }

  /**
   * getObjectColumns
   *
   * @return StubObject[]
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public StubObject[] getObjectColumns() {
    return null;
  }

  /**
   * getObjectID
   *
   * @return String
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public String getObjectID() {
    return "";
  }

  /**
   * getObjectIndexs
   *
   * @return StubObject[]
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public StubObject[] getObjectIndexs() {
    return null;
  }

  /**
   * getObjectInfo
   *
   * @return StubObject
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public StubObject getObjectInfo() {
    return null;
  }

  /**
   * getObjectKeys
   *
   * @return StubObject[]
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public StubObject[] getObjectKeys() {
    return null;
  }

  /**
   * getObjectTriggers
   *
   * @return StubObject[]
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public StubObject[] getObjectTriggers() {
    return null;
  }

  /**
   * getObjectType
   *
   * @return int
   * @todo Implement this com.efounder.metadata.DBMetaData method
   */
  public int getObjectType() {
    return 0;
  }
  /**
   *
   * @return StubObject
   */
  public StubObject getDCTInfo() {
    return null;
  }
  /**
   *
   * @return StubObject
   */
  public StubObject getFCTInfo() {
    return null;
  }
}
