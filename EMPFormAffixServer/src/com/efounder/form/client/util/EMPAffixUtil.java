package com.efounder.form.client.util;

import java.util.List;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;

public class EMPAffixUtil {
	public static String getOperTypeByWjlx (String WJLX){
        List affixTypeList = PackageStub.getContentVector("AffixType");
        String fileSuffix = "";
        for(int i = 0; i < affixTypeList.size(); i++){
            StubObject stub = (StubObject)affixTypeList.get(i);
            String id = stub.getString("id","");
            fileSuffix = stub.getString("fileSuffix", "");
            if(fileSuffix.indexOf(WJLX.toLowerCase()) > -1) {
            	return id;
            }
        }
        return "";
	}
}
