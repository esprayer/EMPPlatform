package com.core.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class mainConfig
{
  private static Properties _$7423 = null;
  private static String _$7424 = null;
  private static final String _$7425 = "mainconfig.inf";

  public static void initMainConfig(Hashtable hashTable)
  {
    if (_$7423 != null) return;
    _$7424 = (String)hashTable.get("root.home");
    _$7423 = new Properties();
    loadConfig();
    try {
      Enumeration eu = hashTable.keys();

      while (eu.hasMoreElements()) {
        String key = (String)eu.nextElement();
        System.setProperty(key, (String)hashTable.get(key));
      }
    } catch (Exception e) {
      e.printStackTrace(); }
  }

  public static void put(Object k, Object v) {
    _$7423.put(k, v);
    saveConfig(); }

  public static Object get(Object k, Object def) {
    Object res = null;
    res = _$7423.get(k);
    if (res == null) res = def;
    return res; }

  public static void saveConfig() {
    File file = null;
    try {
      file = new File(_$7424 + "mainconfig.inf");
      FileOutputStream fos = new FileOutputStream(file);
      _$7423.store(fos, "MainConfig Ver 1.0.0 by xSkyline");
      fos.flush();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace(); }
  }

  public static void loadConfig() {
    File file = null;
    try {
      file = new File(_$7424 + "mainconfig.inf");
      if (file.exists()) {
        FileInputStream fis = new FileInputStream(file);
        _$7423.load(fis);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}