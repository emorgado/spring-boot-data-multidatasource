package br.com.emorgado.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = { "br.com.emorgado.financial" },  
					   entityManagerFactoryRef = "entityManagerFactory", 
					   transactionManagerRef= "transactionManager"
						)
public class DataSourceFinancialConfiguration {
	
	@Primary
	@Bean("transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasources.dsFinancial")
	public DataSourceProperties dataSourceProperties(){
		return new DataSourceProperties();
	}
	
	@Primary
	@Bean("dataSource")
	public DataSource dataSource() {		
	    return dataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Primary
	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory( EntityManagerFactoryBuilder builder ) {
		System.out.println("BUILDING DATASOURCE FINANCIAL "+dataSource());
		return builder
				.dataSource(dataSource())
				.packages("br.com.emorgado.financial")
				.persistenceUnit("financialUnit")
				.build();
	}
}
