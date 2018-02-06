package com.liberty.dataserver.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public class NexusSqlSessionTemplate extends SqlSessionTemplate {
	public NexusSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	public int insertAll(String statement) {
		return this.insert(statement);
	}

	public int insertAll(String statement, Object parameter) {
		return this.insert(statement, parameter);
	}

	public int updateAll(String statement) {
		return this.update(statement);
	}

	public int updateAll(String statement, Object parameter) {
		return this.update(statement, parameter);
	}

	public int deleteAll(String statement) {
		return this.delete(statement);
	}

	public int deleteAll(String statement, Object parameter) {
		return this.delete(statement, parameter);
	}
}
