package dwz.business.book;

import dwz.persistence.BaseConditionVO;

public class ChapterConditionVO extends BaseConditionVO {
	private Integer bookId;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	
	
}
