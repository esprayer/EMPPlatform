package jservice.jbof.classes.GenerQueryObject.action.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.f1j.ss.*;
import com.f1j.swing.*;
import jfoundation.gui.window.classes.*;
import jframework.resource.classes.*;
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
public class JLinePanelDlg
    extends JFrameDialog
    implements WindowFocusListener, ActionListener {
    static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.ui.Language");
  private JPanel jPanel5 = new JPanel();
    JBook mBook;
    short LineStyle = 0;
    private GridLayout gridLayout1 = new GridLayout();
    private JButton bnClose = new JButton();
    private JButton bnBorder1 = new JButton();
    private JButton bnBorder3 = new JButton();
    private JButton bnBorder4 = new JButton();
    private JButton bnBorder5 = new JButton();
    private JButton bnBorder6 = new JButton();
    private JButton bnBorder2 = new JButton();
    private JButton bnBorder7 = new JButton();
    private JButton bnBorder8 = new JButton();
    private JButton bnBorder0 = new JButton();
    private JToggleButton bnLine7 = new JToggleButton();
    private JToggleButton bnLine6 = new JToggleButton();
    private JToggleButton bnLine5 = new JToggleButton();
    private JToggleButton bnLine4 = new JToggleButton();
    private JToggleButton bnLine3 = new JToggleButton();
    private JToggleButton bnLine2 = new JToggleButton();
    private JToggleButton bnLine1 = new JToggleButton();
    private JToggleButton bnLine0 = new JToggleButton();
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JLinePanelDlg() {
        try {
            jbInit();
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
    private void jbInit() throws Exception {
        jPanel5.setLayout(gridLayout1);
        gridLayout1.setColumns(3);
        gridLayout1.setRows(6);
        bnLine0.setActionCommand("L0");
        bnLine1.setActionCommand("L1");
        bnLine4.setActionCommand("L4");
        bnLine3.setActionCommand("L3");
        bnLine2.setActionCommand("L2");
        bnLine5.setActionCommand("L5");
        bnLine6.setActionCommand("L6");
        bnLine7.setActionCommand("L7");
        this.getContentPane().add(jPanel5, BorderLayout.CENTER);
        jPanel5.add(bnClose, null);
        jPanel5.add(bnLine0, null);
        jPanel5.add(bnLine1, null);
        jPanel5.add(bnLine2, null);
        jPanel5.add(bnLine3, null);
        jPanel5.add(bnLine4, null);
        jPanel5.add(bnLine5, null);
        jPanel5.add(bnLine6, null);
        jPanel5.add(bnLine7, null);
        jPanel5.add(bnBorder0, null);
        jPanel5.add(bnBorder1, null);
        jPanel5.add(bnBorder2, null);
        jPanel5.add(bnBorder3, null);
        jPanel5.add(bnBorder4, null);
        jPanel5.add(bnBorder5, null);
        jPanel5.add(bnBorder6, null);
        jPanel5.add(bnBorder7, null);
        jPanel5.add(bnBorder8, null);
        setSize(new Dimension(146, 216));
        setTitle(res.getString("String_14"));
        this.addWindowFocusListener(this);
        InitButton();
    }

    void InitButton() {
        ImageIcon ii;
        Insets is;
        is = new Insets(2, 2, 2, 2);
        ii = JXMLResource.LoadImageIcon(this, "close.gif");
        bnClose.setIcon(ii);
        bnClose.setMargin(is);
        bnClose.addActionListener(this);
        ii = JXMLResource.LoadImageIcon(this, "line0.gif");
        bnLine0.setIcon(ii);
        bnLine0.setMargin(is);
        bnLine0.addActionListener(this);
        ii = JXMLResource.LoadImageIcon(this, "line1.gif");
        bnLine1.setIcon(ii);
        bnLine1.setMargin(is);
        bnLine1.addActionListener(this);
        ii = JXMLResource.LoadImageIcon(this, "line2.gif");
        bnLine2.addActionListener(this);
        bnLine2.setIcon(ii);
        bnLine2.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "line3.gif");
        bnLine3.addActionListener(this);
        bnLine3.setIcon(ii);
        bnLine3.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "line4.gif");
        bnLine4.addActionListener(this);
        bnLine4.setIcon(ii);
        bnLine4.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "line5.gif");
        bnLine5.addActionListener(this);
        bnLine5.setIcon(ii);
        bnLine5.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "line6.gif");
        bnLine6.addActionListener(this);
        bnLine6.setIcon(ii);
        bnLine6.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "line7.gif");
        bnLine7.addActionListener(this);
        bnLine7.setIcon(ii);
        bnLine7.setMargin(is);

        ii = JXMLResource.LoadImageIcon(this, "border0.gif");
        bnBorder0.addActionListener(this);
        bnBorder0.setIcon(ii);
        bnBorder0.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border1.gif");
        bnBorder1.addActionListener(this);
        bnBorder1.setIcon(ii);
        bnBorder1.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border2.gif");
        bnBorder2.addActionListener(this);
        bnBorder2.setIcon(ii);
        bnBorder2.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border3.gif");
        bnBorder3.addActionListener(this);
        bnBorder3.setIcon(ii);
        bnBorder3.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border4.gif");
        bnBorder4.addActionListener(this);
        bnBorder4.setIcon(ii);
        bnBorder4.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border5.gif");
        bnBorder5.addActionListener(this);
        bnBorder5.setIcon(ii);
        bnBorder5.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border6.gif");
        bnBorder6.addActionListener(this);
        bnBorder6.setIcon(ii);
        bnBorder6.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border7.gif");
        bnBorder7.addActionListener(this);
        bnBorder7.setIcon(ii);
        bnBorder7.setMargin(is);
        ii = JXMLResource.LoadImageIcon(this, "border8.gif");
        bnBorder8.addActionListener(this);
        bnBorder8.setIcon(ii);
        bnBorder8.setMargin(is);

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowGainedFocus(WindowEvent e) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void windowLostFocus(WindowEvent e) {
        hide();
        this.dispose();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        String Name;
        short Style;
        Name = e.getActionCommand();
        if (Name.startsWith("L")) {
            Style = Integer.valueOf(Name.substring(1, Name.length())).shortValue();
            PorcessLineStyle(Style, e);
        }
        if (e.getSource() == bnClose) {
            this.hide();
            this.dispose();
        }
        if (e.getSource() == bnBorder0) {
            setBoder0();
        }
        if (e.getSource() == bnBorder1) {
            setBoder1();
        }
        if (e.getSource() == bnBorder2) {
            setBoder2();
        }
        if (e.getSource() == bnBorder3) {
            setBoder3();
        }
        if (e.getSource() == bnBorder4) {
            setBoder4();
        }
        if (e.getSource() == bnBorder5) {
            setBoder5();
        }
        if (e.getSource() == bnBorder6) {
            setBoder6();
        }
        if (e.getSource() == bnBorder7) {
            setBoder7();
        }
        if (e.getSource() == bnBorder7) {
            setBoder8();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void setBoder8() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
//      CF.setLeftBorder(LineStyle);
//      CF.setRightBorder(LineStyle);
            CF.setTopBorder(LineStyle);
//      CF.setBottomBorder(LineStyle);
//      CF.setHorizontalInsideBorder(LineStyle);
//      CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder7() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
//      CF.setLeftBorder(LineStyle);
//      CF.setRightBorder(LineStyle);
//      CF.setTopBorder(LineStyle);
//      CF.setBottomBorder(LineStyle);
            CF.setHorizontalInsideBorder(LineStyle);
//      CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder6() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
//      CF.setLeftBorder(LineStyle);
//      CF.setRightBorder(LineStyle);
//      CF.setTopBorder(LineStyle);
            CF.setBottomBorder(LineStyle);
//      CF.setHorizontalInsideBorder(LineStyle);
//      CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder5() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
            CF.setLeftBorder(LineStyle);
//      CF.setRightBorder(LineStyle);
//      CF.setTopBorder(LineStyle);
//      CF.setBottomBorder(LineStyle);
//      CF.setHorizontalInsideBorder(LineStyle);
//      CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder4() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
//      CF.setLeftBorder(LineStyle);
//      CF.setRightBorder(LineStyle);
//      CF.setTopBorder(LineStyle);
//      CF.setBottomBorder(LineStyle);
//      CF.setHorizontalInsideBorder(LineStyle);
            CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder3() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
//      CF.setLeftBorder(LineStyle);
            CF.setRightBorder(LineStyle);
//      CF.setTopBorder(LineStyle);
//      CF.setBottomBorder(LineStyle);
//      CF.setHorizontalInsideBorder(LineStyle);
//      CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder2() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
//      CF.setLeftBorder(LineStyle);
//      CF.setRightBorder(LineStyle);
//      CF.setTopBorder(LineStyle);
//      CF.setBottomBorder(LineStyle);
            CF.setHorizontalInsideBorder(LineStyle);
            CF.setVerticalInsideBorder(LineStyle);
            mBook.setCellFormat(CF);
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
    void setBoder1() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
            CF.setLeftBorder(LineStyle);
            CF.setRightBorder(LineStyle);
            CF.setTopBorder(LineStyle);
            CF.setBottomBorder(LineStyle);
//      CF.setHorizontalInsideBorder((short)0);
//      CF.setVerticalInsideBorder((short)0);
            mBook.setCellFormat(CF);
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
    void setBoder0() {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
            CF.setLeftBorder( (short) 0);
            CF.setRightBorder( (short) 0);
            CF.setTopBorder( (short) 0);
            CF.setBottomBorder( (short) 0);
            CF.setHorizontalInsideBorder( (short) 0);
            CF.setVerticalInsideBorder( (short) 0);
            mBook.setCellFormat(CF);
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
    void PorcessLineStyle(short Style, ActionEvent e) {
        LineStyle = Style;
        Object so = e.getSource();
        bnLine0.setSelected(false);
        bnLine1.setSelected(false);
        bnLine2.setSelected(false);
        bnLine3.setSelected(false);
        bnLine4.setSelected(false);
        bnLine5.setSelected(false);
        bnLine6.setSelected(false);
        bnLine7.setSelected(false);
        ( (javax.swing.JToggleButton) so).setSelected(true);
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void setCustomObject(Object O) {
        mBook = (JBook) O;
    }
}
