package com.panhb.demo.dao.base;

import java.io.Serializable;
import java.util.List;

import com.panhb.demo.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;
import com.panhb.demo.model.page.PageInfo;
import com.panhb.demo.model.result.PageResult;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID>{

	private static final Logger log = LoggerFactory.getLogger(BaseRepositoryImpl.class);
	
	/**
	 * 持久化上下文
	 */
	private final EntityManager entityManager;

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager = em;
	}

	public PageResult<T> pageBySql(String sql,Object[] objs,PageInfo pageInfo,String sort,Class<T> mappedClass){
		JdbcTemplate jdbcTemplate = SpringUtils.getBean(JdbcTemplate.class);
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
