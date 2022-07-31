package org.dekuan.airpassport.lib.oss;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 *	<a href="https://mkyong.com/spring-boot/spring-boot-yaml-example/">...</a>
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class OssPropertyLoader
{
	private AliyunOss aliyunOss = new AliyunOss();

	private List<SupportedMime> supportedMimes  = new ArrayList<>();


	public boolean isValid()
	{
		return aliyunOss.isValid() && supportedMimes.size() > 0;
	}

	public boolean isSupportedMimeType( @NotNull String mimeType )
	{
		//
		//	supportedMimes
		//		{ extension:, mimeType: }
		//
		String finalMimeType = mimeType.trim();
		return StringUtils.isNotBlank( finalMimeType ) &&
			supportedMimes.stream().anyMatch( m -> finalMimeType.equalsIgnoreCase( m.mimeType ) );
	}

	public String getExtensionByMimeType( String mimeType )
	{
		String finalMimeType = mimeType.trim();
		if ( ! this.isSupportedMimeType( finalMimeType ) )
		{
			return null;
		}

		SupportedMime supportedMime = supportedMimes.stream()
			.filter( m -> finalMimeType.equalsIgnoreCase( m.mimeType ) )
			.findFirst()
			.orElse( null );

		return null != supportedMime ? supportedMime.getExtension() : null;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class AliyunOss
	{
		public final static long FILE_SIZE_MIN_VALUE	= 0;

		//	in bytes, 52428800 = 50 * 1024 * 1024
		public final static long FILE_SIZE_MAX_VALUE	= 52428800;


		private String endpoint		= null;
		private String bucketName      = null;
		private String accessKeyId	= null;
		private String accessKeySecret = null;

		//	in bytes, 6291456 = 6 * 1024 * 1024
		private long maxFileSize	= 6291456;

		public boolean isValid()
		{
			return StringUtils.isNotBlank( this.endpoint ) &&
				StringUtils.isNotBlank( this.accessKeyId ) &&
				StringUtils.isNotBlank( this.accessKeySecret ) &&
				StringUtils.isNotBlank( this.bucketName ) &&
				( maxFileSize >= FILE_SIZE_MIN_VALUE && maxFileSize < FILE_SIZE_MAX_VALUE );
		}
	}


	@Getter
	@Setter
	@NoArgsConstructor
	public static class SupportedMime
	{
		//	doc, docx, pdf, ...
		private String extension;

		//	application/x-tika-ooxml, application/pdf, ...
		private String mimeType;
	}
}