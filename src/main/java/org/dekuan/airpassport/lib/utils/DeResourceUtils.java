package org.dekuan.airpassport.lib.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;

@Slf4j
public class DeResourceUtils
{
	public static void main( String[] args )
	{
		String json = DeResourceUtils.optResource( "test/xiw.json", "" );
		System.out.println( json );
	}


	public static String optResource( final String fileName, final String defaultValue )
	{
		return optResource( fileName, Charsets.UTF_8, defaultValue );
	}
	public static String optResource( final String fileName, Charset charset, final String defaultValue )
	{
		try
		{
			return readResource( fileName, charset );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return defaultValue;
	}


	public static String readResource( final String fileName ) throws IOException
	{
		return readResource( fileName, Charsets.UTF_8 );
	}
	public static String readResource( final String fileName, Charset charset ) throws IOException
	{
		if ( null == fileName )
		{
			throw new InvalidParameterException( "invalid fileName" );
		}
		if ( null == charset )
		{
			throw new InvalidParameterException( "invalid charset" );
		}

		URL url = Resources.getResource( fileName );
		return Resources.toString( url, charset );
	}
}