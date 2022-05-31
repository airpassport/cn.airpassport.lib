package org.dekuan.airpassport.lib;

import lombok.extern.slf4j.Slf4j;
import org.dekuan.airpassport.lib.http.HttpModel;
import org.dekuan.airpassport.lib.http.LibHttp;
import org.dekuan.airpassport.lib.http.LibHttpPost;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class LibHttpPostTests
{
	final String jwtToken	= "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJhdXRoIiwiZXhwIjoxNjU2Mjc2NzAxLCJ1c2VySWQiOjMzLCJpYXQiOjE2NTM2ODQ3MDEsImp0aSI6IjViNTUwOWRlLTQ5MGItNDIxZS05OWM3LTFlMzIxYWU2MzBjMyJ9.YxNt-emUohSq0-oEzxJNK9TqcgheH-9Y_NposLv8RoI97uki2FDU66wKNSGcaodHRmPgL8XJt_trT4VXwPg1DeXuKFppVQYJ0RweabXpum8ON5KYIAxvru0fOSH_pfiI8X-FOxV_vZaT7DuHus7SFNbjFmPtetukA2Q4NvSMGmschS76jnbkwMc38E3sVjzSfFgU2Y-ul5UATTV2OevgUXFHZ9dQvaP3G_b1hQTgsPrpH3A-pODs6YRAIQPt-BJRqFudB2fP5276ar1fODLBx4-KTHfLSsqbQWmlz28W8JEPHzodTyCBI0tjBrpBiMoxgfvfbO1IhtmMpSrg_rikNw";

	public void testPostRequestCase1()
	{
		String url = "http://localhost:2022/api/v1/mails/user/mails";
		String mailAddress = String.format( "rnd%d@163.com", System.currentTimeMillis() );
		HashMap<String,Object> postData    = new HashMap<>();
		postData.put( "tenantId", 1 );
		postData.put( "userId", 1 );
		postData.put( "mailAddress", mailAddress );
		postData.put( "mailPopHost", "pop.163.com" );
		postData.put( "mailPopPort", 995 );
		postData.put( "mailPopSSL", true );
		postData.put( "mailSmtpHost", "smtp.163.com" );
		postData.put( "mailSmtpPort", 465 );
		postData.put( "mailSmtpSSL", true );
		postData.put( "mailUsername", mailAddress );
		postData.put( "mailPassword", "11111" );

		LibHttpPost libHttp = LibHttpPost.builder()
			.url( url )
			.method( LibHttp.HttpMethod.POST )
			.contentType( LibHttp.HttpContentType.ApplicationJson )
			.postData( postData )
			.auth( LibHttp.Auth.builder()
				.authType( LibHttp.Auth.AuthType.Bearer )
				.bearerToken( jwtToken )
				.build() )
			.header( LibHttp.Header.builder()
				.xTenantIDValue( "1" )
				.build() )
			.build();
		HttpModel httpModel = libHttp.postRequest();
		log.debug( "httpModel : {}", httpModel.toString() );

		assertTrue( httpModel.getStatus() >= 200 );
	}
}
