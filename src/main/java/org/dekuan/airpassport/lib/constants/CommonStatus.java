package org.dekuan.airpassport.lib.constants;

public class CommonStatus
{
	public static final int UNSET		= 0;
	public static final int OKAY		= 1;	//	一切正常
	public static final int ARCHIVED	= 2;	//	已归档的
	public static final int DELETED		= 3;	//	已删除的
	public static final int PAUSED		= 4;	//	已暂停的


	public static boolean isValid( int status )
	{
		return UNSET == status ||
			OKAY == status ||
			ARCHIVED == status ||
			DELETED == status ||
			PAUSED == status
		;
	}
}