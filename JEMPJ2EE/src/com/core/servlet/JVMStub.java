package com.core.servlet;

import com.core.xml.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JVMStub extends StubObject {

    /**
     *
     */
    public JVMStub() {
    }

    /**
     *
     * @param GUID String
     */
    public void setGUID(String GUID) {
        this.setString("JVM_GUID", GUID);
    }

    /**
     *
     * @return String
     */
    public String getGUID() {
        return this.getString("JVM_GUID", "");
    }

    /**
     *
     * @param serverName String
     */
    public void setServerName(String serverName) {
        this.setString("SERVER_NAME", serverName);
    }

    /**
     *
     * @return String
     */
    public String[] getServerName() {
        // 一个JVM可能会承担多个服务器的角色
        String serverName = getString("SERVER_NAME", "");
        return serverName.split(",");
    }

    /**
     *
     * @param HOST String
     */
    public void setHOST(String HOST) {
        this.setString("HOST", HOST);
    }

    /**
     *
     * @return String
     */
    public String getHOST() {
        return this.getString("HOST", "");
    }

    private StubObject object = new StubObject();

    /**
     *
     */
    public void incr(String key, long step) {
      synchronized (object) {
        long count = getCounter(key) + step;
        long max = getCounterMax(key);
        if (count > max) setCounterMax(key, count);
        setLong(key, count);
      }
    }

    /**
     *
     */
    public void decr(String key, long step) {
      synchronized (object) {
        long count = getCounter(key) - step;
        if (count < 0) count = 0;
        setLong(key, count);
      }
    }

    /**
     *
     * @return long
     */
    public long getCounter(String key) {
      return getLong(key, 0l);
    }

    /**
     * 取某个计数器的最大值
     * @param key String
     * @return long
     */
    public long getCounterMax(String key) {
      return getLong(key + "_MAX", 0L);
    }

    /**
     *
     * @param key String
     * @param max long
     */
    public void setCounterMax(String key, long max) {
      this.setLong(key + "_MAX", max);
    }

    /**
     *
     * @return String
     */
    public String toString() {
        return getServerName() + ";" + getHOST() + ";" + getGUID();
    }

}
