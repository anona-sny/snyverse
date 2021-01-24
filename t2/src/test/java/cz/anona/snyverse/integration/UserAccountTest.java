package cz.anona.snyverse.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.anona.snyverse.dtos.UserLoginDTO;
import cz.anona.snyverse.dtos.UserRegistrationDTO;
import cz.anona.snyverse.services.ResponseService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAccountTest {

    @LocalServerPort
    public int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders requestHeaders;

    public URI uri;

    Logger logger = LoggerFactory.getLogger(UserAccountTest.class);

    @BeforeAll
    public void beforeTest() {
        this.uri = URI.create("http://localhost:"+this.port);
        ResponseEntity<String> httpResponse = this.restTemplate.exchange(this.uri+"/test",
                HttpMethod.POST, HttpEntity.EMPTY, String.class);
        this.logger.error(asJsonString(httpResponse.getHeaders()));
        List<String> cookies = httpResponse.getHeaders().get("Set-Cookie");
        this.requestHeaders = new HttpHeaders();
        this.requestHeaders.put(HttpHeaders.COOKIE, cookies);
    }

    @Test
    @Order(1)
    public void test01() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setEmail("Not an Email");
        userRegistrationDTO.setName("Not a name =ú.§¨-");
        userRegistrationDTO.setSurname("Not a name =ú.§¨-");
        userRegistrationDTO.setPassword("badpassword");
        userRegistrationDTO.setUsername("´=)¨§-ů. bad username");

        HttpEntity<UserRegistrationDTO> httpRequest1 = new HttpEntity<>(userRegistrationDTO);
        ResponseEntity<String> httpResponse = this.restTemplate.exchange(this.uri+"/users/register",
                HttpMethod.POST, httpRequest1, String.class);
        this.logger.error(asJsonString(httpResponse.getHeaders()));
        Assertions.assertEquals("Bad username", httpResponse.getBody());
        userRegistrationDTO.setUsername("basicUsername");
        httpResponse = this.restTemplate.exchange(this.uri+"/users/register", HttpMethod.POST, httpRequest1, String.class);
        Assertions.assertEquals("Bad password", httpResponse.getBody());
        userRegistrationDTO.setPassword("password165465FDSDF");
        httpResponse = this.restTemplate.exchange(this.uri+"/users/register", HttpMethod.POST, httpRequest1, String.class);
        Assertions.assertEquals("Bad email", httpResponse.getBody());
        userRegistrationDTO.setEmail("frantisekzavazal@seznam.cz"); //anona
        httpResponse = this.restTemplate.exchange(this.uri+"/users/register", HttpMethod.POST, httpRequest1, String.class);
        Assertions.assertEquals("Email exist", httpResponse.getBody());
        userRegistrationDTO.setEmail("frantisekzavazasdasdsawal@seznam.cz"); //anona
        userRegistrationDTO.setUsername("anona");
        httpResponse = this.restTemplate.exchange(this.uri+"/users/register", HttpMethod.POST, httpRequest1, String.class);
        Assertions.assertEquals("Username exist", httpResponse.getBody());
        this.logger.info("User test done");
    }


    @Test
    @Order(2)
    public void successfulRegistration() throws Exception {

        String emailTest = "testemail"+System.nanoTime()+"@test.com";
        String usernameTest = "testusername"+System.nanoTime();
        String passwordTest = "testPassword856";

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setEmail(emailTest);
        userRegistrationDTO.setPassword(passwordTest);
        userRegistrationDTO.setUsername(usernameTest);

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(emailTest);
        userLoginDTO.setPassword(passwordTest);

        HttpEntity httpRequest1 = new HttpEntity<>(userRegistrationDTO);
        ResponseEntity<String> httpResponse = this.restTemplate.exchange(this.uri+"/users/register",
                HttpMethod.POST, httpRequest1, String.class);
        Assertions.assertEquals(ResponseService.CREATED, httpResponse.getStatusCodeValue());

        httpRequest1 = new HttpEntity<>(userLoginDTO);
        httpResponse = this.restTemplate.exchange(this.uri+"/users/login",
                HttpMethod.POST, httpRequest1, String.class);
        this.logger.error(asJsonString(httpResponse.getHeaders()));
        Assertions.assertEquals(ResponseService.OKREQUEST, httpResponse.getStatusCodeValue());
        Assertions.assertEquals("Logged", httpResponse.getBody());

        List<String> cookies = httpResponse.getHeaders().get("Set-Cookie");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookies);
        httpRequest1 = new HttpEntity<>(requestHeaders);
        httpResponse = this.restTemplate.exchange(this.uri+"/users/isLogged",
                HttpMethod.GET, httpRequest1, String.class);
        this.logger.error(asJsonString(httpResponse.getHeaders()));
        Assertions.assertEquals("Logged", httpResponse.getBody());
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
