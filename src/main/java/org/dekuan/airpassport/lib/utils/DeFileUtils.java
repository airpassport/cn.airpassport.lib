package org.dekuan.airpassport.lib.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;


@Slf4j
public class DeFileUtils
{
	/**
	 *	@param prefix	The prefix string to be used in generating the file's name; must be at least three characters long
	 *	@return	{string}
	 */
	public static String createTemporaryFile( String prefix )
	{
		if ( Strings.isBlank( prefix ) )
		{
			throw new InvalidParameterException( "invalid prefix" );
		}

		//	...
		String filename = null;

		try
		{
			File fs = File.createTempFile( String.format( "det-%s", prefix ), ".tmp" );
			filename = fs.getAbsolutePath();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( e.getMessage() );
		}

		return filename;
	}

	public static long getFileSize( final String filePath )
	{
		if ( StringUtils.isBlank( filePath ) )
		{
			throw new InvalidParameterException( "invalid filePath" );
		}

		File file = new File( filePath );
		if ( file.exists() )
		{
			//	size of the file in bytes
			return file.length();
		}

		return 0;
	}

	/**
	 * 	identify mime type of file
	 */
	public static String detectFileMimeType( final String filePath )
	{
		if ( StringUtils.isBlank( filePath ) )
		{
			throw new InvalidParameterException( "invalid filePath" );
		}

		return DeFileUtils.detectFileMimeType( new File( filePath ) );
	}


	/**
	 *	detect mime type
	 *	<a href="https://www.baeldung.com/java-file-mime-type">https://www.baeldung.com/java-file-mime-type</a>
	 */
	public static String detectFileMimeType( final File file )
	{
		if ( null == file )
		{
			throw new InvalidParameterException( "invalid file" );
		}
		if ( ! file.exists() )
		{
			throw new InvalidParameterException( "filePath does not exists" );
		}

		//	...
		String fileType = "Undetermined";

		try
		{
			Tika tika	= new Tika();
			fileType	= tika.detect( file );
		}
		catch ( IOException ioException )
		{
			log.error( "Unable to determine file type for {} due to exception {}", file.toPath(), ioException );
		}

		return fileType;
	}
}
