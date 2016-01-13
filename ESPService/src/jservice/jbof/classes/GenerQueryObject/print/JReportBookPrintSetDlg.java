package jservice.jbof.classes.GenerQueryObject.print;

import java.util.*;
import javax.print.*;
import javax.print.attribute.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import com.efounder.eai.data.JParamObject;
import com.f1j.swing.*;
import jcomponent.custom.swing.*;
import jfoundation.gui.window.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JReportBookPrintSetDlg
    extends JFrameDialog
    implements ActionListener, Runnable {
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    protected JPanel jPanel1 = new JPanel();
    protected JPanel jPanel2 = new JPanel();
    protected JPanel jPanel3 = new JPanel();
    protected JPanel jPanel4 = new JPanel();
    protected JPanel jPanel5 = new JPanel();
    protected JPanel jPanel6 = new JPanel();
    protected VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    protected JLabel jLabel1 = new JLabel();
    protected JComboBox cbPrintList = new JComboBox();
    protected JButton bnPrintAttrib = new JButton();
    protected JLabel jLabel2 = new JLabel();
    protected FlowLayout flowLayout2 = new FlowLayout();
    protected JLabel jLabel3 = new JLabel();
    protected JLabel jLabel4 = new JLabel();
    protected JLabel jLabel5 = new JLabel();
    protected FlowLayout flowLayout3 = new FlowLayout();
    protected FlowLayout flowLayout4 = new FlowLayout();
    protected FlowLayout flowLayout5 = new FlowLayout();
    protected JPanel jPanel7 = new JPanel();
    protected JPanel jPanel8 = new JPanel();
    protected JPanel jPanel9 = new JPanel();
    protected BorderLayout borderLayout2 = new BorderLayout();
    protected JPanel jPanel10 = new JPanel();
    protected JPanel jPanel11 = new JPanel();
    protected BorderLayout borderLayout3 = new BorderLayout();
    protected JPanel jPanel12 = new JPanel();
    protected JRadioButton rbPrintAll = new JRadioButton();
    protected VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    protected JPanel jPanel13 = new JPanel();
    protected JRadioButton rbPrintPage = new JRadioButton();
    protected FlowLayout flowLayout7 = new FlowLayout();
    protected JLabel jLabel6 = new JLabel();
    protected JSpinner spnBeginPage = new JSpinner();
    protected JLabel jLabel7 = new JLabel();
    protected JSpinner spnEndPage = new JSpinner();
    protected TitledBorder titledBorder1;
    protected Border border1;
    protected TitledBorder titledBorder2;
    protected Border border2;
    protected TitledBorder titledBorder3;
    protected Border border3;
    protected TitledBorder titledBorder4;
    protected Border border4;
    protected TitledBorder titledBorder5;
    protected JPanel jPanel14 = new JPanel();
    protected JRadioButton rbPrintF1Book = new JRadioButton();
    protected JRadioButton rbPrintSelectArea = new JRadioButton();
    protected VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
    protected Border border5;
    protected TitledBorder titledBorder6;
    protected VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
    protected JLabel lbPrinterStatus = new JLabel();
    protected JLabel lbPrinterType = new JLabel();
    protected JLabel lbPrinterPos = new JLabel();
    protected JLabel lbPrinterMess = new JLabel();
    JPanel jPanel17 = new JPanel();
    FlowLayout flowLayout9 = new FlowLayout();
    JComboBox cbPaperList = new JComboBox();
    JPanel jPanel18 = new JPanel();
    FlowLayout flowLayout10 = new FlowLayout();
    JSpinner spnPrintScale1 = new JSpinner();
    JLabel jLabel15 = new JLabel();
    JRadioButton rbPrintScale1 = new JRadioButton();
    JPanel jPanel19 = new JPanel();
    JRadioButton rbPrintScale2 = new JRadioButton();
    FlowLayout flowLayout11 = new FlowLayout();
    JPanel jPanel20 = new JPanel();
    FlowLayout flowLayout12 = new FlowLayout();
    JSpinner spnPrintScale2Width = new JSpinner();
    JLabel jLabel14 = new JLabel();
    JSpinner spnPrintScale2Height = new JSpinner();
    JLabel jLabel16 = new JLabel();
    BorderLayout borderLayout4 = new BorderLayout();
    JPanel jPanel15 = new JPanel();
    JSpinner spnPrintPageNumber = new JSpinner();
    FlowLayout flowLayout8 = new FlowLayout();

//    PrintService CurrentPrintService = null;
//    PrinterJob CurrentPrinterJob = null;
    PrintRequestAttributeSet CurrentPrintRequestAttribute = new HashPrintRequestAttributeSet();
    JBook book = null;
    Vector ReportList = null;
    Vector AddList = null;

    JCheckBox cbReportDefaultPage = new JCheckBox();
    JRadioButton jRadioButton8 = new JRadioButton();
    JRadioButton jRadioButton9 = new JRadioButton();
    JPanel jPanel21 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();
    JPanel jPanel22 = new JPanel();
    JButton bnOK = new JButton();
    JButton bnCancel = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    JProgressBar pbProgressBar = new JProgressBar();
    JCheckBox cbPrintDefaultSize = new JCheckBox();
    JPanel jPanel23 = new JPanel();
    FlowLayout flowLayout13 = new FlowLayout();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    ButtonGroup buttonGroup2 = new ButtonGroup();
    ButtonGroup buttonGroup3 = new ButtonGroup();
    JPanel jPanel24 = new JPanel();
    FlowLayout flowLayout14 = new FlowLayout();
    JRadioButton rbVPrint = new JRadioButton();
    JRadioButton rbHPrint = new JRadioButton();
    ButtonGroup buttonGroup4 = new ButtonGroup();
    BorderLayout borderLayout6 = new BorderLayout();
    Border border6;
    Border border7;
    Border border8;
    Border border9;
    GridLayout gridLayout1 = new GridLayout();
    JCheckBox rbNoColor = new JCheckBox();
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JReportBookPrintSetDlg(Frame frame, String title, boolean modal,JBook book) {
        super(JActiveDComDM.MainApplication.MainWindow, title, modal);
        this.book = book;
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JReportBookPrintSetDlg(JBook book) {
        this(JActiveDComDM.MainApplication.MainWindow, "打印设置", true,book);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "打印机");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder3 = new TitledBorder(border2, "范围");
        border3 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder4 = new TitledBorder(border3, "打印");
        border4 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "设置");
        border5 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder6 = new TitledBorder(border5, "打印");
        border6 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border7 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border8 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "纸张"), BorderFactory.createEmptyBorder(5, 5, 5, 5));
        border9 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
        panel1.setLayout(borderLayout1);
        jPanel1.setLayout(verticalFlowLayout1);
        verticalFlowLayout1.setHgap(0);
        verticalFlowLayout1.setVgap(0);
        jLabel1.setBorder(border7);
        jLabel1.setText("名称:");
        jPanel6.setLayout(borderLayout4);
        bnPrintAttrib.setPreferredSize(new Dimension(80, 15));
        bnPrintAttrib.setMnemonic('R');
        bnPrintAttrib.setSelectedIcon(null);
        bnPrintAttrib.setText("属性(R)");
