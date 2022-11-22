package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.utils.DeValidationUtil;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeValidationUtilTests
{
	@Test
	public void testisValidUUIDCase1()
	{
		assertTrue( DeValidationUtil.isValidUUID( "8D1824CA-2B7F-4EC9-ADB4-4572195FA355" ) );
		assertFalse( DeValidationUtil.isValidUUID( "8D1824CA-" ) );
		assertFalse( DeValidationUtil.isValidUUID( "" ) );
	}

	@Test
	public void testisValidUUIDCase2()
	{
		UUID uuid = UUID.fromString( "8D1824CA-2B7F-4EC9-ADB4-4572195FA355" );
		assertTrue( DeValidationUtil.isValidUUID( uuid ) );
	}
}
