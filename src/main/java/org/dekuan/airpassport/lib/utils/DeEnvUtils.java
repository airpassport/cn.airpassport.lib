package org.dekuan.airpassport.lib.utils;

import org.apache.commons.lang3.StringUtils;


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
		String sDebugRpc	= System.getenv( "debug-api" );
		if ( ! StringUtils.isBlank( sDebugRpc ) &&
			sDebugRpc.equalsIgnoreCase( "yes" ) )
		{
			return true;
		}

		String sEnvName	= System.getenv( DeEnvUtils.getKey() );
		return !StringUtils.isBlank( sEnvName ) && DeEnvUtils.getValue().equalsIgnoreCase( sEnvName );
	}
}