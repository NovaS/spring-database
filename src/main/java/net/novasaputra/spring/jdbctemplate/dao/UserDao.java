package net.novasaputra.spring.jdbctemplate.dao;

import java.util.List;

import net.novasaputra.spring.jdbctemplate.model.User;
/**
 * @author Nova Saputra
 */
public interface UserDao {
	public int save(User user);
	public int delete(User user);
	public User findById(int id);
	public User findByName(String name);
	public List<User> findAll();
}
