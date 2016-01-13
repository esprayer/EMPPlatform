package com.efounder.form.client.component.frame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.table.*;

import com.efounder.action.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.bizmodel.*;
import com.efounder.comp.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.service.ParameterManager;
import com.efounder.form.client.component.*;
import com.efounder.form.client.component.infer.ICapture;
import com.efounder.form.client.component.pic.*;
import com.efounder.form.client.util.*;
import org.openide.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;

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
public class JAffixUploadPanel extends JPanel implements ActionListener{
    public JAffixUploadPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static int COL_GUID     = 0;
    public static String NM_GUID   = "GUID";

    public static int COL_STARD    = 1;//8
    public static String NM_STARD   = "附件标准编号";

    public static int COL_NAME     = 2;//1
    public static String NM_NAME   = "名称";
    public static int COL_ODER     = 3;//2
    public static String NM_ODER   = "序号";
    public static int COL_PATH     = 4;//3
    public static String NM_PATH   = "文件路径";
    public static int COL_BZ       = 5;//4
    public static String NM_BZ   = "标志";
    public static int COL_CCFS     = 6;//5  存储方式:文件  数据库
    public static String NM_CCFS   = "存储方式";
    public static int COL_SPATH    = 7;//6 文件存储在服务器的路径
    public static String NM_SPATH   = "路径";
    public static int COL_WJLX     = 8;//7 PIC   PDF    WORD   EXCEL
    public static String NM_WJLX   = "文件类型";


    public static int COL_FLFCT    = 9;//分录事实表
    public static String NM_FLFCT   = "分录事实表";
    public static int COL_FLBH     = 10;//分录事实表编号列
    public static String NM_FLBH   = "分录编号";

    BorderLayout borderLayout = new BorderLayout();
    JTable  listTable  = new ReadonlyTable();
    DefaultTableModel tableModel = new DefaultTableModel();
    JScrollPane scrollPanel = new JScrollPane();
    JAffixShrinkImagePanel shrinkImagePanel = null;

    JPanel mainPanl = new JPanel();
    JPanel cmdPanel = new JPanel();

    JButton addButton = new JButton();
    JButton delButton = new JButton();
    JButton saveButton = new JButton();
    JButton videoCaptureButton = new JButton();

    //业务类型,单位编号,模型ID
    String bizYWLX = null,bizUNIT = null,bizMDL = null,bizGUID = null;

    //附件的DataSet
    EFDataSet affixDataSet = null;
    //标准附件数据
    EFDataSet standardAffixDataSet = null;

    //表单上的附件组件
    FormPicLabel  formImageLabel = null;

    //记录视频捕捉的图片资源
    Map videoResMap = new HashMap();
    //是否标准附件(0：标准)
    int standard = 0;

    //是否分录附件
    boolean isItemAffix = false;
    String itemFctId = null;
    String itemBH = null;
    public int countAffix = 0;

    private void jbInit() throws Exception {
        initTable();
        initEvent();
        initCmdPanel();
        initBtn();

        scrollPanel.getViewport().add(listTable,null);
        mainPanl.setLayout(borderLayout);
        mainPanl.add(scrollPanel);
        this.setLayout(new BorderLayout());
        this.add(cmdPanel,BorderLayout.SOUTH);
        this.add(mainPanl,BorderLayout.CENTER);
    }

    protected void initCmdPanel(){
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        flowLayout.setHgap(8);
        cmdPanel.setLayout(flowLayout);

        cmdPanel.add(addButton);
        cmdPanel.add(delButton);
        cmdPanel.add(saveButton);
        cmdPanel.add(videoCaptureButton);
    }

