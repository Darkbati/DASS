package com.liberty.dataserver.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class ReloadApplicationProperties {
	private static Logger logger = Logger.getLogger(ReloadApplicationProperties.class);
	private static PropertiesConfiguration configuration = null;
	private static final HashMap<String, Object> callbackMap = new HashMap<String, Object>();
	private static final HashMap<String, Object> propertyMap = new HashMap<String, Object>();

	static {
		try {
			String configpath = System.getProperty("service.config");
			if (StringUtils.isEmpty(configpath)) {
				throw new Exception("not found the config properties");
			}

			configuration = new PropertiesConfiguration(configpath);
			FileChangedReloadingStrategy reloadStrategy = new FileChangedReloadingStrategy();
			reloadStrategy.setRefreshDelay(1000);
			configuration.setReloadingStrategy(reloadStrategy);
			configuration.addConfigurationListener(new ConfigurationListener() {
				@Override
				public void configurationChanged(ConfigurationEvent event) {
					if (event == null) {
						return;
					}
					if (configuration == null) {
						return;
					}
					if (propertyMap == null) {
						return;
					}
					if (!event.isBeforeUpdate()) {
						switch (event.getType()) {
						case AbstractFileConfiguration.EVENT_RELOAD: {
							event(event);
							break;
						}
						default:
							break;
						}
					}
				}
			});

			Iterator<String> it = configuration.getKeys();
			while (it.hasNext()) {
				String key = it.next();
				Object value = configuration.getProperty(key);
				propertyMap.put(key, value);
			}
		} catch (ConfigurationException e) {
			logger.error("SYSTEM: System Reason=[Error while reading the file Properties. ConfigurationException],ExceptionMessage=", e);
		} catch (Exception e) {
			logger.error("SYSTEM: System Reason=[Error while reading the file Properties. Exception],ExceptionMessage=", e);
		} finally {
			System.out.println("");
		}
	}

	public static void registerCallback(String propertiesName, CallBackEvent event) {
		synchronized (callbackMap) {
			callbackMap.put(propertiesName, event);
		}
	}

	public static synchronized String getProperty(final String key, final String defaultValue) {
		if (configuration == null)
			return defaultValue;

		synchronized (configuration) {
			return configuration.getString(key);
		}
	}

	public static synchronized String[] getPropertyStringArray(final String key) {
		if (configuration == null)
			return null;

		synchronized (configuration) {
			return configuration.getStringArray(key);
		}
	}

	public static void setProperty(String key, String value) {
		if (configuration == null)
			return;

		synchronized (configuration) {
			configuration.setProperty(key, value);
			try {
				configuration.save();
			} catch (ConfigurationException e) {
				logger.error("SYSTEM: System Reason=[ReloadApplicationProperties.ConfigurationException error],ExceptionMessage=", e);
			}
		}
	}

	public static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@SuppressWarnings("unchecked")
	private static void event(ConfigurationEvent event) {
		if (callbackMap == null) {
			return;
		}

		synchronized (callbackMap) {
			Iterator<String> it = configuration.getKeys();
			while (it.hasNext()) {
				try {
					String key = it.next();
					Object newValue = configuration.getProperty(key);
					Object orgValue = propertyMap.get(key);
					if (orgValue != null) {
						if (newValue.getClass().getName().equals("java.lang.String")) {
							if (orgValue.equals(newValue) == false) {
								CallBackEvent callback = (CallBackEvent) callbackMap.get(key);
								if (callback != null) {
									callback.callback(callback, key, newValue);
								}
							}
						} else if (newValue.getClass().getName().equals("java.util.ArrayList")) {
							ArrayList<String> arrNewValue = (ArrayList<String>) newValue;
							ArrayList<String> arrOrgValue = (ArrayList<String>) orgValue;
							if (arrNewValue.size() != arrOrgValue.size()) {
								CallBackEvent callback = (CallBackEvent) callbackMap.get(key);
								if (callback != null) {
									callback.callback(callback, key, newValue);
								}
								continue;
							}
							for (Integer index = 0; index < arrNewValue.size(); index++) {
								if (arrNewValue.get(index).equals(arrOrgValue.get(index))) {
									CallBackEvent callback = (CallBackEvent) callbackMap.get(key);
									if (callback != null) {
										callback.callback(callback, key, newValue);
										break;
									}
								}
							}
						}
					}
					propertyMap.put(key, newValue);
				} catch (Exception e) {
					logger.error("SYSTEM: System Reason=[reload to the Configuration Files. Exception],ExceptionMessage=", e);
				} finally {

				}
			}
		}
	}
}
