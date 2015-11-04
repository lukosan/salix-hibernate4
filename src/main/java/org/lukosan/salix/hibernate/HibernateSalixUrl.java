package org.lukosan.salix.hibernate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.lukosan.salix.SalixUrl;
import org.springframework.http.HttpStatus;

@Entity
public class HibernateSalixUrl implements SalixUrl {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	private String scope;
	private String url;
	private int httpStatus;
	private String view;
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime published;
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	private LocalDateTime removed;
	@Embedded
	private HibernateSalixMap map = new HibernateSalixMap();
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public HttpStatus getStatus() {
		return Arrays.stream(HttpStatus.values()).filter(i -> i.value() == httpStatus).findFirst().get();
	}

	@Override
	public String getView() {
		return view;
	}

	public LocalDateTime getPublished() {
		return published;
	}

	void setPublished(LocalDateTime published) {
		this.published = published;
	}

	public LocalDateTime getRemoved() {
		return removed;
	}

	void setRemoved(LocalDateTime removed) {
		this.removed = removed;
	}

	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	void setView(String view) {
		this.view = view;
	}
	
	public String getScope() {
		return scope;
	}
	
	void setScope(String scope) {
		this.scope = scope;
	}
	
	public Map<String, Object> getMap() {
		return salixMap();
	}
	
	void setMap(Map<String, Object> map) {
		salixMap().setHashMap(map);
	}
	
	private HibernateSalixMap salixMap() {
		if(null == map)
			map = new HibernateSalixMap();
		return map;
	}
}