    protected void initBtn(){
        addButton.setToolTipText("新增");
//        addButton.setMargin(new Insets(0,0,0,0));
        addButton.addActionListener(this);
        addButton.setText("新增");

        delButton.setToolTipText("删除");
//        delButton.setMargin(new Insets(0,0,0,0));
        delButton.addActionListener(this);
        delButton.setText("删除");

        saveButton.setToolTipText("保存");
//        saveButton.setMargin(new Insets(0,0,0,0));
        saveButton.addActionListener(this);
        saveButton.setText("保存");

        videoCaptureButton.setToolTipText("视频拍照");
//        videoCaptureButton.setMargin(new Insets(0,0,0,0));
        videoCaptureButton.addActionListener(this);
        videoCaptureButton.setText("拍照");

        addButton.setIcon(ExplorerIcons.getExplorerIcon("oicons/image.png"));
        delButton.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/delete.png"));
        saveButton.setIcon(ExplorerIcons.getExplorerIcon("oicons/saveall.png"));
        videoCaptureButton.setIcon(ExplorerIcons.getExplorerIcon("FrssIcon/addPic.png"));
    }

    protected void initTable(){
       Vector columnList = new Vector();
       columnList.add(NM_GUID);
       columnList.add(NM_STARD);
       columnList.add(NM_NAME);
       columnList.add(NM_ODER);
       columnList.add(NM_PATH);
       columnList.add(NM_BZ);//新增记录:A  已存在记录:E
       columnList.add(NM_CCFS);
       columnList.add(NM_SPATH);
       columnList.add(NM_WJLX);

       columnList.add(NM_FLFCT);
       columnList.add(NM_FLBH);

       listTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       tableModel.setColumnIdentifiers(columnList);
       listTable.setModel(tableModel);

       listTable.getColumn(NM_NAME).setPreferredWidth(180);
       listTable.getColumn(NM_STARD).setPreferredWidth(90);

       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_GUID)));
       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_BZ)));
       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_SPATH)));
       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_PATH)));
       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_ODER)));


       //文件类型，和存储方式不显示
       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_WJLX)));
       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_CCFS)));
       //zhtbin
//       listTable.getColumn("名称").setPreferredWidth(100);
//       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_ODER)));
//       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_STARD)));
//       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_STARD)));
//       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_CCFS)));
//       listTable.removeColumn(listTable.getColumn(tableModel.getColumnName(COL_WJLX)));

       listTable.setRowHeight(25);

//       TableColumn colorColumn = listTable.getColumn(NM_WJLX);
//       colorColumn.setCellRenderer(new PicResWJLXCellRender());
//       colorColumn = listTable.getColumn(NM_CCFS);
//       colorColumn.setCellRenderer(new PicResCCLXCellRender());

