package org.dekuan.airpassport.lib.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;


/**
 *	ApStringUtils
 */
public class DeStringUtils
{
	/**
	 * 	Check If a String Is Numeric in Java
	 * 	<a href="https://www.baeldung.com/java-check-string-number">...</a>
	 *
	 */
	public static boolean isNumeric( String strNum )
	{
		try
		{
			long d = Long.parseLong( strNum );
			return true;
		}
		catch ( NumberFormatException nfe )
		{
			return false;
		}
	}

	public static String substringInChars( String sString, int nMaxLengthInChars )
	{
		if ( nMaxLengthInChars <= 0 )
		{
			return Strings.nullToEmpty( sString );
		}

		sString = Strings.nullToEmpty( sString );
		return sString.substring( 0, Math.min( nMaxLengthInChars, sString.length() ) );
	}

	public static String substringInBytes( String sString, int nMaxLengthInBytes )
	{
		if ( nMaxLengthInBytes <= 0 )
		{
			return Strings.nullToEmpty( sString );
		}

		//	...
		sString = Strings.nullToEmpty( sString );

		try
		{
			int nOrgLengthInBytes	= sString.getBytes( StandardCharsets.UTF_8 ).length;
			nMaxLengthInBytes	= Math.min( nOrgLengthInBytes, nMaxLengthInBytes );

			if ( sString.length() > 0 &&
				sString.getBytes( StandardCharsets.UTF_8 ).length >= nMaxLengthInBytes )
			{
				sString = new String( sString.getBytes( StandardCharsets.UTF_8 ), 0, nMaxLengthInBytes - 2, StandardCharsets.UTF_8 );
			}
		}
		catch ( Exception e )
		{
			sString = "### exception ###";
		}

		return sString;
	}

	public static String getNickname( String sLoginAccount )
	{
		if ( StringUtils.isBlank( sLoginAccount ) )
		{
			return "";
		}

		String sUserNickname = String.format( "%s", sLoginAccount.substring( 0, sLoginAccount.length() / 2 ) );
		return sUserNickname.replace( "@", "" );
	}

}