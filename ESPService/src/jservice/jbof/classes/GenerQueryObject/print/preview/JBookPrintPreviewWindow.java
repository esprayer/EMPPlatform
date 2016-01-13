package jservice.jbof.classes.GenerQueryObject.print.preview;

import java.beans.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.f1j.swing.*;
import com.pub.comp.*;
import jbof.gui.window.classes.style.mdi.*;
import jfoundation.gui.window.classes.*;
import jservice.jbof.classes.GenerQueryObject.action.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBookPrintPreviewWindow
    extends JBOFMDIChildWindow
    implements ICustomDraw {

  private boolean isReport = false;
  private JBook mBook = null;
  private JBookPrintPreviewPainter mPainter = null;
  private int mPageIndex = 0;

  private boolean mIsFirstRun = true;
  /**
   *
   */
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private Border border1;
  private JButton mFirstPageBtn = new JButton();
  private FlowLayout flowLayout1 = new FlowLayout();
  private JButton mBackPageBtn = new JButton();
  private JButton mNextPageBtn = new JButton();
  private JButton mLastPageBtn = new JButton();
  private JButton mPageSetupBtn = new JButton();
  private JButton mPrintBtn = new JButton();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JLabel mInfoLb = new JLabel();
  JCustomPanel mCanvas = new JCustomPanel();
  FlowLayout flowLayout2 = new FlowLayout();

  /**
   *
   * @throws HeadlessException
   */
  public JBookPrintPreviewWindow(JBook book, boolean isReport) throws
      HeadlessException {
    if (book != null) {
      this.mBook = book;
      this.isReport = isReport;
    }
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                                              Color.white, new Color(99, 99, 99),
                                              new Color(142, 142, 142));
    jPanel1.setLayout(borderLayout1);
    jPanel1.setBorder(border1);
    jPanel2.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    mFirstPageBtn.setText("第一页");
    mBackPageBtn.setText("上一页");
    mNextPageBtn.setText("下一页");
    mLastPageBtn.setText("最后一页");
    mPageSetupBtn.setText("页面设置");
    mPrintBtn.setText("打印");
    addActionListener(mFirstPageBtn, "onFirstPage");
    addActionListener(mBackPageBtn, "onPrevPage");
    addActionListener(mNextPageBtn, "onNextPage");
    addActionListener(mLastPageBtn, "onLastPage");
    addActionListener(mPageSetupBtn, "onPageSetup");
    addActionListener(mPrintBtn, "onPrint");

    this.setLayout(borderLayout2);
    mInfoLb.setForeground(Color.blue);
    mInfoLb.setText("共1业,当前第1页");
    mCanvas.setAlignmentX( (float) 0.0);
    mCanvas.setAlignmentY( (float) 0.0);
    mCanvas.setPreferredSize(new Dimension(0, 0));
    mCanvas.setCustomDraw(this);
    mCanvas.setLayout(flowLayout2);
    this.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(mCanvas, BorderLayout.CENTER);
    this.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(mFirstPageBtn, null);
    jPanel2.add(mBackPageBtn, null);
    jPanel2.add(mNextPageBtn, null);
    jPanel2.add(mLastPageBtn, null);
    jPanel2.add(mPageSetupBtn, null);
    jPanel2.add(mPrintBtn, null);
    jPanel2.add(mInfoLb, null);
  }

  /**
   *
   * @param g
   */
  public void PaintDraw(Graphics g) {
    if (mBook != null && mCanvas != null) {
      mPainter = new JBookPrintPreviewPainter(mBook, mCanvas);
      int pageIndex = this.getPageIndex();
      mPainter.draw(g, pageIndex);
      if (this.mIsFirstRun) {
        this.refresh();
        this.mIsFirstRun = false;
      }
    }
  }

  /**
   *
   */

  public void onPageSetup() {
    try {
      JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.PageSetupDlg.class,
                                         mBook, true);
      this.refresh();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   */

  public void onPrint() {
    try {
      JFrameDialog Dlg = null;
      if (isReport) {
        Dlg = (JFrameDialog) Class.forName(
            "jreport.swing.classes.Print.JReportBookPrintSetDlg").newInstance();
        Dlg.setCustomObject(mBook);
      }
      else {
        //复制一份,设置格式为正常
//                JBook printBook = JPrintUtils.copyPrintBook(mBook);
//                Dlg = new JPrintDlg(printBook);
      }
      Dlg.CenterWindow();
      Dlg.setModal(true);
      Dlg.Show();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   */
  public void refresh() {
    this.mCanvas.updateUI();

    //按钮的有效性
    boolean isEnable = true;

    int pageIndex = this.getPageIndex();
    if (pageIndex == 0) {
      isEnable = false;
    }
    this.mFirstPageBtn.setEnabled(isEnable);
    this.mBackPageBtn.setEnabled(isEnable);

    isEnable = true;
    if (pageIndex == this.getPageCount() - 1) {
      isEnable = false;
    }
    this.mNextPageBtn.setEnabled(isEnable);
    this.mLastPageBtn.setEnabled(isEnable);

    String infoText = "共 ";
    infoText += this.getPageCount();
    infoText += " 页，";
    infoText += "当前第 ";
    infoText += (this.getPageIndex() + 1);
    infoText += " 页";
    this.mInfoLb.setText(infoText);
  }

  /**
   *
   * @return
   */
  public int getPageIndex() {
    return mPageIndex;
  }

  /**
   *
   * @param pageIndex
   */
  public void setPageIndex(int pageIndex) {
    if (pageIndex >= 0 && pageIndex < this.getPageCount()) {
      mPageIndex = pageIndex;
      refresh();
    }
  }

  /**
   *
   */
  public void onFirstPage() {
    mPageIndex = 0;
    refresh();
  }

  /**
   *
   */
  public void onPrevPage() {
    if (mPageIndex > 0) {
      mPageIndex--;
      refresh();
    }
  }

  /**
   *
   */
  public void onNextPage() {
    if (mPageIndex < this.getPageCount()) {
      mPageIndex++;
      refresh();
    }
  }

  /**
   *
   */
  public void onLastPage() {
    mPageIndex = this.getPageCount() - 1;
    refresh();
  }

  /**
   *
   * @return
   */
  public int getPageCount() {
    if (mPainter != null) {
      return mPainter.getPageCount();
    }
    return 1;
  }

  /**
   *
   * @param btn
   * @param methodName
   */
  private void addActionListener(AbstractButton btn, String methodName) {
    btn.addActionListener( (ActionListener) EventHandler.create(
        ActionListener.class,
        this,
        methodName));

  }

  public Object ProcessmnPrint(Object Param, Object Data, Object CustomObject,
                               Object AdditiveObject) {
    this.onPrint();
    return null;
  }

}
