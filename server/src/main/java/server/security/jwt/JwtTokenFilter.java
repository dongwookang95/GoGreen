package server.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Custom JwtTokenFilter class.
 */
public class JwtTokenFilter extends GenericFilterBean {

    /**
     * Property JwtTokenProvider to provide tokens.
     */
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Set private JwtTokenProvider.
     * @param jwtTokenProvider JwtTokenProvider
     */
    public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Filter with our custom filter when a request comes in.
     * @param req ServletRequest incoming request
     * @param res ServletResponse response given back
     * @param filterChain FilterChain
     * @throws IOException io exception
     * @throws ServletException servlet exception
     */
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, res);
    }
}
