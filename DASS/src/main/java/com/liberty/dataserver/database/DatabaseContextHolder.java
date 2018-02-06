package com.liberty.dataserver.database;

public class DatabaseContextHolder {
	private static final ThreadLocal<DatabaseSourceType> contextHolder = new ThreadLocal<DatabaseSourceType>();

	public static void setDataSourceType(DatabaseSourceType dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static DatabaseSourceType getDataSourceType() {
		return contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}
}
