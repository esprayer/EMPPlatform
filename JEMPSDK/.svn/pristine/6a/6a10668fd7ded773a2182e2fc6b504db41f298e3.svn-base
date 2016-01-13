package com.efounder.service.meta.mdl;

import com.efounder.service.meta.db.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Modelutil {
  /**
   *
   */
  protected Modelutil() {
  }
  /**
   *
   * @param combineModel AnalyzeCombineModel
   * @param dictCombine DictCombine
   */
  public static void attachModelDictCombine(AnalyzeCombineModel combineModel,DictCombine dictCombine) {
    combineModel.dictCombine = dictCombine;
  }
  /**
   *
   * @param analyzeModel AnalyzeModel
   * @param combineModelList List
   */
  public static void attachModelCombineModelList(AnalyzeModel analyzeModel,java.util.List combineModelList) {
    analyzeModel.combineModelList = new java.util.ArrayList();
    AnalyzeCombineModel combineModel = null;
    for(int i=0;i<combineModelList.size();i++) {
      combineModel = (AnalyzeCombineModel)combineModelList.get(i);
      combineModel.analyzeModel = analyzeModel;
    }
    analyzeModel.combineModelList.addAll(combineModelList);
  }
  /**
   *
   * @param dictCombine DictCombine
   * @param dictList List
   */
  public static void attachDictCombineDictList(DictCombine dictCombine,java.util.List dictList) {
    dictCombine.dictList = new java.util.ArrayList();
    dictCombine.dictList.addAll(dictList);
  }
  /**
   *
   * @param analyzeModel AnalyzeModel
   * @param dictMetadata DictMetadata
   */
  public static void attachAnalyzeModelAttribDict(AnalyzeModel analyzeModel,DictMetadata dictMetadata) {
    analyzeModel.attribDict = dictMetadata;
  }
  /**
   *
   * @param analyzeModel AnalyzeModel
   * @param factMetadata FactMetadata
   */
  public static void attachAnalyzeModelValueFact(AnalyzeModel analyzeModel,FactMetadata factMetadata) {
    analyzeModel.valueFact = factMetadata;
  }
}
