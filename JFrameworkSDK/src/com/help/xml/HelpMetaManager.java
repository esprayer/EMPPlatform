package com.help.xml;

import java.util.Vector;


import java.io.InputStream;
import com.report.table.jxml.*;
import org.jdom.*;
import java.util.List;
/**
 * <p>Title: 帮助数据的XML.非公用类</p>
 * <p>Description: 特定类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Pansoft</p>
 * @author chaor
 * @version 1.0
 */

public class HelpMetaManager extends XmlManager{

    private Element     Table;            //帮助表信息
    private Element     TableTitle;       //帮助名称（标签）
    private Element     TableFormate;     //列表格式（表头）
    private Element     Cols;             //表头所有的列

    //private Element     Groups;
    //private Element     TableView;

    public HelpMetaManager() {
      super();
        Table = super.CreateElement("Table");
        TableTitle = super.CreateElement("HelpTitle");
        TableFormate = super.CreateElement("HelpHead");
        Cols = super.CreateElement("Cols");
        //Groups = document.createElement("Groups");
        //TableView = document.createElement("TableView");
        TableFormate.addContent(Cols);
        //TableFormate.appendChild(Groups);
        //TableFormate.appendChild(TableView);
        Table.addContent(TableTitle);
        Table.addContent(TableFormate);
        super.Root = Table;
    }

    public HelpMetaManager(InputStream is) {
        super();
        super.InitXMLStream(is);

        Table      = super.getRootElement();
        TableTitle = super.GetElementByName(Table,"HelpTitle");
        TableFormate = super.GetElementByName(Table,"HelpHead");
        Cols = super.GetElementByName(TableFormate,"Cols");

        super.Root = Table;

    }

    public HelpMetaManager(String s) {
        super(s);
        Table      = super.getRootElement();
         TableTitle = super.GetElementByName(Table,"HelpTitle");
         TableFormate = super.GetElementByName(Table,"HelpHead");
         Cols = super.GetElementByName(TableFormate,"Cols");

    }

    public void addTitle(XmlTitle t){
        Element Title;
        Title = super.CreateElement("Title");
        Title.setAttribute("align",t.getAlign());
        Title.setAttribute("fontname",t.getFontname());
        Title.setAttribute("fontstyle",t.getFontstyle());
        Title.setAttribute("fontsize",t.getFontsize());

        Vector labels = t.getLabels();

        for(int i=0;i<labels.size(); i++){
          XmlLabel label = (XmlLabel)labels.elementAt(i);
          Element Label = super.CreateElement("Label");
          Label.setAttribute("name",label.getName());
          Label.setAttribute("caption",label.getCaption());
          Title.addContent (Label);
        }
        TableTitle.addContent(Title);
    }
    public void addColumn(XmlColumn c){
        Element Column;
        Column = super.CreateElement("Col");

        Column.setAttribute("align",c.getAlign());
        Column.setAttribute("caption",c.getCaption());
        Column.setAttribute("change",c.getChange());
        Column.setAttribute("datatype",c.getDatatype());
        Column.setAttribute("editctrl",c.getEditctrl());
        Column.setAttribute("fontname",c.getFontname());
        Column.setAttribute("fontstyle",c.getFontstyle());
        Column.setAttribute("fontsize",c.getFontsize());
        Column.setAttribute("format",c.getFormat());
        Column.setAttribute("id",c.getId());
        Column.setAttribute("lock",c.getLock());
        Column.setAttribute("name",c.getName());
        Column.setAttribute("no",c.getNo());
        Column.setAttribute("viewctrl",c.getViewctrl());
        Column.setAttribute("width",c.getWidth());
        Column.setAttribute("showmask",c.getShowMask());
        Cols.addContent(Column);

    }
    public Vector getColumns() {
        Vector columns = new Vector();
        List colList = super.getChildrenNodes( Cols,"Col");

        ////System.out.println("len = "+ colList.getLength());
        for (int i = 0; i < colList.size(); i++) {
            XmlColumn col = new XmlColumn();
            Element colNode = (Element)colList.get(i);
            //if (colNode.getNodeType() == Node.ELEMENT_NODE) {
                //printNode(colNode);

                col.setId(getAttribute(colNode, "id"));
                col.setName(getAttribute(colNode, "name"));
                col.setNo(getAttribute(colNode, "no"));
                col.setCaption(getAttribute(colNode, "caption"));
                col.setFontname(getAttribute(colNode, "fontname"));
                col.setFontsize(getAttribute(colNode, "fontsize"));
                col.setWidth(getAttribute(colNode, "width"));
                col.setViewctrl(getAttribute(colNode, "viewctrl"));
                col.setEditctrl(getAttribute(colNode, "editctrl"));
                col.setLock(getAttribute(colNode, "lock"));
                col.setChange(getAttribute(colNode, "change"));
                col.setDatatype(getAttribute(colNode, "datatype"));
                col.setShowMask(getAttribute(colNode, "showmask"));
                columns.addElement(col);
            //}

        }

        return columns;

    }

