package org.dekuan.airpassport.lib.utils;

import org.springframework.validation.FieldError;

import java.util.UUID;


public class DeValidationUtil
{
	public static String buildMessage( FieldError fieldError )
	{
		return null != fieldError ? String.format( "Field error in object '%s' on field '%s': rejected value [%s]; message: %s", fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage() ) : "null field error";
	}

	public static boolean isValidUUID( UUID testUUID )
	{
		try
		{
			return 0 == UUID.fromString( testUUID.toString() ).compareTo( testUUID );
		}
		catch ( Exception ignored )
		{
			return false;
		}
	}

	public static boolean isValidUUID( String testUUID )
	{
		try
		{
			return 0 == UUID.fromString( testUUID ).toString().compareToIgnoreCase( testUUID );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
}
