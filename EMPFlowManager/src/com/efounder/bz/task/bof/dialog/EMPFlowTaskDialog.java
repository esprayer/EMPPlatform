package com.efounder.bz.task.bof.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.openide.WaitingManager;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.component.dc.table.DictTable;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.bz.task.bof.render.DataFormEnumCellRenderer;
import com.efounder.bz.task.bof.render.ImageCellRenderer;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dbc.swing.render.EnumCellRenderer;
import com.efounder.dbservice.data.AccountStub;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.util.EMPTableDictUtils;
import com.efounder.dctbuilder.view.DictTableView;
import com.efounder.dctbuilder.view.DictView;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.ide.ExplorerIcons;

import jfoundation.gui.window.classes.JFrameDialog;

public class EMPFlowTaskDialog extends JFrameDialog implements ListSelectionListener, ActionListener, Runnable {
	//主面板
	JPanel					       jMainPanel = new JPanel();		
	
	//主面板
	JPanel					     jCenterPanel = new JPanel();		
	
	/**
	 * 上下分割面板
	 */
	JSplitPane                     jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
	//待办任务面板
	JPanel				   jFlowTaskListPanel = new JPanel();		
	
	//处理意见面板
	JPanel				     jSuggestionPanel = new JPanel();		
	
	JTextArea             jSuggestionTextArea = new JTextArea();
	
	//放置流程信息面板
	JPanel                        jTitlePanel = new JPanel();
	JLabel                        jTitleLabel = new JLabel();
	FlowLayout               jTitleFlowLayout = new FlowLayout(FlowLayout.LEFT);
	
	JPanel                   jFlowDetailPanel = new JPanel();
	TitledBorder            jFlowDetailBorder;
	FlowLayout          jFlowDetailFlowLayout = new FlowLayout(FlowLayout.LEFT);
	
	//会计期间
	JLabel                         jKjqjLabel = new JLabel();
	JTextField                 jKjqjTextField = new JTextField(); 
	
	//单据编号
	JLabel                         jDjbhLabel = new JLabel();
	JTextField                 jDjbhTextField = new JTextField();
	
	//提交人
	JLabel                          jTjrLabel = new JLabel();
	JTextField                  jTjrTextField = new JTextField();
	
	//提交日期
	JLabel                         jTjrqLabel = new JLabel();
	JTextField                 jTjrqTextField = new JTextField();
	
	JLabel                     jTaskNameLabel = new JLabel();
	JTextField             jTaskNameTextField = new JTextField();
	
	//放置流程任务列表
	DictView                jFlowTaskListView = new DictTableView(); //待办任务列表

	//放置按钮面板
	JPanel                       jButtomPanel = new JPanel();
	JPanel                        jImagePanel = new JPanel();
	JPanel                       jButtonPanel = new JPanel();
	JLabel                   jSuspendingLabel = new JLabel();       //待处理
	JLabel                       jSolvedLabel = new JLabel();       //已处理
	JLabel                     jHandlingLabel = new JLabel();       //处理中
	JButton                         jOkButton = new JButton();
		

	
	public EMPFlowTaskDialog(Frame frame, String title, boolean modal, JParamObject PO) {
		super(frame, title, true);	
		this.PO = PO;
		jbInit();		
		WaitingManager.getDefault().waitInvoke(EAI.EA.getMainWindow(), "系统正在加载流程数据", "系统正在加载流程数据，请稍后......", null, this);
	}
	
