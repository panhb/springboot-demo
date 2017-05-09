package com.panhb.demo.dao.base;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;

@Repository
public class BaseDaoImpl<T,ID extends Serializable> implements BaseDao<T,ID>{

	private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public PageResult<T> pageBySql(String sql,PageInfo pageInfo,Class<T> mappedClass){
		return pageBySql(sql,new Object[]{},pageInfo,mappedClass);
	}
	
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,Class<T> mappedClass){
		return pageBySql(sql,objs,pageInfo,"",mappedClass);
	}
	
	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass){
		int pageNum = pageInfo.getPageNo();
		int pageSize = pageInfo.getPageSize();
		String totalSql = "select count(*) from ("+sql+") total";
		int total = jdbcTemplate.queryForObject(totalSql,objs,Integer.class);
		log.info("count sql:"+totalSql);
		List<T> list = Lists.newArrayList();
		if(total > 0){
			String querySql = sql +" "+sort+ " limit "+(pageNum-1)*pageSize+" , "+pageSize;
			log.info("query sql:"+querySql);
			list = jdbcTemplate.query(querySql, objs,new BeanPropertyRowMapper<T>(mappedClass));
		}
		return new PageResult<T>(pageNum,pageSize,total,list);
	}

}
