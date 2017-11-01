package com.panhb.demo.model.page;

import com.google.common.base.MoreObjects;
import com.panhb.demo.model.base.BaseModel;

/**
 * @author panhb
 */
public class PageInfo extends BaseModel{
	
	private static final long serialVersionUID = -5406259081088515055L;
	
	private Integer pageNo;
	private Integer pageSize;
	private Integer totalRow;
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
	}
	
	public PageInfo(){}
	
	public PageInfo(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public PageInfo(Integer pageNo, Integer pageSize, Integer totalRow) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalRow = totalRow;
	}

	@Override
	public String toString(){
		return MoreObjects.toStringHelper(this)
				.add("pageNo", pageNo)
				.add("pageSize", pageSize)
				.add("totalRow", totalRow)
				.toString();
	}
	
}
