package org.dekuan.airpassport.lib.oss.impls;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.dekuan.airpassport.lib.oss.OssPropertyLoader;
import org.dekuan.airpassport.lib.oss.OssStorage;
import org.dekuan.airpassport.lib.utils.DeFileUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;


@Slf4j
public class UploadImageService
{
	private final OssPropertyLoader ossPros;


	public UploadImageService( OssPropertyLoader ossPropertyLoader )
	{
		this.ossPros = ossPropertyLoader;
	}


	public OssStorage.UploadReturn uploadImageByBase64Contents( String base64Contents ) throws IOException
	{
		if ( Strings.isBlank( base64Contents ) )
		{
			throw new InvalidParameterException( "invalid base64Contents" );
		}

		String tempFilename = DeFileUtils.createTemporaryFile( "base64-image" );
		if ( Strings.isBlank( tempFilename ) )
		{
			throw new InvalidParameterException( "failed to create temporary file" );
		}

		//	...
		byte[] dataForWriting = DatatypeConverter.parseBase64Binary( base64Contents );
		FileUtils.writeByteArrayToFile( new File( tempFilename ), dataForWriting );

		return this.uploadImageByFullFilePath( tempFilename );
	}

	public OssStorage.UploadReturn uploadImageFromUrl( String url ) throws IOException
	{
		if ( Strings.isBlank( url ) )
		{
			throw new InvalidParameterException( "invalid url" );
		}

		String tempFilename = DeFileUtils.createTemporaryFile( "url-image" );
		if ( Strings.isBlank( tempFilename ) )
		{
			throw new InvalidParameterException( "failed to create temporary file" );
		}

		//
		//	download image from url to local file
		//
		URL sourceUrl = new URL( url );
		File destinationFile = new File( tempFilename );
		int connectionTimeoutMillis	=      30 * 1000;
		int readTimeoutMillis		= 10 * 60 * 1000;
		FileUtils.copyURLToFile( sourceUrl, destinationFile, connectionTimeoutMillis, readTimeoutMillis );

		return this.uploadImageByFullFilePath( tempFilename );
	}

	public OssStorage.UploadReturn uploadImageByFullFilePath( String fullFilePath )
	{
		if ( Strings.isBlank( fullFilePath ) )
		{
			throw new InvalidParameterException( "invalid fullFilePath" );
		}

		File checkFile = new File( fullFilePath );
		if ( ! checkFile.exists() || checkFile.isDirectory() )
		{
			throw new InvalidParameterException( "file named fullFilePath does not exist" );
		}

		//	...
		OssService ossService = new OssService( this.ossPros );
		return ossService.uploadFile( fullFilePath );
	}



}