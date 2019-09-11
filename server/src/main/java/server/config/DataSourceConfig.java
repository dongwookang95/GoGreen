package server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;
import javax.sql.DataSource;

/**
 * Configuration for database/datasource.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Logger for writing stuff to console.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(DataSourceConfig.class);

    /**
     * Set up datasource properties.
     * @return Datasource
     */
    @Bean
    @Profile("postgres")
    public static DataSource postgresDataSource() {
        String databaseUrl = System.getenv("postgres://yhnsbfouuwciki:"
                + "596cc8d8f7c67eab4294fa54d239e2c4cf7a4bb18e695c744a52bd7d85e50cc9"
                + "@ec2-54-217-208-105.eu-west-1.compute.amazonaws.com"
                + ":5432/dc1atkjof3uo7i");
        LOG.info("Initializing PostgreSQL database: {}", databaseUrl);

        URI dbUri;
        try {
            dbUri = new URI(databaseUrl);
        } catch (URISyntaxException e) {
            String error = String.format("Invalid DATABASE_URL: %s",
                    databaseUrl);
            LOG.error(error, e);
            return null;
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                + dbUri.getPort() + dbUri.getPath();

        org.apache.tomcat.jdbc.pool.DataSource dataSource
                = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnReturn(true);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    /**
     * Get logger.
     * @return Logger
     */
    public Logger getLog() {
        return LOG;
    }
}

