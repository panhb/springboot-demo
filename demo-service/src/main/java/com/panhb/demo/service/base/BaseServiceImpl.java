package com.panhb.demo.service.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panhb.demo.dao.base.BaseDao;
import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;

@Service
public class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T,ID>{

	@Autowired
	private BaseDao<T,ID> baseDao;

	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,Class<T> mappedClass){
		return pageBySql(sql,new Object[]{},pageInfo,mappedClass);
	}
	
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass){
		return pageBySql(sql,objs,pageInfo,"",mappedClass);
	}
	
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass){
		return baseDao.pageBySql(sql, objs, pageInfo, sort, mappedClass);
	}

}
