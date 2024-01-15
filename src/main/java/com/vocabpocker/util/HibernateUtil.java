package com.vocabpocker.util;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.vocabpocker.model.User;

public class HibernateUtil {
	
	private static final SessionFactory concreteSessionFactory;
	static {
		try {
			Properties prop= new Properties();
			prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/vocabpocker");
			prop.setProperty("hibernate.connection.username", "root");
			prop.setProperty("hibernate.connection.password", "root123");
			prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			prop.setProperty("format_sql", "true");
			prop.setProperty("show_sql", "true");
			//prop.setProperty("hibernate.hbm2ddl.auto", "create-drop");
			prop.setProperty("hibernate.hbm2ddl.auto", "update");
			
			concreteSessionFactory = new AnnotationConfiguration()
		   .addPackage("com.vocabpocker.model")
				   .addProperties(prop)
				   .addAnnotatedClass(User.class)
				   .buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static SessionFactory getSessionFactory()
			throws HibernateException {
		return concreteSessionFactory;
	}
}