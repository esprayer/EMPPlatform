package jreport.swing.classes.wizard;

import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

import org.jdom.Element;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.CalcFieldsListener;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.NavigationEvent;
import com.borland.dx.dataset.NavigationListener;
import com.borland.dx.dataset.ParameterType;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.SortDescriptor;
//import com.borland.dx.text.Alignment;
import com.borland.jbcl.layout.BoxLayout2;
import com.pansoft.esp.db.JAgentDataBase;
import com.pansoft.esp.db.JAgentDataSet;
import jbof.gui.window.classes.style.mdi.JBOFMDIChildWindow;
import jfoundation.bridge.classes.JResponseObject;
import jfoundation.dataobject.classes.JParamObject;
import jframework.foundation.classes.JActiveDComDM;
import jframework.resource.classes.JXMLResource;
import jreport.foundation.classes.JReportObjectEntity;
import jreport.jdof.classes.JDOFReportObject;
import jreport.swing.classes.JReportBook;
import jreport.swing.classes.JReportModel;
import jtoolkit.string.classes.JCommonFunction;
import jtoolkit.xml.classes.JXMLBaseObject;
import jreport.value.JREPORT;
import com.pansoft.esp.fmis.client.fwkview.FMISContentWindow;
import jbof.gui.window.classes.JBOFChildWindow;
import java.math.BigDecimal;

