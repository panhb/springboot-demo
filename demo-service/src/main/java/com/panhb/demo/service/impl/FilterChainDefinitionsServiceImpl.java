package com.panhb.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.panhb.demo.dao.PermissionRepository;
import com.panhb.demo.entity.Permission;
import com.panhb.demo.service.FilterChainDefinitionsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/9.
 */
@Service
public class FilterChainDefinitionsServiceImpl implements FilterChainDefinitionsService {

    private static final Logger log = LoggerFactory.getLogger(FilterChainDefinitionsServiceImpl.class);

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    //此处不能依赖permissionService,会导致依赖循环
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void reloadFilterChains() {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                Map<String, String> chains = Maps.newHashMap();
                List<Permission> list = permissionRepository.findAll();
                if(CollectionUtils.isNotEmpty(list)){
                    for(Permission p: list){
                        chains.put(p.getUrl(), "anon");
                    }
                }
                chains.put("/**", "authc");
                log.info("******权限控制:"+ JSON.toJSONString(chains));
                shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);
            }catch (Exception e){
                log.error("",e);
            }
        }
    }
}
