package com.efounder.service.meta;

import org.openide.util.*;
import com.efounder.service.meta.mdl.*;
import com.efounder.service.meta.db.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;
import com.efounder.eai.*;
/**
 * <p>Title: </p>
 * <p>Description: ��ݿ⼶���Ԫ��ݹ��?����������:��ݱ?����������ͼ���洢���</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DBMetadataManager {
  /**
   *
   */
  protected static DBMetadataManager dbMetadataManager = null;
  /**
   *
   */
  protected DBMetadataManager() {

  }
  protected String metaKey = null;
  /**
   *
   * @param PO JParamObject
   * @param conn JConnection
   * @return MetaContextObject
   */
  public final MetaContextObject createContextObject() {
    MetaContextObject mco = MetaContextObject.getInstance(JParamObject.Create(),null,null,metaKey);
    return mco;
  }
  /**
   *
   * @param PO JParamObject
   * @param conn JConnection
   * @return MetaContextObject
   */
  public final MetaContextObject createContextObject(JParamObject PO) {
    MetaContextObject mco = MetaContextObject.getInstance(PO,null,null,metaKey);
    return mco;
  }
  /**
   *
   * @param PO JParamObject
   * @param conn JConnection
   * @return MetaContextObject
   */
  public final MetaContextObject createContextObject(JParamObject PO,JConnection conn,Object userObject) {
    MetaContextObject mco = MetaContextObject.getInstance(PO,conn,userObject,metaKey);
    return mco;
  }
  /**
   *
   * @return DBMetadataManager
   */
  public static final synchronized DBMetadataManager getDefault(String Key) {
    if ( dbMetadataManager == null ) {
      // lookup��DataResourceManager
      dbMetadataManager = (DBMetadataManager) Lookup.getDefault().lookup(DBMetadataManager.class,EAI.Tier.toLowerCase()+"_"+Key);
      dbMetadataManager.metaKey = Key;
    }
    return dbMetadataManager;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param analyzeKey Object
   * @return AnalyzeModel
   */
  public AnalyzeModel getAnalyzeModel(MetaContextObject mco,Object analyzeKey) throws Exception {
    // ����һ��ʵ��
    AnalyzeModel analyzeModel = AnalyzeModel.getInstance();
    mco.startStyle();
    try {
      // ����Key
      analyzeModel.setID(analyzeKey);
      // װ��ģ����Ϣ
      getAnalyzeModel(mco, analyzeModel);
      // ��ȡ���ģ���б�
      java.util.List combineModelList = getAnalyzeCombineModelList(mco,
          analyzeKey);
      // װ�����ģ���б�
      Modelutil.attachModelCombineModelList(analyzeModel, combineModelList);
      // ��ȡ��������ֵ���Ϣ
      Object DICT_ID = getAttribDictKey(analyzeModel);
      DictMetadata dictMetadata = getDictMetadata(mco, DICT_ID);
      Modelutil.attachAnalyzeModelAttribDict(analyzeModel, dictMetadata);
      // ��ȡ������ʵ����Ϣ
      Object FACT_ID = getValueFactKey(analyzeModel);
      FactMetadata factMetadata = getFactMetadata(mco, FACT_ID);
      Modelutil.attachAnalyzeModelValueFact(analyzeModel, factMetadata);
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    // ����
    return analyzeModel;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param analyzeKey Object
   * @return List
   * @throws Exception
   */
  public java.util.List getAnalyzeCombineModelList(MetaContextObject mco,Object analyzeKey) throws Exception{
    java.util.List modelList = new java.util.ArrayList();
    getAnalyzeCombineModelList(mco,analyzeKey,modelList);
    AnalyzeCombineModel combineModel = null;Object dictCombineKey = null;
    DictCombine dictCombine = null;
    mco.startStyle();
    try {
      // ��ȡÿһ��ϸ��ģ�͵�����ֵ����ģ��
      for (int i = 0; i < modelList.size(); i++) {
        // ��ȡ��Ϸ���ģ��
        combineModel = (AnalyzeCombineModel) modelList.get(i);
        // ��ȡ����ֵ����ģ�͵�KEY
        dictCombineKey = getAnalyzeCombineModelDictCombineKey(combineModel);
        // ��ȡ����ֵ����ģ��
        dictCombine = getDictCombine(mco, dictCombineKey);
        //
        Modelutil.attachModelDictCombine(combineModel, dictCombine);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    return modelList;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param dictCombineKey Object
   * @return DictCombine
   * @throws Exception
   */
  public DictCombine getDictCombine(MetaContextObject mco,Object dictCombineKey) throws Exception {
    DictCombine dictCombine = DictCombine.getInstance();
    // ����ID
    dictCombine.setID(dictCombineKey);
    // ��ȡ
    mco.stopStyle();
    try {
      getDictCombine(mco, dictCombine);
      java.util.List dictKeyList = getDictCombineDictList(dictCombine);
      Object dictKey = null;
      java.util.List dictList = new java.util.ArrayList();
      // ��ȡ����ֵ�����е���������ֵ����Ϣ���ŵ��б���
      for (int i = 0; i < dictKeyList.size(); i++) {
        dictKey = dictKeyList.get(i);
        // ��ȡ����ֵ��Ԫ�����Ϣ
        DictMetadata dictMetadata = getDictMetadata(mco, dictKey);
        // ���ӵ��б���
        dictList.add(dictMetadata);
      }
      // ���������
      Modelutil.attachDictCombineDictList(dictCombine, dictList);
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    return dictCombine;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @return DictMetadata
   * @throws Exception
   */
  public DictMetadata getDictMetadata(MetaContextObject mco,Object dictKey) throws Exception {
    DictMetadata dictMetadata = DictMetadata.getInstance();
    dictMetadata.setID(dictKey);
    mco.startStyle();
    try {
      // ��ȡ����ֵ���Ϣ
      getDictMetadata(mco, dictMetadata);
      // ��ȡ�ֵ��Ӧ�ı���Ϣ
      TableMetadata tableMetadata = getTableMetadata(mco, dictKey);
      // ���ñ���Ϣ
      MetadataUtils.attachDictTable(dictMetadata, tableMetadata);
      // ��ȡ�ֵ�ı������
      DctbmgzMetadata dctbmgzMetadata = DctbmgzMetadata.getInstance();
      getDctbmgzMetadata(mco, dictMetadata, dctbmgzMetadata);
      MetadataUtils.attachDictTableBmgz(dictMetadata, dctbmgzMetadata);
      // ��ȡ�ֵ����ݹ�ϵ
      java.util.List dctrlglList = new java.util.ArrayList();
      getDctrlglMetadata(mco, dictMetadata, dctrlglList);
      MetadataUtils.attachDictTableRlgl(dictMetadata, dctrlglList);
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    return dictMetadata;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param factKey Object
   * @return FactMetadata
   * @throws Exception
   */
  public FactMetadata getFactMetadata(MetaContextObject mco,Object factKey) throws Exception {
    FactMetadata factMetadata = FactMetadata.getInstance();
    factMetadata.setID(factKey);
    mco.startStyle();
    try {
      // ��ȡ��ʵ����Ϣ
      getFactMetadata(mco, factMetadata);
      // ��ȡ��ʵ���Ӧ�ı���Ϣ
      TableMetadata tableMetadata = getTableMetadata(mco, factKey);
      // ���ñ���Ϣ
      MetadataUtils.attachFactTable(factMetadata, tableMetadata);
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    return factMetadata;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @return TableMetadata
   * @throws Exception
   */
  public TableMetadata getTableMetadata(MetaContextObject mco,Object tableKey) throws Exception {
    TableMetadata tableMetadata = TableMetadata.getInstance();
    tableMetadata.setID(tableKey);
    mco.startStyle();
    try {
      // ��ȡ����Ϣ
      getTableMetadata(mco, tableMetadata);
      // ��������
      java.util.List colList = getTableColList(mco, tableKey);
      MetadataUtils.attachTableColumn(tableMetadata, colList);
      // ����������
      java.util.List keyList = getTableKeyList(mco, tableKey);
      MetadataUtils.attachTableKey(tableMetadata, keyList);
      // ����������
      java.util.List indexList = getTableIndexList(mco, tableKey);
      MetadataUtils.attachTableIndex(tableMetadata, indexList);
      // �����е�����
      getColRefDictMetadata(mco, tableMetadata);
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    return tableMetadata;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param modelKey Object
   * @return BizModel
   * @throws Exception
   */
  public BizModel getBizModel(MetaContextObject mco,Object modelKey) throws Exception {
    BizModel bizModel = BizModel.getInstance();
    mco.startStyle();
    try {
      bizModel.setID(modelKey);
      getBizModel(mco, bizModel);
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      mco.stopStyle();
    }
    return bizModel;
  }
  /**
   *
   * @param analyzeModel AnalyzeModel
   * @return Object
   */
  protected abstract Object getAttribDictKey(AnalyzeModel analyzeModel);
  /**
   *
   * @param analyzeModel AnalyzeModel
   * @return Object
   */
  protected abstract Object getValueFactKey(AnalyzeModel analyzeModel);
  /**
   *
   * @param mco MetaContextObject
   * @param analyzeModel AnalyzeModel
   * @throws Exception
   */
  protected abstract void getAnalyzeModel(MetaContextObject mco,AnalyzeModel analyzeModel) throws Exception;

  /**
   *
   * @param combineModel AnalyzeCombineModel
   * @return Object
   */
  protected abstract Object getAnalyzeCombineModelDictCombineKey(AnalyzeCombineModel combineModel);
  /**
   *
   * @param dictCombine DictCombine
   * @return List
   */
  protected abstract java.util.List getDictCombineDictList(DictCombine dictCombine);
  /**
   *
   * @param mco MetaContextObject
   * @param dictCombine DictCombine
   * @throws Exception
   */
  protected abstract void getDictCombine(MetaContextObject mco,DictCombine dictCombine) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param analyzeKey Object
   * @param modelList List
   * @throws Exception
   */
  protected abstract void getAnalyzeCombineModelList(MetaContextObject mco,Object analyzeKey,java.util.List modelList) throws Exception;

  /**
   *
   * @param mco MetaContextObject
   * @param dictMetadata DictMetadata
   * @param dctbmgzMetadata DctbmgzMetadata
   * @throws Exception
   */
  protected abstract void getDctbmgzMetadata(MetaContextObject mco,DictMetadata dictMetadata,DctbmgzMetadata dctbmgzMetadata) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param dictMetadata DictMetadata
   * @param dctrlglList List
   * @throws Exception
   */
  protected abstract void getDctrlglMetadata(MetaContextObject mco,DictMetadata dictMetadata,java.util.List dctrlglList) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param dictMetadata DictMetadata
   * @throws Exception
   */
  protected abstract void getDictMetadata(MetaContextObject mco,DictMetadata dictMetadata) throws Exception;

  /**
   *
   * @param mco MetaContextObject
   * @param factMetadata FactMetadata
   * @throws Exception
   */
  protected abstract void getFactMetadata(MetaContextObject mco,FactMetadata factMetadata) throws Exception;

  /**
   *
   * @param mco MetaContextObject
   * @param tableMetadata TableMetadata
   * @throws Exception
   */
  protected final void getColRefDictMetadata(MetaContextObject mco,TableMetadata tableMetadata) throws Exception {
    java.util.List colList = tableMetadata.getColList();ColMetaData colMetadata = null;
    for(int i=0;i<colList.size();i++) {
      colMetadata = (ColMetaData)colList.get(i);
      getColRefDictMetadata(mco,tableMetadata,colMetadata);
    }
  }
  /**
   *
   * @param mco MetaContextObject
   * @param tableMetadata TableMetadata
   * @param colMetadata ColMetadata
   * @throws Exception
   */
  protected abstract void getColRefDictMetadata(MetaContextObject mco,TableMetadata tableMetadata,ColMetaData colMetadata) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param tableMetadata TableMetadata
   * @throws Exception
   */
  protected abstract void getTableMetadata(MetaContextObject mco,TableMetadata tableMetadata) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @return List
   * @throws Exception
   */
  protected final java.util.List getTableColList(MetaContextObject mco,Object tableKey) throws Exception {
    java.util.List colList = new java.util.ArrayList();
    getTableColList(mco,tableKey,colList);
    return colList;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @param colList List
   * @throws Exception
   */
  protected abstract void getTableColList(MetaContextObject mco,Object tableKey,java.util.List colList) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @return List
   * @throws Exception
   */
  protected final java.util.List getTableKeyList(MetaContextObject mco,Object tableKey) throws Exception {
    java.util.List keyList = new java.util.ArrayList();
    getTableKeyList(mco,tableKey,keyList);
    return keyList;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @param keyList List
   * @throws Exception
   */
  protected abstract void getTableKeyList(MetaContextObject mco,Object tableKey,java.util.List keyList) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @return List
   * @throws Exception
   */
  protected final java.util.List getTableIndexList(MetaContextObject mco,Object tableKey) throws Exception {
    java.util.List indexList = new java.util.ArrayList();
    getTableIndexList(mco,tableKey,indexList);
    return indexList;
  }
  /**
   *
   * @param mco MetaContextObject
   * @param tableKey Object
   * @param indexList List
   * @throws Exception
   */
  protected abstract void getTableIndexList(MetaContextObject mco,Object tableKey,java.util.List indexList) throws Exception;

  /**
   *
   * @param mco MetaContextObject
   * @param bizModel BizModel
   * @throws Exception
   */
  protected abstract void getBizModel(MetaContextObject mco,BizModel bizModel) throws Exception;
  /**
   *
   * @param mco MetaContextObject
   * @param key Object
   * @param userObject Object
   * @return Object
   * @throws Exception
   */
  public abstract Object getMetadata(MetaContextObject mco,Object type,Object key,Object userObject) throws Exception;
}
