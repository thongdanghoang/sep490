package enterprise.rest;

import enterprise.TestcontainersConfigs;
import enterprise.dtos.BuildingDTO;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BuildingControllerTest extends TestcontainersConfigs {
    
    @LocalServerPort
    private Integer port;
    
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }
    
    @Test
    void getEnterpriseBuildings_withValidToken_returns200() {
        RestAssured.given()
                   .auth().oauth2(getToken("enterprise.employee@greenbuildings.com", "enterprise.employee"))
                   .contentType(ContentType.JSON)
                   .body(new SearchCriteriaDTO<Void>(null, null, null))
                   .when()
                   .post("/api/buildings/search")
                   .then()
                   .statusCode(200);
    }
    
    @Test
    void getEnterpriseBuildings_withInvalidToken_returns401() {
        RestAssured.given()
                   .auth().oauth2("invalid_token")
                   .contentType(ContentType.JSON)
                   .body(new SearchCriteriaDTO<Void>(null, null, null))
                   .when()
                   .post("/api/buildings/search")
                   .then()
                   .statusCode(401);
    }
    
    @Test
    void createBuilding_withValidToken_returns201() {
        var building = BuildingDTO.builder()
                                  .name("Building 1")
                                  .floors(1)
                                  .squareMeters(BigDecimal.valueOf(123.45))
                                  .build();
        RestAssured.given()
                   .auth().oauth2(getToken("enterprise.owner@greenbuildings.com", "enterprise.owner"))
                   .contentType(ContentType.JSON)
                   .body(building)
                   .when()
                   .post("/api/buildings")
                   .then()
                   .statusCode(201);
    }
    
    @Test
    void createBuilding_withMissingFields_returns400() {
        RestAssured.given()
                   .auth().oauth2(getToken("enterprise.owner@greenbuildings.com", "enterprise.owner"))
                   .contentType(ContentType.JSON)
                   .body(BuildingDTO.builder().build())
                   .when()
                   .post("/api/buildings")
                   .then()
                   .statusCode(400);
    }
    
    @Test
    void createBuilding_withInvalidToken_returns401() {
        RestAssured.given()
                   .auth().oauth2("invalid_token")
                   .contentType(ContentType.JSON)
                   .body(BuildingDTO.builder().build())
                   .when()
                   .post("/api/buildings")
                   .then()
                   .statusCode(401);
    }
}
