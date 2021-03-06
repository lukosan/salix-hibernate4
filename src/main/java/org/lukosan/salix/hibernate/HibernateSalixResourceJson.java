package org.lukosan.salix.hibernate;

import java.io.IOException;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.lukosan.salix.MapUtils;
import org.lukosan.salix.ResourceWriter;
import org.lukosan.salix.SalixResourceJson;
import org.lukosan.salix.SalixResourceType;

@Entity
@DiscriminatorValue("1")
public class HibernateSalixResourceJson extends HibernateSalixResource implements SalixResourceJson {

	private static final long serialVersionUID = 1L;
	
	@Embedded
	private HibernateSalixMap map = new HibernateSalixMap();

	public Map<String, Object> getMap() {
		return map;
	}
	void setMap(Map<String, Object> map) {
		this.map.setHashMap(map);
	}
	@Transient
	public SalixResourceType getResourceType() {
		return SalixResourceType.JSON;
	}
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void writeTo(ResourceWriter writer) throws IOException {
		writer.getWriter().write(MapUtils.asString(map));
	}

}
