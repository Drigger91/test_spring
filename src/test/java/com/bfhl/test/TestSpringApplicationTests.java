package com.bfhl.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestSpringApplicationTests {
    @LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldPassIfStringMatches(){
		assertEquals("Hello World from spring boot",testRestTemplate.getForObject("http://localhost:" + port +"/user/hello",String.class));

	}
	@Test
	public void SecondTest(){
		assertEquals("first",testRestTemplate.getForObject("http://localhost:" + port + "/user/first",String.class));
	}

	@Test
	public void third(){
		assertEquals("Name",testRestTemplate.getForObject("http://localhost:" + port + "/admin/name", String.class));
	}

}
