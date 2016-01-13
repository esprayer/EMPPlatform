package com.efounder.form.client.util;

import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;

import java.util.List;
import org.openide.ErrorManager;
import javax.swing.JComboBox;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.EFDataSet;
import java.util.Vector;
import com.efounder.builder.base.data.EFRowSet;

import java.util.Map;
import java.util.TreeMap;
import com.core.xml.PackageStub;
import java.util.HashMap;
import javax.swing.JOptionPane;

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
public class JFormAffixUtil {
    public JFormAffixUtil() {
    }



    public static EFDataSet loadStandardAffixList(JParamObject PO){
         JResponseObject RO = null;
          try {
            RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "loadStandardAffixList", PO);
            if(RO.ErrorCode == -1){
                 JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载标准附件数据时出错，原因可能是"+RO.ResponseObject);
                 return null;
            }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载标准附件数据时出错，原因可能是"+e.getMessage());
//              ErrorManager.getDefault().notify(e);
              e.printStackTrace();
              return null;
          }
          return (EFDataSet)RO.ResponseObject;
    }

    public static EFDataSet loadAffixDataSet(String mdl_id,String guid){
        JResponseObject RO = null;
        JParamObject PO = null;
        try {
            PO = JParamObject.Create();
            PO.SetValueByParamName("MDL_ID",mdl_id);
            PO.SetValueByParamName("GUID",guid);
            RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "loadAffixDataSet", PO);
            if(RO.ErrorCode == -1){
                 JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+RO.ResponseObject);
                 return null;
            }
            return (EFDataSet)RO.ResponseObject;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+e.getMessage());
            e.printStackTrace();
           return null;
        }
    }



    /**
    * load(单个附件的数据)
    * @param PO JParamObject
    * @param dataList List
    * @return JResponseObject
    */
   public static JResponseObject loadAffixData(JParamObject PO){
           JResponseObject RO = null;
          try {
            RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "loadAffixData", PO);
            if(RO.ErrorCode == -1){
                 JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+RO.ResponseObject);
                 return null;
            }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+e.getMessage());
//              ErrorManager.getDefault().notify(e);
              e.printStackTrace();
              return null;
          }
          return RO;
   }

   /**
    * 加载同一个GUID的所有附件数据
    *
    * @param PO JParamObject
    * @return JResponseObject
    */
//   public static Map loadAffixDataList(FormModel model,JParamObject PO){
//         JResponseObject RO = null;
//         Map map=null;
//          try {
//            RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "loadAffixDataList", PO);
//            if(RO.ErrorCode == -1){
//                 JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+RO.ResponseObject);
//                 return null;
//            }
//
//            if(RO!=null)map=(Map)RO.ResponseObject;
//            if(map == null) map=new TreeMap();
//            //add by fsz 处理FTP
//            JAffixFtpUtil.downloadAffix(model, map);
//          } catch (Exception e) {
//              JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+e.getMessage());
////              ErrorManager.getDefault().notify(e);
//              e.printStackTrace();
//              return null;
//          }
//          return map;
//   }






   /**
    * save
    * @param PO JParamObject
    * @param dataList List
    * @return JResponseObject
    */
   public static JResponseObject saveAffixData(JParamObject PO,List dataList){
           JResponseObject RO = null;
          try {
            RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "saveAffixData", PO,dataList);
            if(RO.ErrorCode == -1){
                  JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "保存附件数据时出错，原因可能是"+RO.ResponseObject);
                  return null;
            }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "保存附件数据时出错，原因可能是"+e.getMessage());
//              ErrorManager.getDefault().notify(e);
              e.printStackTrace();
              return null;
          }
          return RO;
   }

   public static JResponseObject getAffixData(JParamObject PO,String path){
           JResponseObject RO = null;
          try {
            RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "getAffixData", PO,path);
            if(RO.ErrorCode == -1){
                  JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "获取附件数据时出错，原因可能是"+RO.ResponseObject);
                  return null;
            }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "获取附件数据时出错，原因可能是"+e.getMessage());
//              ErrorManager.getDefault().notify(e);
              e.printStackTrace();
              return null;
          }
          return RO;
   }


   /**
    * ɾ���
    * @param PO JParamObject
    * @param dataList List
    * @return JResponseObject
    */
   public static JResponseObject deleteAffixData(JParamObject PO,List dataList){
           JResponseObject RO = null;
          try {
              RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "deleteAffixData", PO,dataList);
              if(RO.ErrorCode == -1){
                  JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "删除附件数据时出错，原因可能是"+RO.ResponseObject);
                  return null;
              }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "删除附件数据时出错，原因可能是"+e.getMessage());
