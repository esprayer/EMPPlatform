package jreport.swing.classes.wizard;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import jfoundation.gui.window.classes.*;
import jfoundation.dataobject.classes.JParamObject;
import jframework.foundation.classes.JActiveDComDM;
import com.f1j.swing.SelectionChangedListener;
import com.f1j.swing.SelectionChangedEvent;
import jframework.resource.classes.JXMLResource;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JFunctionWizardDialog
    extends JFrameDialog
    implements CaretListener, FocusListener, SelectionChangedListener, WindowListener, ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
    //data
    private String mFuncString = "";
    //
    private JPanel panel1 = new JPanel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JPanel pnFuncPanel = new JPanel();
    private JPanel pnFuncDes2 = new JPanel();
    private JPanel pnParamDes2 = new JPanel();
    private Border border1;
    private TitledBorder tbFuncBorder;
    private Border border2;
    private TitledBorder tbFuncDesBorder;
    private Border border3;
    private TitledBorder tbParamDesBorder;
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextPane edFuncDes = new JTextPane();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private BorderLayout borderLayout2 = new BorderLayout();
    private GridLayout gridLayout1 = new GridLayout();
    private Border stdBorder = null;

    //
    public JFunctionWizardObject WizardObject = null;
    public JFunctionStub CurrentFS = null;
    public JCustomTextField CurrentTextField = null;
    public int SRow = 0, SCol = 0, ERow = 0, ECol = 0;
    public int phySRow = 0, phySCol = 0, phyERow = 0, phyECol = 0;
    Frame Mainframe;
    private JPanel pnFuncDes1 = new JPanel();
    private JPanel pnParamDes1 = new JPanel();
    private JTextPane edParamDes = new JTextPane();

    ImageIcon HelpIcon = null;
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    BorderLayout borderLayout3 = new BorderLayout();
    JButton bnSaveF1 = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel pnOp = new JPanel();
    JButton bnAddFunc = new JButton();
    JComboBox cbFuncList = new JComboBox();
    JButton bnClose = new JButton();
    JScrollPane jScrollPane3 = new JScrollPane();
    JTextField lbFuncDes1 = new JTextField();
    BorderLayout borderLayout4 = new BorderLayout();
    Border border4;
    Border border5;
    JTextField lbParamDes1 = new JTextField();
    GridLayout gridLayout2 = new GridLayout();

    /**
     * 为了支持自定义公式向导中显示什么函数而加，函数类别
     */
    public boolean isCustom;
    public HashMap mFuncMap;

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void DestroyFuncDefine(JFunctionStub FS) {
        Component[] OS;
        OS = pnFuncPanel.getComponents();
        for (int i = 0; i < OS.length; i++) {
            OS[i].removeFocusListener(this);
            if (OS[i] instanceof JCustomTextField) {
                ( (JCustomTextField) OS[i]).getButton().removeFocusListener(this);
            }
            pnFuncPanel.remove(OS[i]);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    String ProcessMacro(String Macro, JFunctionStub FS) {
        if (this.isCustom)
            return "";
        String Res = null;
        if (Macro == null) {
            return null;
        }
        if (Macro.startsWith("@") && Macro.endsWith("@")) {
            Macro = Macro.toUpperCase();
            if (Macro.equals("@ZRZX@")) {
                Res = ProcessMacroZRZX(Macro, FS);
            }
            if (Macro.equals("@BBBH@")) {
                Res = ProcessMacroBBBH(Macro, FS);
            }
            if (Macro.equals("@DOBJ@")) {
                Res = ProcessMacroDOBJ(Macro, FS);
            }
            if (Macro.equals("@COBJ@")) {
                Res = ProcessMacroCOBJ(Macro, FS);
            }
        }
        else {
            Res = Macro;
        }
        return Res;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    String ProcessMacroDOBJ(String Macro, JFunctionStub FS) {
        return FS.dobj;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    String ProcessMacroCOBJ(String Macro, JFunctionStub FS) {
        return FS.cobj;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    String ProcessMacroZRZX(String Macro, JFunctionStub FS) {
        JParamObject PO;
        String Res = null;
        PO = JParamObject.Create();
        Res = PO.GetValueByEnvName("CW_ZWZRZX");
        return Res;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    String ProcessMacroBBBH(String Macro, JFunctionStub FS) {
        return WizardObject.ReportModel.BBZD_BH;
    }

    void setFunctionTitle(JFunctionStub FS) {
        tbFuncBorder.setTitle(FS.toString() + res.getString("String_371"));
        this.tbFuncDesBorder.setTitle(FS.toString() + res.getString("String_372"));
        if (FS.des1 != null && FS.des1.trim().length() == 0) {
            FS.des1 = null;
        }
        if (FS.des1 != null) {
            this.pnFuncDes1.setVisible(true);
            lbFuncDes1.setText(FS.des1);
            lbFuncDes1.setCaretPosition(0);
        }
        else {
            this.pnFuncDes1.setVisible(false);
            lbFuncDes1.setText(FS.des1);
            lbFuncDes1.setCaretPosition(0);
        }
        if (FS.des2 != null && FS.des2.trim().length() == 0) {
            FS.des2 = null;
        }
        if (FS.des2 != null) {
            this.pnFuncDes2.setVisible(true);
            this.edFuncDes.setText(FS.des2);
        }
        else {
            this.pnFuncDes2.setVisible(false);
            this.edFuncDes.setText(FS.des2);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitFuncDefine(JFunctionStub FS) {

        CurrentFS = FS;

        // 清除以前的
        DestroyFuncDefine(FS);

        gridLayout1.setColumns(4);
        int rows = FS.ps / 2 + FS.ps % 2; //奇数和偶数处理
        gridLayout1.setRows(rows);

        JLabel lbName;
        JCustomTextField edValue;
        int i;
        Vector Params = FS.getParam();
        JLimitObjectStub LOS;
        String Param;
        FS.InitFunctionStub();
        for (i = 0; i < Params.size(); i++) {
            Param = (String) Params.get(i);
            LOS = (JLimitObjectStub) WizardObject.LimitObjectList.get(Param);
            if (LOS == null) {
                continue;
            }
            lbName = new JLabel();
            lbName.setText(LOS.text);
            lbName.setBorder(stdBorder);
            pnFuncPanel.add(lbName);
            edValue = new JCustomTextField(JCustomTextField.CUSTOM_TYPE_BUTTON);
            // 这里可以处理宏
            edValue.setText(ProcessMacro(LOS.defvalue, FS));
            // 设置图标
            edValue.setIcon(HelpIcon);
            pnFuncPanel.add(edValue);
            FS.PutParamValueCtrl(Param, edValue);
            edValue.LOS = LOS;
            LOS.FunctionStub = FS;
            edValue.addFocusListener(this);
            edValue.getButton().addFocusListener(this);
        }
        /**
         * 描述: 对于ps==3的处理,
         * 修改: Stephen(2004-05-19)
         */
        if (Params != null && Params.size() == 3) {
            pnFuncPanel.add(new JLabel(""));
            pnFuncPanel.add(new JLabel(""));
        }

        setFunctionTitle(FS);
        this.pack();
        this.repaint();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitcbFuncList() {
        cbFuncList.insertItemAt("SUM()", 0);
        cbFuncList.insertItemAt("AVERAGE()", 1);
        cbFuncList.insertItemAt("COUNT()", 2);
        cbFuncList.insertItemAt("+", 3);
        cbFuncList.insertItemAt("-", 4);
        cbFuncList.insertItemAt("*", 5);
        cbFuncList.insertItemAt("/", 6);
        cbFuncList.insertItemAt("(", 7);
        cbFuncList.insertItemAt(")", 8);
        cbFuncList.insertItemAt("&", 9);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JFunctionWizardDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        Mainframe = frame;
        try {
            setMenuShow(false);
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
    public JFunctionWizardDialog() {
        this(null, "", false);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void jbInit() throws Exception {
        stdBorder = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
        tbFuncBorder = new TitledBorder(border1, res.getString("String_386"));
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
        tbFuncDesBorder = new TitledBorder(border2, res.getString("String_387"));
        border3 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
        tbParamDesBorder = new TitledBorder(border3, res.getString("String_388"));
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, Color.gray), res.getString("String_390"));
        border4 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(103, 101, 98), new Color(148, 145, 140)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        border5 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel1.setLayout(verticalFlowLayout1);
        pnFuncPanel.setBorder(tbFuncBorder);
        pnFuncPanel.setLayout(gridLayout1);
        pnFuncDes2.setBorder(tbFuncDesBorder);
        pnFuncDes2.setLayout(borderLayout1);
        pnParamDes2.setBorder(tbParamDesBorder);
        pnParamDes2.setLayout(borderLayout2);
        verticalFlowLayout1.setHgap(0);
        verticalFlowLayout1.setVgap(0);
        edFuncDes.setBackground(Color.white);
        edFuncDes.setEnabled(false);
        edFuncDes.setBorder(null);
        edFuncDes.setPreferredSize(new Dimension(66, 60));
        edFuncDes.setCaretColor(Color.black);
        edFuncDes.setEditable(false);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new Dimension(463, 60));
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new Dimension(463, 60));
        pnFuncDes1.setLayout(borderLayout4);
        pnParamDes1.setLayout(gridLayout2);
        edParamDes.setBackground(Color.white);
        edParamDes.setEnabled(false);
        edParamDes.setEditable(false);
        pnFuncDes1.setBorder(titledBorder2);
        pnFuncDes1.setDebugGraphicsOptions(0);
        this.getContentPane().setLayout(borderLayout3);
        bnSaveF1.setFont(new java.awt.Font("Dialog", 0, 12));
        bnSaveF1.setText(res.getString("String_392"));
        bnSaveF1.setMnemonic('S');
        bnSaveF1.setPreferredSize(new Dimension(100,25));
        bnSaveF1.addActionListener(this);
/*  lk 这个监听有什么用呢，一获取焦点，textfield的内容就被清空了。
        bnSaveF1.addFocusListener(this);
*/
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        pnOp.setLayout(flowLayout1);
        pnOp.setBorder(border5);
        bnAddFunc.setFont(new java.awt.Font("Dialog", 0, 12));
        bnAddFunc.setText(res.getString("String_395"));
        bnAddFunc.setMnemonic('A');
        bnAddFunc.setPreferredSize(new Dimension(100,25));
        bnAddFunc.addActionListener(this);
        bnAddFunc.addFocusListener(this);
        bnClose.setFont(new java.awt.Font("Dialog", 0, 12));
        bnClose.setMnemonic('C');
        bnClose.setText(res.getString("String_399"));
        bnClose.setPreferredSize(new Dimension(80,25));
        bnClose.addActionListener(this);
        bnClose.addFocusListener(this);
        panel1.setBorder(border4);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        lbFuncDes1.setBackground(Color.white);
        lbFuncDes1.setForeground(Color.red);
        lbFuncDes1.setEditable(false);
        lbParamDes1.setBackground(Color.white);
        lbParamDes1.setForeground(Color.red);
        lbParamDes1.setPreferredSize(new Dimension(60, 22));
        lbParamDes1.setDisabledTextColor(Color.gray);
        lbParamDes1.setEditable(false);
        getContentPane().add(panel1, BorderLayout.CENTER);
        panel1.add(pnFuncPanel, null);
        panel1.add(pnFuncDes1, null);
        pnFuncDes1.add(lbFuncDes1, BorderLayout.CENTER);
        panel1.add(pnFuncDes2, null);
        pnFuncDes2.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(edFuncDes, null);
        panel1.add(pnParamDes2, null);
        pnParamDes2.add(jScrollPane2, BorderLayout.CENTER);
        jScrollPane2.getViewport().add(edParamDes, null);
        pnParamDes2.add(pnParamDes1, BorderLayout.NORTH);
        pnParamDes1.add(lbParamDes1, null);
        this.getContentPane().add(pnOp, BorderLayout.SOUTH);
        pnOp.add(cbFuncList, null);
        pnOp.add(bnAddFunc, null);
        pnOp.add(bnSaveF1, null);
        pnOp.add(bnClose, null);

        addWindowListener(this);
        // 初始化内部函数
        cbFuncList.addActionListener(this);
        InitcbFuncList();
        HelpIcon = JXMLResource.LoadImageIcon(this, "help.gif");
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void InitDialog() {
        if(isCustom){
            ProcesCustomSelectFunction();
            return;
        }
        WizardObject.BookComboBox.removeActionListener(WizardObject.ReportBook);
        WizardObject.bnDHOK.addActionListener(this);
        WizardObject.bnCancelSum.addActionListener(this);
        WizardObject.BookComboBox.addActionListener(this);
        WizardObject.ReportView.addSelectionChangedListener(this);
        WizardObject.BookTextField.TextField.addCaretListener(this);

        JFunctionStub FS;
        int i = 0;
        WizardObject.BookComboBox.setEditable(false);
        for (i = 0; i < WizardObject.FavFuncList.size(); i++) {
            FS = (JFunctionStub) WizardObject.FavFuncList.get(i);
            WizardObject.BookComboBox.insertItemAt(FS, i);
        }
        WizardObject.BookComboBox.insertItemAt(res.getString("String_401"), i);
        try {
            // 如果是外部调用，则显示SAP函数定义向导
            if(WizardObject.isReportWinzard()){
                WizardObject.BookComboBox.setSelectedIndex(0);
            }else{
                WizardObject.BookComboBox.setSelectedIndex(2);
            }
            this.saveZB();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void DestroyDialog() {
        if (isCustom) {
            return;
        }
        WizardObject.bnDHOK.addActionListener(WizardObject.ReportBook);
        WizardObject.bnCancelSum.addActionListener(WizardObject.ReportBook);
        WizardObject.BookComboBox.addActionListener(WizardObject.ReportBook);

        WizardObject.bnDHOK.removeActionListener(this);
        WizardObject.bnCancelSum.removeActionListener(this);
        WizardObject.BookComboBox.removeActionListener(this);
        WizardObject.ReportView.removeSelectionChangedListener(this);

        WizardObject.BookTextField.TextField.removeCaretListener(this);

        WizardObject.BookComboBox.removeAllItems();
        WizardObject.BookComboBox.setEditable(true);
        WizardObject.BookComboBox.setSelectedItem(res.getString("String_402"));
        WizardObject.BookTextField.setWizard(false);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowOpened(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowClosing(WindowEvent e) {
        if (WizardObject.WizardInterface != null) {
            WizardObject.WizardInterface.WizardDialogClose();
        }
        DestroyDialog();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowClosed(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowIconified(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowDeiconified(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowActivated(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowDeactivated(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        if (!isCustom) {
            WizardObject.BookTextField.setWizard(false);
        }
        if (e.getSource() == WizardObject.BookComboBox) {
            ProcessBookComboBox();
        }
        if (e.getSource() == this.bnClose) {
            WizardObject.HideWizard();
        }
        if (e.getSource() == bnAddFunc) {
            ProcessAddFunc();
        }
        if (e.getSource() == this.bnSaveF1) {
            ProcessSaveF1();
        }
        if (e.getSource() == this.cbFuncList) {
            ProcessFuncList();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void AddFunction(String FString) {
        if(!WizardObject.isReportWinzard()){
            return;
        }
        try {
            WizardObject.BookTextField.TextField.removeCaretListener(this);
            WizardObject.BookTextField.AddFunction(FString);
        }
        finally {
            WizardObject.BookTextField.TextField.addCaretListener(this);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessFuncList() {
        String FString = (String)this.cbFuncList.getSelectedItem();
        AddFunction(FString);
        if (FString.endsWith("()")) {
            WizardObject.BookTextField.TextField.removeCaretListener(this);
            WizardObject.BookTextField.TextField.setCaretPosition(WizardObject.BookTextField.TextField.getCaretPosition() - 1);
            WizardObject.BookTextField.TextField.addCaretListener(this);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessAddFunc() {
        String FString;
        FString = this.CurrentFS.getFunctionString();
        AddFunction(FString);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessSaveF1() {
        if(WizardObject.isReportWinzard()){
            this.WizardObject.BookTextField.SaveF1();
        }else{
            String FString = this.CurrentFS.getFunctionString();
            setFuncString(FString);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessBookComboBox() {
        int Count = WizardObject.BookComboBox.getItemCount();
        int Index = WizardObject.BookComboBox.getSelectedIndex();
        if (Index == Count - 1) {
            ProcessSelectFunction();
        }
        else {
            JFunctionStub FS;
            FS = (JFunctionStub) WizardObject.BookComboBox.getSelectedItem();
            InitFuncDefine(FS);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessSelectFunction() {
        JSelectFuncDialog Dlg;
        JFunctionStub FS;
        Dlg = new JSelectFuncDialog(Mainframe, res.getString("String_404"), true);
        Dlg.WizardObject = WizardObject;
        Dlg.CenterWindow();
        Dlg.Show();
        if (Dlg.Result == Dlg.RESULT_OK) {
            if (Dlg.FunctionStub != null) {
                FS = findFunctionObject(Dlg.FunctionStub);
                InitFuncDefine(FS);
                this.validate();
            }
        }
    }

    /**
     * 报告生成用
     *
     * @param map HashMap
     */
    public void setCustomFunctionMap(HashMap map) {
        this.mFuncMap = null;
        this.mFuncMap = map;
    }

    public void ProcesCustomSelectFunction() {
        JSelectFuncDialog Dlg;
        JFunctionStub FS;
        Dlg = new JSelectFuncDialog(Mainframe, res.getString("String_404"), true);
        Dlg.WizardObject = WizardObject;
        Dlg.CenterWindow();
        Dlg.setCustomFunction(true);
        Dlg.setCustomFunctionMap(mFuncMap);
        Dlg.Show();
        if (Dlg.Result == Dlg.RESULT_OK) {
            if (Dlg.FunctionStub != null) {
                InitFuncDefine(Dlg.FunctionStub);
                this.validate();
            }
        }
    }


    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    JFunctionStub findFunctionObject(JFunctionStub FS) {
        for (int i = 0; i < WizardObject.BookComboBox.getItemCount(); i++) {
            if (FS == WizardObject.BookComboBox.getItemAt(i)) {
                WizardObject.BookComboBox.setSelectedIndex(i);
                return FS;
            }
        }
        WizardObject.BookComboBox.insertItemAt(FS, 0);
        WizardObject.BookComboBox.setSelectedIndex(0);
        return FS;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
        try {
            String T1, T2, Text;
            int srow, scol, erow, ecol;
            srow = WizardObject.ReportView.getSelStartRow();
            scol = WizardObject.ReportView.getSelStartCol();
            erow = WizardObject.ReportView.getSelEndRow();
            ecol = WizardObject.ReportView.getSelEndCol();
            T1 = WizardObject.ReportView.formatRCNr(srow, scol, false);
            if (srow == erow && scol == ecol) {
                Text = T1;
            }
            else {
                T2 = WizardObject.ReportView.formatRCNr(WizardObject.ReportView.getSelEndRow(), WizardObject.ReportView.getSelEndCol(), false);
                Text = T1 + ":" + T2;
            }
            WizardObject.BookTextField.isCellRange = Text;
            // 如果是报表范围参考就只修改这里
            if (CurrentTextField != null && CurrentTextField.LOS.id.equals("BBREF")) {
                setFoucsText(Text);
            }
            else {
              this.saveZB();
              setDialogText(Text);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void setDialogText(String Text) {
        Text = res.getString("String_407") + Text;
        setTitle(Text);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void setFoucsText(String Text) {
        JTextField tf;
        if (CurrentFS != null && CurrentFS.ParamList != null) {
            tf = (JTextField) CurrentFS.ParamList.get("BBREF");
            if (tf != null) {
                tf.setText(Text);
            }
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void focusGained(FocusEvent e) {
        Component comp;
        JCustomTextField CTF;
        comp = e.getComponent();
        if (comp instanceof JCustomTextField) {
            CTF = (JCustomTextField) comp;
            ProcessFocusComp(CTF);
            return;
        }
        CurrentTextField = null;
        restoreSave();
        return;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void restoreSave() {
        try {
            WizardObject.ReportView.setPhySelection(phySRow, phySCol, phyERow, phyECol);
        }
        catch (Exception ee) {
            ee.printStackTrace();
        }
    }
    void saveZB() {
      try {
        SRow = WizardObject.ReportView.getSelStartRow();
        SCol = WizardObject.ReportView.getSelStartCol();
        ERow = WizardObject.ReportView.getSelEndRow();
        ECol = WizardObject.ReportView.getSelEndCol();
        phySRow = WizardObject.ReportView.getPhySelStartRow();
        phySCol = WizardObject.ReportView.getSelStartCol();
        phyERow = WizardObject.ReportView.getPhySelEndRow();
        phyECol = WizardObject.ReportView.getSelEndCol();

      } catch ( Exception e ) {

      }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessFocusComp(JCustomTextField comp) {
        if (isCustom) {
            return;
        }
        //
        CurrentTextField = comp;
        // 如果是报表参考
        if (CurrentTextField.LOS.id.equals("BBREF")) {
            WizardObject.BookTextField.setWizard(true);
        }
        else {
            WizardObject.BookTextField.setWizard(false);
        }

        tbParamDesBorder.setTitle(comp.LOS.text + res.getString("String_410"));
        if (comp.LOS.des1 != null && comp.LOS.des1.trim().length() == 0) {
            comp.LOS.des1 = null;
        }
        if (comp.LOS.des2 != null && comp.LOS.des2.trim().length() == 0) {
            comp.LOS.des2 = null;
        }
        if (comp.LOS.des1 != null || comp.LOS.des2 != null) {

            this.pnParamDes2.setVisible(true);
            this.pnParamDes2.updateUI();
            this.lbParamDes1.setText(ProcessMacro(comp.LOS.des1, this.CurrentFS));
            this.lbParamDes1.setCaretPosition(0); //必须在setText后设置
            this.edParamDes.setText(comp.LOS.des2);
        }
        else {
            this.pnParamDes2.setVisible(false);
        }
        pack();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void focusLost(FocusEvent e) {

        return;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void caretUpdate(CaretEvent e) {
        if (e.getSource() == this.WizardObject.BookTextField) {
            ProcessBookTextField();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessBookTextField() {

    }

    private void setFuncString(String func){
        mFuncString = func;
    }

    public String getFuncString(){
        return mFuncString;
    }

}
