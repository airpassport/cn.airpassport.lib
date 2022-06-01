package org.dekuan.airpassport.lib.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;


public class DeDateTimeUtils
{
	public static final ZoneId zone = ZoneId.systemDefault();


	public static LocalDate getDefaultLocalDate()
	{
		return LocalDate.parse( "1979-01-01" );
	}

	public static LocalDate getLocalNowDate()
	{
		return LocalDate.now( zone );
	}
	public static LocalTime getLocalNowTime()
	{
		return LocalTime.now( zone );
	}
	public static LocalDateTime getLocalNowDateTime()
	{
		return LocalDateTime.now( zone );
	}

	public static LocalDate getSafeLocalDate( LocalDate localDate, LocalDate defaultLocalDate )
	{
		return null != localDate ? localDate : defaultLocalDate;
	}


	/**
	 * 	convert type of long to type of LocalDateTime
	 */
	public static LocalDateTime getLocalDateTimeOfTimestamp( long timestamp )
	{
		Instant instant = Instant.ofEpochMilli( timestamp );
		return LocalDateTime.ofInstant( instant, zone );
	}

	public static LocalDateTime getLocalDateTimeOfTimestamp( String timestamp )
	{
		return getLocalDateTimeOfTimestamp( Long.parseLong( timestamp ) );
	}


	/**
	 * 	convert type of LocalDateTime to type of timestamp
	 */
	public static long getTimestampOfDateTime( LocalDateTime localDateTime )
	{
		if ( null == localDateTime )
		{
			return 0;
		}

		Instant instant = localDateTime.atZone( zone ).toInstant();
		return instant.toEpochMilli();
	}


	/**
	 * 	get string type of timestamp
	 */
	public static String getStringTimestampOfLongLocalDateTime( LocalDateTime localDateTime )
	{
		return String.valueOf( getTimestampOfDateTime( localDateTime ) );
	}

	/**
	 * 	format LocalDateTime
	 */
	public static String buildStringOfLocalDateTime( LocalDateTime localDateTime, String format )
	{
		if ( null == localDateTime )
		{
			return "";
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( format );
		return localDateTime.format( formatter );
	}
	public static String buildIsoStringOfLocalDateTime( LocalDateTime localDateTime )
	{
		if ( null == localDateTime )
		{
			return "";
		}

		return localDateTime.format( ISO_LOCAL_DATE_TIME );
	}

	/**
	 * 	convert customized type to type of LocalDateTime
	 */
	public static LocalDateTime parseStringToDateTime( String string, String format )
	{
		DateTimeFormatter df = DateTimeFormatter.ofPattern( format );
		return LocalDateTime.parse( string, df );
	}
	public static LocalDateTime parseStringToIsoDateTime( String string )
	{
		return LocalDateTime.parse( string, ISO_LOCAL_DATE_TIME );
	}

}
