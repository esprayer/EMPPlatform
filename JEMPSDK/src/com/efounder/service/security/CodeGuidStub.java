package com.efounder.service.security;

import com.core.xml.*;

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
public class CodeGuidStub extends StubObject {
  /**
   *
   */
  public CodeGuidStub() {
  }
  /**
   *
   * @return String
   */
  public String getCode() {
    return this.getString("code",null);
  }
  /**
   *
   * @param code String
   */
  public void setCode(String code) {
    this.setString("code",code);
  }
  /**
   *
   * @return String
   */
  public String getGuid() {
    return this.getString("guid",null);
  }
  /**
   *
   * @param guid String
   */
  public void setGuid(String guid) {
    this.setString("guid",guid);
  }
  /**
   *
   * @return String
   */
  public String getSerialNo() {
    return this.getString("serialno",null);
  }
  /**
   *
   * @param serialno String
   */
  public void setSerialNo(String serialno) {
    this.setString("serialno",serialno);
  }

}
