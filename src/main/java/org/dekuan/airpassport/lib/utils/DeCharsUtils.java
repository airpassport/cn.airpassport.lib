package org.dekuan.airpassport.lib.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;


/**
 *	ApStringUtils
 */
public class DeCharsUtils
{
	public static long safeParseLong( String value )
	{
		if ( StringUtils.isBlank( value ) )
		{
			return 0;
		}

		long longValue	= 0;
		try
		{
			longValue = Long.parseLong( value );
		}
		catch ( Exception ignored )
		{
		}

		return longValue;
	}

	public static String getSafeString( String string, String defaultString )
	{
		return null != string ? string : defaultString;
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