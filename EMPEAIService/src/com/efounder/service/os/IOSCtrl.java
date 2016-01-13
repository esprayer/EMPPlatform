package com.efounder.service.os;

import com.core.servlet.JVMStub;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IOSCtrl {
  /**
   *
   * @param jvmStub JVMStub
   * @return boolean
   */
  boolean isValidJVM(JVMStub jvmStub);
}
