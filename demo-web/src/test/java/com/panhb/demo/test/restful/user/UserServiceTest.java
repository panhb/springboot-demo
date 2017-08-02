package com.panhb.demo.test.restful.user;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.panhb.demo.test.restful.BaseTest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


import java.io.InputStream;
import java.security.KeyStore;


public class UserServiceTest extends BaseTest {

	private static final String URL = "https://127.0.0.1:8443/user/";

//	private RestTemplate template = new RestTemplate();
	
//	@Autowired
//	UserService userService;
//
//	@Test
//	public void testUserQuery() {
//		System.out.println(userService.findByUsername("2"));
//	}

//	@Test
	public void testFindAll() throws Exception{
		String keyStorePassword = "123456";
		InputStream keyStoreFile = UserServiceTest.class.getResourceAsStream("/keystore.p12");
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(keyStoreFile, keyStorePassword.toCharArray());

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				new SSLContextBuilder()
						.loadTrustMaterial(null, new TrustSelfSignedStrategy())
						.loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
						.build(),
				NoopHostnameVerifier.INSTANCE);

		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		String result = restTemplate.getForObject(URL+"findAll",String.class);
		System.out.println(result);
	}

//	@Test
	public void testFindAll2() throws Exception{
		String keyStorePassword = "123456";
		InputStream keyStoreFile = UserServiceTest.class.getResourceAsStream("/keystore.p12");
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(keyStoreFile, keyStorePassword.toCharArray());

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				new SSLContextBuilder()
						.loadTrustMaterial(null, new TrustSelfSignedStrategy())
						.loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
						.build(),
				NoopHostnameVerifier.INSTANCE);

		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

		HttpPost httpPost = new HttpPost(URL+"findAll");
		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("result:" + result);
	}

//	@Test
	public void testFindAll3() throws Exception{
		HttpClient httpClient = HttpClients.custom().build();
		HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/user/findAll");
		HttpResponse response = httpClient.execute(httpPost);
		//重定向了  输出是空   302
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println("result:" + result);
	}


}
