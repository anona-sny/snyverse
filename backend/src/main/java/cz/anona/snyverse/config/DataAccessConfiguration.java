package cz.anona.snyverse.config;

import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableNeo4jRepositories(basePackages = "cz.anona.snyverse.repositories.neo", transactionManagerRef = "neo4jTransactionManager")
@EnableJpaRepositories(basePackages = "cz.anona.snyverse.repositories.jpa", transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
public class DataAccessConfiguration {

    @Autowired
    private Environment env;

    Logger logger = LoggerFactory.getLogger(DataAccessConfiguration.class);

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(env.getProperty("spring.data.neo4j.uri"))
                .credentials(env.getProperty("spring.data.neo4j.username"), env.getProperty("spring.data.neo4j.password"))
                .build();
    }

    @Bean
    public SessionFactory sessionFactory(org.neo4j.ogm.config.Configuration configuration) {
        return new SessionFactory(configuration, "cz.anona.snyverse.entities.neo");
    }

    @Bean(name = "neo4jTransactionManager")
    public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }

//////////////////////////////////////////

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("cookie-store.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("cookie-store.datasource.url"));
        dataSource.setUsername(env.getProperty("cookie-store.datasource.username"));
        dataSource.setPassword(env.getProperty("cookie-store.datasource.password"));
        return dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("cz.anona.snyverse.entities.jpa")
                .persistenceUnit("sessionCookiePersistenceUnit").build();
    }

    @Primary
    @Bean(name = "jpaTransactionManager")
    public JpaTransactionManager jpaTransactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.show_sql", false);
        hibernateProperties.put("spring.jpa.generate-ddl", true);
        jpaTransactionManager.setJpaProperties(hibernateProperties);
        return jpaTransactionManager;
    }

}
