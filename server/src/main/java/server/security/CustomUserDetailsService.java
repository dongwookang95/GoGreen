package server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.service.user.UserService;

/**
 * Custom UserDetailsService class that takes care of loading by username.
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Autowired UserRepository to use its functions.
     */
    private final UserService userService;

    /**
     * Injecting repositories into service.
     * @param userService UserService
     */
    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads user by username.
     * @param username username referring to User
     * @return UserDetails object with properties
     * @throws UsernameNotFoundException username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return this.userService.findByUsername(username);
    }
}
