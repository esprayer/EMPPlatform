/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-07-29 17:54:23 by code generator
 */
package dwz.business.book;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import dwz.framework.sys.business.AbstractBusinessObject;
import dwz.persistence.beans.ResBook;

public class Book extends AbstractBusinessObject {
	private static final long serialVersionUID = 1L;
	private ResBook resBook;

	/* generateConstructor */
	public Book() {
		this.resBook = new ResBook();
	}

	public Book(ResBook resBook) {
		this.resBook = resBook;
	}

	public ResBook getResBook() {
		return this.resBook;
	}

	public Integer getId() {
		return this.resBook.getId();
	}
	public void setId(Integer id) {
		this.resBook.setId(id);
	}

	public String getSn() {
		return this.resBook.getSn();
	}

	public void setSn(String sn) {
		this.resBook.setSn(sn);
	}

	public String getNameCn() {
		return this.resBook.getNameCn();
	}

	public void setNameCn(String nameCn) {
		this.resBook.setNameCn(nameCn);
	}

	public String getNameEn() {
		return this.resBook.getNameEn();
	}

	public void setNameEn(String nameEn) {
		this.resBook.setNameEn(nameEn);
	}

	public String getPublish() {
		return this.resBook.getPublish();
	}

	public void setPublish(String publish) {
		this.resBook.setPublish(publish);
	}

	public Date getPublishDate() {
		return this.resBook.getPublishDate();
	}

	public void setPublishDate(Date publishDate) {
		this.resBook.setPublishDate(publishDate);
	}

	public Date getInsertDate() {
		return this.resBook.getInsertDate();
	}

	public void setInsertDate(Date insertDate) {
		this.resBook.setInsertDate(insertDate);
	}

	public Date getUpdateDate() {
		return this.resBook.getUpdateDate();
	}

	public void setUpdateDate(Date updateDate) {
		this.resBook.setUpdateDate(updateDate);
	}

	private Set resChapters = new HashSet(0);

	public void setResChapters(Set resChapter) {
		this.resChapters = resChapter;
	}

	public Set getResChapters() {
		return resChapters;
	}

}
