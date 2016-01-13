package com.efounder.dbc.swing.tree;

import java.awt.*;
import java.math.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.datamanager.pub.meta.MetaDataConstants;
import com.efounder.dbc.*;
import com.efounder.dbc.swing.context.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.builder.base.data.*;

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
public class TreeUtils {
    public TreeUtils() {
    }

    public static DataSetTreeNode createRootNode(String mc) {
        DataSetTreeNode root = new DataSetTreeNode();
        root.setDctBH(MetaDataConstants.DCT_ROOT);
        root.setDctMc(mc);
        root.setCurBH("");
        root.setParentBH("");
        root.setDctMx("0");
        root.setDctJs("0");
        return root;
    }

    public static DataSetTreeNode createRootNode(String mc, boolean control) {
        DataSetTreeNode root = new DataSetTreeNode();
        root.setDctBH(MetaDataConstants.DCT_ROOT);
        root.setDctMc(mc);
        root.setCurBH("");
        root.setParentBH("");
        root.setDctMx("0");
        root.setDctJs("0");
        root.setChildControl(control);
        return root;
    }

    public static DataSetTreeNode createTreeNode(StubObject ds,
                                                 TreeInfoContext context,
                                                 String nodecls) {
      String bmcol = context.getBHColumn();
      String mccol = context.getMCColumn();
      String jscol = context.getJSColumn();
      String mxcol = context.getMXColumn();
      String ptcol = context.getParentColumn();
      //add by gengeng 2009-10-13
      String kzcol = context.getKZColumn();
      String bh = ds.getString(bmcol, "");
      Object o = ds.getObject(mxcol, "");
      String mc = ds.getString(mccol, "");
      String mx = "1";
      if (o instanceof String)
        mx = (String) o;
      if (o instanceof BigDecimal)
        mx = String.valueOf( ( (BigDecimal) o).intValue());
      String pt = ds.getString(ptcol, "");
      o = ds.getObject(jscol, null);
      String js = getJS(context, o);
      DataSetTreeNode node;
      if (js == null || "".equals(js))
        node = createTreeNode(bh, mc, nodecls);
      else
        node = createTreeNode(bh, mc, js, mx, pt, nodecls);
      //设置该节点是否控制下级
      //add by gengeng 2009-10-13
      String kz = ds.getString(kzcol, "");
      node.setChildControl("1".equals(kz.trim()));
      setParent(node, pt, context);
      return node;
    }
    public static DataSetTreeNode createTreeNode(DataSet ds,
                                                 TreeInfoContext context,String nodecls) {
        String bmcol = context.getBHColumn().trim();
        String mccol = context.getMCColumn().trim();
        String jscol = context.getJSColumn().trim();
        String mxcol = context.getMXColumn().trim();
        String ptcol = context.getParentColumn().trim();
        String kzcol = context.getKZColumn().trim();
        String bh = ds.getString(bmcol);
        String mc = ds.getString(mccol);
        Variant v = new Variant();
        String js="0";
        if (!jscol.trim().equals("")) {
          ds.getVariant(jscol, v);
           js = DictUtil.getString(v);
        }
        String mx="",kz="0",pt="";
        if (!mxcol.equals("")) {
            ds.getVariant(mxcol, v);
            mx = DictUtil.getString(v);
        }
        kz = "0";
        if (kzcol.trim().length() > 0) {
          kz = ds.getString(kzcol);
        }
        if(!ptcol.trim().equals(""))
           pt = ds.getString(ptcol);
        DataSetTreeNode node = null;
        if (!context.isGradeDict() || js.equals("0"))
            node = createTreeNode(bh, mc,nodecls);
        else
            node = createTreeNode(bh, mc, js, mx, pt, nodecls);
        node.setChildControl("1".equals(kz));
        setParent(node,pt,context);
        if (ds.hasColumn("UNIT_ID") != null)
            node.setUNIT_ID(ds.getString("UNIT_ID"));
        return node;
    }

