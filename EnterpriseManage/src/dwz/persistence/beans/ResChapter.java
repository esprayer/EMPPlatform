package dwz.persistence.beans;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dwz.dal.object.AbstractDO;

public class ResChapter extends AbstractDO {
	private Integer id;
	private Integer bookId;
	private String bookNameCn;
	private String bookNameEn;
	private Integer authorId;
	private String translator;
	private Integer chapterNo;
	private String nameCn;
	private String nameEn;
	private String summary;
	private String path;
	private String keywords;
	private String content;
	private Boolean initContent;
	private Integer startPageNo;
	private Integer pageCount;
	private java.util.Date insertDate;
	private java.util.Date updateDate;

	public ResChapter() {
	}

	public ResChapter(Integer id) {
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return this.id;
	}

	public void setBookId(Integer value) {
		this.bookId = value;
	}

	public Integer getBookId() {
		return this.bookId;
	}

	public void setAuthorId(Integer value) {
		this.authorId = value;
	}

	public Integer getAuthorId() {
		return this.authorId;
	}

	public void setTranslator(String value) {
		this.translator = value;
	}

	public String getTranslator() {
		return this.translator;
	}

	public void setChapterNo(Integer value) {
		this.chapterNo = value;
	}

	public Integer getChapterNo() {
		return this.chapterNo;
	}

	public void setNameCn(String value) {
		this.nameCn = value;
	}

	public String getNameCn() {
		return this.nameCn;
	}

	public void setNameEn(String value) {
		this.nameEn = value;
	}

	public String getNameEn() {
		return this.nameEn;
	}

	public void setSummary(String value) {
		this.summary = value;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setPath(String value) {
		this.path = value;
	}

	public String getPath() {
		return this.path;
	}

	public void setKeywords(String value) {
		this.keywords = value;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getInitContent() {
		return initContent;
	}

	public void setInitContent(Boolean initContent) {
		this.initContent = initContent;
	}

	public void setStartPageNo(Integer value) {
		this.startPageNo = value;
	}

	public Integer getStartPageNo() {
		return this.startPageNo;
	}

	public void setPageCount(Integer value) {
		this.pageCount = value;
	}

	public Integer getPageCount() {
		return this.pageCount;
	}

	public void setInsertDate(java.util.Date value) {
		this.insertDate = value;
	}

	public java.util.Date getInsertDate() {
		return this.insertDate;
	}

	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	private ResBook resBook;

	public void setResBook(ResBook resBook) {
		this.resBook = resBook;
	}

	public ResBook getResBook() {
		return resBook;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("BookId", getBookId())
				.append("AuthorId", getAuthorId())
				.append("Translator", getTranslator())
				.append("ChapterNo", getChapterNo())
				.append("NameCn", getNameCn()).append("NameEn", getNameEn())
				.append("Summary", getSummary()).append("Path", getPath())
				.append("Keywords", getKeywords())
				.append("StartPageNo", getStartPageNo())
				.append("PageCount", getPageCount())
				.append("InsertDate", getInsertDate())
				.append("UpdateDate", getUpdateDate()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof ResChapter == false)
			return false;
		if (this == obj)
			return true;
		ResChapter other = (ResChapter) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String getBookNameCn() {
		return bookNameCn;
	}

	public void setBookNameCn(String bookNameCn) {
		this.bookNameCn = bookNameCn;
	}

	public String getBookNameEn() {
		return bookNameEn;
	}

	public void setBookNameEn(String bookNameEn) {
		this.bookNameEn = bookNameEn;
	}

}
