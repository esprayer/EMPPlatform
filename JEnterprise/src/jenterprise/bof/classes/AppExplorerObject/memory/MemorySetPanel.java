package jenterprise.bof.classes.AppExplorerObject.memory;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import javax.swing.border.TitledBorder;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class MemorySetPanel
    extends JPanel {
    public MemorySetPanel() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(verticalFlowLayout1);
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.MIDDLE);
        verticalFlowLayout1.setVgap(0);
        setPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jLabel1.setText("采样时间间隔：");
        timeInterval.setPreferredSize(new Dimension(60, 20));
        timeInterval.setToolTipText("单位毫秒");
        jLabel2.setText("ms");
        setPanel1.setBorder(null);
        setPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        this.add(setPanel1);
        this.add(setPanel2);
        setPanel1.add(jLabel1);
        setPanel1.add(timeInterval);
        setPanel1.add(jLabel2);
    }

    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel setPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel1 = new JLabel();
    JSpinner timeInterval = new JSpinner();
    JLabel jLabel2 = new JLabel();
    TitledBorder titledBorder1 = new TitledBorder("");
    JPanel setPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
}
