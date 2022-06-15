package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.utils.DeDateTimeUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DeDateTimeUtilsTests
{
	@Test
	public void testCase1()
	{
//		Date date = new Date( 2022-1900, 6-1, 6, 10, 10, 10 );
//		String stringDate = DeDateTimeUtils.buildIsoStringOfDate( date );
//		assertEquals( stringDate, "2022-06-06 10:10:10" );

//		LocalDateTime localDateTime = LocalDateTime.now();
//		String stringLocalDateTime = DeDateTimeUtils.buildIsoStringOfLocalDateTime( localDateTime );
//		System.out.println( stringLocalDateTime );
//
//		String s2 = "2022-06-14T18:40:23.807979";
//		s2 = "2022-06-14T18:40:23";
//		LocalDateTime l2 = DeDateTimeUtils.parseIsoStringToLocalDateTime( s2 );
//		System.out.println( l2 );
	}

	@Test
	public void testParseIsoStringToLocalDateTimeCase1()
	{
		String stringDateTime = "2022-06-15 22:01:20";
		LocalDateTime localDateTime = DeDateTimeUtils.parseIsoStringToLocalDateTime( stringDateTime );
		assert localDateTime != null;
		assertEquals( 2022, localDateTime.getYear() );
		assertEquals( 6, localDateTime.getMonthValue() );
		assertEquals( 15, localDateTime.getDayOfMonth() );
		assertEquals( 22, localDateTime.getHour() );
		assertEquals( 1, localDateTime.getMinute() );
		assertEquals( 20, localDateTime.getSecond() );
	}

	@Test
	public void testBuildIsoStringOfLocalDateTimeCase1()
	{
		LocalDateTime localDateTime = LocalDateTime.of( 2022, 6, 15, 22, 1, 20 );
		String stringDateTime = DeDateTimeUtils.buildIsoStringOfLocalDateTime( localDateTime );
		assertEquals( stringDateTime, "2022-06-15 22:01:20" );
	}




	@Test
	public void testCase2()
	{
		String stringDate = "2022-06-06 10:10:10";
		Date date = DeDateTimeUtils.parseIsoStringToDate( stringDate );
//		System.out.println( date.getYear() );
//		System.out.println( date.getMonth() );
//		System.out.println( date.getYear() );
//		System.out.println( date.getYear() );
	}
}