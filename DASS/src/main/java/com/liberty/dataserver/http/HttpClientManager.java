package com.liberty.dataserver.http;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

import com.liberty.dataserver.common.ConverterNumber;
import com.liberty.dataserver.config.ReloadApplicationProperties;

@Component
public class HttpClientManager {
	private PoolingHttpClientConnectionManager connectionManager = null;
	private HttpClientBuilder httpClientBuilder = null;

	public HttpClientManager() {
		Integer Timeout = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.Timeout", "3000"), "3000");
		Integer MaxTotal = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.MaxTotal", "1000"), "1000");
		Integer MaxPerRoute = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.MaxPerRoute", "10"), "10");
		Integer SocketTimeout = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.SocketTimeout", "3000"), "3000");
		Integer RequestTimeout = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.RequestTimeout", "3000"), "3000");
		Integer ConnectTimeout = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.ConnectTimeout", "3000"), "3000");
		Integer SoLinger = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.SoLinger", "200"), "200");
		String TcpNoDelay = ReloadApplicationProperties.getProperty("Http.TcpNoDelay", "true");
		String KeepAlive = ReloadApplicationProperties.getProperty("Http.KeepAlive", "false");
		String SoReuseAddress = ReloadApplicationProperties.getProperty("Http.SoReuseAddress", "true");
		Integer HttpRequestRetry = ConverterNumber.parseInt(ReloadApplicationProperties.getProperty("Http.RequestRetry", "3"), "3");

		connectionManager = new PoolingHttpClientConnectionManager(5, TimeUnit.MICROSECONDS);
		connectionManager.setMaxTotal(MaxTotal);
		connectionManager.setDefaultMaxPerRoute(MaxPerRoute);
		connectionManager.closeIdleConnections(5L, TimeUnit.MICROSECONDS);
		connectionManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(Boolean.parseBoolean(TcpNoDelay))
				.setSoKeepAlive(Boolean.parseBoolean(KeepAlive)).setSoLinger(SoLinger)
				.setSoReuseAddress(Boolean.parseBoolean(SoReuseAddress)).setSoTimeout(Timeout)
				.build());

		httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(HttpRequestRetry, false));
		httpClientBuilder.setConnectionManagerShared(true);
		httpClientBuilder.setConnectionManager(connectionManager);
		httpClientBuilder
				.setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(SocketTimeout)
						.setConnectionRequestTimeout(RequestTimeout)
						.setConnectTimeout(ConnectTimeout).build());
	}

	public synchronized CloseableHttpClient createHttpClient() {
		return httpClientBuilder.build();
	}
}