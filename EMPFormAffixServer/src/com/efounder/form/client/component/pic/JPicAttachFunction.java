package com.efounder.form.client.component.pic;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import com.efounder.eai.EAI;
import com.efounder.builder.meta.bizmodel.SYS_MDL_CTN;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.client.component.FormPicLabel;
import com.efounder.form.client.component.frame.JAffixManagerWorkSpace;
import com.efounder.builder.base.data.EFDataSet;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.efounder.builder.base.data.EFRowSet;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import org.openide.ErrorManager;
import com.core.xml.StubObject;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.awt.Graphics2D;
import java.util.Iterator;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import com.efounder.builder.meta.bizmodel.SYS_MDL_CTN;
import java.awt.Frame;
import com.efounder.eai.ide.ExplorerIcons;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import com.efounder.eai.data.JParamObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JPicAttachFunction {
  public JPicAttachFunction() {
  }
//  public static void viewAffixWorkSpaceByModel(FormPicLabel picLabel,JFrame parnetComp) throws Exception{
//      FormModel formModel = (FormModel) picLabel.getDataSetComponent();
//      if (!checkEnableOpen(formModel)) {
//          return;
//      }
//
//      JAffixUploadPanel   uploadAffixPanel = new JAffixUploadPanel();
//      EFFormDataModel formDataModel = formModel.getFormDataModel();
//      EFDataSet hasAffixDS      = (EFDataSet)formDataModel.getDataSet("SYS_MDL_AFFIX");
//      if(hasAffixDS == null || hasAffixDS.getRowCount() == 0){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(),"表单不存在附件,不能查看 !", "提示",JOptionPane.INFORMATION_MESSAGE);
//          return;
//      }
//
//      uploadAffixPanel.setFormModel(formModel);
//      uploadAffixPanel.fillData(hasAffixDS,picLabel,false,null,null);
//      String[] keyParams = JImageAffixUtil.getKeyParams(formModel);
//      uploadAffixPanel.setBizMDL(formModel.getBizMDLID());
//      uploadAffixPanel.setBizUNIT(formModel.getDataObjectBIZ_UNIT());
//      uploadAffixPanel.setBizYWLX(keyParams[0]);
//      uploadAffixPanel.setBizGUID(keyParams[1]);
//      uploadAffixPanel.makeAffixDataList();
//
//      String initOrder = (String)hasAffixDS.getRowSet(0).getDataMap().get("F_ORDE");
//
//      PreViewImagePanel imagePanel = new PreViewImagePanel(uploadAffixPanel,initOrder);
//
//
//      //获取第一行数据
//     DefaultTableModel tableModel = uploadAffixPanel.getTableModel();
//     if(tableModel.getRowCount() == 0 ) return;
//     String imageName = (String)tableModel.getValueAt(0,JAffixUploadPanel.COL_NAME);
//     String order = (String)tableModel.getValueAt(0,JAffixUploadPanel.COL_ODER);
//
//     Map affixDataList = uploadAffixPanel.getAffixDataList();
//     byte[] data = (byte[])affixDataList.get(order);
//
//     imagePanel.callImagePreview(tableModel,uploadAffixPanel.getAffixDataList(),data,imageName);
//     JPubDialog pubDialog = null;
//      if(parnetComp == null){
//          pubDialog = new JPubDialog(EAI.EA.getMainWindow(),"附件预览",true);//搞成模态的，在对话框的基础上打开
//      }else{
//          pubDialog = new JPubDialog(parnetComp,"附件预览",true);//搞成模态的，在对话框的基础上打开
//      }
//
//      pubDialog.addPanelToDialog(imagePanel);
//      pubDialog.setPreferredSize(new Dimension(800,550));
//      //Validate frames that have preset sizes
//      //Pack frames that have useful preferred size info, e.g. from their layout
//      pubDialog.pack();
//      pubDialog.CenterWindow();
//      //Center the window
//      pubDialog.setVisible(true);
//
//
//  }
//  public static void viewAffixWorkspace(FormPicLabel picLabel) throws Exception{
//      viewAffixWorkspace(picLabel,false);
//  }
//
//  public static void viewAffixWorkspace(FormPicLabel picLabel,boolean isExtendScreen) throws Exception{
//      FormModel formModel = (FormModel) picLabel.getDataSetComponent();
//      if (!checkEnableOpen(formModel)) {
//          return;
//      }
//
//      JAffixUploadPanel   uploadAffixPanel = new JAffixUploadPanel();
//      EFFormDataModel formDataModel = formModel.getFormDataModel();
//      EFDataSet hasAffixDS      = (EFDataSet)formDataModel.getDataSet("SYS_MDL_AFFIX");
//      if(hasAffixDS == null || hasAffixDS.getRowCount() == 0){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(),"表单不存在附件,不能查看 !", "提示",JOptionPane.INFORMATION_MESSAGE);
//          return;
//      }
//
//      uploadAffixPanel.setFormModel(formModel);
//      uploadAffixPanel.fillData(hasAffixDS,picLabel,false,null,null);
//      String[] keyParams = JImageAffixUtil.getKeyParams(formModel);
//      uploadAffixPanel.setBizMDL(formModel.getBizMDLID());
//      uploadAffixPanel.setBizUNIT(formModel.getDataObjectBIZ_UNIT());
//      uploadAffixPanel.setBizYWLX(keyParams[0]);
//      uploadAffixPanel.setBizGUID(keyParams[1]);
//      uploadAffixPanel.makeAffixDataList();
//
//      String initOrder = (String)hasAffixDS.getRowSet(0).getDataMap().get("F_ORDE");
//
//      PreViewImagePanel imagePanel = new PreViewImagePanel(uploadAffixPanel,initOrder);
//      JPICFrame  frame = new JPICFrame(imagePanel,"附件预览");
//      frame.setFormPicLabelComp(picLabel);
//      frame.setPreferredSize(new Dimension(700,500));
//      ImageIcon icon = (ImageIcon)ExplorerIcons.getExplorerIcon("oicons/image.png");//oicons/iterator.png
//      frame.setIconImage(icon.getImage());
//
//      imagePanel.setParentFrame(frame);
//      frame.setCancelText("关闭");
//      frame.setOKVisiable(false);
//
//      frame.pack();
//
//      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//      Dimension frameSize = frame.getSize();
//      if (frameSize.height > screenSize.height) {
//          frameSize.height = screenSize.height;
//      }
//      if (frameSize.width > screenSize.width) {
//          frameSize.width = screenSize.width;
//      }
//      if(isExtendScreen){
//          frame.setLocation(screenSize.width+(screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
//      }else{
//          frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
//      }
////
//
//
//      picLabel.setAffixWindow(frame);
//      frame.setVisible(true);
//      //窗口最大化
//      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//      //获取第一行数据
//      DefaultTableModel tableModel = uploadAffixPanel.getTableModel();
//      if(tableModel.getRowCount() == 0 ) return;
//      String imageName = (String)tableModel.getValueAt(0,JAffixUploadPanel.COL_NAME);
//      String order = (String)tableModel.getValueAt(0,JAffixUploadPanel.COL_ODER);
//
//      Map affixDataList = uploadAffixPanel.getAffixDataList();
//      byte[] data = (byte[])affixDataList.get(order);
//
//      imagePanel.callImagePreview(tableModel,uploadAffixPanel.getAffixDataList(),data,imageName);
//
//  }
//
//  /**
//   * 打开分录附件管理
//   */
//  public static void openItemAffixWorkspace(FormModel formModel,String itemFctId,String itemBH){
//      //先判断如果表单不存在GUID,则不允许打开附件
//      try{
//          if (!checkEnableOpen(formModel)) {
//              return;
//          }
//
//          String djbh = formModel.getDataObjectBIZ_DJBH();
//          String title = "附件管理";
//          if(djbh != null && !djbh.equals("")){
//              title += "-"+djbh +"-" + itemBH;
//          }
//
//          JAffixManagerWorkSpace panel = new JAffixManagerWorkSpace();
//          panel.makeData(formModel, null,true,itemFctId,itemBH);
//          EnterpriseExplorer.ContentView.openChildWindow(panel, title, title, null);
//      }
//      catch(Exception e){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "打开附件管理时出错，原因可能是"+e.getMessage());
////          ErrorManager.getDefault().notify(e);
//      }
//
//  }
//
//  /**
//   * 打开分录附件管理
//   */
//  public static void openItemAffixWorkspace(FormModel formModel,FormPicLabel fpl, String itemFctId,String itemBH){
//      //先判断如果表单不存在GUID,则不允许打开附件
//      try{
//          if (!checkEnableOpen(formModel)) {
//              return;
//          }
//
//          String djbh = formModel.getDataObjectBIZ_DJBH();
//          String title = "附件管理";
//          if(djbh != null && !djbh.equals("")){
//              title += "-"+djbh +"-" + itemBH;
//          }
//
//          JAffixManagerWorkSpace panel = new JAffixManagerWorkSpace();
//          panel.makeData(formModel, fpl,true,itemFctId,itemBH);
//          EnterpriseExplorer.ContentView.openChildWindow(panel, title, title, null);
//      }
//      catch(Exception e){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "打开附件管理时出错，原因可能是"+e.getMessage());
////          ErrorManager.getDefault().notify(e);
//      }
//
//  }

  /**
   *
   * @param picLabel FormPicLabel
   * @return JAffixManagerWorkSpace
   */
  public static JAffixManagerWorkSpace createWorkspace(FormPicLabel picLabel){
      JAffixManagerWorkSpace panel = null;
      try{
//          FormModel formModel = (FormModel) picLabel.getDataSetComponent();
//          if (!checkEnableOpen(formModel)) {
//              return null;
//          }
//          String djbh = formModel.getDataObjectBIZ_DJBH();
//          String title = "附件管理";
//          if (djbh != null && !djbh.equals("")) {
//              title += "-" + djbh;
//          }
//          panel = new JAffixManagerWorkSpace();
//          panel.makeData(formModel, picLabel, false, null, null);
      }catch(Exception e){
    	  e.printStackTrace();
          ErrorManager.getDefault().notify(e);
      }
      return panel;
  }


//  public static void openNewAffixWorkspace(FormPicLabel picLabel){
//      //先判断如果表单不存在GUID,则不允许打开附件
//      try{
//          FormModel formModel = (FormModel) picLabel.getDataSetComponent();
//          if (!checkEnableOpen(formModel)) {
//              return;
//          }
//          String djbh = formModel.getDataObjectBIZ_DJBH();
//          String title = "附件管理";
//          if(djbh != null && !djbh.equals("")){
//              title += "-"+djbh;
//          }
//          //add by lchong at 2012-12-23
//          //打开附件窗口前应该从数据库中读取一次，避免该单据长时间不进入附件管理，在其他地方已经形成附件的情况，但是此时该表单当前并不知道是否有附件,这种情况，最好去加载一下附件
//          String guid = formModel.getDataObjectGUID();
//          String mdl_id = formModel.getBizMDLID();
//          EFDataSet dataSet = JFormAffixUtil.loadAffixDataSet(mdl_id,guid);
//          if(dataSet != null && dataSet.getRowCount() > 0){
//              formModel.getFormDataModel().setDataSet("SYS_MDL_AFFIX",dataSet);
//          }
//          //add end
//
//          JAffixManagerWorkSpace affixWindow = new JAffixManagerWorkSpace();
//          affixWindow.setFormPicLabel(picLabel);
//          affixWindow.makeData(formModel, picLabel,false,null,null);
//          EnterpriseExplorer.ContentView.openChildWindow(affixWindow, title, title, null);
//          EnterpriseExplorer.ContentView.getViewDevice().setExtendedState(EnterpriseExplorer.ContentView,Frame.MAXIMIZED_BOTH);
//          picLabel.setAffixWindow(affixWindow);
////          JAffixManagerWorkSpace panel = new JAffixManagerWorkSpace();
////          panel.makeData(formModel.getFormDataModel());
////          JPubFrame frame = null;
////          frame = new JPubFrame(panel,"附件管理");
////          frame.setPreferredSize(new Dimension(800,700));
////          frame.pack();
////          centerFrameWindow(frame);
//      }
//      catch(Exception e){
//          e.printStackTrace();
////          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "打开附件管理时出错，原因可能是"+e.getMessage());
////          ErrorManager.getDefault().notify(e);
//      }
//
//  }

//  /**
//   * 打开图片附件管理窗口
//   * @param picLabel FormPicLabel
//   */
//  public static void openPicManager(FormPicLabel picLabel){
//      //先判断如果表单不存在GUID,则不允许打开附件
//      FormModel formModel = (FormModel)picLabel.getDataSetComponent();
//      if(!checkEnableOpen(formModel)){
//          return;
//      }
//
//        JPicAttachManagerPanel panel = new JPicAttachManagerPanel(picLabel);
////        JPubDialog pubDialog = new JPubDialog(EAI.EA.getMainWindow(),"��������",true);
////        pubDialog.addPanelToDialog(panel);
////        pubDialog.setPreferredSize(new Dimension(700,500));
////        //Validate frames that have preset sizes
////        //Pack frames that have useful preferred size info, e.g. from their layout
////        pubDialog.pack();
////        pubDialog.CenterWindow();
////        //Center the window
////        pubDialog.setVisible(true);
//
//
////        JPubFrame frame = picLabel.getPubFrame();
//         JPubFrame frame = null;//picLabel.getPubFrame();
////        if(frame == null){
//          frame = new JPubFrame(panel,"附件管理");
//          panel.setParentDlg(frame);
//          frame.setPreferredSize(new Dimension(700,500));
////            picLabel.setPubFrame(frame);
////        }
//        frame.pack();
//        centerFrameWindow(frame);
//  }
//
//  protected static boolean checkEnableOpen(FormModel formModel){
//      String GUID = getBillGuid(formModel);
////      FormModel formModel = (FormModel)picLabel.getDataSetComponent();
////      if(formModel == null) return false;
////      EFFormDataModel formDataModel = formModel.getFormDataModel();
////      if(formDataModel == null) return false;
////      EFDataSet billDataSet = formDataModel.getBillDataSet();
////      String tableName = billDataSet.getTableName();
////      EFRowSet rowSet = billDataSet.getRowSet(0);
////      BIZMetaData bizMetaData=formModel.getBIZMetaData();
////      String GUID_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_GUID_COL_,"F_GUID");
////      String  GUID_COL = "F_PJID";
////      String GUID =    rowSet.getString(GUID_COL,"");
////      if(GUID == null || "".equals(GUID.trim())){
////          GUID =  rowSet.getString(tableName+"." + GUID_COL,"");
////      }
//
//      if(GUID == null || "".equals(GUID.trim())){
//          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(),"表单没有保存或表单信息不完整,不能打开附件!", "提示",JOptionPane.INFORMATION_MESSAGE);
//          return false;
//      }
//
//      return true;
//  }
//
//  public static void centerFrameWindow(JFrame frame){
//      //Center the window
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Dimension frameSize = frame.getSize();
//        if (frameSize.height > screenSize.height) {
//            frameSize.height = screenSize.height;
//        }
//        if (frameSize.width > screenSize.width) {
//            frameSize.width = screenSize.width;
//        }
//        frame.setLocation((screenSize.width - frameSize.width) / 2,
//                          (screenSize.height - frameSize.height) / 2);
//        frame.setVisible(true);
//
//  }

//  /**
//   * 获取图片附件的个数
//   * @param picLabel FormPicLabel
//   * @return int
//   */
//  public static int countPicAttachNumber(FormPicLabel picLabel){
//      FormModel formModel = (FormModel)picLabel.getDataSetComponent();
//      String[] CTN_IDs = formModel.getBIZMetaData().getCTN_IDsByCTN_Type(SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_);
//      if (CTN_IDs == null || CTN_IDs.length == 0) return 0;
//
//      EFFormDataModel formDataModel = formModel.getFormDataModel();
//      EFDataSet affixDataSet = formDataModel.getDataSet("SYS_MDL_AFFIX");
//      if(affixDataSet == null) return 0;
//      return affixDataSet.getRowCount();
////       return affixDataSet.getRowSetList().size();
//  }

  public static void SaveFile(BufferedImage originalBufImage){
      try{
          JFileChooser fc = createFileChooser();
          // Add filefilter & fileview
          ExampleFileFilter filter = new ExampleFileFilter(
                  new String[] {"jpg", "gif","png"},"JPEG and GIF Image files(.gif,.jpg,.png) ");
          ExampleFileView fileView = new ExampleFileView();
          fc.setFileView(fileView);

          fc.addChoosableFileFilter(filter);
          fc.setFileFilter(filter);

          int result = fc.showSaveDialog((java.awt.Component) EAI.EA.getMainWindow());
          if (result == JFileChooser.APPROVE_OPTION) {
              File imageFile = fc.getSelectedFile();
//               ImageIO.write(originalBufImage, "JPEG", f);

              //图片缓存
              int width = originalBufImage.getWidth(null);
              int height = originalBufImage.getHeight(null);
              BufferedImage bi = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
              Graphics2D g2 = bi.createGraphics();
              g2.drawImage(originalBufImage, null, null);

              FileOutputStream fout = new FileOutputStream(imageFile);
              JPEGImageEncoder jencoder = JPEGCodec.createJPEGEncoder(fout);
              JPEGEncodeParam enParam = jencoder.getDefaultJPEGEncodeParam(bi);
              enParam.setQuality(1F, true);
              jencoder.setJPEGEncodeParam(enParam);
              jencoder.encode(bi);
              fout.close();
          }
      }catch(Exception e){
          e.printStackTrace();
      }
  }

//  public static File[] findResAction(){
//          JFileChooser fc = createFileChooser();
//          //运行选择多个文件
//          fc.setMultiSelectionEnabled(true);
//
//           // Add filefilter & fileview
////           ExampleFileFilter filter = new ExampleFileFilter(
////               new String[] {"jpg", "gif"}, "JPEG and GIF Image files(.gif,.jpg) "
////           );
//           ExampleFileView fileView = new ExampleFileView();
//           fc.setFileView(fileView);
////           fc.addChoosableFileFilter(filter);
////           fc.setFileFilter(filter);
//
//           // add preview accessory
//           fc.setAccessory(new FilePreviewer(fc));
//
//           // show the filechooser
//           int result = fc.showOpenDialog((java.awt.Component)EAI.EA.getMainWindow());
//
//           // if we selected an image, load the image
//           if(result == JFileChooser.APPROVE_OPTION) {
////             ImageIcon tmpIcon = new ImageIcon(fc.getSelectedFile().getPath());
////             File file = new File(fc.getSelectedFile().getPath());
////            new ImageIcon(fc.getSelectedFile().getPath());
//
////             this.resObject = JFileByteConvertFunction.getBytesFromFile(file);
//               //记录下选择文件的位置,下次选择的时候就在此处选择
////               String path =  fc.getSelectedFile().getPath();
////               String username = EAI.LocalRegistry.Get("PIC_DIR","0001");
//              String directory = fc.getSelectedFile().getParent();
//              EAI.LocalRegistry.Put("PicDIR",directory);
//
//              return fc.getSelectedFiles();
////             return new String[]{ fc.getSelectedFile().getPath(),fc.getSelectedFile().getName()};
//           }
//           return null;
//    }

    public static JFileChooser createFileChooser() {
        // create a filechooser
        JFileChooser fc = new JFileChooser();
//        File swingFile = null;//new File("resources/images/About.jpg");
//        String dir = EAI.LocalRegistry.Get("PicDIR",null);
//        if(dir == null) return fc;
//        dir += "\\";
//         swingFile = new File(dir);
//        if(swingFile.exists()) {
//            fc.setCurrentDirectory(swingFile);
//        }
       return fc;
    }

//    public static String getBillGuid(FormModel formModel){
//      if(formModel == null) return "";
//
//      String GUID_VAL = formModel.getDataObjectGUID();
//
//      if(GUID_VAL == null || GUID_VAL.trim().equals("")){
//          if(formModel.getFormDataModel() == null) return "";
//          GUID_VAL = formModel.getFormDataModel().getBIZ_GUID();
//      }
//
//      if (GUID_VAL == null || GUID_VAL.trim().equals("")) {
//          BIZMetaData bizMetaData = formModel.getBIZMetaData();
//          String GUID_COL = bizMetaData.getSYS_MDL_VAL(SYS_MODEL._BILL_GUID_COL_,
//                  "F_GUID");
//          GUID_VAL = formModel.getFormDataModel().getBillData(0).getString(GUID_COL,"");
//          if(GUID_VAL == null || GUID_VAL.trim().equals("")){
//              EFFormDataModel fmd = formModel.getFormDataModel();
//              if (fmd == null)
//                  return null;
//              String tableName = fmd.getBillDataSet().getTableName();
//              GUID_VAL = fmd.getBillDataSet().getRowSet().getString(tableName +
//                      "." +
//                      GUID_COL, "");
//          }
//      }
//
//      return GUID_VAL;
//  }
}
