package jservice.jbof.classes.GenerQueryObject.print;

import jfoundation.gui.window.classes.JFrameDialog;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import javax.print.PrintService;
import java.awt.print.PrinterJob;
import javax.print.attribute.PrintRequestAttributeSet;
import com.f1j.swing.JBook;
import javax.print.attribute.HashPrintRequestAttributeSet;
import jframework.resource.classes.JXMLResource;
import jcomponent.custom.swing.JIConListCellRenderer;
import javax.print.attribute.PrintServiceAttributeSet;
import java.awt.event.*;
import jframework.foundation.classes.JActiveDComDM;
import jservice.jbof.classes.GenerQueryObject.action.*;
import jservice.jbof.classes.GenerQueryObject.*;
import com.efounder.eai.ide.ExplorerIcons;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintDlg
    extends JFrameDialog implements ActionListener {

//    PrintService CurrentPrintService = null;
//    PrinterJob CurrentPrinterJob = null;
//    PrintRequestAttributeSet CurrentPrintRequestAttribute = new HashPrintRequestAttributeSet();
  JBook book = null;

  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private FlowLayout flowLayout1 = new FlowLayout();
  private JButton btnCancel = new JButton();
  private JButton btnOk = new JButton();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JPanel jPanel4 = new JPanel();
  private JPanel jPanel5 = new JPanel();
  private Border border1;
  private TitledBorder titledBorder1;
  private Border border2;
  private TitledBorder titledBorder2;
  private JLabel jLabel1 = new JLabel();
  private JComboBox cbPrintList = new JComboBox();
  private JButton btnPrinterProps = new JButton();
  private FlowLayout flowLayout2 = new FlowLayout();
  private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  private JPanel jPanel6 = new JPanel();
  private JPanel jPanel7 = new JPanel();
  private JLabel jLabel2 = new JLabel();
  private FlowLayout flowLayout3 = new FlowLayout();
  private JSpinner spCopyNum = new JSpinner();
  private JCheckBox chbNoColor = new JCheckBox();
  private FlowLayout flowLayout4 = new FlowLayout();

  JQueryStubObject QueryStubObject = null;
  JCheckBox cbSavetoExcel = new JCheckBox();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel10 = new JPanel();

  public JPrintDlg(JBook book, JQueryStubObject QSO) {
    super(JActiveDComDM.MainApplication.MainWindow, "打印", true);
    this.book = book;
    QueryStubObject = QSO;
    try {
      jbInit();
      initComponents();
      setTitle(QSO.CaptionText);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder();
    titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(new Color(
        172, 172, 172)), "打印机");
    border2 = BorderFactory.createLineBorder(new Color(172, 172, 172));
    titledBorder2 = new TitledBorder(border2, "打印选项");
    jPanel1.setLayout(borderLayout1);
    jPanel3.setLayout(flowLayout1);
    btnCancel.setPreferredSize(new Dimension(82, 23));
    btnCancel.setMargin(new Insets(2, 2, 2, 2));
    btnCancel.setText("取消(C)");
    btnCancel.addActionListener(this);
    btnOk.setPreferredSize(new Dimension(82, 23));
    btnOk.setMargin(new Insets(2, 2, 2, 2));
    btnOk.setText("确定(O)");
    btnOk.addActionListener(this);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(8);
    flowLayout1.setVgap(7);
    jPanel2.setLayout(borderLayout2);
    jPanel5.setLayout(verticalFlowLayout1);
    jPanel4.setBorder(titledBorder1);
    jPanel4.setPreferredSize(new Dimension(425, 68));
    jPanel4.setLayout(flowLayout2);
    jPanel5.setBorder(titledBorder2);
    jLabel1.setText("  打印机：");
    btnPrinterProps.setPreferredSize(new Dimension(56, 23));
    btnPrinterProps.setText("属性");
    btnPrinterProps.addActionListener(this);
    cbPrintList.setPreferredSize(new Dimension(300, 22));
    flowLayout2.setAlignment(FlowLayout.LEFT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(5);
    jLabel2.setText("  打印份数：");
    jPanel6.setLayout(flowLayout3);
    flowLayout3.setAlignment(FlowLayout.LEFT);
    flowLayout3.setHgap(0);
    spCopyNum.setPreferredSize(new Dimension(50, 22));
    chbNoColor.setText("单色打印");
    jPanel7.setLayout(flowLayout4);
    flowLayout4.setAlignment(FlowLayout.LEFT);
    flowLayout4.setHgap(0);
    cbSavetoExcel.setText("打印到Excel");
    jPanel3.setPreferredSize(new Dimension(129, 37));
    jPanel9.setPreferredSize(new Dimension(6, 6));
    jPanel10.setPreferredSize(new Dimension(6, 6));
    jPanel8.setPreferredSize(new Dimension(6, 6));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(jLabel1, null);
    jPanel4.add(cbPrintList, null);
    jPanel4.add(btnPrinterProps, null);
    jPanel2.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jPanel6, null);
    jPanel6.add(jLabel2, null);
    jPanel6.add(spCopyNum, null);
    jPanel5.add(jPanel7, null);
    jPanel7.add(chbNoColor, null);
    jPanel7.add(cbSavetoExcel, null);
    jPanel1.add(jPanel3, BorderLayout.SOUTH);
    jPanel3.add(btnOk, null);
    jPanel3.add(btnCancel, null);
    jPanel1.add(jPanel8, BorderLayout.WEST);
    jPanel1.add(jPanel9, BorderLayout.EAST);
    jPanel1.add(jPanel10, BorderLayout.NORTH);
    this.setSize(450, 240);
    GUIPrepare();
  }

  public void GUIPrepare() {
    btnOk.setIcon(ExplorerIcons.getExplorerIcon("office/(01,29).gif"));
    btnCancel.setIcon(ExplorerIcons.getExplorerIcon("office/(01,29).gif"));

  }

  // 获取打印机列表
  private void initPrintList() {
    PrintService[] Printers;
    ImageIcon II = JXMLResource.LoadImageIcon(this, "print16.gif");
    JIConListCellRenderer ICR = new JIConListCellRenderer(II);
    cbPrintList.setRenderer(ICR);
    Printers = PrinterJob.lookupPrintServices();
    for (int i = 0; i < Printers.length; i++) {
      cbPrintList.insertItemAt(Printers[i], i);
    }
    if (cbPrintList.getItemCount() > 0) {
      cbPrintList.setSelectedIndex(0);
    }
    //设置缺省打印机未上次使用的打印机
    String printer = JActiveDComDM.LocalRegistry.Get( (String) QueryStubObject.
        Param);
    if (printer != null) {
      String tempPrinter=null;
      for (int i = 0; i < Printers.length; i++) {
        tempPrinter=Printers[i].toString() ;
        if(printer.equals(tempPrinter) ){
          cbPrintList.setSelectedIndex(i);
        }
      }
    }
  }

  private PrintService getPrintService() {
    PrintService ps = null;
    if (cbPrintList.getItemCount() > 0) {
      ps = (PrintService) cbPrintList.getSelectedItem();
    }
    return ps;
  }

  private void initComponents() throws Exception {
    this.spCopyNum.setValue(new Integer(1));
    this.chbNoColor.setSelected(true);
    // 获取打印机列表
    initPrintList();

//        if (CurrentPrinterJob == null) {
//            CurrentPrinterJob = PrinterJob.getPrinterJob();
//        }
//        CurrentPrintService = CurrentPrinterJob.getPrinterJob().getPrintService();
//        PrintServiceAttributeSet ps = CurrentPrintService.getAttributes();

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.btnOk) {
      onOk();
    }
    else if (e.getSource() == this.btnCancel) {
      onCancel();
    }
    else if (e.getSource() == this.btnPrinterProps) {
      onPrinterProps();
    }
  }

  private void onOk() {
    //保存当前打印机设置
    JActiveDComDM.LocalRegistry.Put( (String) QueryStubObject.Param,
                                    cbPrintList.getSelectedItem().toString());
    if (cbSavetoExcel.isSelected()) {
      saveToExcel();
    }
    else {
      Print();
    }
    super.OnOk();
  }

  private void Print() {
    try {
      PrintService PS = this.getPrintService();
      PrinterJob Job = PrinterJob.getPrinterJob();
      Job.setJobName(QueryStubObject.CaptionText);
      Job.setPrintService(PS);
//        Job.printDialog();
      int copyNum = ( (Integer)this.spCopyNum.getValue()).intValue();
//        book.setSheetSelected(1,true);
//        book.setPrintNumberOfCopies(copyNum);
//        book.setPrintNoColor(this.chbNoColor.isSelected());
      book.filePrint(true, Job);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveToExcel() {
    try {
      if (book != null) {
        JBookActionImpl actionImpl = new JGenerQueryActionImpl();
        JBookActionManager actionMgr = new JBookActionManager(actionImpl);
        String actionName = JBookActionConstants.ACTION_EXPORT;
        String defaultName = QueryStubObject.CaptionText;
        JBookAction action = new JBookAction(book, actionName, defaultName);
        actionMgr.execute(action);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void onCancel() {
    super.OnCancel();
  }

  private void onPrinterProps() {
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
