package com.efounder.service.meta.mdl;

import com.efounder.service.meta.base.*;
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
public class AnalyzeModel extends MetaObject {
  /**
   *
   */
  protected AnalyzeModel() {
  }
  /**
   *
   * @return AnalyzeModel
   */
  public static AnalyzeModel getInstance() {
    return new AnalyzeModel();
  }
  /**
   *
   */
  protected java.util.List combineModelList = null;
  /**
   *
   * @return List
   */
  public java.util.List getCombineModelList() {
    return combineModelList;
  }
  protected DictMetadata attribDict = null;
  /**
   *
   * @return DictMetadata
   */
  public DictMetadata getAttribDict() {
    return attribDict;
  }
  protected FactMetadata valueFact  = null;
  /**
   *
   * @return FactMetadata
   */
  public FactMetadata getValueFact() {
    return valueFact;
  }
}
