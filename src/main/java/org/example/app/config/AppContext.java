package org.example.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("org.example.app")
@PropertySource("classpath:db/db.properties")
public class AppContext {

    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource =
                new DriverManagerDataSource();
        driverManagerDataSource.setUrl(getDbUrl());
        driverManagerDataSource.setUsername(environment.getProperty("MYSQL_USER"));
        driverManagerDataSource.setPassword(environment.getProperty("MYSQL_PASSWORD"));
        driverManagerDataSource.setDriverClassName(
                Objects.requireNonNull(environment.getProperty("MYSQL_JDBC_DRIVER")));
        return driverManagerDataSource;
    }

    private String getDbUrl() {
        return String.format("%s%s:%s/%s", environment.getProperty("MYSQL_JDBC_URL_PREFIX"), environment.getProperty("MYSQL_HOST"), environment.getProperty("MYSQL_HOST_PORT"), environment.getProperty("MYSQL_DATABASE"));
    }
}
