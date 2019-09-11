package server.security.jwt;

import org.junit.jupiter.api.Test;
import server.exception.InvalidJwtAuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    @Test
    public void invalidToken(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        boolean thrown = false;

        try {
            jwtTokenProvider.validateToken("Not a working token.");
        } catch (InvalidJwtAuthenticationException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}