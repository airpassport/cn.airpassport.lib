package org.dekuan.airpassport.lib.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;

@Slf4j
public class DeJSONUtils
{
	public static double parseDouble( JSONObject jsonObject, String keyName )
	{
		return parseDouble( jsonObject, keyName, Double.NaN );
	}
	public static double parseDouble( JSONObject jsonObject, String keyName, double defaultValue )
	{
		if ( null == jsonObject )
		{
			return defaultValue;
		}
		if ( Strings.isBlank( keyName ) )
		{
			return defaultValue;
		}

		String stringValue = jsonObject.optString( keyName, "" );
		if ( Strings.isBlank( stringValue ) )
		{
			return defaultValue;
		}

		try
		{
			return Double.parseDouble( stringValue );
		}
		catch ( NumberFormatException e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return defaultValue;
	}


	public static int parseInt( JSONObject jsonObject, String keyName )
	{
		return parseInt( jsonObject, keyName, 0 );
	}
	public static int parseInt( JSONObject jsonObject, String keyName, int defaultValue )
	{
		if ( null == jsonObject )
		{
			return defaultValue;
		}
		if ( Strings.isBlank( keyName ) )
		{
			return defaultValue;
		}

		String stringValue = jsonObject.optString( keyName, "" );
		if ( Strings.isBlank( stringValue ) )
		{
			return defaultValue;
		}

		try
		{
			return Integer.parseInt( stringValue );
		}
		catch ( NumberFormatException e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return defaultValue;
	}
}
