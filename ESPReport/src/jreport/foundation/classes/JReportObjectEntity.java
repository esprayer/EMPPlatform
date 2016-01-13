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
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JReportObjectEntity implements java.io.Serializable {
  public String BBQX = null;
  public String ADDQX = null;
  public Object AddObject = null;    // ���Ӷ���
  public byte[] FormatObject = null; // ������ű���ĸ�ʽ����˶�����ѹ������
//  public byte[] DataObject   = null; // ������ű�������ݶ���,��һ��XML�ַ���
  public String DataObject   = null;   // ������ű�������ݶ���,��һ��XML�ַ���

  /**
   * �䶯���ݶ���
   * ��Ϊ���ڱ䶯����XML�򿪺���
   * ���Ը�Ϊ�ں�̨�γ��������������
   * ��ǰ̨���γ�XML����ķ�ʽ������
   * modified by hufeng 2008.5.5
   */
  private JBdDataObject mBdDataObject = null;

  /**
   * ��ʽ���޸�״̬��������¼��־
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
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JReportObjectEntity(byte[] FO,JDataObject DO) {
    //ZipEntry ze;ZipOutputStream zos;ByteArrayOutputStream os;
    FileOutputStream FOS;
    FormatObject = FO;
    if ( DO != null ) {
      DataObject = DO.GetRootXMLString();

      // ȡ�䶯����
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
   * �����޸�״̬
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
   * ȡ�䶯������
   * @return JBdDataObject
   */
  public JBdDataObject getBdDataObject(){
      return mBdDataObject;
  }
}
