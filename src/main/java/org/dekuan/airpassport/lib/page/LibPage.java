package org.dekuan.airpassport.lib.page;


/**
 *	@class	LibPage
 */
public class LibPage
{
	public static final int defaultPageNo		= 1;
	public static final int defaultPageSize		= 20;
	public static final int defaultPageSizeMax	= 100;
	public static final int defaultPageSizeMin	= 1;



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
		return ( nPageSize >= defaultPageSizeMin && nPageSize <= defaultPageSizeMax ) ? nPageSize : nDefaultPageSize;
	}
}
