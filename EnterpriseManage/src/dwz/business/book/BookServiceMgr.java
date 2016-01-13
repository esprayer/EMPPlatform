package dwz.business.book;

import java.io.File;
import java.util.List;
import java.util.Map;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.persistence.BaseConditionVO;

public interface BookServiceMgr extends BusinessObjectServiceMgr {
	Book getBook(int id);
	void addBook(Book book);
	void updBook(Book book);
	void delBook(int id);
	
	List<Book> searchBook(Map<BookSearchFields, String> criterias, String orderField,
			int startIndex, int count);

	int searchBookNum(Map<BookSearchFields, String> criterias);
	
	Chapter getChapter(int id);
	void addChapter(Chapter chapter, File file);
	void updChapter(Chapter chapter);
	void delChapter(int id);
	
	List<Chapter> listChapters(int bookId);
	List<Chapter> searchChapters(ChapterConditionVO vo);
	Integer searchChaptersNum(ChapterConditionVO vo);
	
	void initChaptersContent();
}
