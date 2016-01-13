package jdatareport.dal.classes;

import java.io.*;

import com.eai.toolkit.xml.*;
import jdal.foundation.classes.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.MtoDB.BofJfc.DBO.*;

public class JDALDataReport
    extends JAbstractDataActiveObject {
    public JDALDataReport() {
    }

    /**
     * 打开报表配置文件
     * @param Param
     * @param Data
     * @param CustomObject
     * @param AdditiveObject
     * @return
     */
    public Object OpenReport(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
        String path = "/JDataReport/" + Data.toString() + ".xml";
        try {
            InputStream IStream = CommonDBObject.createResourceFileInputStream(path, (JParamObject) Param);
            XmlEngine XML = new XmlEngine(IStream);
            if (XML.isEmpty()) {
                System.out.print("Load JDataReport define empty.");
                return new JResponseObject("");
            }
            else {
                return new JResponseObject(XML.GetXmlString());
            }
        }
        catch (Exception E) {
            System.out.print("Load JDataReport define error.");
            E.printStackTrace();
            return new JResponseObject("");
        }

    }

    /**
     * 打开查询结果
     * @param Param
     * @param Data
     * @param CustomObject
     * @param AdditiveObject
     * @return
     */
    public Object OpenQuery(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
        String path = "/JDataReport/" + Data.toString() + ".xml";
        try {
            InputStream IStream = CommonDBObject.createResourceFileInputStream(path, (JParamObject) Param);
            XmlEngine XML = new XmlEngine(IStream);
            if (XML.isEmpty()) {
                System.out.print("Load JDataReport define empty.");
                return new JResponseObject("");
            }
            else {
                return new JResponseObject(XML.GetXmlString());
            }
        }
        catch (Exception E) {
            System.out.print("Load JDataReport define error.");
            E.printStackTrace();
            return new JResponseObject("");
        }
    }

}