package org.dekuan.airpassport.lib.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.FieldError;

import java.util.UUID;


public class DeValidationUtil
{
	public static String buildMessage( FieldError fieldError )
	{
		return null != fieldError ? String.format( "Field error in object '%s' on field '%s': rejected value [%s]; message: %s", fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage() ) : "null field error";
	}

	public static boolean isValidUUID( String testUUID )
	{
		if ( Strings.isBlank( testUUID ) )
		{
			return false;
		}

		boolean isValid = false;

		try
		{
			UUID uuid = UUID.fromString( testUUID );
			isValid = true;
		}
		catch ( IllegalArgumentException exception )
		{
			//	handle the case where string is not valid UUID
		}
		catch ( Exception ignored )
		{
		}

		return isValid;
	}
}
