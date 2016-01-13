package dwz.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import dwz.dal.BaseMapper;
import dwz.persistence.beans.ResChapter;

@Repository
public interface ResChapterMapper extends BaseMapper<ResChapter, Integer> {

	List<ResChapter> findByBook(Integer bookId);

	// 查询
	List<ResChapter> findPageBreakByCondition(
			@Param("keywords") String keywords,
			@Param("bookId") Integer bookId,
			@Param("startInsertDate") Date startInsertDate,
			@Param("endInsertDate") Date endInsertDate, RowBounds rb);

	Integer findNumberByCondition(@Param("keywords") String keywords,
			@Param("bookId") Integer bookId,
			@Param("startInsertDate") Date startInsertDate,
			@Param("endInsertDate") Date endInsertDate);
	
	List<ResChapter> findNotInitContent();
	
}
