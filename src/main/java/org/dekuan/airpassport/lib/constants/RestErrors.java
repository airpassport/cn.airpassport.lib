package org.dekuan.airpassport.lib.constants;

public class RestErrors
{
	public final static String SUCCESS		= "success";
	public final static String UNKNOWN		= "unknown";
	public final static String FAILED		= "failed";
	public final static String INVALID_PAYLOAD	= "invalid_payload";

	public static boolean isSuccess( String error )
	{
		if ( null == error )
		{
			return false;
		}

		return SUCCESS.equalsIgnoreCase( error.trim() );
	}
}