package jtoolkit.htmleditor;

import java.awt.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;

public class HyperLinkPanel extends JPanel{
    public HyperLinkPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HyperLinkPanel untitled2 = new HyperLinkPanel();
        untitled2.setSize(350,300);
        untitled2.setVisible(true);
    }

    private void jbInit() throws Exception {
        this.setLayout(mainLayout);
        mainPanel.setLayout(centerLayout);
        cmdPanel.setLayout(cmdLayout);
        bottomPanel.setLayout(bottomLayout);
        bottomLayout.setAlignment(FlowLayout.RIGHT);
        cancelButton.setPreferredSize(new Dimension(70, 22));
        cancelButton.setMnemonic('C');
        cancelButton.setText("Cancel");
        okButton.setPreferredSize(new Dimension(71, 22));
        okButton.setMnemonic('O');
        okButton.setText("OK");
        urlLabel.setPreferredSize(new Dimension(25, 16));
        urlLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        urlLabel.setText("URL:");
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        urlTextField.setPreferredSize(new Dimension(260, 22));
        urlTextField.setText("http://");
        textTextField.setPreferredSize(new Dimension(260, 22));
        textTextField.setText("");
        textLabel.setText("文本:");
        jPanel1.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        this.add(mainPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(jPanel3);
        jPanel3.add(urlLabel);
        jPanel3.add(urlTextField);
        mainPanel.add(jPanel2);
        jPanel2.add(textLabel);
        jPanel2.add(textTextField);
        mainPanel.add(jPanel1);
        this.add(cmdPanel, java.awt.BorderLayout.EAST);
        cmdPanel.add(okButton);
        cmdPanel.add(cancelButton);
        this.add(bottomPanel, java.awt.BorderLayout.SOUTH);
    }

    BorderLayout mainLayout = new BorderLayout();
    JPanel mainPanel = new JPanel();
    VerticalFlowLayout centerLayout = new VerticalFlowLayout();
    JPanel cmdPanel = new JPanel();
    VerticalFlowLayout cmdLayout = new VerticalFlowLayout();
    JPanel bottomPanel = new JPanel();
    FlowLayout bottomLayout = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    JLabel urlLabel = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JTextField urlTextField = new JTextField();
    JTextField textTextField = new JTextField();
    JLabel textLabel = new JLabel();
    FlowLayout flowLayout3 = new FlowLayout();
}
