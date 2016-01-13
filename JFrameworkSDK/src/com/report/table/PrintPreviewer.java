package com.report.table;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ResourceBundle;

public class PrintPreviewer extends JPanel implements ActionListener{

  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  protected Pageable pageable;
  protected PrintComponent printComponent;
  protected int pageIndex;

  protected JScrollPane scrollPane;
  protected JButton previousButton;
  protected JButton nextButton;
  protected JButton sizeButton;
  protected JTextField scaleText;

  public PrintPreviewer(Pageable p, int page) {
    super(new BorderLayout());
    pageable = p;
    pageIndex = page;
    printComponent = new PrintComponent(null, null);
    printComponent.setBorder(BorderFactory.createBevelBorder( BevelBorder.RAISED));
    buildLayout();
    displayPage(pageIndex);
  }

  /**
   * Adds the appropriate components to this panel
   */
  protected void buildLayout() {
    setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    panel.add(printComponent);
    scrollPane = new JScrollPane(panel);
    add(scrollPane, BorderLayout.CENTER);
    add(getBottomPanel(), BorderLayout.SOUTH);
    addListeners();
  }

  /**
   * Returns a panel that contains the buttons supported by this interface
   */
  protected JPanel getBottomPanel() {
    JPanel outer = new JPanel();
    outer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
    JPanel inner = new JPanel();
    inner.setLayout(new GridLayout(1, 2, 10, 0));
    previousButton = new JButton(res.getString("String_49"));
    inner.add(previousButton);
    nextButton = new JButton(res.getString("String_50"));
    inner.add(nextButton);
    outer.add(inner);
    scaleText = new JTextField(3);
    outer.add(scaleText);
    sizeButton = new JButton(res.getString("String_51"));
    outer.add(sizeButton);
    return outer;
  }

  /**
   * Adds listeners to the buttons and the text field
   */
  protected void addListeners() {
    previousButton.addActionListener(this);
    nextButton.addActionListener(this);
    sizeButton.addActionListener(this);
    scaleText.addActionListener(this);
  }
  public void actionPerformed(ActionEvent event) {
       if(event.getSource().equals(previousButton)){
          displayPage(pageIndex - 1);
       }else if(event.getSource().equals(nextButton)){
          displayPage(pageIndex + 1);
       }else if(event.getSource().equals(sizeButton)){
          sizeToFit();
       }else if(event.getSource().equals(scaleText)){
          try {
            int scale = Integer.parseInt(
              scaleText.getText());
            printComponent.setScaleFactor(scale);
          } catch (NumberFormatException nfe) {};
       }
  }


  /**
   * Displays the specified page within the panel
   */
  protected void displayPage(int index) {
    pageIndex = index;
    printComponent.setPrintable(pageable.getPrintable(pageIndex));
    printComponent.setPageFormat(pageable.getPageFormat(pageIndex));
    printComponent.setDisplayPage(index);
    previousButton.setEnabled(pageIndex > 0);
    nextButton.setEnabled(pageIndex <
        (pageable.getNumberOfPages() - 1));
    repaint();
  }

  /**
   * Determine the largest scale factor that can be used that will
   * allow the current page to be displayed completely. In other words,
   * make the page as large as possible without making it necessary for
   * the user to use scroll bars to view the entire page. This is
   * accomplished by calculating the ratios that represent the actual
   * width of the page relative to the available width, and the actual
   * height of the page to the available height. The smaller of the two
   * values will dictate how large the ratio can be set while still
   * allowing the entire page to be displayed.
   */
  protected void sizeToFit() {
    int newScaleFactor;
    Dimension compSize = printComponent.getSizeWithScale(100d);
    Dimension viewSize = scrollPane.getSize();

    int scaleX = (viewSize.width - 25) * 100 / compSize.width;
    int scaleY = (viewSize.height - 25) * 100 / compSize.height;
    newScaleFactor = Math.min(scaleX, scaleY);

    printComponent.setScaleFactor(newScaleFactor);
    scaleText.setText(Integer.toString(newScaleFactor));
    repaint();
  }

}
