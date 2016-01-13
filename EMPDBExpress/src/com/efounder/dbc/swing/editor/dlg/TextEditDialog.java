package com.efounder.dbc.swing.editor.dlg;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.efounder.form.client.component.pic.TransPanel;
import com.efounder.pfc.dialog.JPDialog;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public  class TextEditDialog extends JPDialog implements ActionListener{
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton btEdit = new JButton();
    JButton btCancel = new JButton();
    JButton btOk = new JButton();
    boolean bchang = false;
    String type = "";
    RSyntaxTextArea scriptEditor = null;
    RTextScrollPane editScrollPane = null;
    
    public TextEditDialog() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TextEditDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
	private void jbInit() throws Exception {
		scriptEditor = new RSyntaxTextArea(580, 480);
		scriptEditor.setEditable(false);
//		scriptEditor.setCodeFoldingEnabled(true);
//		scriptEditor.setAntiAliasingEnabled(true);
		scriptEditor.setCaretPosition(0);
		// textArea.addHyperlinkListener(this);
		scriptEditor.requestFocusInWindow();
		scriptEditor.setMarkOccurrences(true);
		scriptEditor.setEditable(false);
		scriptEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		editScrollPane = new RTextScrollPane(scriptEditor, true);

		flowLayout1.setAlignment(FlowLayout.RIGHT);
		jPanel1.setLayout(flowLayout1);
		btEdit.setText("编辑");
		btOk.setText("确定");
        btOk.setEnabled(false);
		btCancel.setText("取消");
		jPanel1.add(btEdit);
		jPanel1.add(btOk);
		jPanel1.add(btCancel);
		
		this.getContentPane().setLayout(borderLayout1);
		this.getContentPane().add(editScrollPane, BorderLayout.CENTER);
		this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
		
		btEdit.addActionListener(this);
		btOk.addActionListener(this);
		btCancel.addActionListener(this);
	}

    public String getValue(){
    	return scriptEditor.getText();
    }
    
    public void setValue(String val){
    	scriptEditor.setText(val);
    }
    
    public boolean isEditable(){
    	return scriptEditor.isEditable();
    }

    public void setEditable(boolean b){
    	scriptEditor.setEditable(b);
    }
    
    public boolean isEnabled(){
    	return scriptEditor.isEnabled();
    }

    public void setEnabled(boolean b){
    	scriptEditor.setEnabled(b);
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==btEdit){
    		scriptEditor.setEditable(true);
    		btEdit.setEnabled(false);
            btOk.setEnabled(true);
        }
        if(e.getSource()==btOk){
            this.OnOk();
        }
        if(e.getSource()==btCancel){
            this.OnCancel();
        }
    }
}
