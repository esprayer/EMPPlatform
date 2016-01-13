package jservice.jdal.classes.query.pub.parse;

import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class ExteriorQueryStru implements Serializable {
  public static String _FIELDOBJECT_ = "FieldObject";
  public static String _TABLEOBJECT_ = "TableObject";
  public static String _WHEREOBJECT_ = "WhereObject";
  public static String _GROUPOBJECT_ = "GroupObject";
  public static String _HAVINGOBJECT_= "HavingObject";
  public static String _SQLOBJECT_ = "SQLObject";
  public static String _FieldsStru_ = "FieldsStru";
  public static String _ECCQueryID_ = "EccGanerQuery";

  //列字段结构，装载QueryFieldStru
  private Hashtable FieldStru = new Hashtable();
  private Vector Field = new Vector();
  private Vector Table = new Vector();
  public String Where="";
  public String Group="";
  public String Order="";
  public String Having="";
  private String SQLString="";
  /**
   * 无参构造
   */
  public ExteriorQueryStru() {
  }

  /**
   * 增加列结构
   * @param key String
   * @param val QueryFieldStru
   */
  public void addFieldStru(String key,QueryFieldStru val){
    //设置列结构
    this.FieldStru.put(key,val);
    //同时增加字段值
    this.addField(key);
  }
  /**
   * 获取列结构
   * @return Hashtable
   */
  public Hashtable getFieldStru(){
    return this.FieldStru;
  }
  /**
   * 取某列结构
   * @param key String
   * @return QueryFieldStru
   */
  public QueryFieldStru getFieldStru(String key){
    return (QueryFieldStru)this.FieldStru.get(key);
  }
  /**
   * 增加一个或多个列名
   * @param fieldname String
   */
  public void addField(String fieldname){
    String[] field = fieldname.split(",");
    for(int i=0;i<field.length;i++){
      field[i] = field[i]==null?"":field[i].trim();
      if (!field[i].equals(""))
        this.Field.add(field[i]);
    }
  }
  /**
   *
   * @param index int
   * @param fieldname String
   */
  public void addField(int index,String fieldname){
    this.Field.add(index,fieldname);
  }
  /**
   * 取得字段列表
   * @return Vector
   */
  public Vector getField(){
    return this.Field;
  }
  /**
   * 取某一列
   * @param index int
   * @return String
   */
  public String getField(int index){
    return this.Field.get(index).toString();
  }
  /**
   * 取主键列
   * @return String[]
   */
  public Vector getKeyField(){
    Vector keyField = new Vector();
    Object[] field = (Object[])this.Field.toArray();
    QueryFieldStru Stru;
    for (int i=0;i<field.length;i++){
      Stru = (QueryFieldStru)this.FieldStru.get(field[i].toString().trim());
      if (!Stru.isKey()) continue;
      keyField.add(field[i].toString().trim());
    }
    return keyField;
  }
  /**
   * 取所有列名称
   * @return String[]
   */
  public Object[] getFields(){
    return(Object[])this.Field.toArray();
  }
  /**
   *
   * @param table String
   */
  public void addTable(String tablename){
    String[] table = tablename.split(",");
    for(int i=0;i<table.length;i++){
      table[i] = table[i]==null?"":table[i].trim();
      String tablen="",tablea="";
      if (table[i].toUpperCase().indexOf("AS")>0){
        tablen = table[i].substring(0,table[i].toUpperCase().indexOf("AS"));
        tablea = table[i].substring(table[i].toUpperCase().indexOf("AS")+2);
      }else if (table[i].toUpperCase().indexOf(" ")>0){
        tablen = table[i].substring(0,table[i].toUpperCase().indexOf(" "));
        tablea = table[i].substring(table[i].toUpperCase().indexOf(" ")+1);
      }else{
        tablen = tablea = table[i];
      }
      tablen = tablen.trim();
      tablea = tablea.trim();
      this.addTable(new TableStru(tablen,tablea));
    }
  }
  /**
   *
   * @param table String
   */
  public void addTable(String tablename, boolean bFlag){
    if(bFlag){
      addTable(tablename);
    }else{
      this.addTable(new TableStru(tablename,""));
    }
  }
  /**
   * 增加表名
   * @param table TableStru
   */
  public void addTable(TableStru table){
    this.Table.add(table);
  }
  /**
   *
   * @param i int
   * @return TableStru
   */
  public Vector getTable(){
    return Table;
  }
  /**
   * 取所有表名
   * @return String
   */
  public String getTables(){
    String vsTable = "";
    for (int i=0;i<Table.size();i++){
      TableStru table= (TableStru)Table.get(i);
      //表名与别名相同
      if (table.TableA.equals(table.TableN)){
        vsTable += ","+table.TableA;
      }else{
        vsTable += ","+table.TableA +" " +table.TableN;
      }
    }
    if (vsTable.length()>0) vsTable = vsTable.substring(1);
    return vsTable;
  }
  /**
   * 组织成完整SQL
   * @return String
   */
  public String getSql(){
    String column = this.getSQLField();
    SQLString = "select "+column+" from "+getTables();
    if (!Where.equals("")){
      SQLString+=" where "+Where;
    }
    if (!Group.equals("")){
      SQLString+=" group by "+Group;
    }
    if (!Having.equals("")){
      SQLString+=" having "+Having;
    }
    if (!Order.equals("")){
      SQLString+=" order by "+Order;
    }
    return SQLString;
  }
  /**
   * 解析出SELECT 字段
   * @return String
   */
  private String getSQLField(){
    String column = "";
    Object[] field = (Object[])this.Field.toArray();
    if(field.length == 0){
      return "";
    }
    QueryFieldStru Stru;
    for (int i=0;i<field.length;i++){
      Stru = (QueryFieldStru)this.FieldStru.get(field[i].toString().trim());
      if (Stru.isCounted()) continue;
      //如果表达式和列名不等
      if (Stru.getField().equals(Stru.getFieldexp())){
        column += ","+Stru.getField();
      }else{
        column += ","+Stru.getFieldexp()+" as "+Stru.getField();
      }
    }
    return column.substring(1);
  }
  /**
   * 将SQL分行格式化
   * @return List
   */
  public List getSQLObject(){
    List list = new ArrayList();
    int len = 60;
    String string = "select "+this.getSQLField();
    String separator[]={","," "};
    formatSql(list,string,separator,len);
    string = " from "+getTables();
    formatSql(list,string,",",len);
    if (!Where.equals("")){
      string=" where "+Where;
      String separator2[]={" ","AND"};
      formatSql(list,string,separator2,len);
    }
    if (!Group.equals("")){
      string=" group by "+Group;
      formatSql(list,string,",",len);
    }
    if (!Having.equals("")){
      string=" having "+Having;
      formatSql(list,string,",",len);
    }
    if (!Order.equals("")){
      string=" order by "+Order;
      formatSql(list,string,",",len);
    }
    return list;
  }
  /**
   * 将字符按长最大长度切割
   * @param list List
   * @param string String
   * @param separator String
   * @param len int
   */
  private static void formatSql(List list, String string, String[] separator, int len) {
    //先进行字符转换
    string = string.trim();
//    string = ESPConvertSQLToSap.replaceAll(_ECCQueryID_,string);
    String[] value;
    while(string.length()>0){
      value = new String[1];
      if (string.length()>=len){
        value[0] = string.substring(0,len).trim();
        int index = value[0].toUpperCase().lastIndexOf(separator[0]);
        if(index==0) index = separator[0].length();
        if (index<0) index= value[0].toUpperCase().lastIndexOf(separator[1]);
        if(index==0) index = separator[1].length();
        if (index<0) index = value[0].length();
        string = string.trim();
        value[0] = string.substring(0,index);
        string = string.substring(index);
      }else{
        value[0] = string;
        string = "";
      }
      System.out.println(value[0]);
      list.add(value);
    }
  }
  private static void formatSql(List list, String string, String separator, int len) {
    //先进行字符转换
//    string = ESPConvertSQLToSap.replaceAll(_ECCQueryID_,string);
    String[] value;
    while(string.length()>0){
      value = new String[1];
      if (string.length()>=len){
        value[0] = string.substring(0,len);
        int index = value[0].toUpperCase().lastIndexOf(separator);
        if (index<=0) index = value[0].length();
        value[0] = string.substring(0,index);
        string = string.substring(index);
      }else{
        value[0] = string;
        string = "";
      }
      System.out.println(value[0]);
      list.add(value);
    }
  }
  /**
   * 取SAP查询所需要的字段结构
   * @return object
   */
  public Object getFieldSAPStru(){
    List list = new ArrayList();
    String[] property;
    Vector field = this.getField();
    for(int i=0;i<field.size();i++){
      property = new String[4];
      QueryFieldStru stru = this.getFieldStru(field.get(i).toString());
      if (stru.isCounted()) continue;
      property[0] = stru.getField();
      property[1] = stru.getType().equals("N")?"P":stru.getType();
      property[2] = property[1].equals("P")?"16":"100";
      property[3] = property[1].equals("P")?"6":"0";
      list.add(property);
    }
    return list;
  }
//  /**
//   * 将string中的的字符根据package文件进行会替换
//   * @param string String
//   * @return String
//   */
//  public static String replaceAll(String string){
//    List list = PackageStub.getContentVector(_ECCQueryID_+"_Replace");
//    StubObject so=null;
//    for (int i=0;i<list.size();i++){
//      so = (StubObject) list.get(i);
//      String regex = so.getString("regex","");
//      String replacement = so.getString("replacement","");
//      boolean sensitive = so.getString("sensitive","true").equals("true");
//      //是否区分大写小
//      if (sensitive){
//        string = string.replaceAll(regex, replacement);
//      }else{
//        string = string.replaceAll(regex, replacement);
//        while (string.toUpperCase().indexOf(regex.toUpperCase()) > 0) {
//          int index = string.toUpperCase().indexOf(regex.toUpperCase());
//          string = string.substring(0, index) + replacement +
//              string.substring(index + regex.length());
//        }
//      }
//    }
//    return string;
//  }
  /**
   * 根据传入参数不同，取出不同对角
   * @param method String
   * @return String
   */
  public Object getObject(String method){
    Object object = "";
    if (method.equals(this._FIELDOBJECT_)){
      object = this.getSQLField();
    }else if(method.equals(this._TABLEOBJECT_)){
      object = this.getTables();
    }else if(method.equals(this._WHEREOBJECT_)){
      object = this.Where;
    }else if(method.equals(this._GROUPOBJECT_)){
      object = this.Group;
    }else if(method.equals(this._HAVINGOBJECT_)){
      object = this.Having;
    }else if(method.equals(this._SQLOBJECT_)){
      object = this.getSQLObject();
    }else if(method.equals(this._FieldsStru_)){
      object = getFieldSAPStru();
    }
    return object;
  }
  /**
   * 内部类,处理表名
   * <p>Title: </p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2009</p>
   * <p>Company: </p>
   * @author not attributable
   * @version 1.0
   */
  public class TableStru implements Serializable{
    public String TableN = "";//原表名
    public String TableA = "";//表别名
    public TableStru(String tablen,String tablea){
      this.TableA = tablen;
      this.TableN = tablea;
    }
    public TableStru(){

    }
  }
}
