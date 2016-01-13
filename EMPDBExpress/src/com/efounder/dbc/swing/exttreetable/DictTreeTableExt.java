package com.efounder.dbc.swing.exttreetable;

import com.efounder.dbc.swing.context.TreeInfoContext;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import java.util.ArrayList;
import com.efounder.comp.treetable.TreeTableModelAdapter;
import com.efounder.eai.ide.ExplorerIcons;
import javax.swing.tree.DefaultTreeCellRenderer;
import com.efounder.dbc.swing.render.DictTreeCellRender;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Rectangle;
import java.math.BigDecimal;
import javax.swing.tree.*;
import com.efounder.comp.treetable.*;
import java.util.*;
import com.efounder.dbc.swing.*;
import com.core.xml.*;
import com.borland.dx.dataset.*;



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
public class DictTreeTableExt extends JTreeTable {
    TreeTableNodeExt root;
     protected DictDataSet dds=null;
    public DictTreeTableExt() {
        root = new TreeTableNodeExt();
    }
    public void setColumnClass(DictTreeTableModelExt dtm){
        Hashtable colinfo=dds.getDictUseColInfo();
        String name;
        Object obj;
        for(int i=0;i<dds.getColumnCount();i++){
//          name=dds.getColumn(i).getColumnName();
//          if(colinfo.get(name)!=null){
//              StubObject dr=(StubObject)colinfo.get(name);
//              if(!dr.getString("COL_EDIT","1").equals("0")){
//                  obj=dr.getObject("COL_CLASS",null);
//                  if(obj!=null)
//                      dtm.setColumnClass(name,obj);
//              }
//          }
        }
 }
 public void createTree(){
  if(dds==null)return;
//  if(!dds.isOpen())
//    dds.setActiveAgent(true);
//   DataSetTreeNode root = new DataSetTreeNode();
  //add by lchong

  TreeInfoContext context=new TreeInfoContext();
  context.setBHColumn(dds.getBhCol());
  context.setMCColumn(dds.getMcCol());
  context.setJSColumn(dds.getJsCol());
  context.setMXColumn(dds.getMxCol());
  context.setBMStru(dds.getBmStru());
  context.setDataSet(dds);
  //modified by lchong
  initTreeTableModel(root,context);
//   DictTreeTableModel dtm = new DictTreeTableModel(context);
//   setModel(dtm);
//   setColumnClass(dtm);
//   setSwingInfo();
//   dtm.expandAll(getTree(),true);
  //modified end
}




public  void setDataSet(DataSet dataSet) {
//   if(dataSet instanceof DictDataSet && this.dds!=dataSet){
//     dds = (DictDataSet) dataSet;
//     createTree();
//   }
}

public DataSet getDataSet() {
//  return dds;
	return null;
}


    protected void initTreeTableModel(TreeNode root,TreeInfoContext context) {
        DictTreeTableModelExt dtm = new DictTreeTableModelExt(root);
        dtm.CreateTree(context);
        setModel(dtm);
        setColumnClass(dtm);
        setSwingInfo();
        this.addMouseListener(new DictTreeTableExtMouseAdapter(this));
        this.setShowGrid(true);//表格显示线
    }

    JTree jt = null;
    public void setSwingInfo() {
        jt = getTree();
        DictTreeCellRender dtcr = new DictTreeCellRender();
        jt.setCellRenderer(dtcr);
        jt.setRootVisible(false);//不让显示根节点
        jt.setShowsRootHandles(true);//显示线,否则不容易看出是否有子节点
        ((DefaultTreeCellRenderer) jt.getCellRenderer()).setOpenIcon((ExplorerIcons.ICON_REMOVE_ALL_CUSTOM_VIEW));
        ((DefaultTreeCellRenderer) jt.getCellRenderer()).setClosedIcon((ExplorerIcons.ICON_ADD_CUSTOM_VIEW));
        ((DefaultTreeCellRenderer) jt.getCellRenderer()).setLeafIcon((ExplorerIcons.ICON_CUSTOM_VIEW));

    }

    /**
     * 最初的方法
     * @return List
     */
    public List getSelData() {
        int[] rows = this.getSelectedRows();
        if (rows == null) {
            return null;
        }
        TreeTableModelAdapter dtm = (TreeTableModelAdapter)this.getModel();
        List retList = new ArrayList();
        for (int i = 0; i < rows.length; i++) {
            Object[] itemObj = new Object[this.getColumnCount()];
            for (int j = 0; j < this.getColumnCount(); j++) {
               Object o = dtm.getValueAt(rows[i], j);
               if(o instanceof BigDecimal){
                 int intVal=((BigDecimal)o).intValue();
                 itemObj[j] = ""+intVal;
               }
               else{
                 itemObj[j] = o;
               }
            }
            retList.add(itemObj);
        }
        return retList;
    }
    /**
     * 最新获取选择节点对象方法
     * @return List
     */
    public List getSelNodesData(){
      int[] rows = this.getSelectedRows();
      if (rows == null) {
          return null;
      }
      List retList = new ArrayList();
      TreePath path = null;
      for (int i = 0; i < rows.length; i++) {
        path = jt.getPathForRow(rows[i]);
        TreeTableNodeExt selObj = (TreeTableNodeExt)path.getLastPathComponent();
        retList.add(selObj);
      }
      return retList;
    }

