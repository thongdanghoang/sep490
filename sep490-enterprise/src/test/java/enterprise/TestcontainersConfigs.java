package enterprise;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.function.Supplier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class TestcontainersConfigs {
    
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.4-alpine");
    static GenericContainer<?> idP = new GenericContainer<>("thongdh3401/keycloak:24.0.5");
    
    static {
        postgres.start();
        idP.withExposedPorts(8180)
           .withCommand("start-dev --http-port 8180")
           .waitingFor(Wait.forHttp("/realms/greenbuildings/.well-known/openid-configuration"))
           .start();
    }
    
    @LocalServerPort
    Integer port;
    
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

//    @BeforeAll
//    static void beforeAll() {
//        postgres.start();
//        idP.withExposedPorts(8180)
//                .withCommand("start-dev --http-port 8180")
//                .waitingFor(Wait.forHttp("/realms/greenbuildings/.well-known/openid-configuration"))
//                .start();
//    }

//    @AfterAll
//    static void afterAll() {
//        postgres.stop();
//        idP.stop();
//    }
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        Supplier<Object> callable = () -> StringSubstitutor.replace(
                "http://localhost:${mappedPort}/realms/greenbuildings", Map
                        .of("mappedPort", getMappedPort(idP, 8180)));
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", callable);
    }
    
    protected static String getMappedPort(GenericContainer<?> container, int originalPort) {
        return container.getMappedPort(originalPort).toString();
    }
    
    protected String getToken(String username, String password) {
        var tokenUrl = StringSubstitutor.replace(
                "http://localhost:${mappedPort}/realms/greenbuildings/protocol/openid-connect/token", Map
                        .of("mappedPort", getMappedPort(idP, 8180)));
        var response = RestAssured
                .given()
                .contentType(ContentType.URLENC)
                .formParam("client_id", "testcontainer")
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .post(tokenUrl)
                .then()
                .statusCode(200)
                .extract()
                .as(Token.class);
        
        return response.accessToken();
    }
    
    record Token(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") long expiresIn,
            @JsonProperty("refresh_expires_in") long refreshExpiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("not-before-policy") int notBeforePolicy,
            @JsonProperty("session_state") String sessionState,
            @JsonProperty("scope") String scope
    ) {
    }
}
