package com.efounder.dctbuilder.event;

import com.borland.dx.dataset.*;
import com.core.xml.StubObject;
import com.core.xml.JBOFClass;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
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
public class DataSetColumnEventClass extends DataSetEventClass {

	public DataSetColumnEventClass(DictModel m) {
		super(m);
		type="column";
	}

	//column事件
	public void changed(EFDataSet dataSet, Column column) {
		StubObject so=new StubObject();
		so.setObject("column",column);
		try {
			ClientEventProcess( "changed", so);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void validate(EFDataSet dataSet, Column column) throws Exception {
	}
}
