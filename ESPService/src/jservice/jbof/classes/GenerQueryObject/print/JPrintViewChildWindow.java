package jservice.jbof.classes.GenerQueryObject.print;

import java.awt.*;
import javax.swing.*;

import com.efounder.eai.application.classes.JBOFApplication;
import com.f1j.swing.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jbof.gui.window.classes.style.mdi.*;

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
public class JPrintViewChildWindow
    extends JBOFMDIChildWindow
    implements com.pub.comp.ICustomDraw {
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane tpPrintView = new JTabbedPane();
  JScrollPane jScrollPane1 = new JScrollPane();

  JBook mBook;
  com.pub.comp.JCustomPanel pnDraw = new com.pub.comp.JCustomPanel();
  int StartX, StartY, EndX, EndY;
  double PageWidth = 0, PageHeight = 0, PanelWidth = 0, PanelHeight = 0;
  double LeftMargin, HeaderMargin, RightMargin, BottomMargin;
  int Offset = 10;
  double scale = 1.0f;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public Object ProcessmnPrintPreview(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
//    ReportView.PrintView();
//    return null;
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ProcessmnPrint(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
//    mBook.PrintReport();
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ProcessmnPageSetup(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
//    mBook.PageSetup();
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JPrintViewChildWindow() {
    try {
      jbInit();
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
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    tpPrintView.setTabPlacement(JTabbedPane.BOTTOM);
    tpPrintView.setVerifyInputWhenFocusTarget(true);
    this.add(tpPrintView, BorderLayout.CENTER);
    tpPrintView.add(jScrollPane1, "打印预览");
    jScrollPane1.getViewport().add(pnDraw, null);
    tpPrintView.setSelectedComponent(jScrollPane1);
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitChildWindow(JBOFApplication App, JBOFMainWindow MainWindow, Object AddObject, Object AddObject1) {
    mBook = (JBook) AddObject;
    pnDraw.setCustomDraw(this);
    return null;
  }

  void PrintView(Graphics g, double scale) {
    if (mBook == null) {
      return;
    }
    try {
      int width, height, iStartRow, iStartCol;
      int[] row = {
          mBook.getLastRow() + 1};
      int[] col = {
          mBook.getLastCol() + 1};
      width = pnDraw.getWidth();
      height = pnDraw.getHeight();
      iStartRow = 0;
      iStartCol = 0;
      short saveunits = mBook.getColWidthUnits();
      mBook.setColWidthUnits(mBook.eColWidthUnitsTwips);
      mBook.draw(g, //handle to the graphics object for the offscreen image.
                 StartX + (int)this.LeftMargin, //starting x coordinate of graphics image in offscreen coordinates.
                 StartY + (int)this.HeaderMargin, //starting y coordinate of graphics image in offscreen coordinates.
                 (int) (this.PageWidth - this.LeftMargin - this.RightMargin), //width of the image in offscreen coordinates.
                 (int) (this.PageHeight - this.HeaderMargin - this.BottomMargin), //height of the image if offscreen coordinates.
                 iStartRow, //beginning row of the workbook to be drawn - 0 based.
                 iStartCol, //beginning column of the workbook to be drawn - 0 based.
                 row, //number of rows to be drawn.
                 col, //number of columns to be drawn.
                 0, //beginning fixed row of the drawn worksheet - 0 based.
                 0, //number of rows to be fixed in the drawn worksheet.
                 0, //beginning fixed column of the drawn worksheet - 0 based.
                 0); //number of columns to be fixed in the drawn worksheet.
      mBook.setColWidthUnits(saveunits);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void PaintDraw(Graphics g) {
//    PrintView(ReportView,g);
    CustomDraw(g);
  }

  void CustomDraw(Graphics g) {
    DrawPage(g);
  }

  void DrawPage(Graphics g) {

//    if ( !ReportView.isPrintLandscape() ) {
    PageWidth = mBook.getPrintPaperWidth();
    PageHeight = mBook.getPrintPaperHeight();
//    } else {
//      PageWidth = ReportView.getPrintPaperHeight();
//      PageHeight = ReportView.getPrintPaperWidth();
//    }
    PanelWidth = this.pnDraw.getWidth() - Offset;
    PanelHeight = this.pnDraw.getHeight() - Offset;
    if (PageWidth > PanelWidth && PageHeight <= PanelHeight) {
      scale = (PanelWidth / PageWidth);
      PageWidth = PageWidth * scale;
      PageHeight = PageHeight * scale;
    }
    if (PageWidth <= PanelWidth && PageHeight > PanelHeight) {
      scale = (PanelHeight / PageHeight);
      PageWidth = PageWidth * scale;
      PageHeight = PageHeight * scale;
    }
    if (PageWidth > PanelWidth && PageHeight > PanelHeight) {
      double tw, th;
      scale = (PanelWidth / PageWidth);
      tw = PageWidth * scale;
      th = PageHeight * scale;
      if (tw > PanelWidth || th > PanelHeight) {
        scale = (PanelHeight / PageHeight);
        tw = PageWidth * scale;
        th = PageHeight * scale;
      }
      PageWidth = tw;
      PageHeight = th;
    }
    StartX = (int) (pnDraw.getWidth() - PageWidth) / 2;
    StartY = (int) (pnDraw.getHeight() - PageHeight) / 2;
    EndX = (int) (StartX + PageWidth);
    EndY = (int) (StartY + PageHeight);
    g.setColor(Color.black);
//    g.fill3DRect(StartX,StartY,(int)PageWidth,(int)PageHeight,true);
    g.drawRect(StartX - 1, StartY - 1, (int) PageWidth + 1, (int) PageHeight + 1);
    g.setColor(Color.white);
    g.fillRect(StartX, StartY, (int) PageWidth, (int) PageHeight);
    double td = mBook.getPrintPaperWidth() / PageWidth;
    LeftMargin = mBook.getPrintLeftMargin() * td;
    RightMargin = mBook.getPrintRightMargin() * td;
    td = mBook.getPrintPaperHeight() / PageHeight;
    HeaderMargin = mBook.getPrintHeaderMargin() * td;
    BottomMargin = mBook.getPrintBottomMargin() * td;
    PrintView(g, scale);
  }
}