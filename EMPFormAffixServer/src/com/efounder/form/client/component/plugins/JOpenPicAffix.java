package com.efounder.form.client.component.plugins;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.efounder.eai.EAI;
import com.efounder.form.client.component.frame.JAffixUploadPanel;
import com.efounder.form.client.component.frame.JPICFrame;
import com.efounder.form.client.component.frame.PreViewImagePanel;
import com.efounder.form.client.component.pic.JPicDialog;
import com.efounder.form.client.component.pic.JPubFrame;

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
public class JOpenPicAffix  extends JOpenAffix {
    public JOpenPicAffix() {
    }
    
    /**
     * 在JFrame中打开
     */
    public void open(Object showOwner, Object res,String imageName,JPanel panel,String order)  throws Exception{
        PreViewImagePanel imagePanel = new PreViewImagePanel((JAffixUploadPanel) panel,order);
        JPubFrame  frame = new JPICFrame(imagePanel,imageName);
        frame.setPreferredSize(new Dimension(700,500));
        imagePanel.setParentFrame(frame);
//        imagePanel.callImagePreview(panel,res,imageName);
        frame.setCancelText("关闭");
        frame.setOKVisiable(false);
        frame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        //frss system need : send open dialog to second screen
        frame.setLocation(screenSize.width+(screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
//        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        //窗口最大化
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        if(panel != null){
            imagePanel.callImagePreview(((JAffixUploadPanel) panel).getTableModel(),
                                        ((JAffixUploadPanel) panel).getAffixDataList(),
                                        res, imageName);
        }else{
            imagePanel.callImagePreview(res, imageName);
        }
//        imagePanel.autoFitAction();
    }
    
    /**
     * 在JDialog中打开
     */
    public void open(Frame frame, String title, boolean modal, Object res,String imageName,JPanel panel,String order) throws Exception {
        JPicDialog pubDialog = new JPicDialog(EAI.EA.getMainWindow(),title, true, panel, imageName);
        PreViewImagePanel imagePanel = new PreViewImagePanel((JAffixUploadPanel) panel,order);
        if(panel != null){
            imagePanel.callImagePreview(((JAffixUploadPanel) panel).getTableModel(),
                                        ((JAffixUploadPanel) panel).getAffixDataList(),
                                        res, imageName);
        }else{
            imagePanel.callImagePreview(res, imageName);
        }
        imagePanel.setParentDlg(pubDialog);
        pubDialog.addPanelToDialog(imagePanel);
        pubDialog.setCancelText("关闭");
        pubDialog.setOKVisiable(false);
        pubDialog.setPreferredSize(new Dimension(800,620));
        pubDialog.pack();
        pubDialog.CenterWindow();
        pubDialog.setVisible(true);
    }
  }
