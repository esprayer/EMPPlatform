package jenterprise.bof.classes.AppExplorerObject;

import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.efounder.eai.data.JParamObject;

import jbof.gui.window.classes.style.mdi.*;
import jenterprise.bof.classes.AppExplorerObject.noticeService.*;
import jframework.foundation.classes.*;
import jenterprise.bof.classes.AppExplorerObject.JMessageShowWindow.JTableModel;
import jenterprise.bof.classes.AppExplorerObject.messlist.MessageItem;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JMessageShowWindow
    extends JBOFMDIChildWindow implements ActionListener, ItemListener {

    static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  private IFormSTUB mForm;
    private String messType;
    private Object[] dispalyItem = new Object[] {res.getString("String_307"), res.getString("String_308"), res.getString("String_309")};
    private Vector allData = new Vector();

    public JMessageShowWindow(String type) {
        this.messType = type;
        try {
            jbInit();
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init() {
        //显示项
        DefaultComboBoxModel cbModel = new DefaultComboBoxModel(dispalyItem);
        displayComboBox.setModel(cbModel);
        //取数据
        String date1 = getRecentPreviousDate(2);
        String date2 = getServerCurrentDate();
        prepareData(date1, date2);
        //设置JTable的显示风格
        setTableShow();
    }

    private void setTableShow() {
        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn column;
        column = mainTable.getColumn(mainTable.getColumnName(0));
        column.setPreferredWidth(80);
        column = mainTable.getColumn(mainTable.getColumnName(1));
        column.setPreferredWidth(80);
        column = mainTable.getColumn(mainTable.getColumnName(2));
        column.setPreferredWidth(100);
        column = mainTable.getColumn(mainTable.getColumnName(3));
        column.setPreferredWidth(100);
        column = mainTable.getColumn(mainTable.getColumnName(4));
        column.setPreferredWidth(300);
        column = mainTable.getColumn(mainTable.getColumnName(5));
        column.setPreferredWidth(200);
    }

    private JParamObject getServerParamObject() {
        JParamObject PO = JParamObject.Create();
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("SecurityObject", "getCurrentDateTime", PO);
        if (RO == null) {
            return null;
        }
        PO = new JParamObject(RO.ResponseObject.toString());
        return PO;
    }

    private String getServerCurrentDate() {
        JParamObject PO = getServerParamObject();
        return PO.GetValueByParamName("CurrentDate", "");
    }

    private String getServerCurrentTime() {
        JParamObject PO = getServerParamObject();
        return PO.GetValueByParamName("CurrentTime", "");
    }

    private String getRecentPreviousDate(int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -count);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }

    private String toDate(String date) {
        if (date != null && date.length() >= 8) {
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
        }
        return date;
    }

    private String toTime(String time) {
        if (time != null && time.length() >= 6) {
            time = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4);
        }
        return time;
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel northPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JTextField findTextField = new JTextField();
    JButton findButton = new JButton();
    JButton clearButton = new JButton();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JComboBox displayComboBox = new JComboBox();
    JLabel jLabel3 = new JLabel();
    JDateComboBox dateFromComboBox = new JDateComboBox();
    JLabel jLabel4 = new JLabel();
    JDateComboBox dateEndsComboBox = new JDateComboBox();
    JScrollPane centerScrollPane = new JScrollPane();
    JTable mainTable = new JTable();

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        northPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        findTextField.setPreferredSize(new Dimension(150, 22));
        findTextField.setText("");
        findButton.setPreferredSize(new Dimension(80, 22));
        findButton.setText(res.getString("String_328"));
        findButton.setMnemonic('F');
        findButton.addActionListener(this);
        clearButton.setPreferredSize(new Dimension(80, 22));
        clearButton.setText(res.getString("String_329"));
        clearButton.setMnemonic('C');
        clearButton.addActionListener(this);
        jLabel1.setText("|");
        jLabel2.setText(res.getString("String_331"));
        displayComboBox.setPreferredSize(new Dimension(100, 22));
        displayComboBox.addItemListener(this);
        jLabel3.setText(res.getString("String_332"));
        jLabel4.setText(res.getString("String_333"));
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        dateFromComboBox.addItemListener(this);
        dateEndsComboBox.addItemListener(this);
        dateFromComboBox.setVisible(false);
        dateEndsComboBox.setVisible(false);
        dateFromComboBox.setPreferredSize(new Dimension(100, 22));
        dateEndsComboBox.setPreferredSize(new Dimension(100, 22));
        northPanel.add(findTextField);
        northPanel.add(findButton);
        northPanel.add(clearButton);
        northPanel.add(jLabel1);
        northPanel.add(jLabel2);
        northPanel.add(displayComboBox);
        northPanel.add(jLabel3);
        northPanel.add(dateFromComboBox);
        northPanel.add(jLabel4);
        northPanel.add(dateEndsComboBox);
        this.add(northPanel, java.awt.BorderLayout.NORTH);
        this.add(centerScrollPane, java.awt.BorderLayout.CENTER);
        centerScrollPane.getViewport().add(mainTable);
        mainTable.addMouseListener(new JTableMouseListener());
    }

    public void actionPerformed(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == findButton) {
                processFindEvent();
            } else if (e.getSource() == clearButton) {
                processClearEvent();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void processFindEvent() {
        String text = findTextField.getText();
        Vector data = ((JTableModel)mainTable.getModel()).getItems();
        MessageItem item;
        for (int i = 0, n = data.size(); i < n; i++) {
            item = (MessageItem) data.get(i);
            if (item.F_MESSCAP.indexOf(text) >= 0) {
                mainTable.setRowSelectionInterval(i , i);
                break;
            }
        }
    }

    private void processClearEvent() {
        findTextField.setText("");
    }

    public void itemStateChanged(ItemEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == displayComboBox) {
                processDisplayEvent();
            } else if (e.getSource() == dateFromComboBox) {
                processDateEvent();
            } else if (e.getSource() == dateEndsComboBox) {
                processDateEvent();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void processDateEvent() {
        //如果其实月份不属于最近2个月的范围，则重新从数据库中去取
        if (dateFromComboBox.getSelectedItem() == null
            || dateEndsComboBox.getSelectedItem() == null) {
            return;
        }
        String dateFrom = dateFromComboBox.getSelectedItem().toString();
        String dateEnds = dateEndsComboBox.getSelectedItem().toString();
        String dateRTwo = getRecentPreviousDate(2);
        if (dateFrom.compareTo(dateRTwo) < 0) {
            dateFrom = StringFunction.replaceString(dateFrom, "-", "");
            dateEnds = StringFunction.replaceString(dateEnds, "-", "");
            prepareData(dateFrom, dateEnds);
        } else {
            reprepareData(dateFrom, dateEnds);
        }
    }

    private void prepareData(String dateFrom, String dateEnds) {
        Vector dataVector = getDataFromServer(dateFrom,dateEnds);
        HashMap itemMap;
        MessageItem item;
        allData.clear();
        for (int i = 0, n = dataVector.size(); i < n; i++) {
            itemMap = (HashMap) dataVector.get(i);
            item = new MessageItem();
            item.F_ID       = (String) itemMap.get("F_ID");
            item.F_TYPE     = (String) itemMap.get("F_TYPE");
            item.F_XTBH     = (String) itemMap.get("F_XTBH");
            item.F_SEND     = (String) itemMap.get("F_SEND");
            item.F_RECR     = (String) itemMap.get("F_RECR");
            item.F_DATE     = toDate((String) itemMap.get("F_DATE"));
            item.F_TIME     = toTime((String) itemMap.get("F_TIME"));
            item.F_MESSCAP  = (String) itemMap.get("F_MESSCAP");
            item.F_MESSLINK = (String) itemMap.get("F_MESSLINK");
            item.F_MESSOBJ  = (Object) itemMap.get("F_MESSOBJ");
            item.F_OBJECT   = (String) itemMap.get("F_OBJECT");
            item.F_METHOD   = (String) itemMap.get("F_METHOD");
            item.F_PARAM1   = (String) itemMap.get("F_PARAM1");
            item.F_PARAM2   = (String) itemMap.get("F_PARAM2");
            item.F_PARAM3   = (String) itemMap.get("F_PARAM3");
            allData.add(item);
            item = null;
        }
        mainTable.setModel(new JTableModel(allData));
        mainTable.updateUI();
    }

    private Vector getDataFromServer(String dateFrom, String dateEnds) {
        JParamObject PO = JParamObject.Create();
        //
        String url  = PO.GetValueByEnvName("STANDARD_SERVICE","");
        // 如果没有指定标准，没有标准服务地址则从本地取
        if (url.trim().length() > 0) {
            PO.SetValueByParamName("Server", JServiceManager.SV_STANDARD);
            PO.SetValueByParamName("CommBase", url); // 去标准服务器上取
        } else {
            PO.SetValueByParamName("Server", "");
            PO.SetValueByParamName("CommBase", "");
        }
        PO.SetValueByParamName("FromDate", dateFrom);
        PO.SetValueByParamName("EndsDate", dateEnds);
        PO.SetValueByParamName("MessType", messType);

        JResponseObject RO;
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject", "getMessageByDate", PO, null, null, null);
        Vector dataMap = (Vector) RO.ResponseObject;
        if (dataMap == null) {
            dataMap = new Vector();
        }

        return dataMap;
    }

    private void processDisplayEvent() {
        String date1, date2;
        switch (displayComboBox.getSelectedIndex()) {
            case 0:
                date1 = "0000-00-00";
                date2 = "9999-12-31";
                break;
            case 1:
                date1 = toDate(getRecentPreviousDate(1));
                date2 = toDate(getServerCurrentDate());
                break;
            case 2:
                date1 = (String) dateFromComboBox.getSelectedItem();
                date2 = (String) dateEndsComboBox.getSelectedItem();
                break;
            default:
                date1 = "0000-00-00";
                date2 = "9999-12-31";
                break;
        }
        reprepareData(date1,date2);
        setVisible(displayComboBox.getSelectedIndex());
        setTableShow();
    }

    private void reprepareData(String date1, String date2) {
        Vector newData = new Vector();
        MessageItem item;
        for (Iterator it = allData.iterator(); it.hasNext(); ) {
            item = (MessageItem) it.next();
            if (item.F_DATE.compareTo(date1) >= 0
                && item.F_DATE.compareTo(date2) <= 0) {
                newData.add(item);
            }
        }
        mainTable.setModel(new JTableModel(newData));
        mainTable.updateUI();
    }

    private void setVisible(int index) {
        jLabel3.setVisible(index == dispalyItem.length - 1);
        jLabel4.setVisible(index == dispalyItem.length - 1);
        dateFromComboBox.setVisible(index == dispalyItem.length - 1);
        dateEndsComboBox.setVisible(index == dispalyItem.length - 1);
    }

    public class JTableModel
        extends DefaultTableModel {

        Vector items;
        String[] colNames = new String[] {res.getString("String_310"), res.getString("String_311"), res.getString("String_312"), res.getString("String_313"), res.getString("String_314"),res.getString("String_315")};

        public JTableModel(Vector _items) {
            items = new Vector(_items.size());
            for (int i = 0; i < _items.size(); i++) {
                items.addElement(createEntry(_items.elementAt(i)));
            }
        }

        public JTableModel(Object[] _items) {
            items = new Vector(_items.length);
            for (int i = 0; i < _items.length; i++) {
                items.addElement(createEntry(_items[i]));
            }
        }

        private MessageItem createEntry(Object obj) {
            if (obj instanceof MessageItem)
                return (MessageItem) obj;
            else
                return new MessageItem();
        }

        public int getRowCount() {
            if (items == null)
                return 0;
            return items.size();
        }

        public Vector getItems() {
            return items;
        }

        public int getColumnCount() {
            return colNames.length;
        }

        public Object getColumnObject(int col) {
            if (col == 1) {
                return JNoticeParamObject.TYPE_ID;
            }
            return null;
        }

        public String getColumnName(int col) {
            return colNames[col];
        }

        public Object getValueAt(int row, int col) {
            MessageItem entry = (MessageItem) items.elementAt(row);
            switch (col) {
                case 0:
                    return entry.F_ID;
                case 1:
                    return entry.F_TYPE;
                case 2:
                    return entry.F_DATE;
                case 3:
                    return entry.F_TIME;
                case 4:
                    return entry.F_MESSCAP;
                case 5:
                    return entry.F_MESSLINK;
                default:
                    throw new InternalError();
            }
        }

        public Class getColumnClass(int col) {
            switch (col) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                case 3:
                    return String.class;
                case 4:
                    return String.class;
                case 5:
                    return String.class;
                default:
                    throw new InternalError();
            }
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            MessageItem entry = (MessageItem) items.elementAt(row);
            switch (col) {
                case 0:
                    entry.F_TYPE = (String) value;
                    break;
                case 1:
                    entry.F_TYPE = (String) value;
                    break;
                case 2:
                    entry.F_DATE = (String) value;
                    break;
                case 3:
                    entry.F_TIME = (String) value;
                    break;
                case 4:
                    entry.F_MESSCAP = (String) value;
                    break;
                case 5:
                    entry.F_MESSLINK = (String) value;
                    break;
            }
        }
    }

    class JTableMouseListener
        implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == e.BUTTON1 && e.getClickCount() == 2) {
                int index = mainTable.getSelectedRow();
                Vector data = ( (JTableModel) mainTable.getModel()).getItems();
                MessageItem item = (MessageItem) data.get(index);
                if (item.isHelpable()) {
                    JMessageShowDetailWindow showDetail;
                    showDetail = new JMessageShowDetailWindow(item.F_MESSCAP,item.F_MESSOBJ, item.F_ID, item.F_MESSLINK);
                    JActiveDComDM.MainApplication.OpenObjectWindow(res.getString("String_367"), null, res.getString("String_368"), showDetail);
                }
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

}
