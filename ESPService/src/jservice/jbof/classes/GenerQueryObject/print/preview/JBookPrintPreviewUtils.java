package jservice.jbof.classes.GenerQueryObject.print.preview;

import java.awt.*;

import com.f1j.swing.*;
import com.print.CanvasUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBookPrintPreviewUtils {
  public JBookPrintPreviewUtils() {
  }

  /**
   *
   * @param twips
   * @return
   */
  public static double twipsToDots(JBook book, double twips) {
    //return twips * getDpi() / getTwipseScale(book);
//        return (twips + 20.0 ) / 20.0 ;
    double Dots = 0;
    Dots = CanvasUtils.convertTwiptoPix(twips);
    return Dots;
  }

  /**
   *
   */
  public static double dotsToTwips(JBook book, double dots) {
//        return dots * getTwipseScale(book) / getDpi();
//      return (dots * 20.0 ) - 20.0 ;
    double Twips = 0;
    Twips = CanvasUtils.convertPixtoTwip(dots);
    return Twips;
  }

  /**
   *
   * @param inch
   * @return
   */
  public static double inchToTwips(JBook book, double inch) {
//        return  inch * getTwipseScale(book);
    double Twips = 0;
    Twips = CanvasUtils.convertInchtoTwip(inch);
    return Twips;
  }

  /**
   * 每英寸象素数
   * @return
   */
  public static double getDpi() {
    return Toolkit.getDefaultToolkit().getScreenResolution();
  }

  /**
   *
   * @return
   */
  public static double getTwipseScale(JBook book) {
//        if (book.isPrintLandscape()) {
//            return 1;
//        }
//        else {
    return JBookPrintPreviewConstants.TWIPS_SCALE;
//        }
  }

  /**
   *
   * @return
   */
  public static int getPaperOffset() {
    return JBookPrintPreviewConstants.PAPER_OFFSET;
  }
}