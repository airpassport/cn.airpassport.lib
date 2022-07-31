package org.dekuan.airpassport.lib.oss.impls;

import org.dekuan.airpassport.lib.oss.OssPropertyLoader;
import org.dekuan.airpassport.lib.oss.OssStorage;
import org.dekuan.airpassport.lib.oss.impls.aliyun.AliyunOssService;


public class OssService implements OssStorage
{
	private final AliyunOssService aliyunOssService = new AliyunOssService();


	@Override
	public void setPropertyLoader( OssPropertyLoader ossPropertyLoader )
	{
		aliyunOssService.setPropertyLoader( ossPropertyLoader );
	}

	@Override
	public UploadReturn uploadFile( String fullFilePath )
	{
		return aliyunOssService.uploadFile( fullFilePath );
	}

	@Override
	public UploadReturn uploadFile( String fullFilePath, String customizeSha256, String customizeFileName,
					String customizeFileExt )
	{
		return aliyunOssService.uploadFile( fullFilePath, customizeSha256, customizeFileName, customizeFileExt );
	}

	@Override
	public String downloadToTemporaryFile( String keyName )
	{
		return aliyunOssService.downloadToTemporaryFile( keyName );
	}

	@Override
	public boolean downloadToFile( String keyName, String targetFullFilename )
	{
		return aliyunOssService.downloadToFile( keyName, targetFullFilename );
	}
}