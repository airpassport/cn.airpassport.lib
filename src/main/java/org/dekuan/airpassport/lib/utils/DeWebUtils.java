package org.dekuan.airpassport.lib.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpHead;
import org.dekuan.airpassport.lib.network.LibNetwork;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;



public class DeWebUtils
{
	public static String getClientIp()
	{
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		if ( null != httpServletRequest )
		{
			return httpServletRequest.getRemoteAddr();
		}

		return null;
	}

	public static String getClientIpWithProxy()
	{
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		if ( null == httpServletRequest )
		{
			return null;
		}

		String remoteAddress = null;
		final String[] arrIpHeaderCandidates = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

		for ( String sHeader : arrIpHeaderCandidates )
		{
			String sIpList = httpServletRequest.getHeader( sHeader );
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
				remoteAddress = sIp;
				break;
			}
		}

		if ( null == remoteAddress )
		{
			remoteAddress = httpServletRequest.getRemoteAddr();
		}

		return remoteAddress;
	}

	/**
	 * get User-Agent of client
	 *
	 * @return string
	 */
	public static String getUserAgent()
	{
		return getHttpHeaderValue( HttpHeaders.USER_AGENT );
	}

	public static String getHttpHeaderValue( String key )
	{
		if ( Strings.isBlank( key ) )
		{
			return null;
		}

		HttpServletRequest httpServletRequest = getHttpServletRequest();
		if ( null != httpServletRequest )
		{
			return httpServletRequest.getHeader( key );
		}

		return null;
	}

	public static HttpServletRequest getHttpServletRequest()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if ( null != requestAttributes )
		{
			ServletRequestAttributes servletRequestAttributes = ( ServletRequestAttributes ) requestAttributes;
			return servletRequestAttributes.getRequest();
		}

		return null;
	}
	public static HttpServletResponse getHttpServletResponse()
	{
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if ( null != requestAttributes )
		{
			ServletRequestAttributes servletRequestAttributes = ( ServletRequestAttributes ) requestAttributes;
			return servletRequestAttributes.getResponse();
		}

		return null;
	}
}