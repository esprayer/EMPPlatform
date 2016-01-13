package com.efounder.dbc.swing.editor;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.util.MetaDataUtil;

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
public class StringCellEditor extends DefaultCellEditor {

    /**
     *
     */
    protected ColumnMetaData cmd;

    /**
     *
     */
    public StringCellEditor() {
        this(null);
    }

    /**
     *
     * @param selValue Object
     * @param unselValue Object
     */
    public StringCellEditor(ColumnMetaData cmd) {
        super(new JTextField());
        this.setClickCountToStart(2);
        this.cmd = cmd;
        JTextField text = (JTextField) editorComponent;
        text.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        text.setDocument(new LengthDocument());
    }

    /**
     *
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        JTextField text = (JTextField) editorComponent;
        text.setText(value == null ? "" : value.toString());
        return editorComponent;
    }


    /**
     *
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
    class LengthDocument extends PlainDocument {

        /**
         *
         * @param offset int
         * @param str String
         * @param a AttributeSet
         * @throws BadLocationException
         */
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            if (checkLenth(str))
                super.insertString(offset, str, a);
        }

        /**
         *
         * @return boolean
         */
        protected boolean checkLenth(String str) {
            try {
                int length = cmd.getColDataLen();
                // 判断字符的长度是否超过元数据的定义长度
                if (length <= 0)
                    return true;
                JTextField textField = (JTextField) StringCellEditor.this.editorComponent;
                String text = textField.getText() + str;
                String leng = cmd.getExtAttriValue("COL_OPT", "=", ";", "DBLength");
                if (leng == null || leng.trim().length() == 0)
                    leng = "3";
                int lengthed = MetaDataUtil.getLenth(text, Integer.parseInt(leng));
                if (lengthed > length)
                    return false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
    }


}
