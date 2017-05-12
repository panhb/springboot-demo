package com.panhb.demo.dao.base;

import java.io.Serializable;

import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;

/**
 * @author panhb
 *
 */
public interface BaseRepository<T,ID extends Serializable> {
	
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass);
}
