package org.lukosan.salix.hibernate;

import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.lukosan.salix.MapUtils;
import org.lukosan.salix.security.SalixUser;

@Entity
public class HibernateSalixUser implements SalixUser {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	@Email @NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@Embedded
	private HibernateSalixMap map = new HibernateSalixMap();
	
	public Long getId() {
		return id;
	}
	void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	void setPassword(String password) {
		this.password = password;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	void setMap(Map<String, Object> map) {
		this.map.setHashMap(map);
	}
	@Override
	public String[] getRoles() {
		return MapUtils.getMap(getMap(), "roles").keySet().toArray(new String[0]);
	}
	@Override
	public boolean hasRole(String scope, String role) {
		return MapUtils.getStrings(getMap(), "roles."+role).contains(scope);
	}
}