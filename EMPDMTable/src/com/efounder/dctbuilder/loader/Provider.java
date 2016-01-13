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
public  interface Provider {

    public void  provideData(DictModel tableModel, boolean toOpen) throws Exception;
}
