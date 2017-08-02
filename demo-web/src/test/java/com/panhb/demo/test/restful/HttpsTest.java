package com.panhb.demo.test.restful;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.security.cert.CertificateException;


public class HttpsTest {

//	@Test
	public void testHttps() throws Exception{
//		java.lang.System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		//采用绕过验证的方式处理https请求
//		SSLContext sslcontext = SSLContext.getInstance("SSLv3");
		SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
//		SSLContext sslcontext = SSLContexts.createSystemDefault();
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sslcontext.init(null, new TrustManager[] { trustManager }, null);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).setSSLContext(sslcontext).build();
//		CloseableHttpClient httpClient = HttpClients.custom().build();
		//创建自定义的httpclient对象
//		HttpGet httpGet = new HttpGet("https://127.0.0.1:8443/user/findAll");
		HttpGet httpGet = new HttpGet("https://sdk.bangcle.com");
		HttpResponse response = httpClient.execute(httpGet);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("result:" + result);
	}



//	@Test
	public void testHttps2() throws Exception{
		SSLContext sslcontext = SSLContexts.createSystemDefault();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext) {
			@Override
			public Socket connectSocket(
					int connectTimeout,
					Socket socket,
					HttpHost host,
					InetSocketAddress remoteAddress,
					InetSocketAddress localAddress,
					HttpContext context) throws IOException, ConnectTimeoutException {
				if (socket instanceof SSLSocket) {
					try {
						PropertyUtils.setProperty(socket, "host", host.getHostName());
					} catch (NoSuchMethodException ex) {
					} catch (IllegalAccessException ex) {
					} catch (InvocationTargetException ex) {
					}
				}
				return super.connectSocket(connectTimeout, socket, host, remoteAddress,
						localAddress, context);
			}
		};
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLHostnameVerifier(new NoopHostnameVerifier())
//				.setSSLSocketFactory(sslsf)
				.setSSLContext(sslcontext)
				.build();
		HttpGet httpGet = new HttpGet("https://172.16.31.72");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("result:" + result);
	}

//	@Test
	public void testHttps3() throws Exception{
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpGet httpGet = new HttpGet("https://sdk.bangcle.com");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("result:" + result);
	}


	@Test
	public void testHttps4() throws Exception{
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContexts.createDefault(),new NoopHostnameVerifier());
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainsf)
				.register("https", sslsf)
				.build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		// 将最大连接数增加到200
		cm.setMaxTotal(200);
		// 将每个路由基础的连接增加到20
		cm.setDefaultMaxPerRoute(20);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm).setConnectionManagerShared(true)
//				.setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpGet httpGet = new HttpGet("https://172.16.31.72");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("result:" + result);
	}



}
