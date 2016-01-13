package com.efounder.dbc.swing.editor.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dbc.swing.editor.dlg.ImageDialog;
import com.efounder.dbc.swing.editor.dlg.TextEditDialog;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.util.DictUtil;
import com.efounder.eai.EAI;
import com.efounder.eai.ide.ExplorerIcons;

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
public class TextEditField extends JPanel implements ActionListener,  MouseListener, FocusListener {

    BorderLayout bl = new BorderLayout();
    JTextField text = null;
    JButton bthelp = new JButton();
    Icon icon = null;
    ColumnMetaData cmd;
    private DictModel model;

    /**
     *
     * @param jtf JTextField
     * @param cmd ColumnMetaData
     */
    public TextEditField(JTextField jtf,ColumnMetaData cmd) {
        this.cmd = cmd;
        setLayout(bl);
        text = jtf;
        text.setEditable(false);
        text.addMouseListener(this);
        text.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        icon = ExplorerIcons.getExplorerIcon("help.gif");
        initDictModel();
        bthelp.setIcon(icon);
        bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),
                                              icon.getIconHeight()));
        add(text, BorderLayout.CENTER);
        add(bthelp, BorderLayout.EAST);
        text.addFocusListener(this);
        bthelp.addActionListener(this);
    }

    /**
     * 初始化DictModel
     */
    private void initDictModel() {
        if (cmd.getLostValue("DictModelObject", null) != null) {
            model = (DictModel) cmd.getLostValue("DictModelObject", null);
        }
    }
    
    /**
     *
     * @return JButton
     */
    public JButton getHelpButton() {
        return bthelp;
    }

    /**
     *
     * @param e ActionEvent
     */
	public void actionPerformed(ActionEvent e) {
		try {
			String colid = cmd.getColid();
			ClientDataSet cds = model.getDataSet();
			String colval = cds.getRowSet().getString(colid, "");
			// 显示窗口
			TextEditDialog dlg = new TextEditDialog(EAI.EA.getMainWindow(), "编辑器", true);
			dlg.setValue(colval);
			dlg.setSize(600, 500);
			dlg.CenterWindow();
			dlg.setVisible(true);
			if(dlg.Result == dlg.RESULT_CANCEL){
				return;
			}			
			//点确定后赋值
			colval = dlg.getValue();
			text.setText(colval);
//			DictUtil.setVariantValue(cds, colid, colval);
			cds.getRowSet().putString(colid, colval);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//    /**
//     *
//     * @return String
//     */
//    private String getServerName() {
//    	String colServerName = cmd.getExtAttriValue("COL_OPT", "=", ";", "ServerName");
//        if(colServerName != null && colServerName.trim().length() > 0){
//			return colServerName;
//        }else if(cmd.getString("ServerName","") != null && cmd.getString("ServerName","").trim().length() > 0){
//			return cmd.getString("ServerName","");
//        }else if (model != null){
//        	return model.getCdsParam().getServerName();
//        }
//        return "";
//    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     *
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.text && e.getClickCount() == 1 &&
            e.getButton() == e.BUTTON3)
            actionPerformed(null);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     *
     * @param e MouseEvent
     */
    public void mouseExited(MouseEvent e) {
    }



    /**
     * 获得焦点
     */
	public void focusGained(FocusEvent e) {

	}

	/**
	 * 失去焦点
	 */
	public void focusLost(FocusEvent e) {
		if (e.getSource() == this.text){
    		String colval = text.getText();
    		if(colval == null){
    			colval="";
    		}
			text.setText(colval);
			ClientDataSet cds = model.getDataSet();
			String colid = cmd.getColid();
//			DictUtil.setVariantValue(cds, colid, colval);
		}
	}

    /**
     * Requests that this Component get the input focus, and that this Component's top-level ancestor become the
     * focused Window.
     *
     * @todo Implement this java.awt.Component method
     */
    public void requestFocus() {
        this.text.requestFocus();
    }
}
