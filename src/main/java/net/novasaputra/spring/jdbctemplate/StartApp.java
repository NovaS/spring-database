package net.novasaputra.spring.jdbctemplate;

import net.novasaputra.spring.jdbctemplate.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author NovaS
 */
public class StartApp {
	private static final Logger log = LoggerFactory.getLogger(StartApp.class);
	
	public StartApp() {
		log.info("Starting App!");
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("context.xml");
		UserService service = appCtx.getBean(UserService.class);
		log.info("===== PRINT ALL USER =====");
		service.printAllUser();
		log.info("===== INSERT USERS =====");
		service.fillUser(10);
		log.info("===== PRINT ALL USER =====");
		service.printAllUser();
	}
	
	public static void main(String[] args) {
		new StartApp();
	}
}
