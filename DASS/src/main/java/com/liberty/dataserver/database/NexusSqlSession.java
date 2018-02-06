package com.liberty.dataserver.database;

import org.apache.ibatis.session.SqlSession;

public interface NexusSqlSession extends SqlSession {

	public default int insertAll(String statement) {
		return this.insert(statement);
	}

	public default int insertAll(String statement, Object parameter) {
		return this.insert(statement, parameter);
	}

	public default int updateAll(String statement) {
		return this.update(statement);
	}

	public default int updateAll(String statement, Object parameter) {
		return this.update(statement, parameter);
	}

	public default int deleteAll(String statement) {
		return this.delete(statement);
	}

	public default int deleteAll(String statement, Object parameter) {
		return this.delete(statement, parameter);
	}
}
