package org.dekuan.airpassport.lib.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;
import java.util.HashMap;


@Slf4j
public class DeCookieUtils
{
	//
	//	default constants
	//
	private final static int DEFAULT_EXPIRE		= 365 * 24 * 60 * 60;
	private final static boolean DEFAULT_SECURE	= false;
	private final static boolean DEFAULT_HTTP_ONLY	= true;
	private final static String DEFAULT_PATH	= "/";



	/**
	 *	read all cookie
	 *	@return	HashMap
	 */
	public static HashMap<String, Cookie> readAllCookie()
	{
		HttpServletRequest httpServletRequest = DeWebUtils.getHttpServletRequest();
		if ( null == httpServletRequest )
		{
			throw new NullPointerException( "invalid httpServletRequest" );
		}

		HashMap<String, Cookie> mapCookies = new HashMap<>();
		Cookie[] arrCookies = httpServletRequest.getCookies();
		if ( null != arrCookies )
		{
			for ( Cookie cookie : arrCookies )
			{
				if ( ! StringUtils.isBlank( cookie.getName() ) )
				{
					mapCookies.put( cookie.getName(), cookie );
				}
			}
		}

		return mapCookies;
	}

	/**
	 *	read cookie
	 *	@param	name		-
	 *	@return	String
	 */
	public static String readCookie
		(
			String name
		)
	{
		HttpServletRequest httpServletRequest = DeWebUtils.getHttpServletRequest();
		if ( null == httpServletRequest )
		{
			throw new NullPointerException( "invalid httpServletRequest" );
		}
		if ( StringUtils.isBlank( name ) )
		{
			throw new InvalidParameterException( "invalid name" );
		}

		//	...
		String sValue	= null;
		Cookie[] arrCookies = httpServletRequest.getCookies();
		if ( null != arrCookies )
		{
			for ( Cookie cookie : arrCookies )
			{
				if ( StringUtils.isBlank( cookie.getName() ) )
				{
					continue;
				}
				if ( cookie.getName().equalsIgnoreCase( name ) )
				{
					sValue = cookie.getValue();
				}
			}
			//	.map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
		}

		//	...
		log.info( ">>>>>>>>>>" +
			" CookieUtils::readCookie() name:{}, value:{}", name, sValue );

		return sValue;
	}


	/**
	 *	set a cookie
	 *	@param	name		-
	 *	@param	value		-
	 *	@param	expire		-
	 */
	public static void setCookie
		(
			String domain,
			String name,
			String value,
			int expire
		)
	{
		HttpServletRequest httpServletRequest = DeWebUtils.getHttpServletRequest();
		if ( null == httpServletRequest )
		{
			throw new NullPointerException( "invalid httpServletRequest" );
		}
		HttpServletResponse httpServletResponse = DeWebUtils.getHttpServletResponse();
		if ( null == httpServletResponse )
		{
			throw new NullPointerException( "invalid httpServletResponse" );
		}

		if ( StringUtils.isBlank( domain ) )
		{
			throw new InvalidParameterException( "invalid domain" );
		}
		if ( StringUtils.isBlank( name ) )
		{
			throw new InvalidParameterException( "invalid name" );
		}
		if ( expire < 0 )
		{
			throw new InvalidParameterException( "invalid expire" );
		}

		//	create a cookie
		Cookie cookie	= new Cookie( name, value );

		//	set version
		cookie.setVersion( 1 );

		//
		//	Sets the maximum age in seconds for this Cookie
		//
		cookie.setMaxAge( expire );

		//	This cookie should be presented only to hosts satisfying this domain name pattern.
		cookie.setDomain( domain );

		//	global cookie accessible every where
		cookie.setPath( DEFAULT_PATH );

		//	Indicates to the browser whether the cookie
		//	should only be sent using a secure protocol, such as HTTPS or SSL.
		cookie.setSecure( DEFAULT_SECURE );

		//	Marks or unmarks this Cookie as HttpOnly.
		//cookie.setHttpOnly( DEFAULT_HTTP_ONLY );

		//	add cookie to response
		httpServletResponse.addCookie( cookie );
	}
	public static void setCookie
		(
			String sDomain,
			String sName,
			String sValue
		)
	{
		setCookie( sDomain, sName, sValue, DEFAULT_EXPIRE );
	}


	/**
	 *	delete a cookie
	 *	@param	name		-
	 */
	public static void deleteCookie
		(
			String domain,
			String name
		)
	{
		HttpServletResponse httpServletResponse = DeWebUtils.getHttpServletResponse();
		if ( null == httpServletResponse )
		{
			throw new NullPointerException( "invalid httpServletResponse" );
		}
		if ( StringUtils.isBlank( domain ) )
		{
			throw new InvalidParameterException( "invalid domain" );
		}
		if ( StringUtils.isBlank( name ) )
		{
			throw new InvalidParameterException( "invalid name" );
		}

		//
		//	To delete a cookie, set the Max-Age directive to 0 and unset its value.
		//	You must also pass the same other cookie properties you used to set it.
		//	Don't set the Max-Age directive value to -1.
		//	Otherwise, it will be treated as a session cookie by the browser.
		//

		//	delete a cookie
		Cookie cookie = new Cookie( name, null );
		cookie.setMaxAge( 0 );

		//	This cookie should be presented only to hosts satisfying this domain name pattern.
		cookie.setDomain( domain );

		//	Indicates to the browser whether the cookie
		//	should only be sent using a secure protocol, such as HTTPS or SSL.
		cookie.setSecure( DEFAULT_SECURE );

		//	Marks or unmarks this Cookie as HttpOnly.
//		cookie.setHttpOnly( DEFAULT_HTTP_ONLY );

		//	global cookie accessible every where
		cookie.setPath( DEFAULT_PATH );

		//	add cookie to response
		httpServletResponse.addCookie( cookie );
	}
}