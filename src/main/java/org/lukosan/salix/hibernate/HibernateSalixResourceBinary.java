package org.lukosan.salix.hibernate;

import java.io.IOException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.lukosan.salix.ResourceWriter;
import org.lukosan.salix.SalixResourceBinary;
import org.lukosan.salix.SalixResourceType;

@Entity
@DiscriminatorValue("0")
public class HibernateSalixResourceBinary extends HibernateSalixResource implements SalixResourceBinary {

	private static final long serialVersionUID = 1L;
	
	@Lob
	private byte[] bytes;
	private String contentType;

	public byte[] getBytes() {
		return bytes;
	}
	void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getContentType() {
		return contentType;
	}
	void setContentType(String contentType) {
		this.contentType = contentType;
	}
	@Transient
	public SalixResourceType getResourceType() {
		return SalixResourceType.BINARY;
	}
	@Override
	public void writeTo(ResourceWriter writer) throws IOException {
		writer.getOutputStream().write(getBytes());
	}
}