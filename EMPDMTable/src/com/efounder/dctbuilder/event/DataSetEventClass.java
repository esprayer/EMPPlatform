package com.efounder.dctbuilder.event;

import com.core.xml.StubObject;
import com.core.xml.JBOFClass;
import java.util.*;

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
public class DataSetEventClass {
    DictModel dm;
    String type="none";

    public DataSetEventClass(DictModel m) {
        dm = m;
    }
    public void ClientEventProcess(String method,StubObject param)throws Exception{
        List list = dm.getUserPlugin(IClientEvent.class,DictPluginManager.DATASETEVENT);
        for (int i = 0; i < list.size(); i++) {
            IClientEvent ifp = (IClientEvent) list.get(i);
            if (ifp.canProcess(dm, dm.getCdsParam()))
                JBOFClass.CallObjectMethodException(ifp, method, new Object[] {dm, param});
        }
 }

}
