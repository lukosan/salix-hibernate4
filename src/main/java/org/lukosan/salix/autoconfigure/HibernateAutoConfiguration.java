package org.lukosan.salix.autoconfigure;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.lukosan.salix.SalixService;
import org.lukosan.salix.hibernate.HibernateSalixSecurityService;
import org.lukosan.salix.hibernate.HibernateSalixService;
import org.lukosan.salix.hibernate.SalixNamingStrategy;
import org.lukosan.salix.security.SalixSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
public class HibernateAutoConfiguration {

	@Configuration
	@EnableTransactionManagement
	public static class OrmConfig {
			
		@Autowired
		private DataSource dataSource;
		
		@Autowired
		private JpaProperties properties;
		
		@Bean
		@Primary
		public HibernateTransactionManager hibTransMan() {
			return new HibernateTransactionManager(sessionFactory());
		}

		@Bean(name="salixSessionFactory")
		public SessionFactory sessionFactory() {
			LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource).scanPackages("org.lukosan.salix");
			builder.addProperties(getHibernateProperties());
			builder.setNamingStrategy(new SalixNamingStrategy());
			return builder.buildSessionFactory();
		}

		public Properties getHibernateProperties() {
			Properties props =  new Properties();
			properties.getHibernateProperties(dataSource).entrySet().stream().forEach(e -> props.setProperty(e.getKey(), e.getValue()));
			//props.put("hibernate.dialect", ImprovedPostgreSQLDialect.class.getName());
			//props.put("hibernate.ejb.naming_strategy", NamingStrategy.class.getName());
			props.put("hibernate.show_sql", properties.isShowSql() ? "true" : "false");
			return props;
		}
		
		@Bean
		@ConditionalOnProperty(prefix = "salix.hibernate.service", name = "enabled", matchIfMissing = true)
		public SalixService salixService() {
			return new HibernateSalixService();
		}
		
		@Bean
		public SalixSecurityService salixSecurityService() {
			return new HibernateSalixSecurityService();
		}

	}
}