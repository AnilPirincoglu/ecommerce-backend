package dev.anilp.ecommerce_backend.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${DB_USER}")
    private String user;

    @Value(("${DB_PASS}"))
    private String password;

    @Bean(initMethod = "migrate")
    public Flyway flywayPostgres() {
        return Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5433/ecommerce", user, password)
                .locations("classpath:db/migration/postgresql")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway flywayMongodb() {
        return Flyway.configure()
                .dataSource("jdbc:mongodb://localhost:27017/ecommerce", null, null)
                .locations("classpath:db/migration/mongodb")
                .sqlMigrationSuffixes(".js")
                .load();
    }
}