    /**
     * 获取选择节点的编号
     * @return List
     */
    public List getSelNodesBH(){
      int[] rows = this.getSelectedRows();
      if (rows == null) {
          return null;
      }
      List retList = new ArrayList();
      TreePath path = null;
      for (int i = 0; i < rows.length; i++) {
        path = jt.getPathForRow(rows[i]);
        TreeTableNodeExt selObj = (TreeTableNodeExt)path.getLastPathComponent();
        retList.add(selObj.getDctBH());
      }
      return retList;
    }

    /**
    * 模糊查找
    * @param findBH String
    */
   public void findIillegibilityNode(String findBH,List findNodes){
       DictTreeTableModelExt treeModel = (DictTreeTableModelExt)jt.getModel();
       TreeTableNodeExt checkNode = (TreeTableNodeExt)treeModel.getTreeNode("#ROOT");
       findIillegibilityNode(findBH,checkNode,checkNode.getChildCount(),findNodes);
   }
   /**
    * 遍历模糊查找
    * @param findBH String
    * @param parentNode CheckTreeNode
    * @param childNum int
    * @param findNodes List
    */
   protected void findIillegibilityNode(String findBH,TreeTableNodeExt parentNode, int childNum,List findNodes){
       TreeTableNodeExt checkNode = parentNode;
       if (childNum > 0) { //有孩子
           for (int i = 0; i < childNum; i++) {
               TreeTableNodeExt cn = (TreeTableNodeExt) checkNode.getChildAt(i);
               String nodeBh = cn.getDctBH();
               String nodeMC = cn.getDctMc();
               if(nodeBh.toUpperCase().indexOf(findBH.toUpperCase())>-1 || nodeMC.toUpperCase().indexOf(findBH.toUpperCase())>-1){
                   if(!findNodes.contains(cn)){
                       findNodes.add(cn);
                   }
               }
               //如果该节点有儿子还继续
               if(cn.getChildCount()>0){
                   findIillegibilityNode(findBH, cn, cn.getChildCount(),findNodes);
               }
           }
       }
       String nodeBh = checkNode.getDctBH();
       String nodeMC = checkNode.getDctMc();
       if(nodeBh.indexOf(findBH)>-1 || nodeMC.indexOf(findBH)>-1){
         if(!findNodes.contains(checkNode)){
           findNodes.add(checkNode);
         }
       }
   }

   /**
     * 根据传递的编号查找某个节点
     * @param findBH String
     */
    public List findNode(String findBH){
        List retTreePathList = new ArrayList();
        DictTreeTableModelExt treeModel = (DictTreeTableModelExt)jt.getModel();
        TreeTableNodeExt checkNode = (TreeTableNodeExt)treeModel.getTreeNode("#ROOT");
        findNode(findBH,checkNode,checkNode.getChildCount(),retTreePathList);
        return retTreePathList;
    }
    /**
     * 根据传递的编号遍历查找某个节点
     * @param findBH String
     * @param parentNode CheckNode
     * @param childNum int
     */
    protected void findNode(String findBH,TreeTableNodeExt parentNode, int childNum,List retTreePathList){
        TreeTableNodeExt checkNode = parentNode;
        if (childNum > 0) { //有孩子
            for (int i = 0; i < childNum; i++) {
                TreeTableNodeExt cn = (TreeTableNodeExt) checkNode.getChildAt(i);
                String nodeBh = cn.getDctBH();
                String nodeMC = cn.getDctMc();
                if(nodeBh.equals(findBH) || nodeMC.equals(findBH)){
                    //设置光标覆盖状态: 添加在下面
                    jt.setSelectionPath(new TreePath(cn.getPath())); //选中该节点
                    retTreePathList.add(new TreePath(cn.getPath()));
                    return;
                }
                //如果该节点有儿子还继续
                if(cn.getChildCount()>0){
                    findNode(findBH, cn, cn.getChildCount(),retTreePathList);
                }
            }
        }
        String nodeBh = checkNode.getDctBH();
        if(nodeBh.equals(findBH)){
          //设置光标覆盖状态
           jt.setSelectionPath(new TreePath(checkNode.getPath())); //选中该节点
           retTreePathList.add(new TreePath(checkNode.getPath()));
            return ;
        }
    }

    public void treeMouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2 && e.getModifiers() == e.BUTTON1_MASK) {
          int row = this.getSelectedRow();
          TreePath path = jt.getPathForRow(row);
          if(path == null) return;
          boolean isOpen = jt.isCollapsed(path);
          if(!isOpen){
            jt.collapsePath(path);
          }
          else{
            jt.expandPath(path);
          }
        }
    }

    public void scrollRowToVisible(TreePath treePath){
      jt.setSelectionPath(treePath);
      int selRow = this.getSelectedRow();
      Rectangle rect = this.getCellRect(selRow, 0, true);
      this.scrollRectToVisible(rect);
    }
}



class DictTreeTableExtMouseAdapter extends MouseAdapter {
    private DictTreeTableExt adaptee;
    DictTreeTableExtMouseAdapter(DictTreeTableExt adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.treeMouseClicked(e);
    }
}
