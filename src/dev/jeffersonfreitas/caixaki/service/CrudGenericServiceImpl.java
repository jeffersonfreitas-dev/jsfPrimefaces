package dev.jeffersonfreitas.caixaki.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.jeffersonfreitas.caixaki.session.HibernateUtils;

@Component
@Transactional
public class CrudGenericServiceImpl<T> implements CrudGenericService<T>, Serializable{
	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	
	@Autowired
	private JdbcTemplateImpl jdbcTemplate;
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;
	@Autowired
	private SimpleJdbcInsertImpl simpleJdbcInsert;
	

	@Override
	public T save(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T persistent(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T saveOrUpdate(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T update(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(T t) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T merge(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findList(Class<T> ts) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findById(Class<T> entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findListByQueryDinamic(String s) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeUpdateQueryDinamic(String s) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeUpdateSQLDinamic(String s) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSession() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evict(Object objc) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Session getSession() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return simpleJdbcInsert;
	}

	@Override
	public Long totalRegisters(String table) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findListByQueryDinamic(String query, int initialRegister, int maxResult) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	private void transactionValid() {
		if(!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}
	
	private void sessionFactoryValid() {
		if(sessionFactory == null) {
			sessionFactory = HibernateUtils.getSessionFactory();
		}
		
		transactionValid();
	}
	
	//Para transações realizadas via Ajax sem o conhecimento do JSF
	private void transactionFilterAjaxCommited() {
		sessionFactory.getCurrentSession().getTransaction().commit();
	}
	
	
	private void transactionFilgerAjaxRollback() {
		sessionFactory.getCurrentSession().getTransaction().rollback();
	}


}
