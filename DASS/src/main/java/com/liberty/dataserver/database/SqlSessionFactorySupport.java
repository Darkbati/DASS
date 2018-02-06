package com.liberty.dataserver.database;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class SqlSessionFactorySupport {

	@Autowired
	private Map<String, DataSource> dataSourceMap;

	public DataSource dataSourceMap(String key) {
		return dataSourceMap.get(key);
	}

}
