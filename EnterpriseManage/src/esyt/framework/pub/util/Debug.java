package esyt.framework.pub.util;

import dwz.cache.memcache.client.Logger;

/**
 * Debug.java
 * 
 * @author  ES
 * @version 1.0, 2013-10-21
 */
public class Debug {

  protected static Logger logger = Logger.getLogger(Debug.class.getName());
  
  public static void PrintlnMessageToSystem(String message) {
	  logger.debug(message);
  }
}
