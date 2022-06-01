package org.dekuan.airpassport.lib.http;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.io.InterruptedIOException;
import java.security.InvalidParameterException;


@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpPost extends LibHttp
{
	public HttpModel postRequest()
	{
		if ( ! LibHttp.HttpMethod.isPostRequest( this.getMethod() ) )
		{
			throw new InvalidParameterException( "invalid httpMethod, must be one of POST, PATCH, PUT." );
		}

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
			log.error( "InterruptedIOException in postRequest, {}", e.getMessage() );
			throw new AirExceptions.Timeout( String.format( "post request timeout, %s", e.getMessage() ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in postRequest, {}", e.getMessage() );
			throw new AirExceptions.Execute( String.format( "failed to post request, %s", e.getMessage() ) );
		}
	}


	private StringEntity _buildRequestStringEntity()
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
					jsonString = new Gson().toJson( this.getPostData() );
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

	private HttpEntityEnclosingRequestBase _buildHttpRequestObject()
	{
		if ( Strings.isBlank( this.getUrl() ) )
		{
			throw new InvalidParameterException( "invalid url" );
		}

		HttpEntityEnclosingRequestBase httpRequest	= null;
		if ( LibHttp.HttpMethod.POST == this.getMethod() )
		{
			httpRequest = new HttpPost( this.getUrl() );
		}
		else if ( LibHttp.HttpMethod.PATCH == this.getMethod() )
		{
			httpRequest = new HttpPatch( this.getUrl() );
		}
		else if ( LibHttp.HttpMethod.PUT == this.getMethod() )
		{
			httpRequest = new HttpPut( this.getUrl() );
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
