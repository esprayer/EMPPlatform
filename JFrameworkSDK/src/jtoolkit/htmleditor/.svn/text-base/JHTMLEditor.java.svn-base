package jtoolkit.htmleditor;

import java.awt.*;
import javax.swing.*;
import javax.swing.Action;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;
import javax.swing.text.*;
import java.net.URL;
import com.borland.jbcl.layout.VerticalFlowLayout;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction;

/**
 * @version 1.0 ${gengeng}
 */
public class JHTMLEditor
    extends JPanel {

    private UndoableEditListener undoHandler = new UndoHandler();
    public JTextPane textPane = new JTextPane();
    public JButton okButton = new JButton();
    public JButton cancelButton = new JButton();

    private Action cutAction = new CutAction();
    private Action copyAction = new CopyAction();
    private Action pasteAction = new PasteAction();
    private Action fontAction = new FontSelectAction();
    private Action boldAction = new BoldAction();
    private Action italicAction = new ItalicAction();
    private Action underlineAction = new UnderlineAction();
    private Action leftAction = new AlignmentAction("left", StyleConstants.ALIGN_LEFT);
    private Action centerAction = new AlignmentAction("center", StyleConstants.ALIGN_CENTER);
    private Action rightAction = new AlignmentAction("right", StyleConstants.ALIGN_RIGHT);
//    private Action numberAction = new InsertHTMLTextAction("Bullets", "", HTML.Tag.UL, HTML.Tag.LI);
    private Action bulletAction = new InsertHTMLTextAction("hyperlink", "<a href> </li>", HTML.Tag.UL, HTML.Tag.A);

    private Action hyperlinkAction = new HyperLinkAction();
    private Action textcolorAction = new ColorAction(1);
    private Action backcolorAction = new ColorAction(2);
    private Action htmlAction = new ShowHTMLAction();
    public  Action okAction = new OKAction();
    public  Action cancelAction = new CancelAction();
    private UndoAction undoAction = new UndoAction();
    private RedoAction redoAction = new RedoAction();
    private UndoManager undoManager = new UndoManager();

    public JHTMLEditor() {
        //设置整体
        setLayout(new BorderLayout());
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        //创建工具栏
        JPanel toolBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JToolBar toolBar = new JToolBar();
        toolBar.add(createButton("", "cut", "cut.gif", cutAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "copy", "copy.gif", copyAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "paste", "paste.gif", pasteAction, new Dimension(25, 20)));
        toolBar.addSeparator();
        toolBar.add(createButton("", "fontStyle", "font.gif", fontAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "text-color", "textcolor.gif", textcolorAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "background-color", "bgcolor.gif", backcolorAction, new Dimension(25, 20)));
        toolBar.addSeparator();
        toolBar.add(createButton("", "bold", "bold.gif", boldAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "italic", "italic.gif", italicAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "underline", "underline.gif", underlineAction, new Dimension(25, 20)));
        toolBar.addSeparator();
        toolBar.add(createButton("", "left-Alignment", "left.gif", leftAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "center-Alignment", "center.gif", centerAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "right-Alignment", "right.gif", rightAction, new Dimension(25, 20)));
        toolBar.addSeparator();
//        toolBar.add(createButton("","number-list","number.gif",numberAction, new Dimension(25,20)));
//        toolBar.add(createButton("","bullet-list","bullet.gif",bulletAction, new Dimension(25,20)));
//        toolBar.addSeparator();
//        toolBar.add(createButton("", "insertlink", "hyperlink.gif", hyperlinkAction, new Dimension(25, 20)));
//        toolBar.add(createButton("HTML", "html-source", "", htmlAction, new Dimension(40, 20)));
        toolBar.add(createButton("", "undo", "undo.gif", undoAction, new Dimension(25, 20)));
        toolBar.add(createButton("", "redo", "redo.gif", redoAction, new Dimension(25, 20)));
        toolBar.addSeparator();
        toolBar.add(createButton("", "help", "help.gif", null, new Dimension(25, 20)));

        toolBarPanel.add(toolBar);
        //创建编辑器
        //行距在JDK1.4以上版本才起作用
        StyleConstants.setLineSpacing(new SimpleAttributeSet(), -0.2f);
        textPane.setContentType("text/html");
        textPane.getDocument().addUndoableEditListener(undoHandler);
        JScrollPane editorPane = new JScrollPane(textPane);
        //创建底部按钮
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okButton.setIcon(getIcon("ok.gif"));
        okButton.setPreferredSize(new Dimension(80, 22));
        cancelButton.setIcon(getIcon("cancel.gif"));
        cancelButton.setPreferredSize(new Dimension(80, 22));
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);

        this.add(editorPane, java.awt.BorderLayout.CENTER);
        this.add(toolBarPanel, java.awt.BorderLayout.NORTH);
//        this.add(bottomPanel, java.awt.BorderLayout.SOUTH);
    }

    /**
     * 创建一个JButon。
     * @param text     String
     * @param iconName String
     * @param action   Action
     * @return         JButton
     */
    private JButton createButton(String text, String tooltip, String iconName, Action action, Dimension dim) {
        JButton button = new JButton(action);
        button.setText(text);
        button.setToolTipText(tooltip);
        button.setIcon(getIcon(iconName));
        button.setPreferredSize(dim);
        return button;
    }

    /**
     * 获取图片。
     * @param icon     String
     * @return         ImageIcon
     */
    private ImageIcon getIcon(String icon) {
        ImageIcon iicon = null;
        String className = this.getClass().getName();
        try {
            className = className.replace('.', '/');
            className = className + "/icons/" + icon;
            URL url = this.getClass().getClassLoader().getResource(className);
            if (url == null)
                return null;
            iicon = new ImageIcon(url);
        } catch (Exception ex) {
        }
        return iicon;
    }

    class FontSelectAction
        extends AbstractAction {

        JFontChooserDialog fontDialog;

        public FontSelectAction() {
            super();
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Font font;
            JButton fontButton = (JButton) e.getSource();
            fontDialog = new JFontChooserDialog(fontButton.getLocationOnScreen());
            fontDialog.setVisible(true);
            if (fontDialog.OPTION == fontDialog.OK_OPTION) {
                font = fontDialog.getFont();
                MutableAttributeSet attr = new SimpleAttributeSet();
                //字体大小
                StyleConstants.setFontSize(attr, font.getSize());
                setCharacterAttributes(textPane, attr, false);
                //字体类型
                StyleConstants.setFontFamily(attr, font.getFamily());
                setCharacterAttributes(textPane, attr, false);
            }
        }

        private final void setCharacterAttributes(JEditorPane editor, AttributeSet attr, boolean replace) {
            int p0 = editor.getSelectionStart();
            int p1 = editor.getSelectionEnd();
            if (p0 != p1) {
                StyledDocument doc = (StyledDocument) editor.getDocument();
                doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
            }
            StyledEditorKit k = (StyledEditorKit) editor.getEditorKit();
            MutableAttributeSet inputAttributes = k.getInputAttributes();
            if (replace) {
                inputAttributes.removeAttributes(inputAttributes);
            }
            inputAttributes.addAttributes(attr);
        }
    }

    class ColorAction
        extends AbstractAction {

        public int foreground = 1;
        public int background = 2;
        public int type;

        public ColorAction(int type) {
            super();
            this.type = type;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Color color = JColorChooser.showDialog(JHTMLEditor.this, "选择颜色", Color.white);
            if (color == null) {
                return;
            }
            MutableAttributeSet attr = new SimpleAttributeSet();
            if (type == foreground) {
                //字体大小
                StyleConstants.setForeground(attr, color);
                setCharacterAttributes(textPane, attr, false);
            } else {
                //字体类型
                StyleConstants.setBackground(attr, color);
                setCharacterAttributes(textPane, attr, false);
            }
        }

        private final void setCharacterAttributes(JEditorPane editor, AttributeSet attr, boolean replace) {
            int p0 = editor.getSelectionStart();
            int p1 = editor.getSelectionEnd();
            if (p0 != p1) {
                StyledDocument doc = (StyledDocument) editor.getDocument();
                doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
            }
            StyledEditorKit k = (StyledEditorKit) editor.getEditorKit();
            MutableAttributeSet inputAttributes = k.getInputAttributes();
            if (replace) {
                inputAttributes.removeAttributes(inputAttributes);
            }
            inputAttributes.addAttributes(attr);
        }
    }

    class HyperLinkAction
        extends AbstractAction {
        HyperLinkDialog linkDlg;
        public void actionPerformed(java.awt.event.ActionEvent e) {
            JButton linkButton = (JButton) e.getSource();
            Point point = linkButton.getLocationOnScreen();
            linkDlg = new HyperLinkDialog(point);
            linkDlg.setVisible(true);
        }
    }

    class ShowHTMLAction
        extends AbstractAction {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String htmlText = textPane.getText();
        }
    }

    class OKAction
        extends AbstractAction {
        public String text;
        public void actionPerformed(java.awt.event.ActionEvent e) {
            text = textPane.getText();
        }
        public void setText(String text) {
            this.text = text;
        }
        public String getText(){
            return text;
        }
    }

    class CancelAction
        extends AbstractAction {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String htmlText = textPane.getText();
        }
    }

    /**
     * 超链接编辑窗口。
     */
    class HyperLinkDialog
        extends JDialog implements java.awt.event.ActionListener {
        public HyperLinkDialog(Point point) {
            getContentPane().setLayout(new BorderLayout());
            centerLayout.setVgap(0);
            mainPanel.setLayout(centerLayout);
            cmdPanel.setLayout(cmdLayout);
            bottomPanel.setLayout(bottomLayout);
            bottomLayout.setAlignment(FlowLayout.RIGHT);
            cancelButton.setPreferredSize(new Dimension(70, 22));
            cancelButton.setMnemonic('C');
            cancelButton.setText("Cancel");
            okButton.setPreferredSize(new Dimension(70, 22));
            okButton.setMnemonic('O');
            okButton.setText("OK");
            urlLabel.setPreferredSize(new Dimension(25, 16));
            urlLabel.setHorizontalAlignment(SwingConstants.TRAILING);
            urlLabel.setText("URL:");
            jPanel3.setLayout(flowLayout1);
            flowLayout1.setAlignment(FlowLayout.LEFT);
            jPanel2.setLayout(flowLayout2);
            flowLayout2.setAlignment(FlowLayout.LEFT);
            urlTextField.setPreferredSize(new Dimension(160, 22));
            urlTextField.setText("http://");
            textTextField.setPreferredSize(new Dimension(160, 22));
            textTextField.setText("");
            textLabel.setText("文本:");
            jPanel1.setLayout(flowLayout3);
            flowLayout3.setAlignment(FlowLayout.LEFT);
            getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
            mainPanel.add(jPanel3);
            jPanel3.add(urlLabel);
            jPanel3.add(urlTextField);
            mainPanel.add(jPanel2);
            jPanel2.add(textLabel);
            jPanel2.add(textTextField);
            mainPanel.add(jPanel1);
            getContentPane().add(cmdPanel, java.awt.BorderLayout.EAST);
            cmdPanel.add(okButton);
            cmdPanel.add(cancelButton);
            getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);
            this.setTitle("超链接");
            this.setLocation(point);
            this.setSize(300,150);
            this.setResizable(false);
        }

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

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {

            } else if (e.getSource() == cancelButton) {

            }
        }
    }

    class UndoAction
        extends AbstractAction {

        public UndoAction() {
            super("");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            update();
            redoAction.update();
        }

        public void update() {
            if (undoManager.canUndo()) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    class RedoAction
        extends AbstractAction {

        public RedoAction() {
            super("");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undoManager.redo();
            } catch (CannotRedoException ex) {
                System.err.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            update();
            undoAction.update();
        }

        public void update() {
            if (undoManager.canRedo()) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    class UndoHandler
        implements UndoableEditListener {
        public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            undoAction.update();
            redoAction.update();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JHTMLEditor editor = new JHTMLEditor();
        frame.getContentPane().add(editor);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

}
