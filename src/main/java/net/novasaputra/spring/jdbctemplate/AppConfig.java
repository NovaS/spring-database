package net.novasaputra.spring.jdbctemplate;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author NovaS
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config.properties")
public class AppConfig {
	@Autowired
	private Environment env;
//	private JdbcConnectionPool cp;

//	@PostConstruct
//	private void setup() {
//		cp = JdbcConnectionPool.create(env.getProperty("db.url"), env.getProperty("db.user"), env.getProperty("db.pass"));
//	}

//	@Bean
//	public JdbcConnectionPool getDataSource() {
//		return cp;
//	}

	@Bean
	public DataSource getDataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName(env.getProperty("db.driver"));
		ds.setUrl(env.getProperty("db.url"));
		ds.setUsername(env.getProperty("db.user"));
		ds.setPassword(env.getProperty("db.pass"));
		return ds;
	}

	@Bean(name="transactionManager")
	public DataSourceTransactionManager getTransactionManager() {
		return new DataSourceTransactionManager(getDataSource());
	}
}
