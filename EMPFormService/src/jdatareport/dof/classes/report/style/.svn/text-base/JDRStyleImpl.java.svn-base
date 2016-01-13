package jdatareport.dof.classes.report.style;

import java.net.*;
import java.util.*;

import javax.swing.tree.*;

import org.jdom.*;
import jdatareport.dof.classes.report.util.*;
import jtoolkit.xml.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRStyleImpl {
    static final String ROOT_VIEW_NAME = "表格样式";
    static final String ROOT_STORE_NAME = "StyleList";
    /**
     *
     * @param styleDlg
     */
    public JDRStyleImpl() {

    }

    /**
     *
     * @return
     * @throws java.lang.Exception
     */
    public TreeNode createTree(URL url) throws Exception {
        if (url == null) {
            return null;
        }
        JXMLBaseObject XMLObj = new JXMLBaseObject(url);
        if (XMLObj == null) {
            return null;
        }
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(ROOT_VIEW_NAME);
        for (Iterator i = XMLObj.Root.getChildren().iterator(); i.hasNext(); ) {
            Element elmt = (Element) i.next();
            DefaultMutableTreeNode node = createTreeNode(elmt);
            if (node != null) {
                rootNode.add(node);
            }
        }
        return rootNode;
    }

    /**
     *
     * @param elmt
     * @return
     * @throws java.lang.Exception
     */
    private DefaultMutableTreeNode createTreeNode(Element elmt) throws Exception {
        String id = elmt.getAttributeValue("Id");
        String caption = elmt.getAttributeValue("Caption");
        Element titleElmt = elmt.getChild("Title");
        Element headerElmt = elmt.getChild("Header");
        Element bodyElmt = elmt.getChild("Body");

        JDRQueryFormatInfo fmtInfo = new JDRQueryFormatInfo();
        fmtInfo.mTitleColor = Integer.parseInt(titleElmt.getAttributeValue("TitleColor"), 16);
        fmtInfo.mTitleOuterBorder = (short) Integer.parseInt(titleElmt.getAttributeValue("TitleOuterBorder"));
        fmtInfo.mTitleInnerBorder = (short) Integer.parseInt(titleElmt.getAttributeValue("TitleInnerBorder"));
        fmtInfo.mHeadColor = Integer.parseInt(headerElmt.getAttributeValue("HeaderColor"), 16);
        fmtInfo.mHeadOuterBorder = (short) Integer.parseInt(headerElmt.getAttributeValue("HeaderOuterBorder"));
        fmtInfo.mHeadInnerBorder = (short) Integer.parseInt(headerElmt.getAttributeValue("HeaderInnerBorder"));
        fmtInfo.mBodyBgColor0 = Integer.parseInt(bodyElmt.getAttributeValue("BodyBgColor0"), 16);
        fmtInfo.mBodyBgColor1 = Integer.parseInt(bodyElmt.getAttributeValue("BodyBgColor1"), 16);
        fmtInfo.mBodyOuterBorder = (short) Integer.parseInt(bodyElmt.getAttributeValue("BodyOuterBorder"));
        fmtInfo.mBodyInnerBorder = (short) Integer.parseInt(bodyElmt.getAttributeValue("BodyInnerBorder"));
        //
        JDRStyleItemEntry itemEntry = new JDRStyleItemEntry(id, caption);
        itemEntry.userObject = fmtInfo;
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(itemEntry);
        return node;
    }
}