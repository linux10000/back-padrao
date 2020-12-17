package br.com.juliano.appclient.structure.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

	public static final String PERSISTENCE_UNIT_NAME = "unitPrincipal";
	
	
	@Bean()
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		em.setDataSource(dataSource());
		
		//se vc quiser colocar apenas alguns modelos no contexto
//		em.setPersistenceUnitPostProcessors(new PersistenceUnitPostProcessor() {
//	        @Override
//	        public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo persistenceUnit) {
//	        	
//	        	List<String> classes = Arrays.asList(
//	        			"br.com.julliano.venda.api.pojo.cadastro.Atributo",
//	        			"br.com.julliano.venda.api.pojo.cadastro.AtributoValor"
//	        			);
//
//	        	classes.stream().forEach(v -> persistenceUnit.addManagedClassName(v));	        		            
//	        }
//	    });
		
		//se vc quiser colocar todo o pacote de modelos no contexto
		em.setPackagesToScan(new String[] {"br.com.juliano.appclient.model"});
	
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
	
		return em;
	}
	
	@Bean
	public HikariDataSource dataSource() throws Exception {
		
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
//		dataSource.setPassword("password");
		dataSource.setMaximumPoolSize(300); //300 eh o maximo do maximo recomendado para postgresql
		//dataSource.setLeakDetectionThreshold(10000);
		dataSource.setAutoCommit(true);
		dataSource.setMaxLifetime(30000);
		//dataSource.setMinimumIdle(10);
		//dataSource.setConnectionTimeout(2000);
		//dataSource.addDataSourceProperty("logUnclosedConnections", true);
		dataSource.addDataSourceProperty("socketTimeout", 30000);
		
//		if ( pgSslmode != null && !pgSslmode.trim().isEmpty() ) {
//			dataSource.addDataSourceProperty("sslmode", pgSslmode);
//			dataSource.addDataSourceProperty("sslcert", pgSslcert);
//			dataSource.addDataSourceProperty("sslkey", pgSslkey);
//			dataSource.addDataSourceProperty("sslpassword", pgSslpassword);
//			dataSource.addDataSourceProperty("sslrootcert", pgSslrootcert);
//		}
		
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
//	public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {
//	    @Override
//	    protected DataAccessException customTranslate
//	      (String task, String sql, SQLException sqlException) {
//	        if (sqlException.getErrorCode() == 23505) {
//	            return new DuplicateKeyException(
//	                "Custom Exception translator - Integrity constraint violation.", sqlException);
//	        }
//	        return null;
//	    }
//	}
	
	private Properties additionalProperties() {

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
//		properties.setProperty("hibernate.dialect", "br.com.hcf.utils.hibernate.HcfPostgreSQLDialect");
		properties.setProperty("format_sql", "true");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		
		properties.setProperty("spring.datasource.testWhileIdle", "true");
		properties.setProperty("spring.datasource.test-on-borrow", "true");
		properties.setProperty("logging.level.com.zaxxer.hikari", "DEBUG");
		
//		properties.setProperty("javax.persistence.validation.mode", "ddl");
//		properties.setProperty("spring.jpa.hibernate.ddl-auto", "validate");
		
		properties.setProperty("hibernate.order_inserts", "true");
		properties.setProperty("hibernate.order_updates", "true");
		properties.setProperty("hibernate.jdbc.batch_size", "50");
//		properties.setProperty("log4j.logger.org.hibernate.type", "trace");
//		properties.setProperty("hibernate.show_sql", "true");
//		properties.setProperty("hibernate.format_sql", "true");
//		properties.setProperty("hibernate.use_sql_comments", "true");
//		properties.setProperty("logging.level.org.hibernate.type.descriptor.sql", "trace");
		
//		properties.setProperty("annotatedClasses", "br.com.hcf.erp.pojo.Banco");
		return properties;
	}
}
