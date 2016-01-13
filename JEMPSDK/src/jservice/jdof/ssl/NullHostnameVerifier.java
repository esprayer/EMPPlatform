package jservice.jdof.ssl;

import javax.net.ssl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NullHostnameVerifier implements HostnameVerifier {
  public NullHostnameVerifier() {
  }
  public boolean verify(String string, SSLSession sSLSession) {
    return true;
  }
}
