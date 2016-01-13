package com.efounder.mdm.server.buffer;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.service.MemCachedManager;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MDMBufferManager {
  /**
   *
   */
  protected static MDMBufferManager mdmBufferManager = null;
  /**
   *
   */
  protected MDMBufferManager() {
  }
  /**
   *
   * @return MDMBufferManager
   */
  public static MDMBufferManager getDefault() {
    if ( mdmBufferManager == null ) {
      mdmBufferManager = new MDMBufferManager();
    }
    return mdmBufferManager;
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BM String
   * @return EFRowSet
   */
  public EFRowSet getMDRowSet(String DCT_ID,String DCT_BM) {
    EFRowSet[] sets = getRowSets(new String[]{DCT_ID+"_"+DCT_BM});
    if ( sets != null && sets.length >0 ) return sets[0];
    return null;
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BMs String[]
   * @return EFRowSet[]
   */
  public EFRowSet[] getMDRowSets(String DCT_ID,String[] DCT_BMs) {
    String[] keys = new String[DCT_BMs.length];
    for(int i=0;i<keys.length;i++) {
      keys[i] = DCT_ID+"_"+DCT_BMs[i];
    }
    return getRowSets(keys);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BMs String[]
   * @param F_LANG String
   * @return EFRowSet[]
   */
  public EFRowSet[] getMDMLRowSets(String DCT_ID,String[] DCT_BMs,String F_LANG) {
    String[] keys = new String[DCT_BMs.length];
    for(int i=0;i<keys.length;i++) {
      keys[i] = DCT_ID+"_"+DCT_BMs[i]+"_"+F_LANG;
    }
    return getRowSets(keys);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BMs String[]
   * @param UNIT_ID String
   * @return EFRowSet[]
   */
  public EFRowSet[] getMDMURowSets(String DCT_ID,String[] DCT_BMs,String UNIT_ID) {
    String[] keys = new String[DCT_BMs.length];
    for(int i=0;i<keys.length;i++) {
      keys[i] = DCT_ID+"_"+DCT_BMs[i]+"_"+UNIT_ID;
    }
    return getRowSets(keys);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BMs String[]
   * @param F_LANG String
   * @param UNIT_ID String
   * @return EFRowSet[]
   */
  public EFRowSet[] getMDMLMURowSets(String DCT_ID,String[] DCT_BMs,String F_LANG,String UNIT_ID) {
    String[] keys = new String[DCT_BMs.length];
    for(int i=0;i<keys.length;i++) {
      keys[i] = DCT_ID+"_"+DCT_BMs[i]+"_"+F_LANG+"_"+UNIT_ID;
    }
    return getRowSets(keys);
  }


















  /**
   *
   * @param DCT_ID String
   * @param DCT_BM String
   * @param rowSet EFRowSet
   * @return boolean
   */
  public boolean addMDMRowSet(String DCT_ID,String DCT_BM,EFRowSet rowSet) {
    return addRowSet(DCT_ID+"_"+DCT_BM,rowSet);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BM String
   * @param rowSet EFRowSet
   * @param UNIT_ID String
   * @return boolean
   */
  public boolean addMDMURowSet(String DCT_ID,String DCT_BM,String UNIT_ID,EFRowSet rowSet) {
    return addRowSet(DCT_ID+"_"+DCT_BM+"_"+UNIT_ID,rowSet);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BM String
   * @param F_LANG String
   * @param rowSet EFRowSet
   * @return boolean
   */
  public boolean addMDMLRowSet(String DCT_ID,String DCT_BM,String F_LANG,EFRowSet rowSet) {
    return addRowSet(DCT_ID+"_"+DCT_BM+"_"+F_LANG,rowSet);
  }
  /**
   *
   * @param DCT_ID String
   * @param DCT_BM String
   * @param F_LANG String
   * @param rowSet EFRowSet
   * @param UNIT_ID String
   * @return boolean
   */
  public boolean addMDMUMLRowSet(String DCT_ID,String DCT_BM,String F_LANG,String UNIT_ID,EFRowSet rowSet) {
    return addRowSet(DCT_ID+"_"+DCT_BM+"_"+F_LANG+"_"+UNIT_ID,rowSet);
  }







  /**
   *
   * @param keys String[]
   * @return EFRowSet[]
   */
  protected EFRowSet[] getRowSets(String[] keys) {
    Object[] res = MemCachedManager.getDefault().getMemCached().getMultiArray(keys);
    if ( res == null || res.length == 0 ) return null;
    EFRowSet[] sets = new EFRowSet[res.length];
    for(int i=0;i<res.length;i++) {
      sets[i] = (EFRowSet)res[i];
    }
    return sets;
  }
  /**
   *
   * @param key String
   * @param rowSet EFRowSet
   * @return boolean
   */
  protected boolean addRowSet(String key,EFRowSet rowSet) {
//    WriteFile(rowSet);
    if ( MemCachedManager.getDefault().getMemCached().keyExists(key) )
      return MemCachedManager.getDefault().getMemCached().replace(key,rowSet);
    else
      return MemCachedManager.getDefault().getMemCached().add(key,rowSet);
  }
  private void WriteFile(Object wo) {
    try {
      String fileName = "d://testData//EFRowSet//"+wo.getClass().getName()+"_"+ System.currentTimeMillis() + ".dat";
      OutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream out = new ObjectOutputStream(fos);
      out.writeObject(wo);
      out.flush();
      out.close();
      fos.close();
    } catch ( Exception e ) {e.printStackTrace();}
  }
}
