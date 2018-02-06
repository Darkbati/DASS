package com.liberty.dataserver.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DatabaseSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getDataSourceType();
	}
}
