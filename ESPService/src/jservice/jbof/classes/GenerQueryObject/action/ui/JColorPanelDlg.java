package jservice.jbof.classes.GenerQueryObject.action.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.efounder.comp.ColorConstants;
import com.efounder.comp.JColorButton;
import com.f1j.ss.*;
import com.f1j.swing.*;
import jfoundation.gui.window.classes.*;
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
public class JColorPanelDlg
    extends JFrameDialog
    implements WindowFocusListener, ActionListener {
    static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.ui.Language");
  private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JButton bnAutoColor = new JButton();
    private JPanel jPanel3 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private GridLayout gridLayout1 = new GridLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    public boolean isBack = false;
    JBook mBook;
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JColorPanelDlg() {
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
        bnAutoColor.setFont(new java.awt.Font("Dialog", 0, 12));
        bnAutoColor.setActionCommand("bnAutoColor");
        bnAutoColor.setText(res.getString("String_3"));
        jPanel1.setLayout(borderLayout1);
        jPanel3.setLayout(gridLayout1);
        gridLayout1.setColumns(8);
        gridLayout1.setRows(7);
        jPanel2.setLayout(borderLayout2);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(bnAutoColor, BorderLayout.CENTER);
//    this.setDefaultCloseOperation();
        setSize(200, 220);
        setTitle(res.getString("String_4"));
        this.addWindowFocusListener(this);
        InitButton();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitButton() {
        JColorButton bn;
        Insets is;
        Color clr;
        is = new Insets(0, 0, 0, 0);
        for (int Row = 0; Row < 7; Row++) {
            for (int Col = 0; Col < 8; Col++) {
                clr = new Color(ColorConstants.colors[Row * 7 + Col][0], ColorConstants.colors[Row * 7 + Col][1], ColorConstants.colors[Row * 7 + Col][2]);
                bn = new JColorButton(clr);
                bn.addActionListener(this);
                bn.setText("");
                bn.setMargin(is);
                jPanel3.add(bn, null);
            }
        }
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
        if (e.getSource() == bnAutoColor) {

            return;
        }
        if (e.getSource()instanceof JColorButton) {
            SetColor( ( (JColorButton) e.getSource()).getColor());
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void SetColor(Color clr) {
        if (this.isBack) {
            setBackColor(clr);
        }
        else {
            setFrontColor(clr);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void setBackColor(Color clr) {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(clr.getRGB());
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
    //修改:setFontColor
    //------------------------------------------------------------------------------------------------
    void setFrontColor(Color clr) {
        CellFormat CF;
        try {
            CF = mBook.getCellFormat();
            CF.setFontColor(clr.getRGB());
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
    public void setCustomObject(Object O) {
        mBook = (JBook) O;
    }
}
