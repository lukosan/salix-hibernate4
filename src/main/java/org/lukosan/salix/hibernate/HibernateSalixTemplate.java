package org.lukosan.salix.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.lukosan.salix.SalixTemplate;

@Entity
public class HibernateSalixTemplate implements SalixTemplate {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	private String scope;
	private String name;
	private String source;
	
	public Long getId() {
		return id;
	}
	void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	@Override
	public String getSource() {
		return source;
	}
	void setSource(String source) {
		this.source = source;
	}
	@Override
	public String getScope() {
		return scope;
	}
	void setScope(String scope) {
		this.scope = scope;
	}
}