//       TableColumn column = listTable.getColumn(tableModel.getColumnName(COL_PATH));
//       column.setPreferredWidth(0);
//       column = listTable.getColumn(tableModel.getColumnName(COL_CCFS));
//       column.setPreferredWidth(0);
//       column = listTable.getColumn(tableModel.getColumnName(COL_WJLX));
//       column.setPreferredWidth(0);


    }

    public void fillData(EFDataSet dataSet,FormPicLabel  formImageLabel,boolean isItemAffix,String itemFctId,String itemBH){
        this.setFormImageLabel(formImageLabel);

        setIsItemAffix(isItemAffix);
        setItemFctId(itemFctId);
        setItemBH(itemBH);

        if(dataSet == null || dataSet.getRowCount() == 0) return;
        //记录下来
        this.setAffixDataSet(dataSet);
        //先把内容清空
        int size = tableModel.getRowCount();
        for(int i = size -1 ; i >= 0; i--){
            tableModel.removeRow(i);
        }

        //填充新的数据
        for(int i = 0; i < dataSet.getRowCount(); i++){
            EFRowSet rowSet = (EFRowSet)dataSet.getRowSet(i);
            Vector rowItem = new Vector();

            String flFct = rowSet.getString("F_FLFCT","");
            String flFctBH = rowSet.getString("F_FLBH","");
            if(isItemAffix){//分录附件,则要求对应的分录表相同,并且分录表编码相同
                if(itemFctId.equals(flFct.trim()) && itemBH.equals(flFctBH.trim()) ){
                    tableModel.addRow(rowItem);
                }else{//分录表不对应
                    continue;
                }
            }else{
                tableModel.addRow(rowItem);
            }


            rowItem.add(COL_GUID,rowSet.getString("F_GUID",""));
            rowItem.add(COL_STARD,rowSet.getString("F_FJBZBH",""));
            rowItem.add(COL_NAME,rowSet.getString("F_NAME",""));
            rowItem.add(COL_ODER,rowSet.getString("F_ORDE",""));
            rowItem.add(COL_PATH,"");//路径:打开的附件没有路径
            rowItem.add(COL_BZ,"E");//存在
            rowItem.add(COL_CCFS,rowSet.getString("F_CCLX",""));//文件FILE , 数据库DB
            rowItem.add(COL_SPATH,rowSet.getString("F_PATH",""));
            rowItem.add(COL_WJLX,rowSet.getString("F_WJLX",""));//文件类型

            rowItem.add(COL_FLFCT,rowSet.getString("F_FLFCT",""));//分录事实表
            rowItem.add(COL_FLBH,rowSet.getString("F_FLBH",""));//分录编号
        }
    }

    Map affixDataList = null;
    public void makeAffixDataList(){
        JParamObject PO = null;//JParamObject.Create();
//        if(formModel != null){
//            PO = formModel.getParamObject();
//        }else{
            PO = JParamObject.Create();
//        }
        PO.SetValueByParamName("MDL",this.getBizMDL());
//        PO.SetValueByParamName("GUID",JPicAttachFunction.getBillGuid(formModel));
//        
//        affixDataList = JFormAffixUtil.loadAffixDataList(formModel,PO);
        countAffix = affixDataList.size();
    }
    
    public void setAffixDataList(Map affixDataList){
        this.affixDataList = affixDataList;
        if(affixDataList != null){
            countAffix = affixDataList.size();
        }
    }
    
    public void clearAffixData(){
        if(affixDataList != null)
            affixDataList.clear();
    }

    public void showShrinkImage() throws Exception{
        int size = tableModel.getRowCount();
        for(int i = 0; i < size; i++){
            //如果图片太多的话，则在缩略图数量太多，则附件管理窗口打不开，目前缩略图最多显示30个，剩下的图片可以通过浏览上下页可以看到
            if(i > 29){
                JOptionPane.showMessageDialog(EAI.EA.getMainWindow(),
                    "图片数量太多，缩略图支持显示30个，剩余图片可以通过浏览方式查看!", "提示",JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            String name  = (String) tableModel.getValueAt(i, COL_NAME);
            String order = (String) tableModel.getValueAt(i, COL_ODER);
            String type = (String) tableModel.getValueAt(i, COL_WJLX);
            String ccfs = (String) tableModel.getValueAt(i, COL_CCFS);
            byte[] data = (byte[])affixDataList.get(order);

//            Object[] res = JAffixActionFunction.getImageDescBean(data,name,type,order);
//            shrinkImagePanel.addImageToToolbar((JImageDescBean)res[0],(Icon)res[1],i);
        }
    }

    public byte[] imageToByte(Icon ima) throws Exception{
        BufferedImage bu = new  BufferedImage(ima.getIconWidth(),
                                         ima.getIconHeight(),
                                         BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        ImageIO.write(bu, "GIF", imageStream);
        byte[] tagInfo = imageStream.toByteArray();
        return tagInfo;
    }



    protected void initEvent(){
        listTable.addMouseListener(new UploadMouseAdapter(this));
        scrollPanel.addMouseListener(new UploadMouseAdapter(this));
    }


    public void mouseClicked(MouseEvent e) {
       if (( e.getModifiers() & e.BUTTON3_MASK ) != 0  ) {//e.getModifiers() == e.BUTTON3_MASK 右键弹出菜单
           ActionGroup popupMenu = null;
           Point p = e.getPoint();
           if(e.getSource()== listTable || e.getSource()== scrollPanel){
               popupMenu = getMenuAction();
           }
           if (popupMenu != null) {
               showPopupmenu(p, (java.awt.Component) e.getSource(), popupMenu);
           }
        }else if(( e.getModifiers() & e.BUTTON1_MASK ) != 0){
            //点击行的时候
            processClickRow();
        }
    }

    protected void processClickRow(){
        int selRow = listTable.getSelectedRow();
        if(selRow == -1) return;

        //点击的时候选择图片
        String name = (String)tableModel.getValueAt(selRow,COL_NAME);
        String order = (String)tableModel.getValueAt(selRow,COL_ODER);
        JImagePanel imagePanel = shrinkImagePanel.getImagePanel();

        int compCnt = imagePanel.getComponentCount();
        for(int i = 0; i < compCnt; i++){
            Component comp = imagePanel.getComponent(i);
            if(comp instanceof JPanel){
                JPanel buttonBar = (JPanel)comp;
                setBorderNull(buttonBar);
                int subCompCnt = buttonBar.getComponentCount();
                for(int j = 0; j < subCompCnt; j++){
                    Component subComp = buttonBar.getComponent(j);
                    if(subComp instanceof JToggleButton){
                        JImageDescBean desBean = (JImageDescBean)((JToggleButton)subComp).getClientProperty("DES");
                        if(order.equals(desBean.getOrder())){
                            ((JToggleButton)subComp).setSelected(true);

                            Border border  = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(255, 0, 0)), "");
                            ((JToggleButton)subComp).setBorder(border);

                            break;
                        }else{
//                            Border border  = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(166, 200, 240)), "");
                            ((JToggleButton)subComp).setBorder(null);
                        }

                    }
                }
            }
        }
    }

    protected void setBorderNull(JPanel buttonBar){
        int subCompCnt = buttonBar.getComponentCount();
        for(int j = 0; j < subCompCnt; j++){
            Component subComp = buttonBar.getComponent(j);
            if(subComp instanceof JToggleButton){
                ((JToggleButton)subComp).setBorder(null);
            }
        }
    }

    public void removeButtons(java.util.List delNameList){
        for(int i = 0; i < delNameList.size(); i++){
            Vector dataList = (Vector)delNameList.get(i);
//            String name = (String)dataList.get(3);
            String order = (String)dataList.get(1);
            removeButton(order);
        }
    }

    public void removeButton(String order){
        JImagePanel imagePanel = shrinkImagePanel.getImagePanel();
        int compCnt = imagePanel.getComponentCount();
        for(int i = 0; i < compCnt; i++){
            Component comp = imagePanel.getComponent(i);
            if(comp instanceof JPanel){
                JPanel buttonBar = (JPanel)comp;
                int subCompCnt = buttonBar.getComponentCount();
                for(int j = 0; j < subCompCnt; j++){
                    Component subComp = buttonBar.getComponent(j);
                    if(subComp instanceof JToggleButton){
                        JImageDescBean desBean = (JImageDescBean)((JToggleButton)subComp).getClientProperty("DES");
                        if(order.equals(desBean.getOrder())){
                            ((JToggleButton)subComp).setIcon(null);
                            buttonBar.remove(subComp);
                            imagePanel.updateUI();
                            break;
                        }
                    }
                }
            }
        }
    }

    protected ActionGroup getMenuAction() {
        ActionGroup ag = null;
//        Object[] nodeArray = {this, listTable};
//        Action[] actions = ActionManager.getContextActions(this.getClass().getName(), this, nodeArray);
//        if (actions != null) {
//            ag = new ActionGroup();
//            for (int i = 0; i < actions.length; i++) {
//                ag.add(actions[i]);
//            }
//        }
        return ag;
    }

    protected void showPopupmenu(Point p, java.awt.Component invoker,
                         ActionGroup popupMenu) {
        if (popupMenu != null) {
            ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker, popupMenu, true);
            actionPopupMenu.show(invoker, p.x, p.y);
        }
    }

    class ReadonlyTable extends JTable {
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    public JAffixShrinkImagePanel getShrinkImagePanel() {
        return shrinkImagePanel;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public String getBizMDL() {
        return bizMDL;
    }

    public String getBizUNIT() {
        return bizUNIT;
    }

    public String getBizYWLX() {
        return bizYWLX;
    }

    public EFDataSet getAffixDataSet() {
        return affixDataSet;
    }

    public FormPicLabel getFormImageLabel() {
        return formImageLabel;
    }

    public String getBizGUID() {
        return bizGUID;
    }

    public Map getAffixDataList() {
        return affixDataList;
    }

    public EFDataSet getStandardAffixDataSet() {
        return standardAffixDataSet;
    }

    public JTable getListTable() {
        return listTable;
    }

    public void setShrinkImagePanel(JAffixShrinkImagePanel shrinkImagePanel) {
        this.shrinkImagePanel = shrinkImagePanel;
    }

    public void setBizMDL(String bizMDL) {
        this.bizMDL = bizMDL;
    }

    public void setBizUNIT(String bizUNIT) {
        this.bizUNIT = bizUNIT;
    }

    public void setBizYWLX(String bizYWLX) {
        this.bizYWLX = bizYWLX;
    }

    public void setAffixDataSet(EFDataSet affixDataSet) {
        this.affixDataSet = affixDataSet;
    }

    public void setFormImageLabel(FormPicLabel formImageLabel) {
        this.formImageLabel = formImageLabel;
    }

    public void setBizGUID(String bizGUID) {
        this.bizGUID = bizGUID;
    }

    public void setStandardAffixDataSet(EFDataSet standardAffixDataSet) {
        this.standardAffixDataSet = standardAffixDataSet;
    }

    /**
     *  取已存在附件的个数
     * @return int
     */
    public int getAffixCount(){
        return listTable.getRowCount();
    }

    /**
     *
     * @param e ActionEvent
     */
    public void setStandard(int standard){
        this.standard = standard;
    }

    public void setIsItemAffix(boolean isItemAffix) {
        this.isItemAffix = isItemAffix;
    }

    public void setItemFctId(String itemFctId) {
        this.itemFctId = itemFctId;
    }

    public void setItemBH(String itemBH) {
        this.itemBH = itemBH;
    }

    /**
     *
     * @return int
     */
    public int getStandard(){
        return this.standard;
    }

    public boolean isIsItemAffix() {
        return isItemAffix;
    }

    public String getItemFctId() {
        return itemFctId;
    }

    public String getItemBH() {
        return itemBH;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton){
            onSave();
        }else if(e.getSource() == addButton){
            onAdd();
        }else if(e.getSource() == delButton){
            onDelete();
        }else if(e.getSource() == videoCaptureButton){
            onCaptureAction();
        }
    }

    private void onAdd(){
//        try {
//            JAffixActionFunction.addAffix(this, this.listTable);
//        } catch (Exception ex) {
//           JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "新增附件时出错，原因可能是"+ex.getMessage());
//
////            ErrorManager.getDefault().notify(ex);
//        }
    }
    private void onDelete(){
//        try {
//            JAffixActionFunction.delAffix(this, this.listTable);
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "删除附件时出错，原因可能是"+ex.getMessage());
////            ErrorManager.getDefault().notify(ex);
//        }
    }
    public void onSave(){
//        try {
//            JAffixActionFunction.saveAffix(this, this.listTable);
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), "保存附件时出错，原因可能是"+ex.getMessage());
////            ErrorManager.getDefault().notify(ex);
//        }
    }

    public boolean isUpdate(){
//         return JAffixActionFunction.isUpdate(this.listTable);
    	return false;
    }

