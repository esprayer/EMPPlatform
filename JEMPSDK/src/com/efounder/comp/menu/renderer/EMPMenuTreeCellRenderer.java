package com.efounder.comp.menu.renderer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.core.xml.StubObject;
import com.efounder.resource.JResource;

/**
 * 自定义树描述类,将树的每个节点设置成不同的图标
 * @author prayer
 *
 */
public class EMPMenuTreeCellRenderer extends DefaultTreeCellRenderer {
	/**
	 * ID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 重写父类DefaultTreeCellRenderer的方法
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
												  boolean sel, boolean expanded, boolean leaf, int row,
												  boolean hasFocus) {

		//执行父类原型操作
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		setText(value.toString());
		
		if (sel) {
			setForeground(getTextSelectionColor());
		} else {
			setForeground(getTextNonSelectionColor());
		}
		
		//得到每个节点的TreeNode
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		
		//得到每个节点
		StubObject stub = (StubObject) node.getUserObject();		
		
		//判断是哪个文本的节点设置对应的值（这里如果节点传入的是一个实体,则可以根据实体里面的一个类型属性来显示对应的图标）
		this.setIcon(createNodeImage(stub));

		return this;
	}

	private ImageIcon createNodeImage(StubObject stub) {
		ImageIcon imageIcon = JResource.getImageIcon(this,"/com/efounder/eai/ide/image", stub.getString("icon", "").toString(), null);
		return imageIcon;
	}
}
