package com.help.dialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;

import org.openide.*;
import com.efounder.eai.EAI;
import com.efounder.comp.*;
import com.help.plugin.IHelpAdapter;

import javax.swing.tree.ExpandVetoException;

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
public class HelpTabPanel extends JPanel implements ActionListener
        ,Runnable,TreeSelectionListener,PopupComponent,TreeWillExpandListener{
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane jSplitPane1 = new JSplitPane();
    JSplitPane jSplitPane2 = new JSplitPane();
    HelpContentPanel hcp=new HelpContentPanel();
    HelpFilterPanel hfp=new HelpFilterPanel();
    HelpExplorePanel hep=new HelpExplorePanel();
    IHelpAdapter     adapter;
    public HelpTabPanel(IHelpAdapter adapter) {
        this.adapter=adapter;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public HelpExplorePanel getExplorePanel(){
        return hep;
    }
    public HelpContentPanel getContentPanel(){
            return hcp;
    }
    public HelpFilterPanel getFilterPanel(){
           return hfp;
   }

    public void setNoExplorer(){
        jSplitPane1.setLeftComponent(null);
        remove(jSplitPane1);
        //add by lchong:位置不0,客户嫌不舒服
        jSplitPane2.setDividerLocation(0);
        //add end
        add(jSplitPane2,java.awt.BorderLayout.CENTER);
    }
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOneTouchExpandable(true);
        BasicSplitPaneDivider bspd=((BasicSplitPaneUI)jSplitPane2.getUI()).getDivider();
        jSplitPane1.setRightComponent(jSplitPane2);
        this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.add(hep,JSplitPane.LEFT);
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.add(jSplitPane2, JSplitPane.RIGHT);
        jSplitPane2.add(hcp,JSplitPane.BOTTOM);
        jSplitPane2.add(hfp,JSplitPane.TOP);
        hep.getExploreTree().addTreeSelectionListener(this);
        hep.getExploreTree().addTreeWillExpandListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e==null||e.getSource() instanceof JComboBox)

//            WaitingManager.getDefault().waitInvoke(EAI.EA==null?this:null,"信息",
//                        "正在读取帮助数据，请等待。。。。。。", null, this);
        	run();
    }
    boolean bFirst=true;
    public boolean isFirstRun(){
        return bFirst;
    }
    public void run() {
        try{
            adapter.loadExploreData(this);
            if (bFirst) {
//                BasicSplitPaneDivider bspd = ((BasicSplitPaneUI) jSplitPane2.
//                                              getUI()).getDivider();
//                ((JButton) bspd.getComponent(0)).getActionListeners()[0].
//                        actionPerformed(null);
                jSplitPane2.setDividerLocation(0);
                hcp.getContentTable().requestFocus();
                bFirst = false;
            }
        }finally{
            brefrens=false;
        }
    }

    public void setSplitLocation(int pos){
        jSplitPane2.setDividerLocation(pos);
    }
    public void valueChanged(TreeSelectionEvent e) {
        adapter.loadContentData(this);
    }

    public JComponent getJComponent() {
        return this;
    }

    public void setSelectedItem(Object item) {
    }

    public Object getSelectedItem() {
        return null;
    }

    public ListCellRenderer getListCellRenderer() {
        return null;
    }
    public void setJComboBox(JComboBox box) {

    }

    public void setComboPopup(ComboPopup comboPopup) {
    }
    boolean brefrens=false;
    public void refrenshData(){
        brefrens=true;
//        WaitingManager.getDefault().waitInvoke(EAI.EA==null?this:null,"信息",
//                        "正在刷新帮助数据，请等待。。。。。。", null, this);
        run();
    }
    public boolean isRefrensh(){
        return brefrens;
    }
    public boolean isNotUseScrollPane() {
        return false;
    }

    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        hep.getExploreTree().setSelectionPath(event.getPath());
    }

    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
    }
}
