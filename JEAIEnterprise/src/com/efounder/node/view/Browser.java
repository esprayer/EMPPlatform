package com.efounder.node.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.jdesktop.jdic.browser.WebBrowser;
import org.jdesktop.jdic.browser.WebBrowserEvent;
import org.jdesktop.jdic.browser.WebBrowserListener;

/**
 * JDIC API demo main class.
 * <p>
 * <code>Browser</code> is a GUI application demonstrating the usage of the JDIC API package
 * <code>org.jdesktop.jdic.browser</code> (Browser component).
 */

public class Browser
    extends JPanel {
  public static ImageIcon browseIcon = new ImageIcon(
      Browser.class.getResource("images/Right.gif"));

  BorderLayout borderLayout1 = new BorderLayout();

  JToolBar jBrowserToolBar = new JToolBar();
  JButton jStopButton = new JButton("Stopp",
                                    new ImageIcon(getClass().getResource(
      "images/Stop.png")));

  JButton jRefreshButton = new JButton("Refresh",
                                       new ImageIcon(getClass().getResource(
      "images/Reload.png")));
  JButton jForwardButton = new JButton("Forward",
                                       new ImageIcon(getClass().getResource(
      "images/Forward.png")));
  JButton jBackButton = new JButton("Back",
                                    new ImageIcon(getClass().getResource(
      "images/Back.png")));

  JPanel jAddressPanel = new JPanel();
  JLabel jAddressLabel = new JLabel();
  JTextField jAddressTextField = new JTextField();
  JButton jGoButton = new JButton();
  JPanel jAddrToolBarPanel = new JPanel();
  MyStatusBar statusBar = new MyStatusBar();
  JPanel jBrowserPanel = new JPanel();

  WebBrowser webBrowser;

  public Browser(java.net.URL httpURL) {
    try {
      jbInit(httpURL);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {}

    JFrame frame = new JFrame("JDIC API Demo - Browser");

    Container contentPane = frame.getContentPane();

    contentPane.setLayout(new GridLayout(1, 1));
    try {
      contentPane.add(new Browser(new URL("http://java.net")));
    }
    catch (MalformedURLException ex) {
    }

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    frame.pack();
    frame.setVisible(true);
  }

  private void jbInit(java.net.URL httpURL) throws Exception {
    this.setLayout(borderLayout1);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    this.setPreferredSize(new Dimension(screenSize.width * 9 / 10,
                                        screenSize.height * 8 / 10));

    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

    jAddressLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
    jAddressLabel.setToolTipText("");
    jAddressLabel.setText(" URL: ");

    jGoButton.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0,
        2, 0, 2),
        new EtchedBorder()));
    jGoButton.setMaximumSize(new Dimension(60, 25));
    jGoButton.setMinimumSize(new Dimension(60, 25));
    jGoButton.setPreferredSize(new Dimension(60, 25));
    jGoButton.setToolTipText("Load the given URL");
    jGoButton.setIcon(browseIcon);
    jGoButton.setText("GO");
    jGoButton.addActionListener(new Browser_jGoButton_actionAdapter(this));
    jAddressPanel.setLayout(new BorderLayout());

    jAddressTextField.addActionListener(new
                                        Browser_jAddressTextField_actionAdapter(this));
    jBackButton.setToolTipText("Go back one page");
    jBackButton.setHorizontalTextPosition(SwingConstants.TRAILING);
    jBackButton.setEnabled(false);
    jBackButton.setMaximumSize(new Dimension(75, 27));
    jBackButton.setPreferredSize(new Dimension(75, 27));
    jBackButton.addActionListener(new Browser_jBackButton_actionAdapter(this));
    jForwardButton.setToolTipText("Go forward one page");
    jForwardButton.setEnabled(false);
    jForwardButton.addActionListener(new Browser_jForwardButton_actionAdapter(this));
    jRefreshButton.setToolTipText("Reload current page");
    jRefreshButton.setEnabled(true);
    jRefreshButton.setMaximumSize(new Dimension(75, 27));
    jRefreshButton.setMinimumSize(new Dimension(75, 27));
    jRefreshButton.setPreferredSize(new Dimension(75, 27));
    jRefreshButton.addActionListener(new Browser_jRefreshButton_actionAdapter(this));
    jStopButton.setToolTipText("Stop loading this page");
    jStopButton.setVerifyInputWhenFocusTarget(true);
    jStopButton.setText("Stop");
    jStopButton.setEnabled(true);
    jStopButton.setMaximumSize(new Dimension(75, 27));
    jStopButton.setMinimumSize(new Dimension(75, 27));
    jStopButton.setPreferredSize(new Dimension(75, 27));
    jStopButton.addActionListener(new Browser_jStopButton_actionAdapter(this));
    jAddressPanel.add(jAddressLabel, BorderLayout.WEST);
    jAddressPanel.add(jAddressTextField, BorderLayout.CENTER);
    jAddressPanel.add(jGoButton, BorderLayout.EAST);
    jAddressPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEtchedBorder(),
        BorderFactory.createEmptyBorder(2, 0, 2, 0)));

    jBrowserToolBar.setFloatable(false);
    jBrowserToolBar.add(jBackButton, null);
    jBrowserToolBar.add(jForwardButton, null);
    jBrowserToolBar.addSeparator();
    jBrowserToolBar.add(jRefreshButton, null);
    jBrowserToolBar.add(jStopButton, null);
    jBrowserToolBar.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEtchedBorder(),
        BorderFactory.createEmptyBorder(2, 2, 2, 0)));

    jAddrToolBarPanel.setLayout(new BorderLayout());
    jAddrToolBarPanel.add(jAddressPanel, BorderLayout.CENTER);
    jAddrToolBarPanel.add(jBrowserToolBar, BorderLayout.WEST);
    jAddrToolBarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

    statusBar.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    statusBar.lblDesc.setText("JDIC API Demo - Browser");

    try {
      webBrowser = new WebBrowser(httpURL); //new URL("http://java.net")
      // Print out debug messages in the command line.
      //webBrowser.setDebug(true);
//            webBrowser.dispose();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

    webBrowser.addWebBrowserListener(new WebBrowserListener() {
      public void initializationCompleted(WebBrowserEvent webBrowserEvent) {
      }

      public void downloadStarted(WebBrowserEvent event) {
        updateStatusInfo("Loading started.");
      }

      public void downloadCompleted(WebBrowserEvent event) {
        jBackButton.setEnabled(webBrowser.isBackEnabled());
        jForwardButton.setEnabled(webBrowser.isForwardEnabled());

        updateStatusInfo("Loading completed.");

        URL currentUrl = webBrowser.getURL();

        if (currentUrl != null) {
          jAddressTextField.setText(currentUrl.toString());
        }
      }

      public void downloadProgress(WebBrowserEvent event) {
        // updateStatusInfo("Loading in progress...");
      }

      public void downloadError(WebBrowserEvent event) {
        updateStatusInfo("Loading error.");
      }

      public void documentCompleted(WebBrowserEvent event) {
        updateStatusInfo("Document loading completed.");
      }

      public void titleChange(WebBrowserEvent event) {
        updateStatusInfo("Title of the browser window changed.");
      }

      public void statusTextChange(WebBrowserEvent event) {
        // updateStatusInfo("Status text changed.");
      }

      public void windowClose(WebBrowserEvent event) {
        updateStatusInfo("Closed by script.");
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
            webBrowser,
            "The webpage you are viewing is trying to close the window.\n Do you want to close this window?",
            "Warning",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE)) {
          System.exit(0);
        }
      }
    });

    jBrowserPanel.setLayout(new BorderLayout());
    jBrowserPanel.add(webBrowser, BorderLayout.CENTER);

    this.add(jAddrToolBarPanel, BorderLayout.NORTH);
    this.add(statusBar, BorderLayout.SOUTH);
    this.add(jBrowserPanel, BorderLayout.CENTER);
  }

  /**
   *
   */
  public void dispose() {
    if (this.webBrowser != null)
      this.webBrowser.dispose();
  }

  void updateStatusInfo(String statusMessage) {
    statusBar.lblStatus.setText(statusMessage);
  }

  /**
   * Check the current input URL string in the address text field, load it,
   * and update the status info and toolbar info.
   */
  void loadURL() {
    String inputValue = jAddressTextField.getText();

    if (inputValue == null) {
      JOptionPane.showMessageDialog(this, "The given URL is NULL:",
                                    "Warning", JOptionPane.WARNING_MESSAGE);
    }
    else {
      // Check if the text value is a URL string.
      URL curUrl = null;

      try {
        // Check if the input string is a local path by checking if it starts
        // with a driver name(on Windows) or root path(on Unix).
        File[] roots = File.listRoots();

        for (int i = 0; i < roots.length; i++) {
          if (inputValue.toLowerCase().startsWith(roots[i].toString().
                                                  toLowerCase())) {
            File curLocalFile = new File(inputValue);

            curUrl = curLocalFile.toURL();
            break;
          }
        }

        if (curUrl == null) {
          // Check if the text value is a valid URL.
          try {
            curUrl = new URL(inputValue);
          }
          catch (MalformedURLException e) {
            if (inputValue.toLowerCase().startsWith("ftp.")) {
              curUrl = new URL("ftp://" + inputValue);
            }
            else if (inputValue.toLowerCase().startsWith("gopher.")) {
              curUrl = new URL("gopher://" + inputValue);
            }
            else {
              curUrl = new URL("http://" + inputValue);
            }
          }
        }

        webBrowser.setURL(curUrl);

        // Update the address text field, statusbar, and toolbar info.
        updateStatusInfo("Loading " + curUrl.toString() + " ......");

      }
      catch (MalformedURLException mue) {
        JOptionPane.showMessageDialog(this,
                                      "The given URL is not valid:" +
                                      inputValue, "Warning",
                                      JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  void jGoButton_actionPerformed(ActionEvent e) {
    loadURL();
  }

  void jAddressTextField_actionPerformed(ActionEvent e) {
    loadURL();
  }

  void jBackButton_actionPerformed(ActionEvent e) {
    webBrowser.back();
  }

  void jForwardButton_actionPerformed(ActionEvent e) {
    webBrowser.forward();
  }

  void jRefreshButton_actionPerformed(ActionEvent e) {
    webBrowser.refresh();
  }

  void jStopButton_actionPerformed(ActionEvent e) {
    webBrowser.stop();
  }
}

class Browser_jAddressTextField_actionAdapter
    implements java.awt.event.ActionListener {
  Browser adaptee;

  Browser_jAddressTextField_actionAdapter(Browser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jAddressTextField_actionPerformed(e);
  }
}

class Browser_jBackButton_actionAdapter
    implements java.awt.event.ActionListener {
  Browser adaptee;

  Browser_jBackButton_actionAdapter(Browser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBackButton_actionPerformed(e);
  }
}

class Browser_jForwardButton_actionAdapter
    implements java.awt.event.ActionListener {
  Browser adaptee;

  Browser_jForwardButton_actionAdapter(Browser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jForwardButton_actionPerformed(e);
  }
}

class Browser_jRefreshButton_actionAdapter
    implements java.awt.event.ActionListener {
  Browser adaptee;

  Browser_jRefreshButton_actionAdapter(Browser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jRefreshButton_actionPerformed(e);
  }
}

class Browser_jStopButton_actionAdapter
    implements java.awt.event.ActionListener {
  Browser adaptee;

  Browser_jStopButton_actionAdapter(Browser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jStopButton_actionPerformed(e);
  }
}

class Browser_jGoButton_actionAdapter
    implements java.awt.event.ActionListener {
  Browser adaptee;

  Browser_jGoButton_actionAdapter(Browser adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jGoButton_actionPerformed(e);
  }
}
