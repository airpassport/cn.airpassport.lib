package org.dekuan.airpassport.lib.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.util.HashMap;
import java.util.TreeMap;

import static java.util.stream.Collectors.joining;


public class SortedSign
{
	public String getSign( HashMap<String, String> map, String signKey )
	{
		if ( null == map || 0 == map.size() )
		{
			return "";
		}

		return this.getSign( new TreeMap<>( map ), signKey );
	}

	public String getSign( TreeMap<String, String> sortedMap, String signKey )
	{
		if ( null == sortedMap || 0 == sortedMap.size() )
		{
			return "";
		}
		if ( Strings.isBlank( signKey ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid signKey" );
		}

		//	Display the TreeMap which is naturally sorted
		//for ( Map.Entry<String, String> entry : sortedMap.entrySet() )
		//{
		//	System.out.println( "Key = " + entry.getKey() + ", Value = " + entry.getValue() );
		//}

		//
		String keyValueString = sortedMap.entrySet().stream().
			map( e -> e.getKey() + "=" + e.getValue() ).collect( joining( "&" ) );
		String source = String.format( "%s-%s", keyValueString, DigestUtils.sha256Hex( signKey ) );
	 	return DigestUtils.sha256Hex( source );
	}


	public boolean isMatched( HashMap<String, String> map, String signKey, String digest )
	{
		if ( Strings.isBlank( digest ) )
		{
			return false;
		}

		return isMatched( new TreeMap<>( map ), signKey, digest );
	}

	public boolean isMatched( TreeMap<String, String> map, String signKey, String digest )
	{
		if ( Strings.isBlank( digest ) )
		{
			return false;
		}

		String mapDigest = getSign( map, signKey );
		return Strings.isNotBlank( mapDigest ) &&
			mapDigest.equalsIgnoreCase( digest );
	}
}