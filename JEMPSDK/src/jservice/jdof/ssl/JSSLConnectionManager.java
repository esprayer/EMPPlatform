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
                //取出操作系统的分隔符
                String strSeparator = System.getProperty("file.separator");
                Local_UserHome = URI.substring(File.length(), URI.indexOf(WEB_INF) + WEB_INF.length());
                System.out.println("-----EmptyPath: " + URI + "-----");
                System.out.println("-----Local_UserHome: " + Local_UserHome + "-----");
                //获取证书本地路径
                CertificatePath = Local_UserHome + strSeparator + LIB + strSeparator + CertificateName;
                System.out.println("-----CertificatePath: " + CertificatePath + "-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是不是安全连接
     *
     * @param  url URL
     * @return     boolean
     */
    public static boolean isSSLConnection(URL url) {
        if (url != null) {
            /**
             * 这个地方比较正统的方法是:"https".equals(url.getProtocol())
             */
            String urlString = url.toString();
            if (urlString.indexOf("https") >= 0)
                return true;
        }
        return false;
    }

    /**
     * 返回一个安全连接
     * 在sunone和was中分别处理
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
     * 在sun的应用服务器下返回一个安全连接
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

            //从上述SSLContext对象中得到SSLSocketFactory对象
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
     * 返回一个安全连接。
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
     * 返回一个安全连接。
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
