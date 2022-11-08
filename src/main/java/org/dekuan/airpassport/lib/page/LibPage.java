package org.dekuan.airpassport.lib.page;


/**
 *	@class	LibPage
 */
public class LibPage
{
	public static int defaultPageNo = 1;
	public static int defaultPageSize = 20;


	public static int getSafePageNo( int nPageNo )
	{
		return LibPage.getSafePageNo( nPageNo, defaultPageNo );
	}
	public static int getSafePageNo( int nPageNo, int nDefaultPageNo )
	{
		return nPageNo > 0 ? nPageNo : nDefaultPageNo;
	}


	public static int getSafePageSize( int nPageSize )
	{
		return LibPage.getSafePageSize( nPageSize, defaultPageSize );
	}
	public static int getSafePageSize( int nPageSize, int nDefaultPageSize )
	{
		return ( nPageSize > 0 && nPageSize <= nDefaultPageSize ) ? nPageSize : nDefaultPageSize;
	}
}
