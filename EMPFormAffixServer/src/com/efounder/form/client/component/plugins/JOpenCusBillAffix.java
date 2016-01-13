package com.efounder.form.client.component.plugins;

import javax.swing.JPanel;

import java.awt.Frame;
import java.io.File;
import com.efounder.form.client.component.infer.IOpenAffix;
import org.openide.WaitingManager;
import com.core.xml.StubObject;
import com.core.xml.PackageStub;
import java.util.List;
import java.lang.reflect.Constructor;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class JOpenCusBillAffix extends JOpenAffix {
    public JOpenCusBillAffix() {
    }

    public void open(Object showOwner, Object res, String fileName, JPanel panel,
                     String order) throws Exception {
    	open(res, fileName, panel, order);
    }

    public void open(Frame frame, String title, boolean modal, Object res, String fileName, JPanel panel, String order) throws Exception{
    	open(res, fileName, panel, order);
    }
    
    public void open(Object res, String fileName, JPanel panel, String order) throws Exception{
    	Object[] obj = (Object[])res;
        String BBBH = (String)obj[0];
        byte[] blobData = (byte[])obj[1];
        StubObject paramObj = new StubObject();
        paramObj.setString("F_BH", BBBH);
        paramObj.setObject("BlobData",blobData);
        paramObj.setObject("DrawKey","DrawCusBillFormat");
//        paramObj.setInt("UNIT_TYPE", FormModel.UNIT_TYPE_DWRPT);

        List openClassList = PackageStub.getContentVector("CusBillOpenClass");
        if (openClassList != null && openClassList.size() > 0) {
            StubObject stub = (StubObject) openClassList.get(0);
            String openClazz = stub.getString("openClazz", "");

            Class cls = Class.forName(openClazz);
            Class[] paramDef = new Class[]{ StubObject.class};
            Constructor constructor = cls.getConstructor( paramDef );

            Object[] params = new Object[]{ paramObj};
            Runnable clsInstance = (Runnable)constructor.newInstance(params);

           //  不带参数
            WaitingManager.getDefault().waitInvoke("请等待...",    "正在打开" + fileName + ",请稍等...", null, clsInstance);
        }
    }
}
