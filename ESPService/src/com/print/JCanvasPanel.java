package com.print;

import java.awt.*;
import javax.swing.JPanel;
import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JCanvasPanel extends JPanel {
  public static final double CANVAS_PAGE_MARGIN = 5.00;// 电子纸张在画布上的边距，以像素为单位,上下左右都是此边距
  protected JBook  Book    = null;
  protected Object Context = null;
  protected int    PagesNumber = 1;
  protected boolean Exact = false;
  protected CanvasPage[] ElecPages = null;
  public JCanvasPanel() {
    try {
      initComp();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void initComp() throws Exception {
    setBackground(Color.gray);
  }
  /**
   * 初始化画布
   * @param book JBook
   * @param context Object
   */
  public void initCanvas(JBook book,Object context) {
    initCanvas(book,context,1,false);
  }
  /**
   *
   * @param book JBook
   * @param context Object
   * @param pagenumber int
   * @param exact boolean
   */
  public void initCanvas(JBook book,Object context,int pagenumber,boolean exact) {
    Book    = book;
    Context = context;
    PagesNumber = pagenumber; // 每个画布显示几张纸
    Exact = exact; // 是否与纸张类型保持原样
  }
  /**
   *
   * @param g Graphics
   */
  public void paint(Graphics g) {
    super.paint(g);
    // 第一步，将Graphics 转换成Graphics2D，只有这个才能进行浮点型数据的绘制
    Graphics2D g2 = (Graphics2D)g;
    // 第二步，生成电子纸张，
    buildElecPage(g2);
    // 第三步，绘制电子纸张
    drawElecPage(g2);

  }
  // 绘制电子纸张
  protected void drawElecPage(Graphics2D g2) {

  }
  protected void buildElecPage(Graphics2D g2) {
    // 生成电子纸张数组
    buildElecPageArray();
    // 生成电子纸张数组在画布上的坐标
    buildElecPageCoord(g2);
  }
  // 生成电子纸张数组
  private void buildElecPageArray() {
    ElecPages = new CanvasPage[PagesNumber];
  }
  // 生成电子纸张数组在画布上的坐标
  /**
   * 有两种生成坐标的方式：
   * 1、按照纸张原型生成，也就是参数Exact=true
   * 2、按照画布大小生成，也就是参数Exact=false
   *
   * @param g2 Graphics2D
   */
  private void buildElecPageCoord(Graphics2D g2) {
    // 按照原型进行坐分配
    if ( Exact ) {
      buildElecPageCoord1(g2);
    } else { // 不按照原型进行分配
      buildElecPageCoord2(g2);
    }
  }
  // 按原型分配坐标
  private void buildElecPageCoord1(Graphics2D g2) {
    // 咱们就先不实现它吧
  }
  // 不按原型分配坐标
  private void buildElecPageCoord2(Graphics2D g2) {
    JF1PageSets F1PageSets = new JF1PageSets(Book);
  }

  /**
   *
   * @return int
   */
  public int getPagesNumber() {
    return PagesNumber;
  }
  /**
   *
   * @param pn int
   */
  public void setPagesNumber(int pn) {
    if ( pn != PagesNumber ) {
      PagesNumber = pn;
      // 如果页数不同，需要重新预览
      reprintview();
    }
  }
  /**
   * 重新Print view
   */
  protected void reprintview() {
    invalidate();
  }
  /**
   * 电子纸张类,纸张的坐标是以像素为单位
   * <p>Title: </p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: </p>
   * @author not attributable
   * @version 1.0
   */
  public class CanvasPage {
    public double x = 0.00;// 开始X坐标
    public double y = 0.00;// 开始Y坐标
    public double w = 0.00;// 宽度
    public double h = 0.00;// 高度
    public int    page = 1;// 页码
  }
}
