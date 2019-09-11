package server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import server.security.jwt.JwtConfigurer;
import server.security.jwt.JwtTokenProvider;

/**
 * Configuration for security.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Autowired JWT Token provider.
     */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Creating a AuthenticationManager Bean so it can be autowired.
     * @return AuthenticationManager
     * @throws Exception exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configuring which path can be accessed by which role.
     * @param http HttpSecurity
     * @throws Exception exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    /**
     * sets Token provider.
     *@param pjwtTokenProvider token provider
     */
    public void setJwtTokenProvider(final JwtTokenProvider pjwtTokenProvider) {
        this.jwtTokenProvider = pjwtTokenProvider;
    }

    /**
     * gets token provider.
     * @return token provider
     */
    public JwtTokenProvider getJwtTokenProvider() {
        return jwtTokenProvider;
    }
}
