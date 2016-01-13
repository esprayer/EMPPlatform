package com.efounder.dctbuilder.loader;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.mdl.*;

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
public class DefaultResolver implements Resolver {
    public DefaultResolver() {
    }

    public Object resolveData(DictModel dctModel) throws Exception {
        return dctModel.getDataSet().saveData();
    }
}
