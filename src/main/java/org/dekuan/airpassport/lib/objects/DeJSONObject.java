package org.dekuan.airpassport.lib.objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;

@Slf4j
public class DeJSONObject extends JSONObject
{
	public double parseDouble( String keyName )
	{
		return parseDouble( keyName, Double.NaN );
	}
	public double parseDouble( String keyName, double defaultValue )
	{
		if ( Strings.isBlank( keyName ) )
		{
			return defaultValue;
		}

		String stringValue = this.optString( keyName, "" );
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


	public int parseInt( String keyName )
	{
		return parseInt( keyName, 0 );
	}
	public int parseInt( String keyName, int defaultValue )
	{
		if ( Strings.isBlank( keyName ) )
		{
			return defaultValue;
		}

		String stringValue = this.optString( keyName, "" );
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