package org.dekuan.airpassport.lib.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@SuperBuilder
public class LibHttpPost extends LibHttp
{
	public static void main( String[] args )
	{
		final String jwtToken	= "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNjU2Mjc2NzAxLCJ1c2VySWQiOjMzLCJpYXQiOjE2NTM2ODQ3MDEsImp0aSI6IjViNTUwOWRlLTQ5MGItNDIxZS05OWM3LTFlMzIxYWU2MzBjMyJ9.YxNt-emUohSq0-oEzxJNK9TqcgheH-9Y_NposLv8RoI97uki2FDU66wKNSGcaodHRmPgL8XJt_trT4VXwPg1DeXuKFppVQYJ0RweabXpum8ON5KYIAxvru0fOSH_pfiI8X-FOxV_vZaT7DuHus7SFNbjFmPtetukA2Q4NvSMGmschS76jnbkwMc38E3sVjzSfFgU2Y-ul5UATTV2OevgUXFHZ9dQvaP3G_b1hQTgsPrpH3A-pODs6YRAIQPt-BJRqFudB2fP5276ar1fODLBx4-KTHfLSsqbQWmlz28W8JEPHzodTyCBI0tjBrpBiMoxgfvfbO1IhtmMpSrg_rikNw";

		String mailAddress = String.format( "rnd%d@163.com", System.currentTimeMillis() );
		HashMap<String,Object> postData = new HashMap<>();
		postData.put( "tenantId", 1 );
		postData.put( "userId", 1 );
		postData.put( "mailAddress", mailAddress );
		postData.put( "mailPopHost", "pop.163.com" );
		postData.put( "mailPopPort", 995 );
		postData.put( "mailPopSSL", true );
		postData.put( "mailSmtpHost", "smtp.163.com" );
		postData.put( "mailSmtpPort", 465 );
		postData.put( "mailSmtpSSL", true );
		postData.put( "mailUsername", mailAddress );
		postData.put( "mailPassword", "11111" );

		LibHttpPost libHttp = LibHttpPost.builder()
			.url( "http://localhost:2022/api/v1/mails/user/mails" )
			.method( HttpMethod.POST )
			.contentType( HttpContentType.ApplicationJson )
			.postData( postData )
			.auth( Auth.builder()
				.authType( Auth.AuthType.Bearer )
				.bearerToken( jwtToken )
				.build() )
			.header( Header.builder()
				.xTenantIDValue( "1" )
				.build() )
			.build();
		HttpModel httpModel = libHttp.postRequest();
		log.debug( "httpModel : {}", httpModel.toString() );
	}



	public HttpModel postRequest()
	{
		if ( ! LibHttp.HttpMethod.isPostRequest( this.getMethod() ) )
		{
			throw new InvalidLibException( "invalid httpMethod, must be one of POST, PATCH, PUT." );
		}

		//	...
		try ( CloseableHttpClient httpClient = HttpClients
			.custom()
			.setDefaultRequestConfig( this._buildCustomRequestConfig( this.getTimeout() ) )
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
				return this._parseResponse( response );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( "exception in postRequest, {}", e.getMessage() );
			throw new InvalidLibException( String.format( "failed to post request, %s", e.getMessage() ) );
		}
	}

	private HttpModel _parseResponse( HttpResponse response )
	{
		int statusCode	= 0;

		try
		{
			//	EntityUtils.consumeQuietly( response.getEntity() );
			statusCode = response.getStatusLine().getStatusCode();
			log.debug( "_parseResponse :: statusCode {}", statusCode );

			HttpEntity entity = response.getEntity();
			String bodyString = null != entity ? EntityUtils.toString( entity ) : "";

			//	...
			log.debug( "_parseResponse :: body {}", bodyString );
			JsonObject bodyJson = new Gson().fromJson( bodyString, JsonObject.class );
			if ( null == bodyJson )
			{
				throw new NullPointerException( "body is null or the data format is not json" );
			}

			//
			//	do a step by step json parsing, instead of HttpModel.fromString
			//
			String version	= com.google.common.base.Strings.nullToEmpty( bodyJson.get( "version" ).getAsString() );
			String error	= com.google.common.base.Strings.nullToEmpty( bodyJson.get( "error" ).getAsString() );
			String message	= com.google.common.base.Strings.nullToEmpty( bodyJson.get( "message" ).getAsString() );
			String traceId	= com.google.common.base.Strings.nullToEmpty( bodyJson.get( "traceId" ).getAsString() );
			long timestamp	= bodyJson.get( "timestamp" ).getAsLong();
			Object body	= new HashMap<>();
			JsonElement bodyElement = bodyJson.get( "body" );
			if ( null != bodyElement )
			{
				if ( bodyElement.isJsonArray() )
				{
					body = bodyElement.getAsJsonArray();
				}
				else if ( bodyElement.isJsonObject() )
				{
					//	body = bodyElement.getAsJsonObject();
					try
					{
						body = new Gson().fromJson( bodyElement.getAsJsonObject().toString(), Map.class );
					}
					catch ( Exception ignored )
					{
						log.error( "_parseResponse :: exception in parsing body to map." );
					}
				}
			}

			return HttpModel.httpModelBuilder()
				.status( statusCode )
				.version( version )
				.error( error )
				.message( message )
				.traceId( traceId )
				.timestamp( timestamp )
				.body( body )
				.build();
		}
		catch ( Exception e )
		{
			log.error( "_parseResponse :: exception {}", e.getMessage() );
			return HttpModel.httpModelBuilder()
				.status( statusCode )
				.error( "local exception" )
				.message( e.getMessage() )
				.build();
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
			throw new InvalidLibException( e.getMessage() );
		}

		return requestEntity;
	}

	private HttpEntityEnclosingRequestBase _buildHttpRequestObject()
	{
		if ( Strings.isBlank( this.getUrl() ) )
		{
			throw new InvalidLibException( "invalid url" );
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
			throw new InvalidLibException( "failed to build http request by specified method, not supported method." );
		}

		try
		{
			//
			//	build requesting data
			//	set http content type and content value
			//
			StringEntity requestEntity = _buildRequestStringEntity();
			if ( null != requestEntity )
			{
				httpRequest.setEntity( requestEntity );
			}

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
			throw new InvalidLibException( e.getMessage() );
		}

		//	...
		return httpRequest;
	}
}