    public static DataSetTreeNode createTreeNode(EFRowSet rs,
                                                 TreeInfoContext context,
                                                 String nodecls) {
        String bmcol = context.getBHColumn();
        String mccol = context.getMCColumn();
        String jscol = context.getJSColumn();
        String mxcol = context.getMXColumn();
        String ptcol = context.getParentColumn();
        //add by gengeng 2009-10-13
        String kzcol = context.getKZColumn();
        String bh = rs.getString(bmcol, "");
        Object o = rs.getObject(mxcol, "");
        String mc = rs.getString(mccol, "");
        String mx = "1";
        if (o instanceof String)
            mx = (String) o;
        if (o instanceof BigDecimal)
            mx = String.valueOf(((BigDecimal) o).intValue());
        String pt = rs.getString(ptcol, "");
        o = rs.getObject(jscol, null);
        String js=getJS(context,o);
        DataSetTreeNode node;
        if (js == null || "".equals(js))
            node = createTreeNode(bh, mc, nodecls);
        else
            node = createTreeNode(bh, mc, js, mx, pt, nodecls);
        //设置该节点是否控制下级
        //add by gengeng 2009-10-13
        String kz = rs.getString(kzcol, "");
        node.setChildControl("1".equals(kz.trim()));
        setParent(node, pt, context);
        return node;
    }

    private static String getJS(TreeInfoContext context, Object o) {
        String js;
        if (o instanceof Variant || o instanceof BigDecimal) {
            BigDecimal bd = null;
            if (o instanceof Variant)
                bd = ((Variant) o).getBigDecimal();
            else
                bd = (BigDecimal) o;
            if (!context.isGradeDict() || bd == null || bd.intValue() == 0)
                js = ""; //return createTreeNode(bh, mc, nodecls);
            else {
                js = String.valueOf(bd.intValue());
                // return createTreeNode(bh, mc, js, mx, pt, nodecls);
            }
        } else
            js = (String) o;
        return js;

    }
    private static void setParent(DataSetTreeNode node,String pt,TreeInfoContext context){
        if (pt.trim().length() == 0 && node.getIntJs() > 1) { //没有PARENT ID
            int ptjslen = context.getJslen(node.getIntJs() - 2);
            node.setParentBH(node.getDctBH().substring(0, ptjslen));
            node.setCurBH(node.getDctBH().substring(ptjslen));
        }

    }
    protected static DataSetTreeNode createTreeNode(String bm, String mc,String nodecls) {
        DataSetTreeNode node = null;
        try {
            node = (DataSetTreeNode) Class.forName(nodecls).newInstance();
        } catch (Exception e){
            node=new DataSetTreeNode();
        }
        node.setDctBH(bm);
        node.setDctMc(mc);
        node.setCurBH(node.getDctBH());
        return node;
    }

    protected static DataSetTreeNode createTreeNode(String bm, String mc,
        String js, String mx,
        String pt, String nodecls) {
        DataSetTreeNode node = null;
        try {
            node = (DataSetTreeNode) Class.forName(nodecls).newInstance();
        } catch (Exception e) {
            node = new DataSetTreeNode();
        }

        node.setDctBH(bm);
        node.setDctMc(mc);
        node.setCurBH(node.getDctBH());
        node.setDctJs(js);
        node.setDctMx(mx);
        node.setParentBH(pt);
        if (node.getIntJs() > 1) {
            if (bm.length() > pt.length())
                node.setCurBH(bm.substring(pt.length()));
        }
        return node;
    }

    public static DataSetTreeNode createTreeNode(DataSet ds,
                                                 DictMetaDataEx dmde) {
        DataSetTreeNode node = new DataSetTreeNode();
          String bmcol = dmde.getDctBmCol();
          String mccol = dmde.getDctMcCol();
          String jscol = dmde.getDctJsCol();
          String mxcol = dmde.getDctMxCol();
          String ptcol = dmde.getDctParentCol();
          String kzcol = dmde.getDctControlCol();
          node.setDctBH(ds.getString(bmcol));
          node.setDctMc(ds.getString(mccol));
          node.setCurBH(node.getDctBH());
          if (dmde.isGradeDict()) {
//              BigDecimal bg = ds.getBigDecimal(jscol);
//              if (bg == null || bg.intValue() == 0)
//                  return null;
              Variant v = new Variant();
              ds.getVariant(jscol, v);
              node.setDctJs(DictUtil.getString(v));
              ds.getVariant(mxcol, v);
              node.setDctMx(DictUtil.getString(v));
//              node.setParentBH(ds.getString(ptcol));
              node.setParentBH(getParentBH(dmde, ds));
              if (node.getIntJs() > 1) {
                  String bm = node.getDctBH();
                  String pt = node.getParentBH();
                  if (bm.length() > pt.length())
                      node.setCurBH(bm.substring(pt.length()));
              }
          }
          return node;
    }


