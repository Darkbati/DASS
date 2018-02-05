package com.liberty.dataserver.cassandra.domain;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "userid", ordinal = 1, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String userid;

	@Column(value = "emails")
	private String emails;

	@Column(value = "first_name")
	private String first_name;

	@Column(value = "last_name")
	private String last_name;

	@JsonCreator
	public User(@JsonProperty("userid") String userid, @JsonProperty("emails") String emails,
			@JsonProperty("first_name") String first_name, @JsonProperty("last_name") String last_name) {
		this.userid = userid;
		this.emails = emails;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Override
	public int hashCode() {
		int result = userid.hashCode();
		result = 31 * result + emails.hashCode();
		result = 31 * result + first_name.hashCode();
		result = 31 * result + last_name.hashCode();
		return result;
	}
}
