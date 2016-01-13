package com.efounder.dctbuilder.event;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.mdl.*;
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
public class DataLoaderCltAdapter implements IDataLoaderClrEvent {
    public DataLoaderCltAdapter() {
    }
    public void beforeDataLoad(DictModel o1, DctContext context) throws Exception {
    }

    public void SetColumnPropery(DictModel o1, DctContext context) throws Exception {
    }

    public void afterDataLoad(DictModel o1, DctContext context) throws Exception {
    }

    public Object beforeDataSave(DictModel o1, DctContext context) throws Exception {
        return null;
    }

    public void afterDataSave(DictModel o1, DctContext context) throws Exception {
    }

    public boolean canProcess(DictModel o1, DctContext context) throws Exception {
        return true;
    }
}
