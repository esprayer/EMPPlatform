package com.report.table.jxml;


/**
 * 此处插入类型描述。
 * 创建日期：(2002-1-22 14:30:24)
 * @author：Lubo
 */
public class XmlTable extends XmlElement {

    private String caption;
    private String date;
    private String unit;
    private String jedw;
    private String description;

    /**
     * TableModel 构造子注解。
     */
    public XmlTable() {
	    super();
    }
    public String getCaption() {
        return caption;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getJedw() {
        return jedw;
    }
    public String getUnit() {
        return unit;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setJedw(String jedw) {
        this.jedw = jedw;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
