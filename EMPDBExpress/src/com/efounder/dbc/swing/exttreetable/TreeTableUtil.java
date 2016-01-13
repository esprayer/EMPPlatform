package com.efounder.dbc.swing.exttreetable;

import com.efounder.dbc.swing.DictDataSet;
import com.efounder.dctbuilder.data.DctContext;
import java.util.List;
import java.util.ArrayList;
import javax.swing.tree.TreePath;
import javax.swing.JTree;

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
public class TreeTableUtil {
    public TreeTableUtil() {
    }

    public static void createTreeData(String dictId, DictTreeTableExt dictTreeTable) throws Exception {
//        DictDataSet dictDataSet = new RLDictDataSet();
//        dictDataSet.setDictName(dictId);
//        dictDataSet.setActiveAgent(true);
//        dictTreeTable.setDataSet(dictDataSet);
    }

    public static List findNode(String findBH,JTree jt){
        List retTreePathList = new ArrayList();
        DictTreeTableModelExt treeModel = (DictTreeTableModelExt)jt.getModel();
        TreeTableNodeExt checkNode = (TreeTableNodeExt)treeModel.getTreeNode("#ROOT");
        findNode(findBH,checkNode,checkNode.getChildCount(),retTreePathList,jt);
        return retTreePathList;
    }
    /**
     * 根据传递的编号遍历查找某个节点
     * @param findBH String
     * @param parentNode CheckNode
     * @param childNum int
     */
    protected static void findNode(String findBH,TreeTableNodeExt parentNode, int childNum,List retTreePathList,JTree jt){
        TreeTableNodeExt checkNode = parentNode;
        if (childNum > 0) { //有孩子
            for (int i = 0; i < childNum; i++) {
                TreeTableNodeExt cn = (TreeTableNodeExt) checkNode.getChildAt(i);
                String nodeBh = cn.getDctBH();
                String nodeMC = cn.getDctMc();
                if(nodeBh.equals(findBH) || nodeMC.equals(findBH)){
                    //设置光标覆盖状态: 添加在下面
                    jt.setSelectionPath(new TreePath(cn.getPath())); //选中该节点
                    retTreePathList.add(new TreePath(cn.getPath()));
                    return;
                }
                //如果该节点有儿子还继续
                if(cn.getChildCount()>0){
                    findNode(findBH, cn, cn.getChildCount(),retTreePathList,jt);
                }
            }
        }
        String nodeBh = checkNode.getDctBH();
        if(nodeBh.equals(findBH)){
          //设置光标覆盖状态
           jt.setSelectionPath(new TreePath(checkNode.getPath())); //选中该节点
           retTreePathList.add(new TreePath(checkNode.getPath()));
            return ;
        }
 }


}
