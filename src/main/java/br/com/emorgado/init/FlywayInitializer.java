package br.com.emorgado.init;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayInitializer {

	private final DataSource dataSource;	
	private final DataSource factoryDataSource;
	
	public FlywayInitializer( 
				DataSource dataSource
				, @Qualifier("factoryDataSource") DataSource factoryDataSource
				){
		this.dataSource = dataSource;
		this.factoryDataSource = factoryDataSource;
	}
	
	@PostConstruct
	public void migrateFlyway(){
		
		Flyway flywayFinancial = new Flyway();
		flywayFinancial.setDataSource( dataSource );
		flywayFinancial.setLocations("db.migration.financial");
		flywayFinancial.setTarget(MigrationVersion.LATEST);
		flywayFinancial.migrate();
		
		
		
		Flyway flywayFactory = new Flyway();		
		flywayFactory.setDataSource( factoryDataSource );
		flywayFactory.setLocations("db.migration.factory");
		flywayFactory.setTarget(MigrationVersion.LATEST);
		flywayFactory.migrate();
	}
	
}
