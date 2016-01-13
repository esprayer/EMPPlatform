package dwz.business.book.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.business.book.Book;
import dwz.business.book.BookSearchFields;
import dwz.business.book.BookServiceMgr;
import dwz.business.book.Chapter;
import dwz.business.book.ChapterConditionVO;
import dwz.common.util.DateUtil;
import dwz.common.util.ExecUtils;
import dwz.common.util.FileUtils;
import dwz.framework.config.AppConfiguration;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.beans.ResBook;
import dwz.persistence.beans.ResChapter;
import dwz.persistence.mapper.ResBookMapper;
import dwz.persistence.mapper.ResChapterMapper;

@Transactional(rollbackFor = Exception.class)
@Service("bookServiceMgr")
public class BookServiceMgrImpl extends AbstractBusinessObjectServiceMgr
		implements BookServiceMgr {
	@Autowired
	private ResBookMapper bookMapper;
	@Autowired
	private ResChapterMapper chapterMapper;

	public Book getBook(int id) {
		ResBook po = bookMapper.load(id);
		return new Book(po);
	}

	public void addBook(Book book) {
		Date now = new Date();
		ResBook po = book.getResBook();
		po.setInsertDate(now);
		po.setUpdateDate(now);
		bookMapper.insert(po);
	}

	public void updBook(Book book) {
		Date now = new Date();
		ResBook po = bookMapper.load(book.getId());
		po.setSn(book.getSn());
		po.setNameCn(book.getNameCn());
		po.setNameEn(book.getNameEn());
		po.setPublish(book.getPublish());
		po.setPublishDate(book.getPublishDate());
		po.setUpdateDate(now);
		bookMapper.update(po);
	}

	public void delBook(int id) {
		bookMapper.delete(id);
	}

	public List<Book> searchBook(Map<BookSearchFields, String> criterias,
			String orderField, int startIndex, int count) {

		List<Book> bos = new ArrayList<Book>();
		List<ResBook> pos = bookMapper.findAll();
		for (ResBook po : pos) {
			bos.add(new Book(po));
		}
		return bos;
	}

	public int searchBookNum(Map<BookSearchFields, String> criterias) {

		return 0;
	}

	public Chapter getChapter(int id) {
		ResChapter po = chapterMapper.load(id);
		return new Chapter(po);
	}

	public void addChapter(Chapter chapter, File file) {
		if (file==null || !file.exists()) return;
		Date now = new Date();
		AppConfiguration config = getAppConfig();
		
		String fileName = file.getName();
		String name = fileName, ext = "";
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex >= 0) {
			name = fileName.substring(0, lastIndex);
			ext = fileName.substring(lastIndex + 1).toLowerCase();
		}
		
		if (!"pdf".equals(ext)) return;
		
		String dirPath = DateUtil.date2String(now, "/yyyy/MM");
		String bookPath = dirPath + "/" + name+"."+ext;
		File bookDir = new File(config.getBookRootPath()+dirPath);
		if (!bookDir.exists()) bookDir.mkdirs();
		
		System.out.println("renameTo: "+config.getBookRootPath() + bookPath);
		file.renameTo(new File(config.getBookRootPath() + bookPath));

		// save to database
		ResChapter po = chapter.getResChapter();
		po.setPath(bookPath);
		po.setInsertDate(now);
		po.setUpdateDate(now);
		chapterMapper.insert(po);
		
		// pdf2json
		String destDir = config.getStaticContentPath() + dirPath + "/" + name;
		ExecUtils.pdf2json(config.getBookRootPath() + bookPath, destDir);
	}

	public void updChapter(Chapter chapter) {
		Date now = new Date();
		ResChapter po = chapterMapper.load(chapter.getId());
		po.setAuthorId(chapter.getAuthorId());
		po.setTranslator(chapter.getTranslator());
		po.setChapterNo(chapter.getChapterNo());
		po.setKeywords(chapter.getKeywords());
		po.setNameCn(chapter.getNameCn());
		po.setNameEn(chapter.getNameEn());
		po.setSummary(chapter.getSummary());
		po.setStartPageNo(chapter.getStartPageNo());
		po.setPageCount(chapter.getPageCount());
		
		po.setUpdateDate(now);
		chapterMapper.update(po);
	}

	public void delChapter(int id) {
		chapterMapper.delete(id);
	}

	public List<Chapter> listChapters(int bookId) {
		List<Chapter> bos = new ArrayList<Chapter>();
		List<ResChapter> pos = chapterMapper.findByBook(bookId);
		for (ResChapter po : pos) {
			bos.add(new Chapter(po));
		}
		return bos;
	}

	public List<Chapter> searchChapters(ChapterConditionVO vo) {
		
		List<Chapter> bos = new ArrayList<Chapter>();
		RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
		List<ResChapter> pos = chapterMapper.findPageBreakByCondition(vo.getKeywords(), vo.getBookId(), vo.getStartDate(), vo.getEndDate(), rb);
		for (ResChapter po : pos) {
			bos.add(new Chapter(po));
		}
		return bos;
	}
	

	public Integer searchChaptersNum(ChapterConditionVO vo) {

		Integer count = chapterMapper.findNumberByCondition(vo.getKeywords(),
				vo.getBookId(), vo.getStartDate(), vo.getEndDate());

		return count;
	}

	public void initChaptersContent() {
		List<ResChapter> pos = chapterMapper.findNotInitContent();
		Date now = new Date();
		AppConfiguration config = getAppConfig();
		
		System.out.println("############# Start initChaptersContent: " + now + " size:" + pos.size());
		for (ResChapter po : pos) {
			String path = po.getPath();
			if (path != null && path.endsWith(".pdf")) {
				int lastIndex = path.lastIndexOf(".");
				String jsonDirPath = config.getStaticContentPath() + path.substring(0, lastIndex);

				File txtFile = new File(jsonDirPath + "/p.txt");
				
				System.out.println("jsonDirPath: "+txtFile.getPath());
				if (txtFile.exists()) {
					String content = FileUtils.getFileContent(txtFile.getPath());
					po.setContent(content);
					po.setInitContent(true);
					po.setUpdateDate(now);
					
					chapterMapper.updateSelective(po);
				} else {
					ExecUtils.pdf2json(config.getBookRootPath() + path, jsonDirPath);
				}
				
			}
		}
	}
}
