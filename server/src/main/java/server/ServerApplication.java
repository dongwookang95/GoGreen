package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class for running server.
 */
@EnableJpaRepositories
@SpringBootApplication
public class ServerApplication {

    /**
     * Method that starts server.
     * @param args String[]
     */
    public static void main(final String[] args) {

        SpringApplication.run(ServerApplication.class, args);
    }

    /**
     * PasswordEncoder to be used autowired in other classes.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