//    cbPrintList.setBorder(border7);
//        cbPrintList.setMinimumSize(new Dimension(31, 25));
        cbPrintList.setPreferredSize(new Dimension(320, 25));
        cbPrintList.setPopupVisible(false);
        jLabel2.setBorder(border7);
        jLabel2.setText("状态:");
        jPanel5.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(0);
        flowLayout2.setVgap(0);
        jLabel3.setBorder(border7);
        jLabel3.setText("类型:");
        jLabel4.setBorder(border7);
        jLabel4.setText("位置:");
        jLabel5.setBorder(border7);
        jLabel5.setText("备注:");
        jPanel4.setLayout(flowLayout3);
        jPanel3.setLayout(flowLayout4);
        jPanel2.setLayout(flowLayout5);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setHgap(0);
        flowLayout3.setVgap(0);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout4.setHgap(0);
        flowLayout4.setVgap(0);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        flowLayout5.setHgap(0);
        flowLayout5.setVgap(0);
        jPanel8.setLayout(borderLayout2);
        jPanel9.setLayout(borderLayout5);
        jPanel7.setLayout(borderLayout3);
        rbPrintAll.setMnemonic('A');
        rbPrintAll.setText("全部(A)");
        jPanel12.setLayout(verticalFlowLayout2);
        rbPrintPage.setMnemonic('G');
        rbPrintPage.setText("页(G)");
        jPanel13.setLayout(flowLayout7);
        jLabel6.setBorder(border7);
        jLabel6.setDisplayedMnemonic('F');
        jLabel6.setLabelFor(spnBeginPage);
        jLabel6.setText("从(F)");
        flowLayout7.setHgap(0);
        flowLayout7.setVgap(0);
        jLabel7.setBorder(border7);
        jLabel7.setDisplayedMnemonic('T');
        jLabel7.setLabelFor(spnEndPage);
        jLabel7.setText("到(T)");
        jPanel10.setLayout(gridLayout1);
        jPanel11.setLayout(verticalFlowLayout5);
        jPanel1.setBorder(titledBorder2);
        jPanel12.setBorder(titledBorder3);
        jPanel11.setBorder(border8);
        rbPrintF1Book.setMnemonic('E');
        rbPrintF1Book.setText("整个工作薄(E)");
        rbPrintSelectArea.setText("设置区域(N)");
        jPanel14.setLayout(verticalFlowLayout4);
        jPanel14.setBorder(titledBorder6);
        verticalFlowLayout4.setHgap(0);
        verticalFlowLayout4.setVgap(0);
        verticalFlowLayout5.setHgap(0);
        verticalFlowLayout5.setVgap(0);
        verticalFlowLayout5.setHorizontalFill(true);
        verticalFlowLayout5.setVerticalFill(false);
        spnBeginPage.setPreferredSize(new Dimension(55, 26));
        spnEndPage.setPreferredSize(new Dimension(55, 26));
        jPanel17.setLayout(flowLayout9);
        flowLayout9.setAlignment(FlowLayout.LEFT);
        flowLayout9.setHgap(0);
        flowLayout9.setVgap(0);
        cbPaperList.setPreferredSize(new Dimension(140, 25));
        jPanel18.setLayout(flowLayout10);
        flowLayout10.setAlignment(FlowLayout.LEFT);
        flowLayout10.setHgap(0);
        flowLayout10.setVgap(0);
        jLabel15.setBorder(border7);
        jLabel15.setText("%正常尺寸");
        rbPrintScale1.setText("缩放:");
        spnPrintScale1.setMinimumSize(new Dimension(31, 26));
        spnPrintScale1.setPreferredSize(new Dimension(55, 26));
        rbPrintScale2.setText("调整:");
        jPanel19.setLayout(flowLayout11);
        flowLayout11.setAlignment(FlowLayout.LEFT);
        flowLayout11.setHgap(0);
        flowLayout11.setVgap(0);
        flowLayout12.setAlignment(FlowLayout.RIGHT);
        flowLayout12.setHgap(0);
        flowLayout12.setVgap(0);
        jLabel14.setBorder(border7);
        jLabel14.setText("页宽");
        jLabel16.setText("页高");
        spnPrintScale2Width.setMinimumSize(new Dimension(31, 26));
        spnPrintScale2Width.setPreferredSize(new Dimension(55, 26));
        spnPrintScale2Height.setMinimumSize(new Dimension(31, 26));
        spnPrintScale2Height.setOpaque(true);
        spnPrintScale2Height.setPreferredSize(new Dimension(55, 26));
        jPanel15.setLayout(flowLayout8);
        spnPrintPageNumber.setMinimumSize(new Dimension(50, 26));
        spnPrintPageNumber.setPreferredSize(new Dimension(55, 26));
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout8.setHgap(0);
        flowLayout8.setVgap(0);
        cbReportDefaultPage.setToolTipText("");
        cbReportDefaultPage.setActionCommand("");
        cbReportDefaultPage.setText("缺省纸张设置");
        jRadioButton8.setEnabled(false);
        jRadioButton8.setToolTipText("");
        jRadioButton8.setText("份数:");
        jRadioButton9.setEnabled(false);
        jRadioButton9.setText("纸张:");
        lbPrinterStatus.setText("");
        lbPrinterType.setText("");
        lbPrinterPos.setText("");
        lbPrinterMess.setText("");
        verticalFlowLayout2.setHgap(0);
        verticalFlowLayout2.setVgap(0);
        jPanel9.setDebugGraphicsOptions(0);
        bnOK.setText("确定");
        bnCancel.setText("取消");
        jPanel22.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.CENTER);
        flowLayout1.setHgap(5);
        flowLayout1.setVgap(0);
        jPanel21.setLayout(borderLayout6);
        cbPrintDefaultSize.setSelectedIcon(null);
        cbPrintDefaultSize.setText("缺省缩放设置");
        jPanel23.setLayout(flowLayout13);
        flowLayout13.setAlignment(FlowLayout.LEFT);
        flowLayout13.setHgap(0);
        flowLayout13.setVgap(0);
        jPanel24.setLayout(flowLayout14);
        flowLayout14.setAlignment(FlowLayout.LEFT);
        flowLayout14.setHgap(0);
        flowLayout14.setVgap(0);
        rbVPrint.setText("纵向");
        rbHPrint.setText("横向");
        panel1.setBorder(border6);
        jPanel10.setBorder(border8);
        jPanel24.setBorder(border9);
        jPanel17.setBorder(border9);
        jPanel23.setBorder(border9);
        jPanel18.setBorder(border9);
        jPanel19.setBorder(border9);
        jPanel20.setBorder(border9);
        jPanel15.setBorder(border9);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(2);
        rbNoColor.setSelected(true);
        rbNoColor.setText("单色打印(C)");
        getContentPane().add(panel1);
        panel1.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jPanel6, null);
        jPanel6.add(jLabel1, BorderLayout.WEST);
        jPanel6.add(cbPrintList, BorderLayout.CENTER);
        jPanel6.add(bnPrintAttrib, BorderLayout.EAST);
        jPanel1.add(jPanel5, null);
        jPanel1.add(jPanel4, null);
        jPanel4.add(jLabel3, null);
        jPanel1.add(jPanel3, null);
        jPanel3.add(jLabel4, null);
        jPanel1.add(jPanel2, null);
        jPanel2.add(jLabel5, null);
        panel1.add(jPanel7, BorderLayout.CENTER);
        jPanel7.add(jPanel10, BorderLayout.WEST);
        jPanel10.add(jPanel12, null);
        jPanel12.add(rbPrintAll, null);
        jPanel12.add(jPanel13, null);
        jPanel13.add(rbPrintPage, null);
        jPanel13.add(jLabel6, null);
        jPanel13.add(spnBeginPage, null);
        jPanel13.add(jLabel7, null);
        jPanel13.add(spnEndPage, null);
        jPanel10.add(jPanel14, null);
        jPanel14.add(rbPrintSelectArea, null);
        jPanel14.add(rbPrintF1Book, null);
        jPanel7.add(jPanel11, BorderLayout.CENTER);
        jPanel15.add(jRadioButton8, null);
        jPanel15.add(spnPrintPageNumber, null);
        jPanel11.add(jPanel24, null);
        jPanel24.add(cbReportDefaultPage, null);
        jPanel11.add(jPanel17, null);
        jPanel11.add(jPanel23, null);
        jPanel23.add(cbPrintDefaultSize, null);
        jPanel17.add(jRadioButton9, null);
        jPanel17.add(cbPaperList, null);
        panel1.add(jPanel8, BorderLayout.SOUTH);
        jPanel8.add(jPanel9, BorderLayout.CENTER);
        jPanel9.add(jPanel22, BorderLayout.EAST);
        jPanel22.add(bnOK, null);
        jPanel22.add(bnCancel, null);
        jPanel9.add(jPanel21, BorderLayout.CENTER);
        jPanel21.add(pbProgressBar, BorderLayout.CENTER);
        jPanel5.add(jLabel2, null);
        jPanel5.add(lbPrinterStatus, null);
        jPanel4.add(lbPrinterType, null);
        jPanel3.add(lbPrinterPos, null);
        jPanel2.add(lbPrinterMess, null);
        jPanel11.add(jPanel18, null);
        jPanel18.add(rbPrintScale1, null);
        jPanel18.add(spnPrintScale1, null);
        jPanel18.add(jLabel15, null);
        jPanel11.add(jPanel19, null);
        jPanel19.add(rbPrintScale2, null);
        jPanel19.add(spnPrintScale2Width, null);
        jPanel19.add(jLabel14, null);
        jPanel19.add(spnPrintScale2Height, null);
        jPanel19.add(jLabel16, null);
        jPanel11.add(jPanel15, null);
        setTitle("打印");
        this.setResizable(false);
        InitActionListener();
        buttonGroup2.add(rbPrintAll);
        buttonGroup2.add(rbPrintPage);
        buttonGroup3.add(rbPrintSelectArea);
        buttonGroup3.add(rbPrintF1Book);
        jPanel24.add(rbVPrint, null);
        jPanel24.add(rbHPrint, null);
        buttonGroup4.add(rbVPrint);
        buttonGroup4.add(rbHPrint);
        jPanel14.add(rbNoColor, null);
        InitDialog();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitActionListener() {
        bnPrintAttrib.addActionListener(this);
        cbPrintList.addActionListener(this);
        bnOK.addActionListener(this);
        bnCancel.addActionListener(this);
        cbReportDefaultPage.addActionListener(this);
        this.cbPrintDefaultSize.addActionListener(this);
        this.rbPrintScale1.addActionListener(this);
        this.rbPrintScale2.addActionListener(this);
        buttonGroup1.add(rbPrintScale1);
        buttonGroup1.add(rbPrintScale2);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void InitDialog() {
        InitPrinterList();
        InitPaperList();
        InitCustomObject();
        InitValue();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitCustomObject() {
        if (CustomObject instanceof JBook) {
            book = (JBook)this.CustomObject;
        }
        if (CustomObject instanceof Properties) {
            Properties p = (Properties) CustomObject;
            ReportList = (Vector) p.get("BBList");
            AddList = (Vector) p.get("AddList");
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitValue() {
        if (book != null) {
            InitValueWithReportView();
        }
        else {
            InitValueWithnotReportView();
        }
        PO = new JParamObject();
        spnBeginPage.setValue(new Integer(1));
        spnEndPage.setValue(new Integer(9999));
        spnPrintScale1.setValue(new Integer(100));
//        spnPrintScale2Width.setValue(new Integer(1));
        spnPrintScale2Width.setValue(new Integer(book.getPrintScaleFitHPages()));
//        spnPrintScale2Height.setValue(new Integer(1));
        spnPrintScale2Height.setValue(new Integer(book.getPrintScaleFitVPages()));
        spnPrintPageNumber.setValue(new Integer(1));
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitValueWithReportView() {
        // 范围
        rbPrintAll.setSelected(true);
        rbPrintAll.setEnabled(true);
        rbPrintPage.setSelected(false);
        rbPrintPage.setEnabled(true);
        // 打印
        rbPrintSelectArea.setSelected(true);
        rbPrintSelectArea.setEnabled(true);
        rbPrintF1Book.setSelected(false);
        rbPrintF1Book.setEnabled(true);
        rbNoColor.setSelected(false);
        rbNoColor.setEnabled(true);
        // 设置
        InitPrintSet();
        //
//    this.bnPrintView.setVisible(true);
        this.pbProgressBar.setVisible(false);
//    this.lbProcessName.setVisible(false);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitValueWithnotReportView() {
        // 范围
        rbPrintAll.setSelected(true);
        rbPrintAll.setEnabled(false);
        rbPrintPage.setSelected(false);
        rbPrintPage.setEnabled(false);
        // 打印
        rbPrintSelectArea.setSelected(false);
        rbPrintSelectArea.setEnabled(false);
        rbPrintF1Book.setSelected(false);
        rbPrintF1Book.setEnabled(false);
        rbNoColor.setSelected(false);
        rbNoColor.setEnabled(false);
        // 设置
        InitPrintSet();
        //
//    this.bnPrintView.setVisible(false);
        this.pbProgressBar.setVisible(false);
//    this.lbProcessName.setVisible(true);
    }

    void InitPrintSet() {
        cbReportDefaultPage.setSelected(true);
        this.cbPaperList.setEnabled(false);
        rbVPrint.setEnabled(false);
        rbHPrint.setEnabled(false);
        this.rbVPrint.setSelected(true);
        this.cbPrintDefaultSize.setSelected(true);
        this.rbPrintScale1.setSelected(true);
        setPrintScale(false);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitPaperList() {
        ImageIcon PaperIcon, LetterIcon;
        PaperIcon = JXMLResource.LoadImageIcon(this, "paper.gif");
        LetterIcon = JXMLResource.LoadImageIcon(this, "letter.gif");
        JIConListCellRenderer ICR = new JIConListCellRenderer(PaperIcon);
        this.cbPaperList.setRenderer(ICR);
        JPrintPaperStub PPS;
        PPS = new JPrintPaperStub(PaperIcon, "Paper 10\"x14\"", JBook.kPaper10x14);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Paper 11\"x17\"", JBook.kPaper11x17);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "A3", JBook.kPaperA3);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "A4", JBook.kPaperA4);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        cbPaperList.setSelectedItem(PPS);
        PPS = new JPrintPaperStub(PaperIcon, "A4Small", JBook.kPaperA4Small);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "A5", JBook.kPaperA5);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "B4", JBook.kPaperB4);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "B5", JBook.kPaperB5);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "CSheet", JBook.kPaperCSheet);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "DSheet", JBook.kPaperDSheet);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope 10", JBook.kPaperEnv10);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope 11", JBook.kPaperEnv11);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope 12", JBook.kPaperEnv12);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope 14", JBook.kPaperEnv14);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope 9", JBook.kPaperEnv9);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope B4", JBook.kPaperEnvB4);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope B5", JBook.kPaperEnvB5);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope B6", JBook.kPaperEnvB6);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope C3", JBook.kPaperEnvC3);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope C4", JBook.kPaperEnvC4);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope C5", JBook.kPaperEnvC5);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope C6", JBook.kPaperEnvC6);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope C65", JBook.kPaperEnvC65);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope DL", JBook.kPaperEnvDL);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope Italy", JBook.kPaperEnvItaly);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());

        PPS = new JPrintPaperStub(LetterIcon, "envelope Monarch", JBook.kPaperEnvMonarch);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(LetterIcon, "envelope Personal", JBook.kPaperEnvPersonal);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "ESheet", JBook.kPaperESheet);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());

        PPS = new JPrintPaperStub(PaperIcon, "Executive", JBook.kPaperExecutive);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());

        PPS = new JPrintPaperStub(PaperIcon, "Executive", JBook.kPaperExecutive);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "FanfoldLglGerman", JBook.kPaperFanfoldLglGerman);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "FanfoldStdGerman", JBook.kPaperFanfoldStdGerman);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "FanfoldUS", JBook.kPaperFanfoldUS);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Folio ", JBook.kPaperFolio);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Ledger", JBook.kPaperLedger);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Legal", JBook.kPaperLegal);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Letter ", JBook.kPaperLetter);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "LetterSmall", JBook.kPaperLetterSmall);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Note", JBook.kPaperNote);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Quarto", JBook.kPaperQuarto);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Statement", JBook.kPaperStatement);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
        PPS = new JPrintPaperStub(PaperIcon, "Tabloid ", JBook.kPaperTabloid);
        cbPaperList.insertItemAt(PPS, cbPaperList.getItemCount());
