package com.panhb.demo.service.base;

import java.io.Serializable;
import java.util.List;

import com.panhb.demo.dao.base.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;

/**
 * @author panhb
 *
 */
@Transactional
public interface BaseService<R extends BaseRepository,T,ID extends Serializable> {

    public T findById(Long id);

    public List<T> findAll();

    public T save(T entity);

    public boolean exists(Long id);

    public long count();

    public void delete(Long id);

    public void delete(T entity);

    public void delete(List<T> entities);

    public void deleteAll();
	
	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,Class<T> mappedClass);

	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass);

	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,String sort,Class<T> mappedClass);

	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass);
}
