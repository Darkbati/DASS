package com.liberty.dataserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.WriteResult;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ConsistencyLevel;
import com.liberty.dataserver.cassandra.CassandraUserQuery;
import com.liberty.dataserver.cassandra.domain.User;

@Service("userService")
public class UserService {

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Value("${cassandra.ttl}")
	private String ttl;

	public WriteResult addUser(User user) {
		if (Integer.parseInt(ttl) <= 0) {
			return cassandraTemplate.insert(user, InsertOptions.builder().build());
		}

		// InsertOptions.builder().ttl(Duration.newInstance(0, 0, 0L)).build();
		return cassandraTemplate.insert(user, InsertOptions.builder().ttl(Integer.parseInt(ttl)).consistencyLevel(ConsistencyLevel.QUORUM).build());
	}

	public List<User> getUsers() {
		return cassandraTemplate.select(CassandraUserQuery.USER_QUERY, User.class);
	}

}
