package com.efounder.service.comm;

import java.net.*;
import java.util.*;

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
public interface CommListener extends EventListener {
  /**
   *
   * @param actionObject Object
   * @param url URL
   * @param wo Object
   */
  public void startComm(Object actionObject,URL url,Object wo);
  /**
   *
   * @param actionObject Object
   * @param urlc URLConnection
   * @param ro JResponseObject
   */
  public void stopComm(Object actionObject,URLConnection urlc,Object ro);
}
