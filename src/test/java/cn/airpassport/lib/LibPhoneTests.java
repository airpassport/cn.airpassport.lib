package cn.airpassport.lib;

import cn.airpassport.lib.phone.LibPhone;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;


public class LibPhoneTests
{
	@Test
	public void testPhoneNumberUtilRegion()
	{
		Assertions.assertEquals( true, LibPhone.isValidRegion( "CN" ) );
		assertEquals( true, LibPhone.isValidRegion( "CH" ) );
		assertEquals( true, LibPhone.isValidRegion( "US" ) );
		assertEquals( true, LibPhone.isValidRegion( "JP" ) );
		assertEquals( false, LibPhone.isValidRegion( "XX" ) );
		assertEquals( false, LibPhone.isValidRegion( "" ) );
		assertEquals( false, LibPhone.isValidRegion( null ) );
	}

	@Test
	public void testIsValidMobileNumberCase1()
	{
		assertFalse( LibPhone.isValidMobileNumber( "111070903", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "10110550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "11110550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "12110550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "18811070903", "+86" ) );
		assertFalse( LibPhone.isValidMobileNumber( "", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( null, "CN" ) );

		assertTrue( LibPhone.isValidMobileNumber( "13110550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13210550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13310550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13410550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13510550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13610550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13710550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13810550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "13910550569", "CN" ) );

		assertFalse( LibPhone.isValidMobileNumber( "14010550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14110550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14210550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14310550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14410550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "14510550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14610550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "14710550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14810550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "14910550569", "CN" ) );

		assertTrue( LibPhone.isValidMobileNumber( "15010550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15110550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15210550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15310550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "15410550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15510550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15610550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15710550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15810550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "15910550569", "CN" ) );

		assertTrue( LibPhone.isValidMobileNumber( "16110550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16210550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16310550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16410550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16510550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16610550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16710550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16810550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "16910550569", "CN" ) );

		//	013678
		assertTrue( LibPhone.isValidMobileNumber( "17010550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17110550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "17210550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17310550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "17410550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "17510550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17610550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17710550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17810550569", "CN" ) );
		assertFalse( LibPhone.isValidMobileNumber( "17910550569", "CN" ) );

		assertTrue( LibPhone.isValidMobileNumber( "18411053303", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "18511053303", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "18611053303", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "18711053303", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "18811070903", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17011070903", "CN" ) );

		assertTrue( LibPhone.isValidMobileNumber( "17111070903", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17311070903", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17611070903", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17711070903", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "17811070903", "CN" ) );

		assertTrue( LibPhone.isValidMobileNumber( "19110550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19210550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19310550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19410550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19510550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19610550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19710550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19810550569", "CN" ) );
		assertTrue( LibPhone.isValidMobileNumber( "19910550569", "CN" ) );
	}

	@Test
	public void testIsValidMobileNumberCase2()
	{
		assertFalse( LibPhone.isValidMobileNumber( "111070903", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "10110550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "11110550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "12110550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "18811070903", 11 ) );
		assertFalse( LibPhone.isValidMobileNumber( "", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( null, 86 ) );

		assertTrue( LibPhone.isValidMobileNumber( "13110550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13210550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13310550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13410550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13510550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13610550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13710550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13810550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "13910550569", 86 ) );

		assertFalse( LibPhone.isValidMobileNumber( "14110550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "14210550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "14310550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "14410550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "14510550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "14610550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "14710550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "14810550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "14910550569", 86 ) );

		assertTrue( LibPhone.isValidMobileNumber( "15110550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15210550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15310550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "15410550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15510550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15610550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15710550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15810550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "15910550569", 86 ) );

		assertTrue( LibPhone.isValidMobileNumber( "16110550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16210550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16310550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16410550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16510550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16610550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16710550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16810550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "16910550569", 86 ) );

		//	17 [013678]
		assertTrue( LibPhone.isValidMobileNumber( "17010550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17110550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "17210550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17310550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "17410550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "17510550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17610550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17710550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17810550569", 86 ) );
		assertFalse( LibPhone.isValidMobileNumber( "17910550569", 86 ) );

		assertTrue( LibPhone.isValidMobileNumber( "18411053303", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "18511053303", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "18611053303", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "18711053303", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "18811070903", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17011070903", 86 ) );

		assertTrue( LibPhone.isValidMobileNumber( "17111070903", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17311070903", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17611070903", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17711070903", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "17811070903", 86 ) );

		assertTrue( LibPhone.isValidMobileNumber( "19110550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19210550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19310550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19410550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19510550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19610550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19710550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19810550569", 86 ) );
		assertTrue( LibPhone.isValidMobileNumber( "19910550569", 86 ) );

	}

	@Test
	public void testGetCountryCodeByRegion()
	{
		assertTrue( 86 == LibPhone.getCountryCodeByRegion( "CN" ) );
	}


	@Test
	public void testGetRegionByCountryCode()
	{
		assertTrue( "CN".equalsIgnoreCase( LibPhone.getRegionByCountryCode( 86 ) ) );
		assertFalse( "CN".equalsIgnoreCase( LibPhone.getRegionByCountryCode( 11 ) ) );
	}
}