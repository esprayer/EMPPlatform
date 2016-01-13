package com.efounder.dbc.swing.help;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.efounder.dbc.swing.context.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.pfc.dialog.*;
import com.efounder.eai.ide.*;
import com.efounder.dctbuilder.util.DictUtil;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.MetaData;
import com.help.HelpInfoContext;
import com.help.TreeTableHelpPanel;
import com.core.xml.StubObject;

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
public class DictHelpWindow extends JPDialog implements ActionListener{
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout bl1 = new BorderLayout();
    FlowLayout fl1=new FlowLayout();

    JPanel bottom=new JPanel();
    JPanel oppnel=new JPanel();
    DictMetaDataEx cmde=null;

    JButton btOk=new JButton("选择");
    JButton btCancel=new JButton("退出");
    TitledBorder titledBorder1 = new TitledBorder("");
    ColumnMetaData cmd;
    String bhcol = "";
    String mccol = "";

    Object selData;
    JPanel jPanel1 = new JPanel();
    JTextField findtxt = new JTextField();
    JButton btfind = new JButton();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel selText=new JLabel();
    // public DictHelpWindow(Frame frame, String title, boolean modal) {
      public DictHelpWindow(Frame frame) {
        super(frame, "帮助窗口", true);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        btOk.setMargin(new Insets(0, 0, 0, 0));
        btCancel.setMargin(new Insets(0, 0, 0, 0));
        jPanel1.setLayout(borderLayout2);
        btfind.setMargin(new Insets(0, 0, 0, 0));
        btfind.setText("查找");
        fl1.setHgap(0);
        fl1.setVgap(0);

        getContentPane().add(bottom,BorderLayout.SOUTH);
        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
        bottom.setLayout(bl1);
        bottom.add(selText,BorderLayout.CENTER);
        bottom.add(oppnel,BorderLayout.EAST);
        oppnel.setLayout(fl1);
        oppnel.add(btOk);
        oppnel.add(btCancel);
        jPanel1.add(findtxt, java.awt.BorderLayout.CENTER);
        jPanel1.add(btfind, java.awt.BorderLayout.EAST);
        btOk.setActionCommand("OK");
        btCancel.setActionCommand("CANCEL");
        btfind.setActionCommand("FIND");
        btOk.setIcon(ExplorerIcons.getExplorerIcon("beeload/ok.gif"));
        btCancel.setIcon(ExplorerIcons.getExplorerIcon("beeload/exit.gif"));
        btfind.setIcon(ExplorerIcons.getExplorerIcon("office/(32,24)"));
        btOk.addActionListener(this);
        btCancel.addActionListener(this);
        btfind.addActionListener(this);
    }
    IDictColHelp ich;
    public void initHelpObject(ColumnMetaData cmd,String value) {
        if(cmd.getHelpClass()==null){
            ich = new DictHelpPanel();
            ich.initObj(this, cmd);
        }else{
            String cls=cmd.getHelpClass();
            try {
                ich = (IDictColHelp) Class.forName(cls).newInstance();
                ich.initObj(this,value);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        getContentPane().add((JPanel)ich,BorderLayout.CENTER);
    }
      public void CenterWindow() {
          super.CenterWindow();
          if(ich instanceof TreeTableHelpPanel){
              ((TreeTableHelpPanel) ich).getTreeTable().requestFocus();
          }
      }
    public void initHelpObject(TreeInfoContext info){
        ich = new DictHelpPanel();
        ich.initObj(this, info);
        getContentPane().add((JPanel)ich,BorderLayout.CENTER);
    }
    public void initHelpObject(HelpInfoContext hic){
        ich=new TreeTableHelpPanel();
        ich.initObj(this,hic);
        //初始化事实表或者字典元数据
        this.initMetaData(hic);
        getContentPane().add((JPanel)ich,BorderLayout.CENTER);
    }

    /**
     * 初始化字典元数据，主要获取编号、名称列
     * @param hic HelpInfoContext
     */
    public void initMetaData(HelpInfoContext hic){
        DCTMetaData dctMeta = null;
        try {
            if (hic.getDCTID() == null || hic.getDCTID().trim().length() == 0) return;
            dctMeta = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(hic.getDCTID());
            if (dctMeta != null) {
                bhcol = dctMeta.getDCT_BMCOLID();
                mccol = dctMeta.getDCT_MCCOLID();
            }
        } catch (Exception ex) {
        }
    }

    public void setHelpData(Object o){

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("CANCEL"))
            super.OnCancel();
        if(e.getActionCommand().equals("OK")){
            selData=ich.getResult();
            super.OnOk();
        }
        if(e.getActionCommand().equals("FIND")){
            find();
        }
    }
    public JLabel getSelLabel(){
        return selText;
    }

    public Object getResultObject() {
        //兼容原来
        StubObject so = null;
        if (selData != null && selData instanceof EFRowSet) {
            so = DictUtil.getStubFromRowSet((EFRowSet) selData);
            so.setID(so.getString(bhcol, ""));
            so.setCaption(so.getString(mccol, ""));
            return so;
        }
        return selData;
    }


    public void find() {
        String txt= findtxt.getText();
        ich.findValue(txt);
    }
}
