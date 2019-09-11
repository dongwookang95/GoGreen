package server.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Custom JWT configurer for security.
 */
public class JwtConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain,
                HttpSecurity> {

    /**
     * JWT Token provider to provide tokens.
     */
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Set private JwtTokenProvider.
     * @param jwtTokenProvider JwtTokenProvider to set
     */
    public JwtConfigurer(final JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Configure our custom token filter in security.
     * @param http HttpSecurity
     * @throws Exception throws exception
     */
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        http.addFilterBefore(customFilter,
                UsernamePasswordAuthenticationFilter.class);
    }
}
