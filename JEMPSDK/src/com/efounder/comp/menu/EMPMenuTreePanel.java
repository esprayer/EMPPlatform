package com.efounder.comp.menu;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.comp.menu.listener.EMPTreeMouseListener;
import com.efounder.comp.menu.renderer.EMPMenuTreeCellRenderer;
import com.efounder.eai.data.JParamObject;

public class EMPMenuTreePanel extends JPanel{
	private                     JTree                    tree;
	private               JScrollPane                treeView = null;
	private   EMPMenuTreeCellRenderer    menuTreeCellRenderer = new EMPMenuTreeCellRenderer();
	private      EMPTreeMouseListener       treeMouseListener = new EMPTreeMouseListener();
	private              JParamObject                      PO = null;
	
	public EMPMenuTreePanel() {
		PO = JParamObject.Create();
		StubObject stub = new StubObject();
		stub.setID("The Java Series");
		
		initPanel();
		
		//Create the nodes.
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(stub);
        
        stub.setID("RootNode");
        //
        createNodes(stub, treeNode);
        
        //Create a tree that allows one selection at a time.
        tree = new JTree(treeNode);
        
        //隐藏根节点
//        tree.setRootVisible(false);
        
        //设置自定义描述类  
        tree.setCellRenderer(menuTreeCellRenderer);  
        
        //设置自定义监听事件
        tree.addMouseListener(treeMouseListener);
        
        treeView = new JScrollPane(tree);
        
		this.add(treeView, BorderLayout.CENTER);
	}
	
	/**
	 * 初始化面板
	 */
	private void initPanel() {
		this.setLayout(new BorderLayout());
	}
	
	/**
	 * 初始化节点
	 */
	private void createNodes(StubObject parentStub, DefaultMutableTreeNode treeNode) {
		DefaultMutableTreeNode      category = null;
		StubObject                      stub = null;
		Vector                        vector = (Vector) PackageStub.getContenetList().get(parentStub.getID().toString().toLowerCase());
		for(int i = 0; vector != null && i < vector.size(); i++) {
			stub = (StubObject) vector.get(i);
			if(stub.getString("useUser", null) == null) {
				category = new DefaultMutableTreeNode(stub);
				treeNode.add(category);
			} else {
				if(stub.getString("useUser", null).equals(PO.GetValueByEnvName("UserName", ""))) {
					category = new DefaultMutableTreeNode(stub);
					treeNode.add(category);
				}
			}
			createNodes(stub, category);
		}
	}
}
