package org.dekuan.airpassport.lib.page;


/**
 *	@class	LibPage
 */
public class LibPage
{
	public static int getDefaultPageNo()
	{
		return 1;
	}
	public static int getDefaultPageSize()
	{
		return 20;
	}


	public static int getSafePageNo( int nPageNo )
	{
		return LibPage.getSafePageNo( nPageNo, LibPage.getDefaultPageNo() );
	}
	public static int getSafePageNo( int nPageNo, int nDefaultPageNo )
	{
		return nPageNo > 0 ? nPageNo : nDefaultPageNo;
	}


	public static int getSafePageSize( int nPageSize )
	{
		return LibPage.getSafePageSize( nPageSize, LibPage.getDefaultPageSize() );
	}
	public static int getSafePageSize( int nPageSize, int nDefaultPageSize )
	{
		return ( nPageSize > 0 && nPageSize <= nDefaultPageSize ) ? nPageSize : nDefaultPageSize;
	}
}
