package com.efounder.form.client.component.frame;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.action.ActionGroup;
import com.efounder.action.ActionToolBarPane;
import com.efounder.action.ActiveObjectAction;
import com.efounder.actions.ActionManager;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.form.client.component.action.ImagePreviewActionObject;
import com.efounder.form.client.component.action.JAffixActionFunction;
import com.efounder.form.client.component.frame.action.EMPAffixAction;
import com.efounder.form.client.component.infer.IDiglogPanel;
import com.efounder.form.client.component.pic.JFileByteConvertFunction;
import com.efounder.form.client.component.pic.TransPanel;
import com.efounder.form.client.util.EMPAffixUtil;
import com.efounder.form.client.util.JFormAffixVar;
import com.efounder.pfc.dialog.JPDialog;

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

public class PreViewImagePanel extends JPanel implements ActionListener,
        ChangeListener,IDiglogPanel,MouseWheelListener {
    public PreViewImagePanel(JAffixUploadPanel affixUploadPanel,String imageOrder) {
        try {
            this.affixUploadPanel = affixUploadPanel;
            this.imageOrder = imageOrder;

            enableEvents(AWTEvent.WINDOW_EVENT_MASK);
            initPageDesList();
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSplitPane splitPane = new JSplitPane();
    JPanel leftPanel  = new JPanel();
    JScrollPane leftScrollPanel = new JScrollPane();

    JPanel rightPanel = new JPanel();
    Frame parentFrame = null;
    JPDialog parentDlg = null;
    JAffixUploadPanel affixUploadPanel = null;
    TransPanel preView = new TransPanel();
    JScrollPane scrollPane = new JScrollPane();
    JSlider jSlider1 = new JSlider(JSlider.VERTICAL);


//    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(null, "jpeg", "jpg", "png", "gif", "ico");
    JFileChooser imageChooser = new JFileChooser();
    public File copyFile;
    public File cutFile;
    public File directFile;
    public File[] imagePath;

    public JPanel northPanel = new JPanel();
    protected JPanel otherAffixPanel = new JPanel();
    String imageName = null;
    String imageOrder = null;
    ActionToolBarPane commandPanel = new ActionToolBarPane(this);
    JToolBar toolBar = new JToolBar();
    JPanel descPanel = new JPanel();
    JLabel descLabel = new JLabel();
    public void jbInit() {
        this.setLayout(new BorderLayout());

//        VerticalFlowLayout vf = new VerticalFlowLayout();
        FlowLayout vf = new FlowLayout();
        vf.setAlignment(vf.LEFT);
        leftPanel.setLayout(vf);
        leftPanel.setBorder(new TitledBorder("其他附件"));
        
        descLabel.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("宋体",Font.BOLD,15);
        descLabel.setFont(font);
        descLabel.setForeground(Color.red);
//        descLabel.setText("1/1");

        descPanel.setLayout(new BorderLayout());
        descPanel.add(descLabel,BorderLayout.CENTER);
        commandPanel.setBackground(descPanel.getBackground());
        northPanel.setLayout(new BorderLayout());
//        northPanel.add(commandPanel,BorderLayout.WEST);
        northPanel.add(toolBar,BorderLayout.WEST);
        northPanel.add(descPanel,BorderLayout.CENTER);
        
        scrollPane = new JScrollPane(preView);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(northPanel,BorderLayout.NORTH);
        rightPanel.add(scrollPane,BorderLayout.CENTER);
        
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.add(leftScrollPanel,JSplitPane.LEFT);
        splitPane.add(rightPanel,JSplitPane.RIGHT);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);
        

        this.add(splitPane,BorderLayout.CENTER);

        

        Border border = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "");
        scrollPane.setBorder(border);
        this.getActions();
//        commandPanel.addGroup(this.getActions());
        commandPanel.setBorder(new TitledBorder(""));
        initSliderComp();

        preView.addMouseWheelListener(this);

        repaint();
    }


    protected void initSliderComp() {
        //下边距
        jSlider1.setPreferredSize(new Dimension(200, 10));
        jSlider1.addChangeListener(this);
        jSlider1.setMinimum(-10);
        jSlider1.setValue(0);
        jSlider1.setMaximum(10);
        jSlider1.setOrientation(JSlider.HORIZONTAL);
    }

    java.util.List picOrderlist = new ArrayList();
    protected void initPageDesList(){
        DefaultTableModel tableModel = affixUploadPanel.getTableModel();
        for(int i = 0; i < tableModel.getRowCount(); i++){
            String fileType = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_WJLX);
            String order = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_ODER);
            String id = EMPAffixUtil.getOperTypeByWjlx(fileType);
            if(!id.equals(JFormAffixVar.TYPE_PIC)) continue;

            picOrderlist.add(order);
        }
    }

    protected String getPicPageDesc(String order){
        int index = picOrderlist.indexOf(order);
        return ""+(index+1)+"/"+picOrderlist.size();
    }


    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
