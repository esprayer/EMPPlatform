package com.help;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.buffer.*;
import com.efounder.comp.treetable.*;
import com.efounder.dbc.swing.context.*;
import com.efounder.dbc.swing.help.DictHelpWindow;
import com.efounder.dbc.swing.help.IDictColHelp;
import com.efounder.dbc.swing.tree.*;
import com.efounder.dbc.swing.treetable.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;

import org.openide.*;
import com.efounder.builder.base.data.*;

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
public class TreeTableHelpPanel extends JPanel
        implements IDictColHelp,MouseListener,Runnable,KeyListener{
    JScrollPane jsp = new JScrollPane();
    BaseTreeTable tree = new BaseTreeTable();
    JParamObject PO =null;
    HelpInfoContext helpContext;
    JLabel selLabel=null;
    Container parent;
    public TreeTableHelpPanel() {
        PO=JParamObject.Create();
        jbInit();
    }
    public BaseTreeTable getTreeTable(){
        return tree;
    }
    private void jbInit() {
        this.setBackground(SystemColor.control);
        this.setLayout(borderLayout1);
        this.add(jsp, BorderLayout.CENTER);
        jsp.getViewport().add(tree);
        jsp.setPreferredSize(new Dimension(450, 500));
        tree.addMouseListener(this);
        tree.addKeyListener(this);
    }

    BorderLayout borderLayout1 = new BorderLayout();


    protected String getModelKey(HelpInfoContext hic){
        String zrzx=hic.getZrzx();
//        if(zrzx==null)zrzx=ParameterManager.getDefault().getZRZX(PO);
        String key=ParameterManager.getDefault().getUserName(PO)+"-"+zrzx+"-"+hic.getDCTID();
        key+="-"+ParameterManager.getDefault().getUNIT_ID(PO);//公司代码
        if(hic.getWhere()!=null)
            key+="-"+hic.getWhere().toString();
        if(hic.getSXCol()!=null)
            key+="-"+hic.getSXCol()+"="+hic.getSXBM();
        return key;
    }
    protected void initFormContext(HelpInfoContext hic) {
        String key=getModelKey(hic);
        EFDataSetTreeTableModel ltm=(EFDataSetTreeTableModel) DictDataBuffer.getDefault().getDataItem(HelpInfoContext.HELPTREEMODEL,key);
        helpContext = hic;
        if(ltm==null){
            helpContext.setCurBM(""); ;
            helpContext.setCurJS("0");
           EFDataSet list=(EFDataSet) hic.getObject(DctConstants.ALLDATA,null);
            if(list==null)
                list= DictUtil.getHelpDataSet(helpContext,PO);
            if (list != null) {
                TreeInfoContext info=new TreeInfoContext();
                info.setDictInfo(helpContext.getDictInfo());
                String[] bhs = helpContext.getHelpColumn();
                String[] mcs = helpContext.getHelpCapiton();
                info.setDataObject(list);
                ltm = EFDataSetTreeTableModel.getInstance(info, bhs, mcs);
                if(hic.isBufData())
                    DictDataBuffer.getDefault().addDataItem(HelpInfoContext.HELPTREEMODEL,key,ltm);
            }
        }else{
          if(hic.getBHColumn().trim().equals("")){
            TreeInfoContext ct=ltm.getTreeInfoContext();
            if(ct!=null)
            hic.setDictInfo(ct.getDictInfo());
          }
        }
        tree.setModel(ltm);
        selExistValue(hic);
        if(tree.getSelectedRow()==-1){
            tree.getSelectionModel().setSelectionInterval(0, 0);
        }
//        if (hic.isMulSel())
//            tree.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        else
//          tree.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
    protected void selExistValue(HelpInfoContext hc){
        String value=hc.getSelValue();
        if(value==null)return;
        if(hc.getJSColumn().trim().length()>0&&hc.isGradeQuery()){
            String stru = hc.getDictInfo().getDicBHStruct();
            int len = 0;
            for (int i = 0; i < stru.length(); i++) {
                len += Integer.parseInt(stru.substring(i, i + 1));
                if (len >= value.length())
                    break;
                String str=value.substring(0,len);
                TreePath path=TreeTableUtils.findValue(tree,str);
                if(path==null)return;
                loadChild((DataSetTreeNode)path.getLastPathComponent());
            }
        }
        TreeTableUtils.findValue(tree,value);
        //SwingUtilities.invokeLater(new fuckFind(tree,value));

    }
    public class fuckFind implements Runnable{
        JTreeTable jtt;
        String fvalue;
        public fuckFind(JTreeTable jt,String value){
            jtt=jt;
            fvalue=value;
        }
        public void run() {
            TreeTableUtils.findValue(jtt,fvalue);
        }

    }


    public void initObj(Container parent, Object o) {
        initFormContext((HelpInfoContext)o);
        if(parent instanceof DictHelpWindow){
            selLabel=((DictHelpWindow)parent).getSelLabel();
        }
        this.parent=parent;
    }
    public Object getResult() {
        if(helpContext.isMulSel()){
            List list=new ArrayList();
            int[] rows = tree.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                TreePath path = tree.getTree().getPathForRow(rows[i]);
                if (path != null) {
                    DataSetTreeNode dstn = (DataSetTreeNode) path.
                                           getLastPathComponent();
                    list.add(dstn.getUserObject());
                }
            }
            return list;
        }else{
            int row = tree.getSelectedRow();
            TreePath path = tree.getTree().getPathForRow(row);
            if (path != null) {
                DataSetTreeNode dstn = (DataSetTreeNode) path.
                                       getLastPathComponent();
                return dstn.getUserObject();
            } else
                return null;
        }
    }
    protected void loadChild(DataSetTreeNode dsn){
      if(!helpContext.isGradeQuery())return;
        String bh=dsn.getDctBH();
        if(dsn.getDctMx().equals("1")){
                return;
        }else{
            helpContext.setCurBM(dsn.getDctBH());
            helpContext.setCurJS(dsn.getDctJs());
        }
        String key=this.getModelKey(helpContext);
        EFDataSetTreeTableModel ltm=(EFDataSetTreeTableModel) DictDataBuffer.getDefault().getDataItem(HelpInfoContext.HELPTREEMODEL,key);
        if(ltm==null)return;
        key=helpContext.getDCTID()+"."+bh;
        if("1".equals(ltm.getString(key)))return;//下级有没有LOAD过
        EFDataSet list=DictUtil.getHelpDataSet(helpContext,PO);
        ltm.addDataSetData(list,null,dsn);
        ltm.setString(key,"1");
    }

    public void run() {
        JTree jt=tree.getTree();
        int row = tree.getSelectedRow();
        TreePath path = jt.getPathForRow(row);
        if (path == null)
            return;
        DataSetTreeNode dsn = (DataSetTreeNode) path.getLastPathComponent();
        if(dsn.isLeaf()){
            ActionEvent ae=new ActionEvent(this,0,"OK");
            ((ActionListener)parent).actionPerformed(ae);
        }
        else{
            loadChild(dsn);
            tree.getTree().expandPath(path);
        }
    }

    protected void listChild(TreePath path) {
        JTree jt=tree.getTree();
        boolean isOpen = jt.isExpanded(path);
        if (isOpen) {
            jt.collapsePath(path);
        } else {
            if (com.efounder.eai.EAI.EA.getMainWindow() != null)
                WaitingManager.getDefault().waitInvoke("信息",
                        "正在读取下级数据，请等待。。。。。。", null, this);
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    run();
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }

    }
    public void mouseClicked(MouseEvent e) {
        JTree jt=tree.getTree();
        int row = tree.getSelectedRow();
        TreePath path = jt.getPathForRow(row);
        if (path == null)
            return;
        if(e.getClickCount()==1){
            DataSetTreeNode dsn = (DataSetTreeNode) path.getLastPathComponent();
            if(selLabel!=null)
             selLabel.setText(dsn.getDctBH()+" "+dsn.getDctMc());
        }
        if (e.getClickCount() == 2 && e.getModifiers() == e.BUTTON1_MASK||
            TreeUtils.isLocationInExpandControl(jt,path,e.getX(),e.getY())&&e.getClickCount() == 1 && e.getModifiers() == e.BUTTON1_MASK)
            listChild(path);
        if (e.getClickCount() == 1 && e.getModifiers() == e.BUTTON3_MASK){
            ActionEvent ae = new ActionEvent(this, 0, "LAST");
            ((ActionListener) parent).actionPerformed(ae);
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void findValue(String value) {
        TreeTableUtils.findValue(tree,value);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JTree jt=tree.getTree();
            int row = tree.getSelectedRow();
            TreePath path = jt.getPathForRow(row);
            if(path==null)return;
            this.listChild(path);
            e.setKeyCode(0);
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
