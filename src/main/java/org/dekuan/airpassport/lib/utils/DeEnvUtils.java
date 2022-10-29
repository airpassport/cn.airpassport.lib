package org.dekuan.airpassport.lib.utils;

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
		return getValue().equalsIgnoreCase( System.getProperty( getKey() ) );
	}

	public static void setJUnitEnv()
	{
		System.setProperty( getKey(), getValue() );
	}
}