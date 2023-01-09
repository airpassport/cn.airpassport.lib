package org.dekuan.airpassport.lib.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;


@Slf4j
public class DeDateTimeUtils
{
	public static final ZoneId zone = ZoneId.systemDefault();
	public static SimpleDateFormat isoDateFormatter;
	public static DateTimeFormatter isoLocalDateTimeFormatter;


	static
	{
		//	for Date object
		//	https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
		isoDateFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH );
		isoDateFormatter.setTimeZone( TimeZone.getDefault() );

		//	for LocalDateTime object
		//	https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
		isoLocalDateTimeFormatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
	}



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
	public static long getTimestampOfLocalDateTime( LocalDateTime localDateTime )
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
	public static String getStringTimestampOfLocalDateTime( LocalDateTime localDateTime )
	{
		return String.valueOf( getTimestampOfLocalDateTime( localDateTime ) );
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
		return localDateTime.format( isoLocalDateTimeFormatter );
	}
	public static String buildIsoStringWithTSplitOfLocalDateTime( LocalDateTime localDateTime )
	{
		if ( null == localDateTime )
		{
			return "";
		}

		return localDateTime.withNano( 0 ).toString();
	}


	public static LocalDateTime parseStringToLocalDateTime( String string, String format )
	{
		try
		{
			DateTimeFormatter df = DateTimeFormatter.ofPattern( format );
			return LocalDateTime.parse( string, df );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return null;
	}

	/**
	 * 	convert customized type to type of LocalDateTime
	 * 	"2022-06-14 18:40:23.807979"
	 * 	"2022-06-14 18:40:23.807"
	 * 	"2022-06-14 18:40:23"
	 */
	public static LocalDateTime parseIsoStringToLocalDateTime( String string )
	{
		try
		{
			return LocalDateTime.parse( string, isoLocalDateTimeFormatter );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return null;
	}


	/**
	 * 	isoDateFormatter: "yyyy-MM-dd hh:mm:ss"
	 */
	public static String buildIsoStringOfDate( Date date )
	{
		if ( null == date )
		{
			return "";
		}

		try
		{
			return isoDateFormatter.format( date );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return null;
	}

	public static Date parseIsoStringToDate( String stringDate )
	{
		if ( null == stringDate )
		{
			return null;
		}

		try
		{
			return isoDateFormatter.parse( stringDate );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return null;
	}
}