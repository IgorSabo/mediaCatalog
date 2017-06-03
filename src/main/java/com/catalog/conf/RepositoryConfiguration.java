package com.catalog.conf;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.catalog.model.entities"})
@EnableJpaRepositories(basePackages = {"com.catalog.business.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
