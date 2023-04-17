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

import java.awt.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.InvalidParameterException;


@NoArgsConstructor
@SuperBuilder
@Slf4j
public abstract class LibHttpEntityRequest extends LibHttp implements LibHttpRequest
{
	protected HttpModel fetchHttpModel( HttpMethod method )
	{
		if ( ! LibHttp.HttpMethod.isPostRequest( method ) )
		{
			throw new InvalidParameterException( "invalid httpMethod, must be one of POST, PATCH, PUT." );
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
			HttpEntityEnclosingRequestBase httpRequest = this._buildHttpRequestObject();
			log.debug( "postRequest :: executing request " + httpRequest.getRequestLine() );

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
			throw new AirExceptions.Timeout( String.format( "post request timeout, %s", e.getMessage() ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in fetchHttpModel, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to post request, %s", e.getMessage() ) );
		}
	}

	protected HttpResponseRaw fetchRaw( HttpMethod method )
	{
		if ( ! LibHttp.HttpMethod.isPostRequest( method ) )
		{
			throw new InvalidParameterException( "invalid httpMethod, must be one of POST, PATCH, PUT." );
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
			HttpEntityEnclosingRequestBase httpRequest = this._buildHttpRequestObject();
			log.debug( "postRequest :: executing request " + httpRequest.getRequestLine() );

			//
			//	execute the request
			//
			try ( CloseableHttpResponse response = httpClient.execute( httpRequest ) )
			{
				HttpEntity entity = response.getEntity();
				String body = ( null != entity ? EntityUtils.toString( entity ) : null );

				return HttpResponseRaw.builder()
					.status( response.getStatusLine().getStatusCode() )
					.body( body )
					.build();
			}
		}
		catch ( InterruptedIOException e )
		{
			e.printStackTrace();
			log.error( "InterruptedIOException in fetchRaw, {}", e.getMessage() );
			throw new AirExceptions.Timeout( String.format( "post request timeout, %s", e.getMessage() ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in fetchRaw, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to post request, %s", e.getMessage() ) );
		}
	}

	protected String fetchString( HttpMethod method ) throws IOException
	{
		try
		{
			HttpResponseRaw response = this.fetchRaw( method );
			if ( null != response )
			{
				return response.getBody();
			}

			return null;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in fetchString, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to post request, %s", e.getMessage() ) );
		}
	}


	protected StringEntity _buildRequestStringEntity()
	{
		StringEntity requestEntity = null;

		try
		{
			if ( LibHttp.HttpContentType.ApplicationJson == this.getContentType() )
			{
				//
				//	content type : JSON
				//
				String jsonString = "{}";
				if ( null != this.getPostData() )
				{
					if ( this.getPostData() instanceof String )
					{
						//	已经是 String
						jsonString = (String)this.getPostData();
					}
					else
					{
						//	json encode
						jsonString = new Gson().toJson( this.getPostData() );
					}
				}

				//	...
				requestEntity = new StringEntity( jsonString, ContentType.APPLICATION_JSON );
			}
		}
		catch ( Exception e )
		{
			log.info( "failed in _buildRequestStringEntity, {}", e.getMessage() );
			throw new AirExceptions.Execute( e.getMessage() );
		}

		return requestEntity;
	}

	protected HttpEntityEnclosingRequestBase _buildHttpRequestObject()
	{
		if ( Strings.isBlank( this.getUrl() ) )
		{
			throw new InvalidParameterException( "invalid url" );
		}

		HttpEntityEnclosingRequestBase httpRequest	= null;
		if ( HttpMethod.POST == this.getMethod() )
		{
			httpRequest = new HttpPost( this.getUrl() );
		}
		else if ( HttpMethod.PUT == this.getMethod() )
		{
			httpRequest = new HttpPut( this.getUrl() );
		}
		else if ( HttpMethod.PATCH == this.getMethod() )
		{
			httpRequest = new HttpPatch( this.getUrl() );
		}


		if ( null == httpRequest )
		{
			throw new AirExceptions.Unsupported( "failed to build post request by specified method, not supported method." );
		}

		try
		{
			//
			//	for POST, PUT, PATCH request
			//	build requesting data/post data
			//	set http content type and content value
			//
			StringEntity requestEntity = _buildRequestStringEntity();
			if ( null != requestEntity )
			{
				//	set post data, and it's content type to Http Header
				httpRequest.setEntity( requestEntity );
			}

			//
			//	normal headers
			//
			if ( Strings.isNotEmpty( this.getHeader().getContentTypeValue() ) )
			{
				httpRequest.setHeader( HttpHeaders.CONTENT_TYPE, this.getHeader().getContentTypeValue() );
			}
			else if ( LibHttp.HttpContentType.ApplicationJson == this.getContentType() )
			{
				httpRequest.setHeader( HttpHeaders.CONTENT_TYPE, "application/json" );
			}

			if ( Strings.isNotEmpty( this.getHeader().getUserAgentValue() ) )
			{
				httpRequest.setHeader( HttpHeaders.USER_AGENT, this.getHeader().getUserAgentValue() );
			}
			if ( Strings.isNotEmpty( this.getHeader().getAcceptValue() ) )
			{
				httpRequest.setHeader( HttpHeaders.ACCEPT, this.getHeader().getAcceptValue() );
			}
			if ( Strings.isNotEmpty( this.getHeader().getXTenantIDValue() ) )
			{
				httpRequest.setHeader( "X-TenantID", this.getHeader().getXTenantIDValue() );
			}

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
