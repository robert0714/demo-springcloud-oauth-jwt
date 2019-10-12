package pl.piomin.services.product;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
 

//Before running this test make sure authorization server is running   

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude= {EurekaClientAutoConfiguration.class})
public class AuthenticationClaimsIntegrationTest {
	private RestTemplate restTemplate ;

	private static final String CLIENT_ID = "app";
	private static final String CLIENT_SECRET = "123456";

	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
 
    @Autowired
    private JwtTokenStore tokenStore;
    
    @Before
    public void setup() {
        restTemplate =new RestTemplate();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
    	String url = "http://localhost:5566/oauth/token";

        //set up the basic authentication header 
        String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
  
        //setting up the request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        requestHeaders.add("Authorization", authorizationHeader);
    	
    	
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
		params.add("client_id", CLIENT_ID); 
		params.add("client_secret", CLIENT_SECRET); 
		params.add("username", username);
		params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, requestHeaders);

        // @formatter:off
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(response.getBody()).get("access_token").toString();
    }
    @Test
    @Ignore
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        final String accessToken = obtainAccessToken("admin", "123456");
        System.out.println("token:" + accessToken);
//        mockMvc.perform(get("/employee").header("Authorization", "Bearer " + accessToken).param("email", EMAIL)).andExpect(status().isForbidden());
    }
    @Test
    @Ignore
    public void whenTokenDontContainIssuer_thenSuccess() {
        final String tokenValue = obtainAccessToken(CLIENT_ID, "admin", "123456");
        Assert.assertNotNull(tokenValue);
        
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        System.out.println(tokenValue);
        System.out.println(auth);
        assertTrue(auth.isAuthenticated());
        System.out.println(auth.getDetails());

        Map<String, Object> details = (Map<String, Object>) auth.getDetails();
        assertTrue(details.containsKey("organization"));
        System.out.println(details.get("organization"));
    }

	private String obtainAccessToken(String clientId, String username, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId); 
		params.put("client_secret", CLIENT_SECRET); 
		params.put("username", username);
		params.put("password", password);
		final Response response = RestAssured.given().auth().preemptive().basic(clientId, CLIENT_SECRET).and().with()
				.params(params).when().post("http://localhost:5566/oauth/token");
		String content = response.getBody().asString();
		System.out.println(content);
		return response.jsonPath().getString("access_token");
	} 
	@Test
	public void readAuthentication() {
		String tokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYWNjb3VudCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJvbGUiLCJhY2NvdW50Il0sImV4cCI6MTU3MDk4NTk1MCwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiNTIzY2NkOGUtY2MwNi00MDc2LTkwYTEtNmFiYjViYjMwMzA1IiwiY2xpZW50X2lkIjoiYXBwIn0.KssvENr92lERr4i31LBEWU9ctxzG2h-tJ1QKg88kLUg";
//		final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
//		System.out.println(tokenValue);
//		System.out.println(auth);
//		assertTrue(auth.isAuthenticated());
//		System.out.println(auth.getDetails());
//
//		Map<String, Object> details = (Map<String, Object>) auth.getDetails();
//		assertTrue(details.containsKey("organization"));
//		System.out.println(details.get("organization"));
	}
}
