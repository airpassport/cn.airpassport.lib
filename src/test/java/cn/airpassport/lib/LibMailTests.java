package cn.airpassport.lib;

import cn.airpassport.lib.mail.LibMail;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LibMailTests
{
	@Test
	public void testEMailAddressesCase1()
	{
		Assertions.assertEquals( false, LibMail.isValidEMail( "CN" ) );
		assertEquals( false, LibMail.isValidEMail( "" ) );
		assertEquals( false, LibMail.isValidEMail( null ) );
		assertEquals( true, LibMail.isValidEMail( "liuqixing@me.com" ) );
	}
}