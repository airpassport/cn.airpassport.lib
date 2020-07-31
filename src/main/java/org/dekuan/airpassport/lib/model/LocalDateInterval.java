package org.dekuan.airpassport.lib.model;

import java.time.LocalDate;

import org.dekuan.airpassport.lib.common.LibCommon;


/**
 *	LocalDateInterval
 */
public class LocalDateInterval
{
	private LocalDate begin;
	private LocalDate end;

	public LocalDateInterval()
	{
		this.begin	= null;
		this.end	= null;
	}
	public LocalDateInterval( LocalDate begin, LocalDate end )
	{
		if ( null == begin )
		{
			throw new IllegalArgumentException( LibCommon.calcErrorCode(new Object(){}, "null.begin" ) );
		}
		if ( null == end )
		{
			throw new IllegalArgumentException( LibCommon.calcErrorCode( new Object(){}, "null.end" ) );
		}
		if ( begin.isAfter( end ) )
		{
			throw new IllegalArgumentException( LibCommon.calcErrorCode( new Object(){}, "invalid.begin-end" ) );
		}

		//	...
		this.begin	= begin;
		this.end	= end;
	}

	public static LocalDateInterval create( LocalDate begin, LocalDate end )
	{
		return new LocalDateInterval( begin, end );
	}

	public static LocalDateInterval from( LocalDateInterval interval )
	{
		if ( null == interval )
		{
			throw new IllegalArgumentException( LibCommon.calcErrorCode( new Object(){}, "invalid.interval" ) );
		}

		return new LocalDateInterval( interval.begin, interval.end );
	}

	public static boolean isValid( LocalDateInterval interval )
	{
		return null != interval &&
			null != interval.getBegin() &&
			null != interval.getEnd() &&
			! interval.getBegin().isAfter( interval.getEnd() );
	}

	public LocalDate getBegin()
	{
		return this.begin;
	}
	public void setBegin( LocalDate localDate )
	{
		if ( null == localDate )
		{
			throw new IllegalArgumentException( LibCommon.calcErrorCode( new Object(){}, "null.localDate" ) );
		}

		this.begin = localDate;
	}

	public LocalDate getEnd()
	{
		return this.end;
	}
	public void setEnd( LocalDate localDate )
	{
		if ( null == localDate )
		{
			throw new IllegalArgumentException( LibCommon.calcErrorCode( new Object(){}, "null.localDate" ) );
		}

		this.end = localDate;
	}

	/**
	 *	Checks if this date is equal to another date.
	 *	Compares this {@code LocalDate} with another ensuring that the date is the same.
	 *
	 *	@param	anotherDateInterval	LocalDate object to check, null returns false
	 *	@return true if this is equal to the other date
	 */
	public boolean equals( LocalDateInterval anotherDateInterval )
	{
		if ( this == anotherDateInterval )
		{
			return true;
		}
		if ( ! LocalDateInterval.isValid( this ) ||
			! LocalDateInterval.isValid( anotherDateInterval ) )
		{
			return false;
		}

		return this.begin.equals( anotherDateInterval.getBegin() ) &&
			this.end.equals( anotherDateInterval.getEnd() );
	}
}