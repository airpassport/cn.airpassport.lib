package org.dekuan.airpassport.lib.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.util.EntityUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Using Apache Http Client
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Slf4j
public abstract class LibHttp
{
	public final static String defaultUserAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36";

	public final static int CONNECTION_REQUEST_TIMEOUT_MS = 1000;
	public final static int CONNECT_TIMEOUT_MS            = 1000;
	public final static int SOCKET_TIMEOUT_MS             = 1000;

	public enum HttpMethod
	{
		HEAD,
		GET,
		POST,
		PUT,
		PATCH,
		DELETE;

		public static boolean isBaseRequest( HttpMethod method )
		{
			return GET == method || HEAD == method || DELETE == method;
		}
		public static boolean isPostRequest( HttpMethod method )
		{
			return POST == method || PUT == method || PATCH == method;
		}
	}

	public enum HttpContentType
	{
		Unset,
		ApplicationJson
	}

	@Builder.Default
	protected String url	= "";

	@Builder.Default
	protected HttpMethod method = HttpMethod.POST;

	@Builder.Default
	protected HttpContentType contentType	= HttpContentType.Unset;

	@Builder.Default
	protected Object postData	= null;

	//	timeout in milliseconds
	@Builder.Default
	protected int timeout = CONNECTION_REQUEST_TIMEOUT_MS;

	@Builder.Default
	protected Auth auth		= null;

	@Builder.Default
	protected Header header		= null;





	protected HttpModel parseResponse( HttpResponse response )
	{
		int statusCode	= 0;

		try
		{
			//	EntityUtils.consumeQuietly( response.getEntity() );
			statusCode = response.getStatusLine().getStatusCode();
			log.debug( "_parseResponse :: statusCode {}", statusCode );

			HttpEntity entity     = response.getEntity();
			String     bodyString = null != entity ? EntityUtils.toString( entity ) : "";

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
			Object      body        = new HashMap<>();
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


	protected RequestConfig buildCustomRequestConfig( int timeout )
	{
		return RequestConfig
			.custom()
			.setConnectionRequestTimeout( timeout > 0 ? timeout : CONNECTION_REQUEST_TIMEOUT_MS )
			.setConnectTimeout( timeout > 0 ? timeout : CONNECT_TIMEOUT_MS )
			.setSocketTimeout( timeout > 0 ? timeout : SOCKET_TIMEOUT_MS )
			.build();
	}


	/**
	 * 	child class Auth
	 */
	@NoArgsConstructor
	@Getter
	@Setter
	@SuperBuilder
	public static class Auth
	{
		public enum AuthType
		{
			Unset,
			Basic,
			Bearer
		}

		@Builder.Default
		private AuthType authType	= AuthType.Unset;

		@Builder.Default
		private String authKey	= HttpHeaders.AUTHORIZATION;

		@Builder.Default
		private String user		= "";

		@Builder.Default
		private String password		= "";

		@Builder.Default
		private String bearerToken	= "";


		public String getAuthValue()
		{
			if ( AuthType.Bearer == authType )
			{
				return String.format( "Bearer %s", bearerToken );
			}
			else if ( AuthType.Basic == authType )
			{
				String userPasswordPair	= String.format( "%s:%s",
					null != user ? user : "",
					null != password ? password : "" );
				String basicEncodingString = Base64.getEncoder().encodeToString( userPasswordPair.getBytes() );
				return String.format( "Basic %s", basicEncodingString );
			}

			return "";
		}
	}


	@NoArgsConstructor
	@Getter
	@Setter
	@SuperBuilder
	public static class Header
	{
		@Builder.Default
		protected String acceptValue	= "application/json";

		@Builder.Default
		protected String userAgentValue	= defaultUserAgent;

		@Builder.Default
		protected String xTenantIDValue	= "";

		@Builder.Default
		HashMap<String,String> customHeaders	= new HashMap<>();
	}
}
