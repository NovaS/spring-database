package net.novasaputra.spring.jdbctemplate.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import net.novasaputra.spring.jdbctemplate.AppConfig;
import net.novasaputra.spring.jdbctemplate.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
/**
 * @author Nova Saputra
 */
@Repository
public class UserDaoImpl implements UserDao {
	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	private static final String QRY_TABLE_CHECK = "select count(*) from information_schema.tables where table_name = 'TUSER'";
	private static final String QRY_TABLE_CREATE = "create table TUSER (id int primary key auto_increment,username varchar(100),password varchar(50),role int)";
	private static final String QRY_TABLE_ALTER = "alter table TUSER add constraint unique_user unique(username)";
	private static final String QRY_INSERT_USER = "insert into TUSER (username,password,role) values (?,?,?)";
	private static final String QRY_UPDATE_USER = "update TUSER set username=?,password=?,role=? where id=?";
	private static final String QRY_DELETE_USER = "delete from TUSER where id=?";
	private static final String QRY_FIND_ALL = "select * from TUSER";
	private static final String QRY_FIND_BY_ID = "select * from TUSER where id=?";
	private static final String QRY_FIND_BY_NAME = "select * from TUSER where username=?";
	private JdbcTemplate db;
	private MessageDigest digest;
	@Autowired private AppConfig config;
	
	public UserDaoImpl() throws NoSuchAlgorithmException {
		digest = MessageDigest.getInstance("MD5");
	}
	
	@PostConstruct
	public void setup() {
		db = new JdbcTemplate(config.getDataSource());
		int count = db.queryForInt(QRY_TABLE_CHECK);
		if(count<1){
			log.warn("Cannot find table TUSER! creating a new table!");
			db.execute(QRY_TABLE_CREATE);
			log.info("new table created!");
			db.execute(QRY_TABLE_ALTER);
			log.info("new constraint added into table!");
		}else{
			log.info("Found existing table!");
		}
	}
	
	@Override
	public int save(User user) {
		log.info("save: "+user.toString());
		digest.update(user.getPassword().getBytes(), 0, user.getPassword().length());
		user.setPassword(new BigInteger(1, digest.digest()).toString(16));
		if(user.getId()<1){
			log.info("insert: "+user.toString());
			return db.update(QRY_INSERT_USER, user.getUsername(), user.getPassword(), user.getRole());
		}else{
			log.info("update: "+user.toString());
			return db.update(QRY_UPDATE_USER, user.getUsername(), user.getPassword(), user.getRole(), user.getId());
		}
	}
	
	@Override
	public int delete(User user) {
		log.info("delete: "+user.toString());
		return db.update(QRY_DELETE_USER, user.getId());
	}
	
	@Override
	public User findById(int id) {
		log.info("findById: "+id);
		return db.queryForObject(QRY_FIND_BY_ID,new Object[]{id}, new UserRowMapper());
	}
	
	@Override
	public User findByName(String name) {
		log.info("findByName: "+name);
		return db.queryForObject(QRY_FIND_BY_NAME,new Object[]{name}, new UserRowMapper());
	}
	
	@Override
	public List<User> findAll() {
		log.info("findAll!");
		return db.query(QRY_FIND_ALL, new UserRowMapper());
	}
	
	class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int row) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getInt("role"));
			return user;
		}
	}
}
