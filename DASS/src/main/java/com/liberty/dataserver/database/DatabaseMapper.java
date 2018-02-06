package com.liberty.dataserver.database;

import org.springframework.stereotype.Repository;

@Repository	
public class DatabaseMapper extends SqlSessionFactorySupport {

	public int insert() {
		return 500;
	}
}
