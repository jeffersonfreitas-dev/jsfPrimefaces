package dev.jeffersonfreitas.caixaki.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface CrudGenericService<T> extends Serializable{
	
	T save (T t) throws Exception;
	
	T persistent (T t) throws Exception;
	
	void saveOrUpdate (T t) throws Exception;
	
	T update (T t) throws Exception;

	void delete (T t) throws Exception;

	T merge (T t) throws Exception;
	
	List<T> findList(Class<T> ts) throws Exception;
	
	T findById(Class<T> entity, Long id) throws Exception;
	
	List<T> findListByQueryDinamic(String s) throws Exception;
	
	void executeUpdateQueryDinamic(String s) throws Exception;
	
	void executeUpdateSQLDinamic(String s) throws Exception;
	
	void clearSession() throws Exception;
	
	void evict (Object objc) throws Exception;
	
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamica(String sql) throws Exception;
	
	JdbcTemplate getJdbcTemplate();
	
	SimpleJdbcTemplate getSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	Long totalRegisters(String table) throws Exception;
	
	List<T> findListByQueryDinamic(String query, int initialRegister, int maxResult) throws Exception;
	
	
}
