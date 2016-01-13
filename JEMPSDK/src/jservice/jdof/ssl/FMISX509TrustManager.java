package jservice.jdof.ssl;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * <p>Title: FMIS信任管理器</p>
 * <p>Description:
 * <br>
 * Java安全套接扩展 (Java Secure Socket Extension, JSSE)是实现Internet安全通信的一系列
 * 包的集合。它是一个SSL和TLS的纯Java实现，可以透明地提供数据加密、服务器认证、信息完整性等功能，
 * 可以使我们像使用普通的套接字一样使用JSSE建立的安全套接字。
 * <br>
 * <br>
 * Java安全的概念：客户端的TrustStore文件。客户端的TrustStore文件中保存着被客户端所信任的服务
 * 器的证书信息。客户端在进行SSL连接时，JSSE将根据这个文件中的证书决定是否信任服务器端的证书。
 * <br>
 * <br>
 * JSSE中，有一个信任管理器类负责决定是否信任远端的证书，这个类有如下的处理规则：
　　<br>⑴ 如果系统属性javax.net.sll.trustStore指定了TrustStore文件，那么信任管理器就去
          jre安装路径下的lib/security/目录中寻找并使用这个文件来检查证书。
　　<br>⑵ 如果该系统属性没有指定TrustStore文件，它就会去jre安装路径下寻找默认的TrustStore
          文件，这个文件的相对路径为：lib/security/jssecacerts。
　　<br>⑶ 如果jssecacerts不存在，但是cacerts存在(它随J2SDK一起发行，含有数量有限的可信任的
          基本证书)，那么这个默认的TrustStore文件就是cacerts。
 * <br>
 * <br>
 * Java提供了一种非常简洁的方法来访问HTTPS网页，即使用类HttpsURLConnection、URL等。这几个类
 * 为支持HTTPS对JSSE相关类做了进一步的封装。正常情况下访问一个https的站点会抛出javax.net.ssl.SSLException。
 * 这是因为被访问站点的证书不被JSSE信任。根据JSSE简介中对信任管理器的分析，一种解决这个问题的方法
 * 是按照信任管理器的处理规则，把站点的证书放到证书库文件jssecacerts中，或者把证书存放到任一
 * TrustStore文件中，然后设置系统属性javax.net.sll.trustStore指向该文件。另一种解决方法则是
 * 自己实现信任管理器类，让它信任我们指定的证书。
 * <br>
 * <br>
 * 该类实现一个信任管理器，用来信任被访问站点的证书。
 * </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class FMISX509TrustManager
    implements TrustManager, X509TrustManager {

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
