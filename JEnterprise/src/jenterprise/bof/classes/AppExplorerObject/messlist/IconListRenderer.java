package jenterprise.bof.classes.AppExplorerObject.messlist;

import java.awt.*;
import javax.swing.*;

/**
 * 消息体显示图标渲染器.
 *
 * @version 1.0 初始版本${gengeng}
 */
public class IconListRenderer
    extends JLabel implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        MessageItem item = (MessageItem) value;
        setIcon(item.getIcon());
        setText(item.getText());

        setPreferredSize(new Dimension(10,20));

        Color fgNormal = UIManager.getColor("List.foreground");
        Color fgSelect = new Color(0x990033);

        //set readed color
        if (item.isReaded()) {
            setForeground(fgSelect);
        } else {
            setForeground(fgNormal);
        }

        return this;
    }

}

