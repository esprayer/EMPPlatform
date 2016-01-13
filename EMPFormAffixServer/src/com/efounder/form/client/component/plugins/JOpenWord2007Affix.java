package com.efounder.form.client.component.plugins;

import javax.swing.JPanel;
import com.efounder.form.client.component.pic.JFileByteConvertFunction;

import java.awt.Frame;
import java.io.File;

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
public class JOpenWord2007Affix extends JOpenAffix {
    public JOpenWord2007Affix() {
    }

    public void open(Object showOwner, Object res, String fileName, JPanel panel,
                     String order) throws Exception {
    	open(res, fileName, panel, order);
    }
    
    public void open(Frame frame, String title, boolean modal, Object res, String fileName, JPanel panel, String order) throws Exception{
    	open(res, fileName, panel, order);
    }
    
    public void open(Object res, String fileName, JPanel panel, String order) throws Exception{
    	String cmd = "";
        if(res instanceof String){///从指定的路径打开
            String path  = (String)res;
            cmd = "cmd /c start  " + path;
        }else if(res instanceof byte[]){
            String strFile = "C:\\WINDOWS\\Temp\\" + fileName + ".docx";
            File file = JFileByteConvertFunction.getFileFromBytes((byte[]) res,strFile);
//         WordBean wordBean = new WordBean(true);
//         wordBean.openDocument(strFile);
            cmd = "cmd /c start  " + strFile;
        }
        Process ps = Runtime.getRuntime().exec(cmd);
    }
}
