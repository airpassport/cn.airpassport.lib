package org.dekuan.airpassport.lib.utils;

import com.google.common.base.Strings;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class DeAuthUtils
{
	public static String buildBearerTokenHeaderKey()
	{
		return "Authorization";
	}
	public static String buildBearerTokenHeaderValue( String token )
	{
		return String.format( "Bearer %s", Strings.nullToEmpty( token ).trim() );
	}
	public static String readBearerTokenHeader()
	{
		String authorizationHeaderValue = DeWebUtils.getHttpHeaderValue( HttpHeaders.AUTHORIZATION );
		if ( null != authorizationHeaderValue && authorizationHeaderValue.startsWith( "Bearer " ) )
		{
			return authorizationHeaderValue.substring( 7 );
		}

		return null;
	}

}