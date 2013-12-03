package net.novasaputra.spring.jdbctemplate;

import javax.annotation.PostConstruct;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author NovaS
 */
@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {
	@Autowired
	private Environment env;
	private JdbcConnectionPool cp;
	
	@PostConstruct
	private void setup() {
		cp = JdbcConnectionPool.create(env.getProperty("db.url"), env.getProperty("db.user"), env.getProperty("db.pass"));
	}
	
	@Bean
	public JdbcConnectionPool getDataSource() {
		return cp;
	}
}
