/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-07-29 17:54:24 by code generator
 */
package dwz.business.book;

import java.util.Date;

import dwz.framework.sys.business.AbstractBusinessObject;
import dwz.persistence.beans.ResBook;
import dwz.persistence.beans.ResChapter;

public class Chapter extends AbstractBusinessObject{
	private static final long serialVersionUID = 1L;
	private ResChapter resChapter;
	
	/* generateConstructor */
	public Chapter() {
		this.resChapter = new ResChapter();
	}
	public Chapter(ResChapter resChapter) {
		this.resChapter = resChapter;
	}
	public ResChapter getResChapter(){
		return this.resChapter;
	}

	
	public Integer getId() {
		return this.resChapter.getId();
	}
	public void setId(Integer id) {
		this.resChapter.setId(id);
	}
	
	public Integer getBookId() {
		Integer value = this.resChapter.getBookId();
		return value != null ? value : 0;
	}

	public void setBookId(Integer bookId) {
		this.resChapter.setBookId(bookId);
	}
	
	public int getAuthorId() {
		Integer value = this.resChapter.getAuthorId();
		return value != null ? value : 0;
	}

	public void setAuthorId(int authorId) {
		this.resChapter.setAuthorId(authorId);
	}
	
	public String getTranslator() {
		return this.resChapter.getTranslator();
	}

	public void setTranslator(String translator) {
		this.resChapter.setTranslator(translator);
	}
	
	public int getChapterNo() {
		Integer value = this.resChapter.getChapterNo();
		return value != null ? value : 0;
	}

	public void setChapterNo(int chapterNo) {
		this.resChapter.setChapterNo(chapterNo);
	}
	
	public String getNameCn() {
		return this.resChapter.getNameCn();
	}

	public void setNameCn(String nameCn) {
		this.resChapter.setNameCn(nameCn);
	}
	
	public String getNameEn() {
		return this.resChapter.getNameEn();
	}

	public void setNameEn(String nameEn) {
		this.resChapter.setNameEn(nameEn);
	}
	
	public String getSummary() {
		return this.resChapter.getSummary();
	}

	public void setSummary(String summary) {
		this.resChapter.setSummary(summary);
	}
	
	public String getPath() {
		return this.resChapter.getPath();
	}

	public void setPath(String path) {
		this.resChapter.setPath(path);
	}
	
	public String getPdf2jsonUri(){
		String path = getPath();
		
		if (path == null || path.length() == 0) return null;
		
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex >= 0) {
			return getAppConfig().getStaticContentUri() + path.substring(0, lastIndex);
		}
		
		return null;
	}
	public String getKeywords() {
		return this.resChapter.getKeywords();
	}

	public void setKeywords(String keywords) {
		this.resChapter.setKeywords(keywords);
	}
	
	public int getStartPageNo() {
		Integer value = this.resChapter.getStartPageNo();
		return value != null ? value : 0;
	}

	public void setStartPageNo(int startPageNo) {
		this.resChapter.setStartPageNo(startPageNo);
	}
	
	public int getPageCount() {
		Integer value = this.resChapter.getPageCount();
		return value != null ? value : 0;
	}

	public void setPageCount(int pageCount) {
		this.resChapter.setPageCount(pageCount);
	}
	
	public Date getInsertDate() {
		return this.resChapter.getInsertDate();
	}

	public void setInsertDate(Date insertDate) {
		this.resChapter.setInsertDate(insertDate);
	}
	
	public Date getUpdateDate() {
		return this.resChapter.getUpdateDate();
	}

	public void setUpdateDate(Date updateDate) {
		this.resChapter.setUpdateDate(updateDate);
	}

	public String getBookNameCn() {
		return resChapter.getBookNameCn();
	}

	public String getBookNameEn() {
		return resChapter.getBookNameEn();
	}
}