//        if(source == otherAffixList){
//
//        }
    }




    public void previousImageAction(){
        int findIndex = picOrderlist.indexOf(imageOrder);
        if(findIndex == 0) {
            findIndex = picOrderlist.size() -1;
        }else{
            findIndex --;
        }

        imageOrder = (String)picOrderlist.get(findIndex);
        int modeIndex = JAffixActionFunction.findIndex(srcTableModel,imageOrder);
        preView.resetAction();
        changeImage(srcTableModel,modeIndex,srcTableModel.getRowCount(),true);
        setDesc();
    }

    public void nextImageAction(){
        int findIndex = picOrderlist.indexOf(imageOrder);
        if(findIndex == picOrderlist.size() -1) {
            findIndex = 0;
        }else{
            findIndex ++;
        }

        imageOrder = (String)picOrderlist.get(findIndex);
        int modeIndex = JAffixActionFunction.findIndex(srcTableModel,imageOrder);
        preView.resetAction();
        changeImage(srcTableModel,modeIndex,srcTableModel.getRowCount(),true);
        setDesc();
    }

    public void setDesc(){
        String desc = getPicPageDesc(imageOrder);
        descLabel.setText(desc);
    }


    public void autoFitAction(){
        preView.autoFitAction();
    }

    private void changeImage(DefaultTableModel tableModel,int findIndex,int maxRow,boolean rightOrt){
        try{
//            if(findIndex >  maxRow-1) findIndex =0;
//            if(findIndex < 0) findIndex = maxRow-1;
//            setPreNextButtonEnable(findIndex,maxRow);
            String imgName  = (String)tableModel.getValueAt(findIndex,JAffixUploadPanel.COL_NAME);
            String order    = (String)tableModel.getValueAt(findIndex,JAffixUploadPanel.COL_ODER);
            String fileType = (String)tableModel.getValueAt(findIndex,JAffixUploadPanel.COL_WJLX);

            String isNew = (String)tableModel.getValueAt(findIndex,JAffixUploadPanel.COL_BZ);
            String path = (String)tableModel.getValueAt(findIndex,JAffixUploadPanel.COL_PATH);
            //不是图片
//            if(!fileType.equals(JFormAffixVar.TYPE_PIC)){
//                if(rightOrt){
//                    changeImage(srcTableModel, findIndex + 1, srcTableModel.getRowCount(),rightOrt);
//                }else{
//                    changeImage(srcTableModel, findIndex - 1, srcTableModel.getRowCount(),rightOrt);
//                }
//                return;
//            }


            //修改文件名
            imageName = imgName;
//            imageOrder = order;
            //要修改父frame的title
            if(parentFrame != null){
                parentFrame.setTitle(imageName);
            }else if(parentDlg != null){
            	parentDlg.setTitle(imageName);
            }

            Object data = null;
            if("E".equals(isNew)){//已存在
                data = (byte[])affixDataList.get(order);
                if(data == null&& path != null){
                    File tmpFile = new File(path);
                    data = JFileByteConvertFunction.getBytesFromFile(tmpFile);
                }
            }else if("N".equals(isNew)|| "Y".equals(isNew)){
                //没有找到数据,可能是新保存的附件没有重新load数据,找不到资源
                File tmpFile = new File(path);
                if(data == null){
                   data = JFileByteConvertFunction.getBytesFromFile(tmpFile);
                }
            }else if("V".equals(isNew)){
                data = affixUploadPanel.getVideoImage(order);
            }

            preView.loadPicRes(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    DefaultTableModel srcTableModel = null;
    Map affixDataList = null;

    /**
     *
     * @param tableModel DefaultTableModel:table中的字典显示内容
     * @param affixDataList Map:附件的数据
     * @param res Object:当前看到的资源
     * @param imageName String
     */
    public void callImagePreview(DefaultTableModel tableModel,Map affixDataList,Object selRes,String imageName) throws Exception {
        //传递面板是因为有时候,面板上添加内容后,
        this.imageName    = imageName;
        this.srcTableModel = tableModel;
        this.affixDataList = affixDataList;

        preView.loadPicRes(selRes);

        //控制上下按钮的状态
//        int findIndex = JAffixActionFunction.findIndex(tableModel,imageName);
//        setPreNextButtonEnable(findIndex,tableModel.getRowCount());
        initOtherAffix(tableModel);

        setDesc();
    }



    public void callImagePreview(Object selRes,String imageName){
        this.imageName = imageName;
        if(parentFrame != null){
        	this.parentFrame.setTitle(imageName);
        }else if(parentDlg != null){
        	this.parentDlg.setTitle(imageName);
        }
        preView.loadPicRes(selRes);

        //控制上下按钮的状态
//        this.previousImage.setEnabled(false);
//        this.nextImage.setEnabled(false);
    }

    protected void initOtherAffix(DefaultTableModel tableModel) throws Exception{

        int size = tableModel.getRowCount();
        for(int i = 0; i < size; i++){
            String name  = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_NAME);
            String order    = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_ODER);
            String fileType = EMPAffixUtil.getOperTypeByWjlx((String)tableModel.getValueAt(i,JAffixUploadPanel.COL_WJLX));

            String isNew = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_BZ);
            String path = (String)tableModel.getValueAt(i,JAffixUploadPanel.COL_PATH);
            //不是图片
            if(fileType.equals("1")) continue;
            StubObject stub = new StubObject();
            stub.setCaption(name);
            stub.setString("FILETYPE",fileType);
            stub.setString("ORDER",order);
            stub.setString("ISNEW",isNew);
            stub.setString("PATH",path);

            byte[] affixData = (byte[])affixUploadPanel.getAffixDataList().get(order);
            Object[] res = JAffixActionFunction.getImageDescBean(affixData,name,fileType,order);

            JToggleButton toggleButton = createButton((JImageDescBean)res[0],(Icon)res[1]);
            leftPanel.add(toggleButton);
            leftScrollPanel.getViewport().add(leftPanel,null);
        }
    }

    public String getOperTypeByWjlx(String WJLX) throws Exception {
        List affixTypeList = PackageStub.getContentVector("AffixType");
        for(int i = 0; i < affixTypeList.size(); i++){
            StubObject stub = (StubObject)affixTypeList.get(i);
            String id = stub.getString("id","");
            if(WJLX.equals(id)) {
            	return id;
            }
        }
        return "";
    }
    protected JToggleButton createButton(JImageDescBean imgDesBean,Icon icon){
        JToggleButtonExt button = new JToggleButtonExt();
        button.setName(imgDesBean.getImgName());
        button.setLabel(imgDesBean.getImgName());
        button.setToolTipText(imgDesBean.getImgName());
        button.setIcon(icon);
        button.addActionListener(this);
        button.putClientProperty("DES",imgDesBean);
        button.setPreferredSize(new Dimension(130, 70));//300,250
        button.addMouseListener(new OtherAffixMouseAdapter(this));
        button.setBackground(Color.white);

        button.setOpaque(false);
        button.setBorder(null);

        return button;
    }



    public Object retValue() {
        return null;
    }

    protected void changeSliderValue(){

    }

    public void stateChanged(ChangeEvent e) {
        Object Sender = e.getSource();
        if (Sender == this.jSlider1) {
//            int value = jSlider1.getValue();
//            preView.setZoomLevel(value);
        }
    }

    public TransPanel getPreView() {
        return preView;
    }

    public boolean onApply() {
        return false;
    }

    public void setParentFrame(Frame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
    public void setParentDlg(JPDialog parentDlg) {
        this.parentDlg = parentDlg;
    }
    
    public void destroy() {
        affixUploadPanel.clearAffixData();
    }

    public void getActions() {
    	ImagePreviewActionObject imageViewCompActionObject = new ImagePreviewActionObject();
    	Action action = null;
//    	Action action = new EMPAffixAction(imageViewCompActionObject, this, "imageSaveAs", "另存为...", '0', "图像另存",
//        toolBar.add(new JButton(printAction));
    	
//        ActionGroup ag = new ActionGroup();
//        ActionGroup GROUP_POPUP = new ActionGroup();
//        Action actions[] = ActionManager.getContextActions("ImagePreView_ACTION", this, null);
//        Action action = new EMPAffixAction(imageViewCompActionObject, this, "imageSaveAs", "另存为...", '0', "图像另存", ExplorerIcons.getExplorerIcon("jprofiler/action_saveSnapshot_16.png"));
//        toolBar.add(action);

        action = new EMPAffixAction(imageViewCompActionObject, this, "rotateImage1", "逆时针", '0',"逆时针", ExplorerIcons.getExplorerIcon("jprofiler/undo_16.png"));
        toolBar.add(action);
//
        action = new EMPAffixAction(imageViewCompActionObject, this, "rotateImage2", "顺时针", '0',"顺时针", ExplorerIcons.getExplorerIcon("jprofiler/redo_16.png"));

        toolBar.add(action);

        action = new EMPAffixAction(imageViewCompActionObject, this, "autoFitImage", "自适应", '0',"自适应", ExplorerIcons.getExplorerIcon("J2EEApp.gif"));
        toolBar.add(action);
//
        action = new EMPAffixAction(imageViewCompActionObject, this, "resetImage", "原图", '0',"原图", ExplorerIcons.getExplorerIcon("DataSetView_Color16.gif"));

        toolBar.add(action);

		action = new EMPAffixAction(imageViewCompActionObject, this, "previousImage", "上一个", '0',"上一个", ExplorerIcons.getExplorerIcon("jprofiler/browser_backward_16.png"));
		toolBar.add(action);
		
		action = new EMPAffixAction(imageViewCompActionObject, this, "nextImage", "下一个", '0',"下一个", ExplorerIcons.getExplorerIcon("jprofiler/browser_forward_16.png"));
		toolBar.add(action);
		
		action = new EMPAffixAction(imageViewCompActionObject, this, "imageZoomIn", "放大", '0',"放大", ExplorerIcons.getExplorerIcon("oicons/zoomin.png"));
		toolBar.add(action);
		
		action = new EMPAffixAction(imageViewCompActionObject, this, "imageZoomOut", "缩小", '0', "缩小", ExplorerIcons.getExplorerIcon("oicons/zoomout.png"));
		toolBar.add(action);
        
        
//        ActionGroup ag = new ActionGroup();
//        ActionGroup GROUP_POPUP = new ActionGroup();
//        Action actions[] = ActionManager.getContextActions("ImagePreView_ACTION", this, null);
//        for (int i = 0; actions != null && i < actions.length; i++) {
//            GROUP_POPUP.add(actions[i]);
//        }
//        ag.add(GROUP_POPUP);
//        return ag;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {//向上滚动:缩小
            preView.zoomInAction();
        } else {//向下滚动：放大
            preView.zoomOutAction();
        }
    }

    public void mouseClicked(MouseEvent e) {
//    	if (e.getClickCount() == 2 && ( e.getModifiers() & e.BUTTON1_MASK ) != 0  ) {
    	if (e.getClickCount() == 2) {
            try {
              JAffixActionFunction.openImageView(affixUploadPanel,(JToggleButton) e.getSource());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
//    	if (e.getClickCount() == 1 && ( e.getModifiers() & e.BUTTON1_MASK ) != 0  ) {
//			JToggleButton button = (JToggleButton) e.getSource();
//			JImageDescBean imgDesBean = (JImageDescBean)button.getClientProperty("DES");
//			imageOrder = imgDesBean.getOrder();
//			int modeIndex = JAffixActionFunction.findIndex(srcTableModel, imageOrder);
//			preView.resetAction();
//			changeImage(srcTableModel, modeIndex, srcTableModel.getRowCount(), true);
//			setDesc();
//        }
    }

}

class OtherAffixMouseAdapter
    extends MouseAdapter {
  private PreViewImagePanel adaptee;
  OtherAffixMouseAdapter(PreViewImagePanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.mouseClicked(e);
  }
}