//    MediaLocator mediaLocator = null;
//    VideoCamera videoCamera = null;

    public void onCaptureAction(){
        //判断列表中是否有未保存的新增附件有的话则提示，否则不能图像扫描
        if(!isExistNotSaveAffix()){
            JOptionPane.showMessageDialog(EAI.EA.getMainWindow(),
                    "请先保存的附件!", "提示",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
		JParamObject PO=JParamObject.Create();
//		String mdlid=formModel.getMDL_ID();
//		String defcntype=ParameterManager.getDefault().getModelConfigValue(PO, mdlid, "AFFIX_SAVETYPE", "DB");


//        Object ret = onNtCapture(defcntype);
//        if(ret != null) return;

		return;
		
        //使用通用的摄像头接口
//        onGeneralVideoCapture();
    }

    protected boolean isExistNotSaveAffix(){
        for(int i = 0; i < tableModel.getRowCount(); i++){
           String bz = (String)tableModel.getValueAt(i,COL_BZ);
           if(!bz.equals("E")){
               return false;
           }
       }

        return true;
    }

    private Object onNtCapture(String ccfs){
      try {
        Class cls = Class.forName("com.lc.client.dll.JNtNataive");
        if(cls == null) return null;
        ICapture cap = (ICapture)cls.newInstance();

        //请输入文件
        //客户又不让弹出选择标准附件了
//        String[] fileSuff = getStanardAffixName();
        String[] fileSuff = new String[]{"CapShot","拍照"};
        if(fileSuff == null) return "A";
        String[] picFiles = cap.Capture(80,1,1,fileSuff[1],"C:\\WINDOWS\\Temp\\");
        if(picFiles == null||picFiles.length == 0) return "A";//抛出异常

        //找出最大的序号
        int maxOrder = getMaxOrder(this.listTable);
        for(int i = 0; i < picFiles.length; i++){
             String name = picFiles[i].substring(0, picFiles[i].lastIndexOf("."));
             name = name.substring(name.lastIndexOf("\\")+1);

//             String[] rowData = new String[]{name,
//                                ""+(maxOrder+1),
//                                ccfs,//存储方式
//                                JFormAffixVar.TYPE_PIC,//附件类型：pic
//                                picFiles[i],
//                                fileSuff[0]};
//             JAffixActionFunction.putAffixToModel(this,rowData);

             maxOrder = maxOrder+1;
        }

        return "A";
     } catch ( Exception e ){
        e.printStackTrace();
     }
     return null;
   }

   /**
    * 找出最大的序号
    * @param table JTable
    * @return int
    */
   public static int getMaxOrder(JTable table){
       if(table.getRowCount() == 0) return 0;

       int retOrder = 0;
       DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
       for(int i = 0; i < tableModel.getRowCount(); i++){
           String order = (String)tableModel.getValueAt(i,COL_ODER);
           if(Integer.parseInt(order) >retOrder){
               retOrder = Integer.parseInt(order);
           }
       }
       return retOrder;
   }

   private String[] getStanardAffixName(){
//	   if(standardAffixDataSet==null||
//			   standardAffixDataSet.getRowCount()==0){
//		   return new String[]{"99","其它"};
//	   }else{
//	       JSelectStandardAffixPanel sAffixNamePanel = new JSelectStandardAffixPanel(this.getStandardAffixDataSet());
//	       JPubDialog pubDlg = new JPubDialog(EAI.EA.getMainWindow(),"请选择标准附件",true);
//	       pubDlg.addPanelToDialog(sAffixNamePanel);
//	       pubDlg.setPreferredSize(new Dimension(300,150));
//	       pubDlg.setOKText("确定");
//	       pubDlg.setCancelText("取消");
//	       pubDlg.pack();
//	       pubDlg.CenterWindow();
//	       pubDlg.setVisible(true);
//	       return (String[])pubDlg.getRetValue();
//	   }
	   return null;

   }


    public void onGeneralVideoCapture(){
//        //先判断是否有设备环境
//        if(mediaLocator == null){
//            mediaLocator = VideoDeviceDetectUtil.autoDetect();
//        }
//        if(mediaLocator == null) return;
//        //已经打开,不能再打开另外一个视频窗口
////        if(videoCamera != null) return;
//
//        videoCamera = new VideoCamera(mediaLocator,this);
//        boolean isSuccOpen = videoCamera.isIsSuccOpen();
//        if(!isSuccOpen) return;
//
//        JPubDialog pubDlg = new JPubDialog(EAI.EA.getMainWindow(),"视频拍照",false);
//        pubDlg.addPanelToDialog(videoCamera);
//        pubDlg.setPreferredSize(new Dimension(960,700));
//        pubDlg.setOKText("应用");
//        pubDlg.pack();
//        pubDlg.CenterWindow();
//        pubDlg.setVisible(true);
   }

   public void addVideoPic(String order,Image image){
       videoResMap.put(order,image);
   }

   public Image getVideoImage(String order) {
       return (Image)videoResMap.get(order);
   }
}


class UploadMouseAdapter
    extends MouseAdapter {
  private JAffixUploadPanel adaptee;
  UploadMouseAdapter(JAffixUploadPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.mouseClicked(e);
  }
}

