package jreportwh.jdof.classes.common.util;

import java.io.File;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class IOUtil
{
  static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");
    public IOUtil()
    {
    }
    /**
     * ·���Ƿ����
     * @param path
     * @return
     */
    public static boolean isPathExist(String path){
        return new File(path.trim()).exists();
    }

    /**
     * �Ƿ���Excel�ļ�
     * @param path
     * @return
     */
    public static boolean isExcelFile(String path){
        if(path.trim().endsWith(".xls")){
            return true;
        }
        return false;
    }
}
