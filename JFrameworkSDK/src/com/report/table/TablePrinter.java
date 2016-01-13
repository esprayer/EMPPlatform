package com.report.table;

import com.report.table.jxml.*;
import java.awt.*;
import java.awt.print.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;


public class TablePrinter implements Printable, Pageable {

    /**
     * These constants indicate which pages should include column headers or title
     */
    static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  public final static int ALL_PAGES = 0;
    public final static int FIRST_PAGE_ONLY = 1;
    public final static int NO_PAGES = 2;
    private static final String DEFAULT_FORMAT = ",##0.00";
    JPanel title;
    JTable table;
    PageFormat pageFormat;
    TableManager tableManager;

    int titleStatus = FIRST_PAGE_ONLY;
    int headerStatus = ALL_PAGES;

    boolean initDone = false;

    Font printFont;
    FontMetrics printFontMetrics;

    int recallCounts = 1;

    GroupableTableHeader tableHeader;

    int maxPages = 1;

    int rowHeight;
    int rowMargin;
    int rowCount;

    int columnWidth;
    int columnMargin;

    int pageHeight;
    int pageWidth;
    int positionX;
    int positionY;

    int headerWidth;
    int headerHeight;
    Vector headerSplit = new Vector();//元素为String数组，分别为x、y、width、height、caption、

    int titleWidth;
    int titleHeight;

    int tableHeight;
    int tableWidth;

    int rootWidth;
    int rootHeight;

    Vector columnSplit = new Vector();//元素为列宽数组 int型，数组长度为列数，值为列宽
    int rowCountOnFirstPage ;
    int rowCountOnNextPage;
    int rowCountOnLastPage;

    int subPageIndex = 0;
    int prevPageIndex = 0;

    public TablePrinter(JPanel jp, JTable tbl, PageFormat pf, TableManager tm) {
        title = jp;
        table = tbl;
        pageFormat = pf;
        tableManager = tm;

    }
    public void setHeaderStatus(int headerStatus_){
        this.headerStatus = headerStatus_;
    }
    public void setTitleStatus(int titleStatus_){
        this.titleStatus = titleStatus_;
    }
    public int getNumberOfPages() {

        if(!initDone)
            init();
        return maxPages;
    }
    public int getMaxPages(){
		return maxPages;
	}
    public PageFormat getPageFormat(int index) {
        return pageFormat;
    }
    public Printable getPrintable(int index) {
        return this;
    }

