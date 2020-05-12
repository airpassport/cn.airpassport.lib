package org.dekuan.airpassport.lib.common;

import org.apache.commons.lang3.StringUtils;


/**
 *	@class	LibCommon
 */
public class LibCommon
{
	public static boolean isValidMid( String sMid )
	{
		if ( StringUtils.isBlank( sMid ) )
		{
			return false;
		}

		return 36 == sMid.length();
	}

	public static String calcErrorCode( Object oObjectMethod, String sErrorTail )
	{
		try
		{
			String sClassName	= oObjectMethod.getClass().getName();
			int nPosDollarChar	= sClassName.indexOf( "$" );
			if ( nPosDollarChar > 0 )
			{
				sClassName = sClassName.substring( 0, nPosDollarChar );
			}

			String sMethodName	= oObjectMethod.getClass().getEnclosingMethod().getName();

			if ( ! StringUtils.isBlank( sErrorTail ) )
			{
				return String.format( "%s.%s.%s", sClassName, sMethodName, sErrorTail );
			}
			else
			{
				return String.format( "%s.%s", sClassName, sMethodName );
			}
		}
		catch ( Exception e )
		{
			return sErrorTail;
		}
	}
}