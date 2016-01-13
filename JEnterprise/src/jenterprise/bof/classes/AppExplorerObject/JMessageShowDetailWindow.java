package jenterprise.bof.classes.AppExplorerObject;

import java.io.*;
import java.net.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.eai.form.dal.interfaces.*;
import com.pansoft.swing.waiting.*;
import jbof.gui.window.classes.style.mdi.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import com.pansoft.pub.util.StringFunction;
import java.util.ResourceBundle;
import jservice.jdof.ssl.JSSLConnectionManager;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @version 1.0
 */
public class JMessageShowDetailWindow
    extends JBOFMDIChildWindow {

    static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  private String fileName;
    private String directory = "Files";
    private String separator = System.getProperty("file.separator");

    public JMessageShowDetailWindow(IRecord IR) {
        try {
            jbInit();
            prepare(IR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JMessageShowDetailWindow(String title,Object bytes) {
        this(title,bytes, null, null);
    }

    public JMessageShowDetailWindow(String title,Object bytes, String id, String link) {
        try {
            jbInit();
            prepare(title,bytes,link);
            fileName = id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void prepare(String title,Object bytes,String link) {
        // 标题
        titleLabel.setText(title);
        // 内容
        String texts = new String( (byte[]) bytes);
        textPane.setText(texts);
        // 附件
        if (link != null) {
            linkLabel.setText(link);
        }
    }

    private void prepare(String link) {
    }

    private void prepare(IRecord IR) {
        byte[] bytes = IR.Fields().Field("F_MESSOBJ").AsBytes();
        String texts = new String(bytes);
        textPane.setText(texts);
        String link = IR.Fields().Field("F_MESSLINK").AsString();
        linkLabel.setText(link);
        linkLabel.setForeground(Color.BLUE);
        fileName = IR.Fields().Field("F_ID").AsString();
    }

    JTextPane textPane = new JTextPane();
    JScrollPane mainScrollPane = new JScrollPane(textPane);
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel linkPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel linkLabel = new JLabel();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel2 = new JLabel();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel titleLabel = new JLabel();

    private void jbInit() throws Exception {
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        this.setLayout(borderLayout1);
        linkPanel.setLayout(flowLayout1);
        linkLabel.setFont(new java.awt.Font(res.getString("String_286"), Font.PLAIN, 15));
        linkLabel.setText("");
        flowLayout1.setAlignment(FlowLayout.CENTER);
        jLabel1.setFont(new java.awt.Font(res.getString("String_288"), Font.PLAIN, 15));
        jLabel1.setForeground(Color.black);
        jLabel1.setText(res.getString("String_289"));
        linkPanel.setPreferredSize(new Dimension(56, 30));
        jLabel2.setText("消息标题：");
        jPanel1.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        this.add(mainScrollPane, java.awt.BorderLayout.CENTER);
        this.add(linkPanel, java.awt.BorderLayout.SOUTH);
        linkPanel.add(jLabel1);
        linkPanel.add(linkLabel);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(jLabel2);
        jPanel1.add(titleLabel);
        linkLabel.setForeground(Color.BLUE);
        linkLabel.addMouseMotionListener(new JMouseListener());
        linkLabel.addMouseListener(new JMouseListener());
    }

    class JMouseListener
        extends MouseAdapter implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            if (e.getSource() == linkLabel) {
                linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                linkLabel.setCursor(Cursor.getDefaultCursor());
            }
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == linkLabel) {
                String link = linkLabel.getText();
                if (link != null && link.trim().length() > 0) {
                    showFileSaveChooser(link);
                }
            }
        }

        private void showFileSaveChooser(String link) {
            Frame frame = JActiveDComDM.MainApplication.MainWindow;
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setRequestFocusEnabled(true);
            chooser.setSelectedFile(new File(link));
            if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File selectFile = chooser.getSelectedFile();
                if (selectFile.exists()) {
                    if (JOptionPane.showConfirmDialog(frame, res.getString("String_291") + selectFile.toString() + res.getString("String_292"), res.getString("String_293"), JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                        selectFile.delete();
                        saveToLocal(link, selectFile);
                    }
                } else {
                    try {
                        selectFile.createNewFile();
                        saveToLocal(link, selectFile);
                    } catch (IOException ex) {
                    }
                }
            }
        }

        private void saveToLocal(final String link, final File selectFile) {
            Thread t = new Thread(new Runnable(){
                public void run() {
                    String resultString = "";
                    try {
                        JWaitingManager.beginWait(res.getString("String_295"));
                        JParamObject PO = JParamObject.Create();
                        String url = PO.GetValueByEnvName("STANDARD_SERVICE", "");
                        //如果没有取到标准地址,就用本地发布的程序地址
                        if ("".equals(url)) {
                            url = getLocalURL(); ;
                        }
                        String type = link.substring(link.lastIndexOf("."));
                        url = url.substring(0, url.lastIndexOf("/") + 1) + directory + "/" + fileName + type;
                        URL fileURL = new URL(url);
                        URLConnection conn;
                        if (JSSLConnectionManager.isSSLConnection(fileURL))
                            conn = (HttpsURLConnection) JSSLConnectionManager.getSSLConnection(fileURL);
                        else
                            conn = (HttpURLConnection) fileURL.openConnection();

                        InputStream inptstream = conn.getInputStream();
                        FileOutputStream fos = new FileOutputStream(selectFile);
                        byte[] buf = new byte[1024];
                        int len = inptstream.read(buf, 0, 1024);
                        while (len != -1) {
                            fos.write(buf, 0, len);
                            len = inptstream.read(buf, 0, 1024);
                        }
                        fos.flush();
                        fos.close();
                        resultString = res.getString("String_302") + selectFile.toString();
                    } catch (Exception ex) {
                        selectFile.delete();
                        resultString = res.getString("String_303") + ex.getMessage();
                        ex.printStackTrace();
                    } finally {
                        JWaitingManager.endWait();
                        JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, resultString, res.getString("String_304"), JOptionPane.OK_CANCEL_OPTION);
                    }
                }
            });
            t.start();
        }

        private String getLocalURL() {
          String url = JActiveDComDM.CodeBase;
          url = StringFunction.replaceString(url,"Codebase/","");
//            JParamObject PO = JParamObject.Create();
//            JResponseObject RO;
//            PO.SetValueByParamName("CommBase", "");
//            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject", "getLocalURL", PO);
//            String rtn = (String) RO.ResponseObject;
//            return rtn;
          return url;
        }
    }

}
