package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.models.LocalDateInterval;
import org.dekuan.airpassport.lib.time.LibTime;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class LibTimeTests
{
	@Test
	public void testChinaNowCase1()
	{
		LocalDateTime ldtChinaNow = LibTime.getChinaNow();
		assertTrue( ldtChinaNow.getYear() > 0 );
	}

	@Test
	public void testGetTimestampWithGMTCase1()
	{
		long ts = LibTime.getTimestampWithGMT();
		assertTrue( ts > 1615764130383L );
	}

	@Test
	public void testOverlapDatesCase1()
	{
		assertTrue( LibTime.isDateIntervalsOverlapped
			(
				LocalDate.parse( "2019-12-01" ),
				LocalDate.parse( "2019-12-22" ),
				LocalDate.parse( "2019-12-20" ),
				LocalDate.parse( "2020-12-31" )
			) );

		assertFalse( LibTime.isDateIntervalsOverlapped
			(
				LocalDate.parse( "2019-12-01" ),
				LocalDate.parse( "2019-12-10" ),
				LocalDate.parse( "2019-12-20" ),
				LocalDate.parse( "2020-12-31" )
			) );
	}

	@Test
	public void testOverlapDatesCase2()
	{
		assertTrue( LibTime.isDateIntervalsOverlapped
			(
				LocalDateInterval.create
					(
						LocalDate.parse( "2019-12-01" ),
						LocalDate.parse( "2019-12-22" )
					),
				LocalDateInterval.create
					(
						LocalDate.parse( "2019-12-20" ),
						LocalDate.parse( "2020-12-31" )
					)
			) );

		assertFalse( LibTime.isDateIntervalsOverlapped
			(
				new LocalDateInterval
					(
						LocalDate.parse( "2019-12-01" ),
						LocalDate.parse( "2019-12-10" )
					),
				new LocalDateInterval
					(
						LocalDate.parse( "2019-12-20" ),
						LocalDate.parse( "2020-12-31" )
					)
			) );
	}

	@Test
	public void testOverlapDatesCase3()
	{
		LocalDateInterval interval1 = LocalDateInterval.create
			(
				LocalDate.parse( "2019-12-01" ),
				LocalDate.parse( "2019-12-22" )
			);
		assertTrue( LibTime.isDateIntervalsOverlapped
			(
				LocalDateInterval.from( interval1 ),
				LocalDateInterval.create
					(
						LocalDate.parse( "2019-12-20" ),
						LocalDate.parse( "2020-12-31" )
					)
			) );
	}


	@Test
	public void testLocalDateIntervalIsValidCase1()
	{
		assertTrue( LocalDateInterval.isValid
			(
				LocalDateInterval.create
					(
						LocalDate.parse( "2019-12-01" ),
						LocalDate.parse( "2019-12-22" )
					)
			) );
		assertTrue( LocalDateInterval.isValid
			(
				LocalDateInterval.create
					(
						LocalDate.parse( "2019-12-01" ),
						LocalDate.parse( "2019-12-01" )
					)
			) );

		assertFalse( LocalDateInterval.isValid( null ) );
		assertFalse( LocalDateInterval.isValid( new LocalDateInterval() ) );

		LocalDateInterval localDateInterval = new LocalDateInterval();
		localDateInterval.setBegin( LocalDate.parse( "2019-12-31" ) );
		localDateInterval.setEnd( LocalDate.parse( "2019-12-01" ) );
		assertFalse( LocalDateInterval.isValid( localDateInterval ) );
	}


	@Test
	public void testGetOverlappedCase1()
	{
		assertTrue(
			LocalDateInterval.create(
				LocalDate.parse( "2019-12-09" ),
				LocalDate.parse( "2019-12-20" )
			).equals(
				LibTime.getOverlapped(
					LocalDate.parse( "2019-12-01" ),
					LocalDate.parse( "2019-12-20" ),
					LocalDate.parse( "2019-12-09" ),
					LocalDate.parse( "2019-12-31" )
				)
			)
		);

		//	there is no overlapped interval between two date intervals
		assertNull(
			LibTime.getOverlapped(
				LocalDate.parse( "2019-11-01" ),
				LocalDate.parse( "2019-11-20" ),
				LocalDate.parse( "2019-12-09" ),
				LocalDate.parse( "2019-12-31" )
			)
		);
	}
}