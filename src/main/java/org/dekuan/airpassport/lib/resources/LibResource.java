package org.dekuan.airpassport.lib.resources;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.utils.DeFileUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

@Slf4j
public class LibResource
{
	public static void main( String[] args )
	{
		String json = LibResource.optResource( "test/xi.json", "" );
		System.out.println( json );

		String outputFilename = LibResource.dumpResourceToFile( "test/xi.json", "" );
		System.out.println( "outputFilename: " + outputFilename );
	}

	/**
	 * dump resource to file
	 *
	 * @param filename
	 *        {String}
	 * @param defaultValue
	 *        {String}
	 *
	 * @return {String}
	 */
	public static String dumpResourceToFile(
		final String filename,
		final String defaultValue )
	{
		final String outputFilename = DeFileUtils.createTemporaryFile( null );
		return LibResource.dumpResourceToFile( filename, defaultValue, outputFilename );
	}

	/**
	 * dump resource to file
	 *
	 * @param filename
	 *        {String}
	 * @param defaultValue
	 *        {String}
	 * @param outputFilename
	 *        {String}
	 *
	 * @return {String}
	 */
	public static String dumpResourceToFile(
		final String filename,
		final String defaultValue,
		final String outputFilename )
	{
		if ( Strings.isBlank( outputFilename ) )
		{
			throw new InvalidParameterException( "invalid outputFilename" );
		}

		String savedFilename = null;
		String content       = LibResource.optResource( filename, defaultValue );
		if ( null != content )
		{
			try
			{
				Path filePath = Paths.get( outputFilename );
				Files.write( filePath, content.getBytes() );
				savedFilename = outputFilename;
			}
			catch ( Exception e )
			{
				//e.printStackTrace();
			}
		}

		return savedFilename;
	}

	public static String optResource(
		final String filename,
		final String defaultValue )
	{
		return optResource( filename, Charsets.UTF_8, defaultValue );
	}

	public static String optResource(
		final String filename,
		Charset charset,
		final String defaultValue )
	{
		try
		{
			return readResource( filename, charset );
		}
		catch ( Exception e )
		{
			//e.printStackTrace();
			log.error( e.getMessage() );
		}

		return defaultValue;
	}


	public static String readResource( final String filename ) throws IOException
	{
		return readResource( filename, Charsets.UTF_8 );
	}

	public static String readResource(
		final String filename,
		Charset charset ) throws IOException
	{
		if ( Strings.isBlank( filename ) )
		{
			throw new InvalidParameterException( "invalid filename" );
		}
		if ( null == charset )
		{
			throw new InvalidParameterException( "invalid charset" );
		}

		URL url = Resources.getResource( filename );
		return Resources.toString( url, charset );
	}

//
//	public static String readResourceFile( String fileName )
//	{
//		return readResourceFile( fileName, StandardCharsets.UTF_8 );
//	}
//	public static String readResourceFile(
//		String fileName,
//		Charset charset )
//	{
//		if ( Strings.isBlank( fileName ) )
//		{
//			throw new InvalidParameterException( "invalid filename" );
//		}
//		if ( null == charset )
//		{
//			charset = StandardCharsets.UTF_8;
//		}
//
//		// 使用ClassLoader加载资源文件
//		InputStream inputStream = LibResource.class.getResourceAsStream( fileName );
//		if ( inputStream != null )
//		{
//			try ( BufferedReader reader = new BufferedReader(
//				new InputStreamReader( inputStream, charset ) ) )
//			{
//				// 使用Java 8的Stream API将文件内容转换为字符串
//				return reader.lines().collect( Collectors.joining( "\n" ) );
//			}
//			catch ( Exception e )
//			{
//				//e.printStackTrace();
//				log.error( e.getMessage() );
//			}
//		}
//		else
//		{
//			System.out.println( "无法找到文件: " + fileName );
//		}
//
//		return null;
//	}
}