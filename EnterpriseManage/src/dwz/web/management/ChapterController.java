package dwz.web.management;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dwz.business.book.Book;
import dwz.business.book.BookServiceMgr;
import dwz.business.book.Chapter;
import dwz.common.util.FileUtils;
import dwz.framework.config.AppConfiguration;
import dwz.web.BaseController;

@Controller
@RequestMapping(value = "/management/chapter")
public class ChapterController extends BaseController{
	@Autowired
	private BookServiceMgr bookMgr;
	
	@RequestMapping("/list/{bookId}")
	public String list(@PathVariable("bookId") int bookId, Model model) {
		List<Chapter> chapters = bookMgr.listChapters(bookId);
		model.addAttribute(chapters);
		return "/management/chapter/list";
	}

	@RequestMapping("/add/{bookId}")
	public String add(@PathVariable("bookId") int bookId, Model model) {
		Book book = bookMgr.getBook(bookId);
		model.addAttribute(book);
		return "/management/chapter/add";
	}

	@RequestMapping("/edit/{chapterId}")
	public String edit(@PathVariable("chapterId") int chapterId, Model model) {

		Chapter chapter = bookMgr.getChapter(chapterId);
		Book book = bookMgr.getBook(chapter.getBookId());
		model.addAttribute(chapter);
		model.addAttribute(book);

		return "/management/chapter/edit";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(Chapter chapter, @RequestParam("file") CommonsMultipartFile mFile, HttpServletRequest request) {
		if (!mFile.isEmpty()) {
			AppConfiguration config = AppConfiguration.getInstance();
			String tmpPath = config.getTempPath();
			String ext = FileUtils.getFileExt(mFile.getOriginalFilename());
			String filePath = tmpPath + "/" +System.currentTimeMillis() + "."+ext;
			
			File file = new File(filePath); // 新建一个文件
			try {
				mFile.getFileItem().write(file); // 将上传的文件写入新建的文件中
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			bookMgr.addChapter(chapter, file);
			return ajaxDoneSuccess(getMessage("msg.operation.success"));
		} else {
			return ajaxDoneError(getMessage("msg.operation.failure"));
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(Chapter chapter) {
		bookMgr.updChapter(chapter);
		return ajaxDoneError(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{chapterId}")
	public ModelAndView delete(@PathVariable("chapterId") int chapterId) {

		bookMgr.delChapter(chapterId);

		return ajaxDoneError(getMessage("msg.operation.success"));
	}
}
