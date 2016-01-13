package com.mrp.persistence.queryAnalyse.pageQuery.bean;

import dwz.dal.object.AbstractDO;

public class EMPPageQuery extends AbstractDO{
	/**
	 * 总条数
	 */
	private int totalCount;

	/**
	 * 每页显示多少条
	 */
	private int numPerPage = 20;
	
	/**
	 * 页标数字多少个
	 */
	private int pageNumShown;

	/**
	 * 当前页数
	 * @return
	 */
	private int pageNum;
	
	// 可以将dwz传过来的pageNum、numPerPage进行初始化
	public EMPPageQuery() {
		this.numPerPage = 20;
	}
	
	// 可以将dwz传过来的pageNum、numPerPage进行初始化
	public EMPPageQuery(int pageNum, int numPerPage) {
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}
	 
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		int temp = 0;
		if (this.numPerPage != 0 && totalCount % this.numPerPage != 0) {
			temp++;
		}
		if(this.pageNum == 0) this.pageNum = 1;
		this.pageNumShown = totalCount / this.numPerPage + temp;
		this.totalCount = totalCount;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}

