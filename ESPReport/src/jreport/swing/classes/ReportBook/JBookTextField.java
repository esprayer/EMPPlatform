package jreport.swing.classes.ReportBook;

import java.util.ResourceBundle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jframework.foundation.classes.JActiveDComDM;
import jreport.jdof.classes.DOFReportObject.ReportWindow.Dlg.JCommentDefineDialog;
import jreport.model.classes.calculate.JCalculateManager;
import jreport.model.classes.calculate.JFunctionManager;
import jreport.swing.classes.JReportModel;
import jreport.swing.classes.JReportView;
import jreport.swing.classes.util.ReportUtil;
import jreportwh.jdof.classes.common.util.StringUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:KeyListener
//--------------------------------------------------------------------------------------------------
public class JBookTextField
    extends JComboBox implements ActionListener, ItemListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");

    public JTextField TextField;
    public JComboBox cbFH;
    public JCheckBox cbJYGS;
    public boolean isWizard = false;
    //ѡ��ĵ�Ԫ��Χ
    //���û�ѡ����ֹ��޸İ��س�ȷ��ʱ��ֵ�ı�fsz 2004.5.19
    public String isCellRange = "";
    // ���������
    public static JCalculateManager CalculateManager = null;
    // ����������
    public static JFunctionManager FunctionManager = null;
    // ����ؼ�
    private JReportView ReportView;

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public JBookTextField() {
        this.setEditable(true);
        TextField = (JTextField)this.getEditor().getEditorComponent();
        addActionListener(this);
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void setReportView(JReportView rv) {
        ReportView = rv;
        if (ReportView != null) ReportView.BookTextField = this;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public JReportView getReportView() {
        return ReportView;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == TextField) {
            SaveF1();
        }
        if (e.getSource() == this) {
            int status = ReportView.getModel().getDataStatus();
            if (status == JReportModel.MODEL_STATUS_JSGS) {
                int JYGS_INDEX = getSelectedIndex();
                ReportView.getModel().setModelInfo("JYGS_INDEX", new Integer(JYGS_INDEX)); //JYGS_INDEX
                JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(ReportView.getActiveRow() + 1, ReportView.getActiveCol() + 1, (JReportModel) ReportView.getModel());
                if (UI != null && JYGS_INDEX != -1 && JYGS_INDEX < UI.JygsList.size()) {
                    JJygsInfo JygsInfo = (JJygsInfo) UI.JygsList.get(JYGS_INDEX);
                    setBookComboBoxText(UI.getHZD_ZB(), UI.getLZD_ZB(), UI.getHZD_ZB() + JygsInfo.JYGS_HOFFSET, UI.getLZD_ZB() + JygsInfo.JYGS_LOFFSET);
                }
            } else if (status == JReportModel.MODEL_STATUS_BBPZ) {
                int BBFZ_INDEX = getSelectedIndex();
                ReportView.getModel().setModelInfo("BBFZ_INDEX", new Integer(BBFZ_INDEX));
                JUnitInfo UI = JUnitInfo.GetUnitInfoByHL(ReportView.getActiveRow() + 1, ReportView.getActiveCol() + 1, (JReportModel) ReportView.getModel());
                if (UI != null && BBFZ_INDEX != -1 && BBFZ_INDEX < UI.CommentList.size()) {
                    JCommentInfo commentInfo = (JCommentInfo) UI.CommentList.get(BBFZ_INDEX);
                    setBookComboBoxText(UI.getHZD_ZB(), UI.getLZD_ZB(), UI.getHZD_ZB() + commentInfo.Comment_HOFFSET, UI.getLZD_ZB() + commentInfo.Comment_LOFFSET);
                }
            }
        }
        if (e.getSource() == cbFH) { //У�����
        }
        if (e.getSource() == cbJYGS) { //У��״̬���޸Ļ������
            int status = ReportView.getModel().getDataStatus();
            if (status == JReportModel.MODEL_STATUS_JYGS) {
                /**
                 * �ָ�Ϊר�õ�У�鹫ʽ���崰��������
                 * modofied by hufeng 2005.11.18
                 */
                showJygsDefineDlg();
            } else if (status == JReportModel.MODEL_STATUS_BBPZ) {
                showCommentDefineDlg();
            }
//      boolean  JYGS_STAT  = cbJYGS.isSelected();
//      ReportView.getModel().setModelInfo("JYGS_STAT",new Boolean(JYGS_STAT));//�޸�
        }
    }

    /**
     * ��ʾ������ע���崰��
     */
    private void showCommentDefineDlg() {
        JReportModel RM = (JReportModel) ReportView.getModel();
        JCommentDefineDialog dlg = new JCommentDefineDialog(JActiveDComDM.MainApplication.MainWindow, "��ע���崰��", false, RM);
        dlg.CenterWindow();
        dlg.setVisible(true);
    }

    /**
     * ��ʾУ�鹫ʽ���崰��
     */
    private void showJygsDefineDlg() {
//        JReportModel model = (JReportModel) ReportView.getModel();
//        JActiveDComDM.MainApplication.BeginWaitCursor();
//        JJygsDefineDlg pDlg = new JJygsDefineDlg(JActiveDComDM.MainApplication.MainWindow, res.getString("String_39"), false, model);
//        JActiveDComDM.MainApplication.EndWaitCursor();
//        pDlg.CenterWindow();
//        pDlg.setVisible(true);
    }

    public void itemStateChanged(ItemEvent ie) {
        String JYGS_FH = ReportUtil.SYMBOL_NAMES[cbFH.getSelectedIndex()]; //ѡ��ķ���
        String JYGS = TextField.getText(); //ѡ��Ĺ�ʽ
        //�Ƿ�ΪУ�鹫ʽ
        if (StringUtil.isStartWith(JYGS, ReportUtil.SYMBOL_NAMES)) {
            String newJYGS = JYGS_FH + ReportUtil.getJYGSWithoutSymbol(JYGS);
            TextField.setText(newJYGS);
        }
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void SaveF1() {
        try {
            int Row = ReportView.getPhyRow();
            int Col = ReportView.getCol();
            setCellText(TextField.getText());
            ReportView.setPhyRow(Row + 1);
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, ee.getMessage(), res.getString("String_40"), JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
            this.grabFocus();
        }
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void setText(String Text, boolean isEx) {
        if (isWizard)return;
        if (Text != null && Text.trim().length() == 0) Text = "";
        if (Text.startsWith("=")) {
            if (isEx)
                TextField.setForeground(Color.blue);
            else
                TextField.setForeground(Color.red);
        } else {
            TextField.setForeground(Color.black);
        }
        TextField.setText(Text);
    }

    //����У�鹫ʽ����ʾ�������Ϣ
    public void setJYGS(JUnitInfo UI, int Index) {
        ReportView.getModel().setModelInfo("JYGS_INDEX", new Integer(Index));
        JJygsInfo JygsInfo = (JJygsInfo) UI.JygsList.get(Index);
        setBookComboBoxText(UI.getHZD_ZB(), UI.getLZD_ZB(), UI.getHZD_ZB() + JygsInfo.JYGS_HOFFSET, UI.getLZD_ZB() + JygsInfo.JYGS_LOFFSET);
        TextField.setForeground(Color.green.darker());
        this.setSelectedIndex(Index);
    }

    /**
     * ���ø�ע����ʾ�������Ϣ
     * add by gengeng 2008-8-28
     *
     * @param    UI JUnitInfo
     * @param Index int
     */
    public void setComment(JUnitInfo UI, int Index) {
        ReportView.getModel().setModelInfo("BBFZ_INDEX", new Integer(Index));
        JCommentInfo commentInfo = (JCommentInfo) UI.CommentList.get(Index);
        setBookComboBoxText(UI.getHZD_ZB(), UI.getLZD_ZB(), UI.getHZD_ZB() + commentInfo.Comment_HOFFSET, UI.getLZD_ZB() + commentInfo.Comment_LOFFSET);
        TextField.setForeground(Color.gray.darker());
        setSelectedIndex(Index);
    }

    void setBookComboBoxText(int SRow, int SCol, int ERow, int ECol) {
        try {
            String T1, T2, Text;
            T1 = ReportView.formatRCNr(SRow - 1, SCol - 1, false);
            if (SRow == ERow && SCol == ECol) {
                Text = T1;
            } else {
                T2 = ReportView.formatRCNr(ERow - 1, ECol - 1, false);
                Text = T1 + ":" + T2;
            }
            ReportView.BookComboBox.setSelectedItem(Text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    void setCellText(String Text) throws Exception {
        Object Res = null;
        if (ReportView != null) {
            Res = ReportView.setCellF1(Text);
        }
    }

    public void AddFunction(String FString) {
        String Text = TextField.getText();
        int SIndex, EIndex;
        //У�鹫ʽ״̬
        if (ReportView.getModel().getDataStatus() == ( (JReportModel) ReportView.getModel()).MODEL_STATUS_JYGS) {
            String JYGS_FH = ReportUtil.SYMBOL_NAMES[cbFH.getSelectedIndex()];
            if (StringUtil.isStartWith(Text, ReportUtil.SYMBOL_NAMES)) {
                SIndex = TextField.getSelectionStart(); // .getCaretPosition();
                EIndex = TextField.getSelectionEnd();
                Text = Text.substring(0, SIndex) + FString + Text.substring(EIndex, Text.length());
            } else {
                Text = JYGS_FH + FString; ;
            }
        } else {
            if (Text.startsWith("=")) {
                SIndex = TextField.getSelectionStart(); // .getCaretPosition();
                EIndex = TextField.getSelectionEnd();
                Text = Text.substring(0, SIndex) + FString + Text.substring(EIndex, Text.length());
            } else {
                Text = "=" + FString;
            }
        }
        this.setText(Text, true);
    }

    private String parseFunctionString(String functionString) {
        return null;
    }

    public void setWizard(boolean value) {
        isWizard = value;
    }
}
