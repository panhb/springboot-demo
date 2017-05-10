package com.panhb.demo.controller.base;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.panhb.demo.constants.Constants;
import com.panhb.demo.model.page.PageInfo;

@Controller
public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired  
	public  HttpServletRequest request; 
	
	@Autowired  
	public  HttpServletResponse response;
	
	@Autowired
    private MessageSource messageSource;
	
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public String getMessage(String code, Object[] args){
        return getMessage(code, args, "");
    }

    /**
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public String getMessage(String code,Object[] args,String defaultMessage){
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
	
	
	public void setCookie(String cookieName,String cookieValue,int expiry) {
		Cookie cookie = new Cookie(cookieName, cookieValue); 
		cookie.setMaxAge(expiry); 
		response.addCookie(cookie);
	}
	
	public Cookie getCookie(String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(cookieName)){
					return cookie;
				}
			}
		}
		return null;
	}
	
	public String getCookieValue(String cookieName) {
		Cookie cookie = getCookie(cookieName);
		if(cookie != null)
			return cookie.getValue();
		return null;
	}
	
	public void removeCookie(String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(cookieName)){
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return;
				}
			}
		}
	}
	
	public String getRealIp() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length()== 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public String getRealIpV2() {
		String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
	}
	
	public void outputFile(File file, String reFileName){
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			String userAgent = request.getHeader("User-Agent");  
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {  
				reFileName = URLEncoder.encode(reFileName, "UTF-8");  
			} else {  
				reFileName = new String(reFileName.getBytes("UTF-8"), "ISO8859-1");  
			}  
			response.setHeader("Content-Disposition", "attachment; filename=" + reFileName);
			response.setContentType("application/octet-stream;charset=utf-8");  
            response.setCharacterEncoding("UTF-8");
			Files.copy(file, os);
		} catch (Exception e) {
			log.error("", e);
		} finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}
	
	public File uploadFile(MultipartFile uploadFile){
		if (uploadFile != null && !uploadFile.isEmpty()) {  
			try {
				String path = UUID.randomUUID().toString()+"_"+uploadFile.getOriginalFilename();
				File file = new File(path);
				Files.write(uploadFile.getBytes(), file);
			}catch (Exception e) { 
				log.error("", e);
			}
		}
		return null;
	}
	
	public PageInfo initPageInfo(Integer pageNo,Integer pageSize){
		pageNo = pageNo == null ? Constants.DEFAULT_PAGENO : pageNo;
		pageSize = pageSize == null ? Constants.DEFAULT_PAGESIZE : pageSize;
		return new PageInfo(pageNo, pageSize);
	}
}
