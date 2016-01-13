package com.efounder.form.client.component.frame;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
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

import javax.swing.AbstractAction;
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
import javax.swing.SwingConstants;
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
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.form.client.component.action.HImagePreviewActionObject;
import com.efounder.form.client.component.action.ImagePreviewActionObject;
import com.efounder.form.client.component.action.JAffixActionFunction;
import com.efounder.form.client.component.frame.action.EMPAffixAction;
import com.efounder.form.client.component.infer.IDiglogPanel;
import com.efounder.form.client.component.pic.JFileByteConvertFunction;
import com.efounder.form.client.component.pic.TransPanel;
import com.efounder.form.client.util.EMPAffixUtil;
import com.efounder.form.client.util.JFormAffixVar;
import com.efounder.pfc.dialog.JPDialog;
import com.jidesoft.swing.JideButton;

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

public class HPreViewImagePanel extends JPanel implements ActionListener, ChangeListener,IDiglogPanel,MouseWheelListener {
    public HPreViewImagePanel() {
        try {        
            enableEvents(AWTEvent.WINDOW_EVENT_MASK);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    JideButton easternButton = new JideButton();
    JideButton clockwiseButton = new JideButton();
    JideButton autoFitImageButton = new JideButton();
    JideButton resetImageButton = new JideButton();
    JideButton previousImageButton = new JideButton();
    JideButton nextImageButton = new JideButton();
    JideButton imageZoomInButton = new JideButton();
    JideButton imageZoomOutButton = new JideButton();
    
    public void jbInit() {
        this.setLayout(new BorderLayout());
        preView = new TransPanel();
//        VerticalFlowLayout vf = new VerticalFlowLayout();
        FlowLayout vf = new FlowLayout();
        vf.setAlignment(vf.LEFT);

        northPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new GridLayout(8, 1));

        scrollPane = new JScrollPane(preView);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(northPanel,BorderLayout.EAST);
        rightPanel.add(scrollPane,BorderLayout.CENTER);

        this.add(rightPanel,BorderLayout.CENTER);

        

        Border border = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "");
        scrollPane.setBorder(border);
        
        initSliderComp();

        preView.addMouseWheelListener(this);
        jbInitButton();
        repaint();
    }

