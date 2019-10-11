package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.junit4.SpringRunner;

import develop.eurekaserver.EurekaserverApplication;
 
 

@RunWith(SpringRunner.class)
@SpringBootTest(classes=EurekaserverApplication.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
