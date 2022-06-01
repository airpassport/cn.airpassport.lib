package org.dekuan.airpassport.lib.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.network.LibNetwork;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
public class DeWebUtils
{
	public static String getClientIp( HttpServletRequest request )
	{
		return null != request ? request.getRemoteAddr() : "0.0.0.0";
	}

	public static String getClientIpWithProxy( HttpServletRequest request )
	{
		String remoteAddr = null;

		if ( null != request )
		{
			final String[] arrIpHeaderCandidates = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

			for ( String sHeader : arrIpHeaderCandidates )
			{
				String sIpList = request.getHeader( sHeader );
				if ( StringUtils.isBlank( sIpList ) || "unknown".equalsIgnoreCase( sIpList ) )
				{
					continue;
				}

				String[] arrIpList = sIpList.split( "," );
				if ( 0 == arrIpList.length )
				{
					continue;
				}

				//	...
				String sIp = arrIpList[ 0 ].trim();
				if ( LibNetwork.isValidIpAddress( sIp ) )
				{
					remoteAddr = sIp;
					break;
				}
			}

			if ( null == remoteAddr )
			{
				remoteAddr = request.getRemoteAddr();
			}
		}

		return null != remoteAddr ? remoteAddr : "0.0.0.0";
	}

	/**
	 * get User-Agent of client
	 *
	 * @return string
	 */
	public static String getUserAgent( HttpServletRequest request )
	{
		String userAgent = "";

		if ( null != request )
		{
			userAgent = request.getHeader( "User-Agent" );
		}

		return userAgent;
	}

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
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if ( null != requestAttributes )
		{
			ServletRequestAttributes servletRequestAttributes = ( ServletRequestAttributes ) requestAttributes;
			HttpServletRequest       httpServletRequest       = servletRequestAttributes.getRequest();
			String                   authorizationHeaderValue = httpServletRequest.getHeader( "Authorization" );
			if ( null != authorizationHeaderValue && authorizationHeaderValue.startsWith( "Bearer " ) )
			{
				return authorizationHeaderValue.substring( 7 );
			}
		}

		return null;
	}

}