package com.report.table;

import java.awt.*;
import java.awt.print.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import com.report.table.jxml.*;
//import com.pansoft.xreport.xreport.XReport;
//import com.f1j.swing.*;
//import jdatareport.dof.classes.*;
//import jdatareport.dof.classes.report.*;
//import jdatareport.dof.classes.report.util.*;
//import jdatareport.dof.classes.report.paint.*;

/**
 * 此处插入类型描述。
 * 创建日期：(2002-1-21 13:01:46)
 * @author：lubo
 */
//Xyz add 2004/08/11 分页显示 START
public class MultiLevelHeaderTable extends JPanel implements AdjustmentListener {
//Xyz add 2004/08/11 分页显示 END
    //属性
    static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  JPanel topPanel;

    public JTable table;
    JScrollPane    scrollPane;
    //Xyz add 2004/08/11 分页显示 START
    JScrollBar scrollBarV;
    Vector     colIds;
    GroupableHeaderTableModel dm;
    //Xyz add 2004/08/11 分页显示 END

    TableManager     tableManager;
    TableDataManager dataManager;
//    XReport xr;

    PageFormat pageFormat;
    //构造函数
    public MultiLevelHeaderTable() {
        super(new BorderLayout());
    }

    public MultiLevelHeaderTable(String sTable, String sData) {
        super(new BorderLayout());
        try{
//          xr=new XReport(sTable,sData);
        }
        catch (Exception re){
        }
        openData(sTable, sData);
//        try {
//          JBook Book = new JBook();
//          String[] queryResult = {sTable, sData};
//          JQueryPainter QP = new JQueryPainter(Book,queryResult);
//          QP.drawQuery();
//          add(Book, BorderLayout.CENTER);

//          JDRModel model = new JDRModel(queryResult);
//          model.setQueryFormat(new JDRQueryFormatInfo());
//          JDataReport report = new JDataReport(model);
//          report.load();
//          this.add(report, BorderLayout.CENTER);
//        } catch ( Exception e ) {
//          e.printStackTrace();
//        }
    }

    //Xyz add 2004/08/11 分页显示 START
    public synchronized void adjustmentValueChanged(AdjustmentEvent e) {
      if(scrollBarV != null){
        //System.out.println(scrollBarV.getHeight()+":"+scrollBarV.getMaximum()+":"+e.getValue());
        if ( (e.getValue()) >= (scrollBarV.getMaximum()-(scrollBarV.getHeight()*2)) ) {
          //System.out.println("true");
          //
          Object[][] nDatas = dataManager.getTableDatas(colIds.toArray());
          if (nDatas != null) {
            Object[][] oDatas  = dm.datas;
            Object[][] hbDatas = new Object[nDatas.length+oDatas.length][colIds.size()];
            System.arraycopy(oDatas,0,hbDatas,0,oDatas.length);
            System.arraycopy(nDatas,0,hbDatas,oDatas.length,nDatas.length);
            //
            dm.datas = hbDatas;
            dm.fireTableDataChanged();
            table.repaint();
            this.repaint();
          }
        }
        else {
          //System.out.println("false");
          //
        }
      }
    }
    //Xyz add 2004/08/11 分页显示 END