/**
 * <p>Title: 审核公式定义</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c)  004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JAuditFunctionDialog
    extends JBOFMDIChildWindow
    implements CalcFieldsListener, NavigationListener, FocusListener,
    ActionListener, KeyListener, ItemListener, MouseListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
  //
  public String sSvrName = "";
  public static final int STYLE_SHGS = 2; //校验公式的标识
  private String[] symbol = new String[] {
      "-", "*", "/", "+", "<=", "<>", "=", ">=", ">", "<"};

  //校验公式列
  private Column[] RPTJYGS = new Column[29];
  private String[] JYGSCOL = new String[] {
      "HZD_ORDE", "LZD_ORDE", "DYZD_HOFFSET", "DYZD_LOFFSET",
      "JYGS_BJ", "JYGS_EXP", "JYGS_EXP1", "JYGS_EXP2",
      "JYGS_EXP3", "JYGS_EXP4", "JYGS_EXP5", "JYGS_MESS",
      "JYGS_SHJG", "JYGS_SHOW", "JYGS_SHOW1", "JYGS_SHOW2",
      "JYGS_SHOW3", "JYGS_TYPE", "JYGS_STYLE", "JYGS_XF"};
  private int[] JYGSTYPE = new int[] {
      16, 16, 10, 10, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 10, 10, 10};
 // 16, 16, 4, 4, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 4, 4, 4};

  //封面校验公式列
  private Column[] RPTFMJYGS = new Column[20];
  private String[] FMJYGSCOL = new String[] {
      "JYGS_TYPE", "JYGS_VAL", "JYGS_BJ", "JYGS_EXP",
      "JYGS_EXP1", "JYGS_EXP2", "JYGS_EXP3", "JYGS_EXP4",
      "JYGS_EXP5", "JYGS_SHOW", "JYGS_SHOW1", "JYGS_SHOW2",
      "JYGS_SHOW3", "JYGS_XF"};
  private int[] FMJYGSTYPE = new int[] {
      10, 10, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 10};
 // 4, 4, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 4};

  private JParamObject PO;
  private String mSuffix = ""; //年份后缀
  private String mLoginDate = ""; //登录日期
  private int mMaxOrder = 0; //审核公式的最大序号
  private String mBbbh = ""; //当前打开的报表编号
  private JAgentDataSet seleDS = null;
  private JReportBook mReportBook = null;
  private JReportModel mReportModel = null;

  //
  private boolean bModified = false; //数据是否已经修改

  //
  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane jsp = new JSplitPane();
  TableScrollPane tsp = new TableScrollPane();
  JdbTable jbbTab = new JdbTable();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JCheckBox jljck = new JCheckBox();
  JCheckBox jhlck = new JCheckBox();
  JCheckBox jceck = new JCheckBox();
  JTextField jtfgsx = new JTextField();
  JTextField jtfshow = new JTextField();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JTabbedPane jTab = new JTabbedPane();
  JPanel jPanel4 = new JPanel();
  JScrollPane jsp1 = new JScrollPane();
  ButtonGroup btgroup = new ButtonGroup();
  JPanel jbbpanel = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JScrollPane jsp2 = new JScrollPane();
  JTable jfmdmtab = new JTable();
  JScrollPane jsp4 = new JScrollPane();
  JScrollPane jsp3 = new JScrollPane();
  JTable jfhtab = new JTable();
  JTable jfmgstab = new JTable();
  JAgentDataBase Agentdb = new JAgentDataBase();
  JAgentDataSet dsJYGS = new JAgentDataSet();
  JAgentDataSet dsBBZD = new JAgentDataSet();
  JAgentDataSet dsFMDM = new JAgentDataSet();
  JComboBox jcbsymbol = new JComboBox(new String[] {"="});
  JComboBox jcbvalue = new JComboBox(new String[] {"1", "0"});

  JdbTable jgstab = new JdbTable();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel opPane = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton exitButton = new JButton();
  JButton saveButton = new JButton();
  JButton delButton = new JButton();
  JButton addButton = new JButton();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JPanel gsxPane = new JPanel();
  Border border1;
  BoxLayout2 boxLayout21 = new BoxLayout2();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  BoxLayout2 boxLayout23 = new BoxLayout2();
  Border border2;
  JPanel bbSelePane = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel jLabel4 = new JLabel();
  JTextField bbbhTextField = new JTextField();
  JButton bbSeleButton = new JButton();
  JPanel bbPane = new JPanel();
  Border border3;
  BorderLayout borderLayout6 = new BorderLayout();
  Border border4;

  public JAuditFunctionDialog(String date,String pageUrl) {
    try {
		sSvrName = pageUrl;
        // 设置日期
        mLoginDate = date;
      initTable();
      jbInit();
      //初始化字典数据
      initDataSet();
      //读取数据
      loadDataSet();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
    border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(103, 101, 98),
                                              new Color(148, 145, 140));
    border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(103, 101, 98),
                                              new Color(148, 145, 140));
    border4 = BorderFactory.createEmptyBorder();
    setLayout(borderLayout1);
    jbbTab.setAutoCreateColumnsFromModel(true);
    jbbTab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jbbTab.setColumnSortEnabled(false);
    jbbTab.setDataSet(dsBBZD);
    jbbTab.setPopupMenuEnabled(false);
    jbbTab.setEditable(false);
    jgstab.setColumnSortEnabled(false);
    jPanel1.setLayout(borderLayout2);
    jPanel2.setLayout(gridLayout1);
    jLabel1.setToolTipText("");
    jLabel1.setText(res.getString("String_52"));
    jLabel2.setText(res.getString("String_53"));
    jLabel3.setText(res.getString("String_54"));
    jljck.setSelected(true);
    jljck.setText(res.getString("String_55"));
    jhlck.setText(res.getString("String_56"));
    jceck.setToolTipText("");
    jceck.setText(res.getString("String_58"));
    jtfgsx.setMinimumSize(new Dimension(6, 22));
    jtfgsx.setPreferredSize(new Dimension(292, 23));
    jtfgsx.setText("");
    jtfshow.setPreferredSize(new Dimension(780, 23));
    jtfshow.setText("");
    jPanel3.setLayout(borderLayout3);
    jPanel4.setLayout(borderLayout5);
    jbbpanel.setLayout(borderLayout4);
    jsp3.setFont(new java.awt.Font("Monospaced", 0, 11));
    jsp3.setPreferredSize(new Dimension(45, 131));
    jsp3.setRequestFocusEnabled(true);
    jsp4.setFont(new java.awt.Font("Monospaced", 0, 11));
    jsp4.setPreferredSize(new Dimension(60, 231));
    jcbvalue.setPreferredSize(new Dimension(45, 22));
    jcbvalue.setSelectedItem(this);
    jgstab.setDataSet(dsFMDM);
    jgstab.setEditable(false);
    jsp.setOneTouchExpandable(true);
    jfhtab.setFont(new java.awt.Font("Monospaced", 0, 12));
    jfmgstab.setFont(new java.awt.Font("Arial", 0, 12));
    opPane.setDebugGraphicsOptions(0);
    opPane.setPreferredSize(new Dimension(10, 35));
    opPane.setLayout(flowLayout1);
    exitButton.setPreferredSize(new Dimension(74, 25));
    exitButton.setMnemonic('E');
    exitButton.setText(res.getString("String_66"));
    saveButton.setPreferredSize(new Dimension(74, 25));
    saveButton.setMnemonic('S');
    saveButton.setText(res.getString("String_68"));
    delButton.setPreferredSize(new Dimension(74, 25));
    delButton.setMnemonic('D');
    delButton.setText(res.getString("String_70"));
    addButton.setPreferredSize(new Dimension(74, 25));
    addButton.setMnemonic('A');
    addButton.setText(res.getString("String_72"));
    flowLayout1.setAlignment(FlowLayout.CENTER);
    tsp.setBorder(border2);
    tsp.setPreferredSize(new Dimension(472, 425));
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(0);
    gridLayout1.setRows(3);
    gridLayout1.setVgap(1);
    gsxPane.setLayout(boxLayout21);
    jPanel6.setLayout(boxLayout22);
    jPanel5.setLayout(boxLayout23);
    gsxPane.setMinimumSize(new Dimension(135, 32));
    gsxPane.setOpaque(true);
    gsxPane.setPreferredSize(new Dimension(135, 27));
    jPanel6.setPreferredSize(new Dimension(76, 25));
    jPanel5.setPreferredSize(new Dimension(323, 25));
    jPanel2.setBorder(border1);
    jcbsymbol.setPreferredSize(new Dimension(50, 22));
    jbbpanel.setBorder(border2);
    bbSelePane.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    jLabel4.setText(res.getString("String_73"));
    bbbhTextField.setPreferredSize(new Dimension(150, 22));
    bbbhTextField.setEditable(false);
    bbbhTextField.setText("");
    bbSeleButton.setFont(new java.awt.Font("Arial", 1, 11));
    bbSeleButton.setPreferredSize(new Dimension(25, 23));
    bbSeleButton.setText("...");
    jTab.addChangeListener(new JAuditFunctionDialog_jTab_changeAdapter(this));
    bbPane.setBorder(border3);
    bbPane.setLayout(borderLayout6);
    add(jsp, BorderLayout.CENTER);
    jsp.add(tsp, JSplitPane.LEFT);
    tsp.getViewport().add(jbbTab, null);
    jsp.add(jPanel1, JSplitPane.RIGHT);
    jPanel1.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(gsxPane, null);
    gsxPane.add(jLabel1, null);
    gsxPane.add(jcbvalue, null);
    gsxPane.add(jcbsymbol, null);
    gsxPane.add(jtfgsx, null);
    jPanel2.add(jPanel6, null);
    jPanel6.add(jLabel2, null);
    jPanel6.add(jtfshow, null);
    jPanel2.add(jPanel5, null);
    jPanel5.add(jLabel3, null);
    jPanel5.add(jljck, null);
    jPanel5.add(jhlck, null);
    jPanel5.add(jceck, null);
    jPanel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jTab, BorderLayout.CENTER);
    jPanel3.add(jPanel4, BorderLayout.EAST);
    jPanel4.add(jsp3, BorderLayout.WEST);
    jPanel4.add(jsp4, BorderLayout.CENTER);
    jPanel3.add(opPane, BorderLayout.SOUTH);
    jsp.setDividerLocation(240);
    btgroup.add(jljck);
    btgroup.add(jhlck);
    jTab.add(jsp1, res.getString("String_77"));
    jsp1.getViewport().add(jgstab, null);
    jTab.add(jbbpanel, res.getString("String_78"));
    jbbpanel.add(bbSelePane, BorderLayout.NORTH);

 //   jTab.add(jsp2, res.getString("String_79"));
    jsp2.getViewport().add(jfmdmtab, null);

    jsp3.getViewport().add(jfhtab, null);
    jsp4.getViewport().add(jfmgstab, null);

    opPane.add(addButton, null);
    opPane.add(delButton, null);
    opPane.add(saveButton, null);
    opPane.add(exitButton, null);
    bbSelePane.add(jLabel4, null);
    bbSelePane.add(bbbhTextField, null);
    bbSelePane.add(bbSeleButton, null);
    jbbpanel.add(bbPane, BorderLayout.CENTER);
    //
    jceck.setVisible(false);
    //
    seleDS = dsFMDM;
    //add listener
    dsFMDM.addCalcFieldsListener(this);
    dsJYGS.addCalcFieldsListener(this);
    dsBBZD.addNavigationListener(this);
    dsJYGS.addNavigationListener(this);
    dsFMDM.addNavigationListener(this);
    jtfgsx.addFocusListener(this);
    jtfshow.addFocusListener(this);
    jljck.addActionListener(this);
    jhlck.addActionListener(this);
    jceck.addActionListener(this);
    jcbvalue.addItemListener(this);
    jcbsymbol.addItemListener(this);
    //
    addButton.addActionListener(this);
    delButton.addActionListener(this);
    saveButton.addActionListener(this);
    exitButton.addActionListener(this);
    bbSeleButton.addActionListener(this);
    jtfgsx.addKeyListener(this);
    jtfshow.addKeyListener(this);
    //
    jfhtab.addMouseListener(this);
    jfmgstab.addMouseListener(this);
    jfmdmtab.addMouseListener(this);
  }

  /**
   * 初始化数据字典
   */
  private void initTable() throws Exception {
    Element e, node;
    String[] item;
    int index = 0;
    java.net.URL url = JXMLResource.LoadXML(this, "auditcf.xml");
    if (url == null) {
      throw new Exception("Error:url=null");
    }
    String URI = url.toString();
    JXMLBaseObject iniXML = new JXMLBaseObject();
    iniXML.InitXMLURI(URI);
    e = iniXML.GetElementByName("FMDM");
    java.util.List list = iniXML.BeginEnumerate(e);
    if (list != null) {
      Vector v = new Vector();
      node = iniXML.Enumerate(list, index++);
      while (node != null) {
        item = new String[3];
        item[0] = node.getAttributeValue("n");
        item[1] = node.getAttributeValue("i");
        item[2] = node.getAttributeValue("t");
        v.add(item);
        node = iniXML.Enumerate(list, index++);
      }
      selfTabModel model = new selfTabModel(v, new String[] {res.getString("String_85"), res.getString("String_86"), res.getString("String_87")});
      jfmdmtab.setModel(model);
    }
    e = iniXML.GetElementByName("FUNC");
    list = iniXML.BeginEnumerate(e);
    index = 0;
    if (list != null) {
      Vector v = new Vector();
      node = iniXML.Enumerate(list, index++);
      while (node != null) {
        item = new String[1];
        item[0] = node.getAttributeValue("f");
        v.add(item);
        node = iniXML.Enumerate(list, index++);
      }
      selfTabModel model = new selfTabModel(v, new String[] {res.getString("String_90")});
      jfmgstab.setModel(model);
    }
    Vector v = new Vector();
    for (int i = 0; i < this.symbol.length; i++)
      v.add(new String[] {symbol[i]});
    selfTabModel model = new selfTabModel(v, new String[] {res.getString("String_91")});
    jfhtab.setModel(model);
    jgstab.setRowHeaderVisible(false);
    jbbTab.setRowHeaderVisible(false);
    PO = JParamObject.Create();
    if (PO == null)
      PO = new JParamObject();
    mSuffix = PO.GetValueByEnvName("ReportSuffixyear", "");
//    mLoginDate = PO.GetValueByEnvName("LoginDate", "        ").substring(0, 6);
  }

  /**
   * 初始化Dataset
   * @throws Exception
   */
  private void initDataSet() throws Exception {
    //设置取数SQL
    dsJYGS.setAgentSQL("select BBZD_BH,BBZD_DATE,JYGS_ORDE,HZD_ORDE, LZD_ORDE, DYZD_HOFFSET, DYZD_LOFFSET,JYGS_BJ, JYGS_EXP, JYGS_EXP1, JYGS_EXP2,"
       +"JYGS_EXP3, JYGS_EXP4, JYGS_EXP5, JYGS_MESS,JYGS_SHJG, JYGS_SHOW, JYGS_SHOW1,JYGS_SHOW2,JYGS_SHOW3, JYGS_TYPE,JYGS_STYLE, JYGS_XF "
       +" from " +JREPORT.getRTNbyPO("JYGS",PO) + mSuffix + " where BBZD_DATE like '"
                       + mLoginDate + "' and JYGS_STYLE=" + STYLE_SHGS);
    dsJYGS.setTableName(JREPORT.getRTNbyPO("JYGS",PO)+ mSuffix);
    dsBBZD.setAgentSQL("select 1 AS ID,BBZD_BH,BBZD_MC from "+JREPORT.getRTNbyPO("BBZD",PO) + mSuffix + " where BBZD_DATE like '" + mLoginDate +
                       "' order by BBZD_BH");
    dsBBZD.setTableName(JREPORT.getRTNbyPO("BBZD",PO) + mSuffix);
	dsBBZD.setAgentTable(JREPORT.getRTNbyPO("BBZD",PO) + mSuffix);
    dsFMDM.setAgentSQL("select * from "+JREPORT.getRTNbyPO("FMJYGS",PO) + mSuffix);
    dsFMDM.setTableName(JREPORT.getRTNbyPO("FMJYGS",PO) + mSuffix);
    dsJYGS.setAgentDataBase(Agentdb);
    dsBBZD.setAgentDataBase(Agentdb);
    dsFMDM.setAgentDataBase(Agentdb);
    //创建列
    Column BBZD_ID = new Column();
    Column BBZD_BH = new Column();
    Column BBZD_BBZD_BH = new Column();
    Column BBZD_DATE = new Column();
    Column JYGS_ORDE = new Column();
    Column SHGS_BJZ = new Column();
    Column SHGS_BJF = new Column();
    Column SHGS_BDS = new Column();
    Column SHGS_GSSM = new Column();
    Column SHGS_GSXZ = new Column();
    Column SHGS_CEB = new Column();
    Column FMDM_BJZ = new Column();
    Column FMDM_BJF = new Column();
    Column FMDM_BDS = new Column();
    Column FMDM_GSSM = new Column();
    Column FMDM_GSXZ = new Column();
    Column FMDM_JYGS_ORDE = new Column();
    Column BBZD_MC = new Column();
    //
    BBZD_ID.setColumnName("ID");
    BBZD_ID.setDataType(com.borland.dx.dataset.Variant.BIGDECIMAL);
//	BBZD_ID.setDataType(com.borland.dx.dataset.Variant.INT);
    BBZD_ID.setPrecision(8);
    BBZD_ID.setVisible(0);
    BBZD_ID.setSqlType(12);
    BBZD_ID.setSortPrecision(1);
    //
    BBZD_BH.setColumnName("BBZD_BH");
    BBZD_BH.setDataType(com.borland.dx.dataset.Variant.STRING);
    BBZD_BH.setPrecision(8);
    BBZD_BH.setRowId(true);
    BBZD_BH.setTableName(JREPORT.getRTNbyPO("JYGS",PO));
    BBZD_BH.setVisible(-1);
    BBZD_BH.setServerColumnName("BBZD_BH");
    BBZD_BH.setSqlType(12);
    BBZD_BBZD_BH.setParameterType(ParameterType.NONE);
    BBZD_BBZD_BH.setPrecision(8);
    BBZD_BBZD_BH.setWidth(6);
    BBZD_BBZD_BH.setCaption(res.getString("String_108"));
    BBZD_BBZD_BH.setColumnName("BBZD_BH");
    BBZD_BBZD_BH.setDataType(com.borland.dx.dataset.Variant.STRING);
    BBZD_BBZD_BH.setParameterType(ParameterType.NONE);
    BBZD_BBZD_BH.setPrecision(8);
    BBZD_BBZD_BH.setTableName(JREPORT.getRTNbyPO("BBZD",PO));
    BBZD_BBZD_BH.setServerColumnName("BBZD_BH");
    BBZD_BBZD_BH.setSqlType(12);
    BBZD_DATE.setColumnName("BBZD_DATE");
    BBZD_DATE.setDataType(com.borland.dx.dataset.Variant.STRING);
    BBZD_DATE.setPrecision(8);
    BBZD_DATE.setRowId(true);
    BBZD_DATE.setVisible(0);
    BBZD_DATE.setServerColumnName("BBZD_DATE");
    BBZD_DATE.setSqlType(12);
    JYGS_ORDE.setColumnName("JYGS_ORDE");
    JYGS_ORDE.setDataType(com.borland.dx.dataset.Variant.STRING);
    JYGS_ORDE.setPrecision(9);
    JYGS_ORDE.setRowId(true);
    JYGS_ORDE.setVisible(0);
    JYGS_ORDE.setServerColumnName("JYGS_ORDE");
    JYGS_ORDE.setSqlType(12);
    SHGS_BJZ.setCalcType(1);
    SHGS_BJZ.setCaption(res.getString("String_116"));
    SHGS_BJZ.setColumnName("SHGS_BJZ");
    SHGS_BJZ.setDataType(com.borland.dx.dataset.Variant.STRING);
    SHGS_BJZ.setPreferredOrdinal(23);
    SHGS_BJZ.setWidth(5);
    SHGS_BJZ.setServerColumnName("");
    SHGS_BJZ.setSqlType(0);
    SHGS_BJZ.setAlignment(2);
    SHGS_BJF.setCalcType(1);
    SHGS_BJF.setCaption(res.getString("String_119"));
    SHGS_BJF.setColumnName("SHGS_BJF");
    SHGS_BJF.setDataType(com.borland.dx.dataset.Variant.STRING);
    SHGS_BJF.setPreferredOrdinal(24);
    SHGS_BJF.setWidth(5);
    SHGS_BJF.setServerColumnName("");
    SHGS_BJF.setSqlType(0);
    //
    SHGS_BDS.setCalcType(1);
    SHGS_BDS.setCaption(res.getString("String_122"));
    SHGS_BDS.setColumnName("SHGS_BDS");
    SHGS_BDS.setDataType(com.borland.dx.dataset.Variant.STRING);
    SHGS_BDS.setPreferredOrdinal(25);
    SHGS_BDS.setWidth(25);
    SHGS_BDS.setServerColumnName("");
    SHGS_BDS.setSqlType(0);
    SHGS_GSSM.setCalcType(1);
    SHGS_GSSM.setCaption(res.getString("String_125"));
    SHGS_GSSM.setColumnName("SHGS_GSSM");
    SHGS_GSSM.setDataType(com.borland.dx.dataset.Variant.STRING);
    SHGS_GSSM.setPreferredOrdinal(26);
    SHGS_GSSM.setWidth(24);
    SHGS_GSSM.setServerColumnName("");
    SHGS_GSSM.setSqlType(0);
    SHGS_GSXZ.setCalcType(1);
    SHGS_GSXZ.setCaption(res.getString("String_128"));
    SHGS_GSXZ.setColumnName("SHGS_GSXZ");
    SHGS_GSXZ.setDataType(com.borland.dx.dataset.Variant.STRING);
    SHGS_GSXZ.setPreferredOrdinal(27);
    SHGS_GSXZ.setWidth(7);
    SHGS_GSXZ.setServerColumnName("");
    SHGS_GSXZ.setSqlType(0);
    SHGS_CEB.setCalcType(1);
    SHGS_CEB.setCaption(res.getString("String_131"));
    SHGS_CEB.setColumnName("SHGS_CEB");
    SHGS_CEB.setDataType(com.borland.dx.dataset.Variant.STRING);
    SHGS_CEB.setPreferredOrdinal(28);
    SHGS_CEB.setWidth(8);
    SHGS_CEB.setServerColumnName("");
    SHGS_CEB.setSqlType(0);
    SHGS_CEB.setAlignment(2);
    FMDM_JYGS_ORDE.setColumnName("JYGS_ORDE");
    FMDM_JYGS_ORDE.setDataType(com.borland.dx.dataset.Variant.STRING);
    FMDM_JYGS_ORDE.setPrecision(9);
    FMDM_JYGS_ORDE.setRowId(true);
    FMDM_JYGS_ORDE.setTableName(JREPORT.getRTNbyPO("FMJYGS",PO));
    FMDM_JYGS_ORDE.setServerColumnName("JYGS_ORDE");
    FMDM_JYGS_ORDE.setSqlType(12);
    FMDM_JYGS_ORDE.setVisible(0);
    FMDM_BJZ.setCalcType(1);
    FMDM_BJZ.setCaption(res.getString("String_137"));
    FMDM_BJZ.setColumnName("FMDM_BJZ");
    FMDM_BJZ.setCurrency(false);
    FMDM_BJZ.setDataType(com.borland.dx.dataset.Variant.STRING);
    FMDM_BJZ.setPreferredOrdinal(23);
    FMDM_BJZ.setSchemaName("");
    FMDM_BJZ.setServerColumnName("");
    FMDM_BJZ.setSqlType(0);
    FMDM_BJF.setCalcType(1);
    FMDM_BJF.setCaption(res.getString("String_141"));
    FMDM_BJF.setColumnName("FMDM_BJF");
    FMDM_BJF.setDataType(com.borland.dx.dataset.Variant.STRING);
    FMDM_BJF.setPreferredOrdinal(24);
    FMDM_BJF.setServerColumnName("");
    FMDM_BJF.setSqlType(0);
    FMDM_BDS.setCalcType(1);
    FMDM_BDS.setCaption(res.getString("String_144"));
    FMDM_BDS.setColumnName("FMDM_BDS");
    FMDM_BDS.setDataType(com.borland.dx.dataset.Variant.STRING);
    FMDM_BDS.setPreferredOrdinal(26);
    FMDM_BDS.setWidth(60);
    FMDM_BDS.setSqlType(0);
    FMDM_GSSM.setCalcType(1);
    FMDM_GSSM.setCaption(res.getString("String_146"));
    FMDM_GSSM.setColumnName("FMDM_GSSM");
    FMDM_GSSM.setDataType(com.borland.dx.dataset.Variant.STRING);
    FMDM_GSSM.setPreferredOrdinal(25);
    FMDM_GSSM.setWidth(45);
    FMDM_GSSM.setServerColumnName("");
    FMDM_GSSM.setSqlType(0);
    FMDM_GSXZ.setCalcType(1);
    FMDM_GSXZ.setCaption(res.getString("String_149"));
    FMDM_GSXZ.setColumnName("FMDM_GSXZ");
    FMDM_GSXZ.setDataType(com.borland.dx.dataset.Variant.STRING);
    FMDM_GSXZ.setPreferredOrdinal(27);
    FMDM_GSXZ.setServerColumnName("");
    FMDM_GSXZ.setSqlType(0);
    BBZD_MC.setCaption(res.getString("String_152"));
    BBZD_MC.setColumnName("BBZD_MC");
    BBZD_MC.setDataType(com.borland.dx.dataset.Variant.STRING);
    BBZD_MC.setPrecision(60);
    BBZD_MC.setTableName(JREPORT.getRTNbyPO("BBZD",PO));
    BBZD_MC.setWidth(20);
    BBZD_MC.setServerColumnName("BBZD_MC");
    BBZD_MC.setSqlType(12);
    //构建报表审核公式列
    for (int i = 0; i < 20; i++) {
      RPTJYGS[i] = new Column();
      RPTJYGS[i].setColumnName(JYGSCOL[i]);
      RPTJYGS[i].setServerColumnName(JYGSCOL[i]);
      RPTJYGS[i].setDataType(JYGSTYPE[i]);
      RPTJYGS[i].setVisible(0);
      if (JYGSCOL[i].equals("DYZD_HOFFSET") || JYGSCOL[i].equals("DYZD_LOFFSET")) {
        RPTJYGS[i].setDefault("0");
      }
    }
    RPTJYGS[20] = BBZD_BH;
    RPTJYGS[21] = BBZD_DATE;
    RPTJYGS[22] = JYGS_ORDE;
    RPTJYGS[23] = SHGS_BJZ;
    RPTJYGS[24] = SHGS_BJF;
    RPTJYGS[25] = SHGS_BDS;
    RPTJYGS[26] = SHGS_GSSM;
    RPTJYGS[27] = SHGS_GSXZ;
    RPTJYGS[28] = SHGS_CEB;
    //构建封面校验公式列
    for (int i = 0; i < 14; i++) {
      RPTFMJYGS[i] = new Column();
      RPTFMJYGS[i].setColumnName(FMJYGSCOL[i]);
      RPTFMJYGS[i].setServerColumnName(FMJYGSCOL[i]);
      RPTFMJYGS[i].setDataType(FMJYGSTYPE[i]);
      RPTFMJYGS[i].setVisible(0);
    }
    RPTFMJYGS[14] = FMDM_JYGS_ORDE;
    RPTFMJYGS[15] = (Column) SHGS_BJZ.clone();
    RPTFMJYGS[16] = (Column) SHGS_BJF.clone();
    RPTFMJYGS[17] = (Column) SHGS_BDS.clone();
    RPTFMJYGS[18] = (Column) SHGS_GSSM.clone();
    RPTFMJYGS[19] = (Column) SHGS_GSXZ.clone();
    //
    dsJYGS.setMasterLink(new com.borland.dx.dataset.MasterLinkDescriptor(dsBBZD, new String[] {"BBZD_BH"}
        , new String[] {"BBZD_BH"}
        , true, false, false));
    dsJYGS.setColumns(RPTJYGS);
    dsFMDM.setColumns(RPTFMJYGS);
    dsBBZD.setColumns(new Column[] {BBZD_ID, BBZD_BBZD_BH, BBZD_MC});
    dsBBZD.setSort(new SortDescriptor(new String[] {"ID"}));

  }

  /**
   * 读取字典数据
   * @throws Exception
   */
  private void loadDataSet() throws Exception {
    //打开报表字典
    dsBBZD.removeNavigationListener(this);
    dsBBZD.loadData();
    /*手工将FMDM插入*/
/*
    dsBBZD.first();
    dsBBZD.insertRow(true);
    dsBBZD.setInt("ID", 0);
    dsBBZD.setString("BBZD_BH", "FMDM");
    dsBBZD.setString("BBZD_MC", res.getString("String_166"));
 */
    dsBBZD.addNavigationListener(this);
    //打开封面校验公式
//   dsFMDM.loadData();
    //打开校验公式字典
    dsJYGS.loadData();
    //
    dsBBZD.first();
  }

  /**
   * 取出分隔字段
   * @param changedRow ReadRow
   * @param calcRow DataRow
   * @param isPosted boolean
   */
  public void calcFields(ReadRow changedRow, DataRow calcRow, boolean isPosted) {
    int type, nValue;
    String cesh = "0";
    String temp, GSX, SHOW, bjz = "0", bjf = "=", gsxz = "";
	BigDecimal bdTmp;

    try {
      GSX = changedRow.getString("JYGS_EXP");
      if (null == GSX) GSX = "";
      temp = changedRow.getString("JYGS_EXP1");
      if (null == temp) temp = "";
      GSX += temp.trim();
      temp = changedRow.getString("JYGS_EXP2");
      if (null == temp) temp = "";
      GSX += temp.trim();
      temp = changedRow.getString("JYGS_EXP3");
      if (null == temp) temp = "";
      GSX += temp.trim();
      temp = changedRow.getString("JYGS_EXP4");
      if (null == temp) temp = "";
      GSX += temp.trim();
      temp = changedRow.getString("JYGS_EXP5");
      if (null == temp) temp = "";
      GSX += temp.trim();
      SHOW = changedRow.getString("JYGS_SHOW");
      if (null == SHOW) SHOW = "";
      temp = changedRow.getString("JYGS_SHOW1");
      if (null == temp) temp = "";
      SHOW += temp.trim();
      temp = changedRow.getString("JYGS_SHOW2");
      if (null == temp) temp = "";
      SHOW += temp.trim();
      temp = changedRow.getString("JYGS_SHOW3");
      SHOW += temp;
	  bdTmp = changedRow.getBigDecimal("JYGS_TYPE");
	  type = bdTmp.intValue();
  //    type = changedRow.getInt("JYGS_TYPE");
      if (type == 1 || type == 3) {
        gsxz = res.getString("String_181");
      }
      if (type == 2 || type == 4) {
        gsxz = res.getString("String_182");
      }
      if (type == 3 || type == 4) {
        cesh = "1";
      }
      /**
       * 封面代码中比较值和比较符是分开存放的
       * 而在报表审核公式中，是存放在一块儿的
       */
      if (changedRow.getColumnCount() == RPTFMJYGS.length) {
		  bdTmp = changedRow.getBigDecimal("JYGS_VAL");
		  nValue = bdTmp.intValue();
	//	  nValue = changedRow.getInt("JYGS_VAL");
		  bjf = changedRow.getString("JYGS_BJ");
		  calcRow.setString("SHGS_BJZ", String.valueOf(nValue));
		  calcRow.setString("SHGS_BJF", bjf);
		  //将公式转换为用户模式
//        GSX = JDOFReportObject.FunctionManager.StoreModelToUserModel(GSX.trim(), null, getReportModel("FMDM"));
		  calcRow.setString("SHGS_BDS", GSX);
		  calcRow.setString("SHGS_GSXZ", gsxz);
		  calcRow.setString("SHGS_GSSM", SHOW);
      }
      else {
        if (GSX.indexOf("=") != -1) {
          bjz = GSX.substring(0, GSX.indexOf("="));
          GSX = GSX.substring(GSX.indexOf("=") + 1);
          //将公式转换为用户模式
          try{
            GSX = JDOFReportObject.FunctionManager.StoreModelToUserModel(GSX.trim(), null, getReportModel(changedRow.getString("BBZD_BH")));
          }catch(Exception ee){
            //
          }
        }
        calcRow.setString("SHGS_BJZ", bjz);
        calcRow.setString("SHGS_BJF", bjf);
        calcRow.setString("SHGS_BDS", GSX);
        calcRow.setString("SHGS_GSXZ", gsxz);
        calcRow.setString("SHGS_CEB", cesh.equals("1") ? res.getString("String_199") : res.getString("String_200"));
        calcRow.setString("SHGS_GSSM", SHOW);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 数据浏览导航
   * @param event NavigationEvent
   */
  public void navigated(NavigationEvent event) {
    String tableName = "",sValue;
	BigDecimal bdTmp;

    try {
      JAgentDataSet ads = (JAgentDataSet) event.getSource();
      tableName = ads.getTableName();
      if (tableName == null || tableName.trim().equals("")) {
        return;
      }
      if (tableName.indexOf("RPTBBZD") > -1) {
        /**
         * 如果当前选中的是FMDM，则切换到对应的DataSet上
         * 因为存放的表不一样，所以要使用不同的DataSet
         */
        String bbbh = dsBBZD.getString("BBZD_BH");
        if (bbbh.equals("FMDM")) {
          seleDS = dsFMDM;
          jgstab.setDataSet(dsFMDM);
          jceck.setVisible(false);
          setRowEditable(false);
        }
        else {
          seleDS = dsJYGS;
          jgstab.setDataSet(dsJYGS);
          jceck.setVisible(true);
          setRowEditable(true);
        }
        //取当前报表审核公式的最大序号
        seleDS.removeNavigationListener(this);
//        mMaxOrder = getOrder();
        mMaxOrder = getMaxOrder();
        seleDS.addNavigationListener(this);
        jTab.setSelectedIndex(0);
//        if(isSaveChanged()){
//          return;
//        }
      }
      /*报表校验公式*/
      else if (tableName.indexOf("RPTJYGS") > -1) {
        /**
         * 改为显示的时候转换，这样速度快一些
         */
        String GSX = ads.getString("SHGS_BDS");
        String bbbh = ads.getString("BBZD_BH");
        try {
          sValue = JDOFReportObject.FunctionManager.StoreModelToUserModel(GSX, null, getReportModel(bbbh));
        }
        catch (Exception eee) {
          eee.printStackTrace();
          sValue = GSX;
        }
        jtfgsx.setText(sValue);
        //
        jtfshow.setText(ads.getString("SHGS_GSSM"));

		bdTmp = ads.getBigDecimal("JYGS_TYPE");
		int type = bdTmp.intValue();
//        int type = ads.getInt("JYGS_TYPE");

        if (type == 1 || type == 3) {
          jljck.setSelected(true);
        }
        if (type == 2 || type == 4) {
          jhlck.setSelected(true);
        }
        if (type == 3 || type == 4) {
          jceck.setSelected(true);
        }
        else {
          jceck.setSelected(false);
        }
        jcbvalue.removeItemListener(this);
        jcbvalue.setSelectedItem(ads.getString("SHGS_BJZ"));
        jcbvalue.addItemListener(this);
      }
      /*报表封面校验公式*/
      else if (tableName.indexOf("RPTFMJYGS") > -1) {
		  bdTmp = ads.getBigDecimal("JYGS_XF");
		  int nXf = bdTmp.intValue();
//        int nXf = ads.getInt("JYGS_XF");
        if (nXf == 1) {
          setRowEditable(false);
        }
        else {
          setRowEditable(true);
        }
        jtfshow.setText(ads.getString("SHGS_GSSM"));
        jtfgsx.setText(ads.getString("SHGS_BDS"));
		bdTmp = ads.getBigDecimal("JYGS_TYPE");
		int type = bdTmp.intValue();
//        int type = ads.getInt("JYGS_TYPE");
        if (type == 1) {
          jljck.setSelected(true);
        }
        else {
          jhlck.setSelected(true);
        }
        jcbvalue.removeItemListener(this);
        jcbvalue.setSelectedItem(ads.getString("SHGS_BJZ"));
        jcbvalue.addItemListener(this);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

    /**
     * getMaxOrder
     *
     * @return int
     */
    private int getMaxOrder() {
        JResponseObject RO = null;
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("BBZD_BH",   dsBBZD.getString("BBZD_BH"));
        PO.SetValueByParamName("BBZD_DATE", mLoginDate.substring(0, 6) );
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getJygsMaxOrder", PO);
        if (RO != null && RO.GetErrorCode() == 0) {
            Integer in = (Integer) RO.ResponseObject;
            return in.intValue();
        }
        return 0;
    }
    public void  SetRealRptSvrNoDot(String pageUrl) {
        sSvrName = pageUrl;
    }

	public String GetRealRptSvr() {
		if ( !sSvrName.equals("") && sSvrName != null ) {
			return sSvrName + ".";
		} else {
			sSvrName = "";
		}
		return sSvrName;
	}

    /**
   * 设置当前所选行是否可以修改
   * 主要是将所以项目设为不可操作
   * @param bEdit boolean
   */
  private void setRowEditable(boolean bEdit) {
    jcbvalue.setEnabled(bEdit);
    jcbsymbol.setEnabled(bEdit);
    jljck.setEnabled(bEdit);
    jhlck.setEnabled(bEdit);
    jtfgsx.setEnabled(bEdit);
    jtfshow.setEnabled(bEdit);
    jtfgsx.setEditable(bEdit);
    //
    delButton.setEnabled(bEdit);
  }

  /**
   * 取当前报表的最大序号
   * @return int
   */
  private int getOrder() {
    String sValue;
    int count, order, nValue, curRow;
    order = 0;

    curRow = seleDS.getRow();
    count = seleDS.getRowCount();
    seleDS.first();
    sValue = "";
    while (count > 0) {
      sValue = seleDS.getString("JYGS_ORDE");
      if (sValue == null || sValue.trim().equals("")) {
        sValue = "0";
      }
      nValue = Integer.parseInt(sValue);
      if (nValue > order) {
        order = nValue;
      }
      if (!seleDS.next()) {
        break;
      }
    }
    //返回当前行
    seleDS.goToRow(curRow);
    return order;
  }

  public void focusGained(FocusEvent e) {
  }

  public void focusLost(FocusEvent e) {
    String text, value, oldValue, col, bbbh;
    try {
      if (e.getSource() == jtfgsx) {
        if (seleDS == dsJYGS) {
          bbbh = dsJYGS.getString("BBZD_BH").trim();
          oldValue = seleDS.getString("SHGS_BJZ").trim() + seleDS.getString("SHGS_BJF").trim() + seleDS.getString("SHGS_BDS").trim();
          value = (String) jcbvalue.getSelectedItem() + (String) jcbsymbol.getSelectedItem() + jtfgsx.getText().trim();
        }
        else {
          bbbh = "FMDM";
          oldValue = seleDS.getString("SHGS_BDS").trim();
          value = jtfgsx.getText().trim();
        }
        value = value.toUpperCase();
        //如果一样，则退出
        if (value.equals(oldValue)) {
          return;
        }
        //将格式转换为存储格式
        try {
          text = JDOFReportObject.FunctionManager.UserModelToStoreModel(value, null, getReportModel(bbbh));
        }
        catch (Exception eee) {
          eee.printStackTrace();
          text = value;
        }
        String[] SArray = JCommonFunction.SplitString(text, Integer.parseInt(PO.GetValueByEnvName("RPT_GSLEN", "500")), 6);
        col = "JYGS_EXP";
        for (int i = 0; i < 6; i++) {
          jgstab.getDataSet().setString(i > 0 ? col + i : col, SArray[i]);
        }
        setModified(true);
      }
      else if (e.getSource() == jtfshow) {
        oldValue = seleDS.getString("SHGS_GSSM").trim();
        text = jtfshow.getText().trim();
        if (oldValue.equals(text)) {
          return;
        }
        String[] SArray = JCommonFunction.SplitString(text, Integer.parseInt(PO.GetValueByEnvName("RPT_GSLEN", "500")), 4);
        col = "JYGS_SHOW";
        for (int i = 0; i < 4; i++) {
          jgstab.getDataSet().setString(i > 0 ? col + i : col, SArray[i]);
        }
        setModified(true);
      }
    }
    catch (Exception ee) {
//      JOptionPane.showMessageDialog(Application.MainWindow,"公式不正确，请检查！");
      ee.printStackTrace();
//      jtfgsx.requestFocus();

    }
  }

  /**
   * 获取一个空的MODEL
   * 在将BB公式转换成存储模式的时候
   * 需要用到一个包含报表日期和报表编号的MODEL
   */
  private JReportModel getReportModel(String bbbh) {
    if (mReportModel == null) {
      mReportModel = new JReportModel();
    }

    mReportModel.BBZD_BH = bbbh;
    mReportModel.BBZD_DATE = mLoginDate.substring(0, 6);
    return mReportModel;
  }

  /**
   * action 事件
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    int type = 1;
    //增加
    if (e.getSource() == addButton) {
      addRecord();
    }
    //删除
    else if (e.getSource() == delButton) {
      delRecord();
    }
    //保存
    else if (e.getSource() == saveButton) {
      saveData();
    }
    //退出
    else if (e.getSource() == exitButton) {
      exit();
    }
    //报表选择
    else if (e.getSource() == bbSeleButton) {
      onBbbhHelp();
    }
    //属性改正
    else if (e.getSource() == jljck || e.getSource() == jhlck || e.getSource() == jceck) {
      onSxChanged();
    }
  }

  /**
   * 属性改正
   */
  private void onSxChanged() {
    int type = 1;
    if (seleDS == dsJYGS) {
      if (jljck.isSelected()) {
        if (jceck.isSelected())
          type = 3;
        else
          type = 1;
      }
      else if (jhlck.isSelected()) {
        if (jceck.isSelected())
          type = 4;
        else
          type = 2;
      }
    }
    else {
      if (jljck.isSelected()) {
        type = 1;
      }
      else if (jhlck.isSelected()) {
        type = 2;
      }
    }
    seleDS.setBigDecimal("JYGS_TYPE", new BigDecimal(type));
//	seleDS.setInt("JYGS_TYPE", type);
    setModified(true);
  }

  /**
   * 增加一条审核公式
   */
  private void addRecord() {
    String date = "", bh = "";
    if (mLoginDate.length() >= 6) {
      date = mLoginDate.substring(0, 6);
    }
    bh = dsBBZD.getString("BBZD_BH");
    if (bh == null || bh.trim().equals("")) {
      JOptionPane.showMessageDialog(Application.MainWindow, res.getString("String_221"));
      return;
    }
    if (bh.equals("FMDM")) {
      dsFMDM.last();
      dsFMDM.insertRow(false);
      mMaxOrder++;
      dsFMDM.setString("JYGS_ORDE", String.valueOf(mMaxOrder)); //设置序号列
	  dsFMDM.setBigDecimal("JYGS_VAL",new BigDecimal(1));
//      dsFMDM.setInt("JYGS_VAL", 1);
      dsFMDM.setString("JYGS_BJ", "=");
      dsFMDM.setBigDecimal("JYGS_TYPE",new BigDecimal(1));
//	  dsFMDM.setInt("JYGS_TYPE", 1);
    }
    else {
      dsJYGS.last();
      dsJYGS.insertRow(false);
      dsJYGS.setString("BBZD_DATE", date);
      dsJYGS.setString("BBZD_BH", bh);
      mMaxOrder++;
      dsJYGS.setString("JYGS_ORDE", String.valueOf(mMaxOrder)); //设置序号列
      dsJYGS.setString("JYGS_BJ", "=");
      dsJYGS.setString("HZD_ORDE", "H");
      dsJYGS.setString("LZD_ORDE", "L");

	  dsJYGS.setBigDecimal("JYGS_STYLE",new BigDecimal(STYLE_SHGS)); //设置校验公式标识
//      dsJYGS.setInt("JYGS_STYLE", STYLE_SHGS); //设置校验公式标识
      dsJYGS.setString("JYGS_EXP", "1=");
      dsJYGS.setBigDecimal("JYGS_TYPE",new BigDecimal(1));
//	  dsJYGS.setInt("JYGS_TYPE", 1);
    }
    //
    jcbvalue.setSelectedItem("1");
    jtfgsx.requestFocus();
    setModified(true);
  }

  /**
   * 删除选中的审核公式
   */
  private void delRecord() {
    int row;

    row = seleDS.getRow();
    if (row >= 0) {
      seleDS.deleteRow();
      setModified(true);
    }
  }

  /**
   * 是否保存已经修改的内容
   * @return boolean
   */
  private boolean isSaveChanged() {
    int nRtn;
    if (isModified()) {
      nRtn = JOptionPane.showConfirmDialog(Application.MainWindow, res.getString("String_242"), res.getString("String_243"), JOptionPane.YES_NO_OPTION);
      if (nRtn == JOptionPane.YES_OPTION) {
        return saveData();
      }
    }
    return true;
  }

  /**
   * 保存数据
   * @return boolean
   */
  private boolean saveData() {
    int curRow;

    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    seleDS.removeNavigationListener(this);
    curRow = seleDS.getRow();
    try {
      //检查是否已经修改
      if (!isModified()) {
        return false;
      }
      //检查数据是否正确
      if (!checkData()) {
        return false;
      }
      seleDS.saveChanges();
      setModified(false);
      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(Application.MainWindow, res.getString("String_244") + e.getMessage());
      return false;
    }
    finally {
      seleDS.goToRow(curRow);
      seleDS.addNavigationListener(this);
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * 是否已经修改
   * @return boolean
   */
  private boolean isModified() {

    return bModified;
  }

  /**
   * 检查数据是否合法
   * @return boolean
   */
  private boolean checkData() throws Exception {
    int count;
    String sValue;

    count = seleDS.getRowCount();
    seleDS.first();
    while (count > 0) {
      sValue = seleDS.getString("SHGS_BDS");
      if (sValue == null || sValue.trim().equals("")) {
        JOptionPane.showMessageDialog(Application.MainWindow, res.getString("String_246"));
        return false;
      }
      if (!seleDS.next()) {
        break;
      }
    }

    return true;
  }

  /**
   * 选择一个新的报表打开
   */
  private void onBbbhHelp() {
    try {
      setCursor(new Cursor(Cursor.WAIT_CURSOR));
      PO.SetValueByParamName("sYear", PO.GetValueByEnvName("LoginFaYear"));
      PO.SetValueByParamName("sTable", "RPTBBZD");
      PO.SetValueByParamName("sBhzd", "BBZD_BH");
      PO.SetValueByParamName("sMczd", "BBZD_MC");
      PO.SetValueByParamName("sEveryWhere", "BBZD_DATE='" + mLoginDate.substring(0, 6) + "'");
      PO.SetValueByParamName("sAllorClass", "0");
      String[] helpData = com.pansoft.help.cnttier.JHelp.showHelpDialog(Application.MainWindow, res.getString("String_260"), PO);
      if (helpData == null) {
        return;
      }
      if (helpData[0].equals(mBbbh)) {
        return;
      }
      bbbhTextField.setText(helpData[0] + "-" + helpData[1]);
      mBbbh = helpData[0];
      openReportBook(mBbbh);
    }
    finally {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * 打开指定报表
   * @param bbbh String
   */
  private void openReportBook(String bbbh) {
    JResponseObject RO = null;
    JReportObjectEntity ROE = null;
    PO.SetValueByParamName("BBZD_DATE", mLoginDate + "        ".substring(0, 6));
    PO.SetValueByParamName("BBZD_BH", bbbh);
    PO.SetValueByParamName("ModelClassName", "jreport.swing.classes.JReportModel");
    RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "OpenReportObject", PO);
    try {
      if (RO != null && RO.ErrorCode == 0) {
        ROE = (JReportObjectEntity) RO.ResponseObject;
        mReportBook = new JReportBook();
        mReportBook.ReportView.OpenReport(ROE, PO);
        mReportBook.ReportView.setAllowInCellEditing(false);
//		mReportBook.ReportView.setEnableProtection(true);
        /*添加事件监听器*/
        mReportBook.ReportView.addMouseListener(this);
        bbPane.removeAll();
        bbPane.add(mReportBook.ReportView, BorderLayout.CENTER);
      }
    }
    catch (Exception e) {

    }
  }

  /**
   * 退出
   */
  private void exit() {
    Application.CloseObjectWindow(this);
  }

  //-----------------------------------------------------
  //描述:通过企业资源管理器中的保存进行对帐单的保存工作
  //设计:hufeng(2004.2.29)
  //实现:hufeng
  //修改:
  //-----------------------------------------------------
  public Object ProcessmnSave(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
    this.saveData();
    return null;
  }

  //---------------------------------------------------------
  //描述: 在关闭此窗口时进行调用(框架提供的函数)
  //设计:hufeng(2004.2.29)
  //实现:hufeng
  //修改:
  //--------------------------------------------------------
  public boolean Close() {
    boolean res = true;
    res = isSaveChanged();
    //
    return res;
  }

  /**
   *
   * @param e KeyEvent
   */
  public void keyReleased(KeyEvent e) {
    /*在回车事件里处理新增记录*/
    if (e.getKeyCode() == e.VK_ENTER) {
      if (e.getSource() == jtfgsx) {
        if (!jtfgsx.getText().trim().equals("")) {
          jtfshow.requestFocus();
        }
      }
      else if (e.getSource() == jtfshow) {
        if (!jtfgsx.getText().trim().equals("") && !jtfshow.getText().trim().equals("")) {
          jtfgsx.requestFocus(true);
          focusLost(new FocusEvent(jtfshow, FocusEvent.FOCUS_LOST));
          addRecord();
        }
      }
    }
//    else if
  }

  public void keyTyped(KeyEvent e) {
  }

  public void keyPressed(KeyEvent e) {
  }

  /**
   * 项目切换
   * @param e ItemEvent
   * @todo Implement this java.awt.event.ItemListener method
   */
  public void itemStateChanged(ItemEvent e) {
    String sValue, oldValue, sExp;
	BigDecimal bdTmp;

    //比较值
    if (e.getSource().equals(this.jcbvalue)) {
      if (seleDS == dsJYGS) {
        oldValue = dsJYGS.getString("JYGS_EXP");
        if (oldValue != null && oldValue.length() > 0) {
          oldValue = oldValue.substring(0, 1);
        }
      }
      else {
		  bdTmp = dsFMDM.getBigDecimal("JYGS_VAL");
		  oldValue = String.valueOf(bdTmp.intValue());

//        oldValue = String.valueOf(dsFMDM.getInt("JYGS_VAL"));
      }
      sValue = e.getItem().toString();
      /**
       * 如果没有改变的话，则不更新
       */
      if (sValue.equals(oldValue)) {
        return;
      }
      if (seleDS == dsJYGS) {
        sExp = dsJYGS.getString("JYGS_EXP");
        if (sExp.length() > 0) {
          sExp = sValue + sExp.substring(1);
        }
        else {
          sExp = sValue;
        }
        dsJYGS.setString("JYGS_EXP", sExp);
      }
      else {
        dsFMDM.setBigDecimal("JYGS_VAL", new BigDecimal(sValue));
//		dsFMDM.setInt("JYGS_VAL", Integer.parseInt(sValue));
      }
      setModified(true);
    }
    //比较符
    else if (e.getSource().equals(this.jcbsymbol)) {
      if (seleDS == dsJYGS) {
        oldValue = dsJYGS.getString("JYGS_EXP");
        if (oldValue != null && oldValue.length() > 1) {
          oldValue = oldValue.substring(1, 2);
        }
      }
      else {
		  bdTmp = dsFMDM.getBigDecimal("JYGS_BJ");
		  oldValue = String.valueOf(bdTmp.intValue());
//		  oldValue = String.valueOf(dsFMDM.getInt("JYGS_BJ"));
      }
      sValue = e.getItem().toString();
      if (sValue.equals(oldValue)) {
        return;
      }
      if (seleDS == dsJYGS) {
        sExp = dsJYGS.getString("JYGS_EXP");
        if (sExp.length() > 1) {
          sExp = sExp.substring(0, 1) + sValue + sExp.substring(2);
        }
        else {
          sExp = jcbvalue.getSelectedItem().toString() + sValue;
        }
        dsJYGS.setString("JYGS_EXP", sExp);
      }
      else {
        dsFMDM.setString("JYGS_BJ", sValue);
      }
      setModified(true);
    }
  }

  /**
   * 双击时，把所选内容贴到所公式中
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    if (!jtfgsx.isEnabled()) {
      return;
    }
    String name, sValue = "";
    if (e.getClickCount() == 2) {
      if (e.getSource().equals(jfmgstab) || e.getSource().equals(jfhtab)) {
        JTable tab = (JTable) e.getSource();
        sValue = tab.getValueAt(tab.getSelectedRow(), tab.getSelectedColumn()).toString();
        jtfgsx.setText(sValue + jtfgsx.getText());
        jtfgsx.requestFocus();
      }
      else if (e.getSource().equals(jfmdmtab)) {
        sValue = jfmdmtab.getValueAt(jfmdmtab.getSelectedRow(), 1).toString();
        jtfgsx.setText(sValue + jtfgsx.getText());
        jtfgsx.requestFocus();
      }
      //如果是报表，则取报表所选内容
      else if (mReportBook != null && e.getSource().equals(mReportBook.ReportView)) {
        try {
          sValue = mReportBook.ReportView.formatRCNr(mReportBook.ReportView.getSelStartRow(), mReportBook.ReportView.getSelStartCol(), false);
        }
        catch (Exception ee) {}
        if (sValue == null || sValue.trim().equals("")) {
          return;
        }
        sValue = "BB(" + mBbbh + "," + sValue + ")";
        jtfgsx.setText(sValue + jtfgsx.getText());
        jtfgsx.requestFocus();
      }
    }
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  void jTab_stateChanged(ChangeEvent e) {
    String bh = "",mc = "";
    if (jTab.getSelectedIndex() == 1) {
      if(dsBBZD.rowCount() > 0){
        bh = dsBBZD.getString("BBZD_BH");
        mc = dsBBZD.getString("BBZD_MC");
      }
      if(!bh.equals("") && !bh.equals("FMDM") && !bh.equals(mBbbh)){
        bbbhTextField.setText(bh + "-" + mc);
        mBbbh = bh;
        Application.BeginWaitCursor();
        this.openReportBook(mBbbh);
        Application.EndWaitCursor();
      }
    }
  }

  /**
   * 设置修改标志
   * @param bMod boolean
   */
  private void setModified(boolean bMod) {
    bModified = bMod;
  }
}

class selfTabModel
    extends AbstractTableModel {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
  Vector value = new Vector();
  String[] colNames = null;
  public selfTabModel(Vector v, String[] cols) {
    value = v;
    colNames = cols;
  }

  public void setValue(Vector v) {
    value = v;
  }

  public Object getObjectAt(int r) {
    if (r >= value.size() || r == -1)return null;
    return value.get(r);
  }

  public Object getValueAt(int r, int c) {
    if (r != -1 && r < value.size() && c != -1 && c < colNames.length) {
      String[] item = (String[]) value.get(r);
      return item[c];
    }
    return "";
  }

  public String getColumnName(int columnIndex) {
    if (columnIndex != -1 && columnIndex < colNames.length)
      return colNames[columnIndex];
    return "";
  }

  public int getRowCount() {
    return value.size();
  }

  public int getColumnCount() {
    return colNames.length;
  }

}

class JAuditFunctionDialog_jTab_changeAdapter
    implements javax.swing.event.ChangeListener {
  JAuditFunctionDialog adaptee;

  JAuditFunctionDialog_jTab_changeAdapter(JAuditFunctionDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.jTab_stateChanged(e);
  }

}