    public static void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        // Traverse tree from root
        expandAll(tree, new TreePath(root), expand);
    }

    private static void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    /**
     * 如果对于分级的字典，没有父列，则伪造一个父列同时将各级编码的伪父列赋值
     * add by gengeng 2009-10-12
     *
     * @param      dm DictModel
     * @param context DctContext
     */
    public static void setForgedParentColumn(DictMetaDataEx dmd, ClientDataSet cds) {
        String parentCol = dmd.getDctParentCol();
        //分级字典同时父节点列为空
        if (dmd.isGradeDict() &&
            (parentCol.trim().length() == 0 || "ZD_PARENT".equals(parentCol))) {

            //伪造父列的元数据
            ColumnMetaData meta = MetaDataUtil.CreateColumnMetadata("ZD_PARENT",
                    "默认父列", "C", 30, 1, "");
            if (!dmd.hasColumn("ZD_PARENT")) {
                dmd.addUsedCol(meta);
            }
            dmd.setString("DCT_PTCOLID", "ZD_PARENT");
        }
    }

 //从basetreeui挖过来的
 public static int getRowX(int row, int depth) {
        int lci=((Integer)UIManager.get("Tree.leftChildIndent")).
                 intValue();
         int rci=((Integer)UIManager.get("Tree.rightChildIndent")).
                 intValue();
         int totalChildIndent=lci+rci;

        return totalChildIndent *(depth );
    }

    /**
     * 获取其上级编号
     *
     * @param dmd DictMetaDataEx
     * @param cds DataSet
     * @return    String
     */
//    public static String getParentBH(DictMetaDataEx dmd, DataSet cds){
//        String bhcol = dmd.getDctBmCol();
//        String jscol = dmd.getDctJsCol();
//        String  stru = dmd.getDctBMStru();
//        Variant    v = new Variant();
//        cds.getVariant(jscol, v);
//        int curjs = DictUtil.getInt(v);
//        if (cds == null || curjs == 1) {
//            return "";
//        }
//        int ptlen = DictUtil.getCodeLength(stru, curjs - 1);
//        return cds.getString(bhcol).substring(0, ptlen);
//    }

    public static String getParentBH(DictMetaDataEx dmd, ReadRow row) {
        String bhcol = dmd.getDctBmCol();
        String jscol = dmd.getDctJsCol();
        String stru = dmd.getDctBMStru();
        Variant v = new Variant();
        row.getVariant(jscol, v);
        int curjs = DictUtil.getInt(v);
        if (row == null || curjs == 1) {
            return "";
        }
        int ptlen = DictUtil.getCodeLength(stru, curjs - 1);
        return row.getString(bhcol).substring(0, ptlen);
    }

    public static boolean isLocationInExpandControl(JTree tree,TreePath path,
                                                int mouseX, int mouseY) {
        int boxWidth;
            Insets                  i = tree.getInsets();
            Icon icon=((DefaultTreeCellRenderer) tree.getCellRenderer()).getOpenIcon();
            if(icon != null)
                boxWidth = icon.getIconWidth();
            else
                boxWidth = 8;
            int rci=((Integer)UIManager.get("Tree.rightChildIndent")).
			   intValue();

            int boxLeftX = getRowX(tree.getRowForPath(path),
                   path.getPathCount() - 1) - rci -
                   boxWidth / 2;

                boxLeftX += i.left;
            int boxRightX = boxLeftX + boxWidth;

            return mouseX >= boxLeftX && mouseX <= boxRightX;

    }
}