    //属性函数
    public JTable getJTable() {
        return table;
    }
    //功能函数
    protected void makeTitle() {
        topPanel = new JPanel();
        //加边
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        BoxLayout bl = new BoxLayout(topPanel, BoxLayout.Y_AXIS);
        topPanel.setLayout(bl);

        //得到报表属性
        Vector titles = tableManager.getTitles();
        for (int i = 0; i < titles.size(); i++) {
            XmlTitle title = (XmlTitle) titles.elementAt(i);
            String align = title.getAlign();
            Font font = title.getFont();
            Vector labels = title.getLabels();
            JPanel containPanel = new JPanel();
            //每行表头加间距
//            containPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            containPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            if (align.equals("left")) {
                containPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            }
            if (align.equals("right")) {
                containPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            }
            if (align.equals("center")) {
                containPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            }
            if (align.equals("x_axis")) {
                containPanel.setLayout(new GridLayout(1, labels.size()));
            }
            if (align.equals("y_axis")) {
                BoxLayout ybl = new BoxLayout(containPanel, BoxLayout.Y_AXIS);
                containPanel.setLayout(ybl);
            }

            for (int j = 0; j < labels.size(); j++) {
                XmlLabel label = (XmlLabel) labels.elementAt(j);
                JLabel titleLabel = new JLabel(label.getCaption());
                if (align.equals("x_axis")) {
                    if (j == 0) {
                        titleLabel.setHorizontalAlignment(JLabel.LEFT);
                    } else
                        if (j == labels.size() - 1) {
                            titleLabel.setHorizontalAlignment(JLabel.RIGHT);
                        } else {
                            titleLabel.setHorizontalAlignment(JLabel.CENTER);
                        }
                }
                titleLabel.setFont(font);

                containPanel.add(titleLabel);
            }
            topPanel.add(containPanel);
        }
        this.add(topPanel, BorderLayout.NORTH);
    }
    protected void makeTable() {
        //形成报表表头基本数据
        Vector xmlColumns = tableManager.getColumns();
        Vector xmlTableView = tableManager.getTableView();
        Vector xmlGroups = tableManager.getGroups();

        //Xyz add 2004/08/11 分页显示 START
        colIds = new Vector();
        //Xyz add 2004/08/11 分页显示 END
        for (int i = 0; i < xmlTableView.size(); i++) {
            XmlViewCol xmlViewCol = (XmlViewCol) xmlTableView.elementAt(i);
            if (xmlViewCol.getType().equals("col")) {
                XmlColumn xmlColumn =
                    (XmlColumn) TableManager.getElementById(xmlColumns, xmlViewCol.getId());
                colIds.addElement(xmlColumn.getId());
            }
            if (xmlViewCol.getType().equals("group")) {
                XmlGroup xmlGroup =
                    (XmlGroup) TableManager.getElementById(xmlGroups, xmlViewCol.getId());
                Vector leafItems = getLeafItems(xmlGroup, xmlGroups);
                for (int j = 0; j < leafItems.size(); j++) {
                    XmlItem leafItem = (XmlItem) leafItems.elementAt(j);
                    XmlColumn xmlColumn =
                        (XmlColumn) TableManager.getElementById(xmlColumns, leafItem.getId());
                    colIds.addElement(xmlColumn.getId());
                }
            }
        }


        //形成报表表体基本数据
        Object[][] newdata = dataManager.getTableDatas(colIds.toArray());

        //生成基本报表
        //Xyz add 2004/08/11 分页显示 START
        dm = new GroupableHeaderTableModel();
        //Xyz add 2004/08/11 分页显示 END
        dm.setDatas(newdata, colIds.toArray());
        table = new JTable(dm);
        //set id
        for (int i=0; i<colIds.size(); i++){
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setIdentifier(colIds.elementAt(i));
        }

        //形成多级表头
        GroupableTableHeader header = new GroupableTableHeader(table.getColumnModel());
        table.setTableHeader(header);

        for (int i = 0; i < xmlTableView.size(); i++) {
            XmlViewCol xmlViewCol = (XmlViewCol) xmlTableView.elementAt(i);
            if (xmlViewCol.getType().equals("col")) {
                XmlColumn xmlColumn =
                    (XmlColumn) TableManager.getElementById(xmlColumns, xmlViewCol.getId());
                TableColumn tableColumn = makeColumn(xmlColumn);
            }
            if (xmlViewCol.getType().equals("group")) {
                XmlGroup xmlGroup =
                    (XmlGroup) TableManager.getElementById(xmlGroups, xmlViewCol.getId());
                ColumnGroup columnGroup = makeGroup(xmlColumns, xmlGroups, xmlGroup);
                header.addColumnGroup(columnGroup);
            }
        }

        //加入多行表头
        //MultiLineHeaderRenderer mhr = new MultiLineHeaderRenderer();
        //Enumeration enum = table.getColumnModel().getColumns();
        //while (enum.hasMoreElements()) {
        //  ((TableColumn)enum.nextElement()).setHeaderRenderer(mhr);
        //}

        //加入滚动条
        scrollPane =
            new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //Xyz add 2004/08/11 分页显示 START
        scrollBarV = scrollPane.getVerticalScrollBar();
        scrollBarV.addAdjustmentListener(this);
        //Xyz add 2004/08/11 分页显示 END
        //设置冻结列
        //JViewport jvp = new JViewport();
        //jvp.setView(new FrozenColumnHeader(table,2));
        //scrollPane.setRowHeader(jvp);
        //设置自动缩放方式
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //设置行、列、单元格选择
        table.setRowSelectionAllowed(true);
//        table.setColumnSelectionAllowed(false);
//        table.setCellSelectionEnabled(false);
        //设置单元选择类型
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //加入
        this.add(scrollPane, BorderLayout.CENTER);
        //初始化打印纸张
        pageFormat = new PageFormat();
    }

    private TableColumn makeColumn(XmlColumn xmlColumn) {

        TableColumn column = table.getColumn(xmlColumn.getId());

        Font font = xmlColumn.getFont();
        String caption = xmlColumn.getCaption();
        String width = xmlColumn.getWidth();
//        String viewctrl = xmlColumn.getViewctrl();
//        String editctrl = xmlColumn.getEditctrl();
//        String lock = xmlColumn.getLock();
//        String change = xmlColumn.getChange();
        String format = xmlColumn.getFormat();
        String align = xmlColumn.getAlign();
        String datatype = xmlColumn.getDatatype();

        ColumnRenderer cr = new ColumnRenderer();

        cr.setAlign(align);
        cr.setDatatype(datatype);
        cr.setFormat(format);
        cr.init();
        cr.setFont(font);
        cr.repaint();
        column.setCellRenderer(cr);

        column.setHeaderValue(caption);

        column.setPreferredWidth(Integer.parseInt(width));
        column.setWidth(Integer.parseInt(width));

        return column;
    }
    private ColumnGroup makeGroup(
        Vector xmlColumns,
        Vector xmlGroups,
        XmlGroup xmlGroup) {

        TableColumnModel cm = table.getColumnModel();

        ColumnGroup group_name = new ColumnGroup(xmlGroup.getCaption());
        Vector xmlItems = xmlGroup.getItems();
        for (int i = 0; i < xmlItems.size(); i++) {
            XmlItem xmlItem = (XmlItem) xmlItems.elementAt(i);
            if (xmlItem.getType().equals("col")) {
                XmlColumn xmlTemp =
                    (XmlColumn) TableManager.getElementById(xmlColumns, xmlItem.getId());
                TableColumn tableColumn = makeColumn(xmlTemp);
                group_name.add(tableColumn);
            }
            if (xmlItem.getType().equals("group")) {
                XmlGroup xmlTemp =
                    (XmlGroup) TableManager.getElementById(xmlGroups, xmlItem.getId());
                ColumnGroup columnGroup = makeGroup(xmlColumns, xmlGroups, xmlTemp);
                group_name.add(columnGroup);
            }
        }
        return group_name;

    }
    //事件
    public void onPageSetup() {
//        PageSetupThread t = new PageSetupThread();
//        t.serPageFormat(pageFormat);
//        pageFormat = t.getPageFormat();
//        t.start();
//    PrinterJob pj = PrinterJob.getPrinterJob();
//    pageFormat = pj.pageDialog(pageFormat);
//      xr.PageSetup();

    }
    public void onPrint() {
//        PrintThread t = new PrintThread();
//        t.setTopPanel(topPanel);
//        t.setTable(table);
//        t.setPageFormat(pageFormat);
//        t.setTableManager(tableManager);
//        t.start();
//      xr.Print();
    }
    public void onPrintPreview() {
//        PrintPreviewThread t = new PrintPreviewThread();
//        t.setTopPanel(topPanel);
//        t.setTable(table);
//        t.setPageFormat(pageFormat);
//        t.setTableManager(tableManager);
//        t.start();
//      xr.Preview();
    }
    public void openData(InputStream isTable, InputStream isData) {
        init(isTable, isData);
        makeTitle();
        makeTable();
    }
    public void openData(String sTable, String sData) {
        init(sTable, sData);
        makeTitle();
        makeTable();
    }
    private Vector getLeafItems(XmlGroup xmlGroup, Vector xmlGroups) {
        Vector items = xmlGroup.getItems();
        Vector leafItems = new Vector();
        for (int i = 0; i < items.size(); i++) {
            XmlItem item = (XmlItem) items.elementAt(i);
            if (item.getType().equals("col")) {
                leafItems.addElement(item);
            }
            if (item.getType().equals("group")) {
                XmlGroup ggg = (XmlGroup) TableManager.getElementById(xmlGroups, item.getId());
                Vector vvv = getLeafItems(ggg, xmlGroups);
                for (int j = 0; j < vvv.size(); j++) {
                    XmlItem iii = (XmlItem) vvv.elementAt(j);
                    leafItems.addElement(iii);
                }
            }
        }
        return leafItems;
    }
    public void init(InputStream isTable, InputStream isData) {
        tableManager = new TableManager(isTable);
        dataManager = new TableDataManager(isData);
    }
    public void init(String sTable, String sData) {
        tableManager = new TableManager(sTable);
        dataManager = new TableDataManager(sData);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame(res.getString("String_27"));
        java
            .awt
            .GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getAvailableFontFamilyNames();
//        frame.addWindowListener(new java.awt.event.WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

        MultiLevelHeaderTable aTest = new MultiLevelHeaderTable();
        aTest.openData("D:/SkyLine/Table.xml", "D:/SkyLine/TableData.xml");

        frame.getContentPane().add("Center", aTest);
        frame.setSize(aTest.getSize());

        frame.pack();
        frame.setVisible(true);

    }
}
