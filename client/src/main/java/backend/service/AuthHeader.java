package backend.service;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * Class defining methods for making authorized HttpEntities.
 */
@NoArgsConstructor
public class AuthHeader {
    /**
     * Session specific token for a user.
     */
    public static String token;

    /**
     * This method creates an HttpEntity with given headers.
     *
     * @param headers HttpHeaders to be include in the HttpEntity
     * @return HttpEntity entity with specified headers
     */
    public HttpEntity makeEntity(HttpHeaders headers) {
        return new HttpEntity(headers);
    }

    /**
     * This method creates the authorization header.
     *
     * @return HttpHeaders with Bearer Token assigned.
     */
    public HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + AuthHeader.token);
        return headers;
    }

}
