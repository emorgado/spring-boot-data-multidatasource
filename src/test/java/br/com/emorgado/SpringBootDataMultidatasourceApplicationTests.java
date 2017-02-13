package br.com.emorgado;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Integration tests
 * @author emerson
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("scratch")
public class SpringBootDataMultidatasourceApplicationTests {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	/*
	 *   TESTS USING REST TEMPLATE (hateoas)
	 *   https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-hateoas/src/test/java/sample/hateoas/SampleHateoasApplicationTests.java
	 */
	
	@Test
	public void getProductOne() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/api/products/1", String.class);
		
		System.out.println("body: "+entity.getBody());
		assertThat( entity.getStatusCode()).isEqualTo( HttpStatus.OK );
		assertThat(entity.getBody()).contains("\"name\" : \"nike shoes\"");
	}
	
	@Test
	public void getSaleOne() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/api/sales/1", String.class);
		
		System.out.println("body: "+entity.getHeaders().get("Content-Type"));
		assertThat( entity.getStatusCode()).isEqualTo( HttpStatus.OK );
		assertThat( entity.getHeaders().get("Content-Type")).containsExactly("application/json;charset=UTF-8");
		assertThat(entity.getBody()).contains("\"description\" : \"nike shoes size 40\"");
	}
	
	
	
	/*
	 *    TESTS USING MOCK MVC
	 *  https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-data-rest/src/test/java/sample/data/rest/SampleDataRestApplicationTests.java
	 */
	
	@Test
	public void getSaleTwoWithMvc() throws Exception {

		this.mvc.perform(get("/api/sales/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("description", equalTo("nike shoes size 40")))
				;
	}
	

}
