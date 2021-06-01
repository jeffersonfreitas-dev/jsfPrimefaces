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
public class CrudGenericServiceImpl<T> implements CrudGenericService<T>, Serializable {
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
		sessionFactoryValid();
		sessionFactory.getCurrentSession().save(t);
		executeFlushSession();
		return t;
	}

	@Override
	public T persistent(T t) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().persist(t);
		executeFlushSession();
		return t;
	}

	@Override
	public void saveOrUpdate(T t) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().saveOrUpdate(t);
		executeFlushSession();
	}

	@Override
	public T update(T t) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().update(t);
		executeFlushSession();
		return t;
	}

	@Override
	public void delete(T t) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().delete(t);
		executeFlushSession();
	}

	@Override
	public T merge(T t) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().merge(t);
		executeFlushSession();
		return t;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findList(Class<T> entity) throws Exception {
		sessionFactoryValid();
		StringBuilder query = new StringBuilder();
		query.append("select distinct(e) from ").append(entity.getSimpleName()).append("e");
		List<T> list = sessionFactory.getCurrentSession().createQuery(query.toString()).list();
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(Class<T> entity, Long id) throws Exception {
		sessionFactoryValid();
		T result = (T) sessionFactory.getCurrentSession().load(getClass(), id);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findListByQueryDinamic(String s) throws Exception {
		sessionFactoryValid();
		return sessionFactory.getCurrentSession().createQuery(s).list();
	}

	//Executa query de HQL
	@Override
	public void executeUpdateQueryDinamic(String s) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void executeUpdateSQLDinamic(String s) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void clearSession() throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().clear();
	}

	//Tira o objeto da sessão
	@Override
	public void evict(Object objc) throws Exception {
		sessionFactoryValid();
		sessionFactory.getCurrentSession().evict(objc);
	}

	@Override
	public Session getSession() throws Exception {
		sessionFactoryValid();
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {
		sessionFactoryValid();
		List<?> result = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return result;
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
		sessionFactoryValid();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from ").append(table);
		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findListByQueryDinamic(String query, int initialRegister, int maxResult) throws Exception {
		sessionFactoryValid();
		List<T> list = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(initialRegister).setMaxResults(maxResult).list();
		return list;
	}

	private void transactionValid() {
		if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}

	private void sessionFactoryValid() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUtils.getSessionFactory();
		}
		transactionValid();
	}

	// Para transações realizadas via Ajax sem o conhecimento do JSF
	@SuppressWarnings("unused")
	private void transactionFilterAjaxCommited() {
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@SuppressWarnings("unused")
	private void transactionFilgerAjaxRollback() {
		sessionFactory.getCurrentSession().getTransaction().rollback();
	}

	// Salva instantaneo no BD - por padrão os objetos ficam em cache e são salvos
	// somento quando a transação é finalizada
	private void executeFlushSession() {
		sessionFactory.getCurrentSession().flush();
	}

}
