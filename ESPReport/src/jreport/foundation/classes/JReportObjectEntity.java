package jreport.foundation.classes;

import java.io.*;
import java.util.zip.*;

import com.efounder.eai.data.JDataObject;

import jreport.foundation.classes.data.JBdDataObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JReportObjectEntity implements java.io.Serializable {
  public String BBQX = null;
  public String ADDQX = null;
  public Object AddObject = null;    // 附加对象
  public byte[] FormatObject = null; // 用来存放报表的格式对象此对象是压缩过的
//  public byte[] DataObject   = null; // 用来存放报表的数据对象,是一个XML字符串
  public String DataObject   = null;   // 用来存放报表的数据对象,是一个XML字符串

  /**
   * 变动数据对象
   * 因为现在变动行用XML打开很慢
   * 所以改为在后台形成数组对象存放数据
   * 在前台再形成XML对象的方式来处理
   * modified by hufeng 2008.5.5
   */
  private JBdDataObject mBdDataObject = null;

  /**
   * 公式的修改状态，用来记录日志
   * add by hufeng 2006.8.17
   */
  public boolean mIsModifiedJsgs = false;
  public boolean mIsModifiedJygs = false;
  public boolean mIsModifiedTzgs = false;
  public boolean mIsModifiedAddJsgs = false;
  public boolean mIsModifiedAddJygs = false;
  public boolean mIsModifiedAddTzgs = false;
  public boolean mIsModifiedAddZsgs = false;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JReportObjectEntity(byte[] FO,JDataObject DO) {
    //ZipEntry ze;ZipOutputStream zos;ByteArrayOutputStream os;
    FileOutputStream FOS;
    FormatObject = FO;
    if ( DO != null ) {
      DataObject = DO.GetRootXMLString();

      // 取变动数据
      mBdDataObject = ((JReportDataObject)DO).getBdDataObject();

//      try {
//        FOS = new FileOutputStream("d:\\report.xml");
//        FOS.write(DataObject.getBytes());
//        FOS.flush();
//        FOS.close();
//      } catch ( Exception e ) {
//        e.printStackTrace();
//      }
    }
//    String sdo;
//    try {
//      if ( DO != null ) {
//        ze  = new ZipEntry("DataObject");
//        os  = new ByteArrayOutputStream();
//        zos = new ZipOutputStream(os);
//        zos.putNextEntry(ze);
//        sdo   = DO.GetRootXMLString();
//        zos.write(sdo.getBytes());
//        zos.flush();
//        zos.finish();
//        DataObject = os.toByteArray();
//      }
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
  }

  /**
   * 重置修改状态
   */
  public void resetModifiedStatus(){
    mIsModifiedJsgs = false;
    mIsModifiedJygs = false;
    mIsModifiedTzgs = false;
    mIsModifiedAddJsgs = false;
    mIsModifiedAddJygs = false;
    mIsModifiedAddTzgs = false;
    mIsModifiedAddZsgs = false;
  }

  /**
   * 取变动行数据
   * @return JBdDataObject
   */
  public JBdDataObject getBdDataObject(){
      return mBdDataObject;
  }
}
