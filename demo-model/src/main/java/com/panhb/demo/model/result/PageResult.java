package com.panhb.demo.model.result;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.panhb.demo.model.page.PageInfo;

public class PageResult<T> extends PageInfo{
	
	private static final long serialVersionUID = -2797911553763185618L;
	
	private List<T> list;
	
	public PageResult(Integer pageNo,Integer pageSize,Integer totalRow,List<T> list){
		setPageNo(pageNo);
		setPageSize(pageSize);
		setTotalRow(totalRow);
		this.list = list;
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String toString(){
		return MoreObjects.toStringHelper(this)
				.add("pageNo", getPageNo())
				.add("pageSize", getPageSize())
				.add("totalRow", getTotalRow())
				.add("list", list)
				.toString();
	}
	
}
