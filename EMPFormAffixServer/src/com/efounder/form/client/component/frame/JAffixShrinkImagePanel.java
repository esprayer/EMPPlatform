package com.efounder.form.client.component.frame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

//import com.borland.jbcl.layout.*;
import com.efounder.comp.*;
import com.efounder.eai.ide.*;
import com.efounder.form.client.component.action.JAffixActionFunction;
import com.efounder.form.client.component.frame.util.JImageAffixUtil;
import com.efounder.form.client.util.JOpenAffixUtil;
import com.efounder.action.ActionGroup;
import com.l2fprod.common.swing.JButtonBar;
import com.borland.jbcl.layout.VerticalFlowLayout;
import com.borland.jbcl.layout.BoxLayout2;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JAffixShrinkImagePanel extends JPanel implements ActionListener {
    public JAffixShrinkImagePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    JPanel desPanel = new JPanel();
    JLabel desLabel = new JLabel();
    JAffixUploadPanel uploadPanel = null;
    DefaultTableModel tableModel = new DefaultTableModel();
    java.util.List rowToolbarList = new ArrayList();

    VerticalFlowLayout vfl=new VerticalFlowLayout();
    JImagePanel imagePanel = new JImagePanel();
    ButtonGroup group = new ButtonGroup();

    private void jbInit() throws Exception {
        initPanel();
    }

    protected void initPanel() {
        this.setLayout(new BorderLayout());
        desPanel.setLayout(new BorderLayout());
        desPanel.add(desLabel, BorderLayout.CENTER);
        desLabel.setHorizontalAlignment(JLabel.LEFT);
        desLabel.setPreferredSize(new Dimension(200, 30));
        this.add(desPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(imagePanel, null);
        this.add(scrollPane, BorderLayout.CENTER);

        Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        Border border11 = new TitledBorder(border1, "描述");
        desPanel.setBorder(border11);

        Border border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        Border border21 = new TitledBorder(border2, "附件缩略图");
        scrollPane.setBorder(border21);

        vfl.setHgap(10);//10
        vfl.setVgap(30);//10
        imagePanel.setLayout(vfl);
        imagePanel.setBackground(Color.white);
    }

    protected JButtonBar createToolBar(){
        JButtonBar toolbar = new JButtonBar(JButtonBar.HORIZONTAL);
        toolbar.setOpaque(false);
        toolbar.setBorder(null);
        toolbar.setPreferredSize(new Dimension(550,250));//600,320

        return toolbar;
    }

    protected JPanel createRowPanel(){
        JPanel rowPanel = new JPanel();
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(30);

        rowPanel.setLayout(fl);

        return rowPanel;
    }

    protected JToggleButton createButton(JImageDescBean imgDesBean,Icon icon){
        JToggleButtonExt button = new JToggleButtonExt();
        button.setIcon(icon);
        button.addActionListener(this);
        button.putClientProperty("DES",imgDesBean);
        button.setPreferredSize(new Dimension(JImageAffixUtil.IMAGE_BUTTON_WIDTH, JImageAffixUtil.IMAGE_BUTTON_HEIGHT));//300,250
        button.addMouseListener(new ShrinkImageMouseAdapter(this));
        button.setRlLabel(this.desLabel);
        button.setToolTipText(imgDesBean.getImgName());
        button.setBackground(Color.white);
        button.setOpaque(false);

        return button;
    }
    /**
     * 添加一个Image按钮
     * @param imgDes JImageDescBean
     * @param imageIcon Icon
     */
    public void addOneNewImage(JImageDescBean imgDes,Icon imageIcon) {//, String fileName
        try {
            JPanel toolbar =  null;
            //已存在的附件个数
            int cnt = uploadPanel.getAffixCount();
            int imageRows = rowToolbarList.size();

            if((cnt-1)%3 == 0 ){//&& cnt/2 == imageRows
                //每行正好填充满, 这时要创建新的ButtonBar
                toolbar = createRowPanel();
                rowToolbarList.add(toolbar);
                imagePanel.add(toolbar);
            }else{
                int index = rowToolbarList.size();
                toolbar = (JPanel)rowToolbarList.get(index - 1);
            }
            JToggleButton button = createButton(imgDes,imageIcon);
            toolbar.add(button);
            group.add(button);

            toolbar.validate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void addOneNewImage(JImageDescBean imgDes,Icon imageIcon) {//, String fileName
//        try {
//            JButtonBar toolbar =  null;
//            //已存在的附件个数
//            int cnt = uploadPanel.getAffixCount();
//            int imageRows = rowToolbarList.size();
//
//            if((cnt-1)%3 == 0 ){//&& cnt/2 == imageRows
//                //每行正好填充满, 这时要创建新的ButtonBar
//                toolbar = createToolBar();
//                rowToolbarList.add(toolbar);
//                imagePanel.add(toolbar);
//            }else{
//                int index = rowToolbarList.size();
//                toolbar = (JButtonBar)rowToolbarList.get(index - 1);
//            }
//            JToggleButton button = createButton(imgDes,imageIcon);
//            toolbar.add(button);
//            group.add(button);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     *
     * @param imgDes JImageDescBean
     * @param imageIcon Icon
     * @param rowIndex int
     */
//    public void addImageToToolbar(JImageDescBean imgDes,Icon imageIcon,int rowIndex) {//, String fileName
//        try {
//            JButtonBar toolbar =  null;
//            //已存在的附件个数
//            if(rowIndex%3 == 0 ){
//                //每行正好填充满, 这时要创建新的ButtonBar
//                toolbar = createToolBar();
//                rowToolbarList.add(toolbar);
//                imagePanel.add(toolbar);
//            }else{
//                int index = rowToolbarList.size();
//                toolbar = (JButtonBar)rowToolbarList.get(index - 1);
//            }
//            JToggleButton button = createButton(imgDes,imageIcon);
//            toolbar.add(button);
//            group.add(button);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void addImageToToolbar(JImageDescBean imgDes,Icon imageIcon,int rowIndex) {//, String fileName
        try {
            JPanel toolbar =  null;
            //已存在的附件个数
            if(rowIndex%3 == 0 ){
                //每行正好填充满, 这时要创建新的ButtonBar
                toolbar = createRowPanel();
                rowToolbarList.add(toolbar);
                imagePanel.add(toolbar);
            }else{
                int index = rowToolbarList.size();
                toolbar = (JPanel)rowToolbarList.get(index - 1);
            }
            JToggleButton button = createButton(imgDes,imageIcon);
            toolbar.add(button);
            group.add(button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Icon getImageIcon(String type) throws Exception {
        if ("XLS".equals(type)) { //弄个excel图标
            return ExplorerIcons.getExplorerIcon("office/(08,03).gif");
        } else if ("DOC".equals(type)) { //word图标
            return ExplorerIcons.getExplorerIcon("office/(08,03).gif");
        } else if ("PDF".equals(type)) { //Adobe图标
            return ExplorerIcons.getExplorerIcon("office/(08,03).gif");
        } else {
            return ExplorerIcons.getExplorerIcon("office/(08,03).gif");
        }
    }


    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj instanceof JToggleButton) {
            JToggleButtonExt.showImageDes((JToggleButton) obj,this.desLabel);
        }
    }

    public JAffixUploadPanel getUploadPanel() {
        return uploadPanel;
    }

    public JImagePanel getImagePanel() {
        return imagePanel;
    }

    public void setUploadPanel(JAffixUploadPanel uploadPanel) {
        this.uploadPanel = uploadPanel;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && ( e.getModifiers() & e.BUTTON1_MASK ) != 0  ) {
            try {
              Object obj = e.getSource() ;
              JAffixActionFunction.openImageView(this.uploadPanel,(JToggleButton) obj);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else if (( e.getModifiers() & e.BUTTON3_MASK ) != 0  ) {
           ActionGroup popupMenu = null;
           Point p = e.getPoint();
           popupMenu = getMenuAction(e.getSource());

           if (popupMenu != null) {
               showPopupmenu(p, (java.awt.Component) e.getSource(), popupMenu);
           }
        }
    }


    protected ActionGroup getMenuAction(Object obj) {
        ActionGroup ag = null;
        Object[] nodeArray = {this, obj};
//        Action[] actions = ActionManager.getContextActions(this.getClass().getName(), this, nodeArray);
//        if (actions != null) {
//            ag = new ActionGroup();
//            for (int i = 0; i < actions.length; i++) {
//                ag.add(actions[i]);
//            }
//        }
        return ag;
    }

    protected void showPopupmenu(Point p, java.awt.Component invoker,
                         ActionGroup popupMenu) {
        if (popupMenu != null) {
//            ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker, popupMenu, true);
//            actionPopupMenu.show(invoker, p.x, p.y);
        }
    }



}

class ShrinkImageMouseAdapter
    extends MouseAdapter {
  private JAffixShrinkImagePanel adaptee;
  ShrinkImageMouseAdapter(JAffixShrinkImagePanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.mouseClicked(e);
  }
}
