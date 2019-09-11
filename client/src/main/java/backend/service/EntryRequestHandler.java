package backend.service;

import com.google.common.hash.Hashing;

import backend.entity.ConnectionResponse;
import backend.entity.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * EntryController's service to provide functional support.
 * It handles login and registration through RestTemplate.
 */
public class EntryRequestHandler {

    private static String url = "https://ooppgogreen99.herokuapp.com/v1/user/";
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * Set url of the EntryRequestHandler.
     * Needed for testing with Wiremock.
     *
     * @param url of localhost
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets a connection with the server for authentication purposes.
     *
     * @param loginRequest Object containing username and password
     * @return Server's response after authentication's attempt
     */
    public static ConnectionResponse login(LoginRequest loginRequest) {
        loginRequest.setPassword(encode(loginRequest.getPassword()));
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, new HttpHeaders());
        ResponseEntity<String> httpResponse;
        ConnectionResponse response = new ConnectionResponse();

        try {
            httpResponse = EntryRequestHandler.restTemplate
                .exchange(EntryRequestHandler.url + "login", HttpMethod.POST, entity, String.class);
            response = EntryRequestHandler.objectMapper
                .readValue(httpResponse.getBody(), ConnectionResponse.class);
        } catch (IOException | HttpClientErrorException | HttpServerErrorException e) {
            response.setToken("Invalid");
        }
        AuthHeader.token = response.getToken();
        return response;
    }

    /**
     * Sets a connection with the server for registration purposes.
     * Through a LoginRequest instance, it hashes the password and send a request to server
     * to add a new user to the database.
     *
     * @param signUpRequest LoginRequest object containing username and password
     */
    public static String createNewUser(LoginRequest signUpRequest) {
        signUpRequest.setPassword(encode(signUpRequest.getPassword()));
        HttpEntity<LoginRequest> entity = new HttpEntity<>(signUpRequest, new HttpHeaders());
        try {
            restTemplate.exchange(EntryRequestHandler.url + "register",
                    HttpMethod.POST, entity, HttpStatus.class);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return "Username already exists";
        }
        return "Successfully registered.";
    }

    protected static String encode(String str) {
        return Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString();
    }
}
