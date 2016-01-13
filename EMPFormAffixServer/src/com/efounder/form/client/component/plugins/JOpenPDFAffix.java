package com.efounder.form.client.component.plugins;

import com.efounder.form.client.component.pic.JFileByteConvertFunction;
import com.lowagie.tools.Executable;

import java.awt.Frame;
import java.io.File;
import javax.swing.JPanel;

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

public class JOpenPDFAffix extends JOpenAffix{
    public JOpenPDFAffix() {
    }

    public void open(Object showOwner, Object res,String fileName,JPanel panel,String order) throws Exception {
    	open(res, fileName, panel, order);
    }
    
    public void open(Frame frame, String title, boolean modal, Object res, String fileName, JPanel panel, String order) throws Exception{
    	open(res, fileName, panel, order);
    }
    
    public void open(Object res, String fileName, JPanel panel, String order) throws Exception{
    	if(res instanceof String){///从指定的路径打开
            String path  = (String)res;
            Executable.openDocument(path);
        }else if(res instanceof byte[]){
            byte[] resData = (byte[])res;
            String strFile = "C:\\WINDOWS\\Temp\\"+fileName+".pdf";
            File file = JFileByteConvertFunction.getFileFromBytes(resData,strFile);
            Executable.openDocument(file);
        }
    }
    
    /**
     * 删除临时文件
     * @param file File
     * @return boolean
     */
    public boolean deleteFile(File file){
        boolean b = false;
        if(file.exists()){
            b = file.delete();
        }
        return b;
    }

}
