package jenterprise.bof.classes.AppExplorerObject.messlist;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

/**
 * 【更多】对象。
 * 由于可能出现多个【更多】，这个时候应该每个【更多】都有一个唯一的标示。该类中封装了
 * 一个JLabel用来显示，还封装了一个类别用来标示具体的某个消息面板。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JMoreLabel
    extends JLabel {

    private String type;
    private Object object;

    public JMoreLabel() {
        this("", null, LEADING);
    }

    public JMoreLabel(Icon image, int horizontalAlignment) {
        this(null, image, horizontalAlignment);
    }

    public JMoreLabel(Icon image) {
        this(null, image, CENTER);
    }

    public JMoreLabel(String text) {
        this(text, null, LEADING);
    }

    public JMoreLabel(String text, int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }

    public JMoreLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setFont(new Font("宋体", Font.ITALIC, 12));
        setForeground(Color.BLUE);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getIncreObject() {
        return object;
    }

    public void setIncreObject(Object object) {
        this.object = object;
    }

    public boolean isHelpable() {
        return type != null;
    }

}