//    PPS = new JPrintPaperStub(PaperIcon,"",0);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitPrinterList() {
        PrintService[] Printers;
        ImageIcon II = JXMLResource.LoadImageIcon(this, "print16.gif");
        JIConListCellRenderer ICR = new JIConListCellRenderer(II);
        cbPrintList.setRenderer(ICR);
        Printers = PrinterJob.lookupPrintServices();
        for (int i = 0; i < Printers.length; i++) {
            this.cbPrintList.insertItemAt(Printers[i], i);
        }
        if ( cbPrintList.getItemCount() > 0 ) cbPrintList.setSelectedIndex(0);
//        if (CurrentPrinterJob == null) {
//            CurrentPrinterJob = PrinterJob.getPrinterJob();
//        }
//        CurrentPrintService = CurrentPrinterJob.getPrinterJob().getPrintService();
//        PrintServiceAttributeSet ps = CurrentPrintService.getAttributes();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        Object Sender = e.getSource();
        if (Sender == this.bnPrintAttrib) {
            ProcessPrintAttrib();
        }
        if (Sender == this.cbPrintList) {
            ProcessPrintList();
        }
        if (Sender == this.bnOK) {
            ProcessOK();
        }
        if (Sender == this.bnCancel) {
            ProcessCancel();
        }
        if (Sender == cbReportDefaultPage) {
            ProcessReportDefaultPage();
        }
        if (Sender == this.cbPrintDefaultSize) {
            ProcessPrintDefaultSize();
        }
        if (Sender == this.rbPrintScale1 || Sender == this.rbPrintScale2) {
            ProcessPrintScale();
        }
    }

    void ProcessPrintScale() {
        if (!rbPrintScale1.isSelected() && !rbPrintScale2.isSelected()) {
            rbPrintScale1.setSelected(true);
        }
        if (rbPrintScale1.isSelected()) {
            this.spnPrintScale1.setEnabled(true);
            this.spnPrintScale2Width.setEnabled(false);
            this.spnPrintScale2Height.setEnabled(false);
        }
        else {
            this.spnPrintScale1.setEnabled(false);
            this.spnPrintScale2Width.setEnabled(true);
            this.spnPrintScale2Height.setEnabled(true);
        }
    }

    void ProcessPrintDefaultSize() {
        setPrintScale(!cbPrintDefaultSize.isSelected());
        if (!this.cbPrintDefaultSize.isSelected()) {
            ProcessPrintScale();
        }
    }

    void setPrintScale(boolean value) {
        this.rbPrintScale1.setEnabled(value);
        this.rbPrintScale2.setEnabled(value);
        this.spnPrintScale1.setEnabled(value);
        this.spnPrintScale2Width.setEnabled(value);
        this.spnPrintScale2Height.setEnabled(value);
    }

    void ProcessReportDefaultPage() {
        this.cbPaperList.setEnabled(!this.cbReportDefaultPage.isSelected());
        this.rbHPrint.setEnabled(!this.cbReportDefaultPage.isSelected());
        this.rbVPrint.setEnabled(!this.cbReportDefaultPage.isSelected());
    }

    void ProcessOK() {
        if (book != null) {
            ProcessReportViewOK();
            this.OnOk();
        }
        if (this.ReportList != null) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }
    private PrintService getPrintService() {
      PrintService ps = null;
      if ( cbPrintList.getItemCount() > 0 ) {
        ps = (PrintService)cbPrintList.getSelectedItem();
      }
      return ps;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessReportViewOK() {
            try {
              PrintService PS = this.getPrintService();
              PrinterJob Job = PrinterJob.getPrinterJob();
              Job.setJobName("Skyline");
              Job.setPrintService(PS);
              // 1、将当前JBook中有关页面设置的参数保存到PO中
              SaveBookPrintParamToPO(PO);
              // 2、将当前打印设置对话框中的参数设置到当前JBook
              SaveDlgParamToBook();
              // 3、使用创建的Job打印
              book.filePrint(false, Job);
              // 打印完后，恢复JBook以前的设置
              LoadPrintParamObject(PO);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
    void SaveDlgParamToBook() throws Exception {
      // 设置打印份数
      book.setPrintNumberOfCopies(((Integer)this.spnPrintPageNumber.getValue()).intValue());
      // 如果不使用默认的纸张设置,则将对话框中用户设置的参数应用到JBook中
      if ( !cbReportDefaultPage.isSelected() ) {
        // 设置当前选择的纸张大小
        JPrintPaperStub PPS = (JPrintPaperStub)this.cbPaperList.getSelectedItem();
        book.setPrintPaperSize(PPS.PaperIndex);
        // 设置纵向与横向
        book.setPrintLandscape(rbHPrint.isSelected());
      }
      // 如果不使用默认的纸张缩放，则将对话框中的用户设置的参数应用到JBook中
      if ( !cbPrintDefaultSize.isSelected() ) {
        // 设置是否使用缩放比例
        book.setPrintScaleFitToPage(rbPrintScale1.isSelected());
        // 设置缩放比例
        book.setPrintScale(((Integer)spnPrintScale1.getValue()).intValue());
        // 设置是否自动页号
        book.setPrintAutoPageNumbering(rbPrintAll.isSelected());
        // 设置开始页号
        book.setPrintStartPageNumber(((Integer)spnBeginPage.getValue()).intValue());
        // 设置横向纸张张数
        book.setPrintScaleFitHPages(((Integer)spnPrintScale2Width.getValue()).intValue());
        // 设置纵向纸张张数
        book.setPrintScaleFitVPages(((Integer)spnPrintScale2Height.getValue()).intValue());
      }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
     void SaveBookPrintParamToPO(JParamObject PO) {
       PO.SetIntByParamName("Save_PrintPaperSize",book.getPrintPaperSize());
       PO.SetIntByParamName("Save_PrintScale",book.getPrintScale());
       PO.SetIntByParamName("Save_PrintScaleFitHPages",book.getPrintScaleFitHPages());
       PO.SetIntByParamName("Save_PrintScaleFitVPages",book.getPrintScaleFitVPages());
       PO.SetIntByParamName("Save_PrintNumberOfCopies",book.getPrintNumberOfCopies());
       PO.SetIntByParamName("Save_isPrintScaleFitToPage",book.isPrintScaleFitToPage()?1:0);
       PO.SetIntByParamName("Save_isPrintLandscape",book.isPrintLandscape()?1:0);
       PO.SetIntByParamName("Save_isPrintAutoPageNumbering",book.isPrintAutoPageNumbering()?1:0);
       PO.SetIntByParamName("Save_PrintStartPageNumber",book.getPrintStartPageNumber());
     }
     void LoadPrintParamObject(JParamObject PO) {
       try {
         book.setPrintPaperSize( (short) PO.GetIntByParamName("Save_PrintPaperSize"));
         book.setPrintScale((short)PO.GetIntByParamName("Save_PrintScale"));
         book.setPrintScaleFitHPages((short)PO.GetIntByParamName("Save_PrintScaleFitHPages"));
         book.setPrintScaleFitVPages((short)PO.GetIntByParamName("Save_PrintScaleFitVPages"));
         book.setPrintNumberOfCopies((short)PO.GetIntByParamName("Save_PrintNumberOfCopies"));
         book.setPrintScaleFitToPage(PO.GetIntByParamName("Save_isPrintScaleFitToPage")==1?true:false);
         book.setPrintLandscape(PO.GetIntByParamName("Save_isPrintLandscape")==1?true:false);
         book.setPrintAutoPageNumbering(PO.GetIntByParamName("Save_isPrintAutoPageNumbering")==1?true:false);
         book.setPrintStartPageNumber(PO.GetIntByParamName("Save_PrintStartPageNumber"));

       } catch ( Exception e ) {
         e.printStackTrace();
       }

     }

    public void run() {
        ProcessReportListOK();
    }

    void ProcessReportListOK() {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessCancel() {
        this.OnCancel();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessPrintList() {
//        try {
//            CurrentPrintService = (PrintService)this.cbPrintList.getSelectedItem();
//            CurrentPrinterJob.setPrintService(this.CurrentPrintService);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    PrintServiceAttributeSet set = CurrentPrintService.getAttributes();
//    Attribute[] a = set.toArray();
//    for(int i=0;i<a.length;i++) {
//      System.out.print(a[i].getName());
//      System.out.print(a[i].getCategory());
//      System.out.println(a[i]);
//    }
//    sun.awt.windows.WPrinterJob wjob;
//    String nm = System.getProperty("java.awt.printerjob", null);
//    System.out.println(nm);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessPrintAttrib() {
//        if (CurrentPrinterJob == null) {
//            CurrentPrinterJob = PrinterJob.getPrinterJob();
//        }
//        if (CurrentPrinterJob != null) {
//            if (CurrentPrintService != null) {
//                try {
//                    CurrentPrinterJob.setPrintService(CurrentPrintService);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (CurrentPrinterJob.printDialog()) {
//                CurrentPrintService = CurrentPrinterJob.getPrintService();
//        cbPrintList.removeActionListener(this);
//                cbPrintList.setSelectedItem(CurrentPrintService);
//        cbPrintList.addActionListener(this);
//            }
//        }
    }

}
