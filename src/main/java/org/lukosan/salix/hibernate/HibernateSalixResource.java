package org.lukosan.salix.hibernate;

import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.lukosan.salix.SalixResource;

@Entity
public class HibernateSalixResource implements SalixResource {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	private String scope;
	private String sourceId;
	private String sourceUri;
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
	public String getSourceId() {
		return sourceId;
	}
	void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	void setMap(Map<String, Object> map) {
		this.map.setHashMap(map);
	}
	public String getSourceUri() {
		return sourceUri;
	}
	void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}
}
