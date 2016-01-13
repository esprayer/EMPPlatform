package jenterprise.bof.classes.AppExplorerObject;

import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JFunctionNodeRender extends DefaultTreeCellRenderer{
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  private static Icon mOpen,mClose;
  public JFunctionNodeRender() {
    if ( mOpen == null ){
          //装入图标。
          mOpen   =  JResource.LoadImageIcon(this,"TREE_NODE_OPEN");
          mClose  =  JResource.LoadImageIcon(this,"TREE_NODE_CLOSE");
        }
  }
  private static Font      mFont   = new Font(res.getString("String_145"),Font.PLAIN,13);


  public Component getTreeCellRendererComponent(JTree tree, Object value,
                                               boolean sel,
                                               boolean expanded,
                                               boolean leaf, int row,
                                               boolean hasFocus) {


   super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
   Font pFont = super.getFont();
   //只有我的字体比上级的大的时候，才设。
   if ( mFont.getSize() > pFont.getSize()){
     super.setFont(mFont);
   }

   DefaultMutableTreeNode pNode = (DefaultMutableTreeNode)value;

   JMenuItemObjectStub pMyNode = (JMenuItemObjectStub)(pNode.getUserObject());
   Icon         pNodeIcon = null;

   /**
    * 非明细结点如此处理。
    */
   if ( !pNode.isLeaf()){
     try {
       pNodeIcon = pMyNode.getImageIcon();

       if (pNodeIcon == null) {
         pNodeIcon = JResource.LoadImageIcon(this, "SYS_RUN");
       }

       super.setIcon(pNodeIcon);
     }
     catch (Exception E) {
       E.printStackTrace();
     }
   }
   else{
     //明细，使用原来的白板。
   }

   //叶图标
   //super.setLeafIcon (pNodeIcon);

   //打开图标
   //super.setOpenIcon(mOpen);
   //关闭图标。
   //super.setClosedIcon(mClose);

   return this;
 }

}
