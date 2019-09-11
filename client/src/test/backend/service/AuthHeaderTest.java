package backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthHeaderTest {

    @Test
    void makeHeader() {
        AuthHeader test = new AuthHeader();
        test.token = "ABC";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer ABC");

        assertEquals(headers, test.makeHeader());
    }

    @Test
    void makeEntity() {
        AuthHeader test = new AuthHeader();
        test.token = "ABC";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer ABC");

        assertEquals(new HttpEntity(headers), test.makeEntity(headers));
    }

}