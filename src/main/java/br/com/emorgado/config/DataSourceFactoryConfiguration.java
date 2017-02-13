package br.com.emorgado.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"br.com.emorgado.factory"}, 
                       entityManagerFactoryRef = "factoryEntityManagerFactory",
                       transactionManagerRef="factoryTransactionManager"
		)
public class DataSourceFactoryConfiguration {
	
	@Bean(name = "factoryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("factoryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	@ConfigurationProperties("spring.datasources.dsFactory")
	public DataSourceProperties factoryDataSourceProperties(){
		return new DataSourceProperties();
	}
	
	
	@Bean("factoryDataSource")
	public DataSource factoryDataSource() {
	    return factoryDataSourceProperties().initializeDataSourceBuilder().build();
	}
	

	@Bean
	public LocalContainerEntityManagerFactoryBean factoryEntityManagerFactory( EntityManagerFactoryBuilder builder ) {
		System.out.println("BUILDING DATASOURCE FACTORY "+factoryDataSource());
	    return builder
	            .dataSource( factoryDataSource() )
	            .packages("br.com.emorgado.factory")
	            .persistenceUnit("factoryUnit")
	            .build();
	}

}
