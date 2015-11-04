package org.lukosan.salix.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Embeddable
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class HibernateSalixMap implements Map<String, Object>, Serializable {

	private static final long serialVersionUID = 1L;

	@Type(type = "org.lukosan.salix.hibernate.JsonUserType", parameters = { @Parameter(name = "classType", value = "java.util.HashMap") })
	private Map<String, Object> hashMap = new HashMap<String, Object>();

	@Override
	public int size() {
		return getHashMap().size();
	}

	@Override
	public boolean isEmpty() {
		return getHashMap().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return getHashMap().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return getHashMap().containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return getHashMap().get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return getHashMap().put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return getHashMap().remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		getHashMap().putAll(m);
	}

	@Override
	public void clear() {
		getHashMap().clear();
	}

	@Override
	public Set<String> keySet() {
		return getHashMap().keySet();
	}

	@Override
	public Collection<Object> values() {
		return getHashMap().values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return getHashMap().entrySet();
	}

	private Map<String, Object> getHashMap() {
		if(null == hashMap)
			setHashMap(new HashMap<String, Object>());
		return hashMap;
	}

	void setHashMap(Map<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

}