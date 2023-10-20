package org.dekuan.airpassport.lib;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.dekuan.airpassport.lib.http.HttpModel;
import org.dekuan.airpassport.lib.http.LibHttp;
import org.dekuan.airpassport.lib.http.LibHttpEntityRequest;
import org.dekuan.airpassport.lib.http.LibHttpPost;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class LibHttpPostTests
{
	final String jwtToken	= "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNjU2Mjc2NzAxLCJ1c2VySWQiOjMzLCJpYXQiOjE2NTM2ODQ3MDEsImp0aSI6IjViNTUwOWRlLTQ5MGItNDIxZS05OWM3LTFlMzIxYWU2MzBjMyJ9.YxNt-emUohSq0-oEzxJNK9TqcgheH-9Y_NposLv8RoI97uki2FDU66wKNSGcaodHRmPgL8XJt_trT4VXwPg1DeXuKFppVQYJ0RweabXpum8ON5KYIAxvru0fOSH_pfiI8X-FOxV_vZaT7DuHus7SFNbjFmPtetukA2Q4NvSMGmschS76jnbkwMc38E3sVjzSfFgU2Y-ul5UATTV2OevgUXFHZ9dQvaP3G_b1hQTgsPrpH3A-pODs6YRAIQPt-BJRqFudB2fP5276ar1fODLBx4-KTHfLSsqbQWmlz28W8JEPHzodTyCBI0tjBrpBiMoxgfvfbO1IhtmMpSrg_rikNw";

	@Test
	public void testXiResumeAnalyze() throws IOException, JSONException
	{
//		String fullFilename = "/Users/xing/Documents/1.pdf";  //替换为您的文件名
//		String analyzeUrl = "http://api.xiaoxizn.com/v1/bundle/analyze_base?avatar=1&handle_image=1&rawtext=1&parsing_result=1"; //支持图片解析，提取简历头像，提取简历原文本，且返回简历解析结果
//		String clientId = "b17061f0-cb4f-11ec-9314-af6190b5c8c3";
//		String clientSecret = "deece848-4afc-4789-9594-76bb53380450";
//
//		File file = new File( fullFilename );
//		byte[] bytes             = org.apache.commons.io.FileUtils.readFileToByteArray( file );
//		String base64FileContent = new String( Base64.encodeBase64( bytes ), Consts.UTF_8 );
//
//		HashMap<String,String> postData    = new HashMap<>();
//		postData.put( "file_name", fullFilename );		//	文件名
//		postData.put( "resume_base", base64FileContent );	//	经 base64 编码过的文件内容
//
//		HashMap<String, String> customHeaders = new HashMap<>();
//		customHeaders.put( "id", clientId );
//		customHeaders.put( "secret", clientSecret );
//
//		LibHttpPost libHttp = LibHttpPost.builder()
//			.url( analyzeUrl )
//			.contentType( LibHttp.HttpContentType.ApplicationJson )
//			.postData( postData )
//			.timeout( 30 * 1000 )
//			.auth( LibHttp.Auth.builder()
//				.authType( LibHttp.Auth.AuthType.Bearer )
//				.bearerToken( "" )
//				.build() )
//			.header( LibHttp.Header.builder()
//				.xTenantIDValue( "1" )
//				.customHeaders( customHeaders )
//				.build() )
//			.build();
//		String     resContent = libHttp.fetchString();
//		JSONObject res        = new JSONObject( resContent );
//
//		System.out.println( res.toString( 8 ) );
	}

	@Test
	public void testPostRequestCase1()
	{
//		String url = "http://localhost:2022/api/v1/mails/user/mails";
//		String mailAddress = String.format( "rnd%d@163.com", System.currentTimeMillis() );
//		HashMap<String,Object> postData    = new HashMap<>();
//		postData.put( "tenantId", 1 );
//		postData.put( "userId", 1 );
//		postData.put( "mailAddress", mailAddress );
//		postData.put( "mailPopHost", "pop.163.com" );
//		postData.put( "mailPopPort", 995 );
//		postData.put( "mailPopSSL", true );
//		postData.put( "mailSmtpHost", "smtp.163.com" );
//		postData.put( "mailSmtpPort", 465 );
//		postData.put( "mailSmtpSSL", true );
//		postData.put( "mailUsername", mailAddress );
//		postData.put( "mailPassword", "11111" );
//
//		LibHttpPost libHttp = LibHttpPost.builder()
//			.url( url )
//			.contentType( LibHttp.HttpContentType.ApplicationJson )
//			.postData( postData )
//			.auth( LibHttp.Auth.builder()
//				.authType( LibHttp.Auth.AuthType.Bearer )
//				.bearerToken( jwtToken )
//				.build() )
//			.header( LibHttp.Header.builder()
//				.xTenantIDValue( "1" )
//				.build() )
//			.build();
//		HttpModel httpModel = libHttp.fetchHttpModel();
//		log.debug( "httpModel : {}", httpModel.toString() );
//
//		assertTrue( httpModel.getStatus() >= 200 );
	}
}
