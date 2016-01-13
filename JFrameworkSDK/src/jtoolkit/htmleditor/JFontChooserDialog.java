package jtoolkit.htmleditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.borland.jbcl.layout.*;

/**
 *
 * @version 1.0
 */
public class JFontChooserDialog
    extends JDialog implements ActionListener {

    public static void main(String[] args) {
        JFontChooserDialog dlg = new JFontChooserDialog(new Point(100,100));
        dlg.setSize(320, 180);
        dlg.setVisible(true);
    }

    public int OK_OPTION = 0;
    public int CANCEL_OPTION = 1;
    public int OPTION;

    private JList fontList, sizeList;
    private JTextArea txtSample;
    private JButton okButton, cancelButton;

    private String[] sizes = new String[] {
        "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "30", "36", "48", "72"};

    public JFontChooserDialog(Point point) {
        fontList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().
                             getAvailableFontFamilyNames()) {

            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(120, 80);
            }
        };
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sizeList = new JList(sizes) {
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(30, 80);
            }
        };
        sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txtSample = new JTextArea() {
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(300, 40);
            }

            public void paint(Graphics g) {
                ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                super.paint(g);
            }
        };
        txtSample.setText("i love you,中国!");

        setFont(null);

        ListSelectionListener listListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                txtSample.setFont(getCurrentFont());
            }
        };
        fontList.addListSelectionListener(listListener);
        sizeList.addListSelectionListener(listListener);

        getContentPane().setLayout(new java.awt.BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new java.awt.BorderLayout());

        leftPanel.add(new JScrollPane(fontList), java.awt.BorderLayout.CENTER);
        leftPanel.add(new JScrollPane(sizeList), java.awt.BorderLayout.EAST);

        getContentPane().add(leftPanel, java.awt.BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new java.awt.BorderLayout());

        JPanel rightPanelSub2 = new JPanel();
        rightPanelSub2.setLayout(new VerticalFlowLayout(0));
        okButton = new JButton("OK");
        okButton.setMnemonic('O');
        okButton.setPreferredSize(new Dimension(75, 20));
        okButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic('C');
        cancelButton.setPreferredSize(new Dimension(75, 20));
        cancelButton.addActionListener(this);

        rightPanelSub2.add(okButton);
        rightPanelSub2.add(cancelButton);

        rightPanel.add(rightPanelSub2, java.awt.BorderLayout.NORTH);

        getContentPane().add(rightPanel, java.awt.BorderLayout.EAST);
        leftPanel.add(new JScrollPane(txtSample), java.awt.BorderLayout.SOUTH);
        setSize(320, 200);
        setResizable(false);
        setModal(true);
        setLocation(point.x, point.y - getHeight());
        setTitle("选择字体字号");
    }

    public void setFont(Font font) {
        if (font == null)
            font = txtSample.getFont();
        fontList.setSelectedValue(font.getName(), true);
        fontList.ensureIndexIsVisible(fontList.getSelectedIndex());
        sizeList.setSelectedValue("" + font.getSize(), true);
        sizeList.ensureIndexIsVisible(sizeList.getSelectedIndex());
    }

    public Font getFont() {
        return getCurrentFont();
    }

    private Font getCurrentFont() {
        String fontFamily = (String) fontList.getSelectedValue();
        int fontSize = Integer.parseInt( (String) sizeList.getSelectedValue());
        int fontType = Font.PLAIN;

        return new Font(fontFamily, fontType, fontSize);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            processOKOption();
        } else if (e.getSource() == cancelButton) {
            processCancelOption();
        }
    }

    private void processCancelOption() {
        OPTION = CANCEL_OPTION;
        dispose();
    }

    private void processOKOption() {
        OPTION = OK_OPTION;
        dispose();
    }
}
