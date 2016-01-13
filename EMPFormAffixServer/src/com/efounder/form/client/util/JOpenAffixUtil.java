package com.efounder.form.client.util;

import java.awt.Frame;
import java.io.File;
import com.core.xml.PackageStub;
import java.util.List;
import com.core.xml.StubObject;
import com.efounder.form.client.component.FormPicLabel;
import com.efounder.form.client.component.frame.JAffixManagerWorkSpace;
import com.efounder.form.client.component.frame.JAffixUploadPanel;
import com.efounder.form.client.component.infer.IOpenAffix;

import javax.swing.JPanel;
import com.efounder.form.client.component.pic.JPicAttachFunction;

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
public class JOpenAffixUtil {
	
	/**
	 * 
	 */
    public JOpenAffixUtil() {
    }
    
    /**
     * 
     * @param WJLX
     * @param res
     * @param fielName
     * @param panel
     * @param order
     * @throws Exception
     */
    public static void openAffixData(String WJLX,Object res,String fielName,JPanel panel,String order) throws Exception {
    	openAffixData(null, WJLX, res, fielName, panel, order);
    }

    /**
     * 
     * @param showOwner
     * @param WJLX
     * @param res
     * @param fielName
     * @param panel
     * @param order
     * @throws Exception
     */
    public static void openAffixData(Object showOwner, String WJLX,Object res,String fielName,JPanel panel,String order) throws Exception {
        List affixTypeList = PackageStub.getContentVector("AffixType");
        IOpenAffix openAffixApp = null;
        for(int i = 0; i < affixTypeList.size(); i++){
            StubObject stub = (StubObject)affixTypeList.get(i);
            String id = stub.getString("id","");
            String clazz = stub.getString("clazz","");
            String fileSuffix = stub.getString("fileSuffix", "");
            if(fileSuffix.indexOf(WJLX.toLowerCase()) > -1){
                openAffixApp = (IOpenAffix)Class.forName(clazz).newInstance();
                openAffixApp.open(showOwner,res,fielName,panel,order);
                break;
            }
        }
    }

    /**
     * 精简版
     * @param WJLX String
     * @param res Object
     * @throws Exception
     */
    public static void openAffixData(String WJLX,Object res, String fileName,String order) throws Exception {
        List affixTypeList = PackageStub.getContentVector("AffixType");
        IOpenAffix openAffixApp = null;
        for(int i = 0; i < affixTypeList.size(); i++){
            StubObject stub = (StubObject)affixTypeList.get(i);
            String id = stub.getString("id","");
            String clazz = stub.getString("clazz","");
            if(id.equals(WJLX)){
                openAffixApp = (IOpenAffix)Class.forName(clazz).newInstance();
                openAffixApp.open(null,res,fileName,null,order);
                break;
            }
        }
    }

    public static void openAffixData(String WJLX,Object res, String fileName,String order, FormPicLabel picLabel) throws Exception {
        JAffixManagerWorkSpace workspace = JPicAttachFunction.createWorkspace(picLabel);
       List affixTypeList = PackageStub.getContentVector("AffixType");
       IOpenAffix openAffixApp = null;
       for(int i = 0; i < affixTypeList.size(); i++){
           StubObject stub = (StubObject)affixTypeList.get(i);
           String id = stub.getString("id","");
           String clazz = stub.getString("clazz","");
           if(id.equals(WJLX)){
               openAffixApp = (IOpenAffix)Class.forName(clazz).newInstance();
               openAffixApp.open(null,res,fileName,workspace.getUploadAffixPanel(),order);
               break;
           }
       }
   }

    public static void openAffixInDlg(Frame frame, String title, boolean modal, String WJLX,Object res,String fielName,JPanel panel,String order) throws Exception {
        List affixTypeList = PackageStub.getContentVector("AffixType");
        IOpenAffix openAffixApp = null;
        String fileSuffix = "";
        for(int i = 0; i < affixTypeList.size(); i++){
            StubObject stub = (StubObject)affixTypeList.get(i);
            String id = stub.getString("id","");
            String clazz = stub.getString("clazz","");
            fileSuffix = stub.getString("fileSuffix","");
            if(id.equals(WJLX.toLowerCase())){
                openAffixApp = (IOpenAffix)Class.forName(clazz).newInstance();
                openAffixApp.open(frame, title, modal, res, fielName, panel, order);
                break;
            }
        }
    }
}
