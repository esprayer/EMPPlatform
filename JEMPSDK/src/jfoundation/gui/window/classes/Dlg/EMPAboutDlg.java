package jfoundation.gui.window.classes.Dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import jfoundation.gui.window.classes.JFrameDialog;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.eai.EAI;

public class EMPAboutDlg extends JFrameDialog implements ActionListener {
	//主面板
	JPanel					       jMainpanel = new JPanel();	
	TitledBorder            jMainTitledBorder;
	VerticalFlowLayout        jMainFlowLayout = new VerticalFlowLayout();
	
	//放置标题面板
	JPanel                        jTitlePanel = new JPanel();
	JLabel                        jTitleLabel = new JLabel();
	FlowLayout               jTitleFlowLayout = new FlowLayout(FlowLayout.LEFT);
	
	//放置按钮面板
	JPanel                        jButtonPanel = new JPanel();
	FlowLayout               jButtonFlowLayout = new FlowLayout(FlowLayout.RIGHT);

	//版本
	JPanel                       jVerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel                       jVerLabel = new JLabel();
	JLabel                      jVerLabel1 = new JLabel();
	
	//版权所有
	JPanel                        jBqPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel                        jBqLabel = new JLabel();
	JLabel                       jBqLabel1 = new JLabel();
	
	//邮箱
	JPanel                     jEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel                     jEmailLabel = new JLabel();
	JLabel                    jEmailLabel1 = new JLabel();
	
	//电话
	JPanel                     jPhonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel                     jPhoneLabel = new JLabel();
	JLabel                    jPhoneLabel1 = new JLabel();

	//警告
	JPanel                      jWarnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel                      jWarnLabel = new JLabel();
	TitledBorder        jWarnTitledBorder;
	
	JButton                      jOkButton = new JButton();
	
	public EMPAboutDlg(Frame frame, String title, boolean modal) {
		super(frame, title, true);	
		jbInit();
	}
	
	private void jbInit() {
		jMainTitledBorder = new TitledBorder(BorderFactory.createLineBorder(new Color(172, 172, 172)), "");
		jWarnTitledBorder = new TitledBorder(BorderFactory.createLineBorder(new Color(255, 0, 0)), "警告", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.red);

		//设置窗体布局，并且把主Panel放到窗体上
		this.setLayout(new BorderLayout());		
		
		this.getContentPane().setBackground(new Color(163, 193, 228));
		this.add(jTitlePanel, BorderLayout.NORTH);
		this.add(jMainpanel, BorderLayout.CENTER);
		this.add(jButtonPanel, BorderLayout.SOUTH);

		//设置标题面板
		jTitlePanel.setOpaque(false);
		jTitlePanel.setLayout(new FlowLayout());
		jTitleLabel.setText(EAI.Title);
		jTitleLabel.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 20));
		jTitlePanel.add(jTitleLabel);

		//设置主面板，分为左右两部分，左边为图片，右边为数据库信息
		jMainpanel.setOpaque(false);
		jMainpanel.setLayout(jMainFlowLayout);
		jMainpanel.setBorder(jMainTitledBorder);
		
		//单机版
		jVerLabel.setText("软件版本：");
		jVerLabel1.setText("1.0");
		jVerPanel.setOpaque(false);
		jVerPanel.add(jVerLabel);
		jVerPanel.add(jVerLabel1);
		
		//版权所有
		jBqLabel.setText("版权所有：");
		jBqLabel1.setText("诺亚方舟工作室");
		jBqPanel.setOpaque(false);
		jBqPanel.add(jBqLabel);
		jBqPanel.add(jBqLabel1);
		
		//邮箱
		jEmailLabel.setText("联系邮箱：");
		jEmailLabel1.setText("prayeres@126.com");
		jEmailPanel.setOpaque(false);
		jEmailPanel.add(jEmailLabel);
		jEmailPanel.add(jEmailLabel1);

		//电话
		jPhoneLabel.setText("联系电话：");
		jPhoneLabel1.setText("13708928953");
		jPhonePanel.setOpaque(false);
		jPhonePanel.add(jPhoneLabel);
		jPhonePanel.add(jPhoneLabel1);

		//警告
		String warnText = "<html><body>本计算机程序受著作权法和国际公约的保护，<br>"
			            + "未经授权擅自复制或散布本程序的部分或全部，将承受严厉的民事和刑事处罚，<br>"
			            + "对已知的违反者将给予法律范围内的全面制裁。<br>"
			            + "<body></html>";
		jWarnLabel.setText(warnText);
		jWarnPanel.add(jWarnLabel);
		jWarnPanel.setOpaque(false);
		jWarnPanel.setBorder(jWarnTitledBorder);
		
		jMainpanel.add(jVerPanel, null);
		jMainpanel.add(jBqPanel, null);
		jMainpanel.add(jEmailPanel, null);
		jMainpanel.add(jPhonePanel, null);
		jMainpanel.add(jWarnPanel, null);
		
		jOkButton.setPreferredSize(new Dimension( 82, 23 ));
		jOkButton.setText("确定");		
		jOkButton.setMnemonic(KeyEvent.VK_O);
		jOkButton.addActionListener(this);
		
		jButtonFlowLayout.setAlignment(FlowLayout.RIGHT);
		jButtonPanel.setOpaque(false);
		jButtonPanel.setLayout(jButtonFlowLayout);
		
		jButtonPanel.add(jOkButton);
		
		this.setSize(new Dimension(454, 351));
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jOkButton) {
			super.OnOk();
		}
	}
}  
