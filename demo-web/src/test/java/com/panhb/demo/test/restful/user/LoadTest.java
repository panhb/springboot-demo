package com.panhb.demo.test.restful.user;

import com.panhb.demo.test.restful.BaseTest;
import com.panhb.demo.utils.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.UUID;

public class LoadTest extends BaseTest {

	private RestTemplate template = new RestTemplate();

//	private static final String URL = "http://127.0.0.1:8080/load/";
	private static final String URL = "https://127.0.0.1:8443/load/";

//	@Test
	public void testDownLoad() throws Exception{
		String url = URL + "down";
		File file = new File("D:\\test.log.tmp");
		if (!file.exists()){
			file.createNewFile();
		}
		Long postLen = file.length();
		String localFileMd5 = FileUtils.getFileMd5(file);
		int step = 1024;
		String fileMd5 = "";
		Boolean flag = true;
		while(!fileMd5.equals(localFileMd5)){
			HttpHeaders headers = new HttpHeaders();
			headers.add("Range", "bytes="+postLen+"-"+(postLen+step));
			ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET,new HttpEntity<byte[]>(headers),byte[].class);
			int statusCodeValue = response.getStatusCodeValue();
			if(statusCodeValue != 200 && statusCodeValue != 206){
				flag = false;
				break;
			}
			byte[] result = response.getBody();
			fileMd5 = response.getHeaders().get("File-Md5").get(0);
			System.out.println("fileMd5==================="+fileMd5);
			InputStream ins = new ByteArrayInputStream(result);
			RandomAccessFile raFile = new RandomAccessFile(file, "rw");
			raFile.seek(postLen);
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = ins.read(buffer))!=-1){
				raFile.write(buffer,0,len);
			}
			raFile.close();
			localFileMd5 = FileUtils.getFileMd5(file);
			postLen +=  step;
		}
		if(flag){
			File destfile = new File("D:\\test.log");
			if(destfile.exists())
				destfile.delete();
			file.renameTo(destfile);
		}
	}


//	@Test
	public void testUpLoad() throws Exception{
		String url = URL + "up";
		String srcPath = "D:\\test.log";
		String destPath = "D:\\split\\"+ UUID.randomUUID().toString();
		File srcFile = new File(srcPath);
		String file_md5 = FileUtils.getFileMd5(srcFile);
		int chunk_num = FileUtils.split(srcPath,1,destPath);
		File[] files = new File(destPath).listFiles();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json;charset=utf-8");
		headers.add("file_md5", file_md5);
		headers.add("file_name", srcFile.getName());
		headers.add("chunk_num", chunk_num+"");
		for(File file : files){
			headers.set("chunk_index", file.getName());
			headers.set("chunk_md5", FileUtils.getFileMd5(file));
			FileSystemResource resource = new FileSystemResource(file);
			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("apk_file", resource);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
			ResponseEntity<String> response = template.postForEntity(url, requestEntity, String.class);
			System.out.println("result==================="+response.getBody());
			String fileId = response.getHeaders().get("File-Id").get(0);
			headers.set("file_id", fileId);
		}
	}

	@Test
	public void testPost() throws Exception{
		String url = URL + "up2";
		String srcPath = "D:\\test.log";
//		String srcPath = "D:\\demo.apk";
		String fileId = UUID.randomUUID().toString();
		File srcFile = new File(srcPath);
		String fileMd5 = FileUtils.getFileMd5(srcFile);
		long fileSize = srcFile.length();
//		int step = 1024*1024;
		int step = 1024;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost  httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json;charset=utf-8");
		httpPost.setHeader("file_md5", fileMd5);
		httpPost.setHeader("file_name", srcFile.getName());
		httpPost.setHeader("file_id", fileId);
		httpPost.setHeader("file_size", fileSize+"");
		httpPost.setHeader("file_step", step+"");
		byte[] bytes = new byte[step]; // 暂存容器
		RandomAccessFile raf = new RandomAccessFile(srcFile, "r");
		Long start = 0L;
		while(start < srcFile.length()){
			raf.seek(start);
			long tmp = start + step;
			if(tmp > fileSize){
				tmp = fileSize;
				step =(int)(fileSize - start);
				bytes = new byte[step];
			}
			raf.read(bytes,0,step);
			httpPost.setHeader("chunk_md5", DigestUtils.md5Hex(bytes));
			httpPost.setHeader("Range", "bytes="+start+"-"+tmp);
			httpPost.setEntity(new ByteArrayEntity(bytes));
			CloseableHttpResponse response = client.execute(httpPost);
			int rspCode = response.getStatusLine().getStatusCode();
//			System.out.println("rspCode:" + rspCode);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println("result:" + result);
			start = tmp;
		}
	}
}
