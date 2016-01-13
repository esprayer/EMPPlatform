package com.print.service;

import java.util.ResourceBundle;

/**  正式账页打印参数定义
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author  Yrh
 * @version 1.0
 */

public class AccountPrintFormatParam {
  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.print.service.Language");
  public String Type = "";//账册类型
  public String Format = "1";//账式:1、金额；2、数量；3、外币
  //页边距
  public double TopMargin = 50;
  public double BottomMargin = 50;
  public double LeftMargin = 100;
  public double RightMargin = 50;
  public boolean Default_Orientation = true;//打印方向
  public short PaperSize = 0; //纸张
  public String TitleFontName = "宋体";  //表头字体
  public int TitleFontSize = 16; //表头字号
  public String DefaultFontName = TitleFontName;//表体字体
  public int DefaultFontSize = 12;//表体字号
  public boolean YePrint = true; //有余额打印
  public boolean WePrint = false; //无余额也打
  public int Fixcol = 0;//固定列
  public int Valcol = 0;//变动列
  public int SaterPageNO = 1;//开始页号
  public int ChangePage = 1;//是否换页
  public int PageRows = 0;//每页行数,0为自动计算
  public boolean FillPage = false;//是否补空行
  public boolean SaveExcel = false;//是否成为Excel
  public boolean AccountBook  = true;//是否是正式账页
  public boolean TitleFontBold = false;
  public boolean TitleFontItalic = false;
  public boolean DefaultFontBold = false;
  public boolean DefaultFontItalic = false;
  public String DesignatedPage = "0"; //纸张
  public int ZyCount = 1;//打印账册总数
  public AccountPrintFormatParam() {
  }
}
