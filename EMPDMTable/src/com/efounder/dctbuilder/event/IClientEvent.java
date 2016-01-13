package com.efounder.dctbuilder.event;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.data.*;

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
public interface IClientEvent {
    public boolean canProcess(DictModel o1,DctContext context) throws Exception;
}
