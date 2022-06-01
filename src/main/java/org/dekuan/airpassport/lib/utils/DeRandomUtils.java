package org.dekuan.airpassport.lib.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.util.Random;


/**
 * 	RandomUtils
 */
public class DeRandomUtils
{
	/**
	 *	generate random number in range
	 *	@param	nMin	-
	 *	@param	nMax	-
	 *	@return	int
	 */
	public static int generateRandomNumberInRange( int nMin, int nMax )
	{
		if ( nMin >= nMax )
		{
			throw new InvalidParameterException( "invalid min/max" );
		}

		//	...
		Random r = new Random();
		return r.nextInt( ( nMax - nMin ) + 1 ) + nMin;
	}

	/**
	 * 	generate an random digital code
	 *	@return	string
	 */
	public static String generateRandomCode()
	{
		int nRandom	= DeRandomUtils.generateRandomNumberInRange( 1000, 9999 );
		String sCode	= String.format( "%d", nRandom );
		String sEnvName	= System.getenv( "envName" );
		if ( ! StringUtils.isBlank( sEnvName ) &&
			"junit".equalsIgnoreCase( sEnvName ) )
		{
			sCode	= "1234";
		}

		return sCode;
	}
}
