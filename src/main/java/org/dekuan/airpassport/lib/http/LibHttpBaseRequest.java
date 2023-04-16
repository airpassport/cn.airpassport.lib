package org.dekuan.airpassport.lib.http;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.InvalidParameterException;


@NoArgsConstructor
@SuperBuilder
@Slf4j
public abstract class LibHttpBaseRequest extends LibHttp implements LibHttpRequest
{
	protected HttpModel fetchHttpModel( HttpMethod method )
	{
		if ( ! HttpMethod.isBaseRequest( method ) )
		{
			throw new InvalidParameterException( "invalid httpMethod, must be one of HEAD, GET, DELETE." );
		}

		//	...
		this.setMethod( method );

		//	...
		try ( CloseableHttpClient httpClient = HttpClients
			.custom()
			.setDefaultRequestConfig( this.buildCustomRequestConfig( this.getTimeout() ) )
			.build() )
		{
			//
			//	build http request
			//
			HttpRequestBase httpRequest = this._buildHttpRequestObject();
			log.debug( "getRequest :: executing request " + httpRequest.getRequestLine() );

			//
			//	execute the request
			//
			try ( CloseableHttpResponse response = httpClient.execute( httpRequest ) )
			{
				return this.parseResponse( response );
			}
		}
		catch ( InterruptedIOException e )
		{
			e.printStackTrace();
			log.error( "InterruptedIOException in fetchHttpModel, {}", e.getMessage() );
			throw new AirExceptions.Timeout( String.format( "get request timeout, %s", e.getMessage() ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in fetchHttpModel, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to get request, %s", e.getMessage() ) );
		}
	}

	protected HttpResponse fetchRaw( HttpMethod method ) throws IOException
	{
		if ( ! HttpMethod.isBaseRequest( method ) )
		{
			throw new InvalidParameterException( "invalid httpMethod, must be one of HEAD, GET, DELETE." );
		}

		//	...
		this.setMethod( method );

		//	...
		try ( CloseableHttpClient httpClient = HttpClients
			.custom()
			.setDefaultRequestConfig( this.buildCustomRequestConfig( this.getTimeout() ) )
			.build() )
		{
			//
			//	build http request
			//
			HttpRequestBase httpRequest = this._buildHttpRequestObject();
			log.debug( "getRequest :: executing request " + httpRequest.getRequestLine() );

			//
			//	execute the request
			//
			try ( CloseableHttpResponse response = httpClient.execute( httpRequest ) )
			{
				return response;
			}
		}
		catch ( InterruptedIOException e )
		{
			e.printStackTrace();
			log.error( "InterruptedIOException in fetchRaw, {}", e.getMessage() );
			throw new AirExceptions.Timeout( String.format( "get request timeout, %s", e.getMessage() ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in fetchRaw, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to get request, %s", e.getMessage() ) );
		}
	}

	protected String fetchString( HttpMethod method ) throws IOException
	{
		try
		{
			HttpResponse response = this.fetchRaw( method );
			if ( null != response )
			{
				HttpEntity entity = response.getEntity();
				return null != entity ? EntityUtils.toString( entity ) : null;
			}

			return null;
		}
		catch ( InterruptedIOException e )
		{
			e.printStackTrace();
			log.error( "InterruptedIOException in fetchString, {}", e.getMessage() );
			throw new AirExceptions.Timeout( String.format( "get request timeout, %s", e.getMessage() ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in fetchString, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to get request, %s", e.getMessage() ) );
		}
	}

	protected HttpRequestBase _buildHttpRequestObject()
	{
		if ( Strings.isBlank( this.getUrl() ) )
		{
			throw new InvalidParameterException( "invalid url" );
		}

		HttpRequestBase httpRequest	= null;
		if ( HttpMethod.HEAD == this.getMethod() )
		{
			httpRequest = new HttpHead( this.getUrl() );
		}
		else if ( HttpMethod.GET == this.getMethod() )
		{
			httpRequest = new HttpGet( this.getUrl() );
		}
		else if ( HttpMethod.DELETE == this.getMethod() )
		{
			httpRequest = new HttpDelete( this.getUrl() );
		}

		if ( null == httpRequest )
		{
			throw new AirExceptions.Unsupported( "failed to build get request by specified method, not supported method." );
		}

		try
		{
			//
			//	normal headers
			//
			httpRequest.setHeader( HttpHeaders.USER_AGENT, this.getHeader().getUserAgentValue() );
			httpRequest.setHeader( HttpHeaders.ACCEPT, this.getHeader().getAcceptValue() );
			httpRequest.setHeader( "X-TenantID", this.getHeader().getXTenantIDValue() );

			//
			//	process auth
			//
			if ( Auth.AuthType.Unset != this.getAuth().getAuthType() )
			{
				httpRequest.setHeader( this.getAuth().getAuthKey(), this.getAuth().getAuthValue() );
			}

			//
			//	process custom headers
			//
			if ( null != this.getHeader().getCustomHeaders() )
			{
				for ( String customKey : this.getHeader().getCustomHeaders().keySet() )
				{
					customKey	= customKey.trim();
					String customValue	= this.getHeader().getCustomHeaders().get( customKey );
					if ( Strings.isNotBlank( customValue ) )
					{
						httpRequest.setHeader( customKey, customValue );
					}
				}
			}
		}
		catch ( Exception e )
		{
			log.info( "failed in _buildHttpRequestObject, {}", e.getMessage() );
			throw new AirExceptions.Execute( e.getMessage() );
		}

		//	...
		return httpRequest;
	}
}
