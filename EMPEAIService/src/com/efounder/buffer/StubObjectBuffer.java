package com.efounder.buffer;

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
public class StubObjectBuffer extends DataBuffer {
  /**
   *
   */
  protected StubObjectBuffer(Object key) {
    dataBufferKey = key;
  }
  /**
   *
   * @param key Object
   * @return DataBuffer
   */
  protected static DataBuffer getInstance(Object key) {
    return new StubObjectBuffer(key);
  }
}
