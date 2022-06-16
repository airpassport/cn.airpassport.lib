package org.dekuan.airpassport.lib.http;

import com.google.gson.internal.LinkedTreeMap;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.models.rest.RestHeader;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 *	REST Base Response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@SuperBuilder( builderMethodName = "httpModelBuilder" )
public class HttpModel extends RestHeader
{
	@Builder.Default
	protected Object body		= new HashMap<>();




	public static Boolean getBooleanValueByKey( Object obj, String keyName )
	{
		Object result = getObjectByKey( obj, keyName );
		if ( result instanceof Boolean )
		{
			return (Boolean) result;
		}

		return null;
	}

	public static String getStringValueByKey( Object obj, String keyName )
	{
		Object result = getObjectByKey( obj, keyName );
		if ( result instanceof String )
		{
			return (String ) result;
		}

		return null;
	}

	public static Long getLongValueByKey( Object obj, String keyName )
	{
		Object result = getObjectByKey( obj, keyName );
		if ( result instanceof Long )
		{
			return (Long) result;
		}
		else if ( result instanceof Integer )
		{
			return ( (Integer)result ).longValue();
		}
		else if ( result instanceof Double )
		{
			return ( (Double)result ).longValue();
		}
		else if ( result instanceof Float )
		{
			return ( (Float)result ).longValue();
		}

		return null;
	}

	public static Integer getIntegerValueByKey( Object obj, String keyName )
	{
		Object result = getObjectByKey( obj, keyName );
		if ( result instanceof Integer )
		{
			return (Integer) result;
		}
		else if ( result instanceof Long )
		{
			return ( ( Long ) result ).intValue();
		}
		else if ( result instanceof Double )
		{
			return ( (Double)result ).intValue();
		}
		else if ( result instanceof Float )
		{
			return ( (Float)result ).intValue();
		}

		return null;
	}

	public static Double getDoubleValueByKey( Object obj, String keyName )
	{
		Object result = getObjectByKey( obj, keyName );
		if ( result instanceof Integer )
		{
			return ( ( Integer ) result ).doubleValue();
		}
		else if ( result instanceof Long )
		{
			return ( ( Long ) result ).doubleValue();
		}
		else if ( result instanceof Double )
		{
			return ( (Double)result );
		}
		else if ( result instanceof Float )
		{
			return ( (Float)result ).doubleValue();
		}

		return null;
	}

	public static Float getFloatValueByKey( Object obj, String keyName )
	{
		Object result = getObjectByKey( obj, keyName );
		if ( result instanceof Integer )
		{
			return ( ( Integer ) result ).floatValue();
		}
		else if ( result instanceof Long )
		{
			return ( ( Long ) result ).floatValue();
		}
		else if ( result instanceof Double )
		{
			return ( (Double)result ).floatValue();
		}
		else if ( result instanceof Float )
		{
			return ( (Float)result );
		}

		return null;
	}

	public static Object getObjectByKey( Object obj, String keyName )
	{
		if ( null == obj )
		{
			throw new InvalidParameterException( "invalid obj" );
		}
		if ( Strings.isBlank( keyName ) )
		{
			throw new InvalidParameterException( "invalid keyName" );
		}

		try
		{
			if ( obj instanceof LinkedTreeMap )
			{
				return (( LinkedTreeMap<?,?> )obj).get( keyName );
			}
			else if ( obj instanceof LinkedHashMap )
			{
				return (( LinkedHashMap<?,?> )obj).get( keyName );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return null;
	}
}