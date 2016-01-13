package jenterprise.bof.classes.AppExplorerObject.messlist;

import javax.swing.*;

import com.borland.jbcl.layout.*;

public class JIconListDemo
    extends JDialog {
    public JIconListDemo() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JIconListDemo demo = new JIconListDemo();
        demo.setSize(400, 400);
        demo.setVisible(true);
    }

    private void jbInit() throws Exception {
        item1.F_MESSCAP = s1;
        item1.F_METHOD = "openCard";
        item2.F_MESSCAP = s2;
        item3.F_MESSCAP = s3;
        item4.F_MESSCAP = s4;
        jList1.setListData(new Object[] {item1, item2, item3, item4});
        verticalFlowLayout1.setVgap(0);
        this.getContentPane().setLayout(verticalFlowLayout1);
        this.getContentPane().add(jList1.getParentPanel());
    }
    String s1 = "<html>类别为<font color = #3300CC>往来单位信息卡</font><br>编号为<font color = #3300CC>80012002</font>名称为<font color = #3300CC>名称一</font>的标准编码已于<font color = #3300CC>2007-7-28 10:35:02</font>被<font color = #3300CC>耿正</font>申请<font color = #3300CC>停用</font></html>";
    String s2 = "<html>类别为<font color = #3300CC>往来单位信息卡</font>编号为<font color = #3300CC>80012002</font>名称为<font color = #3300CC>名称一</font>的标准编码已于<font color = #3300CC>2007-7-28 10:35:02</font>被<font color = #3300CC>耿正</font>申请<font color = #3300CC>停用</font></html>";
    String s3 = "<html>类别为<font color = #3300CC>往来单位信息卡</font>编号为<font color = #3300CC>80012002</font>名称为<font color = #3300CC>名称一</font>的标准编码已于<font color = #3300CC>2007-7-28 10:35:02</font>被<font color = #3300CC>耿正</font>申请<font color = #3300CC>停用</font></html>";
    String s4 = "<html>类别为<font color = #3300CC><u>往来单位信息卡</u></font>编号为<font color = #3300CC>80012002</font>名称为<font color = #3300CC>名称一</font>的标准编码已于<font color = #3300CC>2007-7-28 10:35:02</font>被<font color = #3300CC>耿正</font>申请<font color = #3300CC>停用</font></html>";
    MessageItem item1 = new MessageItem("default.png");
    MessageItem item2 = new MessageItem("default.png");
    MessageItem item3 = new MessageItem("default.png");
    MessageItem item4 = new MessageItem("default.png");
    JIconList jList1 = new JIconList("InfomationOne","type1");
    JIconList jList2 = new JIconList("InfomationTwo");
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
}
