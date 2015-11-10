package org.lukosan.salix.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.lukosan.salix.MapUtils;
import org.lukosan.salix.security.SalixSecurityService;
import org.lukosan.salix.security.SalixUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Transactional
public class HibernateSalixSecurityService implements SalixSecurityService {

	@Autowired @Qualifier("salixSessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public SalixUser user(String username) {
		return (SalixUser) getSession().createCriteria(HibernateSalixUser.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
	}

	@Override
	public SalixUser save(String username, String password) {
		HibernateSalixUser user = new HibernateSalixUser();
		user.setUsername(username);
		user.setPassword(password);
		getSession().saveOrUpdate(user);
		return user;
	}

	@Override
	public void allow(String username, String scope, String role) {
		HibernateSalixUser user = (HibernateSalixUser) getSession().createCriteria(HibernateSalixUser.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
		MapUtils.getStrings(user.getMap(), "roles."+role).add(scope);
		getSession().saveOrUpdate(user);
	}

	@Override
	public void deny(String username, String scope, String role) {
		HibernateSalixUser user = (HibernateSalixUser) getSession().createCriteria(HibernateSalixUser.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
		MapUtils.getStrings(user.getMap(), "roles."+role).remove(scope);
		getSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixUser> users(String scope) {
		List<HibernateSalixUser> users = getSession().createCriteria(HibernateSalixUser.class).list();
		List<SalixUser> filtered = new ArrayList<SalixUser>();
		for(HibernateSalixUser user : users) {
			for(String key : MapUtils.getMap(user.getMap(), "roles").keySet()) {
				if(MapUtils.getStrings(user.getMap(), "roles." + key).contains(scope)) {
					filtered.add(user);
					break;
				}
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixUser> users(String scope, String role) {
		List<HibernateSalixUser> users = getSession().createCriteria(HibernateSalixUser.class).list();
		List<SalixUser> filtered = new ArrayList<SalixUser>();
		for(HibernateSalixUser user : users) {
			if(MapUtils.getStrings(user.getMap(), "roles." + role).contains(scope)) {
				filtered.add(user);
				break;
			}
		}
		return filtered;
	}
}
