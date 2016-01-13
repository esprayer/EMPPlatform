package com.efounder.service.comm.ssl;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

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
public class NULLX509TrustManager implements TrustManager, X509TrustManager {
  /**
   *
   */
  public NULLX509TrustManager() {
  }
  /**
   * getAcceptedIssuers
   *
   * @return X509Certificate[]
   * @todo Implement this javax.net.ssl.X509TrustManager method
   */
  public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[] {};
  }

  /**
   * checkClientTrusted
   *
   * @param x509CertificateArray X509Certificate[]
   * @param string String
   * @throws CertificateException
   * @todo Implement this javax.net.ssl.X509TrustManager method
   */
  public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
  }

  /**
   * checkServerTrusted
   *
   * @param x509CertificateArray X509Certificate[]
   * @param string String
   * @throws CertificateException
   * @todo Implement this javax.net.ssl.X509TrustManager method
   */
  public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
  }
}
