package org.dekuan.airpassport.lib.network;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.exceptions.InvalidLibException;

import java.io.IOException;

/**
 * Using Apache Http Client
 */
@Slf4j
public class LibHttp
{
	public final static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36";

	public final static int CONNECTION_REQUEST_TIMEOUT_MS = 1000;
	public final static int CONNECT_TIMEOUT_MS            = 1000;
	public final static int SOCKET_TIMEOUT_MS             = 1000;

	public enum HttpMethod
	{
		HEAD,
		GET,
		POST,
		PUT,
		PATCH;

		public static boolean isPostRequest( HttpMethod method )
		{
			return POST == method || PUT == method || PATCH == method;
		}
	}

	public enum HttpContentType
	{
		ApplicationJson
	}

	public void postRequest
		(
			String url,
			HttpMethod httpMethod,
			HttpContentType httpContentType,
			Object postData,
			int timeout
		)
	{
		if ( ! HttpMethod.isPostRequest( httpMethod ) )
		{
			throw new InvalidLibException( "invalid httpMethod, must be one of POST, PATCH, PUT." );
		}

		try ( CloseableHttpClient httpClient = HttpClients
			.custom()
			.setDefaultRequestConfig( this._buildCustomRequestConfig( timeout ) )
			.build() )
		{
			//
			//	build http request
			//
			HttpEntityEnclosingRequestBase httpRequest = this._buildHttpRequestObject
				(
					url,
					httpMethod,
					httpContentType,
					postData
				);

			//
			//	execute the request
			//
			try ( CloseableHttpResponse response = httpClient.execute( httpRequest ) )
			{
				EntityUtils.consumeQuietly( response.getEntity() );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in postRequest, {}", e.getMessage() );
			throw new InvalidLibException( String.format( "failed to post request, %s", e.getMessage() ) );
		}
	}

	private RequestConfig _buildCustomRequestConfig( int timeout )
	{
		return RequestConfig
			.custom()
			.setConnectionRequestTimeout( timeout > 0 ? timeout : CONNECTION_REQUEST_TIMEOUT_MS )
			.setConnectTimeout( timeout > 0 ? timeout : CONNECT_TIMEOUT_MS )
			.setSocketTimeout( timeout > 0 ? timeout : SOCKET_TIMEOUT_MS )
			.build();
	}

	private StringEntity _buildRequestStringEntity( HttpContentType httpContentType, Object postData )
	{
		if ( null == postData )
		{
			return null;
		}

		StringEntity requestEntity = null;

		try
		{
			if ( HttpContentType.ApplicationJson == httpContentType )
			{
				String jsonData = new Gson().toJson( postData );
				if ( Strings.isNotBlank( jsonData ) )
				{
					requestEntity = new StringEntity( jsonData, ContentType.APPLICATION_JSON );
				}
			}
		}
		catch ( Exception e )
		{
			log.info( "failed in _buildRequestStringEntity, {}", e.getMessage() );
			throw new InvalidLibException( e.getMessage() );
		}

		return requestEntity;
	}

	private HttpEntityEnclosingRequestBase _buildHttpRequestObject
		(
			String url,
			HttpMethod httpMethod,
			HttpContentType httpContentType,
			Object postData
		)
	{
		HttpEntityEnclosingRequestBase httpRequest	= null;
		if ( HttpMethod.POST == httpMethod )
		{
			httpRequest = new HttpPost( url );
		}
		else if ( HttpMethod.PATCH == httpMethod )
		{
			httpRequest = new HttpPatch( url );
		}
		else if ( HttpMethod.PUT == httpMethod )
		{
			httpRequest = new HttpPut( url );
		}

		if ( null == httpRequest )
		{
			throw new InvalidLibException( "failed to build http request by specified method, not supported method." );
		}

		try
		{
			//
			//	build requesting data
			//
			StringEntity requestEntity = _buildRequestStringEntity( httpContentType, postData );
			if ( null != requestEntity )
			{
				httpRequest.setEntity( requestEntity );
			}

			httpRequest.setHeader("User-Agent", userAgent );
		}
		catch ( Exception e )
		{
			log.info( "failed in _buildHttpRequestObject, {}", e.getMessage() );
			throw new InvalidLibException( e.getMessage() );
		}

		//	...
		return httpRequest;
	}
}