    private void init() {
        //初始化table的宽度
        if ((table.getWidth() == 0) || (table.getHeight() == 0)) {
            table.setSize(table.getPreferredSize());
        }
        //得到页的信息
        positionX = (int)pageFormat.getImageableX();
        positionY = (int)pageFormat.getImageableY();
        pageWidth = (int)pageFormat.getImageableWidth();
        pageHeight = (int)pageFormat.getImageableHeight();
        //得到表的信息
        tableWidth = (int) table.getColumnModel().getTotalColumnWidth();
        tableHeight = table.getHeight();
        //得到Title的宽度和高度
        if(title == null){
            titleWidth = 0;
            titleHeight = 0;
        }else{
            titleWidth = tableWidth;//title.getWidth();
            titleHeight = title.getHeight();
        }
        //得到表头的信息
        tableHeader = (GroupableTableHeader)table.getTableHeader();
        if(tableHeader == null){
            headerWidth = 0;
            headerHeight = 0;
        }else{
            headerWidth = tableWidth;//tableHeader.getWidth();
            headerHeight = tableHeader.getHeight();
        }
        //得到行的信息
        rowHeight = table.getRowHeight();
        rowMargin = table.getRowMargin();
        rowCount = table.getRowCount();
        //得到列的分割和行的分割
        int x = 0;//水平指针
        int y = 0;//垂直指针
        int fontHeight;
        //得到页脚的高度 宋体 10号
        //printFont = new Font("宋体",Font.PLAIN,10);
        //g.setFont(printFont);
        //printFontMetrics = g.getFontMetrics(printFont);
        //fontHeight = printFontMetrics.getHeight();
        fontHeight = 10;
        rootHeight = fontHeight*2;
        //计算行信息和最大页数
        //第一页
        maxPages = 0;
        if(titleStatus != NO_PAGES){
            y += titleHeight;
        }
        if(headerStatus != NO_PAGES){
            y += headerHeight;
        }
        rowCountOnFirstPage = Math.min ((pageHeight - rootHeight - y)/(rowHeight + rowMargin) , rowCount);
        maxPages += 1;
        if(rowCountOnFirstPage >= rowCount){
            rowCountOnLastPage = 0;
            rowCountOnLastPage = 0;
        }else{
            //以后每页
            y = 0;
            if(titleStatus == ALL_PAGES){
                y += titleHeight;
            }
            if(headerStatus == ALL_PAGES){
                y += headerHeight;
            }
            rowCountOnNextPage = (pageHeight - rootHeight - y)/(rowHeight + rowMargin);
            rowCountOnLastPage = (rowCount - rowCountOnFirstPage)%rowCountOnNextPage;
        }
        if(rowCountOnNextPage != 0)
            maxPages += (rowCount - rowCountOnFirstPage)/rowCountOnNextPage;
        if(rowCountOnLastPage != 0)
            maxPages += 1;
        //计算列信息
        columnSplit.removeAllElements();

        TableColumnModel tableColumnModel = table.getColumnModel();
        int columnCount = tableColumnModel.getColumnCount();
        TableColumn tableColumn = null;

        columnMargin = tableColumnModel.getColumnMargin();

        int length = 0;
        int[] temp = new int[100];//假设每页最多100列
        int ip = 0;
        for(int i=0; i<columnCount; i++){
            tableColumn = tableColumnModel.getColumn(i);
            columnWidth = tableColumn.getWidth();
            if(length + columnWidth + columnMargin > pageWidth){
                length = columnWidth + columnMargin;
                int[] width = new int[ip];
                for(int j=0; j<width.length; j++){
                  width[j] = temp[j];
                }
                columnSplit.addElement(width);
                ip=0;
            }else{
                temp[ip] = columnWidth ;
                ip++;
                length += columnWidth + columnMargin;
            }
        }

        //加上最后一页的列信息
        if (length > columnMargin) {// if there is more columns
            int tttt = 0;
            int[] width = new int[ip+1];
            for(int j=1; j<width.length; j++){
                width[j] = temp[j-1];
                tttt += temp[j-1]+columnMargin;
            }
            width[0] = length-tttt-columnMargin;
            columnSplit.addElement(width);
        }
        //页*
        maxPages *= columnSplit.size();
        //get headerSplit
        headerSplit.removeAllElements();
        Vector xmlTableView = tableManager.getTableView();
        int hX = 0;
        int hY = 0;
        int hWidth = headerWidth;
        int hHeight = headerHeight;
        HeaderParameter hp = null;
        for (int i = 0; i < xmlTableView.size(); i++) {
            XmlViewCol xmlViewCol = (XmlViewCol) xmlTableView.elementAt(i);
            if (xmlViewCol.getType().equals("col")) {
                XmlColumn xmlColumn = tableManager.getColumnById(xmlViewCol.getId());
                hp = makeCol(xmlColumn,hHeight,hX,hY);
            }
            if (xmlViewCol.getType().equals("group")) {
                XmlGroup xmlGroup = tableManager.getGroupById(xmlViewCol.getId());
                hp = makeGroup(xmlGroup,hHeight,hX,hY);
            }
            hX += hp.width;
        }

//        printInfo();
        initDone = true;
    }
    private HeaderParameter makeCol(XmlColumn xmlColumn,int height,int x,int y){

	    TableColumn tableColumn = table.getColumn(xmlColumn.getId());

        HeaderParameter hp = new HeaderParameter(x,y,tableColumn.getWidth()+columnMargin,height,xmlColumn.getCaption());//Integer.parseInt(xmlColumn.getWidth())
        headerSplit.addElement(hp);
        return hp;
    }
    private HeaderParameter makeGroup(XmlGroup xmlGroup,int height,int x,int y){
        int hX = x;
        HeaderParameter hp = new HeaderParameter(x,y,0,rowHeight+rowMargin,xmlGroup.getCaption());
        HeaderParameter hp1 = null;
        Vector xmlItems = xmlGroup.getItems();
        for(int i=0; i<xmlItems.size(); i++){
            XmlItem xmlItem = (XmlItem)xmlItems.elementAt(i);
            if(xmlItem.getType().equals("col")){
                XmlColumn xmlColumn = tableManager.getColumnById(xmlItem.getId());
                hp1 = makeCol(xmlColumn,height-hp.height,hX,hp.y+hp.height);
            }else{
                XmlGroup xmlGroup1 = tableManager.getGroupById(xmlItem.getId());
                hp1 = makeGroup(xmlGroup1,height-hp.height,hX,hp.y+hp.height);
            }
            hX += hp1.width;
            hp.width += hp1.width;
        }
        headerSplit.addElement(hp);
        return hp;
    }
    private int getPageLeft(int columnIndex){
        if (columnIndex == 0){
            return 0;
        }
        int result = 0;
        for(int i=0; i<columnIndex; i++){
            int[] iii = (int[])columnSplit.elementAt(i);
            for(int j=0; j<iii.length; j++){
                result += iii[j]+columnMargin;//
            }
        }
        return result;
    }
    private int getIniCol(int columnIndex){
        if(columnIndex == 0){
            return 0;
        }
        int result = 0;
        for(int i=0; i<columnIndex; i++){
            int[] iii = (int[])columnSplit.elementAt(i);
            result += iii.length;
        }
        return result;
    }
    /**
     * Perform the printing here
     */
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        if(pageIndex > maxPages-1){
            return NO_SUCH_PAGE;
        }

