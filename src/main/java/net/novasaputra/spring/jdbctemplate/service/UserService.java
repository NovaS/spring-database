package net.novasaputra.spring.jdbctemplate.service;

import java.util.List;

import net.novasaputra.spring.jdbctemplate.dao.UserDao;
import net.novasaputra.spring.jdbctemplate.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NovaS
 */
@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserDao daoUser;
	
	public void fillUser(int count) {
		for(int x=0;x<count;x++){
			User user = new User();
			user.setUsername("user"+x);
			user.setPassword("user aja");
			user.setRole(x);
			daoUser.save(user);
		}
	}
	
	public void printAllUser() {
		List<User> list = daoUser.findAll();
		for(User user:list) log.info(user.toString());
	}
}
