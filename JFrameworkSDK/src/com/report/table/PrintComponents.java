package com.report.table;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import java.util.ResourceBundle;

public class PrintComponents extends JFrame {
  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");

  class MyPrintable implements Printable {

    public int print(Graphics g, PageFormat pf, int index) {
      if (index == 0) {
        //g.translate((int)(pf.getImageableX()),
            //(int)(pf.getImageableY()));
        Container pane = getContentPane();
        //pane.paint(g);
        Component[] comps = pane.getComponents();
        for (int i = 0; i < comps.length; i++) {
          g.translate(comps[i].getX(), comps[i].getY());
	  	  comps[i].paint(g);
          g.translate(0 - comps[i].getX(), 0 - comps[i].getY());
	    }
        return Printable.PAGE_EXISTS;
      }
      return Printable.NO_SUCH_PAGE;
    }

  }

  public PrintComponents() {
    super("Print Components");
    JLabel label;
    JTextField textField;

    Container pane = getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 10, 5, 10);

    gbc.gridy = 0;
    label = new JLabel("Last Name:", JLabel.LEFT);
    pane.add(label, gbc);
    textField = new JTextField(8);
    pane.add(textField, gbc);

    label = new JLabel("First Name:", JLabel.LEFT);
    pane.add(label, gbc);
    textField = new JTextField(8);
    pane.add(textField, gbc);

    gbc.gridy++;
    label = new JLabel("Address:", JLabel.LEFT);
    pane.add(label, gbc);
    textField = new JTextField(8);
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    pane.add(textField, gbc);

    gbc.gridy++;
    gbc.gridwidth = 1;
    label = new JLabel("Phone:", JLabel.LEFT);
    pane.add(label, gbc);
    textField = new JTextField(8);
    pane.add(textField, gbc);

    label = new JLabel("Postal Code:", JLabel.LEFT);
    pane.add(label, gbc);
    textField = new JTextField(8);
    pane.add(textField, gbc);

    gbc.gridy++;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    JButton btn = new JButton("Print");
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        performPrint();
      }
    });
    pane.add(btn, gbc);
  }
  public static void main(String[] args) {
    PrintComponents pc = new PrintComponents();
    pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//EXIT_ON_CLOSE
    pc.setSize(400, 300);
    pc.setVisible(true);
  }
  protected void performPrint() {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(new MyPrintable());
    try {
      job.print();
    } catch (PrinterException pe) {};
  }
}
