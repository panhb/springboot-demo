package com.panhb.demo.test.restful.user;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.panhb.demo.test.restful.BaseTest;

public class UserControllerTest extends BaseTest {
	
	private RestTemplate template = new RestTemplate();
	
	@Test
	public void testTacticsQuery() {
		String url = "http://127.0.0.1:8080/user/index";
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		String result = template.postForObject(url, param, String.class);
		System.out.println(result);
	}
}
