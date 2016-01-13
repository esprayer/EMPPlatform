package com.efounder.service.os;

import java.io.*;

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
public class OSCtrlUtil {

  /**
   *
   * @param process Process
   * @param pid String
   * @return boolean
   */
  public static boolean containPID(Process process, String pid) {
    boolean containPID = false;
    try {
      String pid2 = " " + pid + " ";
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line = null;
      while ( (line = reader.readLine()) != null) {
        if (line.indexOf(pid2) >= 0) {
          containPID = true;
          break;
        }
      }
      reader.close();
      process.getInputStream().close();
      process.getOutputStream().close();
    } catch (IOException ex) {
    }
    return containPID;
  }
}
