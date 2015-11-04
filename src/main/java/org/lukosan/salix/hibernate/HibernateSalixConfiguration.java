package org.lukosan.salix.hibernate;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.lukosan.salix.SalixConfiguration;

@Entity
public class HibernateSalixConfiguration implements SalixConfiguration {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long id;
	private String scope;
	private String target;
	@Embedded
	private HibernateSalixMap map = new HibernateSalixMap();
	
	public Long getId() {
		return id;
	}
	void setId(Long id) {
		this.id = id;
	}
	public String getScope() {
		return scope;
	}
	void setScope(String scope) {
		this.scope = scope;
	}
	public String getTarget() {
		return target;
	}
	void setTarget(String target) {
		this.target = target;
	}
	public Map<String, Object> getMap() {
		if(null == map)
			setMap(new HashMap<String, Object>());
		return map;
	}
	void setMap(Map<String, Object> map) {
		this.map.setHashMap(map);
	}
}