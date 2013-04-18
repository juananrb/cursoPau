package com.ejemplos;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class ClienteHttp {
	private static HttpClient customHttpClient;

	public ClienteHttp() {
		// TODO Auto-generated constructor stub
	}

	public static synchronized HttpClient getHttpCliente() {

		if (customHttpClient == null) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params,
					HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);

			/*
			 * Sets the timeout in milliseconds used when retrieving a
			 * ManagedClientConnection from the ClientConnectionManager.
			 */
			ConnManagerParams.setTimeout(params, 1000);
			/*
			 * Sets the timeout until a connection is established. A value of
			 * zero means the timeout is not used. The default value is zero.
			 */
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			/*
			 * Sets the default socket timeout (SO_TIMEOUT) in milliseconds
			 * which is the timeout for waiting for data. A timeout value of
			 * zero is interpreted as an infinite timeout. This value is used
			 * when no socket timeout is set in the method parameters.
			 */
			HttpConnectionParams.setSoTimeout(params, 10000);
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);

			customHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customHttpClient;
	}

}