        if (prevPageIndex != pageIndex) {
            subPageIndex++;
            if (subPageIndex == columnSplit.size()) {
                subPageIndex = 0;
            }
        }

        int X = (int) pageFormat.getImageableX();
        int Y = (int) pageFormat.getImageableY();

        g.translate(X, Y);
        int rowIndex = pageIndex / columnSplit.size();
        //关闭双缓冲
        RepaintManager manager = RepaintManager.currentManager(table);
        boolean wasEnabled = manager.isDoubleBufferingEnabled();
        manager.setDoubleBufferingEnabled(false);

        printSubTable(g,rowIndex,subPageIndex);

        //打开双缓冲
        manager.setDoubleBufferingEnabled(true);

        prevPageIndex = pageIndex;

        return Printable.PAGE_EXISTS;
    }
    protected void printSubTable(Graphics g,int rowIndex,int columnIndex) {

        int strLength;
        int fontHeight;
        int fontAscent;
        int fontDescent;
        int fontLeading;

        String pageNumber = res.getString("String_62") + (rowIndex + 1);
        if (columnSplit.size() > 1)
            pageNumber += "-" + (columnIndex + 1);
        pageNumber += res.getString("String_64") + maxPages/columnSplit.size() + res.getString("String_65");
        printFont = new Font("宋体",Font.PLAIN,10);
        g.setFont(printFont);
        printFontMetrics = g.getFontMetrics(printFont);
        fontHeight = printFontMetrics.getHeight();
        fontLeading = printFontMetrics.getLeading();
        strLength = printFontMetrics.stringWidth(pageNumber);
        g.drawString(pageNumber, (pageWidth-strLength-fontLeading) / 2 , pageHeight - fontHeight);

        int x = 0;
        int y = 0;
        int clipHeight = 0;
        int clipWidth = 0;

        //画title
        if( titleStatus == ALL_PAGES || (titleStatus == FIRST_PAGE_ONLY && rowIndex == 0)){
            x = getPageLeft(columnIndex);
            clipWidth = getPageLeft(columnIndex + 1) -getPageLeft(columnIndex);
            clipHeight = titleHeight;

            g.translate(-x, 0);
            g.setClip(x, 0, clipWidth, clipHeight);

            //Get the title's preferred size
            titleWidth = title.getWidth();
            tableWidth = table.getColumnModel().getTotalColumnWidth();
            if(titleWidth != tableWidth){
                title.invalidate();
                title.setSize(tableWidth,titleHeight);
                title.validate();
            }

//            title.paint(g); // drawing the header on every page
            Component[] titles = title.getComponents();
            for(int i=0;i<titles.length;i++){
                JPanel title = (JPanel)titles[i];
                int tX = title.getX();
                int tY = title.getY();
                Component[] labels = title.getComponents();
                for(int j=0; j<labels.length; j++) {
                    JLabel label = (JLabel)labels[j];
                    printFont = label.getFont();
                    printFontMetrics = g.getFontMetrics(printFont);
                    fontAscent = printFontMetrics.getAscent();
                    fontLeading = printFontMetrics.getLeading();

                    String text = label.getText();
                    strLength = printFontMetrics.stringWidth(text);
                    int align = label.getHorizontalAlignment();
                    int width = label.getWidth();
                    int xxxx = 0;//偏移
                    if(align == JLabel.RIGHT){
                        xxxx = width - strLength - fontLeading;
                    }else if(align == JLabel.CENTER){
                        xxxx = (width-strLength - fontLeading)/2;
                    }else{
                        xxxx = fontLeading;
                    }
                    int pX = tX + label.getX() + xxxx;
                    int pY = tY + label.getY() + fontAscent;
                    g.setColor(Color.black);
                    g.setFont(label.getFont());
                    g.drawString(text,pX,pY);
                }
            }
            //恢复title的width
            title.invalidate();
            title.setSize(titleWidth,titleHeight);
            title.validate();
            g.translate(x, 0);
            y += titleHeight;
        }
        //画多级表头
        if(headerStatus == ALL_PAGES || (headerStatus == FIRST_PAGE_ONLY && rowIndex == 0)){

            x = getPageLeft(columnIndex);
            clipWidth = getPageLeft(columnIndex + 1) - getPageLeft(columnIndex);
            clipHeight = headerHeight;

            g.translate(-x, y);
            g.setClip(x, 0, clipWidth, clipHeight);

            printFont = new Font("宋体",Font.BOLD,12);//tableHeader.getFont();
            g.setFont(printFont);
            printFontMetrics = g.getFontMetrics(printFont);
            fontAscent = printFontMetrics.getAscent();
            fontLeading = printFontMetrics.getLeading();
            for(int i=0; i<headerSplit.size(); i++){
                HeaderParameter hp = (HeaderParameter)headerSplit.elementAt(i);
                g.drawLine(hp.x,hp.y,hp.x + hp.width,hp.y);
                g.drawLine(hp.x, hp.y, hp.x, hp.y + hp.height);
                strLength = printFontMetrics.stringWidth((String)hp.object);
                g.drawString(hp.object,hp.x + (hp.width-strLength-fontLeading)/2 ,hp.y + (fontAscent+hp.height)/2);

            }

            //画边框
            g.drawLine(x+clipWidth,y,x+clipWidth,y+clipHeight);
            g.drawLine(0, y+clipHeight, clipWidth, y+clipHeight);

//            tableHeader.paint(g);
            g.translate(x, -y);
            y += headerHeight;
        }

        //画表体
        int iniRow;
        int endRow;
        int iniCol;
        int endCol;

        if(rowIndex == 0){//画第一页
            iniRow = 0;
            endRow = rowCountOnFirstPage;
        }else if(rowIndex == maxPages/columnSplit.size()-1){//画最后一页
            iniRow = rowCount - rowCountOnLastPage;
            endRow = rowCount;
        }else{//画中间页
            iniRow = rowCountOnFirstPage + (rowIndex - 1) * rowCountOnNextPage;
            endRow = rowCountOnFirstPage + rowIndex * rowCountOnNextPage;
        }
        iniCol = getIniCol(columnIndex);
        endCol = getIniCol(columnIndex + 1);

        x = 0;

        clipWidth = getPageLeft(columnIndex + 1) - getPageLeft(columnIndex);
        clipHeight = (endRow - iniRow)*(rowHeight + rowMargin);
        g.setClip(x,y,clipWidth,clipHeight);
        printFont = new Font("宋体",Font.PLAIN,12);//table.getFont();
        g.setFont(printFont);
        printFontMetrics = g.getFontMetrics(printFont);
        fontHeight = printFontMetrics.getHeight();
        fontAscent = printFontMetrics.getAscent();
        fontLeading = printFontMetrics.getLeading();

        String cell;
        int cellX = 0;
        int cellY = 0;

        TableColumn currentColumn;
        ColumnRenderer columnRenderer;
        TableModel tableModel = table.getModel();
        TableColumnModel columnModel = table.getColumnModel();
        for(int j = iniCol; j<endCol; j++){
            currentColumn = columnModel.getColumn(j);
            columnRenderer = (ColumnRenderer)currentColumn.getCellRenderer();

            String format = columnRenderer.getFormat();
            String align = columnRenderer.getAlign();
            String datatype = columnRenderer.getDatatype();

            int xxxx = 0;//x偏移
            cellX =getCellX(columnIndex,j-iniCol);

            for(int i=iniRow; i<endRow; i++){

                cell = (String)formateValue(tableModel.getValueAt(i,j),datatype,format);
                cellY = (i - iniRow) * (rowHeight + rowMargin);
                strLength = printFontMetrics.stringWidth(cell);

            if(align.equalsIgnoreCase("right")){
                xxxx = currentColumn.getWidth() - strLength-fontLeading;
            }else if(align.equalsIgnoreCase("center")){
                xxxx = (currentColumn.getWidth() - strLength-fontLeading)/2;
            }else{
                xxxx = fontLeading;
            }

                //画左上角
                g.drawLine(cellX,cellY + y,cellX,cellY + rowHeight + rowMargin + y);
                g.drawLine(cellX,cellY + y,cellX + currentColumn.getWidth() + columnMargin,cellY + y);
                if(!cell.trim().equals(""))
	                g.drawString(cell,cellX + xxxx, cellY + y + (fontAscent+rowHeight)/2);
            }
        }
        //画边框
        g.drawLine(clipWidth,y,clipWidth,y+clipHeight);
        g.drawLine(0, y+clipHeight, clipWidth, y+clipHeight);
//        g.translate(positionX,positionY);
//        g.setClip(positionX,positionY,tableWidth,positionY+headerHeight+tableHeight);
//        g.drawLine(positionX + tableWidth,positionY,clipWidth,positionY+headerHeight+tableHeight);
//        g.drawLine(positionX, positionY+headerHeight+tableHeight, positionX + tableWidth, positionY+headerHeight+tableHeight);

    }

