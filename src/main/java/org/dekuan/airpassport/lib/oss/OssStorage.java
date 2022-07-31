package org.dekuan.airpassport.lib.oss;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

public interface OssStorage
{
	@Getter
	@Setter
	@SuperBuilder
	@ToString
	final class UploadReturn
	{
		//	oss key
		@Builder.Default
		private String ossPath = "";

		//	sha256
		@Builder.Default
		private String fileHash = "";

		//	extension
		@Builder.Default
		private String fileExtension = "";
	}

	void setPropertyLoader( OssPropertyLoader ossPropertyLoader );

	UploadReturn uploadFile( String fullFilePath );

	UploadReturn uploadFile( String fullFilePath, String customizeSha256, String customizeFileName, String customizeFileExt );

	String downloadToTemporaryFile( String keyName );

	boolean downloadToFile( String keyName, String targetFullFilename );
}
