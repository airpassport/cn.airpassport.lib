package org.dekuan.airpassport.lib.utils;

import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

public class DeEnvUtils
{
	public static String getKey()
	{
		return "envName";
	}

	public static String getValue()
	{
		return "JUnit";
	}

	public static String getLocationConfigKey()
	{
		return "spring.config.location";
	}

	public static String getLocationConfigValue()
	{
		return "classpath:/application.yml,/etc/airpassport/org.dekuan.rahsys/";
	}


	public static boolean isJUnitEnv()
	{
		return getValue().equalsIgnoreCase( getEnv( getKey() ) );
	}

	public static void setJUnitEnv()
	{
		setEnv( getKey(), getValue() );
	}

	public static String getEnv( String key )
	{
		if ( Strings.isBlank( key ) )
		{
			return null;
		}

		return System.getProperty( key );
	}

	public static void setEnv( String key, String value )
	{
		if ( Strings.isBlank( key ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid key" );
		}

		System.setProperty( key, value );
	}
}