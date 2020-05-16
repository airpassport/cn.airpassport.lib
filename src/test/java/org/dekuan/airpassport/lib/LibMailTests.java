package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.mail.LibMail;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LibMailTests
{
	@Test
	public void testEMailAddressesCase1()
	{
		assertEquals( false, LibMail.isValidEMail( "CN" ) );
		assertEquals( false, LibMail.isValidEMail( "" ) );
		assertEquals( false, LibMail.isValidEMail( null ) );
		assertEquals( true, LibMail.isValidEMail( "liuqixing@me.com" ) );
	}
}