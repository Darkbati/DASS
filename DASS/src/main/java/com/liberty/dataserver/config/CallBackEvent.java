package com.liberty.dataserver.config;

public interface CallBackEvent {
	public void callback(CallBackEvent instance, String propertyName, Object propertyValue);
}
