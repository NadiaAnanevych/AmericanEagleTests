package controllers;

import configs.TestPropertiesConfig;
import dto.AuthResponse;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.NotImplementedException;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;

public class AuthorizationController {
    private static final String TOKEN_ENDPOINT = "/ugp-api/auth/oauth/v4/token";
    public static final String INVALID_AUTH_HEADER = "123I2OTAtOD";
    public static final String INVALID_GRANT_TYPE_VALUE = "my_credentials";

    static TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public static RequestSpecification guestAuthSpec() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept(JSON)
                .contentType(URLENC)
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header("authorization", configProperties.getGuestHeaderAuth())
                .filter(new AllureRestAssured());
    }

    @Step("Get guest token")
    public static String getGuestToken() {
        Response response = guestAuthSpec()
                .when()
                .formParam("grant_type", "client_credentials")
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.jsonPath().getString("access_token");
    }

    @Step("Get guest token response")
    public static AuthResponse getGuestTokenResponse() {
        return guestAuthSpec()
                .when()
                .formParam("grant_type", "client_credentials")
                .post(TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);
    }

    @Step("Get guest token with invalid header")
    public static Response getGuestTokenWithInvalidHeader() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept("application/json")
                .contentType("application/x-www-form-urlencoded")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header("authorization", INVALID_AUTH_HEADER)
                .filter(new AllureRestAssured())
                .when()
                .formParam("grant_type", "client_credentials")
                .post(TOKEN_ENDPOINT);
    }

    @Step("Get guest token with invalid grant type")
    public static Response getGuestTokenWithInvalidGrantTypeValue() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept("application/json")
                .contentType("application/x-www-form-urlencoded")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header("authorization", TOKEN_ENDPOINT)
                .filter(new AllureRestAssured())
                .when()
                .formParam("grant_type", INVALID_GRANT_TYPE_VALUE)
                .post(TOKEN_ENDPOINT);
    }

    public static String getAuthToken() {
        throw new NotImplementedException("Not yet implemented!");
    }
}
