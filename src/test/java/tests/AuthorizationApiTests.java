package tests;


import controllers.AuthorizationController;
import dto.AuthResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API tests")
public class AuthorizationApiTests {

    @DisplayName("Checking valid guest token")
    @Tags({@Tag("PO"),@Tag("smoke"), @Tag("positive")})
    @Test
    void validGuestTokenTest() {
        String token = AuthorizationController.getGuestToken();

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
        assertTrue(token.length() > 10, "Token should have a reasonable length");
    }

    @DisplayName("Checking valid guest token response")
    @Tags({@Tag("PO"),@Tag("smoke"), @Tag("positive")})
    @Test
    void validAuthResponseTest() {
        AuthResponse response = AuthorizationController.getGuestTokenResponse();

        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getAccessToken(), "Access token should not be null");
        assertFalse(response.getAccessToken().isEmpty(), "Access token should not be empty");

        assertTrue(response.getExpiresIn() > 0, "expires_in should be a positive integer");
        assertNotNull(response.getTokenType(), "token_type should not be null");
        assertFalse(response.getTokenType().isEmpty(), "token_type should not be empty");

        assertTrue(response.getNotBeforePolicy() >= 0, "not-before-policy should be zero or positive");
        assertNotNull(response.getScope(), "scope should not be null");
    }

    @DisplayName("Checking invalid authorization with wrong header")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void invalidAuthorizationHeaderTest() {
        Response response = AuthorizationController.getGuestTokenWithInvalidHeader();

        assertEquals(401, response.getStatusCode(), "Expected 401 Unauthorized for invalid auth");
    }

    @DisplayName("Checking invalid authorization with wrong grant type")
    @Tags({@Tag("P1"), @Tag("negative")})
    @Test
    void forbiddenAccessTest() {
        Response response = AuthorizationController.getGuestTokenWithInvalidGrantTypeValue();

        assertEquals(403, response.getStatusCode(), "Expected 403 Forbidden for insufficient permissions");
    }
}
