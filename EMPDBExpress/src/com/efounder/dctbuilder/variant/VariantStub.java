package com.efounder.dctbuilder.variant;

import com.core.xml.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.eai.data.JParamObject;

/**
 * <p>Title: </p>
 * <p>Description: </p
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class VariantStub extends StubObject {

    private String variantName;
    private JParamObject PO;
    private DictModel dictModel;

    public VariantStub(String name, DictModel model) {
        variantName = name;
        dictModel = model;
    }
    
    public VariantStub(String namel) {
        variantName = namel;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getVariantName() {
        return variantName;
    }

	public JParamObject getPO() {
		return PO;
	}

	public void setPO(JParamObject PO) {
		this.PO = PO;
	}
}
