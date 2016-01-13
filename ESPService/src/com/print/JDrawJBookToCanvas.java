package com.print;

import com.f1j.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 * 此类完成通用的JBook类的打印预览
 */
public class JDrawJBookToCanvas {
  protected JBook  ViewBook = null;// 当前打印的JBook
  protected Object Context  = null;// 当前打印的上下文
  /**
   * 通用打印预览的构告函数,无论是账页，报表，凭证打印，都使用些打印预览对象
   * @param Book JBook
   * @param Object context
   */
  public JDrawJBookToCanvas(JBook Book,Object context) {
    ViewBook = Book;
    Context  = context;
  }
  /**
   * 打印预览的过程描述：
   * 1、确定JBook的页面设置参数，也就是打印参数，这些参数都是可以在页面设置对话框进行设置的
   *    A.纸张类型 纸张大小，如A4,B4等，确定出纸张的Size
   *    B.缩放比例 F1的缩放比例的方式有两种，按百分比，按纸张宽、高
   *    C.
   */
  /**
   *
   * @param g Graphics 用于绘制纸张的Canvas 的Graphics
   * @param F1PageSets JF1PageSets
   */
  protected void drawPage(Graphics2D g2,JF1PageSets F1PageSets) {

  }
}
