package com.efounder.service.meta.mdl;

import com.efounder.service.meta.base.*;

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
public class AnalyzeCombineModel extends MetaObject {
  /**
   *
   */
  protected AnalyzeCombineModel() {
  }
  /**
   *
   * @return AnalyzeDictCombine
   */
  public static AnalyzeCombineModel getInstance() {
    return new AnalyzeCombineModel();
  }
  /**
   *
   */
  protected AnalyzeModel analyzeModel = null;
  /**
   *
   * @return AnalyzeModel
   */
  public AnalyzeModel getAnalyzeModel() {
    return analyzeModel;
  }
  /**
   *
   */
  protected DictCombine dictCombine = null;
  /**
   *
   * @return DictCombine
   */
  public DictCombine getDictCombine() {
    return dictCombine;
  }
}
