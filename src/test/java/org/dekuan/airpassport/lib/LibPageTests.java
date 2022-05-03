package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.page.LibPage;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;


public class LibPageTests
{
	@Test
	public void testSafePageNo()
	{
		Assertions.assertEquals( 1, LibPage.getSafePageNo( 1, 1 ) );
		assertEquals( 2, LibPage.getSafePageNo( 2, 1 ) );
		assertEquals( 1, LibPage.getSafePageNo( -1, 1 ) );
	}

	@Test
	public void testCalcErrorCode()
	{
		assertEquals( 1, LibPage.getSafePageSize( 1, 100 ) );
		assertEquals( 10, LibPage.getSafePageSize( 10, 100 ) );
		assertEquals( 100, LibPage.getSafePageSize( 220, 100 ) );
		assertEquals( 100, LibPage.getSafePageSize( -1, 100 ) );
	}
}