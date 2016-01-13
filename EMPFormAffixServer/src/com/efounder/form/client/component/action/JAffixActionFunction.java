package com.efounder.form.client.component.action;

import javax.swing.table.DefaultTableModel;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.form.client.component.FormPicLabel;
import com.efounder.form.client.component.frame.JImageDescBean;
import com.efounder.builder.base.data.EFDataSet;
import java.awt.Dimension;
import javax.swing.JTable;
import com.efounder.form.client.component.frame.JAffixUploadPanel;
import com.efounder.form.EFFormDataModel;
import com.efounder.eai.EAI;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import com.efounder.eai.data.JResponseObject;
import java.util.ArrayList;
import java.util.List;
import com.efounder.eai.data.JParamObject;
import com.core.xml.StubObject;
//import com.efounder.custombill.client.app.node.JBillCusBillFormatAppNode;
import com.efounder.form.client.component.pic.JPicAttachFunction;
import javax.swing.Icon;

import com.efounder.form.client.component.frame.util.JImageAffixUtil;
import com.efounder.form.client.util.JOpenAffixUtil;
import javax.swing.JToggleButton;
import java.util.Map;
import com.efounder.builder.meta.bizmodel.SYS_MDL_VAL;
import com.efounder.builder.base.data.ESPRowSet;
import java.net.URL;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.eai.service.ParameterManager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import com.efounder.form.client.util.JFormAffixVar;

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
public class JAffixActionFunction {
    public JAffixActionFunction() {
    }
//    /**
//     * 新增
//     * @param affixUploadPanel JAffixUploadPanel
//     * @param table JTable
//     */
//    public static void addAffix(JAffixUploadPanel  affixUploadPanel,JTable table) throws Exception{
//
//      int maxOrder = JAffixUploadPanel.getMaxOrder(table);
//      JResNameInputPanel resNamePanel = null;
//      if(affixUploadPanel.getStandard() == 1){
//          resNamePanel = new JResNameInputPanel(table,(maxOrder+1)+"",affixUploadPanel.getStandardAffixDataSet(), 1);
//      }else{
//          resNamePanel = new JResNameInputPanel(table,(maxOrder+1)+"",affixUploadPanel.getStandardAffixDataSet());
//      }
//      //设置默认的存储方式 fsz
//      FormModel fm=affixUploadPanel.getFormModel();
//      String mdlid=fm.getMDL_ID();
//      JParamObject PO=JParamObject.Create();
//      String ccfx=ParameterManager.getDefault().getModelConfigValue(PO, mdlid, "AFFIX_SAVETYPE", "DB");
//      resNamePanel.setDefCCFS(ccfx);
//      //end
//      JPubDialog pubDlg = new JPubDialog(EAI.EA.getMainWindow(),"输入名称",true);
//      pubDlg.addPanelToDialog(resNamePanel);
//      if(affixUploadPanel.getStandard() == 1){
//          pubDlg.setPreferredSize(new Dimension(600, 200));
//      }else{
//          pubDlg.setPreferredSize(new Dimension(400, 230));//400, 330
//      }
//      pubDlg.pack();
//      pubDlg.CenterWindow();
//      pubDlg.setVisible(true);
//
//      Object retObj = pubDlg.getRetValue();
//      if(retObj == null) return;
//
//      String[] retValue = (String[])retObj;
//
//      putAffixToModel(affixUploadPanel,retValue);
//  }
//
//  /**
//   * 把选择的附件放入模型中
//   * @param affixUploadPanel JAffixUploadPanel
//   * @param retValue String[]
//   * @throws Exception
//   */
//  public static void putAffixToModel(JAffixUploadPanel  affixUploadPanel,String[] retValue) throws Exception{
//      DefaultTableModel tableModel = (DefaultTableModel)affixUploadPanel.getTableModel();
//      List fileList  = StringFunction.convertFromStringToStringVectorByString(retValue[4],";");
//      List fileNames = StringFunction.convertFromStringToStringVectorByString(retValue[0],";");
//      int order = Integer.parseInt(retValue[1]);
//
//      for(int i = 0; i < fileList.size(); i++){
//          String path = (String)fileList.get(i);
//          String fileName = (String)fileNames.get(i);
//
//          File tmpFile = new File(path);
//          byte[] fileData = JFileByteConvertFunction.getBytesFromFile(tmpFile);
//
//          //COL_GUID  0,COL_NAME  1,COL_ODER   2,COL_PATH   3,COL_BZ   4,COL_CCFS   5,COL_SPATH   6,COL_WJLX  7
//          Vector rowData = new Vector();
//          rowData.add(JPicAttachFunction.getBillGuid(affixUploadPanel.getFormModel()));//GUID
//          rowData.add(retValue[5]);//附件标准编号
//          rowData.add(fileName);
//          rowData.add(""+(order+i));
//          rowData.add(path);//路径
//          rowData.add("Y");//新建标志
//          rowData.add(retValue[2]);//存储方式
//          String serverPath = makeServerPath(affixUploadPanel.getFormModel(),path,retValue[2],fileName);
//          rowData.add(serverPath);//服务器上路径
//          rowData.add(retValue[3]);//文件类型
//
//          tableModel.addRow(rowData);
//
//          Object[] res = getImageDescBean(fileData,fileName,retValue[3],""+(order+i));
//
//          //加入后要在面板上产生效果
//          affixUploadPanel.getShrinkImagePanel().addOneNewImage((JImageDescBean)res[0],(Icon)res[1]);
//      }
//  }

