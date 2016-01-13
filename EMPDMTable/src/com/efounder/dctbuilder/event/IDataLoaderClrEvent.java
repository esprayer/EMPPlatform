package com.efounder.dctbuilder.event;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.data.DctContext;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IDataLoaderClrEvent extends IClientEvent {

    public void beforeDataLoad(DictModel o1,DctContext context)  throws Exception;
    public void SetColumnPropery(DictModel o1,DctContext context)  throws Exception;
    public void afterDataLoad(DictModel o1,DctContext context)  throws Exception;
    public Object beforeDataSave(DictModel o1,DctContext context)  throws Exception;
    public void afterDataSave(DictModel o1,DctContext context)  throws Exception;
}
