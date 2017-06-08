package com.panhb.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
//		registry.addInterceptor(sysInfoInterceptor).excludePathPatterns("/404","/500");
//		registry.addInterceptor(licenseSessionInterceptor).addPathPatterns("/404","/500");
//		registry.addInterceptor(apiInterceptor).addPathPatterns("/webbox/v5/**");
		super.addInterceptors(registry);
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/i18n/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/i18n/");
		super.addResourceHandlers(registry);
	}
}