  /**
   * 产生图像描述信息
   * @param data byte[]
   * @param name String
   * @param type String
   * @return Object[]
   * @throws Exception
   */
  public static Object[] getImageDescBean(byte[] data,String name,String type,String order) throws Exception{
        int imageSize = 0;
        Icon image = null;
        int[] wh = null;

        if(type.equals(JFormAffixVar.TYPE_PIC)){//Pic
            image = JImageAffixUtil.getImageIcon(data, 300, 300);
            wh = JImageAffixUtil.getSrcImageWH(data);
        }else if(type.equals(JFormAffixVar.TYPE_DOC)){//Word
            image = ExplorerIcons.getExplorerIcon("office/(30,00).gif");
            wh = new int[]{0,0};
        }else if(type.equals(JFormAffixVar.TYPE_XLS)){//Excel
            image = ExplorerIcons.getExplorerIcon("beeload/excel.gif");
            wh = new int[]{0,0};
        }else if(type.equals(JFormAffixVar.TYPE_PDF)){//PDF
            image = ExplorerIcons.getExplorerIcon("office/(12,03).gif");
            wh = new int[]{0,0};
        }else if(type.equals(JFormAffixVar.TYPE_TXT)){//Txt
            image = ExplorerIcons.getExplorerIcon("beeload/bjs.gif");
            wh = new int[]{0,0};
        }else if(type.equals(JFormAffixVar.TYPE_CUSBILL)){
            image = ExplorerIcons.getExplorerIcon("wtable_deselect_all.gif");
            wh = new int[]{0,0};
        }else if(type.equals(JFormAffixVar.TYPE_DOCX)){//Word2007
            image = ExplorerIcons.getExplorerIcon("office/(30,00).gif");
            wh = new int[]{0,0};
        }else if(type.equals(JFormAffixVar.TYPE_XLSX)){//Excel2007
            image = ExplorerIcons.getExplorerIcon("beeload/excel.gif");
            wh = new int[]{0,0};
        }
        imageSize = data.length/1024;

        return new Object[]{new JImageDescBean(name, type, wh[0], wh[1], imageSize,order),image};
    }


//  public static String getGUID(FormModel formModel){
//      BIZMetaData bizMetaData=formModel.getBIZMetaData();
//      String GUID_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_GUID_COL_,SYS_MDL_VAL.BILL_GUID);
//
//      EFFormDataModel formDataModel = formModel.getFormDataModel();
//      EFDataSet  billDataSet = formDataModel.getBillDataSet();
//      String tableName = billDataSet.getTableName();
//      EFRowSet rowSet = billDataSet.getRowSet(0);
//      String GUID =    rowSet.getString(GUID_COL,"");
//      if(GUID == null || "".equals(GUID.trim())){
//          GUID =    rowSet.getString(tableName+"." + GUID_COL,"");
//      }
//      return GUID;
//  }
//
//  protected static String getUNIT_ID(FormModel formModel){
//      BIZMetaData bizMetaData=formModel.getBIZMetaData();
////      String  UNIT_COL = "UNIT_COL";
////      String qjcol   = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._DATE_COL_,SYS_MDL_VAL.BILL_KJQJ);
//        //单位列
//        String UNIT_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._ZZJG_ID_COL_,SYS_MDL_VAL.ZZJG_ID);
//        //票据编号
////        String billBHCol   = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_BH_COL_,SYS_MDL_VAL.BILL_BH);
//
//      EFFormDataModel formDataModel = formModel.getFormDataModel();
//      EFDataSet  billDataSet = formDataModel.getBillDataSet();
//      String tableName = billDataSet.getTableName();
//      EFRowSet rowSet = billDataSet.getRowSet(0);
//      String bizUNIT =    rowSet.getString(UNIT_COL,"");
//      if(bizUNIT == null || "".equals(bizUNIT.trim())){
//            bizUNIT =    rowSet.getString(tableName+"." + UNIT_COL,"");
//      }
//      return bizUNIT;
//  }
//
//  protected static String getYWLXBH(FormModel formModel){
//      BIZMetaData bizMetaData=formModel.getBIZMetaData();
//      String YWLX_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_KEYDCT_COL_,"F_YWLXBH");
//
//      EFFormDataModel formDataModel = formModel.getFormDataModel();
//      EFDataSet  billDataSet = formDataModel.getBillDataSet();
//      String tableName = billDataSet.getTableName();
//      EFRowSet rowSet = billDataSet.getRowSet(0);
//      String bizYWLX =    rowSet.getString(YWLX_COL,"");
//      if(bizYWLX == null || "".equals(bizYWLX.trim())){
//            bizYWLX =    rowSet.getString(tableName+"." + YWLX_COL,"");
//      }
//      return bizYWLX;
//  }
//
//
//
//  public static String makeServerPath(FormModel formModel,String selPath,String CCFS,String name){
//    String serverPath = "";
//    if("FILE".equals(CCFS)){//只有文件的存储方式才会产生放在服务器上的路径
//        String fileSuffix = selPath.substring(selPath.lastIndexOf("."));
//        serverPath = getUNIT_ID(formModel)+"/"+getYWLXBH(formModel)+"/"+name+fileSuffix;
//    }
//    return serverPath;
// }
//
//  /**
//   * 删除
//   * @param affixUploadPanel JAffixUploadPanel
//   * @param table JTable
//   */
//  public static void delAffix(JAffixUploadPanel  affixUploadPanel,JTable table) throws Exception{
//      int[] selIndex = table.getSelectedRows();
//      if(selIndex == null || selIndex.length == 0) return;
//      int isConfirmValue = JOptionPane.showConfirmDialog(EAI.EA.getMainWindow(),
//                        "确定要删除当前选择的记录吗？", "提示", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
//      if (isConfirmValue != JOptionPane.OK_OPTION) {
//          return;
//      }
//      DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
//
//      List delDataList = new ArrayList();
//      List delUIDataList = new ArrayList();
//      for(int i = 0; i < selIndex.length; i++){
//        String guid = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_GUID);//dr.getString("F_GUID");
//        String ORDER = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_ODER);
//        String CCFS = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_CCFS);
//        String isNew = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_BZ);
//        String name = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_NAME);
//
//        String flFctId = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_FLFCT);
//        String flBH = (String)tableModel.getValueAt(selIndex[i],JAffixUploadPanel.COL_FLBH);
//
//
//
//        Vector rowData = new Vector();
//        rowData.add(guid);
//        rowData.add(ORDER);
//        rowData.add(CCFS);
//        rowData.add(name);
//
//        rowData.add(flFctId);
//        rowData.add(flBH);
//
//        //界面上的缩略图要删除掉
//        delUIDataList.add(rowData);
//
//        if("Y".equals(isNew)) continue;//新建的就不用到数据库中删除了.
//
//        delDataList.add(rowData);
//      }
//
//      for(int i = selIndex.length-1; i >= 0; i--){
//          tableModel.removeRow(selIndex[i]);
//      }
//
//      //把界面上的按钮去掉:直接从界面上删除就完事
//      affixUploadPanel.removeButtons(delUIDataList);
//
//      if(delDataList.size() == 0){//没有从数据库中要删除的记录
//          if(!affixUploadPanel.isIsItemAffix()){
//                //更新显示附件数量
//                int count = JPicAttachFunction.countPicAttachNumber(affixUploadPanel.getFormImageLabel());
//                affixUploadPanel.getFormImageLabel().setNumber(count);
//          }
//          return;
//      }
//
//      //调用后台方法向数据库中删除数据
//      JParamObject PO = null;//JParamObject.Create();
//      FormModel formModel = affixUploadPanel.getFormModel();
//      if(formModel != null){
//          PO = formModel.getParamObject();
//      }else{
//          PO = JParamObject.Create();
//      }
//      PO.SetValueByParamName("MDLID",affixUploadPanel.getBizMDL());
//
//      JResponseObject res = JFormAffixUtil.deleteAffixData(PO,delDataList);
//      if(res != null){
////            JOptionPane.showMessageDialog(EAI.EA.getMainWindow(),"删除成功", "提示",JOptionPane.INFORMATION_MESSAGE);
//             //把缓存中的内容刷新(新增,删除) : 把FormDataModel中的数据也删除掉
//            EFDataSet affixDataSet = affixUploadPanel.getAffixDataSet();
//            JFormAffixUtil.delRowSetFromFormDataModel(delDataList,affixDataSet);
//            if(!affixUploadPanel.isIsItemAffix()){
//                //更新显示附件数量
//                int count = JPicAttachFunction.countPicAttachNumber(affixUploadPanel.getFormImageLabel());
//                affixUploadPanel.getFormImageLabel().setNumber(count);
//            }
//      }
//  }
//
//
//
//  /**
//   * 保存
//   * @param affixUploadPanel JAffixUploadPanel
//   * @param table JTable
//   */
//  public static void saveAffix(JAffixUploadPanel  affixUploadPanel,JTable table) throws Exception{
//      int rowCount = table.getRowCount();
//      if(rowCount == 0) return;
//
//      DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
//      List dataList = new ArrayList();
//      String path = null;
//      byte[] affixData = null;
//      for(int i = 0; i < rowCount; i++){
//          String bz = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_BZ);
//          if("Y".equals(bz)|| "V".equals(bz)){//新增的
//              //COL_GUID  0,COL_NAME  1,COL_ODER   2,COL_PATH   3,COL_BZ   4,COL_CCFS   5,COL_SPATH   6,COL_WJLX  7
//              String guid = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_GUID);
//              String name = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_NAME);
//              String order = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_ODER);
//              String CCFS = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_CCFS);
//              String SPATH = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_SPATH);
//              String WJLX = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_WJLX);
//              String BZFJBH = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_STARD);
//
//              if("Y".equals(bz)){
//                  path = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_PATH);
//                  if(path == null || "".equals(path.trim())) continue;
//                  affixData = convertPathToByte(path);
//              }else if("V".equals(bz)){
//                  BufferedImage image = (BufferedImage)affixUploadPanel.getVideoImage(order);
////                  DataBufferInt dataBufferByte   =   ((DataBufferInt)   image.getData().getDataBuffer());
//                  affixData = JImageAffixUtil.imageToByte(image);
//              }
//
//              Vector rowData = new Vector();
//              rowData.add(guid);
//              rowData.add(name);
//              rowData.add(order);
//              rowData.add(affixData);
//              rowData.add(CCFS);//存储方式: 文件(F)   数据库(D)
//              rowData.add(SPATH);
//              rowData.add(WJLX);
//              rowData.add(BZFJBH);
//
//              String itemFctId = affixUploadPanel.getItemFctId();
//              if(itemFctId == null) itemFctId = " ";
//              rowData.add(itemFctId);
//
//              String itemBH = affixUploadPanel.getItemBH();
//              if(itemBH == null) itemBH = " ";
//              rowData.add(itemBH);
//
//              dataList.add(rowData);
//          }
//      }
//
//      if(dataList.size() == 0){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "没有要保存的内容!", "提示",JOptionPane.INFORMATION_MESSAGE);
//          return ;
//      }
//
//      JParamObject PO = null;//
//      if(affixUploadPanel.formModel != null){
//          PO = affixUploadPanel.formModel.getParamObject();
//      }else{
//          PO = JParamObject.Create();
//      }
//      PO.SetValueByParamName("YWLX",affixUploadPanel.getBizYWLX());
//      PO.SetValueByParamName("MDLID",affixUploadPanel.getBizMDL());
//      PO.SetValueByParamName("UNITID",affixUploadPanel.getBizUNIT());
//      boolean b=JAffixFtpUtil.uploadAffix(affixUploadPanel.getFormModel(), dataList);
//      if(!b)return;
//      JResponseObject res = JFormAffixUtil.saveAffixData(PO,dataList);
//      if(res != null){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "保存成功!", "提示",JOptionPane.INFORMATION_MESSAGE);
//          //把是否新建标准置为E
//          for(int i = 0; i < rowCount; i++){
//              String bz = (String) tableModel.getValueAt(i, JAffixUploadPanel.COL_BZ);
//              String order = (String) tableModel.getValueAt(i, JAffixUploadPanel.COL_ODER);
//              String filePath = (String) tableModel.getValueAt(i, JAffixUploadPanel.COL_PATH);
//              if("Y".equals(bz) || "V".equals(bz)){
//                  tableModel.setValueAt("E",i, JAffixUploadPanel.COL_BZ);
//
//                  //还要把byte[] 数据写入到附件数据的Map中，否则如果再次拍照的话会覆盖以前的图片，如果按照路径生成图像data，则就有问题了。
//                  File tmpFile = new File(filePath);
//                  byte[] fileData = JFileByteConvertFunction.getBytesFromFile(tmpFile);
//                  affixUploadPanel.getAffixDataList().put(order,fileData);
//              }
//
//          }
//
//          //把缓存中的内容刷新(新增,删除)
//          EFDataSet affixDataSet = affixUploadPanel.getAffixDataSet();
//          if(affixDataSet == null) {
//              affixDataSet = EFDataSet.getInstance("SYS_MDL_AFFIX");
//              FormModel formModel = affixUploadPanel.formModel;//
//              if(formModel == null) formModel = (FormModel) (affixUploadPanel.getFormImageLabel()).getDataSetComponent();
////              EFFormDataModel formDataModel = formModel.getFormDataModel()
//              formModel.getFormDataModel().setDataSet("SYS_MDL_AFFIX",affixDataSet);
//
//              //记忆起来
//              affixUploadPanel.setAffixDataSet(affixDataSet);
//          }
//          JFormAffixUtil.addNewRowToFormDataModel(PO,dataList,affixDataSet);
//          if(affixDataSet == null) {
//              FormModel formModel = (FormModel) (affixUploadPanel.getFormImageLabel()).getDataSetComponent();
//              formModel.getFormDataModel().setDataSet("SYS_MDL_AFFIX",affixDataSet);
//          }
//          //附件个数就是当前模型中的行数
//          int count = 0;
//          if(affixDataSet.getRowCount() == 0){
//              count = rowCount;
//          }else{
////              if(affixDataSet == null) {
//                  count = JPicAttachFunction.countPicAttachNumber(
//                          affixUploadPanel.getFormImageLabel());
////              }
//          }
////          if(affixDataSet == null) {
//              affixUploadPanel.getFormImageLabel().setNumber(count);
////          }
//      }
//  }
//
//  public static boolean isUpdate(JTable table){
//      int rowCount = table.getRowCount();
//      if(rowCount == 0) return false;
//
//      DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
//      for(int i = 0; i < rowCount; i++){
//          String bz = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_BZ);
//          if("Y".equals(bz)){//只要有新增的,就说明已修改
//              return true;
//          }
//      }
//      return false;
//  }
//
//  public static byte[] convertPathToByte(String path) throws Exception{
//    File file = new File(path);
//    return JFileByteConvertFunction.getBytesFromFile(file);
// }
//
// public static void viewStandardAffix(JTable table) throws Exception{
//      //打开表单附件格式
////      int selIndex = table.getSelectedRow();
////      if(selIndex == -1) return;
////
////      DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
////      String BH = (String)tableModel.getValueAt(selIndex,0);
////      String MC = (String)tableModel.getValueAt(selIndex,1);
////
////      StubObject paramObj = new StubObject();
////      paramObj.setString("F_BH",BH);
////      paramObj.setString("F_MC",MC);
////      paramObj.setInt("UNIT_TYPE",com.efounder.formbuilder.mdl.FormModel.UNIT_TYPE_DWRPT);
////
////      JBillCusBillFormatAppNode jStubNode = new JBillCusBillFormatAppNode();
////      jStubNode.setUserObject(paramObj);
////      Context ctx = new Context(null,jStubNode);
////      jStubNode.setCaption(MC);
////      jStubNode.openObject((String)null,ctx);
//
// }

