package com.panhb.demo.service.base;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;

/**
 * @author panhb
 *
 */
@Transactional
public interface BaseService<T,ID extends Serializable> {
	
//	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,Class<T> mappedClass);
//
//	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass);
//
//	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass);
}
