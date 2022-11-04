package org.dekuan.airpassport.lib;

import org.dekuan.airpassport.lib.security.SortedSign;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortedSignTests
{
	@Test
	public void testHashMapMatchedCase1()
	{
		String signKey = "i am key";
		HashMap<String, String> map = new HashMap<>();
		map.put( "a", "112" );
		map.put( "b", "D3x" );
		map.put( "c", "exv" );
		map.put( "1", "map" );

		SortedSign sortedSign = new SortedSign();
		String digest = sortedSign.getSign( map, signKey );
		assertTrue( sortedSign.isMatched( map, signKey, digest ) );
	}


	@Test
	public void testTreeMapMatchedCase1()
	{
		String                  signKey = "i am key";
		TreeMap<String, String> map     = new TreeMap<>();
		map.put( "a", "112" );
		map.put( "b", "D3x" );
		map.put( "c", "exv" );
		map.put( "1", "map" );

		SortedSign sortedSign = new SortedSign();
		String digest = sortedSign.getSign( map, signKey );
		assertTrue( sortedSign.isMatched( map, signKey, digest ) );
	}
}