 /**
  * 查看附件
  * @param uploadPanel JAffixUploadPanel
  * @param togBtn JToggleButton
  * @throws Exception
  */
 public static void openImageView(JAffixUploadPanel uploadPanel,JToggleButton togBtn) throws Exception{
        //根据当前选择的图片,查找在已存在表格中的位置,然后获取信息
       JImageDescBean imgDesBean = (JImageDescBean)togBtn.getClientProperty("DES");
       String order = imgDesBean.getOrder();
       DefaultTableModel tableModel = uploadPanel.getTableModel();
       int findIndex = findIndex(tableModel,order);
       loadSelRowPic(uploadPanel,tableModel,findIndex);
    }

    /**
     * 查看附件
     * @param affixList Map
     * @param tableModel DefaultTableModel
     * @param fileName String
     * @throws Exception
     */
    public static void openAffixView(JParamObject PO, Map affixList, ESPRowSet rowSet) throws Exception{
        //根据当前选择的图片,查找在已存在表格中的位置,然后获取信息

       loadSelRowAffix(PO, affixList, rowSet);
    }

    public static void openAffixView(JParamObject PO,FormPicLabel picLabel, Map affixList, ESPRowSet rowSet) throws Exception{
        //根据当前选择的图片,查找在已存在表格中的位置,然后获取信息

       loadSelRowAffix(PO, picLabel, affixList, rowSet);
    }