//              ErrorManager.getDefault().notify(e);
              e.printStackTrace();
              return null;
          }
          return RO;
   }

   /**
    * ��������ӵ�DataSet��
    * @param dataList List
    * @param affixDataSet EFDataSet
    */
   public static void addNewRowToFormDataModel(JParamObject PO,List dataList,EFDataSet affixDataSet){
       if(dataList == null || dataList.size() == 0) return;

       String user = PO.GetValueByEnvName("UserCaption");

       for(int i = 0; i < dataList.size(); i++){
           Vector rowdata = (Vector)dataList.get(i);
           String guid = (String)rowdata.get(0);
           String name = (String)rowdata.get(1);
           String oder = (String)rowdata.get(2);
           String CCLX = (String)rowdata.get(4);//�洢����: �ļ�(F)   ��ݿ�(D)
           String serverPath = (String)rowdata.get(5);//�ļ�
           String wjlx = (String)rowdata.get(6);//PIC ,PDF
            String bzfjbh = (String)rowdata.get(7);
           String flfct = (String)rowdata.get(8);
           String flbh = (String)rowdata.get(9);


          EFRowSet newRowSet = EFRowSet.getInstance();
          newRowSet.setValue("F_GUID",guid);
          newRowSet.setValue("F_FJBZBH",bzfjbh);
          newRowSet.setValue("F_NAME",name);
          newRowSet.setValue("F_ORDE",oder);

          newRowSet.setValue("F_YWLX",guid);
          newRowSet.setValue("F_CCLX",CCLX);
          newRowSet.setValue("F_PATH",serverPath);
          newRowSet.setValue("F_WJLX",wjlx);
          newRowSet.setValue("F_FLFCT",flfct);
          newRowSet.setValue("F_FLBH",flbh);

          affixDataSet.addRowSet(newRowSet);
       }
   }

   /**
    * ��DataSet��ɾ�����
    * @param dataList List
    * @param affixDataSet EFDataSet
    */
   public static void delRowSetFromFormDataModel(List dataList,EFDataSet affixDataSet){
       if(dataList == null || dataList.size() == 0) return;
       if(affixDataSet == null || affixDataSet.getRowCount() == 0) return;

        for(int i = 0; i < dataList.size(); i++){
           Vector rowdata = (Vector) dataList.get(i);
           String pguid = (String) rowdata.get(0);
           String poder = (String) rowdata.get(1);

           for(int j = 0; j < affixDataSet.getRowCount(); j++){
              EFRowSet efRowSet = affixDataSet.getRowSet(j);
              String guid = efRowSet.getString("F_GUID","");
              String oder = efRowSet.getString("F_ORDE","");
              if(pguid.equals(guid) && poder.equals(oder)){//ɾ��
//                  affixDataSet.removeRowSet(efRowSet);
                  break;
              }
           }
       }
   }

   /**
    * 获取附件类型Map
    * @return Map
    */
   public static Map getFileSuffixMap(){
       Map suffixMap = new HashMap();
       List affixTypeList = PackageStub.getContentVector("AffixType");
       if(affixTypeList != null){
           for(int i = 0; i < affixTypeList.size(); i++){
               StubObject stub = (StubObject)affixTypeList.get(i);
               String suffix = stub.getString("fileSuffix","");
               String id     = stub.getString("id","");
//               List ls = StringParamUtil.getListFromStr(suffix,",");
//               suffixMap.put(id,ls);
           }
       }
       return suffixMap;
   }

   /**
    * 根据文件后缀，判断文件类型
    * @param sufixMap Map
    * @param fileSffix String
    * @return String
    */
   public static String getFileType(Map sufixMap,String fileSffix){
       Object[] keys = sufixMap.keySet().toArray();
       for(int i = 0; i < keys.length; i++){
           List ls = (List)sufixMap.get(keys[i]);
           if(ls.contains(fileSffix)){
               return (String)keys[i];
           }
       }
       return "-1";
   }

   /**
    * 根据GUID和ORDER加载附件
    * @param conn JConnection
    * @param PO JParamObject
    * @return Object
    * @throws Exception
    */
   public static List loadSomeAffixBlobData(JParamObject PO){
       JResponseObject RO = null;
       try {
           RO = (JResponseObject) EAI.DAL.IOM("FormAffixService", "loadSomeAffixBlobData", PO);
           if(RO.ErrorCode == -1){
               JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+RO.ResponseObject);
               return null;
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "加载附件数据时出错，原因可能是"+e.getMessage());
           e.printStackTrace();
           return null;
       }
       return (List)RO.ResponseObject;
   }




}
