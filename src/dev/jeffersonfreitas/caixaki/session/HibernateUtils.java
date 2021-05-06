package dev.jeffersonfreitas.caixaki.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

@ApplicationScoped
public class HibernateUtils implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static String JAVA_COMP_ENV_JDBC_DATA_SOUCE = "java:/comp/env/jdbc/datasource";
	private static SessionFactory sessionFactory = buildlSessionFactory();
	
	
	//Ler o arquivo de configuração hibernate.cfg.xml
	private static SessionFactory buildlSessionFactory() {
		try {
			if(sessionFactory == null) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			return sessionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar o SessionFactory: " + e.getMessage());
		}
	}
	
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	
	public static Session openSession() {
		if(sessionFactory == null) {
			buildlSessionFactory();
		}
		return sessionFactory.openSession();
	}
	
	
	public static Connection getConnectionProvider() throws SQLException {
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}
	
	
	//Inicial context java:/comp/env/jdbc/datasource
	public static Connection getConnection() throws Exception {
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOUCE);
		return ds.getConnection();
	}
	

	//JNDI Tomcat
	public DataSource getDataSourceJNDI() throws NamingException {
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(VariableConnectionUtils.JAVA_COMP_ENV_JDBC_DATA_SOUCE);
	}
	
}
