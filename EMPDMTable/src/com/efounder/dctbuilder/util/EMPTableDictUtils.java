package com.efounder.dctbuilder.util;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.loader.DefaultDctProvider;
import com.efounder.dctbuilder.loader.DefaultResolver;
import com.efounder.dctbuilder.view.DictView;

public class EMPTableDictUtils {
	
	public static void setDefaultDctViewProperty(DictModel model) {
//        DefaultDictPainter tp = new DefaultDictPainter(model);
//        if (model.getView() != null)
//            ( (DictView) model.getView()).setTablePainter(tp);
        DefaultDctProvider provider = new DefaultDctProvider();
        DefaultResolver resolver = new DefaultResolver();
        model.setProvider(provider);
        model.setResolver(resolver);
    }
}
