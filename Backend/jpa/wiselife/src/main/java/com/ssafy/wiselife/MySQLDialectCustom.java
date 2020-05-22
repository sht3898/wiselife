package com.ssafy.wiselife;

import org.hibernate.dialect.MySQL8Dialect;
//import org.hibernate.dialect.MySQLMyISAMDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLDialectCustom extends MySQL8Dialect {
	public MySQLDialectCustom() {
		super();

		registerFunction("MATCH",
				new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "MATCH(?1) AGAINST(?2 IN BOOLEAN MODE)"));
	}
}
