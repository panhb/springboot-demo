package com.panhb.demo.dao.base;

import java.io.Serializable;

import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author panhb
 *
 */
@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,Class<T> mappedClass);

	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,String sort,Class<T> mappedClass);

	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass);

	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass);
}
