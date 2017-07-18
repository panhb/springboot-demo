package com.panhb.demo.controller.base;

import java.io.*;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.panhb.demo.utils.FileUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.panhb.demo.constants.Constants;
import com.panhb.demo.model.page.PageInfo;

@Controller
@Slf4j
public class BaseController {
	
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
		try {
			@Cleanup OutputStream os = response.getOutputStream();
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
		}
	}
	
	public File uploadFile(MultipartFile uploadFile){
		String destPath = UUID.randomUUID().toString()+"_"+uploadFile.getOriginalFilename();
		return uploadFile(uploadFile,destPath);
	}

	public File uploadFile(MultipartFile uploadFile,String destPath){
		if (uploadFile != null && !uploadFile.isEmpty()) {
			try {
				File file = new File(destPath);
				if(file.exists())
					file.delete();
				if(!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				Files.write(uploadFile.getBytes(), file);
				return file;
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

	public void breakPointDownload(File file){
		long fileLength = file.length();
		long pastLength = 0; // 记录已下载文件大小
		int rangeSwitch = 0; // 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
		long toLength = 0; // 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
		long contentLength = 0; // 客户端请求的字节总量
		String rangeBytes = "";
		response.reset(); // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
		response.setHeader( "File-Md5", FileUtils.getFileMd5(file));
		response.setHeader( "File-Size", fileLength+"");
		response.setHeader( "Accept-Ranges", "bytes" );
		String range = request.getHeader("Range");
		if(!Strings.isNullOrEmpty(range)){
			rangeBytes = range.replaceAll("bytes=" , "" );
			response.setStatus(HttpServletResponse. SC_PARTIAL_CONTENT);
			if (rangeBytes.endsWith("-")) { // bytes=969998336-
				rangeSwitch = 1;
				rangeBytes = rangeBytes.split("-")[0].trim();
				pastLength = Long.parseLong(rangeBytes.trim());
				contentLength = fileLength - pastLength; // 客户端请求的是 969998336 之后的字节
			} else { // bytes=1275856879-1275877358
				rangeSwitch = 2;
				String temp0 = rangeBytes.split("-")[0].trim();
				String temp1 = rangeBytes.split("-")[1].trim();
				pastLength = Long.parseLong(temp0);
				toLength = Long.parseLong(temp1);
				if(toLength > fileLength)
					toLength = fileLength;
				contentLength = toLength - pastLength;
			}
		}else{
			contentLength = fileLength;
		}
		// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
		if (pastLength != 0) {
			switch (rangeSwitch) {
				case 1:
					String contentRange = new StringBuffer("bytes ").append(new Long(pastLength).toString())
							.append("-").append(new Long(fileLength - 1).toString()).append("/")
							.append(new Long(fileLength).toString()).toString();
					response.setHeader( "Content-Range", contentRange);
					break;
				case 2:
                    String contentRange2 = new StringBuffer("bytes ").append(rangeBytes).append("/")
                            .append(new Long(fileLength).toString()).toString();
                    response.setHeader( "Content-Range", contentRange2);
					break;
				default:
					break;
			}
		}
		byte[] b = new byte[1024]; // 暂存容器
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader( "Content-Length", String.valueOf(contentLength));
			@Cleanup OutputStream os = response.getOutputStream();  // 写出数据
			@Cleanup OutputStream out = new BufferedOutputStream(os);  // 缓冲
			@Cleanup RandomAccessFile raf = new RandomAccessFile(file, "r");  // 负责读取数据
			try {
				switch (rangeSwitch) {
					case 0:
					case 1:
						raf.seek(pastLength);
						int n = 0;
						while ((n = raf.read(b, 0, 1024)) != -1) {
							out.write(b, 0, n);
						}
						break;
					case 2:
						raf.seek(pastLength);
						int m = 0;
						long readLength = 0; // 记录已读字节数
						while (readLength <= contentLength - 1024) {// 大部分字节在这里读取
							m = raf.read(b, 0, 1024);
							readLength += 1024;
							out.write(b, 0, m);
						}
						if (readLength <= contentLength) {
							m = raf.read(b, 0, (int)(contentLength - readLength));
							out.write(b, 0, m);
						}
						break;
					default:
						break;
				}
				out.flush();
			}catch (IOException e){
				log.error("", e);
			}
		}catch (IOException e){
			log.error("", e);
		}
	}

	public void printJson(Object obj){
		printJson(JSON.toJSONString(obj));
	}

	public void printJson(String str){
		response.setContentType("application/json;charset=utf-8");
		try {
			@Cleanup PrintWriter pw = response.getWriter();
			pw.write(str);
			pw.flush();
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