	private void jbInit() {
		jFlowDetailBorder = new TitledBorder(BorderFactory.createLineBorder(new Color(0, 153, 153)), "代办任务信息", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.BLACK);

		//设置窗体布局，并且把主Panel放到窗体上
		this.setLayout(new BorderLayout());		
		
		this.getContentPane().setBackground(new Color(163, 193, 228));
		this.add(jTitlePanel, BorderLayout.NORTH);
		this.add(jMainPanel, BorderLayout.CENTER);
		this.add(jButtomPanel, BorderLayout.SOUTH);

		jMainPanel.setOpaque(false);
		jMainPanel.setLayout(new BorderLayout());		
		jMainPanel.add(jFlowDetailPanel, BorderLayout.NORTH);
		jMainPanel.add(jCenterPanel, BorderLayout.CENTER);
		
		jCenterPanel.setLayout(new BorderLayout());
		jCenterPanel.add(jSplitPane);
		
		jSplitPane.setTopComponent(jFlowTaskListView);
		jSplitPane.setBottomComponent(jSuggestionPanel);	
		
		jSuggestionPanel.setLayout(new BorderLayout());
		jSuggestionPanel.add(new JScrollPane(jSuggestionTextArea), BorderLayout.CENTER);
		
		//设置标题面板
		jTitlePanel.setOpaque(false);
		jTitlePanel.setLayout(new FlowLayout());
		jTitleLabel.setText("[" + PO.GetValueByParamName("FLOW_MC", "") + "]代办任务流程信息");
		jTitleLabel.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 20));
		jTitlePanel.add(jTitleLabel);
		
		jFlowDetailPanel.setOpaque(false);
		jFlowDetailPanel.setBorder(jFlowDetailBorder);
		//会计期间
		jKjqjLabel.setText("     会计期间：");
		jKjqjTextField.setText(PO.GetValueByParamName("BIZ_KJQJ", ""));	
		jKjqjTextField.setPreferredSize(new Dimension(150, 23 ));
		jKjqjTextField.setEnabled(false);
		jFlowDetailPanel.add(jKjqjLabel);
		jFlowDetailPanel.add(jKjqjTextField);
		
		//单据编号
		jDjbhLabel.setText("     单据编号：");
		jDjbhTextField.setText(PO.GetValueByParamName("BIZ_DJBH", ""));
		jDjbhTextField.setPreferredSize(new Dimension(150, 23 ));
		jDjbhTextField.setEnabled(false);
		jFlowDetailPanel.add(jDjbhLabel);
		jFlowDetailPanel.add(jDjbhTextField);
		
		//提交人
		jTjrLabel.setText("     提交人：");
		jTjrTextField.setText(PO.GetValueByParamName("OP_USER_NAME", ""));
		jTjrTextField.setPreferredSize(new Dimension(150, 23 ));
		jTjrTextField.setEnabled(false);
		jFlowDetailPanel.add(jTjrLabel);
		jFlowDetailPanel.add(jTjrTextField);
		
		//提交日期
		jTjrqLabel.setText("     提交日期：");
		jTjrqTextField.setText(PO.GetValueByParamName("F_TJRQ", ""));
		jTjrqTextField.setPreferredSize(new Dimension(150, 23 ));
		jTjrqTextField.setEnabled(false);
		jFlowDetailPanel.add(jTjrqLabel);
		jFlowDetailPanel.add(jTjrqTextField);

		
		
		jButtomPanel.setOpaque(false);
		jButtomPanel.setLayout(new BorderLayout());
		jButtomPanel.add(this.jImagePanel, BorderLayout.CENTER);
		jButtomPanel.add(this.jButtonPanel, BorderLayout.EAST);
		
		jImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		jSuspendingLabel.setText("待处理");
		jSuspendingLabel.setIcon(ExplorerIcons.getExplorerIcon("icons/tasks-question.png"));
		jSolvedLabel.setText("已处理");
		jSolvedLabel.setIcon(ExplorerIcons.getExplorerIcon("eclipse2/migrate16.png"));
		jHandlingLabel.setText("处理中");
		jHandlingLabel.setIcon(ExplorerIcons.getExplorerIcon("oicons/forum.png"));
		
		jImagePanel.setOpaque(false);
		jImagePanel.add(jSuspendingLabel);
		jImagePanel.add(jSolvedLabel);
		jImagePanel.add(jHandlingLabel);		
		
		jButtonPanel.setOpaque(false);
		jButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jOkButton.setPreferredSize(new Dimension( 82, 23 ));
		jOkButton.setText("确定");		
		jOkButton.setMnemonic(KeyEvent.VK_O);
		jButtonPanel.add(jOkButton);
		
		this.setSize(new Dimension(854, 671));		
		addListener();		
	}
	
	public void run() {
		jbInitColumn();
		loadData();			
		this.repaint();
//		this.setVisible(true);
	}
	
	private void jbInitColumn() {
		ColumnModel                             columnModel = new ColumnModel();
		ColumnMetaData                          colMetaData = ColumnMetaData.getInstance();
		Column                                  tableColumn = null;
		DataFormEnumCellRenderer   dataFormEnumCellRenderer = new DataFormEnumCellRenderer();
		ImageCellRenderer                 imageCellRenderer = null;
		Map<String, String>                         iconMap = new HashMap<String, String>();
		
		iconMap.put("pending", "icons/tasks-question.png");
		iconMap.put("processed", "eclipse2/migrate16.png");
		iconMap.put("wainting", "oicons/forum.png");
		
		imageCellRenderer = new ImageCellRenderer(iconMap);
		
		colMetaData.setString("COL_ID", "NODE_TAG_NAME");
		colMetaData.setString("COL_MC", "操作节点");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);
		
		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "RESR_IN_CAUSE");
		colMetaData.setString("COL_MC", "数据来源");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);
		
		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "OP_USER_NAME");
		colMetaData.setString("COL_MC", "提交人");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);
		
		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "OP_BMMC");
		colMetaData.setString("COL_MC", "提交部门");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);

		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "TASK_TO_UNIT_NAME");
		colMetaData.setString("COL_MC", "处理部门");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);
		
		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "RESR_OUT_CAUSE");
		colMetaData.setString("COL_MC", "处理意见");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);

		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "RESR_STATUS");
		colMetaData.setString("COL_MC", "数据状态");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);		
		
		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "OP_NOTE");
		colMetaData.setString("COL_MC", "处理说明");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);
		
		colMetaData = ColumnMetaData.getInstance();
		colMetaData.setString("COL_ID", "F_CLRQ");
		colMetaData.setString("COL_MC", "处理时间");
		colMetaData.setString("COL_TYPE", "C");
		colMetaData.setString("COL_EDITABLE", "0");
		columnModel.addColumn(colMetaData);
		
		
		for(int i = 0; i < columnModel.getColumnCount(); i++) {
			tableColumn = (Column) columnModel.getColumn(i);
			if(tableColumn.getColumnMeataData().getColid().equals("RESR_STATUS")) {
				tableColumn.setCellRenderer(imageCellRenderer);
			} else if(tableColumn.getColumnMeataData().getColid().equals("RESR_IN_CAUSE") || tableColumn.getColumnMeataData().getColid().equals("RESR_OUT_CAUSE")) {
				tableColumn.setCellRenderer(dataFormEnumCellRenderer);
			}
			tableColumn.setWidth(150);
			tableColumn.setPreferredWidth(150);
			tableColumn.setDataSetColID(tableColumn.getColumnMeataData().getColid());
		}
		this.getDictTable().setColModel(columnModel);
	}
	
	public void addListener() {
		jFlowTaskListView.getTable().getSelectionModel().addListSelectionListener(this);
		jOkButton.addActionListener(this);
	}
	
	private void loadData() {
		ClientDataSet          dataSet = new ClientDataSet();
		JResponseObject             RO = null;
		EFDataSet      flowTaskDataSet = null;
		
		try {
			RO = (JResponseObject) com.efounder.eai.EAI.DAL.IOM("TaskIOService", "loadAllFlowTask", this.PO);
			if(RO == null || RO.getResponseObject() == null || RO.getErrorCode() == -1) {
				flowTaskDataSet = EFDataSet.getInstance();
			} else {
				flowTaskDataSet = (EFDataSet) RO.getResponseObject();
			}
			dataSet.setRowSetList(flowTaskDataSet.getRowSetList());	
			this.getDictModel().setDataSet(dataSet);		
		} catch(Exception ce) {
			ce.printStackTrace();
		}		
	}
	
	/**
	 *
	 * @return DictModel
	 */
	public DictModel getDictModel(){
      return this.getDictView().getDictModel();
	}

	/**
	 *
	 * @return DictView
	 */
	public DictView getDictView() {
      return this.jFlowTaskListView;
	}
	
	/**
	 *
	 * @return DictTable
	 */
	public DictTable getDictTable() {
      return getDictView().getTable();
	}
  
	/**
	 *
	 * @return ClientDataSet
	 */
	public ClientDataSet getDataSet() {
      return getDictModel().getDataSet();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jOkButton) {
			super.OnOk();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		int  selRow = jFlowTaskListView.getTable().getSelectedRow();
		
		if(selRow == -1) return;
		
		ClientDataSet dataset = jFlowTaskListView.getDictModel().getDataSet();
		
		if(selRow > dataset.getRowCount() - 1) return;
		
		EFRowSet rowset = dataset.getRowSet(selRow);
		
		String OP_NOTE = rowset.getString("OP_NOTE", "");
		
		jSuggestionTextArea.setText(OP_NOTE);
		
	}
}