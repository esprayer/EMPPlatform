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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import jfoundation.gui.window.classes.JFrameDialog;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.pub.util.EMPStringUtils;
import com.efounder.pub.util.MD5Tool;

public class EMPChangePaawordDlg extends JFrameDialog implements ActionListener {
	//主面板
	JPanel					       jMainpanel = new JPanel();	
	TitledBorder            jMainTitledBorder;
	VerticalFlowLayout        jMainFlowLayout = new VerticalFlowLayout();
	
	//放置标题面板
	JPanel                        jTitlePanel = new JPanel();
	JLabel                        jTitleLabel = new JLabel();
	
	//放置按钮面板
	JPanel                        jButtonPanel = new JPanel();
	FlowLayout               jButtonFlowLayout = new FlowLayout(FlowLayout.RIGHT);

	//旧密码
	JPanel                       jOldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JLabel                       jOldLabel = new JLabel();
	JPasswordField               edOldPass = new JPasswordField();
	
	//新密码
	JPanel                       jNewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JLabel                       jNewLabel = new JLabel();
	JPasswordField               edNewPass = new JPasswordField();
	
	//确认密码
	JPanel            jConfirmNewPassPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JLabel            jConfirmNewPassLabel = new JLabel();
	JPasswordField        edConfirmNewPass = new JPasswordField();

	JButton                      jOkButton = new JButton();
	
	public EMPChangePaawordDlg(Frame frame, String title, boolean modal) {
		super(frame, title, true);	
		jbInit();
	}
	
	private void jbInit() {
		String password = "";
		this.PO = JParamObject.Create();
		
		jMainTitledBorder = new TitledBorder(BorderFactory.createLineBorder(new Color(172, 172, 172)), "修改密码");

		//设置窗体布局，并且把主Panel放到窗体上
		this.setLayout(new BorderLayout());		
		
		this.getContentPane().setBackground(new Color(163, 193, 228));
		this.add(jTitlePanel, BorderLayout.NORTH);
		this.add(jMainpanel, BorderLayout.CENTER);
		this.add(jButtonPanel, BorderLayout.SOUTH);

		//设置图片面板
		jTitlePanel.setLayout(new BorderLayout());
		ImageIcon imageIcon = EMPStringUtils.createImageIcon("banner.png");
		if (imageIcon != null) {
			jTitleLabel.setIcon(imageIcon);
        }
		jTitlePanel.setSize(new Dimension(154, 351));
		
		jTitlePanel.add(jTitleLabel, BorderLayout.CENTER);
		
		//设置标题面板
		jTitlePanel.setOpaque(false);
		jTitlePanel.setLayout(new FlowLayout());
		jTitlePanel.add(jTitleLabel);

		//设置主面板，分为左右两部分，左边为图片，右边为数据库信息
		jMainpanel.setOpaque(false);
		jMainpanel.setLayout(jMainFlowLayout);
		jMainpanel.setBorder(jMainTitledBorder);
		
		//旧密码
		jOldLabel.setText("     旧密码：");
		edOldPass.setPreferredSize( new Dimension( 270, 23 ) );
		jOldPanel.setOpaque(false);
		jOldPanel.add(jOldLabel);
		jOldPanel.add(edOldPass);
		
		//新密码
		jNewLabel.setText("     新密码：");
		edNewPass.setPreferredSize( new Dimension( 270, 23 ) );
		jNewPanel.setOpaque(false);
		jNewPanel.add(jNewLabel);
		jNewPanel.add(edNewPass);
		
		//确认密码
		jConfirmNewPassLabel.setText("确认密码：");
		edConfirmNewPass.setPreferredSize( new Dimension( 270, 23 ) );
		jConfirmNewPassPanel.setOpaque(false);
		jConfirmNewPassPanel.add(jConfirmNewPassLabel);
		jConfirmNewPassPanel.add(edConfirmNewPass);
		
		jMainpanel.add(jOldPanel, null);
		jMainpanel.add(jNewPanel, null);
		jMainpanel.add(jConfirmNewPassPanel, null);
		
		jOkButton.setPreferredSize(new Dimension( 82, 23 ));
		jOkButton.setText("确定");		
		jOkButton.setMnemonic(KeyEvent.VK_O);
		jOkButton.addActionListener(this);
		
		jButtonFlowLayout.setAlignment(FlowLayout.RIGHT);
		jButtonPanel.setOpaque(false);
		jButtonPanel.setLayout(jButtonFlowLayout);
		
		jButtonPanel.add(jOkButton);
		
		password = MD5Tool.getDefault().getMD5ofStr(PO.GetValueByEnvName("USER_PASS", ""));
		
		edOldPass.setText(password);
		edOldPass.setEditable(false);
		
		this.setSize(new Dimension(454, 401));
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jOkButton) {
			try {
				boolean bSave = saveNewPassword();
				if(bSave) {
					super.OnOk();
				}
			} catch(Exception ce) {
				ce.printStackTrace();
				JOptionPane.showMessageDialog(this, "密码修改错误，原因：\r\n" + ce.getMessage(),"标题",JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	public boolean saveNewPassword() throws Exception {
		JResponseObject RO = null;
		String newPassword = "";
		
		if(checkNewPass()) {
			this.PO.SetValueByParamName("NEW_PASSWORD", this.edNewPass.getText().trim());
			RO = (JResponseObject) EAI.DAL.IOM("SYSManagerObject", "saveNewPassword", this.PO);
			if(RO != null && RO.getErrorCode() != -1) {
				newPassword = RO.getResponseObject().toString();
				PO.setObject("USER_PASS", this.edNewPass.getText().trim());
				JOptionPane.showMessageDialog(this, "密码修改成功！");
				return true;
			}
		}
		return false;
	}
	
	private boolean checkNewPass() {
		String newPassword = this.edNewPass.getText().trim();
		String confirmNewPass = this.edConfirmNewPass.getText().trim();
		
		if(newPassword.equals("")) {
			JOptionPane.showMessageDialog(this, "请输入新密码！","标题",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!newPassword.equals(confirmNewPass)) {
			JOptionPane.showMessageDialog(this, "新密码与确认密码不一致，请检查后重新输入确认密码！","标题",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}  
