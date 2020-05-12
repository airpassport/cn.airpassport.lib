package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.common.LibCommon;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LibCommonTests
{
	@Test
	public void testIsValidMid()
	{
		assertTrue( LibCommon.isValidMid( "2b877804-1ff8-4e55-8714-068085e285db" ) );
		assertTrue( LibCommon.isValidMid( "2b87780411ff814e55187141068085e285db" ) );
		assertFalse( LibCommon.isValidMid( "2b87780411ff814e55187141068085e285db--9999ddd" ) );
		assertFalse( LibCommon.isValidMid( "" ) );
		assertFalse( LibCommon.isValidMid( null ) );
	}

	@Test
	public void testCalcErrorCode()
	{
		String sErrorCode1 = LibCommon.calcErrorCode( new Object(){}, null );
		assertEquals( "org.dekuan.lib.LibCommonTests.testCalcErrorCode", sErrorCode1 );

		String sErrorCode2 = LibCommon.calcErrorCode( new Object(){}, "invalid.name" );
		assertEquals( "org.dekuan.lib.LibCommonTests.testCalcErrorCode.invalid.name", sErrorCode2 );
	}
}