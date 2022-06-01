package org.dekuan.airpassport.lib.constants;

public class CommonStatus
{
	public static final int UNSET		= 0;
	public static final int OKAY		= 1;
	public static final int ARCHIVED	= 2;
	public static final int DELETED		= 3;


	public static boolean isValid( int status )
	{
		return UNSET == status || OKAY == status || ARCHIVED == status || DELETED == status;
	}
}