package com.core.tree;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class CheckNodeTreeExample extends JFrame
{
  public CheckNodeTreeExample()
  {
    super("CheckNode TreeExample");
    String[] strs = { "swing", "platf", "basic", "metal", "JTree" };

    CheckNode[] nodes = new CheckNode[strs.length];
    for (int i = 0; i < strs.length; ++i) {
      nodes[i] = new CheckNode(strs[i]);
    }
    nodes[0].add(nodes[1]);
    nodes[1].add(nodes[2]);
    nodes[1].add(nodes[3]);
    nodes[0].add(nodes[4]);
    nodes[3].setSelected(true);
    JTree tree = new JTree(nodes[0]);
    tree.setCellRenderer(new CheckRenderer());
    tree.getSelectionModel().setSelectionMode(1);

    tree.putClientProperty("JTree.lineStyle", "Angled");
    tree.addMouseListener(new NodeSelectionListener(tree));
    JScrollPane sp = new JScrollPane(tree);

    ModePanel mp = new ModePanel(nodes);
    JTextArea textArea = new JTextArea(3, 10);
    JScrollPane textPanel = new JScrollPane(textArea);
    JButton button = new JButton("print");
    button.addActionListener(new ButtonActionListener(nodes[0], textArea));

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(mp, "Center");
    panel.add(button, "South");

    getContentPane().add(sp, "Center");
    getContentPane().add(panel, "East");
    getContentPane().add(textPanel, "South");
  }

  public static void main(String[] args)
  {
    CheckNodeTreeExample frame = new CheckNodeTreeExample();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0);
      }
    });
    frame.setSize(300, 200);
    frame.setVisible(true);
  }

  class ButtonActionListener
    implements ActionListener
  {
    CheckNode root;
    JTextArea textArea;

    ButtonActionListener(CheckNode paramCheckNode, JTextArea paramJTextArea)
    {
      this.root = paramCheckNode;
      this.textArea = textArea;
    }

    public void actionPerformed(ActionEvent e) {
      Enumeration enumer = this.root.breadthFirstEnumeration();
      while (enumer.hasMoreElements()) {
        CheckNode node = (CheckNode)enumer.nextElement();
        if (node.isSelected()) {
          TreeNode[] nodes = node.getPath();
          this.textArea.append("\n" + nodes[0].toString());
          for (int i = 1; i < nodes.length; ++i)
            this.textArea.append("/" + nodes[i].toString());
        }
      }
    }
  }

  class ModePanel extends JPanel
    implements ActionListener
  {
    CheckNode[] nodes;
    JRadioButton b_single;
    JRadioButton b_dig_in;

    ModePanel(CheckNode[] nodes)
    {
      this.nodes = nodes;
      setLayout(new GridLayout(2, 1));
      setBorder(new TitledBorder("Check Mode"));
      ButtonGroup group = new ButtonGroup();
      add(this.b_dig_in = new JRadioButton("DIG_IN  "));
      add(this.b_single = new JRadioButton("SINGLE  "));
      group.add(this.b_dig_in);
      group.add(this.b_single);
      this.b_dig_in.addActionListener(this);
      this.b_single.addActionListener(this);
      this.b_dig_in.setSelected(true);
    }

    public void actionPerformed(ActionEvent e)
    {
      int mode;
      if (this.b_single == e.getSource())
        mode = 0;
      else {
        mode = 4;
      }
      for (int i = 0; i < this.nodes.length; ++i)
        this.nodes[i].setSelectionMode(mode);
    }
  }

  class NodeSelectionListener extends MouseAdapter
  {
    JTree tree;

    NodeSelectionListener(JTree paramJTree)
    {
      this.tree = paramJTree;
    }

    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      int row = this.tree.getRowForLocation(x, y);
      TreePath path = this.tree.getPathForRow(row);

      if (path != null) {
        CheckNode node = (CheckNode)path.getLastPathComponent();
        boolean isSelected = !(node.isSelected());
        node.setSelected(isSelected);
        if (node.getSelectionMode() == 4) {
          if (isSelected)
            this.tree.expandPath(path);
          else {
            this.tree.collapsePath(path);
          }
        }
        ((DefaultTreeModel)this.tree.getModel()).nodeChanged(node);

        if (row == 0) {
          this.tree.revalidate();
          this.tree.repaint();
        }
      }
    }
  }
}