package cn.airpassport.lib;

import cn.airpassport.lib.network.LibNetwork;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;


public class LibNetworkTests
{
	@Test
	public void testIsValidIpCase1()
	{
		Assertions.assertFalse( LibNetwork.isValidIpAddress( null ) );
		assertFalse( LibNetwork.isValidIpAddress( "" ) );
		assertFalse( LibNetwork.isValidIpAddress( "127" ) );
		assertFalse( LibNetwork.isValidIpAddress( "127.0" ) );
		assertFalse( LibNetwork.isValidIpAddress( "127.0.0" ) );
		assertTrue( LibNetwork.isValidIpAddress( "127.0.0.1" ) );
		assertTrue( LibNetwork.isValidIpAddress( "2408:8207:24d1:6fb0:8f7:f7e:1550:e48a" ) );

		assertTrue( LibNetwork.isValidIpV4Address( "127.0.0.1" ) );

		assertFalse( LibNetwork.isValidIpV4Address( "2408:8207:24d1:6fb0:8f7:f7e:1550:e48a" ) );
		assertTrue( LibNetwork.isValidIpV6Address( "2408:8207:24d1:6fb0:8f7:f7e:1550:e48a" ) );

		assertFalse( LibNetwork.isValidIpV4Address( "127.0.0.256" ) );
		assertTrue( LibNetwork.isValidIpV4Address( "255.255.255.255" ) );
		assertTrue( LibNetwork.isValidIpV4Address( "0.0.0.0" ) );
	}
}