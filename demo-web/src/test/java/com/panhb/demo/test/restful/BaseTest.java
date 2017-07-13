package com.panhb.demo.test.restful;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.panhb.demo.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest(classes = Application.class)
public class BaseTest {
	
	//在所有测试方法前执行一次，一般在其中写上整体初始化的代码
	@BeforeClass
	public static void beforeAll(){
	}
	
	//在所有测试方法后执行一次，一般在其中写上销毁和释放资源的代码 
	@AfterClass
	public static void afterAll(){
	}
	
}