public Object formateValue(Object value, String datatype, String format) {

    if (datatype.equalsIgnoreCase("n")
        && value != null
        && !((String) value).trim().equals(
            "")) { //&& (value != null) && (value instanceof Number)
        if (isDouble(value)) {
            double v = Double.parseDouble((String) value);
            if (Math.abs(v) < 0.005) { //<0.01)
                value = "";
            } else {
                if ((format == null) || format.trim().equals("")) {
                    format = DEFAULT_FORMAT;
                }
                NumberFormat formatter = new DecimalFormat(format);
                value = formatter.format(v);
            }
        }
    }

    //if (datatype.equalsIgnoreCase("n")
        //&& value != null
        //&& !((String) value).trim().equals(
            //"")) { //&& (value != null) && (value instanceof Number)
        //if (isDouble(value)) {
            //if ((format == null) || format.trim().equals("")) {
                //format = DEFAULT_FORMAT;
            //}
            //NumberFormat formatter = new DecimalFormat(format);
            //value = formatter.format(new Double((String) value).doubleValue());
        //}
    //}
    return value;
}
  private boolean isDouble(Object value){
      char c[] = ((String)value).toCharArray();
      for(int i=0 ; i<c.length; i++){
          if(c[i]<'0' || c[i]>'9'){
              if(c[i]!='.' && c[i]!='-' && c[i]!='e' && c[i]!='E'){
                  return false;
              }
          }
      }
      return true;
  }

    private void printColumnHeader(Graphics g, XmlColumn xmlColumn,int x,int y,int hWidth, int hHeight){
        String name = xmlColumn.getCaption();
        g.drawLine(x,y,x,y+hHeight);
        g.drawLine(x,y,x+hWidth,y);
        g.drawString(name,x,y);
    }
    private void printGroupHeader(Graphics g, XmlGroup xmlGroup, int x, int y,int hWidth, int hHeight){
        Vector xmlItems = xmlGroup.getItems();
        for(int i=0; i<xmlItems.size(); i++){
            XmlItem xmlItem = (XmlItem)xmlItems.elementAt(i);
            if(xmlItem.getType().equalsIgnoreCase("col")){
                XmlColumn xmlColumn = tableManager.getColumnById(xmlItem.getId());
                printColumnHeader(g, xmlColumn,x,y,hWidth,hHeight);
            }else{
                XmlGroup xmlGroup1 = tableManager.getGroupById(xmlItem.getId());
                printGroupHeader(g, xmlGroup1,x,y,hWidth,hHeight);
            }

        }
    }
    private int getCellX(int columnIndex, int cellIndex){
        if(cellIndex == 0)
            return 0;
        int result = 0;
        for(int i=0; i<cellIndex; i++){
            result += ((int[])columnSplit.elementAt(columnIndex))[i] + columnMargin;
        }
        return result;
    }
    private void printInfo(){

//System.out.println(" maxPages = " + maxPages );

//System.out.println(" rowHeight = " + rowHeight );
//System.out.println(" rowMargin = " + rowMargin );
//System.out.println(" rowCount = " + rowCount );

//System.out.println(" columnMargin = " + columnMargin );

//System.out.println(" pageHeight = " + pageHeight );
//System.out.println(" pageWidth = " + pageWidth );
//System.out.println(" positionX = " + positionX );
//System.out.println(" positionY = " + positionY );

//System.out.println(" headerWidth = " + headerWidth );
//System.out.println(" headerHeight = " + headerHeight );

//System.out.println(" titleWidth = " + titleWidth );
//System.out.println(" titleHeight = " + titleHeight );

//System.out.println(" tableHeight = " + tableHeight );
//System.out.println(" tableWidth = " + tableWidth );

//System.out.println(" rootWidth = " + rootWidth );
//System.out.println(" rootHeight = " + rootHeight );

//System.out.println(" rowCountOnFirstPage = " + rowCountOnFirstPage );
//System.out.println(" rowCountOnNextPage = " + rowCountOnNextPage );
//System.out.println(" rowCountOnLastPage = " + rowCountOnLastPage );


        for(int i=0; i<columnSplit.size(); i++){
            //System.out.println(i+ " 页 = ");
            int[] haha = (int[])columnSplit.elementAt(i);
            for(int j=0; j<haha.length; j++){
                //System.out.println(j+ " 列 = "+haha[j]);
            }
        }
        for(int i=0; i<headerSplit.size(); i++){
            HeaderParameter haha = (HeaderParameter)headerSplit.elementAt(i);
                //System.out.println("x = " + haha.x + ", y = " + haha.y + ", w = " + haha.width + ", h = " + haha.height);
                //System.out.println(haha.object);
        }
    }
//    private class HeaderParameter{
//      public int x , y , width , height ;
//      public String object ;
//      public HeaderParameter(int x , int y , int width , int height , String object)
//      {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        this.object = object;
//      }
//    }
}
