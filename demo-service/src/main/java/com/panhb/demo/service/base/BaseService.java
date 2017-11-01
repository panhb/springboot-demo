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
public interface BaseService<R extends BaseRepository,T,ID extends Serializable> {

    /**
     * findById
     *
     * @param id
     * @return T
     */
    T findById(Long id);

    /**
     * findAll
     *
     * @return List<T>
     */
    List<T> findAll();

    /**
     * save
     *
     * @param entity
     * @return T
     */
    T save(T entity);

    /**
     * exists
     *
     * @param id
     * @return boolean
     */
    boolean exists(Long id);

    /**
     * count
     *
     * @return long
     */
    long count();

    /**
     * delete
     *
     * @param id
     */
    void delete(Long id);

    /**
     * delete
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * delete
     *
     * @param entities
     */
    void delete(List<T> entities);

    /**
     * deleteAll
     */
    void deleteAll();

    /**
     * pageBySql
     *
     * @param sql
     * @param pageInfo
     * @param mappedClass
     * @return PageResult<T>
     */
    PageResult<T> pageBySql(String sql,PageInfo pageInfo,Class<T> mappedClass);

    /**
     * pageBySql
     *
     * @param sql
     * @param objs
     * @param pageInfo
     * @param mappedClass
     * @return PageResult<T>
     */
    PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass);

    /**
     * pageBySql
     *
     * @param sql
     * @param pageInfo
     * @param sort
     * @param mappedClass
     * @return PageResult<T>
     */
    PageResult<T> pageBySql(String sql,PageInfo pageInfo,String sort,Class<T> mappedClass);

    /**
     * pageBySql
     *
     * @param sql
     * @param objs
     * @param pageInfo
     * @param sort
     * @param mappedClass
     * @return PageResult<T>
     */
    PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass);
}
