package jservice.jdof.ssl;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JSSLConnectionManager {
    private static final String JavaVendorSUN        = "SUN MICROSYSTEMS INC.";
    private static final String JavaVendorIBM        = "IBM CORPORATION";
    private static final String CertificateName      = "FMISCertificates.jks";
    private static final String CertificatePass      = "PansoftFmis7.0";

    private static final String TRUST_STORE          = "javax.net.ssl.trustStore";
    private static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";

    private static final String File                 = "file:";
    private static final String WEB_INF              = "WEB-INF";
    private static final String LIB                  = "lib";
    private static final String PathName             = "/EAIServer.xml";
    private static       String URI                  = null;
    private static       String Local_UserHome       = null;
    private static       String CertificatePath      = null;
    static {
        try {
            URI = Class.forName("Empty").getResource(PathName).toString();
            if (URI != null && URI.startsWith(File) && URI.indexOf(WEB_INF) != -1) {
                //ȡ������ϵͳ�ķָ���
                String strSeparator = System.getProperty("file.separator");
                Local_UserHome = URI.substring(File.length(), URI.indexOf(WEB_INF) + WEB_INF.length());
                System.out.println("-----EmptyPath: " + URI + "-----");
                System.out.println("-----Local_UserHome: " + Local_UserHome + "-----");
                //��ȡ֤�鱾��·��
                CertificatePath = Local_UserHome + strSeparator + LIB + strSeparator + CertificateName;
                System.out.println("-----CertificatePath: " + CertificatePath + "-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �ж��ǲ��ǰ�ȫ����
     *
     * @param  url URL
     * @return     boolean
     */
    public static boolean isSSLConnection(URL url) {
        if (url != null) {
            /**
             * ����ط��Ƚ���ͳ�ķ�����:"https".equals(url.getProtocol())
             */
            String urlString = url.toString();
            if (urlString.indexOf("https") >= 0)
                return true;
        }
        return false;
    }

    /**
     * ����һ����ȫ����
     * ��sunone��was�зֱ���
     *
     * @param  url URL
     * @return     URLConnection
     */
    public static URLConnection getSSLConnection(URL url) {
        try {
            Properties properties = System.getProperties();
            String javaVendor     = properties.getProperty("java.vendor","").toUpperCase();
            System.out.println("-----JavaVendor:" + javaVendor);
            if (JavaVendorSUN.equals(javaVendor))
                return getConnectionForSunServer(url);
            else
                return getConnectionForIBMServer(url);
//            return getConnectionForServer(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * ��sun��Ӧ�÷������·���һ����ȫ����
     *
     * @return URLConnection
     */
    private static URLConnection getConnectionForSunServer(URL url) {
        URLConnection sslConn = null;
        try {
            System.out.println("https:use method for sun application server.");

            sslConn = url.openConnection();

            TrustManager[] trustManagers = {new FMISX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, trustManagers, new java.security.SecureRandom());

            //������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            ( (javax.net.ssl.HttpsURLConnection) sslConn).setHostnameVerifier(new NullHostnameVerifier());
            if (sslConn instanceof javax.net.ssl.HttpsURLConnection) {
                ( (javax.net.ssl.HttpsURLConnection) sslConn).setSSLSocketFactory(ssf);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sslConn;
    }

    /**
     * ����һ����ȫ���ӡ�
     * @param url URL
     * @return URLConnection
     */
    private static URLConnection getConnectionForIBMServer(URL url) {
        URLConnection sslConn = null;
        try {
            System.out.println("https:use method for ibm websphere.");
            System.setProperty(TRUST_STORE, CertificatePath);
            System.setProperty(TRUST_STORE_PASSWORD, CertificatePass);
            sslConn = url.openConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sslConn;
    }

    /**
     * ����һ����ȫ���ӡ�
     * @param url URL
     * @return URLConnection
     */
    private static URLConnection getConnectionForServer(URL url) {
        URLConnection sslConn = null;
        try {
            System.setProperty(TRUST_STORE, CertificatePath);
            System.setProperty(TRUST_STORE_PASSWORD, CertificatePass);
            sslConn = url.openConnection();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return sslConn;
    }

}
