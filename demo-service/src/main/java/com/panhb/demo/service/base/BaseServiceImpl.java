package com.panhb.demo.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.panhb.demo.utils.SpringUtils;
import org.springframework.stereotype.Service;

import com.panhb.demo.dao.base.BaseRepository;
import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author panhb
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<R extends BaseRepository,T,ID extends Serializable> implements BaseService<R,T,ID>{

    private BaseRepository baseRepository;

    private BaseRepository getBaseRepository(){
        Class<R> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<R>)p[0];
        }
        baseRepository = SpringUtils.getBean(entityClass);
        return baseRepository;
    }


    @Override
    public T findById(Long id) {
        return (T)getBaseRepository().findOne(id);
    }

    @Override
    public List<T> findAll() {
        return getBaseRepository().findAll();
    }

    @Override
    public T save(T entity) {
        return (T)getBaseRepository().save(entity);
    }

    @Override
    public boolean exists(Long id) {
        return getBaseRepository().exists(id);
    }

    @Override
    public long count() {
        return getBaseRepository().count();
    }

    @Override
    public void delete(Long id) {
        getBaseRepository().delete(id);
    }

    @Override
    public void delete(T entity) {
        getBaseRepository().delete(entity);
    }

    @Override
    public void delete(List<T> entities) {
        getBaseRepository().delete(entities);
    }

    @Override
    public void deleteAll() {
        getBaseRepository().deleteAll();
    }

    @Override
    public PageResult<T> pageBySql(String sql, PageInfo pageInfo, Class<T> mappedClass){
		return pageBySql(sql,new Object[]{},pageInfo,mappedClass);
	}

    @Override
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass){
		return pageBySql(sql,objs,pageInfo,"",mappedClass);
	}

    @Override
    public PageResult<T> pageBySql(String sql,PageInfo pageInfo,String sort,Class<T> mappedClass){
        return pageBySql(sql,new Object[]{},pageInfo,sort,mappedClass);
    }

    @Override
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass){
		return getBaseRepository().pageBySql(sql, objs, pageInfo, sort, mappedClass);
	}

}
