package server.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import server.exception.InvalidJwtAuthenticationException;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * JWT Token Provider creates tokens for users to authenticate.
 */
@Component
public class JwtTokenProvider {

    /**
     * Secret key used to construct tokens.
     */
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    /**
     * Determines how long a token can be used.
     */
    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    /**
     * Autowired UserDetailsService to use functionality.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Creating secret key by using basic Base64 encoder.
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }

    /**
     * Create a token for an user with specific roles.
     * @param username username referring to an User
     * @param roles roles this user has
     * @return String which contains token
     */
    public String createToken(final String username, final List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    /**
     * Checks what kind of authentication/role a specific token contains.
     * @param token String which contains used token
     * @return Authentication
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Find the username that belongs to a certain token.
     * @param token String which contains used token
     * @return String which contains username
     */
    public String getUsername(final String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Resolves token by taking a substring of it.
     * @param req HttpServletRequest incoming request with authorization header
     * @return String which contains the token
     */
    public String resolveToken(final HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * Validating if token is actually correct and not expired.
     * @param token String which contains the token
     * @return Boolean whether the token is valid or not
     */
    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}
