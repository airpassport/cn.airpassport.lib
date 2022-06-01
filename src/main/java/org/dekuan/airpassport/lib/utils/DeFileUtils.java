package org.dekuan.airpassport.lib.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;


public class DeFileUtils
{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger( DeFileUtils.class );


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
			logger.error( "Unable to determine file type for {} due to exception {}", file.toPath(), ioException );
		}

		return fileType;
	}


}
