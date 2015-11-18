package org.lukosan.salix.hibernate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.lukosan.salix.SalixResourceText;
import org.lukosan.salix.SalixResourceType;

@Entity
@DiscriminatorValue("2")
public class HibernateSalixResourceText extends HibernateSalixResource implements SalixResourceText {
	
	private static final long serialVersionUID = 1L;
	
	private String text;
	private String contentType;

	public String getText() {
		return text;
	}
	void setText(String text) {
		this.text = text;
	}
	public String getContentType() {
		return contentType;
	}
	void setContentType(String contentType) {
		this.contentType = contentType;
	}
	@Transient
	public SalixResourceType getResourceType() {
		return SalixResourceType.TEXT;
	}
		
}