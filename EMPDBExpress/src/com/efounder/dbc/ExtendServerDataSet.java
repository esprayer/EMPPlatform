package com.efounder.dbc;

import com.borland.dx.sql.dataset.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ExtendServerDataSet {
    // 准备Load
  public Object dataSetStartLoad(Database dataBase, RequestDataSetData request,
                                 ClientDataSetData cds) throws Exception;

  // 结束Load
  public Object dataSetEndLoad(Database dataBase, RequestDataSetData request,
                               ClientDataSetData cds, QueryDataSet qds) throws
      Exception;

    // 准备Save
  public Object dataSetStartSave(Database dataBase, RequestDataSetData request,
                                 ClientDataSetData cds, QueryDataSet qds) throws
      Exception;

    // 结束Save
  public Object dataSetEndSave(Database dataBase, RequestDataSetData request,
                               ClientDataSetData cds, QueryDataSet qds) throws
      Exception;
}
