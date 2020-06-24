package org.dekuan.airpassport.lib.time;

import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.model.LocalDateInterval;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *	@class	LibTime
 */
public class LibTime
{
	public static LocalDate fromDateString( String date )
	{
		if ( StringUtils.isBlank( date ) )
		{
			return null;
		}

		LocalDate parsedDate = null;

		try
		{
			//	a date in format "yyyy-mm-dd".
			parsedDate = LocalDate.parse( date );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return parsedDate;
	}

	public static LocalDateTime fromDateTimeString( String datetime )
	{
		if ( StringUtils.isBlank( datetime ) )
		{
			return null;
		}

		LocalDateTime parsedDate = null;

		try
		{
			//	a date in format "yyyy-mm-dd HH:ii:ss".
			parsedDate = LocalDateTime.parse( datetime );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return parsedDate;
	}



	public static boolean isDateIntervalsOverlapped
		(
			LocalDate oDate1Begin,
			LocalDate oDate1End,
			LocalDate oDate2Begin,
			LocalDate oDate2End
		)
	{
		if ( null == oDate1Begin || null == oDate1End || null == oDate2Begin || null == oDate2End )
		{
			return false;
		}

		return oDate1Begin.isBefore( oDate2End ) && oDate2Begin.isBefore( oDate1End );
	}

	public static boolean isDateIntervalsOverlapped(LocalDateInterval oDateInterval1, LocalDateInterval oDateInterval2 )
	{
		if ( null == oDateInterval1 || null == oDateInterval2 )
		{
			return false;
		}

		return LibTime.isDateIntervalsOverlapped
			(
				oDateInterval1.getBegin(),
				oDateInterval1.getEnd(),
				oDateInterval2.getBegin(),
				oDateInterval2.getEnd()
			);
	}


	/**
	 * 	get overlapped date interval
	 *
	 *	@param	oDate1Begin	-
	 *	@param	oDate1End	-
	 *	@param	oDate2Begin	-
	 *	@param	oDate2End	-
	 *	@return	LocalDateInterval
	 */
	public static LocalDateInterval getOverlapped
		(
			LocalDate oDate1Begin,
			LocalDate oDate1End,
			LocalDate oDate2Begin,
			LocalDate oDate2End
		)
	{
		if ( null == oDate1Begin || null == oDate1End || null == oDate2Begin || null == oDate2End )
		{
			return null;
		}
		if ( ! LibTime.isDateIntervalsOverlapped( oDate1Begin, oDate1End, oDate2Begin, oDate2End ) )
		{
			//	there is no overlapped interval
			return null;
		}

		//	...
		return LocalDateInterval.create(
			oDate1Begin.isAfter( oDate2Begin ) ? oDate1Begin : oDate2Begin,
			oDate1End.isBefore( oDate2End ) ? oDate1End : oDate2End
		);
	}

	/**
	 * 	get overlapped date interval
	 *	@param	oDateInterval1	-
	 *	@param	oDateInterval2	-
	 *	@return	LocalDateInterval
	 */
	public static LocalDateInterval getOverlapped( LocalDateInterval oDateInterval1, LocalDateInterval oDateInterval2 )
	{
		if ( null == oDateInterval1 || null == oDateInterval2 )
		{
			return null;
		}

		return LibTime.getOverlapped
			(
				oDateInterval1.getBegin(),
				oDateInterval1.getEnd(),
				oDateInterval2.getBegin(),
				oDateInterval2.getEnd()
			);
	}
}