    public void jbInitButton() {
    	easternButton.setText("逆时针");
    	easternButton.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/undo_16.png"));
    	easternButton.addActionListener(this);
    	
    	clockwiseButton.setText("顺时针");
    	clockwiseButton.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/redo_16.png"));
    	clockwiseButton.addActionListener(this);
    	
    	autoFitImageButton.setText("原  图");
    	autoFitImageButton.setIcon(ExplorerIcons.getExplorerIcon("J2EEApp.gif"));
    	autoFitImageButton.addActionListener(this);

    	resetImageButton.setText("自适应");
    	resetImageButton.setIcon(ExplorerIcons.getExplorerIcon("DataSetView_Color16.gif"));
    	resetImageButton.addActionListener(this);
    	
    	previousImageButton.setText("上一个");
    	previousImageButton.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/browser_backward_16.png"));
    	previousImageButton.addActionListener(this);
    	
    	nextImageButton.setText("下一个");
    	nextImageButton.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/browser_forward_16.png"));
    	nextImageButton.addActionListener(this);
    	
    	imageZoomInButton.setText("放  大");
    	imageZoomInButton.setIcon(ExplorerIcons.getExplorerIcon("oicons/zoomin.png"));
    	imageZoomInButton.addActionListener(this);
    	
    	imageZoomOutButton.setText("缩  小");
    	imageZoomOutButton.setIcon(ExplorerIcons.getExplorerIcon("oicons/zoomout.png"));
    	imageZoomOutButton.addActionListener(this);
    	
    	northPanel.add(easternButton);
    	northPanel.add(clockwiseButton);
    	northPanel.add(autoFitImageButton);
    	northPanel.add(resetImageButton);
    	northPanel.add(previousImageButton);
    	northPanel.add(nextImageButton);
    	northPanel.add(imageZoomInButton);
    	northPanel.add(imageZoomOutButton);
    	
//    	Action action = null;
//    	HImagePreviewActionObject imageViewCompActionObject = new HImagePreviewActionObject();
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "rotateImage1", "逆时针", '0',"逆时针", ExplorerIcons.getExplorerIcon("jprofiler/undo_16.png"));
//    	easternButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "rotateImage2", "顺时针", '0',"顺时针", ExplorerIcons.getExplorerIcon("jprofiler/redo_16.png"));
//    	clockwiseButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "autoFitImage", "自适应", '0',"自适应", ExplorerIcons.getExplorerIcon("J2EEApp.gif"));
//    	autoFitImageButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "resetImage", "原图", '0',"原图", ExplorerIcons.getExplorerIcon("DataSetView_Color16.gif"));
//    	resetImageButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "previousImage", "上一个", '0',"上一个", ExplorerIcons.getExplorerIcon("jprofiler/browser_backward_16.png"));
//    	previousImageButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "nextImage", "下一个", '0',"下一个", ExplorerIcons.getExplorerIcon("jprofiler/browser_forward_16.png"));
//    	nextImageButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "imageZoomIn", "放大", '0',"放大", ExplorerIcons.getExplorerIcon("oicons/zoomin.png"));
//    	imageZoomInButton.addActionListener(action);
//    	
//    	action = new EMPAffixAction(imageViewCompActionObject, this, "imageZoomOut", "缩小", '0', "缩小", ExplorerIcons.getExplorerIcon("oicons/zoomout.png"));
//    	imageZoomOutButton.addActionListener(action);
    }
    
    public void initData(DictModel tableModel,JAffixUploadPanel affixUploadPanel,String imageOrder) {
    	try {
            this.affixUploadPanel = affixUploadPanel;
            this.imageOrder = imageOrder;
            this.srcTableModel = tableModel;
            enableEvents(AWTEvent.WINDOW_EVENT_MASK);
            initPageDesList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void RotateImage(boolean b) {
    	preView.RotateImage(b);
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
    	
    	EFRowSet    rowset = null;
    	EFDataSet  dataset = srcTableModel.getDataSet();
    	
    	picOrderlist.clear();
    	
        for(int i = 0; dataset != null && i < dataset.getRowCount(); i++){
        	rowset = dataset.getRowSet(i);
            String fileType = rowset.getString("F_WJLX", "");
            String order = rowset.getString("F_GUID", "");
            String id = EMPAffixUtil.getOperTypeByWjlx(fileType);
            if(!id.equals(JFormAffixVar.TYPE_PIC)) continue;

            picOrderlist.add(order);
        }
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        //逆时针
        if(source == easternButton){
        	RotateImage(true);
        } 
        //顺时针
        else if(source == clockwiseButton) {
        	preView.RotateImage(false);
        } 
        //自适应
        else if(source == autoFitImageButton) {
        	preView.resetAction();
        } 
        //原图
        else if(source == resetImageButton) {
        	preView.autoFitAction();
        } 
        //上一个
        else if(source == previousImageButton) {
        	previousImageAction();
        } 
        //下一个
        else if(source == nextImageButton) {
        	nextImageAction();
        } 
        //放大
        else if(source == imageZoomInButton) {
        	preView.zoomInAction();
        } 
        //缩小
        else if(source == imageZoomOutButton) {
        	preView.zoomOutAction();
        }
    }




    public void previousImageAction(){
        int findIndex = picOrderlist.indexOf(imageOrder);
        if(findIndex == 0) {
            findIndex = picOrderlist.size() -1;
        }else{
            findIndex --;
        }

        imageOrder = (String)picOrderlist.get(findIndex);
        int modeIndex = findIndex(srcTableModel,imageOrder);
        preView.resetAction();
        changeImage(srcTableModel,modeIndex,srcTableModel.getRowCount(),true);
    }

    public void nextImageAction(){
        int findIndex = picOrderlist.indexOf(imageOrder);
        if(findIndex == picOrderlist.size() -1) {
            findIndex = 0;
        }else{
            findIndex ++;
        }

        imageOrder = (String)picOrderlist.get(findIndex);
        int modeIndex = findIndex(srcTableModel,imageOrder);
        preView.resetAction();
        changeImage(srcTableModel,modeIndex,srcTableModel.getRowCount(),true);
    }

    public void autoFitAction(){
        preView.autoFitAction();
    }

    private void changeImage(DictModel tableModel,int findIndex,int maxRow,boolean rightOrt){
        try{
//            if(findIndex >  maxRow-1) findIndex =0;
//            if(findIndex < 0) findIndex = maxRow-1;
//            setPreNextButtonEnable(findIndex,maxRow);
        	EFDataSet      dataset = tableModel.getDataSet();
        	EFRowSet        rowset = null;
        	
        	if(dataset == null) return;
        	
        	rowset = dataset.getRowSet(findIndex);
        	
        	if(rowset == null) return;
        	
            String imgName  = rowset.getString("F_NAME", "");
            String order    = rowset.getString("F_GUID", "");
            String fileType = rowset.getString("F_WJLX", "");

            String isNew = rowset.getString("F_BZ", "E");
            String path = rowset.getString("F_PATH", "");
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

    DictModel srcTableModel = null;
    Map affixDataList = null;

    /**
     *
     * @param tableModel DefaultTableModel:table中的字典显示内容
     * @param affixDataList Map:附件的数据
     * @param res Object:当前看到的资源
     * @param imageName String
     */
    public void callImagePreview(Map affixDataList,Object selRes,String imageName) throws Exception {
    	 //传递面板是因为有时候,面板上添加内容后,
        this.imageName    = imageName;
        this.affixDataList = affixDataList;

        preView.loadPicRes(selRes);
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

    public static int findIndex(DictModel tableModel,String order){
        int        findIndex = 0;
        EFDataSet    dataset = tableModel.getDataSet();
        EFRowSet      rowset = null;
        
        for(int i = 0; i < dataset.getRowCount(); i++){
        	rowset = dataset.getRowSet(i);
        	String itemOrder = rowset.getString("F_GUID", "");
        	if(order.equals(itemOrder)){
        		findIndex = i;
        		break;
        	}
        }

        return findIndex;
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