    public static int findIndex(DefaultTableModel tableModel,String order){
        int findIndex = 0;
       for(int i = 0; i < tableModel.getRowCount(); i++){
           String itemOrder = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_ODER);
           if(order.equals(itemOrder)){
               findIndex = i;
               break;
           }
       }

       return findIndex;
    }

    public static int findIndex(EFDataSet dataSet,String order){
      int findIndex = 0;
      for(int i = 0; i < dataSet.getRowCount(); i++){
        EFRowSet rowSet = (EFRowSet)dataSet.getRowSet(i);
        String itemOrder = rowSet.getString("F_ORDE","");
        if(order.equals(itemOrder)){
           findIndex = i;
           break;
        }
      }
      return findIndex;
    }


    protected static void loadSelRowPic(JAffixUploadPanel uploadPanel,DefaultTableModel tableModel,int selRow) throws Exception{
        String isNew = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_BZ);
        String fileType = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_WJLX);
        String name = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_NAME);
        String order = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_ODER);
        String fjBzbh = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_STARD);

        Object res = null;
        if ("Y".equals(isNew)){//新增时从路径中取文件路径
            res = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_PATH);
        }
        else if ("V".equals(isNew)){//新增时从视频捕捉的图像
//            res = (String)tableModel.getValueAt(selRow,JAffixUploadPanel.COL_PATH);

            res = uploadPanel.getVideoImage(order);
        }
        else{//
            Map affixDataList = uploadPanel.getAffixDataList();
            byte[] blobData = (byte[])affixDataList.get(order);
            if(blobData == null){
                //保存后,把COL_BZ修改了,但是并没有从数据库load,affixDataList里面可能没有数据,这是按照路径来获取资源
                res = (String) tableModel.getValueAt(selRow,
                        JAffixUploadPanel.COL_PATH);
                if (res == null || res.equals("")) {
                    String spath = (String) tableModel.getValueAt(selRow,
                            JAffixUploadPanel.COL_SPATH);
                    if (!"".equals(spath) && spath != null) {
                        JParamObject PO = null;
//                        FormModel formModel = uploadPanel.getFormModel();
//                        if(formModel != null){
//                            PO = formModel.getParamObject();
//                        }else{
//                            PO = JParamObject.Create();
//                        }
//                        JResponseObject rs = JFormAffixUtil.getAffixData(
//                                PO, spath);
//
//                        blobData = (byte[])rs.getResponseObject();
                    }
                }
            }

            if(JFormAffixVar.TYPE_CUSBILL.equals(fileType)){
                res = new Object[]{fjBzbh,blobData};
            }else{
                res = blobData;
            }

        }

        JOpenAffixUtil.openAffixData(fileType,res,name,uploadPanel,order);
   }

   protected static void loadSelRowAffix(JParamObject PO, Map affixList, ESPRowSet rowSet) throws
           Exception {

       String name = (String)rowSet.getString("F_NAME","");
       String fileType = rowSet.getString("F_WJLX", "");
       String path = (String) rowSet.getString("F_PATH", "");
       String order = rowSet.getString("F_ORDE", "");
       String fjBzbh = rowSet.getString("F_FJBZBH", "");
       Object res = null;
       if(!"".equals(path) && path != null){
//           JResponseObject rs = JFormAffixUtil.getAffixData(PO,path);
//
//           res = rs.getResponseObject();
       }
       else{
           Map affixDataList = affixList;
           byte[] blobData = (byte[]) affixDataList.get(order);
           if(JFormAffixVar.TYPE_CUSBILL.equals(fileType)){
               res = new Object[]{fjBzbh,blobData};
           }else{
               res = blobData;
           }
       }


       JOpenAffixUtil.openAffixData(fileType, res, name,order);
   }

   protected static void loadSelRowAffix(JParamObject PO, FormPicLabel picLabel, Map affixList, ESPRowSet rowSet) throws
           Exception {

       String name = (String)rowSet.getString("F_NAME","");
       String fileType = rowSet.getString("F_WJLX", "");
       String path = (String) rowSet.getString("F_PATH", "");
       String order = rowSet.getString("F_ORDE", "");
       String fjBzbh = rowSet.getString("F_FJBZBH", "");
       Object res = null;
       if(!"".equals(path) && path != null){
//           JResponseObject rs = JFormAffixUtil.getAffixData(PO,path);
//
//           res = rs.getResponseObject();
       }
       else{
           Map affixDataList = affixList;
//           res = (byte[]) affixDataList.get(order);
           byte[] blobData = (byte[]) affixDataList.get(order);
           if(JFormAffixVar.TYPE_CUSBILL.equals(fileType)){
               res = new Object[]{fjBzbh,blobData};
           }else{
               res = blobData;
           }

       }
//       JOpenAffixUtil.openAffixData(fileType, res, name,order, picLabel);
   }


//   public static byte[] imageToByte(Icon ima) throws Exception{
//        BufferedImage bu = new
//                           BufferedImage(ima.getIconWidth(),
//                                         ima.getIconHeight(),
//                                         BufferedImage.TYPE_INT_RGB);
//        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
//        ImageIO.write(bu, "GIF", imageStream);
//        byte[] tagInfo = imageStream.toByteArray();
//        return tagInfo;
//    }
//
//    /**
//     * 取最大序号
//     * @param ds EFDataSet
//     * @return String
//     */
//    public static String getOrder(EFDataSet ds){
//        if(ds == null || ds.getRowCount() ==0)
//            return "0";
//        String order = "0";
//        for(int i=0; ds!=null && i<ds.getRowCount(); i++){
//            String od = ds.getRowSet(i).getString("F_ORDE","1");
//            if(Integer.valueOf(od)>Integer.valueOf(order))
//                order = String.valueOf(od);
//        }
//
//        return String.valueOf(Integer.valueOf(order)+1);
//    }


}
