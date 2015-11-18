package org.lukosan.salix.hibernate;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Transient;

import org.lukosan.salix.SalixResource;
import org.lukosan.salix.SalixResourceType;

@Entity
@Inheritance
@DiscriminatorColumn(name="resource_type", discriminatorType=DiscriminatorType.INTEGER)
public abstract class HibernateSalixResource implements SalixResource {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	private String scope;
	private String sourceId;
	private String sourceUri;
	
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
	public String getSourceUri() {
		return sourceUri;
	}
	void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}
	public String getResourceId() {
		return getId().toString();
	}
	public String getResourceUri() {
		return "/salix/resource/" + getResourceId(); 
	}
	@Transient
	public abstract SalixResourceType getResourceType();
}
