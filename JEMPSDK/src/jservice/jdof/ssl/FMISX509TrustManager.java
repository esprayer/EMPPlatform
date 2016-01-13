package jservice.jdof.ssl;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * <p>Title: FMIS���ι�����</p>
 * <p>Description:
 * <br>
 * Java��ȫ�׽���չ (Java Secure Socket Extension, JSSE)��ʵ��Internet��ȫͨ�ŵ�һϵ��
 * ���ļ��ϡ�����һ��SSL��TLS�Ĵ�Javaʵ�֣�����͸�����ṩ���ݼ��ܡ���������֤����Ϣ�����Եȹ��ܣ�
 * ����ʹ������ʹ����ͨ���׽���һ��ʹ��JSSE�����İ�ȫ�׽��֡�
 * <br>
 * <br>
 * Java��ȫ�ĸ���ͻ��˵�TrustStore�ļ����ͻ��˵�TrustStore�ļ��б����ű��ͻ��������εķ���
 * ����֤����Ϣ���ͻ����ڽ���SSL����ʱ��JSSE����������ļ��е�֤������Ƿ����η������˵�֤�顣
 * <br>
 * <br>
 * JSSE�У���һ�����ι������ฺ������Ƿ�����Զ�˵�֤�飬����������µĴ������
����<br>�� ���ϵͳ����javax.net.sll.trustStoreָ����TrustStore�ļ�����ô���ι�������ȥ
          jre��װ·���µ�lib/security/Ŀ¼��Ѱ�Ҳ�ʹ������ļ������֤�顣
����<br>�� �����ϵͳ����û��ָ��TrustStore�ļ������ͻ�ȥjre��װ·����Ѱ��Ĭ�ϵ�TrustStore
          �ļ�������ļ������·��Ϊ��lib/security/jssecacerts��
����<br>�� ���jssecacerts�����ڣ�����cacerts����(����J2SDKһ���У������������޵Ŀ����ε�
          ����֤��)����ô���Ĭ�ϵ�TrustStore�ļ�����cacerts��
 * <br>
 * <br>
 * Java�ṩ��һ�ַǳ����ķ���������HTTPS��ҳ����ʹ����HttpsURLConnection��URL�ȡ��⼸����
 * Ϊ֧��HTTPS��JSSE��������˽�һ���ķ�װ����������·���һ��https��վ����׳�javax.net.ssl.SSLException��
 * ������Ϊ������վ���֤�鲻��JSSE���Ρ�����JSSE����ж����ι������ķ�����һ�ֽ���������ķ���
 * �ǰ������ι������Ĵ�����򣬰�վ���֤��ŵ�֤����ļ�jssecacerts�У����߰�֤���ŵ���һ
 * TrustStore�ļ��У�Ȼ������ϵͳ����javax.net.sll.trustStoreָ����ļ�����һ�ֽ����������
 * �Լ�ʵ�����ι������࣬������������ָ����֤�顣
 * <br>
 * <br>
 * ����ʵ��һ�����ι��������������α�����վ���֤�顣
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
