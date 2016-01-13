package jenterprise.bof.classes.AppExplorerObject;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import com.pansoft.swing.history.*;
import jfoundation.gui.window.classes.*;
import jtoolkit.htmleditor.*;
import jframework.foundation.classes.JActiveDComDM;
import com.pansoft.swing.waiting.JWaitingManager;
import java.util.ResourceBundle;

/**
 * 消息录入窗口.
 * @version 1.0
 */
public class JMessageInputDialog
    extends JFrameDialog implements ActionListener {

    static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  public int OPTION_OK = 1;
    public int OPTION_CANCEL = 2;
    public int OPTION;
    private String title;
    private String htmlContent;
    private String affixType;
    private String affixName = "";
    private byte[] affixByte;

    private static JHTMLEditor editor = new JHTMLEditor();

    public JMessageInputDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(verticalFlowLayout1);
        northPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(0);
        cancelButton.setPreferredSize(new Dimension(80, 22));
        cancelButton.setText(res.getString("String_153"));
        okButton.setPreferredSize(new Dimension(80, 22));
        okButton.setMnemonic('O');
        okButton.setText(res.getString("String_154"));
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        cancelButton.setMnemonic('C');
        jLabel1.setText(res.getString("String_155"));
        titleTextField.setPreferredSize(new Dimension(400, 22));
        editor.setBorder(BorderFactory.createEtchedBorder());
        editor.setMinimumSize(new Dimension(406, 100));
        editor.setPreferredSize(new Dimension(460, 280));
        northPanel.setBorder(null);
        affixPanel.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(0);
        jLabel2.setText(res.getString("String_156"));
        affixTextField.setPreferredSize(new Dimension(325, 22));
        affixTextField.setEditable(false);
        affixTextField.setText("");
        browseButton.setPreferredSize(new Dimension(75, 22));
        browseButton.setMnemonic('B');
        browseButton.setText(res.getString("String_158"));
        browseButton.addActionListener(this);
        cmdPanel.add(okButton);
        cmdPanel.add(cancelButton);
        this.getContentPane().add(northPanel, null);
        this.getContentPane().add(editor, null);
        this.getContentPane().add(affixPanel);
        affixPanel.add(jLabel2);
        affixPanel.add(affixTextField);
        affixPanel.add(browseButton);
        this.getContentPane().add(cmdPanel);
        northPanel.add(jLabel1);
        northPanel.add(titleTextField);
    }

    JPanel northPanel = new JPanel();
    JPanel cmdPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JLabel jLabel1 = new JLabel();
    HistoryTextField titleTextField = new HistoryTextField();
    JPanel affixPanel = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel jLabel2 = new JLabel();
    JTextField affixTextField = new JTextField();
    JButton browseButton = new JButton();

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            processBrowse();
        } else if (e.getSource() == okButton) {
            processOK();
        } else if (e.getSource() == cancelButton) {
            processCancel();
        }
    }

    private void processBrowse() {
        Thread thread = new Thread(new Runnable(){
            Frame frame = JActiveDComDM.MainApplication.MainWindow;
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File selectFile = fileChooser.getSelectedFile();
                    if (selectFile != null) {
                        String filePath = selectFile.getPath();
                        affixTextField.setText(filePath);
                        affixType = filePath.substring(filePath.lastIndexOf("."));
                        affixName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                        try {
                            JWaitingManager.beginWait(res.getString("String_161"));
                            FileInputStream fis = new FileInputStream(selectFile);
                            affixByte = new byte[ (int) selectFile.length()];
                            fis.read(affixByte);
                            fis.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }finally{
                            JWaitingManager.endWait();
                        }
                    }
                }
            }
        });
        thread.start();
    }

    private void processOK() {
        this.OPTION = this.OPTION_OK;
        title = titleTextField.getText();
        htmlContent = editor.textPane.getText();
        this.dispose();
    }

    public String getTitleString() {
        return title;
    }

    public void setTitleString(String str) {
        title = str;
        titleTextField.setText(title);
    }

    public Object getHTMLContent() {
        return htmlContent;
    }

    public void setHTMLContent(byte[] bytes) {
        if (bytes != null) {
            htmlContent = new String(bytes);
            editor.textPane.setText(htmlContent);
        }
    }

    public byte[] getAffixByte() {
        return affixByte;
    }

    public String getAffixType(){
        return affixType;
    }

    private void processCancel() {
        this.OPTION = this.OPTION_CANCEL;
        this.dispose();
    }

    public String getAffixName() {
        return affixName;
    }

    public void setAffixName(String name){

    }

}
