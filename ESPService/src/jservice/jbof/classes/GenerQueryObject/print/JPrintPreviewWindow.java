package jservice.jbof.classes.GenerQueryObject.print;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.efounder.eai.application.classes.JBOFApplication;
import com.f1j.swing.*;
import com.pub.comp.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jbof.gui.window.classes.style.mdi.*;
import java.util.*;
import jservice.jbof.classes.GenerQueryObject.print.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JPrintPreviewWindow
    extends JBOFMDIChildWindow
    implements ICustomDraw, ActionListener {
    /**
     *
     */
    private JPrintPreviewHelper mHelper = null;
    private int mPageIndex = 0;
    /**
     *
     */
    private JBook mBook = null;
    /**
     *
     */
    private JCustomPanel mCanvas = new JCustomPanel();
    private JButton mPrevPageBtn = new JButton("上一页");
    private JButton mNextPageBtn = new JButton("下一页");
    private JButton mPrintBtn = new JButton("打印");
    private JButton mPageSetupBtn = new JButton("页面设置");
    private JPanel jPanel1 = new JPanel(new BorderLayout());
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTabbedPane tpPrintView = new JTabbedPane();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JButton mFirstPageBtn = new JButton();
    private JButton mLastPageBtn = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JLabel mInfoLabel = new JLabel();

    /**
     *
     */
    public JPrintPreviewWindow() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        tpPrintView.setTabPlacement(JTabbedPane.BOTTOM);
        tpPrintView.setVerifyInputWhenFocusTarget(true);
        mPrevPageBtn.setPreferredSize(new Dimension(70, 25));
        mPageSetupBtn.setPreferredSize(new Dimension(80, 25));
        mNextPageBtn.setPreferredSize(new Dimension(70, 25));
        mPrintBtn.setPreferredSize(new Dimension(70, 25));
        mFirstPageBtn.setPreferredSize(new Dimension(70, 25));
        mFirstPageBtn.setText("第一页");
        mFirstPageBtn.addActionListener(this);
        mLastPageBtn.setPreferredSize(new Dimension(80, 25));
        mLastPageBtn.setText("最后一页");
        mLastPageBtn.addActionListener(this);
        jLabel1.setText("打印预览:");
        this.add(tpPrintView, BorderLayout.CENTER);

        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        tpPrintView.add(jPanel1, "打印预览");
        JPanel jPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mPrevPageBtn.addActionListener(this);
        mNextPageBtn.addActionListener(this);
        mPrintBtn.addActionListener(this);
        mPageSetupBtn.addActionListener(this);

//        this.mPrevPageBtn.setEnabled(false);
        jPanel2.add(mFirstPageBtn, null);
        jPanel2.add(mPrevPageBtn);
        jPanel2.add(mNextPageBtn);
        jPanel2.add(mLastPageBtn, null);
        jPanel2.add(mPageSetupBtn);
        jPanel2.add(mPrintBtn);
        jPanel2.add(jLabel1, null);
        jPanel2.add(mInfoLabel, null);

        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jScrollPane1.getViewport().add(mCanvas, null);
        tpPrintView.setSelectedComponent(jPanel1);
        setInfo();
    }

    /**
     *
     * @param App
     * @param MainWindow
     * @param AddObject
     * @param AddObject1
     * @return
     */
    public Object InitChildWindow(JBOFApplication App, JBOFMainWindow MainWindow, Object AddObject, Object AddObject1) {
        mBook = (JBook) AddObject;
        mCanvas.setCustomDraw(this);
        if(!mBook.isPrintAutoPageNumbering()){
            this.mPageIndex = mBook.getPrintStartPageNumber()-1;
        }

        return null;
    }

    /**
     * 接口方法
     * @param g
     */
    public void PaintDraw(Graphics g) {
        if(mBook != null){
            initParams();
            drawPaper(g);
            drawBook(g);

//            initParams();
        }
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        if(mHelper == null){
            this.mHelper = new JPrintPreviewHelper(mBook,mCanvas);
        }
        this.mHelper.init();
        setStatus();
    }

    /**
     * 绘制纸张
     * @param g
     */
    public void drawPaper(Graphics g) {
        //背景
        g.setColor(Color.GRAY);
        g.fillRect(mCanvas.getX(), mCanvas.getY(), mCanvas.getWidth(), mCanvas.getHeight());
        //纸张
        g.setColor(Color.black);
        if(mHelper == null){
            return ;
        }
        int x=mHelper.getX();
        int y=mHelper.getY();
        double fitScale = mHelper.getPaperScale();
        int paperWidth = JPrintUtils.twipsToDots(mBook,mHelper.getPrintPaperWidth()*fitScale);
        int paperHeight = JPrintUtils.twipsToDots(mBook,mHelper.getPrintPaperHeight()*fitScale);
        g.fillRect(x + 2, y + 2, paperWidth + 2, paperHeight + 2);
        g.drawRect(x - 1, y - 1, paperWidth + 1, paperHeight + 1);
        g.setColor(Color.white);
        g.fillRect(x, y,  paperWidth, paperHeight);
    }

    /**
     * 绘制数据
     * @param g
     * @param scale
     */
    public void drawBook(Graphics g) {
        if (mBook == null) {
            return;
        }
        try {
            double paperScale = mHelper.getPaperScale();
            int startX = mHelper.getX()+JPrintUtils.twipsToDots(mBook,mHelper.getPrintLeftMargin()*paperScale);
            int startY = mHelper.getY()+JPrintUtils.twipsToDots(mBook,(mHelper.getPrintLeftMargin()+mHelper.getPrintHeaderMargin())*paperScale);

            int pageIndex = this.getPageIndex();
            PageInfo info = this.mHelper.getPageInfo(pageIndex);
            if(info == null){
                return;
            }
            int fixedRowCount = mHelper.getFixedRowCount();
            int fixedColCount = mHelper.getFixedColCount();

            int startRow = info.mStartRow;
            int rowCount = info.mEndRow-info.mStartRow+1;//+fixedRowCount;
//            System.out.println(rowCount);
            int[] rows = new int[] {rowCount};
            int startCol = info.mStartCol;
            int colCount = info.mEndCol - info.mStartCol+1;//+fixedColCount;
            int[] cols = {colCount};
            //单位转换为象素
//            double bookScale = mHelper.getPaperScale();

            int width = (int)JPrintUtils.twipsToDots(mBook,(mHelper.getAvailablePaperWidth()+mHelper.getFixedColWidth())*paperScale);
            int height = (int)JPrintUtils.twipsToDots(mBook,(mHelper.getAvailablePaperHeight()+mHelper.getFixedRowHeighth())*paperScale);

            short saveunits = mBook.getColWidthUnits();
            mBook.setColWidthUnits(mBook.eColWidthUnitsTwips);
            mBook.draw(g,
                       startX, //starting x coordinate of graphics image in offscreen coordinates.
                       startY, //starting y coordinate of graphics image in offscreen coordinates.
                       width, //width of the image in offscreen coordinates.
                       height, //height of the image if offscreen coordinates.
                       startRow, //beginning row of the workbook to be drawn - 0 based.
                       startCol, //beginning column of the workbook to be drawn - 0 based.
                       rows, //number of rows to be drawn.
                       cols, //number of columns to be drawn.
                       0, //beginning fixed row of the drawn worksheet - 0 based.
                       fixedRowCount, //number of rows to be fixed in the drawn worksheet.
                       0, //beginning fixed column of the drawn worksheet - 0 based.
                       fixedColCount); //number of columns to be fixed in the drawn worksheet.
            mBook.setColWidthUnits(saveunits);
//            g.setColor(Color.BLACK);
//            g.fillRect(startX,startY,width,height);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mPrevPageBtn) {
            onPrevPage();
        }
        else if (e.getSource() == this.mNextPageBtn) {
            onNextPage();
        }
        else if (e.getSource() == this.mPrintBtn) {
            onPrint();
        }
        else if (e.getSource() == this.mPageSetupBtn) {
            onPageSetup();
        }
        else if (e.getSource() == this.mFirstPageBtn) {
            onFirstPage();
        }
        else if (e.getSource() == this.mLastPageBtn) {
            onLastPage();
        }

    }

    /**
     *
     */

    private void onPrevPage() {
        int pageIndex = mPageIndex -1;
        if(pageIndex>=0){
            mPageIndex--;
            refresh();
        }

    }

    /**
     *
     */

    private void onNextPage() {
        int pageIndex = mPageIndex +1;
        if(pageIndex<mHelper.getPageCount()){
            mPageIndex++;
            refresh();
        }
    }

    /**
     *
     */
    private void onPrint() {
        try {
            JReportBookPrintSetDlg Print = new JReportBookPrintSetDlg(mBook);
            Print.CenterWindow();
            Print.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    private void onPageSetup() {
        SwingUtilities.invokeLater(new JBookPrintThread(mBook));
    }

    /**
     *
     */
    private void onFirstPage() {
        this.mPageIndex=0;
        refresh();
    }

    /**
     *
     */
    private void onLastPage() {
        this.mPageIndex=mHelper.getPageCount()-1;
        refresh();
    }

    /**
     *
     */
    private void refresh() {
        setStatus();
        this.repaint();
    }
    /**
     *
     */
    private void setStatus(){
        setButtonStatus();
        setInfo();
    }
    /**
     *
     */
    private void setInfo(){
        if(mHelper != null){
            this.mInfoLabel.setText("当前第" + (mPageIndex + 1) + "页 共" + mHelper.getPageCount() + "页");
        }
    }
    /**
     *
     */
    private void setButtonStatus() {
        if(this.getPageIndex()==0){
            this.mPrevPageBtn.setEnabled(false);
            this.mFirstPageBtn.setEnabled(false);
        }else{
            this.mPrevPageBtn.setEnabled(true);
            this.mFirstPageBtn.setEnabled(true);
        }
//        if(this.getPageIndex()>0 && this.getPageIndex()<this.mHelper.getPageCount()){
//            this.mPrevPageBtn.setEnabled(true);
//            this.mFirstPageBtn.setEnabled(true);
//            this.mNextPageBtn.setEnabled(true);
//            this.mLastPageBtn.setEnabled(true);
//        }

        if(this.getPageIndex()==this.mHelper.getPageCount()-1){
            this.mNextPageBtn.setEnabled(false);
            this.mLastPageBtn.setEnabled(false);
        }else{
            this.mNextPageBtn.setEnabled(true);
            this.mLastPageBtn.setEnabled(true);
        }
    }

    /**
     *
     * @return
     */
    private int getPageIndex(){
        return this.mPageIndex;
    }

    /**
     *
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class JBookPrintThread
        extends Thread {
        private JBook mBook = null;
        public JBookPrintThread(JBook book) {
            this.mBook = book;
        }

        public void run() {
            try {
                com.f1j.swing.ss.PageSetupDlg PageDlg = new com.f1j.swing.ss.PageSetupDlg(mBook);
                PageDlg.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
