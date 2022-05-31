package org.dekuan.airpassport.lib.http;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.http.HttpHeaders;

import java.util.Base64;
import java.util.HashMap;

/**
 * Using Apache Http Client
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
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

	@Builder.Default
	protected String url	= "";

	@Builder.Default
	protected HttpMethod method = HttpMethod.POST;

	@Builder.Default
	protected HttpContentType contentType	= HttpContentType.ApplicationJson;

	@Builder.Default
	protected Object postData	= null;

	//	timeout in milliseconds
	@Builder.Default
	protected int timeout = CONNECTION_REQUEST_TIMEOUT_MS;

	@Builder.Default
	protected Auth auth		= null;

	@Builder.Default
	protected Header header		= null;




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
