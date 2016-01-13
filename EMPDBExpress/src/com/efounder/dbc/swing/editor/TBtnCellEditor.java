package com.efounder.dbc.swing.editor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.efounder.dbc.swing.help.*;
import com.efounder.eai.ide.*;
import com.core.xml.StubObject;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TBtnCellEditor extends DefaultCellEditor implements ActionListener{
    public class helpTextField extends JPanel{
        BorderLayout bl=new BorderLayout();
        JTextField text=null;
        JButton bthelp=new JButton();
        Icon icon=null;
        public helpTextField(JTextField jtf){
            setLayout(bl);
            text=jtf;
             icon=ExplorerIcons.getExplorerIcon("help.gif");
            bthelp.setIcon(icon);
            bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
            add(text,BorderLayout.CENTER);
            add(bthelp,BorderLayout.EAST);
        }
        public JButton getHelpButton(){
            return bthelp;
        }
        public void setText(String text){
          this.text.setText(text);
        }
        public String getText(){
          return text.getText();
        }
    }
    protected JComponent helpcomp=null;
    protected StubObject contextSO;
    public TBtnCellEditor(StubObject so){
        super(new JTextField());
        helpcomp=new helpTextField((JTextField)editorComponent);
        ((helpTextField)helpcomp).getHelpButton().addActionListener(this);
        this.contextSO = so;
     }
    public Object getCellEditorValue() {
        String value=(String)super.getCellEditorValue();
        if(value != null)
            return value;
        return "";
     }
     public Component getComponent() {
         return helpcomp;
     }

     public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected,
                                                  int row, int column) {
         super.getTableCellEditorComponent(table,value,isSelected,row,column);
         return helpcomp;
     }

    public void actionPerformed(ActionEvent e) {
        String bh=CommonHelpObject.showHelp(contextSO,contextSO.getString("title",""));
        if(bh != null && !"".equals(bh.trim())){
          ((helpTextField)helpcomp).text.setText(bh);
        }

    }
}
