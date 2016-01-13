package com.core.update;

import com.borland.jbcl.layout.BoxLayout2;
import com.borland.jbcl.layout.VerticalFlowLayout;
import com.core.core.FileUtils;
import com.core.tree.CheckNode;
import com.core.tree.CheckRenderer;
import com.core.tree.StubObjectTreeModel;
import com.core.tree.StubObjectTreeNode;

import esyt.framework.com.core.xml.JDOMXMLBaseObject;
import esyt.framework.com.core.xml.StubObject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Calendar;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class Setp2Form extends JPanel
  implements ActionListener, TreeSelectionListener
{
  JPanel pnRemote = new JPanel();
  CardLayout cardLayout = new CardLayout();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JScrollPane jScrollPane3 = new JScrollPane();
  JEditorPane epDes = new JEditorPane();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JLabel jLabel3 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel edRemoteNewVer = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel edRemoteOldVer = new JLabel();
  JTree treeUpdateList = new JTree();

  StubObject updateCenterStubObject = null;
  UpdateStub rootUpdateStub = null;
  StubObject SO = null;
  Hashtable hashList = null;
  JDOMXMLBaseObject XMLObject = null;
  String REMOTE = "1";

  JTree treePublicUpdateList = null;
  JLabel jLabel13 = new JLabel();
  JPanel pnLocal = new JPanel();
  JButton bnBrowser = new JButton();
  JLabel edRemoteNewDate = new JLabel();
  String Application = null;
  String Product = null;

  public Setp2Form(String Application, String Product) {
    try { this.Application = Application;
      this.Product = Product;

      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    setLayout(this.cardLayout);
    this.pnRemote.setLayout(this.boxLayout21);
    this.boxLayout21.setAxis(1);
    this.jPanel2.setLayout(this.borderLayout1);
    this.jPanel1.setLayout(this.boxLayout22);
    this.jPanel3.setLayout(this.borderLayout2);
    this.jLabel1.setText("可用安装模块：");
    this.jPanel4.setLayout(this.borderLayout4);
    this.jButton1.setEnabled(false);
    this.jButton1.setText("模块帮助");
    this.jButton2.setEnabled(false);
    this.jButton2.setText("模块信息");
    this.jPanel6.setLayout(this.verticalFlowLayout1);
    this.jLabel3.setText("新版本：");
    this.jPanel7.setLayout(this.flowLayout1);

    this.flowLayout1.setAlignment(0);
    this.jLabel4.setText("旧版本：");

    this.epDes.setEditable(false);
    this.jLabel13.setText("更新日期：");

    this.bnBrowser.setMinimumSize(new Dimension(71, 26));
    this.bnBrowser.setMnemonic('B');
    this.bnBrowser.setText("浏览(B)");
    this.edRemoteNewDate.setText("jLabel2");
    add(this.pnRemote, "pnRemote");
    this.pnRemote.add(this.jPanel1, null);
    this.jPanel1.add(this.jPanel3, null);
    this.jPanel3.add(this.jLabel1, "North");
    this.jPanel3.add(this.jScrollPane1, "Center");
    this.jPanel3.add(this.pnLocal, "East");
    this.pnLocal.add(this.bnBrowser, null);
    this.jScrollPane1.getViewport().add(this.treeUpdateList, null);
    this.pnRemote.add(this.jPanel2, null);
    this.jPanel2.add(this.jPanel4, "North");
    this.jPanel4.add(this.jPanel7, "Center");
    this.jPanel7.add(this.jLabel3, null);
    this.jPanel7.add(this.edRemoteNewVer, null);
    this.jPanel7.add(this.jLabel4, null);
    this.jPanel7.add(this.edRemoteOldVer, null);
    this.jPanel7.add(this.jLabel13, null);
    this.jPanel7.add(this.edRemoteNewDate, null);
    this.jPanel2.add(this.jPanel6, "East");
    this.jPanel6.add(this.jButton2, null);
    this.jPanel6.add(this.jButton1, null);
    this.jPanel2.add(this.jScrollPane3, "Center");
    this.jScrollPane3.getViewport().add(this.epDes, null);

    initSize();
    initEvent(); }

  void initEvent() {
    this.treeUpdateList.setToggleClickCount(0);
    this.treeUpdateList.addMouseListener(new NodeSelectionListener(this.treeUpdateList));
    this.treeUpdateList.addTreeSelectionListener(this);
    this.bnBrowser.addActionListener(this);
  }

  void initSize() {
    setPreferredSize(new Dimension(400, 300));
  }

  public Object willNext(Object p1, Object p2, Object p3, Object p4)
  {
    UpdateStub[] USA = null;
    if (this.rootUpdateStub != null)
      USA = UpdateObject.getUpdateFiles(this.rootUpdateStub);
    if ((USA == null) || (USA.length == 0) || (this.updateCenterStubObject == null) || (this.XMLObject == null)) {
      JOptionPane.showMessageDialog(this, "没有可用的企业应用模块下载！", "系统提示", 0);
      return null;
    }
    Object[] Stubs = { USA, this.updateCenterStubObject, this.XMLObject };
    return Stubs;
  }

  public Object willBack(Object p1, Object p2, Object p3, Object p4)
  {
    return null;
  }

  public Object willInto(Object p1, Object p2, Object p3, Object p4)
  {
    this.rootUpdateStub = null; this.XMLObject = null; this.updateCenterStubObject = null;
    this.SO = ((StubObject)p1);
    this.hashList = ((Hashtable)p3);
    clearED();
    this.REMOTE = ((String)this.SO.getObject("REMOTE", "1"));
    if ("1".equals(this.REMOTE)) {
      this.REMOTE = "1";
      this.pnLocal.setVisible(false);
      try {
        processRemote(this.SO, this.hashList);
        this.updateCenterStubObject = this.SO;
      } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "登录下载中心错误：" + e.getMessage(), "系统提示", 0);
        return null;
      }
    } else {
      this.REMOTE = "0";
      this.pnLocal.setVisible(true);
      try {
        setNullLocal();
        this.updateCenterStubObject = null;
        this.XMLObject = null;
      }
      catch (Exception e) {
        JOptionPane.showMessageDialog(this, "安装企业应用模块错误：" + e.getMessage(), "系统提示", 0);
      }
    }

    return this.SO; }

  void setNullLocal() {
    this.treeUpdateList.setModel(null);
  }

  void processRemote(StubObject SO, Hashtable hashList)
    throws Exception
  {
    this.treePublicUpdateList = this.treeUpdateList;

    JDOMXMLBaseObject XML = UpdateObject.buildService(SO.getString("codebase", (String)hashList.get("codebase")), hashList, this.Application, this.Product);
    this.XMLObject = XML;
    String Application = (String)hashList.get("application");

    this.rootUpdateStub = ((UpdateStub)UpdateObject.buildStubObject(Application, XML));

    UpdateObject.removeOProduct(this.rootUpdateStub, this.Product);

    StubObjectTreeNode treeNode = buildTreeNode(this.rootUpdateStub, null);

    StubObjectTreeModel treeModel = buildTreeModel(treeNode);

    buildTree(treeModel);

    buildRenderer();

    setSelectionOne(); }

  void setSelectionOne() {
    if (this.treeUpdateList.getRowCount() > 0)
      this.treeUpdateList.setSelectionRow(0);
  }

  void processLocal(String Codebase, Hashtable hashList) throws Exception {
    this.treePublicUpdateList = this.treeUpdateList;

    this.SO = new StubObject();
    this.SO.setObject("codebase", "file:///" + Codebase);
    this.updateCenterStubObject = this.SO;
    JDOMXMLBaseObject XML = UpdateObject.buildService(Codebase, hashList, this.Application, this.Product);
    this.XMLObject = XML;
    String Application = (String)hashList.get("application");

    this.rootUpdateStub = ((UpdateStub)UpdateObject.buildStubObject(Application, XML));

    StubObjectTreeNode treeNode = buildTreeNode(this.rootUpdateStub, null);

    StubObjectTreeModel treeModel = buildTreeModel(treeNode);

    buildTree(treeModel);

    buildRenderer();

    setSelectionOne(); }

  void buildRenderer() {
    CheckRenderer renderer = new CheckRenderer();
    this.treePublicUpdateList.setCellRenderer(renderer); }

  void buildTree(StubObjectTreeModel treeModel) {
    this.treePublicUpdateList.setModel(treeModel); }

  StubObjectTreeModel buildTreeModel(StubObjectTreeNode treeNode) {
    StubObjectTreeModel treeModel = new StubObjectTreeModel(treeNode);
    return treeModel;
  }

  StubObjectTreeNode buildTreeNode(StubObject SO, StubObjectTreeNode parentTreeNode)
  {
    StubObjectTreeNode treeNode = new StubObjectTreeNode(SO);
    if (parentTreeNode != null) {
      parentTreeNode.add(treeNode);
    }
    StubObject[] SOA = SO.getChilds();
    if ((SOA != null) && (SOA.length > 0)) {
      for (int i = 0; i < SOA.length; ++i) {
        buildTreeNode(SOA[i], treeNode);
      }
    }
    return treeNode;
  }

  private void _$5635(JTree tree, MouseEvent e)
  {
    if (e.getClickCount() == 2) {
      int x = e.getX();
      int y = e.getY();
      int row = tree.getRowForLocation(x, y);
      TreePath path = tree.getPathForRow(row);

      if (path != null) {
        CheckNode node = (CheckNode)path.getLastPathComponent();
        boolean isSelected = !(node.isSelected());
        node.setSelected(isSelected);

        ((DefaultTreeModel)tree.getModel()).nodeChanged(node);

        if (row == 0) {
          tree.revalidate();
          tree.repaint();
        }
      }
    }
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource().equals(this.bnBrowser))
      _$5646();
  }

  public void valueChanged(TreeSelectionEvent e)
  {
    setED(); }

  void setED() {
    TreePath tp = this.treeUpdateList.getSelectionPath();
    if (tp == null) return;
    StubObjectTreeNode stn = (StubObjectTreeNode)tp.getLastPathComponent();
    UpdateStub stubObject = null;
    if (stn != null) {
      stubObject = (UpdateStub)stn.getUserObject();
    }
    if (stubObject != null) {
      String temp = stubObject.getVersion("1.0.0.0");
      this.edRemoteOldVer.setText(temp);
      temp = stubObject.getNewVersion(temp);
      this.edRemoteNewVer.setText(temp);
      temp = stubObject.getDes("");
      this.epDes.setText(temp);
      temp = stubObject.getRemoteTime();
      long l = Long.parseLong(temp);
      if (l != 1L) {
        Calendar cd = Calendar.getInstance();
        cd.setTimeInMillis(l);
        temp = cd.get(1) + "-" + (cd.get(2) + 1) + "-" + cd.get(5);
        temp = temp + " " + cd.get(11) + ":" + cd.get(12) + ":" + cd.get(13);
        this.edRemoteNewDate.setText(temp);
      } else {
        this.edRemoteNewDate.setText("");
      }
    } else {
      clearED(); }
  }

  void clearED() {
    this.edRemoteOldVer.setText("");
    this.edRemoteNewVer.setText("");
    this.edRemoteNewDate.setText("");
    this.epDes.setText(""); }

  private void _$5646() {
    JFileChooser fc = createFileChooser();
    ExampleFileFilter filter = new ExampleFileFilter(new String[] { "eam" }, "企业应用模块文件");

    fc.addChoosableFileFilter(filter);
    fc.setFileFilter(filter);
    fc.setAcceptAllFileFilterUsed(false);
    int result = fc.showOpenDialog(this);
    if (result == 0) {
      File EAMFile = fc.getSelectedFile();
      setCursor(Cursor.getPredefinedCursor(3));
      try {
        _$5679(EAMFile);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "安装本地企业应用模块出错：" + e.getMessage() + "！", "系统提示", 0);
      } finally {
        setCursor(Cursor.getPredefinedCursor(0));
      }
    }
  }

  private void _$5679(File EAMFile) throws Exception {
    String Application = (String)this.hashList.get("application");
    String Product = (String)this.hashList.get("product");
    String EAMFileName = Application + ".eam";
    String PRTFileName = Product + ".eam";
    if ((EAMFile.getName().toUpperCase().equals(EAMFileName.toUpperCase())) || (EAMFile.getName().toUpperCase().equals(PRTFileName.toUpperCase())))
    {
      String Separator = System.getProperty("file.separator");
      String LocalCodeSpace = ((String)this.hashList.get("user.home")) + "ExtractSpace" + Separator;
      File dirFile = new File(LocalCodeSpace);
      FileUtils.deleteDirTree(dirFile);
      if (!(dirFile.exists())) dirFile.mkdirs();
      FileUtils.decodeFile(EAMFile, dirFile);
      processLocal(LocalCodeSpace, this.hashList);
    } else {
      JOptionPane.showMessageDialog(this, "选择的企业应用模块文件与当前登录的应用不一致，不能够正确安装或升级！", "系统提示", 0);
    }
  }

  public JFileChooser createFileChooser() {
    JFileChooser fc = new JFileChooser();

    return fc;
  }

  class NodeSelectionListener extends MouseAdapter
  {
    JTree tree;

    NodeSelectionListener(JTree paramJTree)
    {
      this.tree = paramJTree;
    }

    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2)
        Setp2Form.this._$5635(this.tree, e);
    }
  }
}