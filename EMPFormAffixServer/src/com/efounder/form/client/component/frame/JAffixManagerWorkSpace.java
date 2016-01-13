package com.efounder.form.client.component.frame;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.bizmodel.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.form.*;
import com.efounder.form.client.component.*;
import com.efounder.form.client.component.frame.util.*;
import com.efounder.form.client.component.infer.*;
import com.efounder.form.client.util.*;
import com.efounder.pfc.window.*;


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
public class JAffixManagerWorkSpace extends JChildWindow  implements IDiglogPanel{
    public JAffixManagerWorkSpace() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    FormPicLabel formPicLabel = null;
    JPanel topPanel           =   new JPanel();
    JPanel bottomPanel        =   new JPanel();
    JSplitPane mainSplitPane  =   new JSplitPane();
    JSplitPane typeSplitPane  =   new JSplitPane();
    BorderLayout borderLayout =   new BorderLayout();
    JAffixStandardExamplePanel standardExamplePanel = new JAffixStandardExamplePanel();
    JAffixUploadPanel           uploadAffixPanel    = new JAffixUploadPanel();
    JAffixShrinkImagePanel     imagePanel           = new JAffixShrinkImagePanel();

    public JAffixUploadPanel getUploadAffixPanel(){
        return this.uploadAffixPanel;
    }
    private void jbInit() throws Exception {
        initSplit();
        initPanel();

        this.setLayout(borderLayout);
        this.add(mainSplitPane,BorderLayout.CENTER);
        this.add(topPanel,BorderLayout.NORTH);
        this.add(bottomPanel,BorderLayout.SOUTH);
        mainSplitPane.add(typeSplitPane,JSplitPane.LEFT);
        mainSplitPane.add(imagePanel,JSplitPane.RIGHT);

        //设置2面板间相互引用
        uploadAffixPanel.setShrinkImagePanel(imagePanel);
        imagePanel.setUploadPanel(uploadAffixPanel);
    }

    private void initSplit(){
        mainSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setDividerSize(10);
        mainSplitPane.setDividerLocation(320);

        typeSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        typeSplitPane.setOneTouchExpandable(true);
        typeSplitPane.setDividerSize(10);
        typeSplitPane.setDividerLocation(320);
    }

    protected void initPanel(){
        typeSplitPane.add(standardExamplePanel,JSplitPane.BOTTOM);
        standardExamplePanel.setBorder(new TitledBorder("标准附件列表"));

        typeSplitPane.add(uploadAffixPanel,JSplitPane.TOP);
        uploadAffixPanel.setBorder(new TitledBorder("已上传附件"));
    }

//    /**
//     *
//     * @param formModel FormModel
//     * @param formImageLabel FormPicLabel
//     * @param isItemAffix boolean
//     * @param itemFctId String
//     * @param itemBH String
//     * @throws Exception
//     */
//    public void makeData(FormModel formModel,FormPicLabel  formImageLabel,boolean isItemAffix,String itemFctId,String itemBH) throws Exception{
//        EFFormDataModel formDataModel = formModel.getFormDataModel();
//
//        String[] keyParams = JImageAffixUtil.getKeyParams(formModel);
//
//        EFDataSet standardAffixDS = (EFDataSet)formDataModel.getDataSet("SYS_MDL_AFFIX_STANDARD");
//        EFDataSet hasAffixDS      = (EFDataSet)formDataModel.getDataSet("SYS_MDL_AFFIX");
//        if(standardAffixDS == null && !isItemAffix && formImageLabel.getStandard() == 0  ){
//            //说明进入附件是从新建的单据直接保存后近来的,没有加载插件数据
//            //在此处要专门再加载一下数据
////            JParamObject paramObj = JParamObject.Create();
//            //zhtbin 从模型获取po，夸服务器
//            JParamObject paramObj = formModel.getParamObject();
//            paramObj.SetValueByParamName("YWLX",keyParams[0]);
//            standardAffixDS = JFormAffixUtil.loadStandardAffixList(paramObj);
//        }
//
//        //设置模型
//        standardExamplePanel.setFormModel(formModel);
//        uploadAffixPanel.setFormModel(formModel);
//
//        //显示数据
//        standardExamplePanel.fillData(standardAffixDS);
//        uploadAffixPanel.fillData(hasAffixDS,formImageLabel,isItemAffix,itemFctId,itemBH);
//        uploadAffixPanel.setStandardAffixDataSet(standardAffixDS);
//
//        //设置模型的相关数据
//        uploadAffixPanel.setBizMDL(formModel.getBizMDLID());
//        uploadAffixPanel.setBizUNIT(formModel.getDataObjectBIZ_UNIT());
//
//        //设置业务模型编号
//        uploadAffixPanel.setBizYWLX(keyParams[0]);
//        uploadAffixPanel.setBizGUID(keyParams[1]);
//
//        //加载附件格式数据
//        uploadAffixPanel.makeAffixDataList();
//
//        //把格式数据显示到界面上
//        uploadAffixPanel.showShrinkImage();
//        if(!isItemAffix){
//            int opType = formImageLabel.getOpType();
//            if (opType == 1) { //查看
//                uploadAffixPanel.addButton.setVisible(false);
//                uploadAffixPanel.delButton.setVisible(false);
//                uploadAffixPanel.saveButton.setVisible(false);
//            }
//        }
//        //如果不是标准附件，隐藏标准附件(0:是1否)
//        if(!isItemAffix&&formImageLabel.getStandard() == 1){
//            uploadAffixPanel.setStandard(1);
//            typeSplitPane.setDividerLocation(2000);
//            uploadAffixPanel.listTable.getColumn(uploadAffixPanel.NM_NAME).setPreferredWidth(300);
////            uploadAffixPanel.listTable.getColumn(uploadAffixPanel.NM_WJLX).setPreferredWidth(100);
////            uploadAffixPanel.listTable.removeColumn(uploadAffixPanel.listTable.getColumn(uploadAffixPanel.NM_ODER));
//            uploadAffixPanel.listTable.removeColumn(uploadAffixPanel.listTable.getColumn(uploadAffixPanel.NM_STARD));
////            uploadAffixPanel.listTable.removeColumn(uploadAffixPanel.listTable.getColumn(uploadAffixPanel.NM_CCFS));
//
//        }
//        //如果不分分录添加附件,隐藏分录编号列
//        if (!isItemAffix) {
//            uploadAffixPanel.listTable.removeColumn(uploadAffixPanel.listTable.
//                                                    getColumn(uploadAffixPanel.NM_FLFCT));
//            uploadAffixPanel.listTable.removeColumn(uploadAffixPanel.listTable.
//                                                    getColumn(uploadAffixPanel.NM_FLBH));
//        }
//    }


    public boolean onApply() {
        return false;
    }

    public Object retValue() {
        return null;
    }

    public boolean canClose() {
        //判断是否有更新
        boolean isUpdte =  uploadAffixPanel.isUpdate();
        if(isUpdte){
            int result = JOptionPane.showConfirmDialog(EAI.EA.getMainWindow(), "您已上传了附件,确认要保存吗?");
            if(result == JOptionPane.YES_OPTION) {
                uploadAffixPanel.onSave();
            }else if(result == JOptionPane.CANCEL_OPTION){
                return false;
            }
        }
        //窗口关闭的时候，把组件中记忆的窗口清除
        if(formPicLabel != null){
            formPicLabel.setAffixWindow(null);
        }

        return true;
    }

    public void destroy() {
    }

    public void setFormPicLabel(FormPicLabel formPicLabel) {
        this.formPicLabel = formPicLabel;
    }


}
