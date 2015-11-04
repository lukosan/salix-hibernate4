package org.lukosan.salix.hibernate;

import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.id.IdentityGenerator;

public class SalixPostgreSQLDialect extends PostgreSQL9Dialect {

	public SalixPostgreSQLDialect() {
		super();
		registerColumnType(Types.JAVA_OBJECT, "jsonb");
	}
	
	/**
	 * Overrides the default column length for VARCHARs which is 255 and instead uses the
	 * variable PostgreSQL limit
	 */
	@Override
	public String getTypeName(int code, long length, int precision, int scale) throws HibernateException {

		if (Types.VARCHAR == code && length == org.hibernate.mapping.Column.DEFAULT_LENGTH) {
			return "varchar";
		}
		if (Types.NUMERIC == code && precision == org.hibernate.mapping.Column.DEFAULT_PRECISION && 
				scale == org.hibernate.mapping.Column.DEFAULT_SCALE ) {
			return "numeric";
		}
		return super.getTypeName(code, length, precision, scale);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Class getNativeIdentifierGeneratorClass() {
		return IdentityGenerator.class;
	}

}
