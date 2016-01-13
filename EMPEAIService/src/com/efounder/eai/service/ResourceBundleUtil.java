package com.efounder.eai.service;

import com.efounder.eai.data.*;
import java.util.Locale;
import java.util.*;

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
public class ResourceBundleUtil {
  public static final String LANGUAGE_RESOURCE="LANGUAGE";
  public ResourceBundleUtil() {
  }
  public static String getString(String baseName,String key){
    return getString(JParamObject.Create(),baseName,key);
  }
   public static String getString(String baseName,String key,String def){
     return getString(null,baseName,key,def);
   }
  public static String getString(JParamObject PO,String baseName,String key,String def){
    String value=null;
    try{
      value=getString(PO,baseName,key);
    }catch(Exception e){
      e.printStackTrace();
      value=null;
    }
    if(value==null||value.trim().length()==0)value=def;
    return value;
  }
  public static String getString(JParamObject PO,String baseName,String key){
    String lang="";
    String contry="";
    if(PO==null){
      lang=Locale.getDefault().getLanguage();
      contry=Locale.getDefault().getCountry();
    }else{
      lang = PO.GetValueByEnvName("International", Locale.getDefault().getLanguage());
      contry = PO.GetValueByEnvName("Country", Locale.getDefault().getCountry());
    }
    String lc = LANGUAGE_RESOURCE + "-" + lang + "_" + contry;
    ResourceBundle res = null;
    if (PO != null)
//      res = (ResourceBundle) DictDataBuffer.getDefault(PO).getDataItem(lc, baseName);
    if (res == null) {
      Locale locale = new Locale(lang, contry);
      try {
        res = ResourceBundle.getBundle(baseName, locale);
      }
      catch (Exception e) {
        e.printStackTrace();
        res = ResourceBundle.getBundle(baseName);
      }
      if (res != null && PO != null) {
//        DictDataBuffer.getDefault(PO).addDataItem(lc, baseName, res);
      }
    }
    String mess=res.getString(key);
    if(mess==null)mess=key;
    return    res.getString(key);
  }
  public static void showMessageDialog(java.awt.Component parent,String reskey ,String mess_key,String title_key,int type){
  String mess=getString(reskey,mess_key);
  String title=getString(reskey,title_key);
  javax.swing.JOptionPane.showMessageDialog(parent,mess,title, type);
}

}
