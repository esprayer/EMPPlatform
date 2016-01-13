package jreport.foundation.classes.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.jdom.Element;
import java.util.List;
import java.util.Iterator;

import jreport.foundation.classes.JReportDataObject;

/**
 * <p>Title: �䶯����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: pansoft</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JBdDataObject implements Serializable {
    public JBdDataObject() {
        super();
    }

    private String BBZD_BH     = null;
    private String BBZD_DATE   = null;
    private String ADD_TYPE    = null;
    private String ADD_BH      = null;

    private int    mColCount   = 0;  // �����п�

    private ArrayList  mColNames     = null; // ������
    private HashMap    mAddDataMap   = null; // ��λ�䶯����
    private HashMap    mAddTzDataMap   = null; // ��λ�䶯��������
    private HashMap    mSysDataMap   = null; // ϵͳ��������


    /**
     * ����ϵͳ����䶯����
     * @param rs ResultSet
     * @throws Exception
     */
    public int setSysBdData(ResultSet rs,String bdhOrde) throws Exception{
        if(mSysDataMap == null){
            mSysDataMap = new HashMap();
        }
        // ��ʼ����
        if(mColNames == null){
            initColNames(rs);
        }
        int i;
        String value,datas[];
        ArrayList rowList;
        ArrayList dataList = new ArrayList();
        while(rs.next()){
            rowList = new ArrayList();
            for(i = 1; i<=mColCount; i++){
                value = rs.getString(i);
                if (value != null){
                    value = value.trim();
                }else{
                    value = "";
                }
                // �ռ�¼����
                if(value.equals("") || value.equals("0")){
                    continue;
                }
                datas = new String[2];
                datas[0] = String.valueOf(i);
                datas[1] = value;
                rowList.add(datas);
            }
            dataList.add(rowList);
        }
        if(dataList.size() > 0){
            mSysDataMap.put(bdhOrde,dataList);
        }
        return dataList.size();
    }

    /**
     * ��ʼ��������
     * @param rs ResultSet
     * @throws Exception
     */
    private void initColNames(ResultSet rs) throws Exception{
        String colName;
        mColNames = new ArrayList();
        ResultSetMetaData rsmd = rs.getMetaData();
        mColCount = rsmd.getColumnCount();
        for (int i = 1; i <= mColCount; i++) {
            colName = rsmd.getColumnName(i);
            mColNames.add(colName);
        }
    }

    /**
     * ���õ�λ����䶯����
     * @param rs ResultSet
     * @throws Exception
     */
    public int setAddBdData(ResultSet rs,String bdhOrde) throws Exception{
        if(mAddDataMap == null){
            mAddDataMap = new HashMap();
        }
        // ��ʼ����
        if(mColNames == null){
            initColNames(rs);
        }
        int i;
        String value,datas[];
        ArrayList rowList;
        ArrayList dataList = new ArrayList();
        while(rs.next()){
            rowList = new ArrayList();
            for(i = 1; i<=mColCount; i++){
                value = rs.getString(i);
                if (value != null){
                    value = value.trim();
                }else{
                    value = "";
                }
                // �ռ�¼���������ټ�¼��
                if(value.equals("") || value.equals("0")){
                    continue;
                }
                datas = new String[2];
                datas[0] = String.valueOf(i);
                datas[1] = value;
                rowList.add(datas);
            }
            dataList.add(rowList);
        }
        if(dataList.size() > 0){
            mAddDataMap.put(bdhOrde,dataList);
        }
        return dataList.size();
    }

    /**
   * ���õ�λ����䶯����
   * @param rs ResultSet
   * @throws Exception
   */
  public int setAddBdTzData(ResultSet rs,String bdhOrde) throws Exception{
      if(mAddTzDataMap == null){
          mAddTzDataMap = new HashMap();
      }
      // ��ʼ����
      if(mColNames == null){
          initColNames(rs);
      }
      int i;
      String value,datas[];
      ArrayList rowList;
      ArrayList dataList = new ArrayList();
      while(rs.next()){
          rowList = new ArrayList();
          for(i = 1; i<=mColCount; i++){
              value = rs.getString(i);
              if (value != null){
                  value = value.trim();
              }else{
                  value = "";
              }
              // �ռ�¼���������ټ�¼��
              if(value.equals("") || value.equals("0")){
                  continue;
              }
              datas = new String[2];
              datas[0] = String.valueOf(i);
              datas[1] = value;
              rowList.add(datas);
          }
          dataList.add(rowList);
      }
      if(dataList.size() > 0){
          mAddTzDataMap.put(bdhOrde,dataList);
      }
      return dataList.size();
  }


    /**
     * ����ϵͳ����䶯����
     * @param rs ResultSet
     * @throws Exception
     */
    public void getSysBdData(JReportDataObject RDO,Element bdElement){
        if(bdElement == null || mSysDataMap == null ){
            return;
        }
        int i = 0;
        String[] datas;
        ArrayList dataList,rowList,xhList;
        Element node,bdhNode,dyzdNode;
        String xh,bdhOrde,ordeName,isNewName,isNew;
        List nodelist = RDO.BeginEnumerate(bdElement);
        ordeName = bdElement.getAttributeValue("BDH_ORDE","a");
        isNewName = bdElement.getAttributeValue("BDH_ISNEW","0");
        while (nodelist != null) {
            bdhNode = RDO.Enumerate(nodelist, i++);
            if (bdhNode == null) {
                break;
            }
            // ֻ�����±䶯��
            isNew  = bdhNode.getAttributeValue(isNewName,"0");
            if(!isNew.equals("1")){
                continue;
            }
            bdhOrde  = bdhNode.getAttributeValue(ordeName);
            if(bdhOrde == null || bdhOrde.trim().equals("")){
                System.out.println("Sys BdhOrde is null!");
                continue;
            }
            dyzdNode = getDyzdElement(RDO,bdhNode,"BDDyzd");
            dataList = (ArrayList)mSysDataMap.get(bdhOrde);
            if(dataList == null || dataList.size() <= 0){
                continue;
            }
            for(Iterator it = dataList.iterator();it.hasNext();){
                rowList = (ArrayList)it.next();
                xhList  = new ArrayList();
                node = RDO.AddChildElement(dyzdNode,"R");
                for(Iterator rowIt = rowList.iterator();rowIt.hasNext();){
                    datas = (String[])rowIt.next();
                    RDO.SetElementValue(node, "c" + datas[0], datas[1]);
                    xhList.add(datas[0]);
                }
                for(int j=1; j<=mColCount; j++){
                    xh = String.valueOf(j);
                    if(!xhList.contains(xh)){
                        RDO.SetElementValue(node, "c" + j, "");
                    }
                }
            }
        }
    }

    /**
     * ���õ�λ����䶯����
     * @param rs ResultSet
     * @throws Exception
     */
    public void getAddBdData(JReportDataObject RDO,Element bdElement){
        if(bdElement == null || mAddDataMap == null ){
            return;
        }
        int i = 0;
        String[] datas;
        ArrayList dataList,rowList,xhList;
        Element node,bdhNode,dyzdNode;
        String xh,bdhOrde,ordeName,isNewName,isNew;
        List nodelist = RDO.BeginEnumerate(bdElement);
        ordeName = bdElement.getAttributeValue("BDH_ORDE","a");
        isNewName = bdElement.getAttributeValue("BDH_ISNEW","0");
        while (nodelist != null) {
            bdhNode = RDO.Enumerate(nodelist, i++);
            if (bdhNode == null) {
                break;
            }
            // ֻ�����±䶯��
            isNew  = bdhNode.getAttributeValue(isNewName,"0");
            if(!isNew.equals("1")){
                continue;
            }
            bdhOrde  = bdhNode.getAttributeValue(ordeName);
            if(bdhOrde == null || bdhOrde.trim().equals("")){
                System.out.println("Add BdhOrde is null!");
                continue;
            }
            dyzdNode = getDyzdElement(RDO,bdhNode,"BDAddDyzd");
            dataList = (ArrayList)mAddDataMap.get(bdhOrde);
            if(dataList == null || dataList.size() <= 0){
                continue;
            }
            for(Iterator it = dataList.iterator();it.hasNext();){
                rowList = (ArrayList)it.next();
                xhList  = new ArrayList();
                node = RDO.AddChildElement(dyzdNode,"R");
                for(Iterator rowIt = rowList.iterator();rowIt.hasNext();){
                    datas = (String[])rowIt.next();
                    RDO.SetElementValue(node, "c" + datas[0], datas[1]);
                    xhList.add(datas[0]);
                }
                for(int j=1; j<=mColCount; j++){
                    xh = String.valueOf(j);
                    if(!xhList.contains(xh)){
                        RDO.SetElementValue(node, "c" + j, "");
                    }
                }
            }
        }
    }

    /**
      * ���õ�λ����䶯��������
      * @param rs ResultSet
      * @throws Exception
      */
     public void getAddBdTzData(JReportDataObject RDO,Element bdElement){
         if(bdElement == null || mAddTzDataMap == null ){
             return;
         }
         int i = 0;
         String[] datas;
         ArrayList dataList,rowList,xhList;
         Element node,bdhNode,dyzdNode;
         String xh,bdhOrde,ordeName,isNewName,isNew;
         List nodelist = RDO.BeginEnumerate(bdElement);
         ordeName = bdElement.getAttributeValue("BDH_ORDE","a");
         isNewName = bdElement.getAttributeValue("BDH_ISNEW","0");
         while (nodelist != null) {
             bdhNode = RDO.Enumerate(nodelist, i++);
             if (bdhNode == null) {
                 break;
             }
             // ֻ�����±䶯��
             isNew  = bdhNode.getAttributeValue(isNewName,"0");
             if(!isNew.equals("1")){
                 continue;
             }
             bdhOrde  = bdhNode.getAttributeValue(ordeName);
             if(bdhOrde == null || bdhOrde.trim().equals("")){
                 System.out.println("Add BdhOrde is null!");
                 continue;
             }
             dyzdNode = getDyzdElement(RDO,bdhNode,"BDTZAddDyzd");
             dataList = (ArrayList)mAddTzDataMap.get(bdhOrde);
             if(dataList == null || dataList.size() <= 0){
                 continue;
             }
             for(Iterator it = dataList.iterator();it.hasNext();){
                 rowList = (ArrayList)it.next();
                 xhList  = new ArrayList();
                 node = RDO.AddChildElement(dyzdNode,"R");
                 for(Iterator rowIt = rowList.iterator();rowIt.hasNext();){
                     datas = (String[])rowIt.next();
                     RDO.SetElementValue(node, "c" + datas[0], datas[1]);
                     xhList.add(datas[0]);
                 }
                 for(int j=1; j<=mColCount; j++){
                     xh = String.valueOf(j);
                     if(!xhList.contains(xh)){
                         RDO.SetElementValue(node, "c" + j, "");
                     }
                 }
             }
         }
    }

    /**
     * ����DYZD�ڵ�
     * @param RDO JReportDataObject
     * @param bdElement Element
     */
    private Element getDyzdElement(JReportDataObject RDO,Element bdhNode,String key){
        String colName;
        Element node = RDO.AddChildElement(bdhNode,key);
        for (int i = 1; i <= mColCount; i++) {
            colName = (String)mColNames.get(i-1);
            RDO.SetElementValue(node, colName, "c" + i);
        }
        return node;
    }
}
