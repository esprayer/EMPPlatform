package com.efounder.comp.menu.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.openide.WaitingManager;

import com.core.xml.StubObject;
import com.efounder.eai.EAI;

public class EMPTreeMouseListener implements MouseListener{

	public void mouseClicked(MouseEvent e) {			
		if(e.getClickCount() == 2) {
			JTree treeSource = (JTree) e.getSource();
			TreePath path = treeSource.getPathForLocation(e.getX(), e.getY());
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
			StubObject stub = (StubObject) node.getUserObject();
			if(node != null && !stub.getString("object", "").equals("") && !stub.getString("method", "").equals("")) {
				try {
//					Thread t = new Thread(new Runnable() {
//			            public void run() {
//			                try {
//								EAI.BOF.IOM("EMPBOFFWManager", "loadHouseLeaseInfo", null);
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//			            }
//			        });
//					WaitingManager.getDefault().waitInvoke(EAI.getMainWindow().getJChildWindow(), "多语言", "正在打开数据信息......", null, t);
					EAI.BOF.IOM(stub.getString("object", ""), stub.getString("method", ""), stub);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override 	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
