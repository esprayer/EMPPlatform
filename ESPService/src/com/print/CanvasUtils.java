package com.print;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CanvasUtils {

    /**
     * 像素  磅     毫米    十二点活字    厘米       英寸
     *  1  3/4   127/480    1/16    127/4800    1/96
     */

    public static final double TWIP_TO_INCH     = 1440.00;
    public static final double InchToCentimiter = 2.54;
    public static final double CentimiterToInch = 0.3937;

    /**
     * 当前屏幕的分辨率，也就是DPI（dot per inch:每英寸像素数）
     */
    public static double PIX_TO_INCH = 0.00;
    static {
        PIX_TO_INCH = Toolkit.getDefaultToolkit().getScreenResolution();
    }

    /**
     * DPI:dot per inch，每英寸象素数
     * @return double
     */
    public static double getDPI() {
        return Toolkit.getDefaultToolkit().getScreenResolution();
    }

    /**
     * 英寸换算为厘米
     * @param   inch  double
     * @return        double
     */
    public static double convertInchToCentimiter(double inch) {
        return inch * InchToCentimiter;
    }

    /**
     * 厘米换算为英寸
     * @param   inch  double
     * @return        double
     */
    public static double convertCentimiterToInch(double centimiter) {
        return centimiter * CentimiterToInch;
    }

    /**
     * 转换Twip单位到英寸
     * @param twip double
     * @return double
     */
    public static double convertTwiptoInch(double twip) {
        return twip / TWIP_TO_INCH;
    }

    /**
     * 转换Inch单位到Twip单位
     * @param inch double
     * @return double
     */
    public static double convertInchtoTwip(double inch) {
        return inch * TWIP_TO_INCH;
    }

    /**
     * 转换像素PIX到英寸,不同的屏幕有不同的分PIAN率
     * @param pix double
     * @return double
     */
    public static double convertPixtoInch(double pix) {
        return pix / PIX_TO_INCH;
    }

    /**
     * 转换英寸到点,不同的屏幕有不同的分PIAN率
     * @param inch double
     * @return double
     */
    public static double convertInchtoPix(double inch) {
        return inch * PIX_TO_INCH;
    }

    /**
     * 转换像素到缇
     * @param pix double
     * @return double
     */
    public static double convertPixtoTwip(double pix) {
        // 先转成inch
        double d = convertPixtoInch(pix);
        // Inch to Twip 经过inch 这一步转换
        return convertInchtoTwip(d);
    }

    /**
     * 转换缇到像素
     * @param twip double
     * @return double
     */
    public static double convertTwiptoPix(double twip) {
        // 先将Twip 转换成Inch
        double d = convertTwiptoInch(twip);
        // 再将inch 转换成PIX
        return convertInchtoPix(d);
    }
}