    public Vector getTitles(){
      Vector titles = new Vector();
      List  titleNodeList = super.getChildrenNodes(TableTitle,"Title");

//      //System.out.println("取得标签组个数:");
//      //System.out.println(titleNodeList.getLength());
      for(int i=0; i<titleNodeList.size(); i++){
          Element tempTitle = (Element)titleNodeList.get(i);
          //if (tempTitle.getNodeType() == Node.ELEMENT_NODE) {
            XmlTitle title = new XmlTitle();
            title.setAlign(getAttribute(tempTitle,"align"));
            title.setFontname(getAttribute(tempTitle,"fontname"));
            title.setFontsize(getAttribute(tempTitle,"fontsize"));

            List labelList =  super.getChildrenNodes(tempTitle,"Label");

            Vector vLabels = new Vector();
            for(int j=0;j<labelList.size();j++){
              Element tempLabel = (Element)labelList.get(j);
              //if(tempLabel.getNodeType() == Node.ELEMENT_NODE){
                XmlLabel label = new XmlLabel();
                label.setCaption(getAttribute(tempLabel,"caption"));
                vLabels.addElement(label);
                ////System.out.println(label.getCaption());
              //}
            }
            title.setLabels(vLabels);
            titles.addElement(title);
          //}
      }
      return titles;
    }

    public String getValueByName(String name){
      if(name==null || name.equals(""))
        return "";
      String value = "";
      List metaNodeList = super.getChildrenNodes(Table,"Meta");
//      //System.out.println("取得参数个数:"+metaNodeList.getLength());

     for(int j=0;j<metaNodeList.size();j++){
        Element tempLabel = (Element)metaNodeList.get(j);
        //if(tempLabel.getNodeType() == Node.ELEMENT_NODE){
          String curName = this.getAttribute(tempLabel,"name");
//          //System.out.println(name);
          if(curName != null && curName.equals(name))
            value = this.getAttribute(tempLabel,"value");
        //}
      }
      return value;
    }

   /* //取得现示列信息
    public Vector getTableView() {
        Vector tableView = new Vector();
        Node views = document.getElementsByTagName("TableView").item(0);

        //printNode(views);

        NodeList viewList = views.getChildNodes();
        ////System.out.println("len = "+ viewList.getLength());
        for (int i = 0; i < viewList.getLength(); i++) {
            XmlViewCol view = new XmlViewCol();
            Node viewNode = viewList.item(i);
            if (viewNode.getNodeType() == Node.ELEMENT_NODE) {
                //printNode(viewNode);

                view.setId(getAttribute(viewNode, "id"));
                view.setType(getAttribute(viewNode, "type"));
                tableView.addElement(view);
            }

        }

        return tableView;
    }
    */
    //public static void main(String[] args) {
    //    HelpMetaManager test1 = new HelpMetaManager("D:\\JWorkspace\\CommCtrl\\CommonHelp\\xml\\HelpMeta.xml");
    //}
}
