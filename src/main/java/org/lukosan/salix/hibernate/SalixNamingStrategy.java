package org.lukosan.salix.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.internal.util.StringHelper;

public class SalixNamingStrategy extends ImprovedNamingStrategy {

	private static final long serialVersionUID = 1L;

	@Override
	public String foreignKeyColumnName(String propertyName,
	        String propertyEntityName, String propertyTableName,
	        String referencedColumnName) {
		String s = super.foreignKeyColumnName(propertyName, propertyEntityName,
		        propertyTableName, referencedColumnName);
		return s.endsWith("_id") ? s : s + "_id";
	}

	@Override
	public String logicalColumnName(String columnName, String propertyName) {
		return StringHelper.isNotEmpty(columnName) ? columnName : unqualify(propertyName);
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		return addUnderscores(unqualify(propertyName));
	}

	// This deals with @Embedded classes by converting address.kv to address_kv
	private static String unqualify(String qualified) {

		if (qualified.contains("collection&&element.")) { // Collections of embedded
			int i = qualified.lastIndexOf("&&element.");
			qualified = qualified.substring(i + 10);
		}

		if (qualified.split("\\.").length > 0)
			return qualified.replace(".", "_");
		return StringHelper.unqualify(qualified);
	}

}