package com.efounder.service.tree.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.dof.node.DCTTreeNode;
import com.efounder.eai.ide.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import java.util.List;
import java.util.ArrayList;
import com.efounder.dbc.swing.exttreetable.TreeTableNodeExt;
import com.efounder.dbc.swing.exttreetable.DictTreeTableModelExt;
import com.efounder.service.tree.model.TreeExModel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JFlowTreePanel extends JPanel implements TreeWillExpandListener,MouseListener,ActionListener,KeyListener {
  public JFlowTreePanel() {
      try {
        jInit();
        initTreeEvent();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
  }

  JTree tree = new JTree();
  JPanel panel = new JPanel();
  JTextField txtField = new JTextField(5);
  JCheckBox isIllegibilityBox = new JCheckBox();

  JPanel northInPanel = new JPanel();
  JButton queryBtn = new JButton();

  protected TreeManager treeManager = null;
  protected TreeNodeOpenEvent   openEvent = null;
  protected TreeNodeExecEvent   execEvent = null;
  protected TreeNodeActionEvent actionEvent = null;


  protected void jInit() throws Exception{
      this.setLayout(new BorderLayout());
      this.add(panel,BorderLayout.NORTH);
      this.add(tree,BorderLayout.CENTER);

      initNorPanel();
  }

  protected void initNorPanel(){
      northInPanel.setLayout(new BorderLayout());
      northInPanel.add(txtField,BorderLayout.CENTER);
      northInPanel.add(queryBtn,BorderLayout.EAST);

      txtField.addKeyListener(this);

      queryBtn.setToolTipText("����");
      Icon helpIcon = ExplorerIcons.getExplorerIcon("idea/actions/find.png");
      queryBtn.setIcon(helpIcon);
      queryBtn.addActionListener(this);
      queryBtn.setMargin(new Insets(0,0,0,0));

      panel.setLayout(new BorderLayout());
      panel.add(new JLabel("���/���"),BorderLayout.WEST);
      panel.add(northInPanel,BorderLayout.CENTER);
      panel.add(isIllegibilityBox,BorderLayout.EAST);
      panel.setBorder(new javax.swing.border.TitledBorder(""));

      isIllegibilityBox.setText("ģ��");
      isIllegibilityBox.setSelected(true);
  }


  /**
   *
   * @return TreeNodeOpenExecute
   */
  public TreeNodeActionEvent getTreeNodeActionEvent() {
    return actionEvent;
  }
  /**
   *
   * @param openExec TreeNodeOpenExecute
   */
  public void setTreeNodeActionEvent(TreeNodeActionEvent actionEvent) {
    this.actionEvent = actionEvent;
  }
  /**
   *
   * @return TreeNodeOpenExecute
   */
  public TreeNodeExecEvent getTreeNodeExecEvent() {
    return execEvent;
  }
  /**
   *
   * @param openExec TreeNodeOpenExecute
   */
  public void setTreeNodeExecEvent(TreeNodeExecEvent execEvent) {
    this.execEvent = execEvent;
  }
  /**
   *
   * @return TreeNodeOpenExecute
   */
  public TreeNodeOpenEvent getTreeNodeOpenEvent() {
    return openEvent;
  }
  /**
   *
   * @param openExec TreeNodeOpenExecute
   */
  public void setTreeNodeOpenExecute(TreeNodeOpenEvent openEvent) {
    this.openEvent = openEvent;
  }

  public void setTreeManager(TreeManager treeManager) {
    this.treeManager = treeManager;
  }
  /**
   *
   * @return TreeManager
   */
  public TreeManager getTreeManager() {
    return treeManager;
  }

  public JTree getTree() {
    return tree;
  }

  protected void initTreeEvent() {
    tree.addTreeWillExpandListener(this);
    tree.addMouseListener(this);
  }

  /**
   * �������ϵļ�¼
   */
  protected void onFind(){
      String findText = txtField.getText();
      if (isIllegibilityBox.isSelected()) {
       //ģ�����
       onFindStepByStep();
     }
     else {
       //��ȷ����
       onFind(findText);
     }
  }

  //�����ҵĴ���(��Ӧ��ģ���ѯ),���ٴ�����Ҫ��ѯ�ı�Ż������ʱ,����Ϊ0
  int clickFindIndex = 0;
  List findNodes = null;

  protected void onFind(String findVal) {
    TreePath treePath = null;
    List retTPList = findNode(tree,findVal);
    if(retTPList != null && retTPList.size() > 0){
      treePath = (TreePath)retTPList.get(0);
    }
    if(treePath != null){
//      tree.scrollRowToVisible(treePath);
      tree.scrollPathToVisible(treePath);
    }
  }

  private void onFindStepByStep() {
    TreePath treePath = null;
    if (clickFindIndex == 0) { //��һ�ε����Ұ�ť
      findNodes = new ArrayList();
      onIllegibilityFind(findNodes);
      if (findNodes != null && findNodes.size() > 0) {
        DCTTreeNode checkNode = (DCTTreeNode) findNodes.get(0);
        List retTPList = findNode(tree,checkNode.getDctBH());
        if(retTPList != null && retTPList.size() > 0){
          treePath = (TreePath)retTPList.get(0);
        }
        clickFindIndex++;
      }
    }
    else {
      if (findNodes != null) {
        if (clickFindIndex < findNodes.size()) {
          DCTTreeNode checkNode = (DCTTreeNode) findNodes.get(
              clickFindIndex);
          List retTPList = findNode(tree,checkNode.getDctBH());
          if(retTPList != null && retTPList.size() > 0){
              treePath = (TreePath)retTPList.get(0);
          }
        }
        clickFindIndex++;
        if (clickFindIndex >= findNodes.size()) {
          clickFindIndex = 0;
        }
      }
    }
    if(treePath != null){
      tree.scrollPathToVisible(treePath);
    }
  }

  private void onIllegibilityFind(List findNodes) {
    String findBh = this.txtField.getText();
    findIillegibilityNode(tree,findBh, findNodes);
  }


  /**
    * ģ�����
    * @param findBH String
    */
   public void findIillegibilityNode(JTree generalTree,String findBH,List findNodes){
       TreeExModel treeModel = (TreeExModel)generalTree.getModel();
       TreeNode rootNode = (TreeNode)treeModel.getRoot();
       int childCount = rootNode.getChildCount();
       if(childCount == 0) return;
       DCTTreeNode treeRootNode = (DCTTreeNode)rootNode.getChildAt(0);
       findIillegibilityNode(generalTree,findBH,treeRootNode,treeRootNode.getChildCount(),findNodes);
   }
   /**
    * ����ģ�����
    * @param findBH String
    * @param parentNode CheckTreeNode
    * @param childNum int
    * @param findNodes List
    */
   protected void findIillegibilityNode(JTree generalTree,String findBH,DCTTreeNode parentNode, int childNum,List findNodes){
       DCTTreeNode checkNode = parentNode;
       if (childNum > 0) { //�к���
           for (int i = 0; i < childNum; i++) {
               DCTTreeNode cn = (DCTTreeNode) checkNode.getChildAt(i);

               String nodeBh = cn.getDctBH();
               String nodeMC = cn.getDctMc();
               if(nodeBh.toUpperCase().indexOf(findBH.toUpperCase())>-1 || nodeMC.toUpperCase().indexOf(findBH.toUpperCase())>-1){
                   if(!findNodes.contains(cn)){
                       findNodes.add(cn);
                   }
               }
               //���ýڵ��ж��ӻ�����
               if(cn.getChildCount()>0){
                   findIillegibilityNode(generalTree,findBH, cn, cn.getChildCount(),findNodes);
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
     * ��ݴ��ݵı�Ų���ĳ��ڵ�
     * @param findBH String
     */
    public List findNode(JTree generalTree,String findBH){
        List retTreePathList = new ArrayList();
        TreeExModel treeModel = (TreeExModel)generalTree.getModel();
        TreeNode rootNode = (TreeNode)treeModel.getRoot();
        int childCount = rootNode.getChildCount();
        if(childCount == 0) return null;
        DCTTreeNode treeRootNode = (DCTTreeNode)rootNode.getChildAt(0);

        findNode(generalTree,findBH,treeRootNode,treeRootNode.getChildCount(),retTreePathList);
        return retTreePathList;
    }
    /**
     * ��ݴ��ݵı�ű������ĳ��ڵ�
     * @param findBH String
     * @param parentNode CheckNode
     * @param childNum int
     */
    protected void findNode(JTree generalTree,String findBH,DCTTreeNode parentNode, int childNum,List retTreePathList){
        DCTTreeNode checkNode = parentNode;
        if (childNum > 0) { //�к���
            for (int i = 0; i < childNum; i++) {
                DCTTreeNode cn = (DCTTreeNode) checkNode.getChildAt(i);
                String nodeBh = cn.getDctBH();
                String nodeMC = cn.getDctMc();
                if(nodeBh.equals(findBH) || nodeMC.equals(findBH)){
                    //���ù�긲��״̬: ���������
                    generalTree.setSelectionPath(new TreePath(cn.getPath())); //ѡ�иýڵ�
                    retTreePathList.add(new TreePath(cn.getPath()));
                    return;
                }
                //���ýڵ��ж��ӻ�����
                if(cn.getChildCount()>0){
                    findNode(generalTree,findBH, cn, cn.getChildCount(),retTreePathList);
                }
            }
        }
        String nodeBh = checkNode.getDctBH();
        if(nodeBh.equals(findBH)){
          //���ù�긲��״̬
           generalTree.setSelectionPath(new TreePath(checkNode.getPath())); //ѡ�иýڵ�
           retTreePathList.add(new TreePath(checkNode.getPath()));
            return ;
        }
    }




  /**
   *
   * @param event TreeExpansionEvent
   * @throws ExpandVetoException
   */
  public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

  }
  /**
   *
   * @param event TreeExpansionEvent
   * @throws ExpandVetoException
   */
  public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
    try {
      treeManager.expandTreeNode((ModelTreeNode)event.getPath().getLastPathComponent());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    if ( ( e.getModifiers() & e.BUTTON3_MASK ) != 0 ) {
      processActionEvent();
    }
    if ( ( e.getModifiers() & e.BUTTON1_MASK ) != 0 && e.getClickCount() == 2 ) {
      processOpenExecEvent();
    }
  }
  /**
   *
   */
  protected void processActionEvent() {

  }
  /**
   *
   */
  protected void processOpenExecEvent() {
    TreePath tp = tree.getSelectionPath();
    if ( tp == null ) return;
    ModelTreeNode treeNode = (ModelTreeNode)tp.getLastPathComponent();
    if ( openEvent != null ) openEvent.openTreeNode(tree,treeNode);
    if ( execEvent != null ) execEvent.execTreeNode(tree,treeNode);
  }
  /**
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
   *
   * @param e MouseEvent
   */
  public void mousePressed(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
  }

  public void actionPerformed(ActionEvent e) {
    onFind();
  }

  public void keyTyped(KeyEvent e) {
    if (e.getSource() == txtField) {
      //���س������
      if (e.getKeyChar() == '\n') {
        String findVal = txtField.getText();
        if (isIllegibilityBox.isSelected()) { //ģ�����
          onFindStepByStep();
        }
        else {
          onFind(findVal);
        }
      }
      else{
        clickFindIndex = 0;
      }
    }

  }

  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

}
