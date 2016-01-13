package com.efounder.dbc.swing.editor.dlg;

import com.efounder.dbc.swing.help.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.pfc.dialog.*;
import java.awt.Frame;
import java.awt.BorderLayout;
import com.efounder.form.client.component.pic.TransPanel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.*;
import com.efounder.eai.EAI;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

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
public  class ImageDialog extends JPDialog implements ActionListener{
    TransPanel imagPanel=new TransPanel();
    public ImageDialog() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ImageDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
        private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        btCancel.setText("取消");
        btOk.setText("确定");
        btFile.setText("选择文件");
        this.getContentPane().add(imagPanel,BorderLayout.CENTER);
        this.getContentPane().add(jPanel1,BorderLayout.SOUTH);
        jPanel1.add(btFile);
        jPanel1.add(btOk);
        jPanel1.add(btCancel);
        btOk.addActionListener(this);
        btCancel.addActionListener(this);
        btFile.addActionListener(this);
    }
    public void setRead(){
        btFile.setVisible(false);
        btCancel.setVisible(false);
    }
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton btCancel = new JButton();
    JButton btOk = new JButton();
    JButton btFile = new JButton();
    public void showImage(Object bt){
        imagPanel.loadPicRes(bt);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btOk){
            this.OnOk();
        }
        if(e.getSource()==btCancel){
            image=null;
            this.OnCancel();
        }
        if(e.getSource()==btFile){
            JFileChooser f = new JFileChooser();
           f.setFileFilter(new FileFilter() {
               public boolean accept(File f) {
                   String name = f.getName();
                   return name.endsWith("jpg")||f.isDirectory()||name.endsWith("gif")||
                           name.endsWith("png")||name.endsWith("bmp");
               }
               public String getDescription() {
                   return "";
               }
           });
           int aa = f.showOpenDialog(EAI.EA.getMainWindow());
           if (aa == f.CANCEL_OPTION)
               return;
           String file = f.getSelectedFile().getAbsolutePath();
           type=file.substring(file.length()-3).toLowerCase();
           imagPanel.loadImage(file);
           image=imagPanel.getBufImage();
           bchang=true;
        }
    }
    public byte[] getImageBytes() throws Exception {
        if (image == null)
            return null;
        ByteArrayOutputStream bot = new ByteArrayOutputStream();
        image.getType();
        ImageIO.write(image, type, bot);
        byte[] b = null;
        b = bot.toByteArray();
        return b;
    }

    public BufferedImage image = null;
    boolean bchang = false;
    String type = "";
}
